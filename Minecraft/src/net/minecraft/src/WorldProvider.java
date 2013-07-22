package net.minecraft.src;

public abstract class WorldProvider
{
	public World worldObj;
	public WorldType terrainType;
	public String field_82913_c;
	public WorldChunkManager worldChunkMgr;
	public boolean isHellWorld = false;
	public boolean hasNoSky = false;
	public float[] lightBrightnessTable = new float[16];
	public int dimensionId = 0;
	private float[] colorsSunriseSunset = new float[4];
	
	public float[] calcSunriseSunsetColors(float par1, float par2)
	{
		float var3 = 0.4F;
		float var4 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) - 0.0F;
		float var5 = -0.0F;
		if(var4 >= var5 - var3 && var4 <= var5 + var3)
		{
			float var6 = (var4 - var5) / var3 * 0.5F + 0.5F;
			float var7 = 1.0F - (1.0F - MathHelper.sin(var6 * (float) Math.PI)) * 0.99F;
			var7 *= var7;
			colorsSunriseSunset[0] = var6 * 0.3F + 0.7F;
			colorsSunriseSunset[1] = var6 * var6 * 0.7F + 0.2F;
			colorsSunriseSunset[2] = var6 * var6 * 0.0F + 0.2F;
			colorsSunriseSunset[3] = var7;
			return colorsSunriseSunset;
		} else return null;
	}
	
	public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
	{
		int var4 = (int) (p_76563_1_ % 24000L);
		float var5 = (var4 + p_76563_3_) / 24000.0F - 0.25F;
		if(var5 < 0.0F)
		{
			++var5;
		}
		if(var5 > 1.0F)
		{
			--var5;
		}
		float var6 = var5;
		var5 = 1.0F - (float) ((Math.cos(var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}
	
	public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
	{
		int var3 = worldObj.getFirstUncoveredBlock(p_76566_1_, p_76566_2_);
		return var3 == Block.grass.blockID;
	}
	
	public boolean canRespawnHere()
	{
		return true;
	}
	
	public IChunkProvider createChunkGenerator()
	{
		return terrainType == WorldType.FLAT ? new ChunkProviderFlat(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), field_82913_c) : new ChunkProviderGenerate(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
	}
	
	public boolean doesXZShowFog(int par1, int par2)
	{
		return false;
	}
	
	protected void generateLightBrightnessTable()
	{
		float var1 = 0.0F;
		for(int var2 = 0; var2 <= 15; ++var2)
		{
			float var3 = 1.0F - var2 / 15.0F;
			lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}
	}
	
	public int getAverageGroundLevel()
	{
		return terrainType == WorldType.FLAT ? 4 : 64;
	}
	
	public float getCloudHeight()
	{
		return 128.0F;
	}
	
	public abstract String getDimensionName();
	
	public ChunkCoordinates getEntrancePortalLocation()
	{
		return null;
	}
	
	public Vec3 getFogColor(float par1, float par2)
	{
		float var3 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var3 < 0.0F)
		{
			var3 = 0.0F;
		}
		if(var3 > 1.0F)
		{
			var3 = 1.0F;
		}
		float var4 = 0.7529412F;
		float var5 = 0.84705883F;
		float var6 = 1.0F;
		var4 *= var3 * 0.94F + 0.06F;
		var5 *= var3 * 0.94F + 0.06F;
		var6 *= var3 * 0.91F + 0.09F;
		return worldObj.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
	}
	
	public int getMoonPhase(long p_76559_1_)
	{
		return (int) (p_76559_1_ / 24000L) % 8;
	}
	
	public double getVoidFogYFactor()
	{
		return terrainType == WorldType.FLAT ? 1.0D : 0.03125D;
	}
	
	public boolean getWorldHasVoidParticles()
	{
		return terrainType != WorldType.FLAT && !hasNoSky;
	}
	
	public boolean isSkyColored()
	{
		return true;
	}
	
	public boolean isSurfaceWorld()
	{
		return true;
	}
	
	public final void registerWorld(World p_76558_1_)
	{
		worldObj = p_76558_1_;
		terrainType = p_76558_1_.getWorldInfo().getTerrainType();
		field_82913_c = p_76558_1_.getWorldInfo().getGeneratorOptions();
		registerWorldChunkManager();
		generateLightBrightnessTable();
	}
	
	protected void registerWorldChunkManager()
	{
		if(worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT)
		{
			FlatGeneratorInfo var1 = FlatGeneratorInfo.createFlatGeneratorFromString(worldObj.getWorldInfo().getGeneratorOptions());
			worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.biomeList[var1.getBiome()], 0.5F, 0.5F);
		} else
		{
			worldChunkMgr = new WorldChunkManager(worldObj);
		}
	}
	
	public static WorldProvider getProviderForDimension(int p_76570_0_)
	{
		return p_76570_0_ == -1 ? new WorldProviderHell() : p_76570_0_ == 0 ? new WorldProviderSurface() : p_76570_0_ == 1 ? new WorldProviderEnd() : null;
	}
}
