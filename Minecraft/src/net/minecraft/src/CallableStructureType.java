package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableStructureType implements Callable
{
	final MapGenStructure theMapStructureGenerator;
	
	CallableStructureType(MapGenStructure par1MapGenStructure)
	{
		theMapStructureGenerator = par1MapGenStructure;
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
