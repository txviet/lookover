package org.ctu.cusc.core.document;

import java.io.File;
import java.net.URI;

import org.ctu.cusc.core.source.Department;
import org.ctu.cusc.core.source.Member;

public class Document {
	public String code;
	public String title;
	public String content;
	public Signature[] signatureList;
	public URI uri;
	public File file;
	public RedirectAddresses redirectAddresses;
	
	public class RedirectAddresses
	{
		Department[] deparmentList;
		Member[] memberList;
	}
}
