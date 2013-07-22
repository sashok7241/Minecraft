package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdRightTurn extends ComponentStrongholdLeftTurn
{
	public ComponentStrongholdRightTurn(int p_i3843_1_, Random p_i3843_2_, StructureBoundingBox p_i3843_3_, int p_i3843_4_)
	{
		super(p_i3843_1_, p_i3843_2_, p_i3843_3_, p_i3843_4_);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 1, 1, 0);
			if(coordBaseMode != 2 && coordBaseMode != 3)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, 0, 0, false);
			} else
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, 0, 0, false);
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		if(coordBaseMode != 2 && coordBaseMode != 3)
		{
			getNextComponentX((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		} else
		{
			getNextComponentZ((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		}
	}
}
