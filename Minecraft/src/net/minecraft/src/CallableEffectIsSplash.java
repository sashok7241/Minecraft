package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectIsSplash implements Callable
{
	final PotionEffect field_102043_a;
	final EntityLiving field_102042_b;
	
	CallableEffectIsSplash(EntityLiving par1EntityLiving, PotionEffect par2PotionEffect)
	{
		field_102042_b = par1EntityLiving;
		field_102043_a = par2PotionEffect;
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
