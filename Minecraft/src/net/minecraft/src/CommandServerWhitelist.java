package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerWhitelist extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		if(p_71516_2_.length == 1) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "on", "off", "list", "add", "remove", "reload" });
		else
		{
			if(p_71516_2_.length == 2)
			{
				if(p_71516_2_[0].equals("add"))
				{
					String[] var3 = MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat();
					ArrayList var4 = new ArrayList();
					String var5 = p_71516_2_[p_71516_2_.length - 1];
					String[] var6 = var3;
					int var7 = var3.length;
					for(int var8 = 0; var8 < var7; ++var8)
					{
						String var9 = var6[var8];
						if(doesStringStartWith(var5, var9) && !MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().contains(var9))
						{
							var4.add(var9);
						}
					}
					return var4;
				}
				if(p_71516_2_[0].equals("remove")) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers());
			}
			return null;
		}
	}
	
	@Override public String getCommandName()
	{
		return "whitelist";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.whitelist.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length >= 1)
		{
			if(p_71515_2_[0].equals("on"))
			{
				MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(true);
				notifyAdmins(p_71515_1_, "commands.whitelist.enabled", new Object[0]);
				return;
			}
			if(p_71515_2_[0].equals("off"))
			{
				MinecraftServer.getServer().getConfigurationManager().setWhiteListEnabled(false);
				notifyAdmins(p_71515_1_, "commands.whitelist.disabled", new Object[0]);
				return;
			}
			if(p_71515_2_[0].equals("list"))
			{
				p_71515_1_.sendChatToPlayer(p_71515_1_.translateString("commands.whitelist.list", new Object[] { Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().size()), Integer.valueOf(MinecraftServer.getServer().getConfigurationManager().getAvailablePlayerDat().length) }));
				p_71515_1_.sendChatToPlayer(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getWhiteListedPlayers().toArray(new String[0])));
				return;
			}
			if(p_71515_2_[0].equals("add"))
			{
				if(p_71515_2_.length < 2) throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
				MinecraftServer.getServer().getConfigurationManager().addToWhiteList(p_71515_2_[1]);
				notifyAdmins(p_71515_1_, "commands.whitelist.add.success", new Object[] { p_71515_2_[1] });
				return;
			}
			if(p_71515_2_[0].equals("remove"))
			{
				if(p_71515_2_.length < 2) throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
				MinecraftServer.getServer().getConfigurationManager().removeFromWhitelist(p_71515_2_[1]);
				notifyAdmins(p_71515_1_, "commands.whitelist.remove.success", new Object[] { p_71515_2_[1] });
				return;
			}
			if(p_71515_2_[0].equals("reload"))
			{
				MinecraftServer.getServer().getConfigurationManager().loadWhiteList();
				notifyAdmins(p_71515_1_, "commands.whitelist.reloaded", new Object[0]);
				return;
			}
		}
		throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
	}
}
