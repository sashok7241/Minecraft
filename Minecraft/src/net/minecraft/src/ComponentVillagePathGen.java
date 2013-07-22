package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillagePathGen extends ComponentVillageRoadPiece
{
	private int averageGroundLevel;
	
	public ComponentVillagePathGen(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
	{
		super(par1ComponentVillageStartPiece, par2);
		coordBaseMode = par5;
		boundingBox = par4StructureBoundingBox;
		averageGroundLevel = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		int var4 = getBiomeSpecificBlock(Block.gravel.blockID, 0);
		for(int var5 = boundingBox.minX; var5 <= boundingBox.maxX; ++var5)
		{
			for(int var6 = boundingBox.minZ; var6 <= boundingBox.maxZ; ++var6)
			{
				if(par3StructureBoundingBox.isVecInside(var5, 64, var6))
				{
					int var7 = par1World.getTopSolidOrLiquidBlock(var5, var6) - 1;
					par1World.setBlock(var5, var7, var6, var4, 0, 2);
				}
			}
		}
		return true;
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		boolean var4 = false;
		int var5;
		StructureComponent var6;
		for(var5 = par3Random.nextInt(5); var5 < averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5))
		{
			var6 = getNextComponentNN((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, 0, var5);
			if(var6 != null)
			{
				var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
				var4 = true;
			}
		}
		for(var5 = par3Random.nextInt(5); var5 < averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5))
		{
			var6 = getNextComponentPP((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, 0, var5);
			if(var6 != null)
			{
				var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
				var4 = true;
			}
		}
		if(var4 && par3Random.nextInt(3) > 0)
		{
			switch(coordBaseMode)
			{
				case 0:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.maxZ - 2, 1, getComponentType());
					break;
				case 1:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
					break;
				case 2:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, getComponentType());
					break;
				case 3:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.maxX - 2, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
			}
		}
		if(var4 && par3Random.nextInt(3) > 0)
		{
			switch(coordBaseMode)
			{
				case 0:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.maxZ - 2, 3, getComponentType());
					break;
				case 1:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
					break;
				case 2:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, getComponentType());
					break;
				case 3:
					StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece) par1StructureComponent, par2List, par3Random, boundingBox.maxX - 2, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
			}
		}
	}
	
	public static StructureBoundingBox func_74933_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
	{
		for(int var7 = 7 * MathHelper.getRandomIntegerInRange(par2Random, 3, 5); var7 >= 7; var7 -= 7)
		{
			StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 3, var7, par6);
			if(StructureComponent.findIntersecting(par1List, var8) == null) return var8;
		}
		return null;
	}
}
