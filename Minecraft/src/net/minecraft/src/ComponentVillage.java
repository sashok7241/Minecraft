package net.minecraft.src;

import java.util.List;
import java.util.Random;

abstract class ComponentVillage extends StructureComponent
{
	private int villagersSpawned;
	protected ComponentVillageStartPiece startPiece;
	
	protected ComponentVillage(ComponentVillageStartPiece p_i3873_1_, int p_i3873_2_)
	{
		super(p_i3873_2_);
		startPiece = p_i3873_1_;
	}
	
	@Override protected void fillCurrentPositionBlocksDownwards(World p_74870_1_, int p_74870_2_, int p_74870_3_, int p_74870_4_, int p_74870_5_, int p_74870_6_, StructureBoundingBox p_74870_7_)
	{
		int var8 = getBiomeSpecificBlock(p_74870_2_, p_74870_3_);
		int var9 = getBiomeSpecificBlockMetadata(p_74870_2_, p_74870_3_);
		super.fillCurrentPositionBlocksDownwards(p_74870_1_, var8, var9, p_74870_4_, p_74870_5_, p_74870_6_, p_74870_7_);
	}
	
	@Override protected void fillWithBlocks(World p_74884_1_, StructureBoundingBox p_74884_2_, int p_74884_3_, int p_74884_4_, int p_74884_5_, int p_74884_6_, int p_74884_7_, int p_74884_8_, int p_74884_9_, int p_74884_10_, boolean p_74884_11_)
	{
		int var12 = getBiomeSpecificBlock(p_74884_9_, 0);
		int var13 = getBiomeSpecificBlockMetadata(p_74884_9_, 0);
		int var14 = getBiomeSpecificBlock(p_74884_10_, 0);
		int var15 = getBiomeSpecificBlockMetadata(p_74884_10_, 0);
		super.fillWithMetadataBlocks(p_74884_1_, p_74884_2_, p_74884_3_, p_74884_4_, p_74884_5_, p_74884_6_, p_74884_7_, p_74884_8_, var12, var13, var14, var15, p_74884_11_);
	}
	
	protected int getAverageGroundLevel(World p_74889_1_, StructureBoundingBox p_74889_2_)
	{
		int var3 = 0;
		int var4 = 0;
		for(int var5 = boundingBox.minZ; var5 <= boundingBox.maxZ; ++var5)
		{
			for(int var6 = boundingBox.minX; var6 <= boundingBox.maxX; ++var6)
			{
				if(p_74889_2_.isVecInside(var6, 64, var5))
				{
					var3 += Math.max(p_74889_1_.getTopSolidOrLiquidBlock(var6, var5), p_74889_1_.provider.getAverageGroundLevel());
					++var4;
				}
			}
		}
		if(var4 == 0) return -1;
		else return var3 / var4;
	}
	
	protected int getBiomeSpecificBlock(int p_74890_1_, int p_74890_2_)
	{
		if(startPiece.inDesert)
		{
			if(p_74890_1_ == Block.wood.blockID) return Block.sandStone.blockID;
			if(p_74890_1_ == Block.cobblestone.blockID) return Block.sandStone.blockID;
			if(p_74890_1_ == Block.planks.blockID) return Block.sandStone.blockID;
			if(p_74890_1_ == Block.stairsWoodOak.blockID) return Block.stairsSandStone.blockID;
			if(p_74890_1_ == Block.stairsCobblestone.blockID) return Block.stairsSandStone.blockID;
			if(p_74890_1_ == Block.gravel.blockID) return Block.sandStone.blockID;
		}
		return p_74890_1_;
	}
	
	protected int getBiomeSpecificBlockMetadata(int p_74892_1_, int p_74892_2_)
	{
		if(startPiece.inDesert)
		{
			if(p_74892_1_ == Block.wood.blockID) return 0;
			if(p_74892_1_ == Block.cobblestone.blockID) return 0;
			if(p_74892_1_ == Block.planks.blockID) return 2;
		}
		return p_74892_2_;
	}
	
	protected StructureComponent getNextComponentNN(ComponentVillageStartPiece p_74891_1_, List p_74891_2_, Random p_74891_3_, int p_74891_4_, int p_74891_5_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return StructureVillagePieces.getNextStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, boundingBox.minX - 1, boundingBox.minY + p_74891_4_, boundingBox.minZ + p_74891_5_, 1, getComponentType());
			case 1:
				return StructureVillagePieces.getNextStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, boundingBox.minX + p_74891_5_, boundingBox.minY + p_74891_4_, boundingBox.minZ - 1, 2, getComponentType());
			case 2:
				return StructureVillagePieces.getNextStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, boundingBox.minX - 1, boundingBox.minY + p_74891_4_, boundingBox.minZ + p_74891_5_, 1, getComponentType());
			case 3:
				return StructureVillagePieces.getNextStructureComponent(p_74891_1_, p_74891_2_, p_74891_3_, boundingBox.minX + p_74891_5_, boundingBox.minY + p_74891_4_, boundingBox.minZ - 1, 2, getComponentType());
			default:
				return null;
		}
	}
	
	protected StructureComponent getNextComponentPP(ComponentVillageStartPiece p_74894_1_, List p_74894_2_, Random p_74894_3_, int p_74894_4_, int p_74894_5_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return StructureVillagePieces.getNextStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, boundingBox.maxX + 1, boundingBox.minY + p_74894_4_, boundingBox.minZ + p_74894_5_, 3, getComponentType());
			case 1:
				return StructureVillagePieces.getNextStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, boundingBox.minX + p_74894_5_, boundingBox.minY + p_74894_4_, boundingBox.maxZ + 1, 0, getComponentType());
			case 2:
				return StructureVillagePieces.getNextStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, boundingBox.maxX + 1, boundingBox.minY + p_74894_4_, boundingBox.minZ + p_74894_5_, 3, getComponentType());
			case 3:
				return StructureVillagePieces.getNextStructureComponent(p_74894_1_, p_74894_2_, p_74894_3_, boundingBox.minX + p_74894_5_, boundingBox.minY + p_74894_4_, boundingBox.maxZ + 1, 0, getComponentType());
			default:
				return null;
		}
	}
	
	protected int getVillagerType(int p_74888_1_)
	{
		return 0;
	}
	
	@Override protected void placeBlockAtCurrentPosition(World p_74864_1_, int p_74864_2_, int p_74864_3_, int p_74864_4_, int p_74864_5_, int p_74864_6_, StructureBoundingBox p_74864_7_)
	{
		int var8 = getBiomeSpecificBlock(p_74864_2_, p_74864_3_);
		int var9 = getBiomeSpecificBlockMetadata(p_74864_2_, p_74864_3_);
		super.placeBlockAtCurrentPosition(p_74864_1_, var8, var9, p_74864_4_, p_74864_5_, p_74864_6_, p_74864_7_);
	}
	
	protected void spawnVillagers(World p_74893_1_, StructureBoundingBox p_74893_2_, int p_74893_3_, int p_74893_4_, int p_74893_5_, int p_74893_6_)
	{
		if(villagersSpawned < p_74893_6_)
		{
			for(int var7 = villagersSpawned; var7 < p_74893_6_; ++var7)
			{
				int var8 = getXWithOffset(p_74893_3_ + var7, p_74893_5_);
				int var9 = getYWithOffset(p_74893_4_);
				int var10 = getZWithOffset(p_74893_3_ + var7, p_74893_5_);
				if(!p_74893_2_.isVecInside(var8, var9, var10))
				{
					break;
				}
				++villagersSpawned;
				EntityVillager var11 = new EntityVillager(p_74893_1_, getVillagerType(var7));
				var11.setLocationAndAngles(var8 + 0.5D, var9, var10 + 0.5D, 0.0F, 0.0F);
				p_74893_1_.spawnEntityInWorld(var11);
			}
		}
	}
	
	protected static boolean canVillageGoDeeper(StructureBoundingBox p_74895_0_)
	{
		return p_74895_0_ != null && p_74895_0_.minY > 10;
	}
}
