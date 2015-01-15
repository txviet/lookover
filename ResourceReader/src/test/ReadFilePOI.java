package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class ReadFilePOI {
	public static void main(String[] args)
	{
		FileInputStream file;
		try {
			
			//http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
			
			file = new FileInputStream(new File("input/Test.xls"));
			//Get the workbook instance for XLS file
			HSSFWorkbook workbook;
			workbook = new HSSFWorkbook(file);
			 
			//Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			 
			//Get iterator to all the rows in current sheet
			Iterator<Row> rowIterator = sheet.iterator();
			 
			while(rowIterator.hasNext())
			{
				Row row = rowIterator.next();
				//Get iterator to all cells of current row
				Iterator<Cell> cellIterator = row.cellIterator();
				while(cellIterator.hasNext())
				{
					Cell cell = cellIterator.next();
					switch(cell.getCellType()) {
		                case Cell.CELL_TYPE_BOOLEAN:
		                    System.out.print(cell.getBooleanCellValue() + "\t\t");
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                    System.out.print(cell.getNumericCellValue() + "\t\t");
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    System.out.print(cell.getStringCellValue() + "\t\t");
		                    break;
		            }
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
