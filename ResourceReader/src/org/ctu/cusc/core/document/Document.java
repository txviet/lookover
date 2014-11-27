package org.ctu.cusc.core.document;

import org.ctu.cusc.core.source.Department;
import org.ctu.cusc.core.source.Member;

public class Document {
	public String title;
	public String content;
	public Signature[] signatureList;
	
	public class Redirect
	{
		Department[] deparmentList;
		Member[] memberList;
	}
}
