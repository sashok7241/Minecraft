package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOn extends CommandBase
{
	@Override public String getCommandName()
	{
		return "save-on";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		MinecraftServer var3 = MinecraftServer.getServer();
		for(WorldServer worldServer : var3.worldServers)
		{
			if(worldServer != null)
			{
				WorldServer var5 = worldServer;
				var5.canNotSave = false;
			}
		}
		notifyAdmins(p_71515_1_, "commands.save.enabled", new Object[0]);
	}
}
