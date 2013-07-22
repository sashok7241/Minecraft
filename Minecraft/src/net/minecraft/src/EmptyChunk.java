package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EmptyChunk extends Chunk
{
	public EmptyChunk(World par1World, int par2, int par3)
	{
		super(par1World, par2, par3);
	}
	
	@Override public void addEntity(Entity par1Entity)
	{
	}
	
	@Override public void addTileEntity(TileEntity par1TileEntity)
	{
	}
	
	@Override public boolean canBlockSeeTheSky(int par1, int par2, int par3)
	{
		return false;
	}
	
	@Override public void generateHeightMap()
	{
	}
	
	@Override public void generateSkylightMap()
	{
	}
	
	@Override public boolean getAreLevelsEmpty(int par1, int par2)
	{
		return true;
	}
	
	@Override public int getBlockID(int par1, int par2, int par3)
	{
		return 0;
	}
	
	@Override public int getBlockLightOpacity(int par1, int par2, int par3)
	{
		return 255;
	}
	
	@Override public int getBlockLightValue(int par1, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public int getBlockMetadata(int par1, int par2, int par3)
	{
		return 0;
	}
	
	@Override public TileEntity getChunkBlockTileEntity(int par1, int par2, int par3)
	{
		return null;
	}
	
	@Override public void getEntitiesOfTypeWithinAAAB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
	{
	}
	
	@Override public void getEntitiesWithinAABBForEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
	{
	}
	
	@Override public int getHeightValue(int par1, int par2)
	{
		return 0;
	}
	
	@Override public Random getRandomWithSeed(long par1)
	{
		return new Random(worldObj.getSeed() + xPosition * xPosition * 4987142 + xPosition * 5947611 + zPosition * zPosition * 4392871L + zPosition * 389711 ^ par1);
	}
	
	@Override public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public boolean isAtLocation(int par1, int par2)
	{
		return par1 == xPosition && par2 == zPosition;
	}
	
	@Override public boolean isEmpty()
	{
		return true;
	}
	
	@Override public boolean needsSaving(boolean par1)
	{
		return false;
	}
	
	@Override public void onChunkLoad()
	{
	}
	
	@Override public void onChunkUnload()
	{
	}
	
	@Override public void removeChunkBlockTileEntity(int par1, int par2, int par3)
	{
	}
	
	@Override public void removeEntity(Entity par1Entity)
	{
	}
	
	@Override public void removeEntityAtIndex(Entity par1Entity, int par2)
	{
	}
	
	@Override public boolean setBlockIDWithMetadata(int par1, int par2, int par3, int par4, int par5)
	{
		return true;
	}
	
	@Override public boolean setBlockMetadata(int par1, int par2, int par3, int par4)
	{
		return false;
	}
	
	@Override public void setChunkBlockTileEntity(int par1, int par2, int par3, TileEntity par4TileEntity)
	{
	}
	
	@Override public void setChunkModified()
	{
	}
	
	@Override public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
	{
	}
}
