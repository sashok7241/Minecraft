package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableEntityType implements Callable
{
	final Entity theEntity;
	
	CallableEntityType(Entity p_i6811_1_)
	{
		theEntity = p_i6811_1_;
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
