package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandEnchant extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, getListOfPlayers()) : null;
	}
	
	@Override public String getCommandName()
	{
		return "enchant";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.enchant.usage", new Object[0]);
	}
	
	protected String[] getListOfPlayers()
	{
		return MinecraftServer.getServer().getAllUsernames();
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
		if(p_71515_2_.length < 2) throw new WrongUsageException("commands.enchant.usage", new Object[0]);
		else
		{
			EntityPlayerMP var3 = func_82359_c(p_71515_1_, p_71515_2_[0]);
			int var4 = parseIntBounded(p_71515_1_, p_71515_2_[1], 0, Enchantment.enchantmentsList.length - 1);
			int var5 = 1;
			ItemStack var6 = var3.getCurrentEquippedItem();
			if(var6 == null)
			{
				notifyAdmins(p_71515_1_, "commands.enchant.noItem", new Object[0]);
			} else
			{
				Enchantment var7 = Enchantment.enchantmentsList[var4];
				if(var7 == null) throw new NumberInvalidException("commands.enchant.notFound", new Object[] { Integer.valueOf(var4) });
				else if(!var7.canApply(var6))
				{
					notifyAdmins(p_71515_1_, "commands.enchant.cantEnchant", new Object[0]);
				} else
				{
					if(p_71515_2_.length >= 3)
					{
						var5 = parseIntBounded(p_71515_1_, p_71515_2_[2], var7.getMinLevel(), var7.getMaxLevel());
					}
					if(var6.hasTagCompound())
					{
						NBTTagList var8 = var6.getEnchantmentTagList();
						if(var8 != null)
						{
							for(int var9 = 0; var9 < var8.tagCount(); ++var9)
							{
								short var10 = ((NBTTagCompound) var8.tagAt(var9)).getShort("id");
								if(Enchantment.enchantmentsList[var10] != null)
								{
									Enchantment var11 = Enchantment.enchantmentsList[var10];
									if(!var11.canApplyTogether(var7))
									{
										notifyAdmins(p_71515_1_, "commands.enchant.cantCombine", new Object[] { var7.getTranslatedName(var5), var11.getTranslatedName(((NBTTagCompound) var8.tagAt(var9)).getShort("lvl")) });
										return;
									}
								}
							}
						}
					}
					var6.addEnchantment(var7, var5);
					notifyAdmins(p_71515_1_, "commands.enchant.success", new Object[0]);
				}
			}
		}
	}
}
