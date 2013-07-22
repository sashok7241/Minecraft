package net.minecraft.src;

public class ChunkPosition
{
	public final int x;
	public final int y;
	public final int z;
	
	public ChunkPosition(int p_i3743_1_, int p_i3743_2_, int p_i3743_3_)
	{
		x = p_i3743_1_;
		y = p_i3743_2_;
		z = p_i3743_3_;
	}
	
	public ChunkPosition(Vec3 p_i3744_1_)
	{
		this(MathHelper.floor_double(p_i3744_1_.xCoord), MathHelper.floor_double(p_i3744_1_.yCoord), MathHelper.floor_double(p_i3744_1_.zCoord));
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof ChunkPosition)) return false;
		else
		{
			ChunkPosition var2 = (ChunkPosition) p_equals_1_;
			return var2.x == x && var2.y == y && var2.z == z;
		}
	}
	
	@Override public int hashCode()
	{
		return x * 8976890 + y * 981131 + z;
	}
}
