package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectIsAmbient implements Callable
{
	final PotionEffect field_102046_a;
	final EntityLiving field_102045_b;
	
	CallableEffectIsAmbient(EntityLiving par1EntityLiving, PotionEffect par2PotionEffect)
	{
		field_102045_b = par1EntityLiving;
		field_102046_a = par2PotionEffect;
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
