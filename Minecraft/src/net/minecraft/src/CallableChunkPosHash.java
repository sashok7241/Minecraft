package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableChunkPosHash implements Callable
{
	final int field_85165_a;
	final int field_85163_b;
	final MapGenStructure theMapStructureGenerator;
	
	CallableChunkPosHash(MapGenStructure par1MapGenStructure, int par2, int par3)
	{
		theMapStructureGenerator = par1MapGenStructure;
		field_85165_a = par2;
		field_85163_b = par3;
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
