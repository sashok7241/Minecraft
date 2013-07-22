package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerBan extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length >= 1 ? getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
	}
	
	@Override public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive() && super.canCommandSenderUseCommand(p_71519_1_);
	}
	
	@Override public String getCommandName()
	{
		return "ban";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.ban.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length >= 1 && p_71515_2_[0].length() > 0)
		{
			EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(p_71515_2_[0]);
			BanEntry var4 = new BanEntry(p_71515_2_[0]);
			var4.setBannedBy(p_71515_1_.getCommandSenderName());
			if(p_71515_2_.length >= 2)
			{
				var4.setBanReason(func_82360_a(p_71515_1_, p_71515_2_, 1));
			}
			MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().put(var4);
			if(var3 != null)
			{
				var3.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
			}
			notifyAdmins(p_71515_1_, "commands.ban.success", new Object[] { p_71515_2_[0] });
		} else throw new WrongUsageException("commands.ban.usage", new Object[0]);
	}
}
