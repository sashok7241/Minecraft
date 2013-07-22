package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeCrossing extends ComponentNetherBridgePiece
{
	public ComponentNetherBridgeCrossing(int p_i3829_1_, Random p_i3829_2_, StructureBoundingBox p_i3829_3_, int p_i3829_4_)
	{
		super(p_i3829_1_);
		coordBaseMode = p_i3829_4_;
		boundingBox = p_i3829_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 6, 1, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 6, 7, 6, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 1, 6, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 6, 1, 6, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 0, 6, 6, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 6, 6, 6, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 6, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 5, 0, 6, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 2, 0, 6, 6, 1, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 2, 5, 6, 6, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 6, 0, 4, 6, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 0, 4, 5, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 6, 6, 4, 6, 6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 5, 6, 4, 5, 6, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 6, 2, 0, 6, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 2, 0, 5, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 6, 2, 6, 6, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 5, 2, 6, 5, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
		for(int var4 = 0; var4 <= 6; ++var4)
		{
			for(int var5 = 0; var5 <= 6; ++var5)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var4, -1, var5, p_74875_3_);
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 2, 0, false);
		getNextComponentX((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
		getNextComponentZ((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, 2, false);
	}
	
	public static ComponentNetherBridgeCrossing createValidComponent(List p_74974_0_, Random p_74974_1_, int p_74974_2_, int p_74974_3_, int p_74974_4_, int p_74974_5_, int p_74974_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74974_2_, p_74974_3_, p_74974_4_, -2, 0, 0, 7, 9, 7, p_74974_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74974_0_, var7) == null ? new ComponentNetherBridgeCrossing(p_74974_6_, p_74974_1_, var7, p_74974_5_) : null;
	}
}
