package net.minecraft.src;

import java.util.Comparator;
import java.util.List;

public class Score
{
	public static final Comparator field_96658_a = new ScoreComparator();
	private final Scoreboard theScoreboard;
	private final ScoreObjective field_96657_c;
	private final String field_96654_d;
	private int field_96655_e;
	
	public Score(Scoreboard p_i10063_1_, ScoreObjective p_i10063_2_, String p_i10063_3_)
	{
		theScoreboard = p_i10063_1_;
		field_96657_c = p_i10063_2_;
		field_96654_d = p_i10063_3_;
	}
	
	public ScoreObjective func_96645_d()
	{
		return field_96657_c;
	}
	
	public void func_96646_b(int p_96646_1_)
	{
		if(field_96657_c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
		else
		{
			func_96647_c(getScorePoints() - p_96646_1_);
		}
	}
	
	public void func_96647_c(int p_96647_1_)
	{
		int var2 = field_96655_e;
		field_96655_e = p_96647_1_;
		if(var2 != p_96647_1_)
		{
			func_96650_f().func_96536_a(this);
		}
	}
	
	public void func_96648_a()
	{
		if(field_96657_c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
		else
		{
			func_96649_a(1);
		}
	}
	
	public void func_96649_a(int p_96649_1_)
	{
		if(field_96657_c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
		else
		{
			func_96647_c(getScorePoints() + p_96649_1_);
		}
	}
	
	public Scoreboard func_96650_f()
	{
		return theScoreboard;
	}
	
	public void func_96651_a(List p_96651_1_)
	{
		func_96647_c(field_96657_c.getCriteria().func_96635_a(p_96651_1_));
	}
	
	public String getPlayerName()
	{
		return field_96654_d;
	}
	
	public int getScorePoints()
	{
		return field_96655_e;
	}
}
