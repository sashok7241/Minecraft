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
	
	public PathPoint(int par1, int par2, int par3)
	{
		xCoord = par1;
		yCoord = par2;
		zCoord = par3;
		hash = makeHash(par1, par2, par3);
	}
	
	public float distanceTo(PathPoint par1PathPoint)
	{
		float var2 = par1PathPoint.xCoord - xCoord;
		float var3 = par1PathPoint.yCoord - yCoord;
		float var4 = par1PathPoint.zCoord - zCoord;
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof PathPoint)) return false;
		else
		{
			PathPoint var2 = (PathPoint) par1Obj;
			return hash == var2.hash && xCoord == var2.xCoord && yCoord == var2.yCoord && zCoord == var2.zCoord;
		}
	}
	
	public float func_75832_b(PathPoint par1PathPoint)
	{
		float var2 = par1PathPoint.xCoord - xCoord;
		float var3 = par1PathPoint.yCoord - yCoord;
		float var4 = par1PathPoint.zCoord - zCoord;
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
	
	public static int makeHash(int par0, int par1, int par2)
	{
		return par1 & 255 | (par0 & 32767) << 8 | (par2 & 32767) << 24 | (par0 < 0 ? Integer.MIN_VALUE : 0) | (par2 < 0 ? 32768 : 0);
	}
}
