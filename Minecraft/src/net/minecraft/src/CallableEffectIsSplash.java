package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectIsSplash implements Callable
{
	final PotionEffect field_102043_a;
	final EntityLiving field_102042_b;
	
	CallableEffectIsSplash(EntityLiving p_i22007_1_, PotionEffect p_i22007_2_)
	{
		field_102042_b = p_i22007_1_;
		field_102043_a = p_i22007_2_;
	}
	
	@Override public Object call()
	{
		return func_102041_a();
	}
	
	public String func_102041_a()
	{
		return field_102043_a.isSplashPotionEffect() + "";
	}
}
