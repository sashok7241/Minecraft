package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeCrossing2 extends ComponentNetherBridgePiece
{
	public ComponentNetherBridgeCrossing2(int p_i3820_1_, Random p_i3820_2_, StructureBoundingBox p_i3820_3_, int p_i3820_4_)
	{
		super(p_i3820_1_);
		coordBaseMode = p_i3820_4_;
		boundingBox = p_i3820_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 1, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 4, 5, 4, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 0, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 2, 0, 4, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 4, 0, 5, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 2, 4, 4, 5, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
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
		getNextComponentNormal((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 1, 0, true);
		getNextComponentX((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
		getNextComponentZ((ComponentNetherBridgeStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, 1, true);
	}
	
	public static ComponentNetherBridgeCrossing2 createValidComponent(List p_74979_0_, Random p_74979_1_, int p_74979_2_, int p_74979_3_, int p_74979_4_, int p_74979_5_, int p_74979_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74979_2_, p_74979_3_, p_74979_4_, -1, 0, 0, 5, 7, 5, p_74979_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74979_0_, var7) == null ? new ComponentNetherBridgeCrossing2(p_74979_6_, p_74979_1_, var7, p_74979_5_) : null;
	}
}
