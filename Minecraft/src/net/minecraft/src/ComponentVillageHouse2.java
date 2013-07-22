package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHouse2 extends ComponentVillage
{
	private static final WeightedRandomChestContent[] villageBlacksmithChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3), new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5), new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.swordIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.plateIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.helmetIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.legsIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.bootsIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Block.obsidian.blockID, 0, 3, 7, 5), new WeightedRandomChestContent(Block.sapling.blockID, 0, 3, 7, 5) };
	private int averageGroundLevel = -1;
	private boolean hasMadeChest;
	
	public ComponentVillageHouse2(ComponentVillageStartPiece p_i3869_1_, int p_i3869_2_, Random p_i3869_3_, StructureBoundingBox p_i3869_4_, int p_i3869_5_)
	{
		super(p_i3869_1_, p_i3869_2_);
		coordBaseMode = p_i3869_5_;
		boundingBox = p_i3869_4_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 6 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 9, 4, 6, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 9, 0, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 4, 0, 9, 4, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 0, 9, 5, 6, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 8, 5, 5, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 0, 2, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 0, 0, 4, 0, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 0, 3, 4, 0, Block.wood.blockID, Block.wood.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 6, 0, 4, 6, Block.wood.blockID, Block.wood.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 3, 3, 1, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 2, 3, 3, 2, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 3, 5, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 5, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 6, 5, 3, 6, Block.planks.blockID, Block.planks.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 0, 5, 3, 0, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 1, 0, 9, 3, 0, Block.fence.blockID, Block.fence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, 4, 9, 4, 6, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.lavaMoving.blockID, 0, 7, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.lavaMoving.blockID, 0, 8, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fenceIron.blockID, 0, 9, 2, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fenceIron.blockID, 0, 9, 2, 4, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 7, 2, 4, 8, 2, 5, 0, 0, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 6, 1, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.furnaceIdle.blockID, 0, 6, 2, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.furnaceIdle.blockID, 0, 6, 3, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stoneDoubleSlab.blockID, 0, 8, 1, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 0, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 2, 2, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.thinGlass.blockID, 0, 4, 2, 6, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 2, 1, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.pressurePlatePlanks.blockID, 0, 2, 2, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 1, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 3), 2, 1, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsWoodOak.blockID, getMetadataWithOffset(Block.stairsWoodOak.blockID, 1), 1, 1, 4, p_74875_3_);
		int var4;
		int var5;
		if(!hasMadeChest)
		{
			var4 = getYWithOffset(1);
			var5 = getXWithOffset(5, 5);
			int var6 = getZWithOffset(5, 5);
			if(p_74875_3_.isVecInside(var5, var4, var6))
			{
				hasMadeChest = true;
				generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 5, 1, 5, villageBlacksmithChestContents, 3 + p_74875_2_.nextInt(6));
			}
		}
		for(var4 = 6; var4 <= 8; ++var4)
		{
			if(getBlockIdAtCurrentPosition(p_74875_1_, var4, 0, -1, p_74875_3_) == 0 && getBlockIdAtCurrentPosition(p_74875_1_, var4, -1, -1, p_74875_3_) != 0)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), var4, 0, -1, p_74875_3_);
			}
		}
		for(var4 = 0; var4 < 7; ++var4)
		{
			for(var5 = 0; var5 < 10; ++var5)
			{
				clearCurrentPositionBlocksUpwards(p_74875_1_, var5, 6, var4, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.cobblestone.blockID, 0, var5, -1, var4, p_74875_3_);
			}
		}
		spawnVillagers(p_74875_1_, p_74875_3_, 7, 1, 1, 1);
		return true;
	}
	
	@Override protected int getVillagerType(int p_74888_1_)
	{
		return 3;
	}
	
	public static ComponentVillageHouse2 func_74915_a(ComponentVillageStartPiece p_74915_0_, List p_74915_1_, Random p_74915_2_, int p_74915_3_, int p_74915_4_, int p_74915_5_, int p_74915_6_, int p_74915_7_)
	{
		StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74915_3_, p_74915_4_, p_74915_5_, 0, 0, 0, 10, 6, 7, p_74915_6_);
		return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(p_74915_1_, var8) == null ? new ComponentVillageHouse2(p_74915_0_, p_74915_7_, p_74915_2_, var8, p_74915_6_) : null;
	}
}
