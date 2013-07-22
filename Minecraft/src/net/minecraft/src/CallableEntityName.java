package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityName implements Callable
{
	final Entity theEntity;
	
	CallableEntityName(Entity par1Entity)
	{
		theEntity = par1Entity;
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
