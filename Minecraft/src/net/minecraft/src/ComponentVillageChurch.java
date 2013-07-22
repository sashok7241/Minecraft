package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageChurch extends ComponentVillage
{
	private int averageGroundLevel = -1;
	
	public ComponentVillageChurch(ComponentVillageStartPiece p_i3868_1_, int p_i3868_2_, Random p_i3868_3_, StructureBoundingBox p_i3868_4_, int p_i3868_5_)
	{
		super(p_i3868_1_, p_i3868_2_);
		coordBaseMode = p_i3868_5_;
		boundingBox = p_i3868_4_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 12 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 3, 7, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 3, 9, 3, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 0, 3, 0, 8, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 3, 10, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 10, 3, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 10, 3, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 4, 0, 4, 7, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 0, 4, 4, 4, 7, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 8, 3, 4, 8, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 4, 3, 10, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 5, 3, 5, 7, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 9, 0, 4, 9, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 0, 4, 4, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 11, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 11, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 2, 11, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 2, 11, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 1, 1, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 1, 1, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 2, 1, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 3, 1, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 3, 1, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 2, 1, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 3, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 1), 1, 2, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 0), 3, 2, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 3, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 3, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 6, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 7, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 6, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 7, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 6, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 7, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 6, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 7, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 3, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 3, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 3, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 4, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 1, 4, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 3, 4, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 4, 5, p_74875_3_);
		int var4 = getMetadataWithOffset(Block.ladder.blockID, 4);
		int var5;
		for(var5 = 1; var5 <= 9; ++var5)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var4, 3, var5, 3, p_74875_3_);
		}
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 2, 0, p_74875_3_);
		placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		if(getBlockIdAtCurrentPosition(p_74875_1_, 2, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, 2, -1, -1, p_74875_3_) != 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 2, 0, -1, p_74875_3_);
		}
		for(var5 = 0; var5 < 9; ++var5)
		{
			for(int var6 = 0; var6 < 5; ++var6)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var6, 12, var5, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var6, -1, var5, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 2, 1, 2, 1);
		return true;
	}
	
	@Override protected int getVillagerType(int p_74888_1_)
	{
		return 2;
	}
	
	public static ComponentVillageChurch func_74919_a(ComponentVillageStartPiece p_74919_0_, List p_74919_1_, Random p_74919_2_, int p_74919_3_, int p_74919_4_, int p_74919_5_, int p_74919_6_, int p_74919_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74919_3_, p_74919_4_, p_74919_5_, 0, 0, 0, 5, 12, 9, p_74919_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74919_1_, var8) == null ? new ComponentVillageChurch(p_74919_0_, p_74919_7_, p_74919_2_, var8, p_74919_6_) : null;
	}
}
