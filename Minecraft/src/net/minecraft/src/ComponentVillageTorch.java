package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageTorch extends ComponentVillage
{
	private int averageGroundLevel = -1;
	
	public ComponentVillageTorch(ComponentVillageStartPiece p_i3863_1_, int p_i3863_2_, Random p_i3863_3_, StructureBoundingBox p_i3863_4_, int p_i3863_5_)
	{
		super(p_i3863_1_, p_i3863_2_);
		coordBaseMode = p_i3863_5_;
		boundingBox = p_i3863_4_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(averageGroundLevel < 0)
		{
			averageGroundLevel = getAverageGroundLevel(p_74875_1_, p_74875_3_);
			if(averageGroundLevel < 0) return true;
			boundingBox.offset(0, averageGroundLevel - boundingBox.maxY + 4 - 1, 0);
		}
		fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 2, 3, 1, 0, 0, false);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 0, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 1, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 1, 2, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.cloth.blockID, 15, 1, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 0, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 1, 3, 1, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 3, 0, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 1, 3, -1, p_74875_3_);
		return true;
	}
	
	public static StructureBoundingBox func_74904_a(ComponentVillageStartPiece p_74904_0_, List p_74904_1_, Random p_74904_2_, int p_74904_3_, int p_74904_4_, int p_74904_5_, int p_74904_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74904_3_, p_74904_4_, p_74904_5_, 0, 0, 0, 3, 4, 2, p_74904_6_);
		return StructureComponent.findIntersecting(p_74904_1_, var7) != null ? null : var7;
	}
}
