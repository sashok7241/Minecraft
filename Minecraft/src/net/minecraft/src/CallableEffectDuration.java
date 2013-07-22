package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectDuration implements Callable
{
	final PotionEffect field_102037_a;
	final EntityLiving field_102036_b;
	
	CallableEffectDuration(EntityLiving p_i22005_1_, PotionEffect p_i22005_2_)
	{
		field_102036_b = p_i22005_1_;
		field_102037_a = p_i22005_2_;
	}
	
	@Override public Object call()
	{
		return func_102035_a();
	}
	
	public String func_102035_a()
	{
		return field_102037_a.getDuration() + "";
	}
}
