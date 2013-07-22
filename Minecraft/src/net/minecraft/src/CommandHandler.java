package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CommandHandler implements ICommandManager
{
	private final Map commandMap = new HashMap();
	private final Set commandSet = new HashSet();
	
	@Override public int executeCommand(ICommandSender p_71556_1_, String p_71556_2_)
	{
		p_71556_2_ = p_71556_2_.trim();
		if(p_71556_2_.startsWith("/"))
		{
			p_71556_2_ = p_71556_2_.substring(1);
		}
		String[] var3 = p_71556_2_.split(" ");
		String var4 = var3[0];
		var3 = dropFirstString(var3);
		ICommand var5 = (ICommand) commandMap.get(var4);
		int var6 = getUsernameIndex(var5, var3);
		int var7 = 0;
		try
		{
			if(var5 == null) throw new CommandNotFoundException();
			if(var5.canCommandSenderUseCommand(p_71556_1_))
			{
				if(var6 > -1)
				{
					EntityPlayerMP[] var8 = PlayerSelector.matchPlayers(p_71556_1_, var3[var6]);
					String var9 = var3[var6];
					EntityPlayerMP[] var10 = var8;
					int var11 = var8.length;
					for(int var12 = 0; var12 < var11; ++var12)
					{
						EntityPlayerMP var13 = var10[var12];
						var3[var6] = var13.getEntityName();
						try
						{
							var5.processCommand(p_71556_1_, var3);
							++var7;
						} catch(CommandException var15)
						{
							p_71556_1_.sendChatToPlayer(EnumChatFormatting.RED + p_71556_1_.translateString(var15.getMessage(), var15.getErrorOjbects()));
						}
					}
					var3[var6] = var9;
				} else
				{
					var5.processCommand(p_71556_1_, var3);
					++var7;
				}
			} else
			{
				p_71556_1_.sendChatToPlayer("" + EnumChatFormatting.RED + "You do not have permission to use this command.");
			}
		} catch(WrongUsageException var16)
		{
			p_71556_1_.sendChatToPlayer(EnumChatFormatting.RED + p_71556_1_.translateString("commands.generic.usage", new Object[] { p_71556_1_.translateString(var16.getMessage(), var16.getErrorOjbects()) }));
		} catch(CommandException var17)
		{
			p_71556_1_.sendChatToPlayer(EnumChatFormatting.RED + p_71556_1_.translateString(var17.getMessage(), var17.getErrorOjbects()));
		} catch(Throwable var18)
		{
			p_71556_1_.sendChatToPlayer(EnumChatFormatting.RED + p_71556_1_.translateString("commands.generic.exception", new Object[0]));
			var18.printStackTrace();
		}
		return var7;
	}
	
	@Override public Map getCommands()
	{
		return commandMap;
	}
	
	@Override public List getPossibleCommands(ICommandSender p_71557_1_)
	{
		ArrayList var2 = new ArrayList();
		Iterator var3 = commandSet.iterator();
		while(var3.hasNext())
		{
			ICommand var4 = (ICommand) var3.next();
			if(var4.canCommandSenderUseCommand(p_71557_1_))
			{
				var2.add(var4);
			}
		}
		return var2;
	}
	
	@Override public List getPossibleCommands(ICommandSender p_71558_1_, String p_71558_2_)
	{
		String[] var3 = p_71558_2_.split(" ", -1);
		String var4 = var3[0];
		if(var3.length == 1)
		{
			ArrayList var8 = new ArrayList();
			Iterator var6 = commandMap.entrySet().iterator();
			while(var6.hasNext())
			{
				Entry var7 = (Entry) var6.next();
				if(CommandBase.doesStringStartWith(var4, (String) var7.getKey()) && ((ICommand) var7.getValue()).canCommandSenderUseCommand(p_71558_1_))
				{
					var8.add(var7.getKey());
				}
			}
			return var8;
		} else
		{
			if(var3.length > 1)
			{
				ICommand var5 = (ICommand) commandMap.get(var4);
				if(var5 != null) return var5.addTabCompletionOptions(p_71558_1_, dropFirstString(var3));
			}
			return null;
		}
	}
	
	private int getUsernameIndex(ICommand p_82370_1_, String[] p_82370_2_)
	{
		if(p_82370_1_ == null) return -1;
		else
		{
			for(int var3 = 0; var3 < p_82370_2_.length; ++var3)
			{
				if(p_82370_1_.isUsernameIndex(p_82370_2_, var3) && PlayerSelector.matchesMultiplePlayers(p_82370_2_[var3])) return var3;
			}
			return -1;
		}
	}
	
	public ICommand registerCommand(ICommand p_71560_1_)
	{
		List var2 = p_71560_1_.getCommandAliases();
		commandMap.put(p_71560_1_.getCommandName(), p_71560_1_);
		commandSet.add(p_71560_1_);
		if(var2 != null)
		{
			Iterator var3 = var2.iterator();
			while(var3.hasNext())
			{
				String var4 = (String) var3.next();
				ICommand var5 = (ICommand) commandMap.get(var4);
				if(var5 == null || !var5.getCommandName().equals(var4))
				{
					commandMap.put(var4, p_71560_1_);
				}
			}
		}
		return p_71560_1_;
	}
	
	private static String[] dropFirstString(String[] p_71559_0_)
	{
		String[] var1 = new String[p_71559_0_.length - 1];
		for(int var2 = 1; var2 < p_71559_0_.length; ++var2)
		{
			var1[var2 - 1] = p_71559_0_[var2];
		}
		return var1;
	}
}
