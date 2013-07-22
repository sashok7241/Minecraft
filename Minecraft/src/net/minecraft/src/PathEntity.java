package net.minecraft.src;

public class PathEntity
{
	private final PathPoint[] points;
	private int currentPathIndex;
	private int pathLength;
	
	public PathEntity(PathPoint[] p_i3902_1_)
	{
		points = p_i3902_1_;
		pathLength = p_i3902_1_.length;
	}
	
	public int getCurrentPathIndex()
	{
		return currentPathIndex;
	}
	
	public int getCurrentPathLength()
	{
		return pathLength;
	}
	
	public PathPoint getFinalPathPoint()
	{
		return pathLength > 0 ? points[pathLength - 1] : null;
	}
	
	public PathPoint getPathPointFromIndex(int p_75877_1_)
	{
		return points[p_75877_1_];
	}
	
	public Vec3 getPosition(Entity p_75878_1_)
	{
		return getVectorFromIndex(p_75878_1_, currentPathIndex);
	}
	
	public Vec3 getVectorFromIndex(Entity p_75881_1_, int p_75881_2_)
	{
		double var3 = points[p_75881_2_].xCoord + (int) (p_75881_1_.width + 1.0F) * 0.5D;
		double var5 = points[p_75881_2_].yCoord;
		double var7 = points[p_75881_2_].zCoord + (int) (p_75881_1_.width + 1.0F) * 0.5D;
		return p_75881_1_.worldObj.getWorldVec3Pool().getVecFromPool(var3, var5, var7);
	}
	
	public void incrementPathIndex()
	{
		++currentPathIndex;
	}
	
	public boolean isDestinationSame(Vec3 p_75880_1_)
	{
		PathPoint var2 = getFinalPathPoint();
		return var2 == null ? false : var2.xCoord == (int) p_75880_1_.xCoord && var2.zCoord == (int) p_75880_1_.zCoord;
	}
	
	public boolean isFinished()
	{
		return currentPathIndex >= pathLength;
	}
	
	public boolean isSamePath(PathEntity p_75876_1_)
	{
		if(p_75876_1_ == null) return false;
		else if(p_75876_1_.points.length != points.length) return false;
		else
		{
			for(int var2 = 0; var2 < points.length; ++var2)
			{
				if(points[var2].xCoord != p_75876_1_.points[var2].xCoord || points[var2].yCoord != p_75876_1_.points[var2].yCoord || points[var2].zCoord != p_75876_1_.points[var2].zCoord) return false;
			}
			return true;
		}
	}
	
	public void setCurrentPathIndex(int p_75872_1_)
	{
		currentPathIndex = p_75872_1_;
	}
	
	public void setCurrentPathLength(int p_75871_1_)
	{
		pathLength = p_75871_1_;
	}
}
