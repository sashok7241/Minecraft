package net.minecraft.src;

import java.util.Random;

public class ComponentScatteredFeatureDesertPyramid extends ComponentScatteredFeature
{
	private boolean[] field_74940_h = new boolean[4];
	private static final WeightedRandomChestContent[] itemsToGenerateInTemple = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3), new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 2, 7, 15), new WeightedRandomChestContent(Item.emerald.itemID, 0, 1, 3, 2), new WeightedRandomChestContent(Item.bone.itemID, 0, 4, 6, 20), new WeightedRandomChestContent(Item.rottenFlesh.itemID, 0, 3, 7, 16) };
	
	public ComponentScatteredFeatureDesertPyramid(Random p_i3833_1_, int p_i3833_2_, int p_i3833_3_)
	{
		super(p_i3833_1_, p_i3833_2_, 64, p_i3833_3_, 21, 15, 21);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, -4, 0, scatteredFeatureSizeX - 1, 0, scatteredFeatureSizeZ - 1, Block.sandStone.blockID, Block.sandStone.blockID, false);
		int var4;
		for(var4 = 1; var4 <= 9; ++var4)
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, var4, var4, var4, scatteredFeatureSizeX - 1 - var4, var4, scatteredFeatureSizeZ - 1 - var4, Block.sandStone.blockID, Block.sandStone.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, var4 + 1, var4, var4 + 1, scatteredFeatureSizeX - 2 - var4, var4, scatteredFeatureSizeZ - 2 - var4, 0, 0, false);
		}
		int var5;
		for(var4 = 0; var4 < scatteredFeatureSizeX; ++var4)
		{
			for(var5 = 0; var5 < scatteredFeatureSizeZ; ++var5)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.sandStone.blockID, 0, var4, -5, var5, p_74875_3_);
			}
		}
		var4 = getMetadataWithOffset(Block.stairsSandStone.blockID, 3);
		var5 = getMetadataWithOffset(Block.stairsSandStone.blockID, 2);
		int var6 = getMetadataWithOffset(Block.stairsSandStone.blockID, 0);
		int var7 = getMetadataWithOffset(Block.stairsSandStone.blockID, 1);
		byte var8 = 1;
		byte var9 = 11;
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 9, 4, Block.sandStone.blockID, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 10, 1, 3, 10, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var4, 2, 10, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var5, 2, 10, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var6, 0, 10, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var7, 4, 10, 2, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 5, 0, 0, scatteredFeatureSizeX - 1, 9, 4, Block.sandStone.blockID, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 4, 10, 1, scatteredFeatureSizeX - 2, 10, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var4, scatteredFeatureSizeX - 3, 10, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var5, scatteredFeatureSizeX - 3, 10, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var6, scatteredFeatureSizeX - 5, 10, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var7, scatteredFeatureSizeX - 1, 10, 2, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 0, 0, 12, 4, 4, Block.sandStone.blockID, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 1, 0, 11, 3, 4, 0, 0, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 9, 1, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 9, 2, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 9, 3, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 10, 3, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 11, 3, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 11, 2, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 11, 1, 1, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 8, 3, 3, Block.sandStone.blockID, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 2, 8, 2, 2, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 12, 1, 1, 16, 3, 3, Block.sandStone.blockID, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 12, 1, 2, 16, 2, 2, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 4, 5, scatteredFeatureSizeX - 6, 4, scatteredFeatureSizeZ - 6, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 4, 9, 11, 4, 11, 0, 0, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 8, 1, 8, 8, 3, 8, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 12, 1, 8, 12, 3, 8, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 8, 1, 12, 8, 3, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 12, 1, 12, 12, 3, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 5, 4, 4, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 5, 1, 5, scatteredFeatureSizeX - 2, 4, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 7, 9, 6, 7, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 7, 7, 9, scatteredFeatureSizeX - 7, 7, 11, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 5, 5, 9, 5, 7, 11, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 6, 5, 9, scatteredFeatureSizeX - 6, 7, 11, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 5, 5, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 5, 6, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 6, 6, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, scatteredFeatureSizeX - 6, 5, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, scatteredFeatureSizeX - 6, 6, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, scatteredFeatureSizeX - 7, 6, 10, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 4, 4, 2, 6, 4, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 3, 4, 4, scatteredFeatureSizeX - 3, 6, 4, 0, 0, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var4, 2, 4, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var4, 2, 3, 4, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var4, scatteredFeatureSizeX - 3, 4, 5, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var4, scatteredFeatureSizeX - 3, 3, 4, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 3, 2, 2, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 3, 1, 3, scatteredFeatureSizeX - 2, 2, 3, Block.sandStone.blockID, Block.sandStone.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, 0, 1, 1, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, 0, scatteredFeatureSizeX - 2, 1, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 1, 1, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 1, scatteredFeatureSizeX - 2, 2, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var7, 2, 1, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsSandStone.blockID, var6, scatteredFeatureSizeX - 3, 1, 2, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 3, 5, 4, 3, 18, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 5, 3, 5, scatteredFeatureSizeX - 5, 3, 17, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 5, 4, 2, 16, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, scatteredFeatureSizeX - 6, 1, 5, scatteredFeatureSizeX - 5, 2, 16, 0, 0, false);
		int var10;
		for(var10 = 5; var10 <= 17; var10 += 2)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 4, 1, var10, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, 4, 2, var10, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, scatteredFeatureSizeX - 5, 1, var10, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, scatteredFeatureSizeX - 5, 2, var10, p_74875_3_);
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 10, 0, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 10, 0, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 9, 0, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 11, 0, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 8, 0, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 12, 0, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 7, 0, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 13, 0, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 9, 0, 11, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 11, 0, 11, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 10, 0, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 10, 0, 13, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var9, 10, 0, 10, p_74875_3_);
		for(var10 = 0; var10 <= scatteredFeatureSizeX - 1; var10 += scatteredFeatureSizeX - 1)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 2, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 2, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 2, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 3, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 3, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 4, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, var10, 4, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 4, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 5, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 5, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 5, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 6, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, var10, 6, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 6, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 7, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 7, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 7, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 8, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 8, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 8, 3, p_74875_3_);
		}
		for(var10 = 2; var10 <= scatteredFeatureSizeX - 3; var10 += scatteredFeatureSizeX - 3 - 2)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 - 1, 2, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 2, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 + 1, 2, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 - 1, 3, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 3, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 + 1, 3, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10 - 1, 4, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, var10, 4, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10 + 1, 4, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 - 1, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 + 1, 5, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10 - 1, 6, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, var10, 6, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10 + 1, 6, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10 - 1, 7, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10, 7, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, var10 + 1, 7, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 - 1, 8, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10, 8, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, var10 + 1, 8, 0, p_74875_3_);
		}
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 8, 4, 0, 12, 6, 0, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 8, 6, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 12, 6, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 9, 5, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, 10, 5, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, var8, 11, 5, 0, p_74875_3_);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 8, -14, 8, 12, -11, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 8, -10, 8, 12, -10, 12, Block.sandStone.blockID, 1, Block.sandStone.blockID, 1, false);
		fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 8, -9, 8, 12, -9, 12, Block.sandStone.blockID, 2, Block.sandStone.blockID, 2, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, -8, 8, 12, -1, 12, Block.sandStone.blockID, Block.sandStone.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, -11, 9, 11, -1, 11, 0, 0, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.pressurePlateStone.blockID, 0, 10, -11, 10, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, -13, 9, 11, -13, 11, Block.tnt.blockID, 0, false);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 8, -11, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 8, -10, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, 7, -10, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 7, -11, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 12, -11, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 12, -10, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, 13, -10, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 13, -11, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 10, -11, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 10, -10, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, 10, -10, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 10, -11, 7, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 10, -11, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 10, -10, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 1, 10, -10, 13, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.sandStone.blockID, 2, 10, -11, 13, p_74875_3_);
		for(var10 = 0; var10 < 4; ++var10)
		{
			if(!field_74940_h[var10])
			{
				int var11 = Direction.offsetX[var10] * 2;
				int var12 = Direction.offsetZ[var10] * 2;
				field_74940_h[var10] = generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 10 + var11, -11, 10 + var12, WeightedRandomChestContent.func_92080_a(itemsToGenerateInTemple, new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 2 + p_74875_2_.nextInt(5));
			}
		}
		return true;
	}
}
