package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillagePathGen extends ComponentVillageRoadPiece
{
	private int averageGroundLevel;
	
	public ComponentVillagePathGen(ComponentVillageStartPiece p_i3871_1_, int p_i3871_2_, Random p_i3871_3_, StructureBoundingBox p_i3871_4_, int p_i3871_5_)
	{
		super(p_i3871_1_, p_i3871_2_);
		coordBaseMode = p_i3871_5_;
		boundingBox = p_i3871_4_;
		averageGroundLevel = Math.max(p_i3871_4_.getXSize(), p_i3871_4_.getZSize());
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		int var4 = getBiomeSpecificBlock(Block.gravel.blockID, 0);
		for(int var5 = boundingBox.minX; var5 <= boundingBox.maxX; ++var5)
		{
			for(int var6 = boundingBox.minZ; var6 <= boundingBox.maxZ; ++var6)
			{
				if(p_74875_3_.isVecInside(var5, 64, var6))
				{
					int var7 = p_74875_1_.getTopSolidOrLiquidBlock(var5, var6) - 1;
					p_74875_1_.setBlock(var5, var7, var6, var4, 0, 2);
				}
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		boolean var4 = false;
		int var5;
		StructureComponent var6;
		for(var5 = p_74861_3_.nextInt(5); var5 < averageGroundLevel - 8; var5 += 2 + p_74861_3_.nextInt(5))
		{
			var6 = getNextComponentNN((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
			if(var6 != null)
			{
				var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
				var4 = true;
			}
		}
		for(var5 = p_74861_3_.nextInt(5); var5 < averageGroundLevel - 8; var5 += 2 + p_74861_3_.nextInt(5))
		{
			var6 = getNextComponentPP((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, 0, var5);
			if(var6 != null)
			{
				var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
				var4 = true;
			}
		}
		if(var4 && p_74861_3_.nextInt(3) > 0)
		{
			switch(coordBaseMode)
			{
				case 0:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, boundingBox.maxZ - 2, 1, getComponentType());
					break;
				case 1:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
					break;
				case 2:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, getComponentType());
					break;
				case 3:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX - 2, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
			}
		}
		if(var4 && p_74861_3_.nextInt(3) > 0)
		{
			switch(coordBaseMode)
			{
				case 0:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, boundingBox.maxZ - 2, 3, getComponentType());
					break;
				case 1:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
					break;
				case 2:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, getComponentType());
					break;
				case 3:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX - 2, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
			}
		}
	}
	
	public static StructureBoundingBox func_74933_a(ComponentVillageStartPiece p_74933_0_, List p_74933_1_, Random p_74933_2_, int p_74933_3_, int p_74933_4_, int p_74933_5_, int p_74933_6_)
	{
		for(int var7 = 7 * MathHelper.getRandomIntegerInRange(p_74933_2_, 3, 5); var7 >= 7; var7 -= 7)
		{
			StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(p_74933_3_, p_74933_4_, p_74933_5_, 0, 0, 0, 3, 3, var7, p_74933_6_);
			if(StructureComponent.findIntersecting(p_74933_1_, var8) == null) return var8;
		}
		return null;
	}
}
