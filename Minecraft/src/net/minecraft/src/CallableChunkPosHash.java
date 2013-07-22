package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableChunkPosHash implements Callable
{
	final int field_85165_a;
	final int field_85163_b;
	final MapGenStructure theMapStructureGenerator;
	
	CallableChunkPosHash(MapGenStructure p_i6818_1_, int p_i6818_2_, int p_i6818_3_)
	{
		theMapStructureGenerator = p_i6818_1_;
		field_85165_a = p_i6818_2_;
		field_85163_b = p_i6818_3_;
	}
	
	@Override public Object call()
	{
		return callChunkPositionHash();
	}
	
	public String callChunkPositionHash()
	{
		return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(field_85165_a, field_85163_b));
	}
}
