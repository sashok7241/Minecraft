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
	
	@Override public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object ... par4ArrayOfObj)
	{
		boolean var5 = true;
		if(par1ICommandSender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput"))
		{
			var5 = false;
		}
		if(var5)
		{
			Iterator var6 = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
			while(var6.hasNext())
			{
				EntityPlayerMP var7 = (EntityPlayerMP) var6.next();
				if(var7 != par1ICommandSender && MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var7.username))
				{
					var7.sendChatToPlayer("" + EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + "[" + par1ICommandSender.getCommandSenderName() + ": " + var7.translateString(par3Str, par4ArrayOfObj) + "]");
				}
			}
		}
		if(par1ICommandSender != MinecraftServer.getServer())
		{
			MinecraftServer.getServer().getLogAgent().logInfo("[" + par1ICommandSender.getCommandSenderName() + ": " + MinecraftServer.getServer().translateString(par3Str, par4ArrayOfObj) + "]");
		}
		if((par2 & 1) != 1)
		{
			par1ICommandSender.sendChatToPlayer(par1ICommandSender.translateString(par3Str, par4ArrayOfObj));
		}
	}
}
