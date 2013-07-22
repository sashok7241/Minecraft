package net.minecraft.src;

public interface IBlockAccess
{
	boolean doesBlockHaveSolidTopSurface(int var1, int var2, int var3);
	
	boolean extendedLevelsInChunkCache();
	
	BiomeGenBase getBiomeGenForCoords(int var1, int var2);
	
	int getBlockId(int var1, int var2, int var3);
	
	Material getBlockMaterial(int var1, int var2, int var3);
	
	int getBlockMetadata(int var1, int var2, int var3);
	
	TileEntity getBlockTileEntity(int var1, int var2, int var3);
	
	float getBrightness(int var1, int var2, int var3, int var4);
	
	int getHeight();
	
	float getLightBrightness(int var1, int var2, int var3);
	
	int getLightBrightnessForSkyBlocks(int var1, int var2, int var3, int var4);
	
	Vec3Pool getWorldVec3Pool();
	
	boolean isAirBlock(int var1, int var2, int var3);
	
	boolean isBlockNormalCube(int var1, int var2, int var3);
	
	boolean isBlockOpaqueCube(int var1, int var2, int var3);
	
	int isBlockProvidingPowerTo(int var1, int var2, int var3, int var4);
}
