package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentMineshaftCross extends StructureComponent
{
	private final int corridorDirection;
	private final boolean isMultipleFloors;
	
	public ComponentMineshaftCross(int p_i3808_1_, Random p_i3808_2_, StructureBoundingBox p_i3808_3_, int p_i3808_4_)
	{
		super(p_i3808_1_);
		corridorDirection = p_i3808_4_;
		boundingBox = p_i3808_3_;
		isMultipleFloors = p_i3808_3_.getYSize() > 3;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			if(isMultipleFloors)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.minY + 3 - 1, boundingBox.maxZ, 0, 0, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.minY + 3 - 1, boundingBox.maxZ - 1, 0, 0, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, 0, 0, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, 0, 0, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1, 0, 0, false);
			} else
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, 0, 0, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, 0, 0, false);
			}
			fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, Block.planks.blockID, 0, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, Block.planks.blockID, 0, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, Block.planks.blockID, 0, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, Block.planks.blockID, 0, false);
			for(int var4 = boundingBox.minX; var4 <= boundingBox.maxX; ++var4)
			{
				for(int var5 = boundingBox.minZ; var5 <= boundingBox.maxZ; ++var5)
				{
					int var6 = getBlockIdAtCurrentPosition(p_74875_1_, var4, boundingBox.minY - 1, var5, p_74875_3_);
					if(var6 == 0)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, var4, boundingBox.minY - 1, var5, p_74875_3_);
					}
				}
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		int var4 = getComponentType();
		switch(corridorDirection)
		{
			case 0:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, var4);
				break;
			case 1:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, var4);
				break;
			case 2:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, var4);
				break;
			case 3:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, var4);
		}
		if(isMultipleFloors)
		{
			if(p_74861_3_.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ - 1, 2, var4);
			}
			if(p_74861_3_.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 1, var4);
			}
			if(p_74861_3_.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 3, var4);
			}
			if(p_74861_3_.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, 0, var4);
			}
		}
	}
	
	public static StructureBoundingBox findValidPlacement(List p_74951_0_, Random p_74951_1_, int p_74951_2_, int p_74951_3_, int p_74951_4_, int p_74951_5_)
	{
		StructureBoundingBox var6 = new StructureBoundingBox(p_74951_2_, p_74951_3_, p_74951_4_, p_74951_2_, p_74951_3_ + 2, p_74951_4_);
		if(p_74951_1_.nextInt(4) == 0)
		{
			var6.maxY += 4;
		}
		switch(p_74951_5_)
		{
			case 0:
				var6.minX = p_74951_2_ - 1;
				var6.maxX = p_74951_2_ + 3;
				var6.maxZ = p_74951_4_ + 4;
				break;
			case 1:
				var6.minX = p_74951_2_ - 4;
				var6.minZ = p_74951_4_ - 1;
				var6.maxZ = p_74951_4_ + 3;
				break;
			case 2:
				var6.minX = p_74951_2_ - 1;
				var6.maxX = p_74951_2_ + 3;
				var6.minZ = p_74951_4_ - 4;
				break;
			case 3:
				var6.maxX = p_74951_2_ + 4;
				var6.minZ = p_74951_4_ - 1;
				var6.maxZ = p_74951_4_ + 3;
		}
		return StructureComponent.findIntersecting(p_74951_0_, var6) != null ? null : var6;
	}
}
