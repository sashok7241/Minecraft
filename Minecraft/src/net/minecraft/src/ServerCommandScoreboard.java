package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.server.MinecraftServer;

public class ServerCommandScoreboard extends CommandBase
{
	protected void addObjective(ICommandSender p_96350_1_, String[] p_96350_2_, int p_96350_3_)
	{
		String var4 = p_96350_2_[p_96350_3_++];
		String var5 = p_96350_2_[p_96350_3_++];
		Scoreboard var6 = getScoreboardFromWorldServer();
		ScoreObjectiveCriteria var7 = (ScoreObjectiveCriteria) ScoreObjectiveCriteria.field_96643_a.get(var5);
		if(var7 == null)
		{
			String[] var10 = (String[]) ScoreObjectiveCriteria.field_96643_a.keySet().toArray(new String[0]);
			throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", new Object[] { joinNiceString(var10) });
		} else if(var6.getObjective(var4) != null) throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] { var4 });
		else if(var4.length() > 16) throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", new Object[] { var4, Integer.valueOf(16) });
		else
		{
			ScoreObjective var8 = var6.func_96535_a(var4, var7);
			if(p_96350_2_.length > p_96350_3_)
			{
				String var9 = func_82360_a(p_96350_1_, p_96350_2_, p_96350_3_);
				if(var9.length() > 32) throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", new Object[] { var9, Integer.valueOf(32) });
				if(var9.length() > 0)
				{
					var8.setDisplayName(var9);
				}
			}
			notifyAdmins(p_96350_1_, "commands.scoreboard.objectives.add.success", new Object[] { var4 });
		}
	}
	
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		if(p_71516_2_.length == 1) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "objectives", "players", "teams" });
		else
		{
			if(p_71516_2_[0].equalsIgnoreCase("objectives"))
			{
				if(p_71516_2_.length == 2) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "list", "add", "remove", "setdisplay" });
				if(p_71516_2_[1].equalsIgnoreCase("add"))
				{
					if(p_71516_2_.length == 4) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, ScoreObjectiveCriteria.field_96643_a.keySet());
				} else if(p_71516_2_[1].equalsIgnoreCase("remove"))
				{
					if(p_71516_2_.length == 3) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreObjectivesList(false));
				} else if(p_71516_2_[1].equalsIgnoreCase("setdisplay"))
				{
					if(p_71516_2_.length == 3) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "list", "sidebar", "belowName" });
					if(p_71516_2_.length == 4) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreObjectivesList(false));
				}
			} else if(p_71516_2_[0].equalsIgnoreCase("players"))
			{
				if(p_71516_2_.length == 2) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "set", "add", "remove", "reset", "list" });
				if(!p_71516_2_[1].equalsIgnoreCase("set") && !p_71516_2_[1].equalsIgnoreCase("add") && !p_71516_2_[1].equalsIgnoreCase("remove"))
				{
					if((p_71516_2_[1].equalsIgnoreCase("reset") || p_71516_2_[1].equalsIgnoreCase("list")) && p_71516_2_.length == 3) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreboardFromWorldServer().getObjectiveNames());
				} else
				{
					if(p_71516_2_.length == 3) return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
					if(p_71516_2_.length == 4) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreObjectivesList(true));
				}
			} else if(p_71516_2_[0].equalsIgnoreCase("teams"))
			{
				if(p_71516_2_.length == 2) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "add", "remove", "join", "leave", "empty", "list", "option" });
				if(p_71516_2_[1].equalsIgnoreCase("join"))
				{
					if(p_71516_2_.length == 3) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreboardFromWorldServer().func_96531_f());
					if(p_71516_2_.length >= 4) return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
				} else
				{
					if(p_71516_2_[1].equalsIgnoreCase("leave")) return getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
					if(!p_71516_2_[1].equalsIgnoreCase("empty") && !p_71516_2_[1].equalsIgnoreCase("list") && !p_71516_2_[1].equalsIgnoreCase("remove"))
					{
						if(p_71516_2_[1].equalsIgnoreCase("option"))
						{
							if(p_71516_2_.length == 3) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreboardFromWorldServer().func_96531_f());
							if(p_71516_2_.length == 4) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "color", "friendlyfire", "seeFriendlyInvisibles" });
							if(p_71516_2_.length == 5)
							{
								if(p_71516_2_[3].equalsIgnoreCase("color")) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, EnumChatFormatting.func_96296_a(true, false));
								if(p_71516_2_[3].equalsIgnoreCase("friendlyfire") || p_71516_2_[3].equalsIgnoreCase("seeFriendlyInvisibles")) return getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "true", "false" });
							}
						}
					} else if(p_71516_2_.length == 3) return getListOfStringsFromIterableMatchingLastWord(p_71516_2_, getScoreboardFromWorldServer().func_96531_f());
				}
			}
			return null;
		}
	}
	
	protected void addTeam(ICommandSender p_96340_1_, String[] p_96340_2_, int p_96340_3_)
	{
		String var4 = p_96340_2_[p_96340_3_++];
		Scoreboard var5 = getScoreboardFromWorldServer();
		if(var5.func_96508_e(var4) != null) throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] { var4 });
		else if(var4.length() > 16) throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", new Object[] { var4, Integer.valueOf(16) });
		else
		{
			ScorePlayerTeam var6 = var5.func_96527_f(var4);
			if(p_96340_2_.length > p_96340_3_)
			{
				String var7 = func_82360_a(p_96340_1_, p_96340_2_, p_96340_3_);
				if(var7.length() > 32) throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", new Object[] { var7, Integer.valueOf(32) });
				if(var7.length() > 0)
				{
					var6.func_96664_a(var7);
				}
			}
			notifyAdmins(p_96340_1_, "commands.scoreboard.teams.add.success", new Object[] { var4 });
		}
	}
	
	protected void emptyTeam(ICommandSender p_96346_1_, String[] p_96346_2_, int p_96346_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		ScorePlayerTeam var5 = getTeam(p_96346_2_[p_96346_3_++]);
		ArrayList var6 = new ArrayList(var5.getMembershipCollection());
		if(var6.isEmpty()) throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] { var5.func_96661_b() });
		else
		{
			Iterator var7 = var6.iterator();
			while(var7.hasNext())
			{
				String var8 = (String) var7.next();
				var4.removePlayerFromTeam(var8, var5);
			}
			notifyAdmins(p_96346_1_, "commands.scoreboard.teams.empty.success", new Object[] { Integer.valueOf(var6.size()), var5.func_96661_b() });
		}
	}
	
	@Override public String getCommandName()
	{
		return "scoreboard";
	}
	
	protected void getObjectivesList(ICommandSender p_96336_1_)
	{
		Scoreboard var2 = getScoreboardFromWorldServer();
		Collection var3 = var2.getScoreObjectives();
		if(var3.size() <= 0) throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
		else
		{
			p_96336_1_.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + p_96336_1_.translateString("commands.scoreboard.objectives.list.count", new Object[] { Integer.valueOf(var3.size()) }));
			Iterator var4 = var3.iterator();
			while(var4.hasNext())
			{
				ScoreObjective var5 = (ScoreObjective) var4.next();
				p_96336_1_.sendChatToPlayer(p_96336_1_.translateString("commands.scoreboard.objectives.list.entry", new Object[] { var5.getName(), var5.getDisplayName(), var5.getCriteria().func_96636_a() }));
			}
		}
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	protected Scoreboard getScoreboardFromWorldServer()
	{
		return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
	}
	
	protected ScoreObjective getScoreObjective(String p_96345_1_, boolean p_96345_2_)
	{
		Scoreboard var3 = getScoreboardFromWorldServer();
		ScoreObjective var4 = var3.getObjective(p_96345_1_);
		if(var4 == null) throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] { p_96345_1_ });
		else if(p_96345_2_ && var4.getCriteria().isReadOnly()) throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] { p_96345_1_ });
		else return var4;
	}
	
	protected List getScoreObjectivesList(boolean p_96335_1_)
	{
		Collection var2 = getScoreboardFromWorldServer().getScoreObjectives();
		ArrayList var3 = new ArrayList();
		Iterator var4 = var2.iterator();
		while(var4.hasNext())
		{
			ScoreObjective var5 = (ScoreObjective) var4.next();
			if(!p_96335_1_ || !var5.getCriteria().isReadOnly())
			{
				var3.add(var5.getName());
			}
		}
		return var3;
	}
	
	protected ScorePlayerTeam getTeam(String p_96338_1_)
	{
		Scoreboard var2 = getScoreboardFromWorldServer();
		ScorePlayerTeam var3 = var2.func_96508_e(p_96338_1_);
		if(var3 == null) throw new CommandException("commands.scoreboard.teamNotFound", new Object[] { p_96338_1_ });
		else return var3;
	}
	
	protected void getTeamList(ICommandSender p_96344_1_, String[] p_96344_2_, int p_96344_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		if(p_96344_2_.length > p_96344_3_)
		{
			ScorePlayerTeam var5 = getTeam(p_96344_2_[p_96344_3_++]);
			Collection var6 = var5.getMembershipCollection();
			if(var6.size() <= 0) throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] { var5.func_96661_b() });
			p_96344_1_.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + p_96344_1_.translateString("commands.scoreboard.teams.list.player.count", new Object[] { Integer.valueOf(var6.size()), var5.func_96661_b() }));
			p_96344_1_.sendChatToPlayer(joinNiceString(var6.toArray()));
		} else
		{
			Collection var8 = var4.func_96525_g();
			if(var8.size() <= 0) throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
			p_96344_1_.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + p_96344_1_.translateString("commands.scoreboard.teams.list.count", new Object[] { Integer.valueOf(var8.size()) }));
			Iterator var9 = var8.iterator();
			while(var9.hasNext())
			{
				ScorePlayerTeam var7 = (ScorePlayerTeam) var9.next();
				p_96344_1_.sendChatToPlayer(p_96344_1_.translateString("commands.scoreboard.teams.list.entry", new Object[] { var7.func_96661_b(), var7.func_96669_c(), Integer.valueOf(var7.getMembershipCollection().size()) }));
			}
		}
	}
	
	@Override public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_1_[0].equalsIgnoreCase("players") ? p_82358_2_ == 2 : !p_82358_1_[0].equalsIgnoreCase("teams") ? false : p_82358_2_ == 2 || p_82358_2_ == 3;
	}
	
	protected void joinTeam(ICommandSender p_96342_1_, String[] p_96342_2_, int p_96342_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		ScorePlayerTeam var5 = var4.func_96508_e(p_96342_2_[p_96342_3_++]);
		HashSet var6 = new HashSet();
		String var7;
		if(p_96342_1_ instanceof EntityPlayer && p_96342_3_ == p_96342_2_.length)
		{
			var7 = getCommandSenderAsPlayer(p_96342_1_).getEntityName();
			var4.func_96521_a(var7, var5);
			var6.add(var7);
		} else
		{
			while(p_96342_3_ < p_96342_2_.length)
			{
				var7 = func_96332_d(p_96342_1_, p_96342_2_[p_96342_3_++]);
				var4.func_96521_a(var7, var5);
				var6.add(var7);
			}
		}
		if(!var6.isEmpty())
		{
			notifyAdmins(p_96342_1_, "commands.scoreboard.teams.join.success", new Object[] { Integer.valueOf(var6.size()), var5.func_96661_b(), joinNiceString(var6.toArray(new String[0])) });
		}
	}
	
	protected void leaveTeam(ICommandSender p_96349_1_, String[] p_96349_2_, int p_96349_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		HashSet var5 = new HashSet();
		HashSet var6 = new HashSet();
		String var7;
		if(p_96349_1_ instanceof EntityPlayer && p_96349_3_ == p_96349_2_.length)
		{
			var7 = getCommandSenderAsPlayer(p_96349_1_).getEntityName();
			if(var4.func_96524_g(var7))
			{
				var5.add(var7);
			} else
			{
				var6.add(var7);
			}
		} else
		{
			while(p_96349_3_ < p_96349_2_.length)
			{
				var7 = func_96332_d(p_96349_1_, p_96349_2_[p_96349_3_++]);
				if(var4.func_96524_g(var7))
				{
					var5.add(var7);
				} else
				{
					var6.add(var7);
				}
			}
		}
		if(!var5.isEmpty())
		{
			notifyAdmins(p_96349_1_, "commands.scoreboard.teams.leave.success", new Object[] { Integer.valueOf(var5.size()), joinNiceString(var5.toArray(new String[0])) });
		}
		if(!var6.isEmpty()) throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] { Integer.valueOf(var6.size()), joinNiceString(var6.toArray(new String[0])) });
	}
	
	protected void listPlayers(ICommandSender p_96341_1_, String[] p_96341_2_, int p_96341_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		if(p_96341_2_.length > p_96341_3_)
		{
			String var5 = func_96332_d(p_96341_1_, p_96341_2_[p_96341_3_++]);
			Map var6 = var4.func_96510_d(var5);
			if(var6.size() <= 0) throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] { var5 });
			p_96341_1_.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + p_96341_1_.translateString("commands.scoreboard.players.list.player.count", new Object[] { Integer.valueOf(var6.size()), var5 }));
			Iterator var7 = var6.values().iterator();
			while(var7.hasNext())
			{
				Score var8 = (Score) var7.next();
				p_96341_1_.sendChatToPlayer(p_96341_1_.translateString("commands.scoreboard.players.list.player.entry", new Object[] { Integer.valueOf(var8.getScorePoints()), var8.func_96645_d().getDisplayName(), var8.func_96645_d().getName() }));
			}
		} else
		{
			Collection var9 = var4.getObjectiveNames();
			if(var9.size() <= 0) throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
			p_96341_1_.sendChatToPlayer(EnumChatFormatting.DARK_GREEN + p_96341_1_.translateString("commands.scoreboard.players.list.count", new Object[] { Integer.valueOf(var9.size()) }));
			p_96341_1_.sendChatToPlayer(joinNiceString(var9.toArray()));
		}
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length >= 1)
		{
			if(p_71515_2_[0].equalsIgnoreCase("objectives"))
			{
				if(p_71515_2_.length == 1) throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
				if(p_71515_2_[1].equalsIgnoreCase("list"))
				{
					getObjectivesList(p_71515_1_);
				} else if(p_71515_2_[1].equalsIgnoreCase("add"))
				{
					if(p_71515_2_.length < 4) throw new WrongUsageException("commands.scoreboard.objectives.add.usage", new Object[0]);
					addObjective(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("remove"))
				{
					if(p_71515_2_.length != 3) throw new WrongUsageException("commands.scoreboard.objectives.remove.usage", new Object[0]);
					removeObjective(p_71515_1_, p_71515_2_[2]);
				} else
				{
					if(!p_71515_2_[1].equalsIgnoreCase("setdisplay")) throw new WrongUsageException("commands.scoreboard.objectives.usage", new Object[0]);
					if(p_71515_2_.length != 3 && p_71515_2_.length != 4) throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
					setObjectivesDisplay(p_71515_1_, p_71515_2_, 2);
				}
				return;
			}
			if(p_71515_2_[0].equalsIgnoreCase("players"))
			{
				if(p_71515_2_.length == 1) throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
				if(p_71515_2_[1].equalsIgnoreCase("list"))
				{
					if(p_71515_2_.length > 3) throw new WrongUsageException("commands.scoreboard.players.list.usage", new Object[0]);
					listPlayers(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("add"))
				{
					if(p_71515_2_.length != 5) throw new WrongUsageException("commands.scoreboard.players.add.usage", new Object[0]);
					setPlayerScore(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("remove"))
				{
					if(p_71515_2_.length != 5) throw new WrongUsageException("commands.scoreboard.players.remove.usage", new Object[0]);
					setPlayerScore(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("set"))
				{
					if(p_71515_2_.length != 5) throw new WrongUsageException("commands.scoreboard.players.set.usage", new Object[0]);
					setPlayerScore(p_71515_1_, p_71515_2_, 2);
				} else
				{
					if(!p_71515_2_[1].equalsIgnoreCase("reset")) throw new WrongUsageException("commands.scoreboard.players.usage", new Object[0]);
					if(p_71515_2_.length != 3) throw new WrongUsageException("commands.scoreboard.players.reset.usage", new Object[0]);
					resetPlayerScore(p_71515_1_, p_71515_2_, 2);
				}
				return;
			}
			if(p_71515_2_[0].equalsIgnoreCase("teams"))
			{
				if(p_71515_2_.length == 1) throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
				if(p_71515_2_[1].equalsIgnoreCase("list"))
				{
					if(p_71515_2_.length > 3) throw new WrongUsageException("commands.scoreboard.teams.list.usage", new Object[0]);
					getTeamList(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("add"))
				{
					if(p_71515_2_.length < 3) throw new WrongUsageException("commands.scoreboard.teams.add.usage", new Object[0]);
					addTeam(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("remove"))
				{
					if(p_71515_2_.length != 3) throw new WrongUsageException("commands.scoreboard.teams.remove.usage", new Object[0]);
					removeTeam(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("empty"))
				{
					if(p_71515_2_.length != 3) throw new WrongUsageException("commands.scoreboard.teams.empty.usage", new Object[0]);
					emptyTeam(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("join"))
				{
					if(p_71515_2_.length < 4 && (p_71515_2_.length != 3 || !(p_71515_1_ instanceof EntityPlayer))) throw new WrongUsageException("commands.scoreboard.teams.join.usage", new Object[0]);
					joinTeam(p_71515_1_, p_71515_2_, 2);
				} else if(p_71515_2_[1].equalsIgnoreCase("leave"))
				{
					if(p_71515_2_.length < 3 && !(p_71515_1_ instanceof EntityPlayer)) throw new WrongUsageException("commands.scoreboard.teams.leave.usage", new Object[0]);
					leaveTeam(p_71515_1_, p_71515_2_, 2);
				} else
				{
					if(!p_71515_2_[1].equalsIgnoreCase("option")) throw new WrongUsageException("commands.scoreboard.teams.usage", new Object[0]);
					if(p_71515_2_.length != 4 && p_71515_2_.length != 5) throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
					setTeamOption(p_71515_1_, p_71515_2_, 2);
				}
				return;
			}
		}
		throw new WrongUsageException("commands.scoreboard.usage", new Object[0]);
	}
	
	protected void removeObjective(ICommandSender p_96337_1_, String p_96337_2_)
	{
		Scoreboard var3 = getScoreboardFromWorldServer();
		ScoreObjective var4 = getScoreObjective(p_96337_2_, false);
		var3.func_96519_k(var4);
		notifyAdmins(p_96337_1_, "commands.scoreboard.objectives.remove.success", new Object[] { p_96337_2_ });
	}
	
	protected void removeTeam(ICommandSender p_96343_1_, String[] p_96343_2_, int p_96343_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		ScorePlayerTeam var5 = getTeam(p_96343_2_[p_96343_3_++]);
		var4.func_96511_d(var5);
		notifyAdmins(p_96343_1_, "commands.scoreboard.teams.remove.success", new Object[] { var5.func_96661_b() });
	}
	
	protected void resetPlayerScore(ICommandSender p_96351_1_, String[] p_96351_2_, int p_96351_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		String var5 = func_96332_d(p_96351_1_, p_96351_2_[p_96351_3_++]);
		var4.func_96515_c(var5);
		notifyAdmins(p_96351_1_, "commands.scoreboard.players.reset.success", new Object[] { var5 });
	}
	
	protected void setObjectivesDisplay(ICommandSender p_96347_1_, String[] p_96347_2_, int p_96347_3_)
	{
		Scoreboard var4 = getScoreboardFromWorldServer();
		String var5 = p_96347_2_[p_96347_3_++];
		int var6 = Scoreboard.getObjectiveDisplaySlotNumber(var5);
		ScoreObjective var7 = null;
		if(p_96347_2_.length == 4)
		{
			var7 = getScoreObjective(p_96347_2_[p_96347_3_++], false);
		}
		if(var6 < 0) throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] { var5 });
		else
		{
			var4.func_96530_a(var6, var7);
			if(var7 != null)
			{
				notifyAdmins(p_96347_1_, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] { Scoreboard.getObjectiveDisplaySlot(var6), var7.getName() });
			} else
			{
				notifyAdmins(p_96347_1_, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] { Scoreboard.getObjectiveDisplaySlot(var6) });
			}
		}
	}
	
	protected void setPlayerScore(ICommandSender p_96339_1_, String[] p_96339_2_, int p_96339_3_)
	{
		String var4 = p_96339_2_[p_96339_3_ - 1];
		String var5 = func_96332_d(p_96339_1_, p_96339_2_[p_96339_3_++]);
		ScoreObjective var6 = getScoreObjective(p_96339_2_[p_96339_3_++], true);
		int var7 = var4.equalsIgnoreCase("set") ? parseInt(p_96339_1_, p_96339_2_[p_96339_3_++]) : parseIntWithMin(p_96339_1_, p_96339_2_[p_96339_3_++], 1);
		Scoreboard var8 = getScoreboardFromWorldServer();
		Score var9 = var8.func_96529_a(var5, var6);
		if(var4.equalsIgnoreCase("set"))
		{
			var9.func_96647_c(var7);
		} else if(var4.equalsIgnoreCase("add"))
		{
			var9.func_96649_a(var7);
		} else
		{
			var9.func_96646_b(var7);
		}
		notifyAdmins(p_96339_1_, "commands.scoreboard.players.set.success", new Object[] { var6.getName(), var5, Integer.valueOf(var9.getScorePoints()) });
	}
	
	protected void setTeamOption(ICommandSender p_96348_1_, String[] p_96348_2_, int p_96348_3_)
	{
		ScorePlayerTeam var4 = getTeam(p_96348_2_[p_96348_3_++]);
		String var5 = p_96348_2_[p_96348_3_++].toLowerCase();
		if(!var5.equalsIgnoreCase("color") && !var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
		else if(p_96348_2_.length == 4)
		{
			if(var5.equalsIgnoreCase("color")) throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, func_96333_a(EnumChatFormatting.func_96296_a(true, false)) });
			else if(!var5.equalsIgnoreCase("friendlyfire") && !var5.equalsIgnoreCase("seeFriendlyInvisibles")) throw new WrongUsageException("commands.scoreboard.teams.option.usage", new Object[0]);
			else throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, func_96333_a(Arrays.asList(new String[] { "true", "false" })) });
		} else
		{
			String var6 = p_96348_2_[p_96348_3_++];
			if(var5.equalsIgnoreCase("color"))
			{
				EnumChatFormatting var7 = EnumChatFormatting.func_96300_b(var6);
				if(var6 == null) throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, func_96333_a(EnumChatFormatting.func_96296_a(true, false)) });
				var4.func_96666_b(var7.toString());
				var4.func_96662_c(EnumChatFormatting.RESET.toString());
			} else if(var5.equalsIgnoreCase("friendlyfire"))
			{
				if(!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, func_96333_a(Arrays.asList(new String[] { "true", "false" })) });
				var4.func_96660_a(var6.equalsIgnoreCase("true"));
			} else if(var5.equalsIgnoreCase("seeFriendlyInvisibles"))
			{
				if(!var6.equalsIgnoreCase("true") && !var6.equalsIgnoreCase("false")) throw new WrongUsageException("commands.scoreboard.teams.option.noValue", new Object[] { var5, func_96333_a(Arrays.asList(new String[] { "true", "false" })) });
				var4.func_98300_b(var6.equalsIgnoreCase("true"));
			}
			notifyAdmins(p_96348_1_, "commands.scoreboard.teams.option.success", new Object[] { var5, var4.func_96661_b(), var6 });
		}
	}
}
