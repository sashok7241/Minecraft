package net.minecraft.src;

public class CommandNotFoundException extends CommandException
{
	private static final long serialVersionUID = 1L;
	
	public CommandNotFoundException()
	{
		this("commands.generic.notFound", new Object[0]);
	}
	
	public CommandNotFoundException(String par1Str, Object ... par2ArrayOfObj)
	{
		super(par1Str, par2ArrayOfObj);
	}
}
