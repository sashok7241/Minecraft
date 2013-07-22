package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerPublishLocal extends CommandBase
{
	@Override public String getCommandName()
	{
		return "publish";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		String var3 = MinecraftServer.getServer().shareToLAN(EnumGameType.SURVIVAL, false);
		if(var3 != null)
		{
			notifyAdmins(p_71515_1_, "commands.publish.started", new Object[] { var3 });
		} else
		{
			notifyAdmins(p_71515_1_, "commands.publish.failed", new Object[0]);
		}
	}
}
