package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class Vec3Pool
{
	private final int truncateArrayResetThreshold;
	private final int minimumSize;
	private final List vec3Cache = new ArrayList();
	private int nextFreeSpace;
	private int maximumSizeSinceLastTruncation;
	private int resetCount;
	
	public Vec3Pool(int par1, int par2)
	{
		truncateArrayResetThreshold = par1;
		minimumSize = par2;
	}
	
	public void clear()
	{
		if(!func_82589_e())
		{
			if(nextFreeSpace > maximumSizeSinceLastTruncation)
			{
				maximumSizeSinceLastTruncation = nextFreeSpace;
			}
			if(resetCount++ == truncateArrayResetThreshold)
			{
				int var1 = Math.max(maximumSizeSinceLastTruncation, vec3Cache.size() - minimumSize);
				while(vec3Cache.size() > var1)
				{
					vec3Cache.remove(var1);
				}
				maximumSizeSinceLastTruncation = 0;
				resetCount = 0;
			}
			nextFreeSpace = 0;
		}
	}
	
	public void clearAndFreeCache()
	{
		if(!func_82589_e())
		{
			nextFreeSpace = 0;
			vec3Cache.clear();
		}
	}
	
	private boolean func_82589_e()
	{
		return minimumSize < 0 || truncateArrayResetThreshold < 0;
	}
	
	public int func_82590_d()
	{
		return nextFreeSpace;
	}
	
	public int getPoolSize()
	{
		return vec3Cache.size();
	}
	
	public Vec3 getVecFromPool(double par1, double par3, double par5)
	{
		if(func_82589_e()) return new Vec3(this, par1, par3, par5);
		else
		{
			Vec3 var7;
			if(nextFreeSpace >= vec3Cache.size())
			{
				var7 = new Vec3(this, par1, par3, par5);
				vec3Cache.add(var7);
			} else
			{
				var7 = (Vec3) vec3Cache.get(nextFreeSpace);
				var7.setComponents(par1, par3, par5);
			}
			++nextFreeSpace;
			return var7;
		}
	}
}
