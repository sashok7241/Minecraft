package net.minecraft.src;

import java.util.Comparator;

final class ScoreComparator implements Comparator
{
	@Override public int compare(Object par1Obj, Object par2Obj)
	{
		return func_96659_a((Score) par1Obj, (Score) par2Obj);
	}
	
	public int func_96659_a(Score par1Score, Score par2Score)
	{
		return par1Score.getScorePoints() > par2Score.getScorePoints() ? 1 : par1Score.getScorePoints() < par2Score.getScorePoints() ? -1 : 0;
	}
}
