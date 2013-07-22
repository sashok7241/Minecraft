package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandEffect extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, getAllUsernames()) : null;
	}
	
	protected String[] getAllUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	
	@Override public String getCommandName()
	{
		return "effect";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.effect.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 0;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length >= 2)
		{
			EntityPlayerMP var3 = func_82359_c(p_71515_1_, p_71515_2_[0]);
			int var4 = parseIntWithMin(p_71515_1_, p_71515_2_[1], 1);
			int var5 = 600;
			int var6 = 30;
			int var7 = 0;
			if(var4 >= 0 && var4 < Potion.potionTypes.length && Potion.potionTypes[var4] != null)
			{
				if(p_71515_2_.length >= 3)
				{
					var6 = parseIntBounded(p_71515_1_, p_71515_2_[2], 0, 1000000);
					if(Potion.potionTypes[var4].isInstant())
					{
						var5 = var6;
					} else
					{
						var5 = var6 * 20;
					}
				} else if(Potion.potionTypes[var4].isInstant())
				{
					var5 = 1;
				}
				if(p_71515_2_.length >= 4)
				{
					var7 = parseIntBounded(p_71515_1_, p_71515_2_[3], 0, 255);
				}
				if(var6 == 0)
				{
					if(!var3.isPotionActive(var4)) throw new CommandException("commands.effect.failure.notActive", new Object[] { StatCollector.translateToLocal(Potion.potionTypes[var4].getName()), var3.getEntityName() });
					var3.removePotionEffect(var4);
					notifyAdmins(p_71515_1_, "commands.effect.success.removed", new Object[] { StatCollector.translateToLocal(Potion.potionTypes[var4].getName()), var3.getEntityName() });
				} else
				{
					PotionEffect var8 = new PotionEffect(var4, var5, var7);
					var3.addPotionEffect(var8);
					notifyAdmins(p_71515_1_, "commands.effect.success", new Object[] { StatCollector.translateToLocal(var8.getEffectName()), Integer.valueOf(var4), Integer.valueOf(var7), var3.getEntityName(), Integer.valueOf(var6) });
				}
			} else throw new NumberInvalidException("commands.effect.notFound", new Object[] { Integer.valueOf(var4) });
		} else throw new WrongUsageException("commands.effect.usage", new Object[0]);
	}
}
