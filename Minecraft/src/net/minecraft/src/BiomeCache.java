package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class BiomeCache
{
	private final WorldChunkManager chunkManager;
	private long lastCleanupTime;
	private LongHashMap cacheMap = new LongHashMap();
	private List cache = new ArrayList();
	
	public BiomeCache(WorldChunkManager par1WorldChunkManager)
	{
		chunkManager = par1WorldChunkManager;
	}
	
	public void cleanupCache()
	{
		long var1 = MinecraftServer.func_130071_aq();
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
	
	public BiomeCacheBlock getBiomeCacheBlock(int par1, int par2)
	{
		par1 >>= 4;
		par2 >>= 4;
		long var3 = par1 & 4294967295L | (par2 & 4294967295L) << 32;
		BiomeCacheBlock var5 = (BiomeCacheBlock) cacheMap.getValueByKey(var3);
		if(var5 == null)
		{
			var5 = new BiomeCacheBlock(this, par1, par2);
			cacheMap.add(var3, var5);
			cache.add(var5);
		}
		var5.lastAccessTime = MinecraftServer.func_130071_aq();
		return var5;
	}
	
	public BiomeGenBase getBiomeGenAt(int par1, int par2)
	{
		return getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
	}
	
	public BiomeGenBase[] getCachedBiomes(int par1, int par2)
	{
		return getBiomeCacheBlock(par1, par2).biomes;
	}
	
	static WorldChunkManager getChunkManager(BiomeCache par0BiomeCache)
	{
		return par0BiomeCache.chunkManager;
	}
}
