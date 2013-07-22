package net.minecraft.src;

public class WorldProviderHell extends WorldProvider
{
	@Override public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.5F;
	}
	
	@Override public boolean canCoordinateBeSpawn(int par1, int par2)
	{
		return false;
	}
	
	@Override public boolean canRespawnHere()
	{
		return false;
	}
	
	@Override public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderHell(worldObj, worldObj.getSeed());
	}
	
	@Override public boolean doesXZShowFog(int par1, int par2)
	{
		return true;
	}
	
	@Override protected void generateLightBrightnessTable()
	{
		float var1 = 0.1F;
		for(int var2 = 0; var2 <= 15; ++var2)
		{
			float var3 = 1.0F - var2 / 15.0F;
			lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}
	}
	
	@Override public String getDimensionName()
	{
		return "Nether";
	}
	
	@Override public Vec3 getFogColor(float par1, float par2)
	{
		return worldObj.getWorldVec3Pool().getVecFromPool(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
	}
	
	@Override public boolean isSurfaceWorld()
	{
		return false;
	}
	
	@Override public void registerWorldChunkManager()
	{
		worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.hell, 1.0F, 0.0F);
		isHellWorld = true;
		hasNoSky = true;
		dimensionId = -1;
	}
}
