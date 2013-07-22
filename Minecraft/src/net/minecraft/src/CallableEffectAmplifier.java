package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectAmplifier implements Callable
{
	final PotionEffect field_102040_a;
	final EntityLiving field_102039_b;
	
	CallableEffectAmplifier(EntityLiving par1EntityLiving, PotionEffect par2PotionEffect)
	{
		field_102039_b = par1EntityLiving;
		field_102040_a = par2PotionEffect;
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
