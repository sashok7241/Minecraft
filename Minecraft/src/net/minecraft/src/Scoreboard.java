package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Scoreboard
{
	private final Map scoreObjectives = new HashMap();
	private final Map field_96543_b = new HashMap();
	private final Map field_96544_c = new HashMap();
	private final ScoreObjective[] field_96541_d = new ScoreObjective[3];
	private final Map field_96542_e = new HashMap();
	private final Map teamMemberships = new HashMap();
	
	public ScorePlayerTeam func_96508_e(String p_96508_1_)
	{
		return (ScorePlayerTeam) field_96542_e.get(p_96508_1_);
	}
	
	public Map func_96510_d(String p_96510_1_)
	{
		Object var2 = field_96544_c.get(p_96510_1_);
		if(var2 == null)
		{
			var2 = new HashMap();
		}
		return (Map) var2;
	}
	
	public void func_96511_d(ScorePlayerTeam p_96511_1_)
	{
		field_96542_e.remove(p_96511_1_.func_96661_b());
		Iterator var2 = p_96511_1_.getMembershipCollection().iterator();
		while(var2.hasNext())
		{
			String var3 = (String) var2.next();
			teamMemberships.remove(var3);
		}
		func_96513_c(p_96511_1_);
	}
	
	public void func_96513_c(ScorePlayerTeam p_96513_1_)
	{
	}
	
	public void func_96515_c(String p_96515_1_)
	{
		Map var2 = (Map) field_96544_c.remove(p_96515_1_);
		if(var2 != null)
		{
			func_96516_a(p_96515_1_);
		}
	}
	
	public void func_96516_a(String p_96516_1_)
	{
	}
	
	public void func_96519_k(ScoreObjective p_96519_1_)
	{
		scoreObjectives.remove(p_96519_1_.getName());
		for(int var2 = 0; var2 < 3; ++var2)
		{
			if(func_96539_a(var2) == p_96519_1_)
			{
				func_96530_a(var2, (ScoreObjective) null);
			}
		}
		List var5 = (List) field_96543_b.get(p_96519_1_.getCriteria());
		if(var5 != null)
		{
			var5.remove(p_96519_1_);
		}
		Iterator var3 = field_96544_c.values().iterator();
		while(var3.hasNext())
		{
			Map var4 = (Map) var3.next();
			var4.remove(p_96519_1_);
		}
		func_96533_c(p_96519_1_);
	}
	
	public Collection func_96520_a(ScoreObjectiveCriteria p_96520_1_)
	{
		Collection var2 = (Collection) field_96543_b.get(p_96520_1_);
		return var2 == null ? new ArrayList() : new ArrayList(var2);
	}
	
	public void func_96521_a(String p_96521_1_, ScorePlayerTeam p_96521_2_)
	{
		if(getPlayersTeam(p_96521_1_) != null)
		{
			func_96524_g(p_96521_1_);
		}
		teamMemberships.put(p_96521_1_, p_96521_2_);
		p_96521_2_.getMembershipCollection().add(p_96521_1_);
	}
	
	public void func_96522_a(ScoreObjective p_96522_1_)
	{
	}
	
	public void func_96523_a(ScorePlayerTeam p_96523_1_)
	{
	}
	
	public boolean func_96524_g(String p_96524_1_)
	{
		ScorePlayerTeam var2 = getPlayersTeam(p_96524_1_);
		if(var2 != null)
		{
			removePlayerFromTeam(p_96524_1_, var2);
			return true;
		} else return false;
	}
	
	public Collection func_96525_g()
	{
		return field_96542_e.values();
	}
	
	public ScorePlayerTeam func_96527_f(String p_96527_1_)
	{
		ScorePlayerTeam var2 = func_96508_e(p_96527_1_);
		if(var2 != null) throw new IllegalArgumentException("An objective with the name \'" + p_96527_1_ + "\' already exists!");
		else
		{
			var2 = new ScorePlayerTeam(this, p_96527_1_);
			field_96542_e.put(p_96527_1_, var2);
			func_96523_a(var2);
			return var2;
		}
	}
	
	public Collection func_96528_e()
	{
		Collection var1 = field_96544_c.values();
		ArrayList var2 = new ArrayList();
		if(var1 != null)
		{
			Iterator var3 = var1.iterator();
			while(var3.hasNext())
			{
				Map var4 = (Map) var3.next();
				var2.addAll(var4.values());
			}
		}
		return var2;
	}
	
	public Score func_96529_a(String p_96529_1_, ScoreObjective p_96529_2_)
	{
		Object var3 = field_96544_c.get(p_96529_1_);
		if(var3 == null)
		{
			var3 = new HashMap();
			field_96544_c.put(p_96529_1_, var3);
		}
		Score var4 = (Score) ((Map) var3).get(p_96529_2_);
		if(var4 == null)
		{
			var4 = new Score(this, p_96529_2_, p_96529_1_);
			((Map) var3).put(p_96529_2_, var4);
		}
		return var4;
	}
	
	public void func_96530_a(int p_96530_1_, ScoreObjective p_96530_2_)
	{
		field_96541_d[p_96530_1_] = p_96530_2_;
	}
	
	public Collection func_96531_f()
	{
		return field_96542_e.keySet();
	}
	
	public void func_96532_b(ScoreObjective p_96532_1_)
	{
	}
	
	public void func_96533_c(ScoreObjective p_96533_1_)
	{
	}
	
	public Collection func_96534_i(ScoreObjective p_96534_1_)
	{
		ArrayList var2 = new ArrayList();
		Iterator var3 = field_96544_c.values().iterator();
		while(var3.hasNext())
		{
			Map var4 = (Map) var3.next();
			Score var5 = (Score) var4.get(p_96534_1_);
			if(var5 != null)
			{
				var2.add(var5);
			}
		}
		Collections.sort(var2, Score.field_96658_a);
		return var2;
	}
	
	public ScoreObjective func_96535_a(String p_96535_1_, ScoreObjectiveCriteria p_96535_2_)
	{
		ScoreObjective var3 = getObjective(p_96535_1_);
		if(var3 != null) throw new IllegalArgumentException("An objective with the name \'" + p_96535_1_ + "\' already exists!");
		else
		{
			var3 = new ScoreObjective(this, p_96535_1_, p_96535_2_);
			Object var4 = field_96543_b.get(p_96535_2_);
			if(var4 == null)
			{
				var4 = new ArrayList();
				field_96543_b.put(p_96535_2_, var4);
			}
			((List) var4).add(var3);
			scoreObjectives.put(p_96535_1_, var3);
			func_96522_a(var3);
			return var3;
		}
	}
	
	public void func_96536_a(Score p_96536_1_)
	{
	}
	
	public void func_96538_b(ScorePlayerTeam p_96538_1_)
	{
	}
	
	public ScoreObjective func_96539_a(int p_96539_1_)
	{
		return field_96541_d[p_96539_1_];
	}
	
	public ScoreObjective getObjective(String p_96518_1_)
	{
		return (ScoreObjective) scoreObjectives.get(p_96518_1_);
	}
	
	public Collection getObjectiveNames()
	{
		return field_96544_c.keySet();
	}
	
	public ScorePlayerTeam getPlayersTeam(String p_96509_1_)
	{
		return (ScorePlayerTeam) teamMemberships.get(p_96509_1_);
	}
	
	public Collection getScoreObjectives()
	{
		return scoreObjectives.values();
	}
	
	public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_)
	{
		if(getPlayersTeam(p_96512_1_) != p_96512_2_) throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team \'" + p_96512_2_.func_96661_b() + "\'.");
		else
		{
			teamMemberships.remove(p_96512_1_);
			p_96512_2_.getMembershipCollection().remove(p_96512_1_);
		}
	}
	
	public static String getObjectiveDisplaySlot(int p_96517_0_)
	{
		switch(p_96517_0_)
		{
			case 0:
				return "list";
			case 1:
				return "sidebar";
			case 2:
				return "belowName";
			default:
				return null;
		}
	}
	
	public static int getObjectiveDisplaySlotNumber(String p_96537_0_)
	{
		return p_96537_0_.equalsIgnoreCase("list") ? 0 : p_96537_0_.equalsIgnoreCase("sidebar") ? 1 : p_96537_0_.equalsIgnoreCase("belowName") ? 2 : -1;
	}
}
