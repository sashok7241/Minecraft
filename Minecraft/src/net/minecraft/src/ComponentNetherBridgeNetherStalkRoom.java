package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeNetherStalkRoom extends ComponentNetherBridgePiece
{
	public ComponentNetherBridgeNetherStalkRoom(int p_i3824_1_, Random p_i3824_2_, StructureBoundingBox p_i3824_3_, int p_i3824_4_)
	{
		super(p_i3824_1_);
		coordBaseMode = p_i3824_4_;
		boundingBox = p_i3824_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 0, 12, 4, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 0, 12, 13, 12, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 0, 1, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 11, 5, 0, 12, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 11, 4, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 5, 11, 10, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 9, 11, 7, 12, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 0, 4, 12, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 5, 0, 10, 12, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 9, 0, 7, 12, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 11, 2, 10, 12, 10, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		int var4;
		for(var4 = 1; var4 <= 11; var4 += 2)
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, var4, 10, 0, var4, 11, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, var4, 10, 12, var4, 11, 12, Block.netherFence.blockID, Block.netherFence.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 10, var4, 0, 11, var4, Block.netherFence.blockID, Block.netherFence.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 12, 10, var4, 12, 11, var4, Block.netherFence.blockID, Block.netherFence.blockID, false);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherBrick.blockID, 0, var4, 13, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherBrick.blockID, 0, var4, 13, 12, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherBrick.blockID, 0, 0, 13, var4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherBrick.blockID, 0, 12, 13, var4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, var4 + 1, 13, 0, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, var4 + 1, 13, 12, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 0, 13, var4 + 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 12, 13, var4 + 1, p_74875_3_);
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 0, 13, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 0, 13, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 0, 13, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 12, 13, 0, p_74875_3_);
		for(var4 = 3; var4 <= 9; var4 += 2)
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 7, var4, 1, 8, var4, Block.netherFence.blockID, Block.netherFence.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 11, 7, var4, 11, 8, var4, Block.netherFence.blockID, Block.netherFence.blockID, false);
		}
		var4 = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 3);
		int var5;
		int var6;
		int var7;
		for(var5 = 0; var5 <= 6; ++var5)
		{
			var6 = var5 + 4;
			for(var7 = 5; var7 <= 7; ++var7)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var4, var7, 5 + var5, var6, p_74875_3_);
			}
			if(var6 >= 5 && var6 <= 8)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 5, 5, var6, 7, var5 + 4, var6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			} else if(var6 >= 9 && var6 <= 10)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 5, 8, var6, 7, var5 + 4, var6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			}
			if(var5 >= 1)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 5, 6 + var5, var6, 7, 9 + var5, var6, 0, 0, false);
			}
		}
		for(var5 = 5; var5 <= 7; ++var5)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var4, var5, 12, 11, p_74875_3_);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 6, 7, 5, 7, 7, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 7, 6, 7, 7, 7, 7, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 13, 12, 7, 13, 12, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 2, 3, 5, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 9, 3, 5, 10, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 4, 2, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 5, 2, 10, 5, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 5, 9, 10, 5, 10, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 10, 5, 4, 10, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		var5 = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 0);
		var6 = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 1);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var6, 4, 5, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var6, 4, 5, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var6, 4, 5, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var6, 4, 5, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var5, 8, 5, 2, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var5, 8, 5, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var5, 8, 5, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var5, 8, 5, 10, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 4, 4, 4, 4, 8, Block.slowSand.blockID, Block.slowSand.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 4, 4, 9, 4, 8, Block.slowSand.blockID, Block.slowSand.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 5, 4, 4, 5, 8, Block.netherStalk.blockID, Block.netherStalk.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 8, 5, 4, 9, 5, 8, Block.netherStalk.blockID, Block.netherStalk.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 2, 0, 8, 2, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 4, 12, 2, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 0, 0, 8, 1, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 0, 9, 8, 1, 12, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 4, 3, 1, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 0, 4, 12, 1, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		int var8;
		for(var7 = 4; var7 <= 8; ++var7)
		{
			for(var8 = 0; var8 <= 2; ++var8)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var7, -1, var8, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var7, -1, 12 - var8, p_74875_3_);
			}
		}
		for(var7 = 0; var7 <= 2; ++var7)
		{
			for(var8 = 4; var8 <= 8; ++var8)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var7, -1, var8, p_74875_3_);
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, 12 - var7, -1, var8, p_74875_3_);
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 5, 3, true);
		getNextComponentNormal((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 5, 11, true);
	}
	
	public static ComponentNetherBridgeNetherStalkRoom createValidComponent(List p_74977_0_, Random p_74977_1_, int p_74977_2_, int p_74977_3_, int p_74977_4_, int p_74977_5_, int p_74977_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74977_2_, p_74977_3_, p_74977_4_, -5, -3, 0, 13, 14, 13, p_74977_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74977_0_, var7) == null ? new ComponentNetherBridgeNetherStalkRoom(p_74977_6_, p_74977_1_, var7, p_74977_5_) : null;
	}
}
