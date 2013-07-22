package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandGive extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, getPlayers()) : null;
	}
	
	@Override public String getCommandName()
	{
		return "give";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.give.usage", new Object[0]);
	}
	
	protected String[] getPlayers()
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
		if(p_71515_2_.length >= 2)
		{
			EntityPlayerMP var3 = func_82359_c(p_71515_1_, p_71515_2_[0]);
			int var4 = parseIntWithMin(p_71515_1_, p_71515_2_[1], 1);
			int var5 = 1;
			int var6 = 0;
			if(Item.itemsList[var4] == null) throw new NumberInvalidException("commands.give.notFound", new Object[] { Integer.valueOf(var4) });
			else
			{
				if(p_71515_2_.length >= 3)
				{
					var5 = parseIntBounded(p_71515_1_, p_71515_2_[2], 1, 64);
				}
				if(p_71515_2_.length >= 4)
				{
					var6 = parseInt(p_71515_1_, p_71515_2_[3]);
				}
				ItemStack var7 = new ItemStack(var4, var5, var6);
				EntityItem var8 = var3.dropPlayerItem(var7);
				var8.delayBeforeCanPickup = 0;
				notifyAdmins(p_71515_1_, "commands.give.success", new Object[] { Item.itemsList[var4].getItemStackDisplayName(var7), Integer.valueOf(var4), Integer.valueOf(var5), var3.getEntityName() });
			}
		} else throw new WrongUsageException("commands.give.usage", new Object[0]);
	}
}
