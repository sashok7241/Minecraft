package net.minecraft.src;

import java.util.Random;

public class ComponentScatteredFeatureSwampHut extends ComponentScatteredFeature
{
	private boolean hasWitch;
	
	public ComponentScatteredFeatureSwampHut(Random p_i5095_1_, int p_i5095_2_, int p_i5095_3_)
	{
		super(p_i5095_1_, p_i5095_2_, 64, p_i5095_3_, 7, 5, 9);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(!func_74935_a(p_74875_1_, p_74875_3_, 0)) return false;
		else
		{
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 5, 1, 7, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 1, 4, 2, 5, 4, 7, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 2, 1, 0, 4, 1, 0, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 2, 2, 2, 3, 3, 2, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 1, 2, 3, 1, 3, 6, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 5, 2, 3, 5, 3, 6, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 2, 2, 7, 4, 3, 7, Block.planks.blockID, 1, Block.planks.blockID, 1, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 2, 1, 3, 2, Block.wood.blockID, Block.wood.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 0, 2, 5, 3, 2, Block.wood.blockID, Block.wood.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 7, 1, 3, 7, Block.wood.blockID, Block.wood.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 0, 7, 5, 3, 7, Block.wood.blockID, Block.wood.blockID, false);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 2, 3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 3, 3, 7, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 1, 3, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 5, 3, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 5, 3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.flowerPot.blockID, 7, 1, 3, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.workbench.blockID, 0, 3, 2, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.cauldron.blockID, 0, 4, 2, 6, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 2, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 5, 2, 1, p_74875_3_);
			int var4 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
			int var5 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 1);
			int var6 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 0);
			int var7 = getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 0, 4, 1, 6, 4, 1, Block.stairsWoodSpruce.blockID, var4, Block.stairsWoodSpruce.blockID, var4, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 0, 4, 2, 0, 4, 7, Block.stairsWoodSpruce.blockID, var6, Block.stairsWoodSpruce.blockID, var6, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 6, 4, 2, 6, 4, 7, Block.stairsWoodSpruce.blockID, var5, Block.stairsWoodSpruce.blockID, var5, false);
			fillWithMetadataBlocks(p_74875_1_, p_74875_3_, 0, 4, 8, 6, 4, 8, Block.stairsWoodSpruce.blockID, var7, Block.stairsWoodSpruce.blockID, var7, false);
			int var8;
			int var9;
			for(var8 = 2; var8 <= 7; var8 += 5)
			{
				for(var9 = 1; var9 <= 5; var9 += 4)
				{
					fillCurrentPositionBlocksDownwards(p_74875_1_, Block.wood.blockID, 0, var9, -1, var8, p_74875_3_);
				}
			}
			if(!hasWitch)
			{
				var8 = getXWithOffset(2, 5);
				var9 = getYWithOffset(2);
				int var10 = getZWithOffset(2, 5);
				if(p_74875_3_.isVecInside(var8, var9, var10))
				{
					hasWitch = true;
					EntityWitch var11 = new EntityWitch(p_74875_1_);
					var11.setLocationAndAngles(var8 + 0.5D, var9, var10 + 0.5D, 0.0F, 0.0F);
					var11.initCreature();
					p_74875_1_.spawnEntityInWorld(var11);
				}
			}
			return true;
		}
	}
}
