package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandClearInventory extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, getAllOnlineUsernames()) : null;
	}
	
	protected String[] getAllOnlineUsernames()
	{
		return MinecraftServer.getServer().getAllUsernames();
	}
	
	@Override public String getCommandName()
	{
		return "clear";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.clear.usage", new Object[0]);
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
		EntityPlayerMP var3 = p_71515_2_.length == 0 ? getCommandSenderAsPlayer(p_71515_1_) : func_82359_c(p_71515_1_, p_71515_2_[0]);
		int var4 = p_71515_2_.length >= 2 ? parseIntWithMin(p_71515_1_, p_71515_2_[1], 1) : -1;
		int var5 = p_71515_2_.length >= 3 ? parseIntWithMin(p_71515_1_, p_71515_2_[2], 0) : -1;
		int var6 = var3.inventory.clearInventory(var4, var5);
		var3.inventoryContainer.detectAndSendChanges();
		if(var6 == 0) throw new CommandException("commands.clear.failure", new Object[] { var3.getEntityName() });
		else
		{
			notifyAdmins(p_71515_1_, "commands.clear.success", new Object[] { var3.getEntityName(), Integer.valueOf(var6) });
		}
	}
}
