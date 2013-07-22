package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandDefaultGameMode extends CommandGameMode
{
	@Override public String getCommandName()
	{
		return "defaultgamemode";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return par1ICommandSender.translateString("commands.defaultgamemode.usage", new Object[0]);
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		if(par2ArrayOfStr.length > 0)
		{
			EnumGameType var3 = getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
			setGameType(var3);
			String var4 = StatCollector.translateToLocal("gameMode." + var3.getName());
			notifyAdmins(par1ICommandSender, "commands.defaultgamemode.success", new Object[] { var4 });
		} else throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
	}
	
	protected void setGameType(EnumGameType par1EnumGameType)
	{
		MinecraftServer.getServer().setGameType(par1EnumGameType);
	}
}
