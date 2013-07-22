package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectName implements Callable
{
	final PotionEffect field_102031_a;
	final EntityLiving field_102030_b;
	
	CallableEffectName(EntityLiving par1EntityLiving, PotionEffect par2PotionEffect)
	{
		field_102030_b = par1EntityLiving;
		field_102031_a = par2PotionEffect;
	}
	
	@Override public Object call()
	{
		return func_102029_a();
	}
	
	public String func_102029_a()
	{
		return Potion.potionTypes[field_102031_a.getPotionID()].getName();
	}
}
