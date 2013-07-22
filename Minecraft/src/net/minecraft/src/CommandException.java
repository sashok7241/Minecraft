package net.minecraft.src;

public class CommandException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private Object[] errorObjects;
	
	public CommandException(String p_i3254_1_, Object ... p_i3254_2_)
	{
		super(p_i3254_1_);
		errorObjects = p_i3254_2_;
	}
	
	public Object[] getErrorOjbects()
	{
		return errorObjects;
	}
}
