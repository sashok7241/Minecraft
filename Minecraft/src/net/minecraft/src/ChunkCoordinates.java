package net.minecraft.src;

public class ChunkCoordinates implements Comparable
{
	public int posX;
	public int posY;
	public int posZ;
	
	public ChunkCoordinates()
	{
	}
	
	public ChunkCoordinates(ChunkCoordinates p_i3252_1_)
	{
		posX = p_i3252_1_.posX;
		posY = p_i3252_1_.posY;
		posZ = p_i3252_1_.posZ;
	}
	
	public ChunkCoordinates(int p_i3251_1_, int p_i3251_2_, int p_i3251_3_)
	{
		posX = p_i3251_1_;
		posY = p_i3251_2_;
		posZ = p_i3251_3_;
	}
	
	public int compareChunkCoordinate(ChunkCoordinates p_71570_1_)
	{
		return posY == p_71570_1_.posY ? posZ == p_71570_1_.posZ ? posX - p_71570_1_.posX : posZ - p_71570_1_.posZ : posY - p_71570_1_.posY;
	}
	
	@Override public int compareTo(Object p_compareTo_1_)
	{
		return compareChunkCoordinate((ChunkCoordinates) p_compareTo_1_);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof ChunkCoordinates)) return false;
		else
		{
			ChunkCoordinates var2 = (ChunkCoordinates) p_equals_1_;
			return posX == var2.posX && posY == var2.posY && posZ == var2.posZ;
		}
	}
	
	public float getDistanceSquared(int p_71569_1_, int p_71569_2_, int p_71569_3_)
	{
		int var4 = posX - p_71569_1_;
		int var5 = posY - p_71569_2_;
		int var6 = posZ - p_71569_3_;
		return var4 * var4 + var5 * var5 + var6 * var6;
	}
	
	public float getDistanceSquaredToChunkCoordinates(ChunkCoordinates p_82371_1_)
	{
		return getDistanceSquared(p_82371_1_.posX, p_82371_1_.posY, p_82371_1_.posZ);
	}
	
	@Override public int hashCode()
	{
		return posX + posZ << 8 + posY << 16;
	}
	
	public void set(int p_71571_1_, int p_71571_2_, int p_71571_3_)
	{
		posX = p_71571_1_;
		posY = p_71571_2_;
		posZ = p_71571_3_;
	}
}
