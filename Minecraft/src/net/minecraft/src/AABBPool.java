package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class AABBPool
{
	private final int maxNumCleans;
	private final int numEntriesToRemove;
	private final List listAABB = new ArrayList();
	private int nextPoolIndex;
	private int maxPoolIndex;
	private int numCleans;
	
	public AABBPool(int par1, int par2)
	{
		maxNumCleans = par1;
		numEntriesToRemove = par2;
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
	
	public AxisAlignedBB getAABB(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		AxisAlignedBB var13;
		if(nextPoolIndex >= listAABB.size())
		{
			var13 = new AxisAlignedBB(par1, par3, par5, par7, par9, par11);
			listAABB.add(var13);
		} else
		{
			var13 = (AxisAlignedBB) listAABB.get(nextPoolIndex);
			var13.setBounds(par1, par3, par5, par7, par9, par11);
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
