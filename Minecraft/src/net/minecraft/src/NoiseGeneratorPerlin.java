package net.minecraft.src;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator
{
	private int[] permutations;
	public double xCoord;
	public double yCoord;
	public double zCoord;
	
	public NoiseGeneratorPerlin()
	{
		this(new Random());
	}
	
	public NoiseGeneratorPerlin(Random p_i3876_1_)
	{
		permutations = new int[512];
		xCoord = p_i3876_1_.nextDouble() * 256.0D;
		yCoord = p_i3876_1_.nextDouble() * 256.0D;
		zCoord = p_i3876_1_.nextDouble() * 256.0D;
		int var2;
		for(var2 = 0; var2 < 256; permutations[var2] = var2++)
		{
			;
		}
		for(var2 = 0; var2 < 256; ++var2)
		{
			int var3 = p_i3876_1_.nextInt(256 - var2) + var2;
			int var4 = permutations[var2];
			permutations[var2] = permutations[var3];
			permutations[var3] = var4;
			permutations[var2 + 256] = permutations[var2];
		}
	}
	
	public final double func_76309_a(int p_76309_1_, double p_76309_2_, double p_76309_4_)
	{
		int var6 = p_76309_1_ & 15;
		double var7 = (1 - ((var6 & 8) >> 3)) * p_76309_2_;
		double var9 = var6 < 4 ? 0.0D : var6 != 12 && var6 != 14 ? p_76309_4_ : p_76309_2_;
		return ((var6 & 1) == 0 ? var7 : -var7) + ((var6 & 2) == 0 ? var9 : -var9);
	}
	
	public final double grad(int p_76310_1_, double p_76310_2_, double p_76310_4_, double p_76310_6_)
	{
		int var8 = p_76310_1_ & 15;
		double var9 = var8 < 8 ? p_76310_2_ : p_76310_4_;
		double var11 = var8 < 4 ? p_76310_4_ : var8 != 12 && var8 != 14 ? p_76310_6_ : p_76310_2_;
		return ((var8 & 1) == 0 ? var9 : -var9) + ((var8 & 2) == 0 ? var11 : -var11);
	}
	
	public final double lerp(double p_76311_1_, double p_76311_3_, double p_76311_5_)
	{
		return p_76311_3_ + p_76311_1_ * (p_76311_5_ - p_76311_3_);
	}
	
	public void populateNoiseArray(double[] p_76308_1_, double p_76308_2_, double p_76308_4_, double p_76308_6_, int p_76308_8_, int p_76308_9_, int p_76308_10_, double p_76308_11_, double p_76308_13_, double p_76308_15_, double p_76308_17_)
	{
		int var19;
		int var22;
		double var31;
		double var35;
		double var38;
		int var37;
		double var42;
		int var40;
		int var41;
		int var10001;
		int var77;
		if(p_76308_9_ == 1)
		{
			boolean var66 = false;
			boolean var65 = false;
			boolean var21 = false;
			boolean var67 = false;
			double var72 = 0.0D;
			double var71 = 0.0D;
			var77 = 0;
			double var74 = 1.0D / p_76308_17_;
			for(int var30 = 0; var30 < p_76308_8_; ++var30)
			{
				var31 = p_76308_2_ + var30 * p_76308_11_ + xCoord;
				int var78 = (int) var31;
				if(var31 < var78)
				{
					--var78;
				}
				int var34 = var78 & 255;
				var31 -= var78;
				var35 = var31 * var31 * var31 * (var31 * (var31 * 6.0D - 15.0D) + 10.0D);
				for(var37 = 0; var37 < p_76308_10_; ++var37)
				{
					var38 = p_76308_6_ + var37 * p_76308_15_ + zCoord;
					var40 = (int) var38;
					if(var38 < var40)
					{
						--var40;
					}
					var41 = var40 & 255;
					var38 -= var40;
					var42 = var38 * var38 * var38 * (var38 * (var38 * 6.0D - 15.0D) + 10.0D);
					var19 = permutations[var34] + 0;
					int var64 = permutations[var19] + var41;
					int var69 = permutations[var34 + 1] + 0;
					var22 = permutations[var69] + var41;
					var72 = lerp(var35, func_76309_a(permutations[var64], var31, var38), grad(permutations[var22], var31 - 1.0D, 0.0D, var38));
					var71 = lerp(var35, grad(permutations[var64 + 1], var31, 0.0D, var38 - 1.0D), grad(permutations[var22 + 1], var31 - 1.0D, 0.0D, var38 - 1.0D));
					double var79 = lerp(var42, var72, var71);
					var10001 = var77++;
					p_76308_1_[var10001] += var79 * var74;
				}
			}
		} else
		{
			var19 = 0;
			double var20 = 1.0D / p_76308_17_;
			var22 = -1;
			boolean var23 = false;
			boolean var24 = false;
			boolean var25 = false;
			boolean var26 = false;
			boolean var27 = false;
			boolean var28 = false;
			double var29 = 0.0D;
			var31 = 0.0D;
			double var33 = 0.0D;
			var35 = 0.0D;
			for(var37 = 0; var37 < p_76308_8_; ++var37)
			{
				var38 = p_76308_2_ + var37 * p_76308_11_ + xCoord;
				var40 = (int) var38;
				if(var38 < var40)
				{
					--var40;
				}
				var41 = var40 & 255;
				var38 -= var40;
				var42 = var38 * var38 * var38 * (var38 * (var38 * 6.0D - 15.0D) + 10.0D);
				for(int var44 = 0; var44 < p_76308_10_; ++var44)
				{
					double var45 = p_76308_6_ + var44 * p_76308_15_ + zCoord;
					int var47 = (int) var45;
					if(var45 < var47)
					{
						--var47;
					}
					int var48 = var47 & 255;
					var45 -= var47;
					double var49 = var45 * var45 * var45 * (var45 * (var45 * 6.0D - 15.0D) + 10.0D);
					for(int var51 = 0; var51 < p_76308_9_; ++var51)
					{
						double var52 = p_76308_4_ + var51 * p_76308_13_ + yCoord;
						int var54 = (int) var52;
						if(var52 < var54)
						{
							--var54;
						}
						int var55 = var54 & 255;
						var52 -= var54;
						double var56 = var52 * var52 * var52 * (var52 * (var52 * 6.0D - 15.0D) + 10.0D);
						if(var51 == 0 || var55 != var22)
						{
							var22 = var55;
							int var68 = permutations[var41] + var55;
							int var73 = permutations[var68] + var48;
							int var70 = permutations[var68 + 1] + var48;
							int var76 = permutations[var41 + 1] + var55;
							var77 = permutations[var76] + var48;
							int var75 = permutations[var76 + 1] + var48;
							var29 = lerp(var42, grad(permutations[var73], var38, var52, var45), grad(permutations[var77], var38 - 1.0D, var52, var45));
							var31 = lerp(var42, grad(permutations[var70], var38, var52 - 1.0D, var45), grad(permutations[var75], var38 - 1.0D, var52 - 1.0D, var45));
							var33 = lerp(var42, grad(permutations[var73 + 1], var38, var52, var45 - 1.0D), grad(permutations[var77 + 1], var38 - 1.0D, var52, var45 - 1.0D));
							var35 = lerp(var42, grad(permutations[var70 + 1], var38, var52 - 1.0D, var45 - 1.0D), grad(permutations[var75 + 1], var38 - 1.0D, var52 - 1.0D, var45 - 1.0D));
						}
						double var58 = lerp(var56, var29, var31);
						double var60 = lerp(var56, var33, var35);
						double var62 = lerp(var49, var58, var60);
						var10001 = var19++;
						p_76308_1_[var10001] += var62 * var20;
					}
				}
			}
		}
	}
}
