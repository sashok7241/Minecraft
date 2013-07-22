package net.minecraft.src;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.server.MinecraftServer;

public class ScoreboardSaveData extends WorldSavedData
{
	private Scoreboard field_96507_a;
	private NBTTagCompound field_96506_b;
	
	public ScoreboardSaveData()
	{
		this("scoreboard");
	}
	
	public ScoreboardSaveData(String p_i10064_1_)
	{
		super(p_i10064_1_);
	}
	
	protected NBTTagList func_96496_a()
	{
		NBTTagList var1 = new NBTTagList();
		Collection var2 = field_96507_a.func_96525_g();
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			ScorePlayerTeam var4 = (ScorePlayerTeam) var3.next();
			NBTTagCompound var5 = new NBTTagCompound();
			var5.setString("Name", var4.func_96661_b());
			var5.setString("DisplayName", var4.func_96669_c());
			var5.setString("Prefix", var4.getColorPrefix());
			var5.setString("Suffix", var4.getColorSuffix());
			var5.setBoolean("AllowFriendlyFire", var4.func_96665_g());
			var5.setBoolean("SeeFriendlyInvisibles", var4.func_98297_h());
			NBTTagList var6 = new NBTTagList();
			Iterator var7 = var4.getMembershipCollection().iterator();
			while(var7.hasNext())
			{
				String var8 = (String) var7.next();
				var6.appendTag(new NBTTagString("", var8));
			}
			var5.setTag("Players", var6);
			var1.appendTag(var5);
		}
		return var1;
	}
	
	protected void func_96497_d(NBTTagCompound p_96497_1_)
	{
		NBTTagCompound var2 = new NBTTagCompound();
		boolean var3 = false;
		for(int var4 = 0; var4 < 3; ++var4)
		{
			ScoreObjective var5 = field_96507_a.func_96539_a(var4);
			if(var5 != null)
			{
				var2.setString("slot_" + var4, var5.getName());
				var3 = true;
			}
		}
		if(var3)
		{
			p_96497_1_.setCompoundTag("DisplaySlots", var2);
		}
	}
	
	protected void func_96498_a(NBTTagList p_96498_1_)
	{
		for(int var2 = 0; var2 < p_96498_1_.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) p_96498_1_.tagAt(var2);
			ScorePlayerTeam var4 = field_96507_a.func_96527_f(var3.getString("Name"));
			var4.func_96664_a(var3.getString("DisplayName"));
			var4.func_96666_b(var3.getString("Prefix"));
			var4.func_96662_c(var3.getString("Suffix"));
			if(var3.hasKey("AllowFriendlyFire"))
			{
				var4.func_96660_a(var3.getBoolean("AllowFriendlyFire"));
			}
			if(var3.hasKey("SeeFriendlyInvisibles"))
			{
				var4.func_98300_b(var3.getBoolean("SeeFriendlyInvisibles"));
			}
			func_96502_a(var4, var3.getTagList("Players"));
		}
	}
	
	public void func_96499_a(Scoreboard p_96499_1_)
	{
		field_96507_a = p_96499_1_;
		if(field_96506_b != null)
		{
			readFromNBT(field_96506_b);
		}
	}
	
	protected void func_96500_c(NBTTagList p_96500_1_)
	{
		for(int var2 = 0; var2 < p_96500_1_.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) p_96500_1_.tagAt(var2);
			ScoreObjective var4 = field_96507_a.getObjective(var3.getString("Objective"));
			Score var5 = field_96507_a.func_96529_a(var3.getString("Name"), var4);
			var5.func_96647_c(var3.getInteger("Score"));
		}
	}
	
	protected void func_96501_b(NBTTagList p_96501_1_)
	{
		for(int var2 = 0; var2 < p_96501_1_.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) p_96501_1_.tagAt(var2);
			ScoreObjectiveCriteria var4 = (ScoreObjectiveCriteria) ScoreObjectiveCriteria.field_96643_a.get(var3.getString("CriteriaName"));
			ScoreObjective var5 = field_96507_a.func_96535_a(var3.getString("Name"), var4);
			var5.setDisplayName(var3.getString("DisplayName"));
		}
	}
	
	protected void func_96502_a(ScorePlayerTeam p_96502_1_, NBTTagList p_96502_2_)
	{
		for(int var3 = 0; var3 < p_96502_2_.tagCount(); ++var3)
		{
			field_96507_a.func_96521_a(((NBTTagString) p_96502_2_.tagAt(var3)).data, p_96502_1_);
		}
	}
	
	protected NBTTagList func_96503_e()
	{
		NBTTagList var1 = new NBTTagList();
		Collection var2 = field_96507_a.func_96528_e();
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			Score var4 = (Score) var3.next();
			NBTTagCompound var5 = new NBTTagCompound();
			var5.setString("Name", var4.getPlayerName());
			var5.setString("Objective", var4.func_96645_d().getName());
			var5.setInteger("Score", var4.getScorePoints());
			var1.appendTag(var5);
		}
		return var1;
	}
	
	protected void func_96504_c(NBTTagCompound p_96504_1_)
	{
		for(int var2 = 0; var2 < 3; ++var2)
		{
			if(p_96504_1_.hasKey("slot_" + var2))
			{
				String var3 = p_96504_1_.getString("slot_" + var2);
				ScoreObjective var4 = field_96507_a.getObjective(var3);
				field_96507_a.func_96530_a(var2, var4);
			}
		}
	}
	
	protected NBTTagList func_96505_b()
	{
		NBTTagList var1 = new NBTTagList();
		Collection var2 = field_96507_a.getScoreObjectives();
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			ScoreObjective var4 = (ScoreObjective) var3.next();
			NBTTagCompound var5 = new NBTTagCompound();
			var5.setString("Name", var4.getName());
			var5.setString("CriteriaName", var4.getCriteria().func_96636_a());
			var5.setString("DisplayName", var4.getDisplayName());
			var1.appendTag(var5);
		}
		return var1;
	}
	
	@Override public void readFromNBT(NBTTagCompound p_76184_1_)
	{
		if(field_96507_a == null)
		{
			field_96506_b = p_76184_1_;
		} else
		{
			func_96501_b(p_76184_1_.getTagList("Objectives"));
			func_96500_c(p_76184_1_.getTagList("PlayerScores"));
			if(p_76184_1_.hasKey("DisplaySlots"))
			{
				func_96504_c(p_76184_1_.getCompoundTag("DisplaySlots"));
			}
			if(p_76184_1_.hasKey("Teams"))
			{
				func_96498_a(p_76184_1_.getTagList("Teams"));
			}
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound p_76187_1_)
	{
		if(field_96507_a == null)
		{
			MinecraftServer.getServer().getLogAgent().logWarning("Tried to save scoreboard without having a scoreboard...");
		} else
		{
			p_76187_1_.setTag("Objectives", func_96505_b());
			p_76187_1_.setTag("PlayerScores", func_96503_e());
			p_76187_1_.setTag("Teams", func_96496_a());
			func_96497_d(p_76187_1_);
		}
	}
}
