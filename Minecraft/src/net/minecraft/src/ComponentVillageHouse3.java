package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHouse3 extends ComponentVillage
{
	private int averageGroundLevel = -1;
	
	public ComponentVillageHouse3(ComponentVillageStartPiece p_i3872_1_, int p_i3872_2_, Random p_i3872_3_, StructureBoundingBox p_i3872_4_, int p_i3872_5_)
	{
		super(p_i3872_1_, p_i3872_2_);
		coordBaseMode = p_i3872_5_;
		boundingBox = p_i3872_4_;
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
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 0, 5, 8, 0, 10, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 1, 7, 0, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 0, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 0, 0, 8, 3, 10, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 0, 7, 2, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 5, 2, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 0, 6, 2, 3, 10, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 0, 10, 7, 3, 10, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 7, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 5, 2, 3, 5, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 1, 8, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 4, 3, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 2, 8, 5, 3, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 0, 4, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 0, 4, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 4, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 4, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 4, 4, p_74875_3_);
		int var4 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
		int var5 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
		int var6;
		int var7;
		for(var6 = -1; var6 <= 2; ++var6)
		{
			for(var7 = 0; var7 <= 8; ++var7)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var4, var7, 4 + var6, var6, p_74875_3_);
				if((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6))
				{
					placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var5, var7, 4 + var6, 5 - var6, p_74875_3_);
				}
			}
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 4, 5, 3, 4, 10, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 7, 4, 2, 7, 4, 10, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 5, 4, 4, 5, 10, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 5, 4, 6, 5, 10, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 6, 3, 5, 6, 10, Block.planks.blockID, Block.planks.blockID, false);
		var6 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 0);
		int var8;
		for(var7 = 4; var7 >= 1; --var7)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, var7, 2 + var7, 7 - var7, p_74875_3_);
			for(var8 = 8 - var7; var8 <= 10; ++var8)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var6, var7, 2 + var7, var8, p_74875_3_);
			}
		}
		var7 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 1);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 6, 6, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 7, 5, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var7, 6, 6, 4, p_74875_3_);
		int var9;
		for(var8 = 6; var8 <= 8; ++var8)
		{
			for(var9 = 5; var9 <= 10; ++var9)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, var7, var8, 12 - var8, var9, p_74875_3_);
			}
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 2, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 0, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 4, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 5, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 6, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 8, 2, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 8, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 8, 2, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 8, 2, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 8, 2, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 2, 2, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 2, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 2, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 2, 2, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 4, 4, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 5, 4, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.wood.blockID, 0, 6, 4, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 5, 5, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 2, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 3, 1, p_74875_3_);
		placeDoorAtCurrentPosition(p_74875_1_, p_74875_3_, p_74875_2_, 2, 1, 0, getMetadataWithOffset(Block.doorWood.blockID, 1));
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, -1, 3, 2, -1, 0, 0, false);
		if(getBlockIdAtCurrentPosition(p_74875_1_, 2, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, 2, -1, -1, p_74875_3_) != 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 2, 0, -1, p_74875_3_);
		}
		for(var8 = 0; var8 < 5; ++var8)
		{
			for(var9 = 0; var9 < 9; ++var9)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var9, 7, var8, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var9, -1, var8, p_74875_3_);
			}
		}
		for(var8 = 5; var8 < 11; ++var8)
		{
			for(var9 = 2; var9 < 9; ++var9)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var9, 7, var8, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var9, -1, var8, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 4, 1, 2, 2);
		return true;
	}
	
	public static ComponentVillageHouse3 func_74921_a(ComponentVillageStartPiece p_74921_0_, List p_74921_1_, Random p_74921_2_, int p_74921_3_, int p_74921_4_, int p_74921_5_, int p_74921_6_, int p_74921_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74921_3_, p_74921_4_, p_74921_5_, 0, 0, 0, 9, 7, 12, p_74921_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74921_1_, var8) == null ? new ComponentVillageHouse3(p_74921_0_, p_74921_7_, p_74921_2_, var8, p_74921_6_) : null;
	}
}
