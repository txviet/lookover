package org.ctu.cusc.core.document.administrative;

import org.ctu.cusc.core.document.Document;

public class Report extends Document {
	public static String reportType;
	public static String description;
	
	class ReportType
	{
		final String WEEKLY = "WEEKLY";
		final String MONTHLY = "MONTHLY";
		final String ANNUALLY = "ANNUALLY";
		final String SPECIFIC_REPORT = "SPECIFIC_REPORT";
	}
}
