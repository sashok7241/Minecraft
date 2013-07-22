package net.minecraft.src;

public class WorldProviderEnd extends WorldProvider
{
	@Override public float[] calcSunriseSunsetColors(float par1, float par2)
	{
		return null;
	}
	
	@Override public float calculateCelestialAngle(long p_76563_1_, float p_76563_3_)
	{
		return 0.0F;
	}
	
	@Override public boolean canCoordinateBeSpawn(int p_76566_1_, int p_76566_2_)
	{
		int var3 = worldObj.getFirstUncoveredBlock(p_76566_1_, p_76566_2_);
		return var3 == 0 ? false : Block.blocksList[var3].blockMaterial.blocksMovement();
	}
	
	@Override public boolean canRespawnHere()
	{
		return false;
	}
	
	@Override public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderEnd(worldObj, worldObj.getSeed());
	}
	
	@Override public boolean doesXZShowFog(int par1, int par2)
	{
		return true;
	}
	
	@Override public int getAverageGroundLevel()
	{
		return 50;
	}
	
	@Override public float getCloudHeight()
	{
		return 8.0F;
	}
	
	@Override public String getDimensionName()
	{
		return "The End";
	}
	
	@Override public ChunkCoordinates getEntrancePortalLocation()
	{
		return new ChunkCoordinates(100, 50, 0);
	}
	
	@Override public Vec3 getFogColor(float par1, float par2)
	{
		int var3 = 10518688;
		float var4 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;
		if(var4 < 0.0F)
		{
			var4 = 0.0F;
		}
		if(var4 > 1.0F)
		{
			var4 = 1.0F;
		}
		float var5 = (var3 >> 16 & 255) / 255.0F;
		float var6 = (var3 >> 8 & 255) / 255.0F;
		float var7 = (var3 & 255) / 255.0F;
		var5 *= var4 * 0.0F + 0.15F;
		var6 *= var4 * 0.0F + 0.15F;
		var7 *= var4 * 0.0F + 0.15F;
		return worldObj.getWorldVec3Pool().getVecFromPool(var5, var6, var7);
	}
	
	@Override public boolean isSkyColored()
	{
		return false;
	}
	
	@Override public boolean isSurfaceWorld()
	{
		return false;
	}
	
	@Override public void registerWorldChunkManager()
	{
		worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5F, 0.0F);
		dimensionId = 1;
		hasNoSky = true;
	}
}
