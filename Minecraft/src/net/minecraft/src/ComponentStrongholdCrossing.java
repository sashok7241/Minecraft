package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdCrossing extends ComponentStronghold
{
	protected final EnumDoor doorType;
	private boolean field_74996_b;
	private boolean field_74997_c;
	private boolean field_74995_d;
	private boolean field_74999_h;
	
	public ComponentStrongholdCrossing(int p_i3842_1_, Random p_i3842_2_, StructureBoundingBox p_i3842_3_, int p_i3842_4_)
	{
		super(p_i3842_1_);
		coordBaseMode = p_i3842_4_;
		doorType = getRandomDoor(p_i3842_2_);
		boundingBox = p_i3842_3_;
		field_74996_b = p_i3842_2_.nextBoolean();
		field_74997_c = p_i3842_2_.nextBoolean();
		field_74995_d = p_i3842_2_.nextBoolean();
		field_74999_h = p_i3842_2_.nextInt(3) > 0;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 9, 8, 10, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 4, 3, 0);
			if(field_74996_b)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, 1, 0, 5, 3, 0, 0, false);
			}
			if(field_74995_d)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 9, 3, 1, 9, 5, 3, 0, 0, false);
			}
			if(field_74997_c)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 7, 0, 7, 9, 0, 0, false);
			}
			if(field_74999_h)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 9, 5, 7, 9, 7, 9, 0, 0, false);
			}
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 10, 7, 3, 10, 0, 0, false);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 2, 1, 8, 2, 6, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 5, 4, 4, 9, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 8, 1, 5, 8, 4, 9, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 4, 7, 3, 4, 9, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 3, 5, 3, 3, 6, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 3, 4, 3, 3, 4, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 4, 6, 3, 4, 6, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 5, 1, 7, 7, 1, 8, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 9, 7, 1, 9, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 2, 7, 7, 2, 7, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 4, 5, 7, 4, 5, 9, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 8, 5, 7, 8, 5, 9, Block.stoneSingleSlab.blockID, Block.stoneSingleSlab.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 5, 7, 7, 5, 9, Block.stoneDoubleSlab.blockID, Block.stoneDoubleSlab.blockID, false);
			placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 6, 5, 6, p_74875_3_);
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		int var4 = 3;
		int var5 = 5;
		if(coordBaseMode == 1 || coordBaseMode == 2)
		{
			var4 = 8 - var4;
			var5 = 8 - var5;
		}
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);
		if(field_74996_b)
		{
			getNextComponentX((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
		}
		if(field_74997_c)
		{
			getNextComponentX((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
		}
		if(field_74995_d)
		{
			getNextComponentZ((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
		}
		if(field_74999_h)
		{
			getNextComponentZ((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
		}
	}
	
	public static ComponentStrongholdCrossing findValidPlacement(List p_74994_0_, Random p_74994_1_, int p_74994_2_, int p_74994_3_, int p_74994_4_, int p_74994_5_, int p_74994_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_74994_2_, p_74994_3_, p_74994_4_, -4, -3, 0, 10, 9, 11, p_74994_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_74994_0_, var7) == null ? new ComponentStrongholdCrossing(p_74994_6_, p_74994_1_, var7, p_74994_5_) : null;
	}
}
