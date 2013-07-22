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
	
	public Score(Scoreboard par1Scoreboard, ScoreObjective par2ScoreObjective, String par3Str)
	{
		theScoreboard = par1Scoreboard;
		field_96657_c = par2ScoreObjective;
		field_96654_d = par3Str;
	}
	
	public ScoreObjective func_96645_d()
	{
		return field_96657_c;
	}
	
	public void func_96646_b(int par1)
	{
		if(field_96657_c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
		else
		{
			func_96647_c(getScorePoints() - par1);
		}
	}
	
	public void func_96647_c(int par1)
	{
		int var2 = field_96655_e;
		field_96655_e = par1;
		if(var2 != par1)
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
	
	public void func_96649_a(int par1)
	{
		if(field_96657_c.getCriteria().isReadOnly()) throw new IllegalStateException("Cannot modify read-only score");
		else
		{
			func_96647_c(getScorePoints() + par1);
		}
	}
	
	public Scoreboard func_96650_f()
	{
		return theScoreboard;
	}
	
	public void func_96651_a(List par1List)
	{
		func_96647_c(field_96657_c.getCriteria().func_96635_a(par1List));
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
