package net.minecraft.src;

import java.util.Random;

public class ComponentScatteredFeatureJunglePyramid extends ComponentScatteredFeature
{
	private boolean field_74947_h;
	private boolean field_74948_i;
	private boolean field_74945_j;
	private boolean field_74946_k;
	private static final WeightedRandomChestContent[] junglePyramidsChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3), new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 2, 7, 15), new WeightedRandomChestContent(Item.emerald.itemID, 0, 1, 3, 2), new WeightedRandomChestContent(Item.bone.itemID, 0, 4, 6, 20), new WeightedRandomChestContent(Item.rottenFlesh.itemID, 0, 3, 7, 16) };
	private static final WeightedRandomChestContent[] junglePyramidsDispenserContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.arrow.itemID, 0, 2, 7, 30) };
	private static StructureScatteredFeatureStones junglePyramidsRandomScatteredStones = new StructureScatteredFeatureStones((ComponentScatteredFeaturePieces2) null);
	
	public ComponentScatteredFeatureJunglePyramid(Random p_i3835_1_, int p_i3835_2_, int p_i3835_3_)
	{
		super(p_i3835_1_, p_i3835_2_, 64, p_i3835_3_, 12, 10, 15);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(!func_74935_a(p_74875_1_, p_74875_3_, 0)) return false;
		else
		{
			int var4 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 3);
			int var5 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 2);
			int var6 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 0);
			int var7 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 1);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, -4, 0, scatteredFeatureSizeX - 1, 0, scatteredFeatureSizeZ - 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 1, 2, 9, 2, 2, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 1, 12, 9, 2, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 1, 3, 2, 2, 11, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, 1, 3, 9, 2, 11, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 3, 1, 10, 6, 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 3, 13, 10, 6, 13, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 3, 2, 1, 6, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 10, 3, 2, 10, 6, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 3, 2, 9, 3, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 6, 2, 9, 6, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 3, 7, 3, 8, 7, 11, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 8, 4, 7, 8, 10, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithAir(p_74875_1_, p_74875_3_, 3, 1, 3, 8, 2, 11);
			fillWithAir(p_74875_1_, p_74875_3_, 4, 3, 6, 7, 3, 9);
			fillWithAir(p_74875_1_, p_74875_3_, 2, 4, 2, 9, 5, 12);
			fillWithAir(p_74875_1_, p_74875_3_, 4, 6, 5, 7, 6, 9);
			fillWithAir(p_74875_1_, p_74875_3_, 5, 7, 6, 6, 7, 8);
			fillWithAir(p_74875_1_, p_74875_3_, 5, 1, 2, 6, 2, 2);
			fillWithAir(p_74875_1_, p_74875_3_, 5, 2, 12, 6, 2, 12);
			fillWithAir(p_74875_1_, p_74875_3_, 5, 5, 1, 6, 5, 1);
			fillWithAir(p_74875_1_, p_74875_3_, 5, 5, 13, 6, 5, 13);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 5, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 10, 5, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 5, 9, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 10, 5, 9, p_74875_3_);
			int var8;
			for(var8 = 0; var8 <= 14; var8 += 14)
			{
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 4, var8, 2, 5, var8, false, p_74875_2_, junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 4, var8, 4, 5, var8, false, p_74875_2_, junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 7, 4, var8, 7, 5, var8, false, p_74875_2_, junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, 4, var8, 9, 5, var8, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			}
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 5, 6, 0, 6, 6, 0, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			for(var8 = 0; var8 <= 11; var8 += 11)
			{
				for(int var9 = 2; var9 <= 12; var9 += 2)
				{
					fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, var8, 4, var9, var8, 5, var9, false, p_74875_2_, junglePyramidsRandomScatteredStones);
				}
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, var8, 6, 5, var8, 6, 5, false, p_74875_2_, junglePyramidsRandomScatteredStones);
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, var8, 6, 9, var8, 6, 9, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			}
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 7, 2, 2, 9, 2, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, 7, 2, 9, 9, 2, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, 7, 12, 2, 9, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, 7, 12, 9, 9, 12, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 9, 4, 4, 9, 4, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 7, 9, 4, 7, 9, 4, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 9, 10, 4, 9, 10, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 7, 9, 10, 7, 9, 10, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 5, 9, 7, 6, 9, 7, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 5, 9, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 6, 9, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var5, 5, 9, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var5, 6, 9, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 4, 0, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 5, 0, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 6, 0, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 7, 0, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 4, 1, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 4, 2, 9, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 4, 3, 10, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 7, 1, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 7, 2, 9, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 7, 3, 10, p_74875_3_);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 9, 4, 1, 9, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 7, 1, 9, 7, 1, 9, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 10, 7, 2, 10, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 5, 4, 5, 6, 4, 5, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var6, 4, 4, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var7, 7, 4, 5, p_74875_3_);
			for(var8 = 0; var8 < 4; ++var8)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var5, 5, 0 - var8, 6 + var8, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var5, 6, 0 - var8, 6 + var8, p_74875_3_);
				fillWithAir(p_74875_1_, p_74875_3_, 5, 0 - var8, 7 + var8, 6, 0 - var8, 9 + var8);
			}
			fillWithAir(p_74875_1_, p_74875_3_, 1, -3, 12, 10, -1, 13);
			fillWithAir(p_74875_1_, p_74875_3_, 1, -3, 1, 3, -1, 13);
			fillWithAir(p_74875_1_, p_74875_3_, 1, -3, 1, 9, -1, 5);
			for(var8 = 1; var8 <= 13; var8 += 2)
			{
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, -3, var8, 1, -2, var8, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			}
			for(var8 = 2; var8 <= 12; var8 += 2)
			{
				fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, -1, var8, 3, -1, var8, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			}
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, -2, 1, 5, -2, 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 7, -2, 1, 9, -2, 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 6, -3, 1, 6, -3, 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 6, -1, 1, 6, -1, 1, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWireSource.blockID, getMetadataWithOffset(Block.tripWireSource.blockID, 3) | 4, 1, -3, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWireSource.blockID, getMetadataWithOffset(Block.tripWireSource.blockID, 1) | 4, 4, -3, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWire.blockID, 4, 2, -3, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWire.blockID, 4, 3, -3, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 7, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 5, -3, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 4, -3, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 3, -3, 1, p_74875_3_);
			if(!field_74945_j)
			{
				field_74945_j = generateStructureDispenserContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, -2, 1, 2, junglePyramidsDispenserContents, 2);
			}
			placeBlockAtCurrentPosition(p_74875_1_, Block.vine.blockID, 15, 3, -2, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWireSource.blockID, getMetadataWithOffset(Block.tripWireSource.blockID, 2) | 4, 7, -3, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWireSource.blockID, getMetadataWithOffset(Block.tripWireSource.blockID, 0) | 4, 7, -3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWire.blockID, 4, 7, -3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWire.blockID, 4, 7, -3, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.tripWire.blockID, 4, 7, -3, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 8, -3, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 9, -3, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 9, -3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 9, -3, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 9, -2, 4, p_74875_3_);
			if(!field_74946_k)
			{
				field_74946_k = generateStructureDispenserContents(p_74875_1_, p_74875_3_, p_74875_2_, 9, -2, 3, 4, junglePyramidsDispenserContents, 2);
			}
			placeBlockAtCurrentPosition(p_74875_1_, Block.vine.blockID, 15, 8, -1, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.vine.blockID, 15, 8, -2, 3, p_74875_3_);
			if(!field_74947_h)
			{
				field_74947_h = generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 8, -3, 3, WeightedRandomChestContent.func_92080_a(junglePyramidsChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 2 + p_74875_2_.nextInt(5));
			}
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 9, -3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 8, -3, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 4, -3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 5, -2, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 5, -1, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 6, -3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 7, -2, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 7, -1, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 8, -3, 5, p_74875_3_);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, -1, 1, 9, -1, 5, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithAir(p_74875_1_, p_74875_3_, 8, -3, 8, 10, -1, 10);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 3, 8, -2, 11, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 3, 9, -2, 11, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 3, 10, -2, 11, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.lever.blockID, BlockLever.invertMetadata(getMetadataWithOffset(Block.lever.blockID, 2)), 8, -2, 12, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.lever.blockID, BlockLever.invertMetadata(getMetadataWithOffset(Block.lever.blockID, 2)), 9, -2, 12, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.lever.blockID, BlockLever.invertMetadata(getMetadataWithOffset(Block.lever.blockID, 2)), 10, -2, 12, p_74875_3_);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 8, -3, 8, 8, -3, 10, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 10, -3, 8, 10, -3, 10, false, p_74875_2_, junglePyramidsRandomScatteredStones);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestoneMossy.blockID, 0, 10, -2, 9, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 8, -2, 9, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 8, -2, 10, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneWire.blockID, 0, 10, -1, 9, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.pistonStickyBase.blockID, 1, 9, -2, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.pistonStickyBase.blockID, getMetadataWithOffset(Block.pistonStickyBase.blockID, 4), 10, -2, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.pistonStickyBase.blockID, getMetadataWithOffset(Block.pistonStickyBase.blockID, 4), 10, -1, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.redstoneRepeaterIdle.blockID, getMetadataWithOffset(Block.redstoneRepeaterIdle.blockID, 2), 10, -2, 10, p_74875_3_);
			if(!field_74948_i)
			{
				field_74948_i = generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 9, -3, 10, WeightedRandomChestContent.func_92080_a(junglePyramidsChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 2 + p_74875_2_.nextInt(5));
			}
			return true;
		}
	}
}
