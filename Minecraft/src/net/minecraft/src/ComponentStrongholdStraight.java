package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdStraight extends ComponentStronghold
{
	private final EnumDoor doorType;
	private final boolean expandsX;
	private final boolean expandsZ;
	
	public ComponentStrongholdStraight(int p_i3853_1_, Random p_i3853_2_, StructureBoundingBox p_i3853_3_, int p_i3853_4_)
	{
		super(p_i3853_1_);
		coordBaseMode = p_i3853_4_;
		doorType = getRandomDoor(p_i3853_2_);
		boundingBox = p_i3853_3_;
		expandsX = p_i3853_2_.nextInt(2) == 0;
		expandsZ = p_i3853_2_.nextInt(2) == 0;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 1, 1, 0);
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, EnumDoor.OPENING, 1, 1, 6);
			randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 1, 2, 1, Block.torchWood.blockID, 0);
			randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 3, 2, 1, Block.torchWood.blockID, 0);
			randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 1, 2, 5, Block.torchWood.blockID, 0);
			randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 3, 2, 5, Block.torchWood.blockID, 0);
			if(expandsX)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 2, 0, 3, 4, 0, 0, false);
			}
			if(expandsZ)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 2, 4, 3, 4, 0, 0, false);
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
		if(expandsX)
		{
			getNextComponentX((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
		}
		if(expandsZ)
		{
			getNextComponentZ((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
		}
	}
	
	public static ComponentStrongholdStraight findValidPlacement(List p_75018_0_, Random p_75018_1_, int p_75018_2_, int p_75018_3_, int p_75018_4_, int p_75018_5_, int p_75018_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75018_2_, p_75018_3_, p_75018_4_, -1, -1, 0, 5, 5, 7, p_75018_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75018_0_, var7) == null ? new ComponentStrongholdStraight(p_75018_6_, p_75018_1_, var7, p_75018_5_) : null;
	}
}
