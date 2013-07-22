package net.minecraft.src;

import java.util.List;

public class ScoreDummyCriteria implements ScoreObjectiveCriteria
{
	private final String field_96644_g;
	
	public ScoreDummyCriteria(String p_i10065_1_)
	{
		field_96644_g = p_i10065_1_;
		ScoreObjectiveCriteria.field_96643_a.put(p_i10065_1_, this);
	}
	
	@Override public int func_96635_a(List p_96635_1_)
	{
		return 0;
	}
	
	@Override public String func_96636_a()
	{
		return field_96644_g;
	}
	
	@Override public boolean isReadOnly()
	{
		return false;
	}
}
