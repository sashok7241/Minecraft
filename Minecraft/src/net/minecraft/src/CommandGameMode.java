package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandGameMode extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "survival", "creative", "adventure" }) : p_71516_2_.length == 2 ? getListOfStringsMatchingLastWord(p_71516_2_, getListOfPlayerUsernames()) : null;
	}
	
	@Override public String getCommandName()
	{
		return "gamemode";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.gamemode.usage", new Object[0]);
	}
	
	protected EnumGameType getGameModeFromCommand(ICommandSender p_71539_1_, String p_71539_2_)
	{
		return !p_71539_2_.equalsIgnoreCase(EnumGameType.SURVIVAL.getName()) && !p_71539_2_.equalsIgnoreCase("s") ? !p_71539_2_.equalsIgnoreCase(EnumGameType.CREATIVE.getName()) && !p_71539_2_.equalsIgnoreCase("c") ? !p_71539_2_.equalsIgnoreCase(EnumGameType.ADVENTURE.getName()) && !p_71539_2_.equalsIgnoreCase("a") ? WorldSettings.getGameTypeById(parseIntBounded(p_71539_1_, p_71539_2_, 0, EnumGameType.values().length - 2)) : EnumGameType.ADVENTURE : EnumGameType.CREATIVE : EnumGameType.SURVIVAL;
	}
	
	protected String[] getListOfPlayerUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 1;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length > 0)
		{
			EnumGameType var3 = getGameModeFromCommand(p_71515_1_, p_71515_2_[0]);
			EntityPlayerMP var4 = p_71515_2_.length >= 2 ? func_82359_c(p_71515_1_, p_71515_2_[1]) : getCommandSenderAsPlayer(p_71515_1_);
			var4.setGameType(var3);
			var4.fallDistance = 0.0F;
			String var5 = StatCollector.translateToLocal("gameMode." + var3.getName());
			if(var4 != p_71515_1_)
			{
				notifyAdmins(p_71515_1_, 1, "commands.gamemode.success.other", new Object[] { var4.getEntityName(), var5 });
			} else
			{
				notifyAdmins(p_71515_1_, 1, "commands.gamemode.success.self", new Object[] { var5 });
			}
		} else throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
	}
}
