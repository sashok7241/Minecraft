package net.minecraft.src;

import java.util.Arrays;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandServerMessage extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}
	
	@Override public List getCommandAliases()
	{
		return Arrays.asList(new String[] { "w", "msg" });
	}
	
	@Override public String getCommandName()
	{
		return "tell";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 0;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length < 2) throw new WrongUsageException("commands.message.usage", new Object[0]);
		else
		{
			EntityPlayerMP var3 = func_82359_c(p_71515_1_, p_71515_2_[0]);
			if(var3 == null) throw new PlayerNotFoundException();
			else if(var3 == p_71515_1_) throw new PlayerNotFoundException("commands.message.sameTarget", new Object[0]);
			else
			{
				String var4 = func_82361_a(p_71515_1_, p_71515_2_, 1, !(p_71515_1_ instanceof EntityPlayer));
				var3.sendChatToPlayer(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + var3.translateString("commands.message.display.incoming", new Object[] { p_71515_1_.getCommandSenderName(), var4 }));
				p_71515_1_.sendChatToPlayer(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + p_71515_1_.translateString("commands.message.display.outgoing", new Object[] { var3.getCommandSenderName(), var4 }));
			}
		}
	}
}
