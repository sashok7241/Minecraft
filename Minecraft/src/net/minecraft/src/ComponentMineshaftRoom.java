package net.minecraft.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ComponentMineshaftRoom extends StructureComponent
{
	private List roomsLinkedToTheRoom = new LinkedList();
	
	public ComponentMineshaftRoom(int p_i3809_1_, Random p_i3809_2_, int p_i3809_3_, int p_i3809_4_)
	{
		super(p_i3809_1_);
		boundingBox = new StructureBoundingBox(p_i3809_3_, 50, p_i3809_4_, p_i3809_3_ + 7 + p_i3809_2_.nextInt(6), 54 + p_i3809_2_.nextInt(6), p_i3809_4_ + 7 + p_i3809_2_.nextInt(6));
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, Block.dirt.blockID, 0, true);
			fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ, boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ, 0, 0, false);
			Iterator var4 = roomsLinkedToTheRoom.iterator();
			while(var4.hasNext())
			{
				StructureBoundingBox var5 = (StructureBoundingBox) var4.next();
				fillWithBlocks(p_74875_1_, p_74875_3_, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, 0, 0, false);
			}
			randomlyRareFillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 0, false);
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		int var4 = getComponentType();
		int var6 = boundingBox.getYSize() - 3 - 1;
		if(var6 <= 0)
		{
			var6 = 1;
		}
		int var5;
		StructureComponent var7;
		StructureBoundingBox var8;
		for(var5 = 0; var5 < boundingBox.getXSize(); var5 += 4)
		{
			var5 += p_74861_3_.nextInt(boundingBox.getXSize());
			if(var5 + 3 > boundingBox.getXSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + var5, boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.minZ - 1, 2, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, boundingBox.minZ, var8.maxX, var8.maxY, boundingBox.minZ + 1));
			}
		}
		for(var5 = 0; var5 < boundingBox.getXSize(); var5 += 4)
		{
			var5 += p_74861_3_.nextInt(boundingBox.getXSize());
			if(var5 + 3 > boundingBox.getXSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + var5, boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.maxZ + 1, 0, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, boundingBox.maxZ - 1, var8.maxX, var8.maxY, boundingBox.maxZ));
			}
		}
		for(var5 = 0; var5 < boundingBox.getZSize(); var5 += 4)
		{
			var5 += p_74861_3_.nextInt(boundingBox.getZSize());
			if(var5 + 3 > boundingBox.getZSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.minZ + var5, 1, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, var8.minY, var8.minZ, boundingBox.minX + 1, var8.maxY, var8.maxZ));
			}
		}
		for(var5 = 0; var5 < boundingBox.getZSize(); var5 += 4)
		{
			var5 += p_74861_3_.nextInt(boundingBox.getZSize());
			if(var5 + 3 > boundingBox.getZSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY + p_74861_3_.nextInt(var6) + 1, boundingBox.minZ + var5, 3, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.maxX - 1, var8.minY, var8.minZ, boundingBox.maxX, var8.maxY, var8.maxZ));
			}
		}
	}
}
