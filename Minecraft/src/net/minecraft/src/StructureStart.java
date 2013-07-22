package net.minecraft.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class StructureStart
{
	protected LinkedList components = new LinkedList();
	protected StructureBoundingBox boundingBox;
	
	public void generateStructure(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		Iterator var4 = components.iterator();
		while(var4.hasNext())
		{
			StructureComponent var5 = (StructureComponent) var4.next();
			if(var5.getBoundingBox().intersectsWith(par3StructureBoundingBox) && !var5.addComponentParts(par1World, par2Random, par3StructureBoundingBox))
			{
				var4.remove();
			}
		}
	}
	
	public StructureBoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
	public LinkedList getComponents()
	{
		return components;
	}
	
	public boolean isSizeableStructure()
	{
		return true;
	}
	
	protected void markAvailableHeight(World par1World, Random par2Random, int par3)
	{
		int var4 = 63 - par3;
		int var5 = boundingBox.getYSize() + 1;
		if(var5 < var4)
		{
			var5 += par2Random.nextInt(var4 - var5);
		}
		int var6 = var5 - boundingBox.maxY;
		boundingBox.offset(0, var6, 0);
		Iterator var7 = components.iterator();
		while(var7.hasNext())
		{
			StructureComponent var8 = (StructureComponent) var7.next();
			var8.getBoundingBox().offset(0, var6, 0);
		}
	}
	
	protected void setRandomHeight(World par1World, Random par2Random, int par3, int par4)
	{
		int var5 = par4 - par3 + 1 - boundingBox.getYSize();
		boolean var6 = true;
		int var10;
		if(var5 > 1)
		{
			var10 = par3 + par2Random.nextInt(var5);
		} else
		{
			var10 = par3;
		}
		int var7 = var10 - boundingBox.minY;
		boundingBox.offset(0, var7, 0);
		Iterator var8 = components.iterator();
		while(var8.hasNext())
		{
			StructureComponent var9 = (StructureComponent) var8.next();
			var9.getBoundingBox().offset(0, var7, 0);
		}
	}
	
	protected void updateBoundingBox()
	{
		boundingBox = StructureBoundingBox.getNewBoundingBox();
		Iterator var1 = components.iterator();
		while(var1.hasNext())
		{
			StructureComponent var2 = (StructureComponent) var1.next();
			boundingBox.expandTo(var2.getBoundingBox());
		}
	}
}
