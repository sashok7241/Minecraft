package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableIsFeatureChunk implements Callable
{
	final int field_85169_a;
	final int field_85167_b;
	final MapGenStructure theMapStructureGenerator;
	
	CallableIsFeatureChunk(MapGenStructure par1MapGenStructure, int par2, int par3)
	{
		theMapStructureGenerator = par1MapGenStructure;
		field_85169_a = par2;
		field_85167_b = par3;
	}
	
	@Override public Object call()
	{
		return func_85166_a();
	}
	
	public String func_85166_a()
	{
		return theMapStructureGenerator.canSpawnStructureAtCoords(field_85169_a, field_85167_b) ? "True" : "False";
	}
}
