package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ChunkProviderEnd implements IChunkProvider
{
	private Random endRNG;
	private NoiseGeneratorOctaves noiseGen1;
	private NoiseGeneratorOctaves noiseGen2;
	private NoiseGeneratorOctaves noiseGen3;
	public NoiseGeneratorOctaves noiseGen4;
	public NoiseGeneratorOctaves noiseGen5;
	private World endWorld;
	private double[] densities;
	private BiomeGenBase[] biomesForGeneration;
	double[] noiseData1;
	double[] noiseData2;
	double[] noiseData3;
	double[] noiseData4;
	double[] noiseData5;
	int[][] field_73203_h = new int[32][32];
	
	public ChunkProviderEnd(World p_i3783_1_, long p_i3783_2_)
	{
		endWorld = p_i3783_1_;
		endRNG = new Random(p_i3783_2_);
		noiseGen1 = new NoiseGeneratorOctaves(endRNG, 16);
		noiseGen2 = new NoiseGeneratorOctaves(endRNG, 16);
		noiseGen3 = new NoiseGeneratorOctaves(endRNG, 8);
		noiseGen4 = new NoiseGeneratorOctaves(endRNG, 10);
		noiseGen5 = new NoiseGeneratorOctaves(endRNG, 16);
	}
	
	@Override public boolean canSave()
	{
		return true;
	}
	
	@Override public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}
	
	@Override public ChunkPosition findClosestStructure(World p_73150_1_, String p_73150_2_, int p_73150_3_, int p_73150_4_, int p_73150_5_)
	{
		return null;
	}
	
	@Override public void func_104112_b()
	{
	}
	
	public void generateTerrain(int p_73189_1_, int p_73189_2_, byte[] p_73189_3_, BiomeGenBase[] p_73189_4_)
	{
		byte var5 = 2;
		int var6 = var5 + 1;
		byte var7 = 33;
		int var8 = var5 + 1;
		densities = initializeNoiseField(densities, p_73189_1_ * var5, 0, p_73189_2_ * var5, var6, var7, var8);
		for(int var9 = 0; var9 < var5; ++var9)
		{
			for(int var10 = 0; var10 < var5; ++var10)
			{
				for(int var11 = 0; var11 < 32; ++var11)
				{
					double var12 = 0.25D;
					double var14 = densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
					double var16 = densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
					double var18 = densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
					double var20 = densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
					double var22 = (densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var14) * var12;
					double var24 = (densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
					double var26 = (densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var18) * var12;
					double var28 = (densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var20) * var12;
					for(int var30 = 0; var30 < 4; ++var30)
					{
						double var31 = 0.125D;
						double var33 = var14;
						double var35 = var16;
						double var37 = (var18 - var14) * var31;
						double var39 = (var20 - var16) * var31;
						for(int var41 = 0; var41 < 8; ++var41)
						{
							int var42 = var41 + var9 * 8 << 11 | 0 + var10 * 8 << 7 | var11 * 4 + var30;
							short var43 = 128;
							double var44 = 0.125D;
							double var46 = var33;
							double var48 = (var35 - var33) * var44;
							for(int var50 = 0; var50 < 8; ++var50)
							{
								int var51 = 0;
								if(var46 > 0.0D)
								{
									var51 = Block.whiteStone.blockID;
								}
								p_73189_3_[var42] = (byte) var51;
								var42 += var43;
								var46 += var48;
							}
							var33 += var37;
							var35 += var39;
						}
						var14 += var22;
						var16 += var24;
						var18 += var26;
						var20 += var28;
					}
				}
			}
		}
	}
	
	@Override public int getLoadedChunkCount()
	{
		return 0;
	}
	
	@Override public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
	{
		BiomeGenBase var5 = endWorld.getBiomeGenForCoords(p_73155_2_, p_73155_4_);
		return var5 == null ? null : var5.getSpawnableList(p_73155_1_);
	}
	
	private double[] initializeNoiseField(double[] p_73187_1_, int p_73187_2_, int p_73187_3_, int p_73187_4_, int p_73187_5_, int p_73187_6_, int p_73187_7_)
	{
		if(p_73187_1_ == null)
		{
			p_73187_1_ = new double[p_73187_5_ * p_73187_6_ * p_73187_7_];
		}
		double var8 = 684.412D;
		double var10 = 684.412D;
		noiseData4 = noiseGen4.generateNoiseOctaves(noiseData4, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 1.121D, 1.121D, 0.5D);
		noiseData5 = noiseGen5.generateNoiseOctaves(noiseData5, p_73187_2_, p_73187_4_, p_73187_5_, p_73187_7_, 200.0D, 200.0D, 0.5D);
		var8 *= 2.0D;
		noiseData1 = noiseGen3.generateNoiseOctaves(noiseData1, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, var8 / 80.0D, var10 / 160.0D, var8 / 80.0D);
		noiseData2 = noiseGen1.generateNoiseOctaves(noiseData2, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, var8, var10, var8);
		noiseData3 = noiseGen2.generateNoiseOctaves(noiseData3, p_73187_2_, p_73187_3_, p_73187_4_, p_73187_5_, p_73187_6_, p_73187_7_, var8, var10, var8);
		int var12 = 0;
		int var13 = 0;
		for(int var14 = 0; var14 < p_73187_5_; ++var14)
		{
			for(int var15 = 0; var15 < p_73187_7_; ++var15)
			{
				double var16 = (noiseData4[var13] + 256.0D) / 512.0D;
				if(var16 > 1.0D)
				{
					var16 = 1.0D;
				}
				double var18 = noiseData5[var13] / 8000.0D;
				if(var18 < 0.0D)
				{
					var18 = -var18 * 0.3D;
				}
				var18 = var18 * 3.0D - 2.0D;
				float var20 = (var14 + p_73187_2_ - 0) / 1.0F;
				float var21 = (var15 + p_73187_4_ - 0) / 1.0F;
				float var22 = 100.0F - MathHelper.sqrt_float(var20 * var20 + var21 * var21) * 8.0F;
				if(var22 > 80.0F)
				{
					var22 = 80.0F;
				}
				if(var22 < -100.0F)
				{
					var22 = -100.0F;
				}
				if(var18 > 1.0D)
				{
					var18 = 1.0D;
				}
				var18 /= 8.0D;
				var18 = 0.0D;
				if(var16 < 0.0D)
				{
					var16 = 0.0D;
				}
				var16 += 0.5D;
				var18 = var18 * p_73187_6_ / 16.0D;
				++var13;
				double var23 = p_73187_6_ / 2.0D;
				for(int var25 = 0; var25 < p_73187_6_; ++var25)
				{
					double var26 = 0.0D;
					double var28 = (var25 - var23) * 8.0D / var16;
					if(var28 < 0.0D)
					{
						var28 *= -1.0D;
					}
					double var30 = noiseData2[var12] / 512.0D;
					double var32 = noiseData3[var12] / 512.0D;
					double var34 = (noiseData1[var12] / 10.0D + 1.0D) / 2.0D;
					if(var34 < 0.0D)
					{
						var26 = var30;
					} else if(var34 > 1.0D)
					{
						var26 = var32;
					} else
					{
						var26 = var30 + (var32 - var30) * var34;
					}
					var26 -= 8.0D;
					var26 += var22;
					byte var36 = 2;
					double var37;
					if(var25 > p_73187_6_ / 2 - var36)
					{
						var37 = (var25 - (p_73187_6_ / 2 - var36)) / 64.0F;
						if(var37 < 0.0D)
						{
							var37 = 0.0D;
						}
						if(var37 > 1.0D)
						{
							var37 = 1.0D;
						}
						var26 = var26 * (1.0D - var37) + -3000.0D * var37;
					}
					var36 = 8;
					if(var25 < var36)
					{
						var37 = (var36 - var25) / (var36 - 1.0F);
						var26 = var26 * (1.0D - var37) + -30.0D * var37;
					}
					p_73187_1_[var12] = var26;
					++var12;
				}
			}
		}
		return p_73187_1_;
	}
	
	@Override public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		return provideChunk(p_73158_1_, p_73158_2_);
	}
	
	@Override public String makeString()
	{
		return "RandomLevelSource";
	}
	
	@Override public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		BlockSand.fallInstantly = true;
		int var4 = p_73153_2_ * 16;
		int var5 = p_73153_3_ * 16;
		BiomeGenBase var6 = endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);
		var6.decorate(endWorld, endWorld.rand, var4, var5);
		BlockSand.fallInstantly = false;
	}
	
	@Override public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		endRNG.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
		byte[] var3 = new byte[32768];
		biomesForGeneration = endWorld.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
		generateTerrain(p_73154_1_, p_73154_2_, var3, biomesForGeneration);
		replaceBlocksForBiome(p_73154_1_, p_73154_2_, var3, biomesForGeneration);
		Chunk var4 = new Chunk(endWorld, var3, p_73154_1_, p_73154_2_);
		byte[] var5 = var4.getBiomeArray();
		for(int var6 = 0; var6 < var5.length; ++var6)
		{
			var5[var6] = (byte) biomesForGeneration[var6].biomeID;
		}
		var4.generateSkylightMap();
		return var4;
	}
	
	@Override public void recreateStructures(int p_82695_1_, int p_82695_2_)
	{
	}
	
	public void replaceBlocksForBiome(int p_73188_1_, int p_73188_2_, byte[] p_73188_3_, BiomeGenBase[] p_73188_4_)
	{
		for(int var5 = 0; var5 < 16; ++var5)
		{
			for(int var6 = 0; var6 < 16; ++var6)
			{
				byte var7 = 1;
				int var8 = -1;
				byte var9 = (byte) Block.whiteStone.blockID;
				byte var10 = (byte) Block.whiteStone.blockID;
				for(int var11 = 127; var11 >= 0; --var11)
				{
					int var12 = (var6 * 16 + var5) * 128 + var11;
					byte var13 = p_73188_3_[var12];
					if(var13 == 0)
					{
						var8 = -1;
					} else if(var13 == Block.stone.blockID)
					{
						if(var8 == -1)
						{
							if(var7 <= 0)
							{
								var9 = 0;
								var10 = (byte) Block.whiteStone.blockID;
							}
							var8 = var7;
							if(var11 >= 0)
							{
								p_73188_3_[var12] = var9;
							} else
							{
								p_73188_3_[var12] = var10;
							}
						} else if(var8 > 0)
						{
							--var8;
							p_73188_3_[var12] = var10;
						}
					}
				}
			}
		}
	}
	
	@Override public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		return true;
	}
	
	@Override public boolean unloadQueuedChunks()
	{
		return false;
	}
}
