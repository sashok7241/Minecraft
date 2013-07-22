package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdStairs extends ComponentStronghold
{
	private final boolean field_75024_a;
	private final EnumDoor doorType;
	
	public ComponentStrongholdStairs(int p_i3850_1_, Random p_i3850_2_, int p_i3850_3_, int p_i3850_4_)
	{
		super(p_i3850_1_);
		field_75024_a = true;
		coordBaseMode = p_i3850_2_.nextInt(4);
		doorType = EnumDoor.OPENING;
		switch(coordBaseMode)
		{
			case 0:
			case 2:
				boundingBox = new StructureBoundingBox(p_i3850_3_, 64, p_i3850_4_, p_i3850_3_ + 5 - 1, 74, p_i3850_4_ + 5 - 1);
				break;
			default:
				boundingBox = new StructureBoundingBox(p_i3850_3_, 64, p_i3850_4_, p_i3850_3_ + 5 - 1, 74, p_i3850_4_ + 5 - 1);
		}
	}
	
	public ComponentStrongholdStairs(int p_i3851_1_, Random p_i3851_2_, StructureBoundingBox p_i3851_3_, int p_i3851_4_)
	{
		super(p_i3851_1_);
		field_75024_a = false;
		coordBaseMode = p_i3851_4_;
		doorType = getRandomDoor(p_i3851_2_);
		boundingBox = p_i3851_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 10, 4, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 1, 7, 0);
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, EnumDoor.OPENING, 1, 1, 4);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 2, 6, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 5, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 1, 6, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 5, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 4, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 1, 5, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 2, 4, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 3, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 3, 4, 3, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 2, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 3, 3, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 2, 2, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 1, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 1, 2, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 1, 1, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 1, 1, 3, p_74875_3_);
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		if(field_75024_a)
		{
			StructureStrongholdPieces.setComponentType(ComponentStrongholdCrossing.class);
		}
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
	}
	
	public static ComponentStrongholdStairs getStrongholdStairsComponent(List p_75022_0_, Random p_75022_1_, int p_75022_2_, int p_75022_3_, int p_75022_4_, int p_75022_5_, int p_75022_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75022_2_, p_75022_3_, p_75022_4_, -1, -7, 0, 5, 11, 5, p_75022_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75022_0_, var7) == null ? new ComponentStrongholdStairs(p_75022_6_, p_75022_1_, var7, p_75022_5_) : null;
	}
}
