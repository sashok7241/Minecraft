package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveOff extends CommandBase
{
	@Override public String getCommandName()
	{
		return "save-off";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.save-off.usage";
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
				if(!var6.canNotSave)
				{
					var6.canNotSave = true;
					var4 = true;
				}
			}
		}
		if(var4)
		{
			notifyAdmins(par1ICommandSender, "commands.save.disabled", new Object[0]);
		} else throw new CommandException("commands.save-off.alreadyOff", new Object[0]);
	}
}
