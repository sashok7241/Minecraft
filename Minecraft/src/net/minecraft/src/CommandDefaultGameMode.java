package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandDefaultGameMode extends CommandGameMode
{
	@Override public String getCommandName()
	{
		return "defaultgamemode";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.defaultgamemode.usage", new Object[0]);
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length > 0)
		{
			EnumGameType var3 = getGameModeFromCommand(p_71515_1_, p_71515_2_[0]);
			setGameType(var3);
			String var4 = StatCollector.translateToLocal("gameMode." + var3.getName());
			notifyAdmins(p_71515_1_, "commands.defaultgamemode.success", new Object[] { var4 });
		} else throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
	}
	
	protected void setGameType(EnumGameType p_71541_1_)
	{
		MinecraftServer.getServer().setGameType(p_71541_1_);
	}
}
