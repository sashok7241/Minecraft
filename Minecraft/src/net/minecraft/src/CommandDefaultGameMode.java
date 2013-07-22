package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.server.MinecraftServer;

public class CommandDefaultGameMode extends CommandGameMode
{
	@Override public String getCommandName()
	{
		return "defaultgamemode";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.defaultgamemode.usage";
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		if(par2ArrayOfStr.length > 0)
		{
			EnumGameType var3 = getGameModeFromCommand(par1ICommandSender, par2ArrayOfStr[0]);
			setGameType(var3);
			notifyAdmins(par1ICommandSender, "commands.defaultgamemode.success", new Object[] { ChatMessageComponent.func_111077_e("gameMode." + var3.getName()) });
		} else throw new WrongUsageException("commands.defaultgamemode.usage", new Object[0]);
	}
	
	protected void setGameType(EnumGameType par1EnumGameType)
	{
		MinecraftServer var2 = MinecraftServer.getServer();
		var2.setGameType(par1EnumGameType);
		EntityPlayerMP var4;
		if(var2.func_104056_am())
		{
			for(Iterator var3 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator(); var3.hasNext(); var4.fallDistance = 0.0F)
			{
				var4 = (EntityPlayerMP) var3.next();
				var4.setGameType(par1EnumGameType);
			}
		}
	}
}
