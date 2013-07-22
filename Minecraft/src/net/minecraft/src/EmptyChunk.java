package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EmptyChunk extends Chunk
{
	public EmptyChunk(World p_i3770_1_, int p_i3770_2_, int p_i3770_3_)
	{
		super(p_i3770_1_, p_i3770_2_, p_i3770_3_);
	}
	
	@Override public void addEntity(Entity p_76612_1_)
	{
	}
	
	@Override public void addTileEntity(TileEntity p_76620_1_)
	{
	}
	
	@Override public boolean canBlockSeeTheSky(int p_76619_1_, int p_76619_2_, int p_76619_3_)
	{
		return false;
	}
	
	@Override public void generateHeightMap()
	{
	}
	
	@Override public void generateSkylightMap()
	{
	}
	
	@Override public boolean getAreLevelsEmpty(int p_76606_1_, int p_76606_2_)
	{
		return true;
	}
	
	@Override public int getBlockID(int p_76610_1_, int p_76610_2_, int p_76610_3_)
	{
		return 0;
	}
	
	@Override public int getBlockLightOpacity(int p_76596_1_, int p_76596_2_, int p_76596_3_)
	{
		return 255;
	}
	
	@Override public int getBlockLightValue(int p_76629_1_, int p_76629_2_, int p_76629_3_, int p_76629_4_)
	{
		return 0;
	}
	
	@Override public int getBlockMetadata(int p_76628_1_, int p_76628_2_, int p_76628_3_)
	{
		return 0;
	}
	
	@Override public TileEntity getChunkBlockTileEntity(int p_76597_1_, int p_76597_2_, int p_76597_3_)
	{
		return null;
	}
	
	@Override public void getEntitiesOfTypeWithinAAAB(Class p_76618_1_, AxisAlignedBB p_76618_2_, List p_76618_3_, IEntitySelector p_76618_4_)
	{
	}
	
	@Override public void getEntitiesWithinAABBForEntity(Entity p_76588_1_, AxisAlignedBB p_76588_2_, List p_76588_3_, IEntitySelector p_76588_4_)
	{
	}
	
	@Override public int getHeightValue(int p_76611_1_, int p_76611_2_)
	{
		return 0;
	}
	
	@Override public Random getRandomWithSeed(long p_76617_1_)
	{
		return new Random(worldObj.getSeed() + xPosition * xPosition * 4987142 + xPosition * 5947611 + zPosition * zPosition * 4392871L + zPosition * 389711 ^ p_76617_1_);
	}
	
	@Override public int getSavedLightValue(EnumSkyBlock p_76614_1_, int p_76614_2_, int p_76614_3_, int p_76614_4_)
	{
		return 0;
	}
	
	@Override public boolean isAtLocation(int p_76600_1_, int p_76600_2_)
	{
		return p_76600_1_ == xPosition && p_76600_2_ == zPosition;
	}
	
	@Override public boolean isEmpty()
	{
		return true;
	}
	
	@Override public boolean needsSaving(boolean p_76601_1_)
	{
		return false;
	}
	
	@Override public void onChunkLoad()
	{
	}
	
	@Override public void onChunkUnload()
	{
	}
	
	@Override public void removeChunkBlockTileEntity(int p_76627_1_, int p_76627_2_, int p_76627_3_)
	{
	}
	
	@Override public void removeEntity(Entity p_76622_1_)
	{
	}
	
	@Override public void removeEntityAtIndex(Entity p_76608_1_, int p_76608_2_)
	{
	}
	
	@Override public boolean setBlockIDWithMetadata(int p_76592_1_, int p_76592_2_, int p_76592_3_, int p_76592_4_, int p_76592_5_)
	{
		return true;
	}
	
	@Override public boolean setBlockMetadata(int p_76589_1_, int p_76589_2_, int p_76589_3_, int p_76589_4_)
	{
		return false;
	}
	
	@Override public void setChunkBlockTileEntity(int p_76604_1_, int p_76604_2_, int p_76604_3_, TileEntity p_76604_4_)
	{
	}
	
	@Override public void setChunkModified()
	{
	}
	
	@Override public void setLightValue(EnumSkyBlock p_76633_1_, int p_76633_2_, int p_76633_3_, int p_76633_4_, int p_76633_5_)
	{
	}
}
