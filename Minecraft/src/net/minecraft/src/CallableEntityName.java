package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityName implements Callable
{
	final Entity theEntity;
	
	CallableEntityName(Entity p_i10048_1_)
	{
		theEntity = p_i10048_1_;
	}
	
	@Override public Object call()
	{
		return callEntityName();
	}
	
	public String callEntityName()
	{
		return theEntity.getEntityName();
	}
}
