package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class ScoreHealthCriteria extends ScoreDummyCriteria
{
	public ScoreHealthCriteria(String p_i10066_1_)
	{
		super(p_i10066_1_);
	}
	
	@Override public int func_96635_a(List p_96635_1_)
	{
		float var2 = 0.0F;
		int var5;
		float var6;
		for(Iterator var3 = p_96635_1_.iterator(); var3.hasNext(); var2 += var5 / var6)
		{
			EntityPlayer var4 = (EntityPlayer) var3.next();
			var5 = var4.getHealth();
			var6 = var4.getMaxHealth();
			if(var5 < 0)
			{
				var5 = 0;
			}
			if(var5 > var6)
			{
				var5 = var4.getMaxHealth();
			}
		}
		if(p_96635_1_.size() > 0)
		{
			var2 /= p_96635_1_.size();
		}
		return MathHelper.floor_float(var2 * 19.0F) + (var2 > 0.0F ? 1 : 0);
	}
	
	@Override public boolean isReadOnly()
	{
		return true;
	}
}
