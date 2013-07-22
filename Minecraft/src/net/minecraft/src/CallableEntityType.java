package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityType implements Callable
{
	final Entity theEntity;
	
	CallableEntityType(Entity par1Entity)
	{
		theEntity = par1Entity;
	}
	
	@Override public Object call()
	{
		return callEntityType();
	}
	
	public String callEntityType()
	{
		return EntityList.getEntityString(theEntity) + " (" + theEntity.getClass().getCanonicalName() + ")";
	}
}
