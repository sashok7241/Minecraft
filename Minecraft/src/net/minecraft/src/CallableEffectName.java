package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectName implements Callable
{
	final PotionEffect field_102031_a;
	final EntityLiving field_102030_b;
	
	CallableEffectName(EntityLiving p_i22003_1_, PotionEffect p_i22003_2_)
	{
		field_102030_b = p_i22003_1_;
		field_102031_a = p_i22003_2_;
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
