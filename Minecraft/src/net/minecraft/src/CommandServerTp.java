package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerTp extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length != 1 && p_71516_2_.length != 2 ? null : getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}
	
	private double func_82367_a(ICommandSender p_82367_1_, double p_82367_2_, String p_82367_4_, int p_82367_5_, int p_82367_6_)
	{
		boolean var7 = p_82367_4_.startsWith("~");
		double var8 = var7 ? p_82367_2_ : 0.0D;
		if(!var7 || p_82367_4_.length() > 1)
		{
			boolean var10 = p_82367_4_.contains(".");
			if(var7)
			{
				p_82367_4_ = p_82367_4_.substring(1);
			}
			var8 += parseDouble(p_82367_1_, p_82367_4_);
			if(!var10 && !var7)
			{
				var8 += 0.5D;
			}
		}
		if(p_82367_5_ != 0 || p_82367_6_ != 0)
		{
			if(var8 < p_82367_5_) throw new NumberInvalidException("commands.generic.double.tooSmall", new Object[] { Double.valueOf(var8), Integer.valueOf(p_82367_5_) });
			if(var8 > p_82367_6_) throw new NumberInvalidException("commands.generic.double.tooBig", new Object[] { Double.valueOf(var8), Integer.valueOf(p_82367_6_) });
		}
		return var8;
	}
	
	private double func_82368_a(ICommandSender p_82368_1_, double p_82368_2_, String p_82368_4_)
	{
		return func_82367_a(p_82368_1_, p_82368_2_, p_82368_4_, -30000000, 30000000);
	}
	
	@Override public String getCommandName()
	{
		return "tp";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.tp.usage", new Object[0]);
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
		if(p_71515_2_.length < 1) throw new WrongUsageException("commands.tp.usage", new Object[0]);
		else
		{
			EntityPlayerMP var3;
			if(p_71515_2_.length != 2 && p_71515_2_.length != 4)
			{
				var3 = getCommandSenderAsPlayer(p_71515_1_);
			} else
			{
				var3 = func_82359_c(p_71515_1_, p_71515_2_[0]);
				if(var3 == null) throw new PlayerNotFoundException();
			}
			if(p_71515_2_.length != 3 && p_71515_2_.length != 4)
			{
				if(p_71515_2_.length == 1 || p_71515_2_.length == 2)
				{
					EntityPlayerMP var11 = func_82359_c(p_71515_1_, p_71515_2_[p_71515_2_.length - 1]);
					if(var11 == null) throw new PlayerNotFoundException();
					if(var11.worldObj != var3.worldObj)
					{
						notifyAdmins(p_71515_1_, "commands.tp.notSameDimension", new Object[0]);
						return;
					}
					var3.mountEntity((Entity) null);
					var3.playerNetServerHandler.setPlayerLocation(var11.posX, var11.posY, var11.posZ, var11.rotationYaw, var11.rotationPitch);
					notifyAdmins(p_71515_1_, "commands.tp.success", new Object[] { var3.getEntityName(), var11.getEntityName() });
				}
			} else if(var3.worldObj != null)
			{
				int var4 = p_71515_2_.length - 3;
				double var5 = func_82368_a(p_71515_1_, var3.posX, p_71515_2_[var4++]);
				double var7 = func_82367_a(p_71515_1_, var3.posY, p_71515_2_[var4++], 0, 0);
				double var9 = func_82368_a(p_71515_1_, var3.posZ, p_71515_2_[var4++]);
				var3.mountEntity((Entity) null);
				var3.setPositionAndUpdate(var5, var7, var9);
				notifyAdmins(p_71515_1_, "commands.tp.success.coordinates", new Object[] { var3.getEntityName(), Double.valueOf(var5), Double.valueOf(var7), Double.valueOf(var9) });
			}
		}
	}
}
