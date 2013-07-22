package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerOp extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		if(p_71516_2_.length == 1)
		{
			String var3 = p_71516_2_[p_71516_2_.length - 1];
			ArrayList var4 = new ArrayList();
			String[] var5 = MinecraftServer.getServer().getAllUsernames();
			int var6 = var5.length;
			for(int var7 = 0; var7 < var6; ++var7)
			{
				String var8 = var5[var7];
				if(!MinecraftServer.getServer().getConfigurationManager().areCommandsAllowed(var8) && doesStringStartWith(var3, var8))
				{
					var4.add(var8);
				}
			}
			return var4;
		} else return null;
	}
	
	@Override public String getCommandName()
	{
		return "op";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.op.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length == 1 && p_71515_2_[0].length() > 0)
		{
			MinecraftServer.getServer().getConfigurationManager().addOp(p_71515_2_[0]);
			notifyAdmins(p_71515_1_, "commands.op.success", new Object[] { p_71515_2_[0] });
		} else throw new WrongUsageException("commands.op.usage", new Object[0]);
	}
}
