package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerPardon extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return par2ArrayOfStr.length == 1 ? getListOfStringsFromIterableMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getBannedList().keySet()) : null;
	}
	
	@Override public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
	{
		return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isListActive() && super.canCommandSenderUseCommand(par1ICommandSender);
	}
	
	@Override public String getCommandName()
	{
		return "pardon";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.unban.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		if(par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
		{
			MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().remove(par2ArrayOfStr[0]);
			notifyAdmins(par1ICommandSender, "commands.unban.success", new Object[] { par2ArrayOfStr[0] });
		} else throw new WrongUsageException("commands.unban.usage", new Object[0]);
	}
}
