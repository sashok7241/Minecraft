package net.minecraft.src;

public class CommandNotFoundException extends CommandException
{
	private static final long serialVersionUID = 1L;
	
	public CommandNotFoundException()
	{
		this("commands.generic.notFound", new Object[0]);
	}
	
	public CommandNotFoundException(String p_i3258_1_, Object ... p_i3258_2_)
	{
		super(p_i3258_1_, p_i3258_2_);
	}
}
