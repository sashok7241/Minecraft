package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandGameMode extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		return par2ArrayOfStr.length == 1 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, new String[] { "survival", "creative", "adventure" }) : par2ArrayOfStr.length == 2 ? getListOfStringsMatchingLastWord(par2ArrayOfStr, getListOfPlayerUsernames()) : null;
	}
	
	@Override public String getCommandName()
	{
		return "gamemode";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.gamemode.usage";
	}
	
	protected EnumGameType getGameModeFromCommand(ICommandSender par1ICommandSender, String par2Str)
	{
		return !par2Str.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) && !par2Str.equalsIgnoreCase("s") ? !par2Str.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) && !par2Str.equalsIgnoreCase("c") ? !par2Str.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) && !par2Str.equalsIgnoreCase("a") ? WorldSettings.getGameTypeById(parseIntBounded(par1ICommandSender, par2Str, 0, EnumGameType.values().length - 2)) : EnumGameType.ADVENTURE : EnumGameType.CREATIVE : EnumGameType.SURVIVAL;
	}
	
	protected String[] getListOfPlayerUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
	{
		return par2 == 1;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		if(par2ArrayOfStr.length > 0)
		{
			EnumGameType var3 = getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
			EntityPlayerMP var4 = par2ArrayOfStr.length >= 2 ? func_82359_c(par1ICommandSender, par2ArrayOfStr[1]) : getCommandSenderAsPlayer(par1ICommandSender);
			var4.setGameType(var3);
			var4.fallDistance = 0.0F;
			ChatMessageComponent var5 = ChatMessageComponent.func_111077_e("gameMode." + var3.getName());
			if(var4 != par1ICommandSender)
			{
				notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.other", new Object[] { var4.getEntityName(), var5 });
			} else
			{
				notifyAdmins(par1ICommandSender, 1, "commands.gamemode.success.self", new Object[] { var5 });
			}
		} else throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
	}
}
