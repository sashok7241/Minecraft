package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableIsFeatureChunk implements Callable
{
	final int field_85169_a;
	final int field_85167_b;
	final MapGenStructure theMapStructureGenerator;
	
	CallableIsFeatureChunk(MapGenStructure p_i6817_1_, int p_i6817_2_, int p_i6817_3_)
	{
		theMapStructureGenerator = p_i6817_1_;
		field_85169_a = p_i6817_2_;
		field_85167_b = p_i6817_3_;
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
