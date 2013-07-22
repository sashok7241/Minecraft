package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerEmote extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}
	
	@Override public String getCommandName()
	{
		return "me";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.me.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length > 0)
		{
			String var3 = func_82361_a(p_71515_1_, p_71515_2_, 0, p_71515_1_.canCommandSenderUseCommand(1, "me"));
			MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat("* " + p_71515_1_.getCommandSenderName() + " " + var3));
		} else throw new WrongUsageException("commands.me.usage", new Object[0]);
	}
}
