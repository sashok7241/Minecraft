package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdPrison extends ComponentStronghold
{
	protected final EnumDoor doorType;
	
	public ComponentStrongholdPrison(int p_i3847_1_, Random p_i3847_2_, StructureBoundingBox p_i3847_3_, int p_i3847_4_)
	{
		super(p_i3847_1_);
		coordBaseMode = p_i3847_4_;
		doorType = getRandomDoor(p_i3847_2_);
		boundingBox = p_i3847_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 8, 4, 10, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 1, 1, 0);
			fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 10, 3, 3, 10, 0, 0, false);
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 1, 4, 3, 1, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 3, 4, 3, 3, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 7, 4, 3, 7, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 9, 4, 3, 9, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 4, 4, 3, 6, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 5, 1, 5, 7, 3, 5, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fenceIron.blockID, 0, 4, 3, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.fenceIron.blockID, 0, 4, 3, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.doorIron.blockID, getMetadataWithOffset(Block.doorIron.blockID, 3), 4, 1, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.doorIron.blockID, getMetadataWithOffset(Block.doorIron.blockID, 3) + 8, 4, 2, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.doorIron.blockID, getMetadataWithOffset(Block.doorIron.blockID, 3), 4, 1, 8, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.doorIron.blockID, getMetadataWithOffset(Block.doorIron.blockID, 3) + 8, 4, 2, 8, p_74875_3_);
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
	}
	
	public static ComponentStrongholdPrison findValidPlacement(List p_75016_0_, Random p_75016_1_, int p_75016_2_, int p_75016_3_, int p_75016_4_, int p_75016_5_, int p_75016_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75016_2_, p_75016_3_, p_75016_4_, -1, -1, 0, 9, 5, 11, p_75016_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75016_0_, var7) == null ? new ComponentStrongholdPrison(p_75016_6_, p_75016_1_, var7, p_75016_5_) : null;
	}
}
