package net.minecraft.src;

public class ChunkPosition
{
	public final int x;
	public final int y;
	public final int z;
	
	public ChunkPosition(int par1, int par2, int par3)
	{
		x = par1;
		y = par2;
		z = par3;
	}
	
	public ChunkPosition(Vec3 par1Vec3)
	{
		this(MathHelper.floor_double(par1Vec3.xCoord), MathHelper.floor_double(par1Vec3.yCoord), MathHelper.floor_double(par1Vec3.zCoord));
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof ChunkPosition)) return false;
		else
		{
			ChunkPosition var2 = (ChunkPosition) par1Obj;
			return var2.x == x && var2.y == y && var2.z == z;
		}
	}
	
	@Override public int hashCode()
	{
		return x * 8976890 + y * 981131 + z;
	}
}
