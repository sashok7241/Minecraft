package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectID implements Callable
{
	final PotionEffect field_102034_a;
	final EntityLiving field_102033_b;
	
	CallableEffectID(EntityLiving p_i22004_1_, PotionEffect p_i22004_2_)
	{
		field_102033_b = p_i22004_1_;
		field_102034_a = p_i22004_2_;
	}
	
	@Override public Object call()
	{
		return func_102032_a();
	}
	
	public String func_102032_a()
	{
		return field_102034_a.getPotionID() + "";
	}
}
