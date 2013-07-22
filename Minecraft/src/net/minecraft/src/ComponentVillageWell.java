package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageWell extends ComponentVillage
{
	private final boolean field_74924_a = true;
	private int averageGroundLevel = -1;
	
	public ComponentVillageWell(ComponentVillageStartPiece p_i3875_1_, int p_i3875_2_, Random p_i3875_3_, int p_i3875_4_, int p_i3875_5_)
	{
		super(p_i3875_1_, p_i3875_2_);
		coordBaseMode = p_i3875_3_.nextInt(4);
		switch(coordBaseMode)
		{
			case 0:
			case 2:
				boundingBox = new StructureBoundingBox(p_i3875_4_, 64, p_i3875_5_, p_i3875_4_ + 6 - 1, 78, p_i3875_5_ + 6 - 1);
				break;
			default:
				boundingBox = new StructureBoundingBox(p_i3875_4_, 64, p_i3875_5_, p_i3875_4_ + 6 - 1, 78, p_i3875_5_ + 6 - 1);
		}
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 3, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 4, 12, 4, Block.cobblestone.blockID, Block.waterMoving.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 12, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 3, 12, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 12, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 3, 12, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 13, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 14, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 13, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 14, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 13, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 14, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 13, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 14, 4, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 15, 1, 4, 15, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		for(int var4 = 0; var4 <= 5; ++var4)
		{
			for(int var5 = 0; var5 <= 5; ++var5)
			{
				if(var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5)
				{
					placeBlockAtCurrentPosition(p_74875_1_, Block.gravel.blockID, 0, var5, 11, var4, p_74875_3_);
					clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 12, var4, p_74875_3_);
				}
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.maxY - 4, boundingBox.minZ + 1, 1, getComponentType());
		StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.maxY - 4, boundingBox.minZ + 1, 3, getComponentType());
		StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.maxY - 4, boundingBox.minZ - 1, 2, getComponentType());
		StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX + 1, boundingBox.maxY - 4, boundingBox.maxZ + 1, 0, getComponentType());
	}
}
