package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableStructureType implements Callable
{
	final MapGenStructure theMapStructureGenerator;
	
	CallableStructureType(MapGenStructure p_i6819_1_)
	{
		theMapStructureGenerator = p_i6819_1_;
	}
	
	@Override public Object call()
	{
		return callStructureType();
	}
	
	public String callStructureType()
	{
		return theMapStructureGenerator.getClass().getCanonicalName();
	}
}
