package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectIsAmbient implements Callable
{
	final PotionEffect field_102046_a;
	final EntityLiving field_102045_b;
	
	CallableEffectIsAmbient(EntityLiving p_i22008_1_, PotionEffect p_i22008_2_)
	{
		field_102045_b = p_i22008_1_;
		field_102046_a = p_i22008_2_;
	}
	
	@Override public Object call()
	{
		return func_102044_a();
	}
	
	public String func_102044_a()
	{
		return field_102046_a.getIsAmbient() + "";
	}
}
