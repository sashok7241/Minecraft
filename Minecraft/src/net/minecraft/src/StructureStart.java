package net.minecraft.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public abstract class StructureStart
{
	protected LinkedList components = new LinkedList();
	protected StructureBoundingBox boundingBox;
	
	public void generateStructure(World p_75068_1_, Random p_75068_2_, StructureBoundingBox p_75068_3_)
	{
		Iterator var4 = components.iterator();
		while(var4.hasNext())
		{
			StructureComponent var5 = (StructureComponent) var4.next();
			if(var5.getBoundingBox().intersectsWith(p_75068_3_) && !var5.addComponentParts(p_75068_1_, p_75068_2_, p_75068_3_))
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
	
	protected void markAvailableHeight(World p_75067_1_, Random p_75067_2_, int p_75067_3_)
	{
		int var4 = 63 - p_75067_3_;
		int var5 = boundingBox.getYSize() + 1;
		if(var5 < var4)
		{
			var5 += p_75067_2_.nextInt(var4 - var5);
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
	
	protected void setRandomHeight(World p_75070_1_, Random p_75070_2_, int p_75070_3_, int p_75070_4_)
	{
		int var5 = p_75070_4_ - p_75070_3_ + 1 - boundingBox.getYSize();
		boolean var6 = true;
		int var10;
		if(var5 > 1)
		{
			var10 = p_75070_3_ + p_75070_2_.nextInt(var5);
		} else
		{
			var10 = p_75070_3_;
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
