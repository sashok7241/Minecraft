package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectAmplifier implements Callable
{
	final PotionEffect field_102040_a;
	final EntityLiving field_102039_b;
	
	CallableEffectAmplifier(EntityLiving p_i22006_1_, PotionEffect p_i22006_2_)
	{
		field_102039_b = p_i22006_1_;
		field_102040_a = p_i22006_2_;
	}
	
	@Override public Object call()
	{
		return func_102038_a();
	}
	
	public String func_102038_a()
	{
		return field_102040_a.getAmplifier() + "";
	}
}
