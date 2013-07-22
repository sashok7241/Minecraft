package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldChunkManager
{
	private GenLayer genBiomes;
	private GenLayer biomeIndexLayer;
	private BiomeCache biomeCache;
	private List biomesToSpawnIn;
	
	protected WorldChunkManager()
	{
		biomeCache = new BiomeCache(this);
		biomesToSpawnIn = new ArrayList();
		biomesToSpawnIn.add(BiomeGenBase.forest);
		biomesToSpawnIn.add(BiomeGenBase.plains);
		biomesToSpawnIn.add(BiomeGenBase.taiga);
		biomesToSpawnIn.add(BiomeGenBase.taigaHills);
		biomesToSpawnIn.add(BiomeGenBase.forestHills);
		biomesToSpawnIn.add(BiomeGenBase.jungle);
		biomesToSpawnIn.add(BiomeGenBase.jungleHills);
	}
	
	public WorldChunkManager(long p_i3751_1_, WorldType p_i3751_3_)
	{
		this();
		GenLayer[] var4 = GenLayer.initializeAllBiomeGenerators(p_i3751_1_, p_i3751_3_);
		genBiomes = var4[0];
		biomeIndexLayer = var4[1];
	}
	
	public WorldChunkManager(World p_i3752_1_)
	{
		this(p_i3752_1_.getSeed(), p_i3752_1_.getWorldInfo().getTerrainType());
	}
	
	public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_)
	{
		IntCache.resetIntCache();
		int var5 = p_76940_1_ - p_76940_3_ >> 2;
		int var6 = p_76940_2_ - p_76940_3_ >> 2;
		int var7 = p_76940_1_ + p_76940_3_ >> 2;
		int var8 = p_76940_2_ + p_76940_3_ >> 2;
		int var9 = var7 - var5 + 1;
		int var10 = var8 - var6 + 1;
		int[] var11 = genBiomes.getInts(var5, var6, var9, var10);
		for(int var12 = 0; var12 < var9 * var10; ++var12)
		{
			BiomeGenBase var13 = BiomeGenBase.biomeList[var11[var12]];
			if(!p_76940_4_.contains(var13)) return false;
		}
		return true;
	}
	
	public void cleanupCache()
	{
		biomeCache.cleanupCache();
	}
	
	public ChunkPosition findBiomePosition(int p_76941_1_, int p_76941_2_, int p_76941_3_, List p_76941_4_, Random p_76941_5_)
	{
		IntCache.resetIntCache();
		int var6 = p_76941_1_ - p_76941_3_ >> 2;
		int var7 = p_76941_2_ - p_76941_3_ >> 2;
		int var8 = p_76941_1_ + p_76941_3_ >> 2;
		int var9 = p_76941_2_ + p_76941_3_ >> 2;
		int var10 = var8 - var6 + 1;
		int var11 = var9 - var7 + 1;
		int[] var12 = genBiomes.getInts(var6, var7, var10, var11);
		ChunkPosition var13 = null;
		int var14 = 0;
		for(int var15 = 0; var15 < var10 * var11; ++var15)
		{
			int var16 = var6 + var15 % var10 << 2;
			int var17 = var7 + var15 / var10 << 2;
			BiomeGenBase var18 = BiomeGenBase.biomeList[var12[var15]];
			if(p_76941_4_.contains(var18) && (var13 == null || p_76941_5_.nextInt(var14 + 1) == 0))
			{
				var13 = new ChunkPosition(var16, 0, var17);
				++var14;
			}
		}
		return var13;
	}
	
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, int p_76931_2_, int p_76931_3_, int p_76931_4_, int p_76931_5_, boolean p_76931_6_)
	{
		IntCache.resetIntCache();
		if(p_76931_1_ == null || p_76931_1_.length < p_76931_4_ * p_76931_5_)
		{
			p_76931_1_ = new BiomeGenBase[p_76931_4_ * p_76931_5_];
		}
		if(p_76931_6_ && p_76931_4_ == 16 && p_76931_5_ == 16 && (p_76931_2_ & 15) == 0 && (p_76931_3_ & 15) == 0)
		{
			BiomeGenBase[] var9 = biomeCache.getCachedBiomes(p_76931_2_, p_76931_3_);
			System.arraycopy(var9, 0, p_76931_1_, 0, p_76931_4_ * p_76931_5_);
			return p_76931_1_;
		} else
		{
			int[] var7 = biomeIndexLayer.getInts(p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);
			for(int var8 = 0; var8 < p_76931_4_ * p_76931_5_; ++var8)
			{
				p_76931_1_[var8] = BiomeGenBase.biomeList[var7[var8]];
			}
			return p_76931_1_;
		}
	}
	
	public BiomeGenBase getBiomeGenAt(int p_76935_1_, int p_76935_2_)
	{
		return biomeCache.getBiomeGenAt(p_76935_1_, p_76935_2_);
	}
	
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, int p_76937_2_, int p_76937_3_, int p_76937_4_, int p_76937_5_)
	{
		IntCache.resetIntCache();
		if(p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_)
		{
			p_76937_1_ = new BiomeGenBase[p_76937_4_ * p_76937_5_];
		}
		int[] var6 = genBiomes.getInts(p_76937_2_, p_76937_3_, p_76937_4_, p_76937_5_);
		for(int var7 = 0; var7 < p_76937_4_ * p_76937_5_; ++var7)
		{
			p_76937_1_[var7] = BiomeGenBase.biomeList[var6[var7]];
		}
		return p_76937_1_;
	}
	
	public List getBiomesToSpawnIn()
	{
		return biomesToSpawnIn;
	}
	
	public float[] getRainfall(float[] p_76936_1_, int p_76936_2_, int p_76936_3_, int p_76936_4_, int p_76936_5_)
	{
		IntCache.resetIntCache();
		if(p_76936_1_ == null || p_76936_1_.length < p_76936_4_ * p_76936_5_)
		{
			p_76936_1_ = new float[p_76936_4_ * p_76936_5_];
		}
		int[] var6 = biomeIndexLayer.getInts(p_76936_2_, p_76936_3_, p_76936_4_, p_76936_5_);
		for(int var7 = 0; var7 < p_76936_4_ * p_76936_5_; ++var7)
		{
			float var8 = BiomeGenBase.biomeList[var6[var7]].getIntRainfall() / 65536.0F;
			if(var8 > 1.0F)
			{
				var8 = 1.0F;
			}
			p_76936_1_[var7] = var8;
		}
		return p_76936_1_;
	}
	
	public float getTemperatureAtHeight(float par1, int par2)
	{
		return par1;
	}
	
	public float[] getTemperatures(float[] p_76934_1_, int p_76934_2_, int p_76934_3_, int p_76934_4_, int p_76934_5_)
	{
		IntCache.resetIntCache();
		if(p_76934_1_ == null || p_76934_1_.length < p_76934_4_ * p_76934_5_)
		{
			p_76934_1_ = new float[p_76934_4_ * p_76934_5_];
		}
		int[] var6 = biomeIndexLayer.getInts(p_76934_2_, p_76934_3_, p_76934_4_, p_76934_5_);
		for(int var7 = 0; var7 < p_76934_4_ * p_76934_5_; ++var7)
		{
			float var8 = BiomeGenBase.biomeList[var6[var7]].getIntTemperature() / 65536.0F;
			if(var8 > 1.0F)
			{
				var8 = 1.0F;
			}
			p_76934_1_[var7] = var8;
		}
		return p_76934_1_;
	}
	
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] p_76933_1_, int p_76933_2_, int p_76933_3_, int p_76933_4_, int p_76933_5_)
	{
		return this.getBiomeGenAt(p_76933_1_, p_76933_2_, p_76933_3_, p_76933_4_, p_76933_5_, true);
	}
}
