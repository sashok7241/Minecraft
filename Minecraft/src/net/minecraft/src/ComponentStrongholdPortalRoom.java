package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdPortalRoom extends ComponentStronghold
{
	private boolean hasSpawner;
	
	public ComponentStrongholdPortalRoom(int p_i3846_1_, Random p_i3846_2_, StructureBoundingBox p_i3846_3_, int p_i3846_4_)
	{
		super(p_i3846_1_);
		coordBaseMode = p_i3846_4_;
		boundingBox = p_i3846_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 10, 7, 15, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, EnumDoor.GRATES, 4, 1, 0);
		byte var4 = 6;
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, var4, 1, 1, var4, 14, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 9, var4, 1, 9, var4, 14, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, var4, 1, 8, var4, 2, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 2, var4, 14, 8, var4, 14, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 2, 1, 4, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 8, 1, 1, 9, 1, 4, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, 1, 1, 1, 3, Block.lavaMoving.blockID, Block.lavaMoving.blockID, false);
		fillWithBlocks(p_74875_1_, p_74875_3_, 9, 1, 1, 9, 1, 3, Block.lavaMoving.blockID, Block.lavaMoving.blockID, false);
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 3, 1, 8, 7, 1, 12, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 9, 6, 1, 11, Block.lavaMoving.blockID, Block.lavaMoving.blockID, false);
		int var5;
		for(var5 = 3; var5 < 14; var5 += 2)
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 3, var5, 0, 4, var5, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 10, 3, var5, 10, 4, var5, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
		}
		for(var5 = 2; var5 < 9; var5 += 2)
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, var5, 3, 15, var5, 4, 15, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
		}
		var5 = getMetadataWithOffset(Block.stairsStoneBrick.blockID, 3);
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 1, 5, 6, 1, 7, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 2, 6, 6, 2, 7, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 4, 3, 7, 6, 3, 7, false, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
		for(int var6 = 4; var6 <= 6; ++var6)
		{
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsStoneBrick.blockID, var5, var6, 1, 4, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsStoneBrick.blockID, var5, var6, 2, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stairsStoneBrick.blockID, var5, var6, 3, 6, p_74875_3_);
		}
		byte var14 = 2;
		byte var7 = 0;
		byte var8 = 3;
		byte var9 = 1;
		switch(coordBaseMode)
		{
			case 0:
				var14 = 0;
				var7 = 2;
				break;
			case 1:
				var14 = 1;
				var7 = 3;
				var8 = 0;
				var9 = 2;
			case 2:
			default:
				break;
			case 3:
				var14 = 3;
				var7 = 1;
				var8 = 0;
				var9 = 2;
		}
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var14 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 4, 3, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var14 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 5, 3, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var14 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 6, 3, 8, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var7 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 4, 3, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var7 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 5, 3, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var7 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 6, 3, 12, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var8 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 3, 3, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var8 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 3, 3, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var8 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 3, 3, 11, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var9 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 7, 3, 9, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var9 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 7, 3, 10, p_74875_3_);
		placeBlockAtCurrentPosition(p_74875_1_, Block.endPortalFrame.blockID, var9 + (p_74875_2_.nextFloat() > 0.9F ? 4 : 0), 7, 3, 11, p_74875_3_);
		if(!hasSpawner)
		{
			int var13 = getYWithOffset(3);
			int var10 = getXWithOffset(5, 6);
			int var11 = getZWithOffset(5, 6);
			if(p_74875_3_.isVecInside(var10, var13, var11))
			{
				hasSpawner = true;
				p_74875_1_.setBlock(var10, var13, var11, Block.mobSpawner.blockID, 0, 2);
				TileEntityMobSpawner var12 = (TileEntityMobSpawner) p_74875_1_.getBlockTileEntity(var10, var13, var11);
				if(var12 != null)
				{
					var12.getSpawnerLogic().setMobID("Silverfish");
				}
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		if(p_74861_1_ != null)
		{
			((ComponentStrongholdStairs2) p_74861_1_).strongholdPortalRoom = this;
		}
	}
	
	public static ComponentStrongholdPortalRoom findValidPlacement(List p_75004_0_, Random p_75004_1_, int p_75004_2_, int p_75004_3_, int p_75004_4_, int p_75004_5_, int p_75004_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75004_2_, p_75004_3_, p_75004_4_, -4, -1, 0, 11, 8, 16, p_75004_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75004_0_, var7) == null ? new ComponentStrongholdPortalRoom(p_75004_6_, p_75004_1_, var7, p_75004_5_) : null;
	}
}
