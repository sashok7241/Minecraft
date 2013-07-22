package net.minecraft.src;

public class BiomeCacheBlock
{
	public float[] temperatureValues;
	public float[] rainfallValues;
	public BiomeGenBase[] biomes;
	public int xPosition;
	public int zPosition;
	public long lastAccessTime;
	final BiomeCache theBiomeCache;
	
	public BiomeCacheBlock(BiomeCache p_i3748_1_, int p_i3748_2_, int p_i3748_3_)
	{
		theBiomeCache = p_i3748_1_;
		temperatureValues = new float[256];
		rainfallValues = new float[256];
		biomes = new BiomeGenBase[256];
		xPosition = p_i3748_2_;
		zPosition = p_i3748_3_;
		BiomeCache.getChunkManager(p_i3748_1_).getTemperatures(temperatureValues, p_i3748_2_ << 4, p_i3748_3_ << 4, 16, 16);
		BiomeCache.getChunkManager(p_i3748_1_).getRainfall(rainfallValues, p_i3748_2_ << 4, p_i3748_3_ << 4, 16, 16);
		BiomeCache.getChunkManager(p_i3748_1_).getBiomeGenAt(biomes, p_i3748_2_ << 4, p_i3748_3_ << 4, 16, 16, false);
	}
	
	public BiomeGenBase getBiomeGenAt(int p_76885_1_, int p_76885_2_)
	{
		return biomes[p_76885_1_ & 15 | (p_76885_2_ & 15) << 4];
	}
}
