package net.minecraft.src;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ScorePlayerTeam
{
	private final Scoreboard theScoreboard;
	private final String field_96675_b;
	private final Set membershipSet = new HashSet();
	private String field_96673_d;
	private String field_96674_e = "";
	private String field_96671_f = "";
	private boolean field_96672_g = true;
	private boolean field_98301_h = true;
	
	public ScorePlayerTeam(Scoreboard p_i10062_1_, String p_i10062_2_)
	{
		theScoreboard = p_i10062_1_;
		field_96675_b = p_i10062_2_;
		field_96673_d = p_i10062_2_;
	}
	
	public void func_96660_a(boolean p_96660_1_)
	{
		field_96672_g = p_96660_1_;
		theScoreboard.func_96538_b(this);
	}
	
	public String func_96661_b()
	{
		return field_96675_b;
	}
	
	public void func_96662_c(String p_96662_1_)
	{
		if(p_96662_1_ == null) throw new IllegalArgumentException("Suffix cannot be null");
		else
		{
			field_96671_f = p_96662_1_;
			theScoreboard.func_96538_b(this);
		}
	}
	
	public void func_96664_a(String p_96664_1_)
	{
		if(p_96664_1_ == null) throw new IllegalArgumentException("Name cannot be null");
		else
		{
			field_96673_d = p_96664_1_;
			theScoreboard.func_96538_b(this);
		}
	}
	
	public boolean func_96665_g()
	{
		return field_96672_g;
	}
	
	public void func_96666_b(String p_96666_1_)
	{
		if(p_96666_1_ == null) throw new IllegalArgumentException("Prefix cannot be null");
		else
		{
			field_96674_e = p_96666_1_;
			theScoreboard.func_96538_b(this);
		}
	}
	
	public String func_96669_c()
	{
		return field_96673_d;
	}
	
	public boolean func_98297_h()
	{
		return field_98301_h;
	}
	
	public void func_98298_a(int par1)
	{
		byte var2 = 0;
		int var4 = var2 + 1;
		func_96660_a((par1 & 1 << var2) > 0);
		func_98300_b((par1 & 1 << var4++) > 0);
	}
	
	public int func_98299_i()
	{
		int var1 = 0;
		int var2 = 0;
		if(func_96665_g())
		{
			var1 |= 1 << var2++;
		}
		if(func_98297_h())
		{
			var1 |= 1 << var2++;
		}
		return var1;
	}
	
	public void func_98300_b(boolean p_98300_1_)
	{
		field_98301_h = p_98300_1_;
		theScoreboard.func_96538_b(this);
	}
	
	public String getColorPrefix()
	{
		return field_96674_e;
	}
	
	public String getColorSuffix()
	{
		return field_96671_f;
	}
	
	public Collection getMembershipCollection()
	{
		return membershipSet;
	}
	
	public static String formatPlayerName(ScorePlayerTeam p_96667_0_, String p_96667_1_)
	{
		return p_96667_0_ == null ? p_96667_1_ : p_96667_0_.getColorPrefix() + p_96667_1_ + p_96667_0_.getColorSuffix();
	}
}
