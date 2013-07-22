package net.minecraft.src;

public class ReportedException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private final CrashReport theReportedExceptionCrashReport;
	
	public ReportedException(CrashReport p_i3253_1_)
	{
		theReportedExceptionCrashReport = p_i3253_1_;
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
