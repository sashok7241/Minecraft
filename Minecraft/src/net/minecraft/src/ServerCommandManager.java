package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.server.MinecraftServer;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
	public ServerCommandManager()
	{
		registerCommand(new CommandTime());
		registerCommand(new CommandGameMode());
		registerCommand(new CommandDifficulty());
		registerCommand(new CommandDefaultGameMode());
		registerCommand(new CommandKill());
		registerCommand(new CommandToggleDownfall());
		registerCommand(new CommandWeather());
		registerCommand(new CommandXP());
		registerCommand(new CommandServerTp());
		registerCommand(new CommandGive());
		registerCommand(new CommandEffect());
		registerCommand(new CommandEnchant());
		registerCommand(new CommandServerEmote());
		registerCommand(new CommandShowSeed());
		registerCommand(new CommandHelp());
		registerCommand(new CommandDebug());
		registerCommand(new CommandServerMessage());
		registerCommand(new CommandServerSay());
		registerCommand(new CommandSetSpawnpoint());
		registerCommand(new CommandGameRule());
		registerCommand(new CommandClearInventory());
		registerCommand(new ServerCommandTestFor());
		registerCommand(new CommandSpreadPlayers());
		registerCommand(new CommandPlaySound());
		registerCommand(new ServerCommandScoreboard());
		if(MinecraftServer.getServer().isDedicatedServer())
		{
			registerCommand(new CommandServerOp());
			registerCommand(new CommandServerDeop());
			registerCommand(new CommandServerStop());
			registerCommand(new CommandServerSaveAll());
			registerCommand(new CommandServerSaveOff());
			registerCommand(new CommandServerSaveOn());
			registerCommand(new CommandServerBanIp());
			registerCommand(new CommandServerPardonIp());
			registerCommand(new CommandServerBan());
			registerCommand(new CommandServerBanlist());
			registerCommand(new CommandServerPardon());
			registerCommand(new CommandServerKick());
			registerCommand(new CommandServerList());
			registerCommand(new CommandServerWhitelist());
		} else
		{
			registerCommand(new CommandServerPublishLocal());
		}
		CommandBase.setAdminCommander(this);
	}
	
	@Override public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object ... par4ArrayOfObj)
	{
		boolean var5 = true;
		if(par1ICommandSender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"))
		{
			var5 = false;
		}
		ChatMessageComponent var6 = ChatMessageComponent.func_111082_b("chat.type.admin", new Object[] { par1ICommandSender.getCommandSenderName(), ChatMessageComponent.func_111082_b(par3Str, par4ArrayOfObj) });
		var6.func_111059_a(EnumChatFormatting.GRAY);
		var6.func_111063_b(Boolean.valueOf(true));
		if(var5)
		{
			Iterator var7 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
			while(var7.hasNext())
			{
				EntityPlayerMP var8 = (EntityPlayerMP) var7.next();
				if(var8 != par1ICommandSender && MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var8.getCommandSenderName()))
				{
					var8.sendChatToPlayer(var6);
				}
			}
		}
		if(par1ICommandSender != MinecraftServer.getServer())
		{
			MinecraftServer.getServer().sendChatToPlayer(var6);
		}
		if((par2 & 1) != 1)
		{
			par1ICommandSender.sendChatToPlayer(ChatMessageComponent.func_111082_b(par3Str, par4ArrayOfObj));
		}
	}
}
