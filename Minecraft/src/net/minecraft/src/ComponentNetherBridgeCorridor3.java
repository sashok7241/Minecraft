package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeCorridor3 extends ComponentNetherBridgePiece
{
	public ComponentNetherBridgeCorridor3(int p_i3817_1_, Random p_i3817_2_, StructureBoundingBox p_i3817_3_, int p_i3817_4_)
	{
		super(p_i3817_1_);
		coordBaseMode = p_i3817_4_;
		boundingBox = p_i3817_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		int var4 = getMetadataWithOffset(Block.stairsNetherBrick.blockID, 2);
		for(int var5 = 0; var5 <= 9; ++var5)
		{
			int var6 = Math.max(1, 7 - var5);
			int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
			int var8 = var5;
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, var5, 4, var6, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, var6 + 1, var5, 3, var7 - 1, var5, 0, 0, false);
			if(var5 <= 6)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var4, 1, var6 + 1, var5, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var4, 2, var6 + 1, var5, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsNetherBrick.blockID, var4, 3, var6 + 1, var5, p_74875_3_);
			}
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, var7, var5, 4, var7, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, var6 + 1, var5, 0, var7 - 1, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 4, var6 + 1, var5, 4, var7 - 1, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			if((var5 & 1) == 0)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, var6 + 2, var5, 0, var6 + 3, var5, Block.netherFence.blockID, Block.netherFence.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, var6 + 2, var5, 4, var6 + 3, var5, Block.netherFence.blockID, Block.netherFence.blockID, false);
			}
			for(int var9 = 0; var9 <= 4; ++var9)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var9, -1, var8, p_74875_3_);
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
	}
	
	public static ComponentNetherBridgeCorridor3 createValidComponent(List p_74982_0_, Random p_74982_1_, int p_74982_2_, int p_74982_3_, int p_74982_4_, int p_74982_5_, int p_74982_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74982_2_, p_74982_3_, p_74982_4_, -1, -7, 0, 5, 14, 10, p_74982_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74982_0_, var7) == null ? new ComponentNetherBridgeCorridor3(p_74982_6_, p_74982_1_, var7, p_74982_5_) : null;
	}
}
