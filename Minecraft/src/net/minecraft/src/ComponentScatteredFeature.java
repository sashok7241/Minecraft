package net.minecraft.src;

import java.util.Random;

abstract class ComponentScatteredFeature extends StructureComponent
{
	protected final int scatteredFeatureSizeX;
	protected final int scatteredFeatureSizeY;
	protected final int scatteredFeatureSizeZ;
	protected int field_74936_d = -1;
	
	protected ComponentScatteredFeature(Random p_i3836_1_, int p_i3836_2_, int p_i3836_3_, int p_i3836_4_, int p_i3836_5_, int p_i3836_6_, int p_i3836_7_)
	{
		super(0);
		scatteredFeatureSizeX = p_i3836_5_;
		scatteredFeatureSizeY = p_i3836_6_;
		scatteredFeatureSizeZ = p_i3836_7_;
		coordBaseMode = p_i3836_1_.nextInt(4);
		switch(coordBaseMode)
		{
			case 0:
			case 2:
				boundingBox = new StructureBoundingBox(p_i3836_2_, p_i3836_3_, p_i3836_4_, p_i3836_2_ + p_i3836_5_ - 1, p_i3836_3_ + p_i3836_6_ - 1, p_i3836_4_ + p_i3836_7_ - 1);
				break;
			default:
				boundingBox = new StructureBoundingBox(p_i3836_2_, p_i3836_3_, p_i3836_4_, p_i3836_2_ + p_i3836_7_ - 1, p_i3836_3_ + p_i3836_6_ - 1, p_i3836_4_ + p_i3836_5_ - 1);
		}
	}
	
	protected boolean func_74935_a(World p_74935_1_, StructureBoundingBox p_74935_2_, int p_74935_3_)
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
					if(p_74935_2_.isVecInside(var7, 64, var6))
					{
						var4 += Math.max(p_74935_1_.getTopSolidOrLiquidBlock(var7, var6), p_74935_1_.provider.getAverageGroundLevel());
						++var5;
					}
				}
			}
			if(var5 == 0) return false;
			else
			{
				field_74936_d = var4 / var5;
				boundingBox.offset(0, field_74936_d - boundingBox.minY + p_74935_3_, 0);
				return true;
			}
		}
	}
}
