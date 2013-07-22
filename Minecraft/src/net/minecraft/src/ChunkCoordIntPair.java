package net.minecraft.src;

public class ChunkCoordIntPair
{
	public final int chunkXPos;
	public final int chunkZPos;
	
	public ChunkCoordIntPair(int par1, int par2)
	{
		chunkXPos = par1;
		chunkZPos = par2;
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		ChunkCoordIntPair var2 = (ChunkCoordIntPair) par1Obj;
		return var2.chunkXPos == chunkXPos && var2.chunkZPos == chunkZPos;
	}
	
	public int getCenterXPos()
	{
		return (chunkXPos << 4) + 8;
	}
	
	public int getCenterZPosition()
	{
		return (chunkZPos << 4) + 8;
	}
	
	public ChunkPosition getChunkPosition(int par1)
	{
		return new ChunkPosition(getCenterXPos(), par1, getCenterZPosition());
	}
	
	@Override public int hashCode()
	{
		long var1 = chunkXZ2Int(chunkXPos, chunkZPos);
		int var3 = (int) var1;
		int var4 = (int) (var1 >> 32);
		return var3 ^ var4;
	}
	
	@Override public String toString()
	{
		return "[" + chunkXPos + ", " + chunkZPos + "]";
	}
	
	public static long chunkXZ2Int(int par0, int par1)
	{
		return par0 & 4294967295L | (par1 & 4294967295L) << 32;
	}
}
