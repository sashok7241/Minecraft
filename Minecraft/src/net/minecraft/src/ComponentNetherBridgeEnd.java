package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentNetherBridgeEnd extends ComponentNetherBridgePiece
{
	private int fillSeed;
	
	public ComponentNetherBridgeEnd(int p_i3815_1_, Random p_i3815_2_, StructureBoundingBox p_i3815_3_, int p_i3815_4_)
	{
		super(p_i3815_1_);
		coordBaseMode = p_i3815_4_;
		boundingBox = p_i3815_3_;
		fillSeed = p_i3815_2_.nextInt();
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		Random var4 = new Random(fillSeed);
		int var5;
		int var6;
		int var7;
		for(var5 = 0; var5 <= 4; ++var5)
		{
			for(var6 = 3; var6 <= 4; ++var6)
			{
				var7 = var4.nextInt(8);
				fillWithBlocks(p_74875_1_, p_74875_3_, var5, var6, 0, var5, var6, var7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			}
		}
		var5 = var4.nextInt(8);
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 0, 0, 5, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		var5 = var4.nextInt(8);
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 5, 0, 4, 5, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		for(var5 = 0; var5 <= 4; ++var5)
		{
			var6 = var4.nextInt(5);
			fillWithBlocks(p_74875_1_, p_74875_3_, var5, 2, 0, var5, 2, var6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
		}
		for(var5 = 0; var5 <= 4; ++var5)
		{
			for(var6 = 0; var6 <= 1; ++var6)
			{
				var7 = var4.nextInt(3);
				fillWithBlocks(p_74875_1_, p_74875_3_, var5, var6, 0, var5, var6, var7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
			}
		}
		return true;
	}
	
	public static ComponentNetherBridgeEnd func_74971_a(List p_74971_0_, Random p_74971_1_, int p_74971_2_, int p_74971_3_, int p_74971_4_, int p_74971_5_, int p_74971_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74971_2_, p_74971_3_, p_74971_4_, -1, -3, 0, 5, 10, 8, p_74971_5_);
		return isAboveGround(var7) && StructureComponent.findIntersecting(p_74971_0_, var7) == null ? new ComponentNetherBridgeEnd(p_74971_6_, p_74971_1_, var7, p_74971_5_) : null;
	}
}
