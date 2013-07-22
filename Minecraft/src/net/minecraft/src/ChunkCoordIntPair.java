package net.minecraft.src;

public class ChunkCoordIntPair
{
	public final int chunkXPos;
	public final int chunkZPos;
	
	public ChunkCoordIntPair(int p_i3726_1_, int p_i3726_2_)
	{
		chunkXPos = p_i3726_1_;
		chunkZPos = p_i3726_2_;
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		ChunkCoordIntPair var2 = (ChunkCoordIntPair) p_equals_1_;
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
	
	public ChunkPosition getChunkPosition(int p_77271_1_)
	{
		return new ChunkPosition(getCenterXPos(), p_77271_1_, getCenterZPosition());
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
	
	public static long chunkXZ2Int(int p_77272_0_, int p_77272_1_)
	{
		return p_77272_0_ & 4294967295L | (p_77272_1_ & 4294967295L) << 32;
	}
}
