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
	
	@Override public void notifyAdmins(ICommandSender p_71563_1_, int p_71563_2_, String p_71563_3_, Object ... p_71563_4_)
	{
		boolean var5 = true;
		if(p_71563_1_ instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"))
		{
			var5 = false;
		}
		if(var5)
		{
			Iterator var6 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
			while(var6.hasNext())
			{
				EntityPlayerMP var7 = (EntityPlayerMP) var6.next();
				if(var7 != p_71563_1_ && MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var7.username))
				{
					var7.sendChatToPlayer("" + EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "[" + p_71563_1_.getCommandSenderName() + ": " + var7.translateString(p_71563_3_, p_71563_4_) + "]");
				}
			}
		}
		if(p_71563_1_ != MinecraftServer.getServer())
		{
			MinecraftServer.getServer().getLogAgent().logInfo("[" + p_71563_1_.getCommandSenderName() + ": " + MinecraftServer.getServer().translateString(p_71563_3_, p_71563_4_) + "]");
		}
		if((p_71563_2_ & 1) != 1)
		{
			p_71563_1_.sendChatToPlayer(p_71563_1_.translateString(p_71563_3_, p_71563_4_));
		}
	}
}
