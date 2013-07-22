package net.minecraft.src;

public class PathEntity
{
	private final PathPoint[] points;
	private int currentPathIndex;
	private int pathLength;
	
	public PathEntity(PathPoint[] par1ArrayOfPathPoint)
	{
		points = par1ArrayOfPathPoint;
		pathLength = par1ArrayOfPathPoint.length;
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
	
	public PathPoint getPathPointFromIndex(int par1)
	{
		return points[par1];
	}
	
	public Vec3 getPosition(Entity par1Entity)
	{
		return getVectorFromIndex(par1Entity, currentPathIndex);
	}
	
	public Vec3 getVectorFromIndex(Entity par1Entity, int par2)
	{
		double var3 = points[par2].xCoord + (int) (par1Entity.width + 1.0F) * 0.5D;
		double var5 = points[par2].yCoord;
		double var7 = points[par2].zCoord + (int) (par1Entity.width + 1.0F) * 0.5D;
		return par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(var3, var5, var7);
	}
	
	public void incrementPathIndex()
	{
		++currentPathIndex;
	}
	
	public boolean isDestinationSame(Vec3 par1Vec3)
	{
		PathPoint var2 = getFinalPathPoint();
		return var2 == null ? false : var2.xCoord == (int) par1Vec3.xCoord && var2.zCoord == (int) par1Vec3.zCoord;
	}
	
	public boolean isFinished()
	{
		return currentPathIndex >= pathLength;
	}
	
	public boolean isSamePath(PathEntity par1PathEntity)
	{
		if(par1PathEntity == null) return false;
		else if(par1PathEntity.points.length != points.length) return false;
		else
		{
			for(int var2 = 0; var2 < points.length; ++var2)
			{
				if(points[var2].xCoord != par1PathEntity.points[var2].xCoord || points[var2].yCoord != par1PathEntity.points[var2].yCoord || points[var2].zCoord != par1PathEntity.points[var2].zCoord) return false;
			}
			return true;
		}
	}
	
	public void setCurrentPathIndex(int par1)
	{
		currentPathIndex = par1;
	}
	
	public void setCurrentPathLength(int par1)
	{
		pathLength = par1;
	}
}
