package net.minecraft.src;

public class ServerCommand
{
	public final String command;
	public final ICommandSender sender;
	
	public ServerCommand(String p_i3369_1_, ICommandSender p_i3369_2_)
	{
		command = p_i3369_1_;
		sender = p_i3369_2_;
	}
}
