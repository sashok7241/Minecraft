package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageWoodHut extends ComponentVillage
{
	private int averageGroundLevel = -1;
	private final boolean isTallHouse;
	private final int tablePosition;
	
	public ComponentVillageWoodHut(ComponentVillageStartPiece p_i3867_1_, int p_i3867_2_, Random p_i3867_3_, StructureBoundingBox p_i3867_4_, int p_i3867_5_)
	{
		super(p_i3867_1_, p_i3867_2_);
		coordBaseMode = p_i3867_5_;
		boundingBox = p_i3867_4_;
		isTallHouse = p_i3867_3_.nextBoolean();
		tablePosition = p_i3867_3_.nextInt(3);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 6 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 5, 4, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 3, 0, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 2, 0, 3, Block.dirt.blockID, Block.dirt.blockID, false);
		if(isTallHouse)
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 1, 2, 4, 3, Block.wood.blockID, Block.wood.blockID, false);
		} else
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 2, 5, 3, Block.wood.blockID, Block.wood.blockID, false);
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 1, 4, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 2, 4, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 1, 4, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 2, 4, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 4, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 4, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 4, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 3, 4, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 3, 4, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 3, 4, 3, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 4, 3, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 1, 3, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 4, 2, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 3, 2, 2, p_74875_3_);
		if(tablePosition > 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, tablePosition, 1, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.pressurePlatePlanks.blockID, 0, tablePosition, 2, 3, p_74875_3_);
		}
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 2, 0, p_74875_3_);
		placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		if(getBlockIdAtCurrentPosition(p_74875_1_, 1, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, 1, -1, -1, p_74875_3_) != 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 0, -1, p_74875_3_);
		}
		for(int var4 = 0; var4 < 5; ++var4)
		{
			for(int var5 = 0; var5 < 4; ++var5)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 6, var4, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var5, -1, var4, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);
		return true;
	}
	
	public static ComponentVillageWoodHut func_74908_a(ComponentVillageStartPiece p_74908_0_, List p_74908_1_, Random p_74908_2_, int p_74908_3_, int p_74908_4_, int p_74908_5_, int p_74908_6_, int p_74908_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74908_3_, p_74908_4_, p_74908_5_, 0, 0, 0, 4, 6, 5, p_74908_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74908_1_, var8) == null ? new ComponentVillageWoodHut(p_74908_0_, p_74908_7_, p_74908_2_, var8, p_74908_6_) : null;
	}
}
