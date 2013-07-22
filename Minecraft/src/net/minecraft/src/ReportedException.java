package net.minecraft.src;

public class ReportedException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private final CrashReport theReportedExceptionCrashReport;
	
	public ReportedException(CrashReport par1CrashReport)
	{
		theReportedExceptionCrashReport = par1CrashReport;
	}
	
	@Override public Throwable getCause()
	{
		return theReportedExceptionCrashReport.getCrashCause();
	}
	
	public CrashReport getCrashReport()
	{
		return theReportedExceptionCrashReport;
	}
	
	@Override public String getMessage()
	{
		return theReportedExceptionCrashReport.getDescription();
	}
}
