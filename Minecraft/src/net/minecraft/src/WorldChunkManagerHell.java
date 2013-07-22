package net.minecraft.src;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WorldChunkManagerHell extends WorldChunkManager
{
	private BiomeGenBase biomeGenerator;
	private float hellTemperature;
	private float rainfall;
	
	public WorldChunkManagerHell(BiomeGenBase p_i3755_1_, float p_i3755_2_, float p_i3755_3_)
	{
		biomeGenerator = p_i3755_1_;
		hellTemperature = p_i3755_2_;
		rainfall = p_i3755_3_;
	}
	
	@Override public boolean areBiomesViable(int p_76940_1_, int p_76940_2_, int p_76940_3_, List p_76940_4_)
	{
		return p_76940_4_.contains(biomeGenerator);
	}
	
	@Override public ChunkPosition findBiomePosition(int p_76941_1_, int p_76941_2_, int p_76941_3_, List p_76941_4_, Random p_76941_5_)
	{
		return p_76941_4_.contains(biomeGenerator) ? new ChunkPosition(p_76941_1_ - p_76941_3_ + p_76941_5_.nextInt(p_76941_3_ * 2 + 1), 0, p_76941_2_ - p_76941_3_ + p_76941_5_.nextInt(p_76941_3_ * 2 + 1)) : null;
	}
	
	@Override public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, int p_76931_2_, int p_76931_3_, int p_76931_4_, int p_76931_5_, boolean p_76931_6_)
	{
		return loadBlockGeneratorData(p_76931_1_, p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);
	}
	
	@Override public BiomeGenBase getBiomeGenAt(int p_76935_1_, int p_76935_2_)
	{
		return biomeGenerator;
	}
	
	@Override public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] p_76937_1_, int p_76937_2_, int p_76937_3_, int p_76937_4_, int p_76937_5_)
	{
		if(p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_)
		{
			p_76937_1_ = new BiomeGenBase[p_76937_4_ * p_76937_5_];
		}
		Arrays.fill(p_76937_1_, 0, p_76937_4_ * p_76937_5_, biomeGenerator);
		return p_76937_1_;
	}
	
	@Override public float[] getRainfall(float[] p_76936_1_, int p_76936_2_, int p_76936_3_, int p_76936_4_, int p_76936_5_)
	{
		if(p_76936_1_ == null || p_76936_1_.length < p_76936_4_ * p_76936_5_)
		{
			p_76936_1_ = new float[p_76936_4_ * p_76936_5_];
		}
		Arrays.fill(p_76936_1_, 0, p_76936_4_ * p_76936_5_, rainfall);
		return p_76936_1_;
	}
	
	@Override public float[] getTemperatures(float[] p_76934_1_, int p_76934_2_, int p_76934_3_, int p_76934_4_, int p_76934_5_)
	{
		if(p_76934_1_ == null || p_76934_1_.length < p_76934_4_ * p_76934_5_)
		{
			p_76934_1_ = new float[p_76934_4_ * p_76934_5_];
		}
		Arrays.fill(p_76934_1_, 0, p_76934_4_ * p_76934_5_, hellTemperature);
		return p_76934_1_;
	}
	
	@Override public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] p_76933_1_, int p_76933_2_, int p_76933_3_, int p_76933_4_, int p_76933_5_)
	{
		if(p_76933_1_ == null || p_76933_1_.length < p_76933_4_ * p_76933_5_)
		{
			p_76933_1_ = new BiomeGenBase[p_76933_4_ * p_76933_5_];
		}
		Arrays.fill(p_76933_1_, 0, p_76933_4_ * p_76933_5_, biomeGenerator);
		return p_76933_1_;
	}
}
