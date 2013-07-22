package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOn extends CommandBase
{
	@Override public String getCommandName()
	{
		return "save-on";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.save-on.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		MinecraftServer var3 = MinecraftServer.getServer();
		boolean var4 = false;
		for(WorldServer worldServer : var3.worldServers)
		{
			if(worldServer != null)
			{
				WorldServer var6 = worldServer;
				if(var6.canNotSave)
				{
					var6.canNotSave = false;
					var4 = true;
				}
			}
		}
		if(var4)
		{
			notifyAdmins(par1ICommandSender, "commands.save.enabled", new Object[0]);
		} else throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
	}
}
