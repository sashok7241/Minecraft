package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerPardon extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsFromIterableMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet()) : null;
	}
	
	@Override public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive() && super.canCommandSenderUseCommand(p_71519_1_);
	}
	
	@Override public String getCommandName()
	{
		return "pardon";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.unban.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length == 1 && p_71515_2_[0].length() > 0)
		{
			MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(p_71515_2_[0]);
			notifyAdmins(p_71515_1_, "commands.unban.success", new Object[] { p_71515_2_[0] });
		} else throw new WrongUsageException("commands.unban.usage", new Object[0]);
	}
}
