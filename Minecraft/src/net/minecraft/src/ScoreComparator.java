package net.minecraft.src;

import java.util.Comparator;

final class ScoreComparator implements Comparator
{
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return func_96659_a((Score) p_compare_1_, (Score) p_compare_2_);
	}
	
	public int func_96659_a(Score p_96659_1_, Score p_96659_2_)
	{
		return p_96659_1_.getScorePoints() > p_96659_2_.getScorePoints() ? 1 : p_96659_1_.getScorePoints() < p_96659_2_.getScorePoints() ? -1 : 0;
	}
}
