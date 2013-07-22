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
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		p_71515_1_.sendChatToPlayer(p_71515_1_.translateString("commands.players.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().getCurrentPlayerCount()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()) }));
		p_71515_1_.sendChatToPlayer(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString());
	}
}
