package net.minecraft.src;

public class ServerCommand
{
	public final String command;
	public final ICommandSender sender;
	
	public ServerCommand(String par1Str, ICommandSender par2ICommandSender)
	{
		command = par1Str;
		sender = par2ICommandSender;
	}
}
