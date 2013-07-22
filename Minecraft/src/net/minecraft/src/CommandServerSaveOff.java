package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOff extends CommandBase
{
	@Override public String getCommandName()
	{
		return "save-off";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		MinecraftServer var3 = MinecraftServer.getServer();
		for(WorldServer worldServer : var3.worldServers)
		{
			if(worldServer != null)
			{
				WorldServer var5 = worldServer;
				var5.canNotSave = true;
			}
		}
		notifyAdmins(par1ICommandSender, "commands.save.disabled", new Object[0]);
	}
}
