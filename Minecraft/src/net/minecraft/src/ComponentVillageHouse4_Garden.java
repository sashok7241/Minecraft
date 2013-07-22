package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHouse4_Garden extends ComponentVillage
{
	private int averageGroundLevel = -1;
	private final boolean isRoofAccessible;
	
	public ComponentVillageHouse4_Garden(ComponentVillageStartPiece p_i3866_1_, int p_i3866_2_, Random p_i3866_3_, StructureBoundingBox p_i3866_4_, int p_i3866_5_)
	{
		super(p_i3866_1_, p_i3866_2_);
		coordBaseMode = p_i3866_5_;
		boundingBox = p_i3866_4_;
		isRoofAccessible = p_i3866_3_.nextBoolean();
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 6 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 0, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 0, 4, 4, 4, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 1, 3, 4, 3, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 0, 3, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 3, 4, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 4, 3, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 1, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 1, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 1, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 2, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 3, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 3, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 3, 1, 0, p_74875_3_);
		if(getBlockIdAtCurrentPosition(p_74875_1_, 2, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, 2, -1, -1, p_74875_3_) != 0)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 2, 0, -1, p_74875_3_);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 3, 3, 3, 0, 0, false);
		if(isRoofAccessible)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 0, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 2, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 3, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 0, 5, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 5, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 2, 5, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 3, 5, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 5, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 5, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 5, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 4, 5, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 0, 5, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 0, 5, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 0, 5, 3, p_74875_3_);
		}
		int var4;
		if(isRoofAccessible)
		{
			var4 = getMetadataWithOffset(Block.ladder.blockID, 3);
			placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var4, 3, 1, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var4, 3, 2, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var4, 3, 3, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var4, 3, 4, 3, p_74875_3_);
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 3, 1, p_74875_3_);
		for(var4 = 0; var4 < 5; ++var4)
		{
			for(int var5 = 0; var5 < 5; ++var5)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 6, var4, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var5, -1, var4, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 1, 1, 2, 1);
		return true;
	}
	
	public static ComponentVillageHouse4_Garden func_74912_a(ComponentVillageStartPiece p_74912_0_, List p_74912_1_, Random p_74912_2_, int p_74912_3_, int p_74912_4_, int p_74912_5_, int p_74912_6_, int p_74912_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74912_3_, p_74912_4_, p_74912_5_, 0, 0, 0, 5, 6, 5, p_74912_6_);
		return StructureComponent.findIntersecting(p_74912_1_, var8) != null ? null : new ComponentVillageHouse4_Garden(p_74912_0_, p_74912_7_, p_74912_2_, var8, p_74912_6_);
	}
}
