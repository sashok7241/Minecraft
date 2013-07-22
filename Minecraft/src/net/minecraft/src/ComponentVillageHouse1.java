package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHouse1 extends ComponentVillage
{
	private int averageGroundLevel = -1;
	
	public ComponentVillageHouse1(ComponentVillageStartPiece p_i3860_1_, int p_i3860_2_, Random p_i3860_3_, StructureBoundingBox p_i3860_4_, int p_i3860_5_)
	{
		super(p_i3860_1_, p_i3860_2_);
		coordBaseMode = p_i3860_5_;
		boundingBox = p_i3860_4_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 9 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 7, 5, 4, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 8, 0, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 0, 8, 5, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 6, 1, 8, 6, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 7, 2, 8, 7, 3, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		int var4 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		int var5 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
		int var6;
		int var7;
		for(var6 = -1; var6 <= 2; ++var6)
		{
			for(var7 = 0; var7 <= 8; ++var7)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var4, var7, 6 + var6, var6, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var5, var7, 6 + var6, 5 - var6, p_74875_3_);
			}
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 5, 8, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 1, 0, 8, 1, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 1, 0, 7, 1, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 4, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 5, 0, 4, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 2, 5, 8, 4, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 2, 0, 8, 4, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 1, 0, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 5, 7, 4, 5, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 2, 1, 8, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 4, 0, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 5, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 6, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 5, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 6, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 3, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 3, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 3, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 3, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 3, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 5, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 6, 2, 5, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 1, 7, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 4, 7, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 4, 7, 3, 4, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 7, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 0), 7, 1, 3, p_74875_3_);
		var6 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var6, 6, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var6, 5, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var6, 4, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var6, 3, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 6, 1, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.pressurePlatePlanks.blockID, 0, 6, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 1, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.pressurePlatePlanks.blockID, 0, 4, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.workbench.blockID, 0, 7, 1, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 2, 0, p_74875_3_);
		placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 1, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		if(getBlockIdAtCurrentPosition(p_74875_1_, 1, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, 1, -1, -1, p_74875_3_) != 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 0, -1, p_74875_3_);
		}
		for(var7 = 0; var7 < 6; ++var7)
		{
			for(int var8 = 0; var8 < 9; ++var8)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var8, 9, var7, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var8, -1, var7, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 2, 1, 2, 1);
		return true;
	}
	
	@Override protected int getVillagerType(int p_74888_1_)
	{
		return 1;
	}
	
	public static ComponentVillageHouse1 func_74898_a(ComponentVillageStartPiece p_74898_0_, List p_74898_1_, Random p_74898_2_, int p_74898_3_, int p_74898_4_, int p_74898_5_, int p_74898_6_, int p_74898_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74898_3_, p_74898_4_, p_74898_5_, 0, 0, 0, 9, 9, 6, p_74898_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74898_1_, var8) == null ? new ComponentVillageHouse1(p_74898_0_, p_74898_7_, p_74898_2_, var8, p_74898_6_) : null;
	}
}
