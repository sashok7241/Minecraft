package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerList extends CommandBase
{
	@Override public String getCommandName()
	{
		return "list";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString("commands.players.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().getCurrentPlayerCount()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()) }));
		par1ICommandSender.sendChatToPlayer(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString());
	}
}
