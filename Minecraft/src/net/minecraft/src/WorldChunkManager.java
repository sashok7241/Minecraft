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
	
	public WorldChunkManager(long par1, WorldType par3WorldType)
	{
		this();
		GenLayer[] var4 = GenLayer.initializeAllBiomeGenerators(par1, par3WorldType);
		genBiomes = var4[0];
		biomeIndexLayer = var4[1];
	}
	
	public WorldChunkManager(World par1World)
	{
		this(par1World.getSeed(), par1World.getWorldInfo().getTerrainType());
	}
	
	public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
	{
		IntCache.resetIntCache();
		int var5 = par1 - par3 >> 2;
		int var6 = par2 - par3 >> 2;
		int var7 = par1 + par3 >> 2;
		int var8 = par2 + par3 >> 2;
		int var9 = var7 - var5 + 1;
		int var10 = var8 - var6 + 1;
		int[] var11 = genBiomes.getInts(var5, var6, var9, var10);
		for(int var12 = 0; var12 < var9 * var10; ++var12)
		{
			BiomeGenBase var13 = BiomeGenBase.biomeList[var11[var12]];
			if(!par4List.contains(var13)) return false;
		}
		return true;
	}
	
	public void cleanupCache()
	{
		biomeCache.cleanupCache();
	}
	
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
	{
		IntCache.resetIntCache();
		int var6 = par1 - par3 >> 2;
		int var7 = par2 - par3 >> 2;
		int var8 = par1 + par3 >> 2;
		int var9 = par2 + par3 >> 2;
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
			if(par4List.contains(var18) && (var13 == null || par5Random.nextInt(var14 + 1) == 0))
			{
				var13 = new ChunkPosition(var16, 0, var17);
				++var14;
			}
		}
		return var13;
	}
	
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
	{
		IntCache.resetIntCache();
		if(par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}
		if(par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			BiomeGenBase[] var9 = biomeCache.getCachedBiomes(par2, par3);
			System.arraycopy(var9, 0, par1ArrayOfBiomeGenBase, 0, par4 * par5);
			return par1ArrayOfBiomeGenBase;
		} else
		{
			int[] var7 = biomeIndexLayer.getInts(par2, par3, par4, par5);
			for(int var8 = 0; var8 < par4 * par5; ++var8)
			{
				par1ArrayOfBiomeGenBase[var8] = BiomeGenBase.biomeList[var7[var8]];
			}
			return par1ArrayOfBiomeGenBase;
		}
	}
	
	public BiomeGenBase getBiomeGenAt(int par1, int par2)
	{
		return biomeCache.getBiomeGenAt(par1, par2);
	}
	
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();
		if(par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
		{
			par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
		}
		int[] var6 = genBiomes.getInts(par2, par3, par4, par5);
		for(int var7 = 0; var7 < par4 * par5; ++var7)
		{
			par1ArrayOfBiomeGenBase[var7] = BiomeGenBase.biomeList[var6[var7]];
		}
		return par1ArrayOfBiomeGenBase;
	}
	
	public List getBiomesToSpawnIn()
	{
		return biomesToSpawnIn;
	}
	
	public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();
		if(par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
		{
			par1ArrayOfFloat = new float[par4 * par5];
		}
		int[] var6 = biomeIndexLayer.getInts(par2, par3, par4, par5);
		for(int var7 = 0; var7 < par4 * par5; ++var7)
		{
			float var8 = BiomeGenBase.biomeList[var6[var7]].getIntRainfall() / 65536.0F;
			if(var8 > 1.0F)
			{
				var8 = 1.0F;
			}
			par1ArrayOfFloat[var7] = var8;
		}
		return par1ArrayOfFloat;
	}
	
	public float getTemperatureAtHeight(float par1, int par2)
	{
		return par1;
	}
	
	public float[] getTemperatures(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
	{
		IntCache.resetIntCache();
		if(par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
		{
			par1ArrayOfFloat = new float[par4 * par5];
		}
		int[] var6 = biomeIndexLayer.getInts(par2, par3, par4, par5);
		for(int var7 = 0; var7 < par4 * par5; ++var7)
		{
			float var8 = BiomeGenBase.biomeList[var6[var7]].getIntTemperature() / 65536.0F;
			if(var8 > 1.0F)
			{
				var8 = 1.0F;
			}
			par1ArrayOfFloat[var7] = var8;
		}
		return par1ArrayOfFloat;
	}
	
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
	{
		return this.getBiomeGenAt(par1ArrayOfBiomeGenBase, par2, par3, par4, par5, true);
	}
}
