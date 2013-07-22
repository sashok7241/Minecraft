package net.minecraft.src;

public class WrongUsageException extends SyntaxErrorException
{
	private static final long serialVersionUID = 1L;
	
	public WrongUsageException(String par1Str, Object ... par2ArrayOfObj)
	{
		super(par1Str, par2ArrayOfObj);
	}
}
