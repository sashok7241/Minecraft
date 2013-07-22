package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerList extends CommandBase
{
	@Override public String getCommandName()
	{
		return "list";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.players.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		par1ICommandSender.sendChatToPlayer(ChatMessageComponent.func_111082_b("commands.players.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().getCurrentPlayerCount()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers()) }));
		par1ICommandSender.sendChatToPlayer(ChatMessageComponent.func_111066_d(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString()));
	}
}
