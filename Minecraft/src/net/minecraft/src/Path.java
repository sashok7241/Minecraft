package net.minecraft.src;

public class Path
{
	private PathPoint[] pathPoints = new PathPoint[1024];
	private int count;
	
	public PathPoint addPoint(PathPoint par1PathPoint)
	{
		if(par1PathPoint.index >= 0) throw new IllegalStateException("OW KNOWS!");
		else
		{
			if(count == pathPoints.length)
			{
				PathPoint[] var2 = new PathPoint[count << 1];
				System.arraycopy(pathPoints, 0, var2, 0, count);
				pathPoints = var2;
			}
			pathPoints[count] = par1PathPoint;
			par1PathPoint.index = count;
			sortBack(count++);
			return par1PathPoint;
		}
	}
	
	public void changeDistance(PathPoint par1PathPoint, float par2)
	{
		float var3 = par1PathPoint.distanceToTarget;
		par1PathPoint.distanceToTarget = par2;
		if(par2 < var3)
		{
			sortBack(par1PathPoint.index);
		} else
		{
			sortForward(par1PathPoint.index);
		}
	}
	
	public void clearPath()
	{
		count = 0;
	}
	
	public PathPoint dequeue()
	{
		PathPoint var1 = pathPoints[0];
		pathPoints[0] = pathPoints[--count];
		pathPoints[count] = null;
		if(count > 0)
		{
			sortForward(0);
		}
		var1.index = -1;
		return var1;
	}
	
	public boolean isPathEmpty()
	{
		return count == 0;
	}
	
	private void sortBack(int par1)
	{
		PathPoint var2 = pathPoints[par1];
		int var4;
		for(float var3 = var2.distanceToTarget; par1 > 0; par1 = var4)
		{
			var4 = par1 - 1 >> 1;
			PathPoint var5 = pathPoints[var4];
			if(var3 >= var5.distanceToTarget)
			{
				break;
			}
			pathPoints[par1] = var5;
			var5.index = par1;
		}
		pathPoints[par1] = var2;
		var2.index = par1;
	}
	
	private void sortForward(int par1)
	{
		PathPoint var2 = pathPoints[par1];
		float var3 = var2.distanceToTarget;
		while(true)
		{
			int var4 = 1 + (par1 << 1);
			int var5 = var4 + 1;
			if(var4 >= count)
			{
				break;
			}
			PathPoint var6 = pathPoints[var4];
			float var7 = var6.distanceToTarget;
			PathPoint var8;
			float var9;
			if(var5 >= count)
			{
				var8 = null;
				var9 = Float.POSITIVE_INFINITY;
			} else
			{
				var8 = pathPoints[var5];
				var9 = var8.distanceToTarget;
			}
			if(var7 < var9)
			{
				if(var7 >= var3)
				{
					break;
				}
				pathPoints[par1] = var6;
				var6.index = par1;
				par1 = var4;
			} else
			{
				if(var9 >= var3)
				{
					break;
				}
				pathPoints[par1] = var8;
				var8.index = par1;
				par1 = var5;
			}
		}
		pathPoints[par1] = var2;
		var2.index = par1;
	}
}
