package net.minecraft.src;

import java.util.List;
import java.util.Random;

abstract class ComponentStronghold extends StructureComponent
{
	protected ComponentStronghold(int p_i3856_1_)
	{
		super(p_i3856_1_);
	}
	
	protected StructureComponent getNextComponentNormal(ComponentStrongholdStairs2 p_74986_1_, List p_74986_2_, Random p_74986_3_, int p_74986_4_, int p_74986_5_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74986_1_, p_74986_2_, p_74986_3_, boundingBox.minX + p_74986_4_, boundingBox.minY + p_74986_5_, boundingBox.maxZ + 1, coordBaseMode, getComponentType());
			case 1:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74986_1_, p_74986_2_, p_74986_3_, boundingBox.minX - 1, boundingBox.minY + p_74986_5_, boundingBox.minZ + p_74986_4_, coordBaseMode, getComponentType());
			case 2:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74986_1_, p_74986_2_, p_74986_3_, boundingBox.minX + p_74986_4_, boundingBox.minY + p_74986_5_, boundingBox.minZ - 1, coordBaseMode, getComponentType());
			case 3:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74986_1_, p_74986_2_, p_74986_3_, boundingBox.maxX + 1, boundingBox.minY + p_74986_5_, boundingBox.minZ + p_74986_4_, coordBaseMode, getComponentType());
			default:
				return null;
		}
	}
	
	protected StructureComponent getNextComponentX(ComponentStrongholdStairs2 p_74989_1_, List p_74989_2_, Random p_74989_3_, int p_74989_4_, int p_74989_5_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74989_1_, p_74989_2_, p_74989_3_, boundingBox.minX - 1, boundingBox.minY + p_74989_4_, boundingBox.minZ + p_74989_5_, 1, getComponentType());
			case 1:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74989_1_, p_74989_2_, p_74989_3_, boundingBox.minX + p_74989_5_, boundingBox.minY + p_74989_4_, boundingBox.minZ - 1, 2, getComponentType());
			case 2:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74989_1_, p_74989_2_, p_74989_3_, boundingBox.minX - 1, boundingBox.minY + p_74989_4_, boundingBox.minZ + p_74989_5_, 1, getComponentType());
			case 3:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74989_1_, p_74989_2_, p_74989_3_, boundingBox.minX + p_74989_5_, boundingBox.minY + p_74989_4_, boundingBox.minZ - 1, 2, getComponentType());
			default:
				return null;
		}
	}
	
	protected StructureComponent getNextComponentZ(ComponentStrongholdStairs2 p_74987_1_, List p_74987_2_, Random p_74987_3_, int p_74987_4_, int p_74987_5_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74987_1_, p_74987_2_, p_74987_3_, boundingBox.maxX + 1, boundingBox.minY + p_74987_4_, boundingBox.minZ + p_74987_5_, 3, getComponentType());
			case 1:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74987_1_, p_74987_2_, p_74987_3_, boundingBox.minX + p_74987_5_, boundingBox.minY + p_74987_4_, boundingBox.maxZ + 1, 0, getComponentType());
			case 2:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74987_1_, p_74987_2_, p_74987_3_, boundingBox.maxX + 1, boundingBox.minY + p_74987_4_, boundingBox.minZ + p_74987_5_, 3, getComponentType());
			case 3:
				return StructureStrongholdPieces.getNextValidComponentAccess(p_74987_1_, p_74987_2_, p_74987_3_, boundingBox.minX + p_74987_5_, boundingBox.minY + p_74987_4_, boundingBox.maxZ + 1, 0, getComponentType());
			default:
				return null;
		}
	}
	
	protected EnumDoor getRandomDoor(Random p_74988_1_)
	{
		int var2 = p_74988_1_.nextInt(5);
		switch(var2)
		{
			case 0:
			case 1:
			default:
				return EnumDoor.OPENING;
			case 2:
				return EnumDoor.WOOD_DOOR;
			case 3:
				return EnumDoor.GRATES;
			case 4:
				return EnumDoor.IRON_DOOR;
		}
	}
	
	protected void placeDoor(World p_74990_1_, Random p_74990_2_, StructureBoundingBox p_74990_3_, EnumDoor p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_)
	{
		switch(EnumDoorHelper.doorEnum[p_74990_4_.ordinal()])
		{
			case 1:
			default:
				fillWithBlocks(p_74990_1_, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, 0, 0, false);
				break;
			case 2:
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.doorWood.blockID, 0, p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.doorWood.blockID, 8, p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				break;
			case 3:
				placeBlockAtCurrentPosition(p_74990_1_, 0, 0, p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, 0, 0, p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.fenceIron.blockID, 0, p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
				break;
			case 4:
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneBrick.blockID, 0, p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.doorIron.blockID, 0, p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.doorIron.blockID, 8, p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneButton.blockID, getMetadataWithOffset(Block.stoneButton.blockID, 4), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
				placeBlockAtCurrentPosition(p_74990_1_, Block.stoneButton.blockID, getMetadataWithOffset(Block.stoneButton.blockID, 3), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
		}
	}
	
	protected static boolean canStrongholdGoDeeper(StructureBoundingBox p_74991_0_)
	{
		return p_74991_0_ != null && p_74991_0_.minY > 10;
	}
}
