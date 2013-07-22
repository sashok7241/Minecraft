package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class BiomeCache
{
	private final WorldChunkManager chunkManager;
	private long lastCleanupTime = 0L;
	private LongHashMap cacheMap = new LongHashMap();
	private List cache = new ArrayList();
	
	public BiomeCache(WorldChunkManager p_i3749_1_)
	{
		chunkManager = p_i3749_1_;
	}
	
	public void cleanupCache()
	{
		long var1 = System.currentTimeMillis();
		long var3 = var1 - lastCleanupTime;
		if(var3 > 7500L || var3 < 0L)
		{
			lastCleanupTime = var1;
			for(int var5 = 0; var5 < cache.size(); ++var5)
			{
				BiomeCacheBlock var6 = (BiomeCacheBlock) cache.get(var5);
				long var7 = var1 - var6.lastAccessTime;
				if(var7 > 30000L || var7 < 0L)
				{
					cache.remove(var5--);
					long var9 = var6.xPosition & 4294967295L | (var6.zPosition & 4294967295L) << 32;
					cacheMap.remove(var9);
				}
			}
		}
	}
	
	public BiomeCacheBlock getBiomeCacheBlock(int p_76840_1_, int p_76840_2_)
	{
		p_76840_1_ >>= 4;
		p_76840_2_ >>= 4;
		long var3 = p_76840_1_ & 4294967295L | (p_76840_2_ & 4294967295L) << 32;
		BiomeCacheBlock var5 = (BiomeCacheBlock) cacheMap.getValueByKey(var3);
		if(var5 == null)
		{
			var5 = new BiomeCacheBlock(this, p_76840_1_, p_76840_2_);
			cacheMap.add(var3, var5);
			cache.add(var5);
		}
		var5.lastAccessTime = System.currentTimeMillis();
		return var5;
	}
	
	public BiomeGenBase getBiomeGenAt(int p_76837_1_, int p_76837_2_)
	{
		return getBiomeCacheBlock(p_76837_1_, p_76837_2_).getBiomeGenAt(p_76837_1_, p_76837_2_);
	}
	
	public BiomeGenBase[] getCachedBiomes(int p_76839_1_, int p_76839_2_)
	{
		return getBiomeCacheBlock(p_76839_1_, p_76839_2_).biomes;
	}
	
	static WorldChunkManager getChunkManager(BiomeCache p_76836_0_)
	{
		return p_76836_0_.chunkManager;
	}
}
