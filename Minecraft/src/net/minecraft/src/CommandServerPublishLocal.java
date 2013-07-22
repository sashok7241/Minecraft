package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerPublishLocal extends CommandBase
{
	@Override public String getCommandName()
	{
		return "publish";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.publish.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		String var3 = MinecraftServer.getServer().shareToLAN(EnumGameType.SURVIVAL, false);
		if(var3 != null)
		{
			notifyAdmins(par1ICommandSender, "commands.publish.started", new Object[] { var3 });
		} else
		{
			notifyAdmins(par1ICommandSender, "commands.publish.failed", new Object[0]);
		}
	}
}
