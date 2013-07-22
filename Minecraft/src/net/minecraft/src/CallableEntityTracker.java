package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityTracker implements Callable
{
	final int field_96570_a;
	final EntityTracker theEntityTracker;
	
	CallableEntityTracker(EntityTracker par1EntityTracker, int par2)
	{
		theEntityTracker = par1EntityTracker;
		field_96570_a = par2;
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
