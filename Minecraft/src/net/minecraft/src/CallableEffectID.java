package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectID implements Callable
{
	final PotionEffect field_102034_a;
	final EntityLiving field_102033_b;
	
	CallableEffectID(EntityLiving par1EntityLiving, PotionEffect par2PotionEffect)
	{
		field_102033_b = par1EntityLiving;
		field_102034_a = par2PotionEffect;
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
