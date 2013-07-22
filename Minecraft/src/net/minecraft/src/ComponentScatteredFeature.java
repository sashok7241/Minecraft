package net.minecraft.src;

import java.util.Random;

abstract class ComponentScatteredFeature extends StructureComponent
{
	protected final int scatteredFeatureSizeX;
	protected final int scatteredFeatureSizeY;
	protected final int scatteredFeatureSizeZ;
	protected int field_74936_d = -1;
	
	protected ComponentScatteredFeature(Random par1Random, int par2, int par3, int par4, int par5, int par6, int par7)
	{
		super(0);
		scatteredFeatureSizeX = par5;
		scatteredFeatureSizeY = par6;
		scatteredFeatureSizeZ = par7;
		coordBaseMode = par1Random.nextInt(4);
		switch(coordBaseMode)
		{
			case 0:
			case 2:
				boundingBox = new StructureBoundingBox(par2, par3, par4, par2 + par5 - 1, par3 + par6 - 1, par4 + par7 - 1);
				break;
			default:
				boundingBox = new StructureBoundingBox(par2, par3, par4, par2 + par7 - 1, par3 + par6 - 1, par4 + par5 - 1);
		}
	}
	
	protected boolean func_74935_a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3)
	{
		if(field_74936_d >= 0) return true;
		else
		{
			int var4 = 0;
			int var5 = 0;
			for(int var6 = boundingBox.minZ; var6 <= boundingBox.maxZ; ++var6)
			{
				for(int var7 = boundingBox.minX; var7 <= boundingBox.maxX; ++var7)
				{
					if(par2StructureBoundingBox.isVecInside(var7, 64, var6))
					{
						var4 += Math.max(par1World.getTopSolidOrLiquidBlock(var7, var6), par1World.provider.getAverageGroundLevel());
						++var5;
					}
				}
			}
			if(var5 == 0) return false;
			else
			{
				field_74936_d = var4 / var5;
				boundingBox.offset(0, field_74936_d - boundingBox.minY + par3, 0);
				return true;
			}
		}
	}
}
