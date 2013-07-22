package net.minecraft.src;

public class ChunkCoordinates implements Comparable
{
	public int posX;
	public int posY;
	public int posZ;
	
	public ChunkCoordinates()
	{
	}
	
	public ChunkCoordinates(ChunkCoordinates par1ChunkCoordinates)
	{
		posX = par1ChunkCoordinates.posX;
		posY = par1ChunkCoordinates.posY;
		posZ = par1ChunkCoordinates.posZ;
	}
	
	public ChunkCoordinates(int par1, int par2, int par3)
	{
		posX = par1;
		posY = par2;
		posZ = par3;
	}
	
	public int compareChunkCoordinate(ChunkCoordinates par1ChunkCoordinates)
	{
		return posY == par1ChunkCoordinates.posY ? posZ == par1ChunkCoordinates.posZ ? posX - par1ChunkCoordinates.posX : posZ - par1ChunkCoordinates.posZ : posY - par1ChunkCoordinates.posY;
	}
	
	@Override public int compareTo(Object par1Obj)
	{
		return compareChunkCoordinate((ChunkCoordinates) par1Obj);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof ChunkCoordinates)) return false;
		else
		{
			ChunkCoordinates var2 = (ChunkCoordinates) par1Obj;
			return posX == var2.posX && posY == var2.posY && posZ == var2.posZ;
		}
	}
	
	public float getDistanceSquared(int par1, int par2, int par3)
	{
		float var4 = posX - par1;
		float var5 = posY - par2;
		float var6 = posZ - par3;
		return var4 * var4 + var5 * var5 + var6 * var6;
	}
	
	public float getDistanceSquaredToChunkCoordinates(ChunkCoordinates par1ChunkCoordinates)
	{
		return getDistanceSquared(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
	}
	
	@Override public int hashCode()
	{
		return posX + posZ << 8 + posY << 16;
	}
	
	public void set(int par1, int par2, int par3)
	{
		posX = par1;
		posY = par2;
		posZ = par3;
	}
}
