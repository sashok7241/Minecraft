package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandToggleDownfall extends CommandBase
{
	@Override public String getCommandName()
	{
		return "toggledownfall";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		toggleDownfall();
		notifyAdmins(p_71515_1_, "commands.downfall.success", new Object[0]);
	}
	
	protected void toggleDownfall()
	{
		MinecraftServer.getServer().worldServers[0].toggleRain();
		MinecraftServer.getServer().worldServers[0].getWorldInfo().setThundering(true);
	}
}
