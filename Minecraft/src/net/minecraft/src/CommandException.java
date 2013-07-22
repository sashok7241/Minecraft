package net.minecraft.src;

public class CommandException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private Object[] errorObjects;
	
	public CommandException(String par1Str, Object ... par2ArrayOfObj)
	{
		super(par1Str);
		errorObjects = par2ArrayOfObj;
	}
	
	public Object[] getErrorOjbects()
	{
		return errorObjects;
	}
}
