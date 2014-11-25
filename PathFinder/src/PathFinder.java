import java.io.IOException;
import java.util.*;
 
import org.apache.commons.lang.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
 
public class PathFinder {
 
    public static class Map extends Mapper<LongWritable, Text, Text, Text> {
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] nodes = line.split(",");
            if (nodes.length >= 2) {
                String firstNode = nodes[0];
                String[] restOfPath = Arrays.copyOfRange(nodes, 1, nodes.length);
                context.write(new Text(firstNode), new Text(StringUtils.join(restOfPath, ",")));
                Collections.reverse(Arrays.asList(nodes));
                firstNode = nodes[0];
                restOfPath = Arrays.copyOfRange(nodes, 1, nodes.length);
                context.write(new Text(firstNode), new Text(StringUtils.join(restOfPath, ",")));
            }
        }
    }
 
    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>();
            for (Text val : values) {
                paths.add(new ArrayList<String>(Arrays.asList(val.toString().split(","))));
            }
            // Remove duplicates.
            LinkedHashSet<ArrayList<String>> set = new LinkedHashSet<ArrayList<String>>(paths);
            paths.clear();
            paths.addAll(set);
            // Output original and new paths.
            ArrayList<String> nodes = new ArrayList<String>();
            ArrayList<String> nodes1 = new ArrayList<String>();
            ArrayList<String> nodes2 = new ArrayList<String>();
            for (int i = 0; i < paths.size(); i++) {
                nodes1 = paths.get(i);
                nodes.clear();
                nodes.add(key.toString());
                nodes.addAll(nodes1);
                context.write(null, new Text(StringUtils.join(nodes, ",")));   // Original paths.
                for (int j = i+1; j < paths.size(); j++) {
                    nodes2 = paths.get(j);
                    nodes.clear();
                    nodes.addAll(nodes1);
                    nodes.retainAll(nodes2);
                    if (nodes.isEmpty()) {  // nodes1 and nodes2 don't have a common node.
                        nodes.addAll(nodes1);
                        Collections.reverse(nodes);
                        nodes.add(key.toString());
                        nodes.addAll(nodes2);
                        context.write(null, new Text(StringUtils.join(nodes, ",")));   // New paths.
                    }
                }
            }
        }
    }
 
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
 
        Job job = new Job(conf, "PathFinder");
        job.setJarByClass(PathFinder.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
 
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
 
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
 
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
 
        job.waitForCompletion(true);
    }       
}