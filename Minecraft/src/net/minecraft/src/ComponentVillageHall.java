package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHall extends ComponentVillage
{
	private int averageGroundLevel = -1;
	
	public ComponentVillageHall(ComponentVillageStartPiece p_i3865_1_, int p_i3865_2_, Random p_i3865_3_, StructureBoundingBox p_i3865_4_, int p_i3865_5_)
	{
		super(p_i3865_1_, p_i3865_2_);
		coordBaseMode = p_i3865_5_;
		boundingBox = p_i3865_4_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 7 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 7, 4, 4, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 6, 8, 4, 10, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 0, 6, 8, 0, 10, Block.dirt.blockID, Block.dirt.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 6, 0, 6, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 6, 2, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 1, 6, 8, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 10, 7, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 7, 0, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 0, 0, 8, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 0, 7, 1, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 5, 7, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 5, 7, 3, 5, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 1, 8, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 4, 8, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 2, 8, 5, 3, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 0, 4, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 0, 4, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 4, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 4, 3, p_74875_3_);
		int var4 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		int var5 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
		int var6;
		int var7;
		for(var6 = -1; var6 <= 2; ++var6)
		{
			for(var7 = 0; var7 <= 8; ++var7)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var4, var7, 4 + var6, var6, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var5, var7, 4 + var6, 5 - var6, p_74875_3_);
			}
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 2, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 8, 2, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 8, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 3, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 5, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 6, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 2, 1, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.pressurePlatePlanks.blockID, 0, 2, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 1, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 3), 2, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 1), 1, 1, 3, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 0, 1, 7, 0, 3, Block.stoneDoubleSlab.blockID, Block.stoneDoubleSlab.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stoneDoubleSlab.blockID, 0, 6, 1, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stoneDoubleSlab.blockID, 0, 6, 1, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 3, 1, p_74875_3_);
		placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		if(getBlockIdAtCurrentPosition(p_74875_1_, 2, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, 2, -1, -1, p_74875_3_) != 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 2, 0, -1, p_74875_3_);
		}
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 6, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 6, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 6, 3, 4, p_74875_3_);
		placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 6, 1, 5, getMetadataWithOffset(Block.doorWood.blockID, 1));
		for(var6 = 0; var6 < 5; ++var6)
		{
			for(var7 = 0; var7 < 9; ++var7)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var7, 7, var6, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var7, -1, var6, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 4, 1, 2, 2);
		return true;
	}
	
	@Override protected int getVillagerType(int p_74888_1_)
	{
		return p_74888_1_ == 0 ? 4 : 0;
	}
	
	public static ComponentVillageHall func_74906_a(ComponentVillageStartPiece p_74906_0_, List p_74906_1_, Random p_74906_2_, int p_74906_3_, int p_74906_4_, int p_74906_5_, int p_74906_6_, int p_74906_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74906_3_, p_74906_4_, p_74906_5_, 0, 0, 0, 9, 7, 11, p_74906_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74906_1_, var8) == null ? new ComponentVillageHall(p_74906_0_, p_74906_7_, p_74906_2_, var8, p_74906_6_) : null;
	}
}
