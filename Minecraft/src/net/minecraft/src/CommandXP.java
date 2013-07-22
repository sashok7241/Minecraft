package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandXP extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 2 ? getListOfStringsMatchingLastWord(p_71516_2_, getAllUsernames()) : null;
	}
	
	protected String[] getAllUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	
	@Override public String getCommandName()
	{
		return "xp";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.xp.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 1;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length <= 0) throw new WrongUsageException("commands.xp.usage", new Object[0]);
		else
		{
			String var4 = p_71515_2_[0];
			boolean var5 = var4.endsWith("l") || var4.endsWith("L");
			if(var5 && var4.length() > 1)
			{
				var4 = var4.substring(0, var4.length() - 1);
			}
			int var6 = parseInt(p_71515_1_, var4);
			boolean var7 = var6 < 0;
			if(var7)
			{
				var6 *= -1;
			}
			EntityPlayerMP var3;
			if(p_71515_2_.length > 1)
			{
				var3 = func_82359_c(p_71515_1_, p_71515_2_[1]);
			} else
			{
				var3 = getCommandSenderAsPlayer(p_71515_1_);
			}
			if(var5)
			{
				if(var7)
				{
					var3.addExperienceLevel(-var6);
					notifyAdmins(p_71515_1_, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(var6), var3.getEntityName() });
				} else
				{
					var3.addExperienceLevel(var6);
					notifyAdmins(p_71515_1_, "commands.xp.success.levels", new Object[] { Integer.valueOf(var6), var3.getEntityName() });
				}
			} else
			{
				if(var7) throw new WrongUsageException("commands.xp.failure.widthdrawXp", new Object[0]);
				var3.addExperience(var6);
				notifyAdmins(p_71515_1_, "commands.xp.success", new Object[] { Integer.valueOf(var6), var3.getEntityName() });
			}
		}
	}
}
