package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.server.MinecraftServer;

public class CommandServerBanIp extends CommandBase
{
	public static final Pattern IPv4Pattern = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
	
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
	}
	
	protected void banIP(ICommandSender p_71544_1_, String p_71544_2_, String p_71544_3_)
	{
		BanEntry var4 = new BanEntry(p_71544_2_);
		var4.setBannedBy(p_71544_1_.getCommandSenderName());
		if(p_71544_3_ != null)
		{
			var4.setBanReason(p_71544_3_);
		}
		MinecraftServer.getServer().getConfigurationManager().getBannedIPs().put(var4);
		List var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerList(p_71544_2_);
		String[] var6 = new String[var5.size()];
		int var7 = 0;
		EntityPlayerMP var9;
		for(Iterator var8 = var5.iterator(); var8.hasNext(); var6[var7++] = var9.getEntityName())
		{
			var9 = (EntityPlayerMP) var8.next();
			var9.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
		}
		if(var5.isEmpty())
		{
			notifyAdmins(p_71544_1_, "commands.banip.success", new Object[] { p_71544_2_ });
		} else
		{
			notifyAdmins(p_71544_1_, "commands.banip.success.players", new Object[] { p_71544_2_, joinNiceString(var6) });
		}
	}
	
	@Override public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isListActive() && super.canCommandSenderUseCommand(p_71519_1_);
	}
	
	@Override public String getCommandName()
	{
		return "ban-ip";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.banip.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length >= 1 && p_71515_2_[0].length() > 1)
		{
			Matcher var3 = IPv4Pattern.matcher(p_71515_2_[0]);
			String var4 = null;
			if(p_71515_2_.length >= 2)
			{
				var4 = func_82360_a(p_71515_1_, p_71515_2_, 1);
			}
			if(var3.matches())
			{
				banIP(p_71515_1_, p_71515_2_[0], var4);
			} else
			{
				EntityPlayerMP var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(p_71515_2_[0]);
				if(var5 == null) throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
				banIP(p_71515_1_, var5.getPlayerIP(), var4);
			}
		} else throw new WrongUsageException("commands.banip.usage", new Object[0]);
	}
}
