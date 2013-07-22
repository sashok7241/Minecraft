package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
	private final int maxNumCleans;
	private final int numEntriesToRemove;
	private final List listAABB = new ArrayList();
	private int nextPoolIndex = 0;
	private int maxPoolIndex = 0;
	private int numCleans = 0;
	
	public AABBPool(int p_i4030_1_, int p_i4030_2_)
	{
		maxNumCleans = p_i4030_1_;
		numEntriesToRemove = p_i4030_2_;
	}
	
	public void cleanPool()
	{
		if(nextPoolIndex > maxPoolIndex)
		{
			maxPoolIndex = nextPoolIndex;
		}
		if(numCleans++ == maxNumCleans)
		{
			int var1 = Math.max(maxPoolIndex, listAABB.size() - numEntriesToRemove);
			while(listAABB.size() > var1)
			{
				listAABB.remove(var1);
			}
			maxPoolIndex = 0;
			numCleans = 0;
		}
		nextPoolIndex = 0;
	}
	
	public void clearPool()
	{
		nextPoolIndex = 0;
		listAABB.clear();
	}
	
	public AxisAlignedBB getAABB(double p_72299_1_, double p_72299_3_, double p_72299_5_, double p_72299_7_, double p_72299_9_, double p_72299_11_)
	{
		AxisAlignedBB var13;
		if(nextPoolIndex >= listAABB.size())
		{
			var13 = new AxisAlignedBB(p_72299_1_, p_72299_3_, p_72299_5_, p_72299_7_, p_72299_9_, p_72299_11_);
			listAABB.add(var13);
		} else
		{
			var13 = (AxisAlignedBB) listAABB.get(nextPoolIndex);
			var13.setBounds(p_72299_1_, p_72299_3_, p_72299_5_, p_72299_7_, p_72299_9_, p_72299_11_);
		}
		++nextPoolIndex;
		return var13;
	}
	
	public int getlistAABBsize()
	{
		return listAABB.size();
	}
	
	public int getnextPoolIndex()
	{
		return nextPoolIndex;
	}
}
