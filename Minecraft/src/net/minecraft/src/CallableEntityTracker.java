package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityTracker implements Callable
{
	final int field_96570_a;
	final EntityTracker theEntityTracker;
	
	CallableEntityTracker(EntityTracker p_i10045_1_, int p_i10045_2_)
	{
		theEntityTracker = p_i10045_1_;
		field_96570_a = p_i10045_2_;
	}
	
	@Override public Object call()
	{
		return func_96568_a();
	}
	
	public String func_96568_a()
	{
		String var1 = "Once per " + field_96570_a + " ticks";
		if(field_96570_a == Integer.MAX_VALUE)
		{
			var1 = "Maximum (" + var1 + ")";
		}
		return var1;
	}
}
