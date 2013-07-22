package net.minecraft.src;

public class ChunkCache implements IBlockAccess
{
	private int chunkX;
	private int chunkZ;
	private Chunk[][] chunkArray;
	private boolean isEmpty;
	private World worldObj;
	
	public ChunkCache(World p_i22009_1_, int p_i22009_2_, int p_i22009_3_, int p_i22009_4_, int p_i22009_5_, int p_i22009_6_, int p_i22009_7_, int p_i22009_8_)
	{
		worldObj = p_i22009_1_;
		chunkX = p_i22009_2_ - p_i22009_8_ >> 4;
		chunkZ = p_i22009_4_ - p_i22009_8_ >> 4;
		int var9 = p_i22009_5_ + p_i22009_8_ >> 4;
		int var10 = p_i22009_7_ + p_i22009_8_ >> 4;
		chunkArray = new Chunk[var9 - chunkX + 1][var10 - chunkZ + 1];
		isEmpty = true;
		int var11;
		int var12;
		Chunk var13;
		for(var11 = chunkX; var11 <= var9; ++var11)
		{
			for(var12 = chunkZ; var12 <= var10; ++var12)
			{
				var13 = p_i22009_1_.getChunkFromChunkCoords(var11, var12);
				if(var13 != null)
				{
					chunkArray[var11 - chunkX][var12 - chunkZ] = var13;
				}
			}
		}
		for(var11 = p_i22009_2_ >> 4; var11 <= p_i22009_5_ >> 4; ++var11)
		{
			for(var12 = p_i22009_4_ >> 4; var12 <= p_i22009_7_ >> 4; ++var12)
			{
				var13 = chunkArray[var11 - chunkX][var12 - chunkZ];
				if(var13 != null && !var13.getAreLevelsEmpty(p_i22009_3_, p_i22009_6_))
				{
					isEmpty = false;
				}
			}
		}
	}
	
	@Override public boolean doesBlockHaveSolidTopSurface(int p_72797_1_, int p_72797_2_, int p_72797_3_)
	{
		Block var4 = Block.blocksList[getBlockId(p_72797_1_, p_72797_2_, p_72797_3_)];
		return worldObj.isBlockTopFacingSurfaceSolid(var4, getBlockMetadata(p_72797_1_, p_72797_2_, p_72797_3_));
	}
	
	@Override public boolean extendedLevelsInChunkCache()
	{
		return isEmpty;
	}
	
	@Override public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_)
	{
		return worldObj.getBiomeGenForCoords(p_72807_1_, p_72807_2_);
	}
	
	@Override public int getBlockId(int p_72798_1_, int p_72798_2_, int p_72798_3_)
	{
		if(p_72798_2_ < 0) return 0;
		else if(p_72798_2_ >= 256) return 0;
		else
		{
			int var4 = (p_72798_1_ >> 4) - chunkX;
			int var5 = (p_72798_3_ >> 4) - chunkZ;
			if(var4 >= 0 && var4 < chunkArray.length && var5 >= 0 && var5 < chunkArray[var4].length)
			{
				Chunk var6 = chunkArray[var4][var5];
				return var6 == null ? 0 : var6.getBlockID(p_72798_1_ & 15, p_72798_2_, p_72798_3_ & 15);
			} else return 0;
		}
	}
	
	@Override public Material getBlockMaterial(int p_72803_1_, int p_72803_2_, int p_72803_3_)
	{
		int var4 = getBlockId(p_72803_1_, p_72803_2_, p_72803_3_);
		return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
	}
	
	@Override public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_)
	{
		if(p_72805_2_ < 0) return 0;
		else if(p_72805_2_ >= 256) return 0;
		else
		{
			int var4 = (p_72805_1_ >> 4) - chunkX;
			int var5 = (p_72805_3_ >> 4) - chunkZ;
			return chunkArray[var4][var5].getBlockMetadata(p_72805_1_ & 15, p_72805_2_, p_72805_3_ & 15);
		}
	}
	
	@Override public TileEntity getBlockTileEntity(int p_72796_1_, int p_72796_2_, int p_72796_3_)
	{
		int var4 = (p_72796_1_ >> 4) - chunkX;
		int var5 = (p_72796_3_ >> 4) - chunkZ;
		return chunkArray[var4][var5].getChunkBlockTileEntity(p_72796_1_ & 15, p_72796_2_, p_72796_3_ & 15);
	}
	
	@Override public float getBrightness(int par1, int par2, int par3, int par4)
	{
		int var5 = getLightValue(par1, par2, par3);
		if(var5 < par4)
		{
			var5 = par4;
		}
		return worldObj.provider.lightBrightnessTable[var5];
	}
	
	@Override public int getHeight()
	{
		return 256;
	}
	
	@Override public float getLightBrightness(int p_72801_1_, int p_72801_2_, int p_72801_3_)
	{
		return worldObj.provider.lightBrightnessTable[getLightValue(p_72801_1_, p_72801_2_, p_72801_3_)];
	}
	
	@Override public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4)
	{
		int var5 = getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
		int var6 = getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
		if(var6 < par4)
		{
			var6 = par4;
		}
		return var5 << 20 | var6 << 4;
	}
	
	public int getLightValue(int par1, int par2, int par3)
	{
		return getLightValueExt(par1, par2, par3, true);
	}
	
	public int getLightValueExt(int par1, int par2, int par3, boolean par4)
	{
		if(par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 <= 30000000)
		{
			int var5;
			int var6;
			if(par4)
			{
				var5 = getBlockId(par1, par2, par3);
				if(var5 == Block.stoneSingleSlab.blockID || var5 == Block.woodSingleSlab.blockID || var5 == Block.tilledField.blockID || var5 == Block.stairsWoodOak.blockID || var5 == Block.stairsCobblestone.blockID)
				{
					var6 = getLightValueExt(par1, par2 + 1, par3, false);
					int var7 = getLightValueExt(par1 + 1, par2, par3, false);
					int var8 = getLightValueExt(par1 - 1, par2, par3, false);
					int var9 = getLightValueExt(par1, par2, par3 + 1, false);
					int var10 = getLightValueExt(par1, par2, par3 - 1, false);
					if(var7 > var6)
					{
						var6 = var7;
					}
					if(var8 > var6)
					{
						var6 = var8;
					}
					if(var9 > var6)
					{
						var6 = var9;
					}
					if(var10 > var6)
					{
						var6 = var10;
					}
					return var6;
				}
			}
			if(par2 < 0) return 0;
			else if(par2 >= 256)
			{
				var5 = 15 - worldObj.skylightSubtracted;
				if(var5 < 0)
				{
					var5 = 0;
				}
				return var5;
			} else
			{
				var5 = (par1 >> 4) - chunkX;
				var6 = (par3 >> 4) - chunkZ;
				return chunkArray[var5][var6].getBlockLightValue(par1 & 15, par2, par3 & 15, worldObj.skylightSubtracted);
			}
		} else return 15;
	}
	
	public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		if(par3 < 0)
		{
			par3 = 0;
		}
		if(par3 >= 256)
		{
			par3 = 255;
		}
		if(par3 >= 0 && par3 < 256 && par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 <= 30000000)
		{
			if(par1EnumSkyBlock == EnumSkyBlock.Sky && worldObj.provider.hasNoSky) return 0;
			else
			{
				int var5;
				int var6;
				if(Block.useNeighborBrightness[getBlockId(par2, par3, par4)])
				{
					var5 = getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3 + 1, par4);
					var6 = getSpecialBlockBrightness(par1EnumSkyBlock, par2 + 1, par3, par4);
					int var7 = getSpecialBlockBrightness(par1EnumSkyBlock, par2 - 1, par3, par4);
					int var8 = getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 + 1);
					int var9 = getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 - 1);
					if(var6 > var5)
					{
						var5 = var6;
					}
					if(var7 > var5)
					{
						var5 = var7;
					}
					if(var8 > var5)
					{
						var5 = var8;
					}
					if(var9 > var5)
					{
						var5 = var9;
					}
					return var5;
				} else
				{
					var5 = (par2 >> 4) - chunkX;
					var6 = (par4 >> 4) - chunkZ;
					return chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
				}
			}
		} else return par1EnumSkyBlock.defaultLightValue;
	}
	
	public int getSpecialBlockBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		if(par3 < 0)
		{
			par3 = 0;
		}
		if(par3 >= 256)
		{
			par3 = 255;
		}
		if(par3 >= 0 && par3 < 256 && par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 <= 30000000)
		{
			int var5 = (par2 >> 4) - chunkX;
			int var6 = (par4 >> 4) - chunkZ;
			return chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 15, par3, par4 & 15);
		} else return par1EnumSkyBlock.defaultLightValue;
	}
	
	@Override public Vec3Pool getWorldVec3Pool()
	{
		return worldObj.getWorldVec3Pool();
	}
	
	@Override public boolean isAirBlock(int p_72799_1_, int p_72799_2_, int p_72799_3_)
	{
		Block var4 = Block.blocksList[getBlockId(p_72799_1_, p_72799_2_, p_72799_3_)];
		return var4 == null;
	}
	
	@Override public boolean isBlockNormalCube(int p_72809_1_, int p_72809_2_, int p_72809_3_)
	{
		Block var4 = Block.blocksList[getBlockId(p_72809_1_, p_72809_2_, p_72809_3_)];
		return var4 == null ? false : var4.blockMaterial.blocksMovement() && var4.renderAsNormalBlock();
	}
	
	@Override public boolean isBlockOpaqueCube(int p_72804_1_, int p_72804_2_, int p_72804_3_)
	{
		Block var4 = Block.blocksList[getBlockId(p_72804_1_, p_72804_2_, p_72804_3_)];
		return var4 == null ? false : var4.isOpaqueCube();
	}
	
	@Override public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_)
	{
		int var5 = getBlockId(p_72879_1_, p_72879_2_, p_72879_3_);
		return var5 == 0 ? 0 : Block.blocksList[var5].isProvidingStrongPower(this, p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
	}
}
