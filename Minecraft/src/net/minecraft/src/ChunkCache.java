package net.minecraft.src;

public class ChunkCache implements IBlockAccess
{
	private int chunkX;
	private int chunkZ;
	private Chunk[][] chunkArray;
	private boolean isEmpty;
	private World worldObj;
	
	public ChunkCache(World par1World, int par2, int par3, int par4, int par5, int par6, int par7, int par8)
	{
		worldObj = par1World;
		chunkX = par2 - par8 >> 4;
		chunkZ = par4 - par8 >> 4;
		int var9 = par5 + par8 >> 4;
		int var10 = par7 + par8 >> 4;
		chunkArray = new Chunk[var9 - chunkX + 1][var10 - chunkZ + 1];
		isEmpty = true;
		int var11;
		int var12;
		Chunk var13;
		for(var11 = chunkX; var11 <= var9; ++var11)
		{
			for(var12 = chunkZ; var12 <= var10; ++var12)
			{
				var13 = par1World.getChunkFromChunkCoords(var11, var12);
				if(var13 != null)
				{
					chunkArray[var11 - chunkX][var12 - chunkZ] = var13;
				}
			}
		}
		for(var11 = par2 >> 4; var11 <= par5 >> 4; ++var11)
		{
			for(var12 = par4 >> 4; var12 <= par7 >> 4; ++var12)
			{
				var13 = chunkArray[var11 - chunkX][var12 - chunkZ];
				if(var13 != null && !var13.getAreLevelsEmpty(par3, par6))
				{
					isEmpty = false;
				}
			}
		}
	}
	
	@Override public boolean doesBlockHaveSolidTopSurface(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[getBlockId(par1, par2, par3)];
		return worldObj.isBlockTopFacingSurfaceSolid(var4, getBlockMetadata(par1, par2, par3));
	}
	
	@Override public boolean extendedLevelsInChunkCache()
	{
		return isEmpty;
	}
	
	@Override public BiomeGenBase getBiomeGenForCoords(int par1, int par2)
	{
		return worldObj.getBiomeGenForCoords(par1, par2);
	}
	
	@Override public int getBlockId(int par1, int par2, int par3)
	{
		if(par2 < 0) return 0;
		else if(par2 >= 256) return 0;
		else
		{
			int var4 = (par1 >> 4) - chunkX;
			int var5 = (par3 >> 4) - chunkZ;
			if(var4 >= 0 && var4 < chunkArray.length && var5 >= 0 && var5 < chunkArray[var4].length)
			{
				Chunk var6 = chunkArray[var4][var5];
				return var6 == null ? 0 : var6.getBlockID(par1 & 15, par2, par3 & 15);
			} else return 0;
		}
	}
	
	@Override public Material getBlockMaterial(int par1, int par2, int par3)
	{
		int var4 = getBlockId(par1, par2, par3);
		return var4 == 0 ? Material.air : Block.blocksList[var4].blockMaterial;
	}
	
	@Override public int getBlockMetadata(int par1, int par2, int par3)
	{
		if(par2 < 0) return 0;
		else if(par2 >= 256) return 0;
		else
		{
			int var4 = (par1 >> 4) - chunkX;
			int var5 = (par3 >> 4) - chunkZ;
			return chunkArray[var4][var5].getBlockMetadata(par1 & 15, par2, par3 & 15);
		}
	}
	
	@Override public TileEntity getBlockTileEntity(int par1, int par2, int par3)
	{
		int var4 = (par1 >> 4) - chunkX;
		int var5 = (par3 >> 4) - chunkZ;
		return chunkArray[var4][var5].getChunkBlockTileEntity(par1 & 15, par2, par3 & 15);
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
	
	@Override public float getLightBrightness(int par1, int par2, int par3)
	{
		return worldObj.provider.lightBrightnessTable[getLightValue(par1, par2, par3)];
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
	
	@Override public boolean isAirBlock(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[getBlockId(par1, par2, par3)];
		return var4 == null;
	}
	
	@Override public boolean isBlockNormalCube(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[getBlockId(par1, par2, par3)];
		return var4 == null ? false : var4.blockMaterial.blocksMovement() && var4.renderAsNormalBlock();
	}
	
	@Override public boolean isBlockOpaqueCube(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[getBlockId(par1, par2, par3)];
		return var4 == null ? false : var4.isOpaqueCube();
	}
	
	@Override public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4)
	{
		int var5 = getBlockId(par1, par2, par3);
		return var5 == 0 ? 0 : Block.blocksList[var5].isProvidingStrongPower(this, par1, par2, par3, par4);
	}
}
