package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEffectDuration implements Callable
{
	final PotionEffect field_102037_a;
	final EntityLiving field_102036_b;
	
	CallableEffectDuration(EntityLiving par1EntityLiving, PotionEffect par2PotionEffect)
	{
		field_102036_b = par1EntityLiving;
		field_102037_a = par2PotionEffect;
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
