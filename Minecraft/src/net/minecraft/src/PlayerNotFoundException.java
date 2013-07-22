package net.minecraft.src;

public class PlayerNotFoundException extends CommandException
{
	private static final long serialVersionUID = 1L;
	
	public PlayerNotFoundException()
	{
		this("commands.generic.player.notFound", new Object[0]);
	}
	
	public PlayerNotFoundException(String p_i3257_1_, Object ... p_i3257_2_)
	{
		super(p_i3257_1_, p_i3257_2_);
	}
}
