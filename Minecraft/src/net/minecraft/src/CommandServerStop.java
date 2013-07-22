package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerStop extends CommandBase
{
	@Override public String getCommandName()
	{
		return "stop";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		notifyAdmins(p_71515_1_, "commands.stop.start", new Object[0]);
		MinecraftServer.getServer().initiateShutdown();
	}
}
