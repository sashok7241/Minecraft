package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeThrone extends ComponentNetherBridgePiece
{
	private boolean hasSpawner;
	
	public ComponentNetherBridgeThrone(int p_i3825_1_, Random p_i3825_2_, StructureBoundingBox p_i3825_3_, int p_i3825_4_)
	{
		super(p_i3825_1_);
		coordBaseMode = p_i3825_4_;
		boundingBox = p_i3825_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, 0, 6, 7, 7, 0, 0, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 0, 0, 5, 1, 7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 1, 5, 2, 7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 2, 5, 3, 7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 3, 5, 4, 7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 2, 0, 1, 4, 2, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 0, 5, 4, 2, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 2, 1, 5, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 5, 5, 2, 5, 5, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 3, 0, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 5, 3, 6, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 8, 5, 5, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 1, 6, 3, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.netherFence.blockID, 0, 5, 6, 3, p_74875_3_);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 6, 3, 0, 6, 8, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 6, 6, 3, 6, 6, 8, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 6, 8, 5, 7, 8, Block.netherFence.blockID, Block.netherFence.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 2, 8, 8, 4, 8, 8, Block.netherFence.blockID, Block.netherFence.blockID, false);
		int var4;
		int var5;
		if(!hasSpawner)
		{
			var4 = getYWithOffset(5);
			var5 = getXWithOffset(3, 5);
			int var6 = getZWithOffset(3, 5);
			if(p_74875_3_.isVecInside(var5, var4, var6))
			{
				hasSpawner = true;
				p_74875_1_.setBlock(var5, var4, var6, Block.mobSpawner.blockID, 0, 2);
				TileEntityMobSpawner var7 = (TileEntityMobSpawner) p_74875_1_.getBlockTileEntity(var5, var4, var6);
				if(var7 != null)
				{
					var7.getSpawnerLogic().setMobID("Blaze");
				}
			}
		}
		for(var4 = 0; var4 <= 6; ++var4)
		{
			for(var5 = 0; var5 <= 6; ++var5)
			{
				fillCurrentPositionBlocksDownwards(p_74875_1_, Block.netherBrick.blockID, 0, var4, -1, var5, p_74875_3_);
			}
		}
		return true;
	}
	
	public static ComponentNetherBridgeThrone createValidComponent(List p_74975_0_, Random p_74975_1_, int p_74975_2_, int p_74975_3_, int p_74975_4_, int p_74975_5_, int p_74975_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74975_2_, p_74975_3_, p_74975_4_, -2, 0, 0, 7, 8, 9, p_74975_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74975_0_, var7) == null ? new ComponentNetherBridgeThrone(p_74975_6_, p_74975_1_, var7, p_74975_5_) : null;
	}
}
