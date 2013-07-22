package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdStairsStraight extends ComponentStronghold
{
	private final EnumDoor doorType;
	
	public ComponentStrongholdStairsStraight(int p_i3854_1_, Random p_i3854_2_, StructureBoundingBox p_i3854_3_, int p_i3854_4_)
	{
		super(p_i3854_1_);
		coordBaseMode = p_i3854_4_;
		doorType = getRandomDoor(p_i3854_2_);
		boundingBox = p_i3854_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 10, 7, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 1, 7, 0);
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, EnumDoor.OPENING, 1, 1, 7);
			int var4 = getMetadataWithOffset(Block.stairsCobblestone.blockID, 2);
			for(int var5 = 0; var5 < 6; ++var5)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 1, 6 - var5, 1 + var5, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 2, 6 - var5, 1 + var5, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.stairsCobblestone.blockID, var4, 3, 6 - var5, 1 + var5, p_74875_3_);
				if(var5 < 5)
				{
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 5 - var5, 1 + var5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 2, 5 - var5, 1 + var5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 5 - var5, 1 + var5, p_74875_3_);
				}
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
	}
	
	public static ComponentStrongholdStairsStraight findValidPlacement(List p_75028_0_, Random p_75028_1_, int p_75028_2_, int p_75028_3_, int p_75028_4_, int p_75028_5_, int p_75028_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75028_2_, p_75028_3_, p_75028_4_, -1, -7, 0, 5, 11, 8, p_75028_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75028_0_, var7) == null ? new ComponentStrongholdStairsStraight(p_75028_6_, p_75028_1_, var7, p_75028_5_) : null;
	}
}
