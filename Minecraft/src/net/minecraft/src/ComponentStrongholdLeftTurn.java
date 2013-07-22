package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdLeftTurn extends ComponentStronghold
{
	protected final EnumDoor doorType;
	
	public ComponentStrongholdLeftTurn(int p_i3843_1_, Random p_i3843_2_, StructureBoundingBox p_i3843_3_, int p_i3843_4_)
	{
		super(p_i3843_1_);
		coordBaseMode = p_i3843_4_;
		doorType = getRandomDoor(p_i3843_2_);
		boundingBox = p_i3843_3_;
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
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 3, 0, 0, false);
			} else
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 1, 0, 3, 3, 0, 0, false);
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		if(coordBaseMode != 2 && coordBaseMode != 3)
		{
			getNextComponentZ((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		} else
		{
			getNextComponentX((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		}
	}
	
	public static ComponentStrongholdLeftTurn findValidPlacement(List p_75010_0_, Random p_75010_1_, int p_75010_2_, int p_75010_3_, int p_75010_4_, int p_75010_5_, int p_75010_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75010_2_, p_75010_3_, p_75010_4_, -1, -1, 0, 5, 5, 5, p_75010_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75010_0_, var7) == null ? new ComponentStrongholdLeftTurn(p_75010_6_, p_75010_1_, var7, p_75010_5_) : null;
	}
}
