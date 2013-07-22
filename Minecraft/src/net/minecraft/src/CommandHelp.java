package net.minecraft.src;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.server.MinecraftServer;

public class CommandHelp extends CommandBase
{
	@Override public List getCommandAliases()
	{
		return Arrays.asList(new String[] { "?" });
	}
	
	@Override public String getCommandName()
	{
		return "help";
	}
	
	protected Map getCommands()
	{
		return MinecraftServer.getServer().getCommandManager().getCommands();
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.help.usage", new Object[0]);
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	protected List getSortedPossibleCommands(ICommandSender p_71534_1_)
	{
		List var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(p_71534_1_);
		Collections.sort(var2);
		return var2;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		List var3 = getSortedPossibleCommands(p_71515_1_);
		byte var4 = 7;
		int var5 = (var3.size() - 1) / var4;
		boolean var6 = false;
		ICommand var9;
		int var11;
		try
		{
			var11 = p_71515_2_.length == 0 ? 0 : parseIntBounded(p_71515_1_, p_71515_2_[0], 1, var5 + 1) - 1;
		} catch(NumberInvalidException var10)
		{
			Map var8 = getCommands();
			var9 = (ICommand) var8.get(p_71515_2_[0]);
			if(var9 != null) throw new WrongUsageException(var9.getCommandUsage(p_71515_1_), new Object[0]);
			throw new CommandNotFoundException();
		}
		int var7 = Math.min((var11 + 1) * var4, var3.size());
		p_71515_1_.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + p_71515_1_.translateString("commands.help.header", new Object[] { Integer.valueOf(var11 + 1), Integer.valueOf(var5 + 1) }));
		for(int var12 = var11 * var4; var12 < var7; ++var12)
		{
			var9 = (ICommand) var3.get(var12);
			p_71515_1_.sendChatToPlayer(var9.getCommandUsage(p_71515_1_));
		}
		if(var11 == 0 && p_71515_1_ instanceof EntityPlayer)
		{
			p_71515_1_.sendChatToPlayer(EnumChatFormatting.GREEN + p_71515_1_.translateString("commands.help.footer", new Object[0]));
		}
	}
}
