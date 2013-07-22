package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandGameRule extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, getGameRules().getRules()) : p_71516_2_.length == 2 ? getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "true", "false" }) : null;
	}
	
	@Override public String getCommandName()
	{
		return "gamerule";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.gamerule.usage", new Object[0]);
	}
	
	private GameRules getGameRules()
	{
		return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		String var6;
		if(p_71515_2_.length == 2)
		{
			var6 = p_71515_2_[0];
			String var7 = p_71515_2_[1];
			GameRules var8 = getGameRules();
			if(var8.hasRule(var6))
			{
				var8.setOrCreateGameRule(var6, var7);
				notifyAdmins(p_71515_1_, "commands.gamerule.success", new Object[0]);
			} else
			{
				notifyAdmins(p_71515_1_, "commands.gamerule.norule", new Object[] { var6 });
			}
		} else if(p_71515_2_.length == 1)
		{
			var6 = p_71515_2_[0];
			GameRules var4 = getGameRules();
			if(var4.hasRule(var6))
			{
				String var5 = var4.getGameRuleStringValue(var6);
				p_71515_1_.sendChatToPlayer(var6 + " = " + var5);
			} else
			{
				notifyAdmins(p_71515_1_, "commands.gamerule.norule", new Object[] { var6 });
			}
		} else if(p_71515_2_.length == 0)
		{
			GameRules var3 = getGameRules();
			p_71515_1_.sendChatToPlayer(joinNiceString(var3.getRules()));
		} else throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
	}
}
