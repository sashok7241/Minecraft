package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeCorridor2 extends ComponentNetherBridgePiece
{
	public ComponentNetherBridgeCorridor2(int p_i3823_1_, Random p_i3823_2_, StructureBoundingBox p_i3823_3_, int p_i3823_4_)
	{
		super(p_i3823_1_);
		coordBaseMode = p_i3823_4_;
		boundingBox = p_i3823_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 5, 4, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 5, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 4, 1, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 3, 0, 4, 3, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 2, 0, 4, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 4, 4, 5, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 4, 1, 4, 4, Block.netherFence.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 3, 3, 4, 3, 4, 4, Block.netherFence.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 6, 0, 4, 6, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		for(int var4 = 0; var4 <= 4; ++var4)
		{
			for(int var5 = 0; var5 <= 4; ++var5)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var4, -1, var5, p_74875_3_);
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentZ((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
	}
	
	public static ComponentNetherBridgeCorridor2 createValidComponent(List p_74980_0_, Random p_74980_1_, int p_74980_2_, int p_74980_3_, int p_74980_4_, int p_74980_5_, int p_74980_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74980_2_, p_74980_3_, p_74980_4_, -1, 0, 0, 5, 7, 5, p_74980_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74980_0_, var7) == null ? new ComponentNetherBridgeCorridor2(p_74980_6_, p_74980_1_, var7, p_74980_5_) : null;
	}
}
