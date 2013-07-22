package net.minecraft.src;

public class PathPoint
{
	public final int xCoord;
	public final int yCoord;
	public final int zCoord;
	private final int hash;
	int index = -1;
	float totalPathDistance;
	float distanceToNext;
	float distanceToTarget;
	PathPoint previous;
	public boolean isFirst = false;
	
	public PathPoint(int p_i3901_1_, int p_i3901_2_, int p_i3901_3_)
	{
		xCoord = p_i3901_1_;
		yCoord = p_i3901_2_;
		zCoord = p_i3901_3_;
		hash = makeHash(p_i3901_1_, p_i3901_2_, p_i3901_3_);
	}
	
	public float distanceTo(PathPoint p_75829_1_)
	{
		float var2 = p_75829_1_.xCoord - xCoord;
		float var3 = p_75829_1_.yCoord - yCoord;
		float var4 = p_75829_1_.zCoord - zCoord;
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof PathPoint)) return false;
		else
		{
			PathPoint var2 = (PathPoint) p_equals_1_;
			return hash == var2.hash && xCoord == var2.xCoord && yCoord == var2.yCoord && zCoord == var2.zCoord;
		}
	}
	
	public float func_75832_b(PathPoint p_75832_1_)
	{
		float var2 = p_75832_1_.xCoord - xCoord;
		float var3 = p_75832_1_.yCoord - yCoord;
		float var4 = p_75832_1_.zCoord - zCoord;
		return var2 * var2 + var3 * var3 + var4 * var4;
	}
	
	@Override public int hashCode()
	{
		return hash;
	}
	
	public boolean isAssigned()
	{
		return index >= 0;
	}
	
	@Override public String toString()
	{
		return xCoord + ", " + yCoord + ", " + zCoord;
	}
	
	public static int makeHash(int p_75830_0_, int p_75830_1_, int p_75830_2_)
	{
		return p_75830_1_ & 255 | (p_75830_0_ & 32767) << 8 | (p_75830_2_ & 32767) << 24 | (p_75830_0_ < 0 ? Integer.MIN_VALUE : 0) | (p_75830_2_ < 0 ? 32768 : 0);
	}
}
