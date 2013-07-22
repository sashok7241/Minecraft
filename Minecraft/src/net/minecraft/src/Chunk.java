package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chunk
{
	public static boolean isLit;
	private ExtendedBlockStorage[] storageArrays;
	private byte[] blockBiomeArray;
	public int[] precipitationHeightMap;
	public boolean[] updateSkylightColumns;
	public boolean isChunkLoaded;
	public World worldObj;
	public int[] heightMap;
	public final int xPosition;
	public final int zPosition;
	private boolean isGapLightingUpdated;
	public Map chunkTileEntityMap;
	public List[] entityLists;
	public boolean isTerrainPopulated;
	public boolean isModified;
	public boolean hasEntities;
	public long lastSaveTime;
	public boolean sendUpdates;
	public int heightMapMinimum;
	private int queuedLightChecks;
	boolean field_76653_p;
	
	public Chunk(World p_i3772_1_, byte[] p_i3772_2_, int p_i3772_3_, int p_i3772_4_)
	{
		this(p_i3772_1_, p_i3772_3_, p_i3772_4_);
		int var5 = p_i3772_2_.length / 256;
		for(int var6 = 0; var6 < 16; ++var6)
		{
			for(int var7 = 0; var7 < 16; ++var7)
			{
				for(int var8 = 0; var8 < var5; ++var8)
				{
					byte var9 = p_i3772_2_[var6 << 11 | var7 << 7 | var8];
					if(var9 != 0)
					{
						int var10 = var8 >> 4;
						if(storageArrays[var10] == null)
						{
							storageArrays[var10] = new ExtendedBlockStorage(var10 << 4, !p_i3772_1_.provider.hasNoSky);
						}
						storageArrays[var10].setExtBlockID(var6, var8 & 15, var7, var9);
					}
				}
			}
		}
	}
	
	public Chunk(World p_i3771_1_, int p_i3771_2_, int p_i3771_3_)
	{
		storageArrays = new ExtendedBlockStorage[16];
		blockBiomeArray = new byte[256];
		precipitationHeightMap = new int[256];
		updateSkylightColumns = new boolean[256];
		isGapLightingUpdated = false;
		chunkTileEntityMap = new HashMap();
		isTerrainPopulated = false;
		isModified = false;
		hasEntities = false;
		lastSaveTime = 0L;
		sendUpdates = false;
		heightMapMinimum = 0;
		queuedLightChecks = 4096;
		field_76653_p = false;
		entityLists = new List[16];
		worldObj = p_i3771_1_;
		xPosition = p_i3771_2_;
		zPosition = p_i3771_3_;
		heightMap = new int[256];
		for(int var4 = 0; var4 < entityLists.length; ++var4)
		{
			entityLists[var4] = new ArrayList();
		}
		Arrays.fill(precipitationHeightMap, -999);
		Arrays.fill(blockBiomeArray, (byte) -1);
	}
	
	public void addEntity(Entity p_76612_1_)
	{
		hasEntities = true;
		int var2 = MathHelper.floor_double(p_76612_1_.posX / 16.0D);
		int var3 = MathHelper.floor_double(p_76612_1_.posZ / 16.0D);
		if(var2 != xPosition || var3 != zPosition)
		{
			worldObj.getWorldLogAgent().logSevere("Wrong location! " + p_76612_1_);
			Thread.dumpStack();
		}
		int var4 = MathHelper.floor_double(p_76612_1_.posY / 16.0D);
		if(var4 < 0)
		{
			var4 = 0;
		}
		if(var4 >= entityLists.length)
		{
			var4 = entityLists.length - 1;
		}
		p_76612_1_.addedToChunk = true;
		p_76612_1_.chunkCoordX = xPosition;
		p_76612_1_.chunkCoordY = var4;
		p_76612_1_.chunkCoordZ = zPosition;
		entityLists[var4].add(p_76612_1_);
	}
	
	public void addTileEntity(TileEntity p_76620_1_)
	{
		int var2 = p_76620_1_.xCoord - xPosition * 16;
		int var3 = p_76620_1_.yCoord;
		int var4 = p_76620_1_.zCoord - zPosition * 16;
		setChunkBlockTileEntity(var2, var3, var4, p_76620_1_);
		if(isChunkLoaded)
		{
			worldObj.loadedTileEntityList.add(p_76620_1_);
		}
	}
	
	public boolean canBlockSeeTheSky(int p_76619_1_, int p_76619_2_, int p_76619_3_)
	{
		return p_76619_2_ >= heightMap[p_76619_3_ << 4 | p_76619_1_];
	}
	
	private void checkSkylightNeighborHeight(int p_76599_1_, int p_76599_2_, int p_76599_3_)
	{
		int var4 = worldObj.getHeightValue(p_76599_1_, p_76599_2_);
		if(var4 > p_76599_3_)
		{
			updateSkylightNeighborHeight(p_76599_1_, p_76599_2_, p_76599_3_, var4 + 1);
		} else if(var4 < p_76599_3_)
		{
			updateSkylightNeighborHeight(p_76599_1_, p_76599_2_, var4, p_76599_3_ + 1);
		}
	}
	
	public void enqueueRelightChecks()
	{
		for(int var1 = 0; var1 < 8; ++var1)
		{
			if(queuedLightChecks >= 4096) return;
			int var2 = queuedLightChecks % 16;
			int var3 = queuedLightChecks / 16 % 16;
			int var4 = queuedLightChecks / 256;
			++queuedLightChecks;
			int var5 = (xPosition << 4) + var3;
			int var6 = (zPosition << 4) + var4;
			for(int var7 = 0; var7 < 16; ++var7)
			{
				int var8 = (var2 << 4) + var7;
				if(storageArrays[var2] == null && (var7 == 0 || var7 == 15 || var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15) || storageArrays[var2] != null && storageArrays[var2].getExtBlockID(var3, var7, var4) == 0)
				{
					if(Block.lightValue[worldObj.getBlockId(var5, var8 - 1, var6)] > 0)
					{
						worldObj.updateAllLightTypes(var5, var8 - 1, var6);
					}
					if(Block.lightValue[worldObj.getBlockId(var5, var8 + 1, var6)] > 0)
					{
						worldObj.updateAllLightTypes(var5, var8 + 1, var6);
					}
					if(Block.lightValue[worldObj.getBlockId(var5 - 1, var8, var6)] > 0)
					{
						worldObj.updateAllLightTypes(var5 - 1, var8, var6);
					}
					if(Block.lightValue[worldObj.getBlockId(var5 + 1, var8, var6)] > 0)
					{
						worldObj.updateAllLightTypes(var5 + 1, var8, var6);
					}
					if(Block.lightValue[worldObj.getBlockId(var5, var8, var6 - 1)] > 0)
					{
						worldObj.updateAllLightTypes(var5, var8, var6 - 1);
					}
					if(Block.lightValue[worldObj.getBlockId(var5, var8, var6 + 1)] > 0)
					{
						worldObj.updateAllLightTypes(var5, var8, var6 + 1);
					}
					worldObj.updateAllLightTypes(var5, var8, var6);
				}
			}
		}
	}
	
	public void fillChunk(byte[] par1ArrayOfByte, int par2, int par3, boolean par4)
	{
		int var5 = 0;
		boolean var6 = !worldObj.provider.hasNoSky;
		int var7;
		for(var7 = 0; var7 < storageArrays.length; ++var7)
		{
			if((par2 & 1 << var7) != 0)
			{
				if(storageArrays[var7] == null)
				{
					storageArrays[var7] = new ExtendedBlockStorage(var7 << 4, var6);
				}
				byte[] var8 = storageArrays[var7].getBlockLSBArray();
				System.arraycopy(par1ArrayOfByte, var5, var8, 0, var8.length);
				var5 += var8.length;
			} else if(par4 && storageArrays[var7] != null)
			{
				storageArrays[var7] = null;
			}
		}
		NibbleArray var9;
		for(var7 = 0; var7 < storageArrays.length; ++var7)
		{
			if((par2 & 1 << var7) != 0 && storageArrays[var7] != null)
			{
				var9 = storageArrays[var7].getMetadataArray();
				System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
				var5 += var9.data.length;
			}
		}
		for(var7 = 0; var7 < storageArrays.length; ++var7)
		{
			if((par2 & 1 << var7) != 0 && storageArrays[var7] != null)
			{
				var9 = storageArrays[var7].getBlocklightArray();
				System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
				var5 += var9.data.length;
			}
		}
		if(var6)
		{
			for(var7 = 0; var7 < storageArrays.length; ++var7)
			{
				if((par2 & 1 << var7) != 0 && storageArrays[var7] != null)
				{
					var9 = storageArrays[var7].getSkylightArray();
					System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
					var5 += var9.data.length;
				}
			}
		}
		for(var7 = 0; var7 < storageArrays.length; ++var7)
		{
			if((par3 & 1 << var7) != 0)
			{
				if(storageArrays[var7] == null)
				{
					var5 += 2048;
				} else
				{
					var9 = storageArrays[var7].getBlockMSBArray();
					if(var9 == null)
					{
						var9 = storageArrays[var7].createBlockMSBArray();
					}
					System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
					var5 += var9.data.length;
				}
			} else if(par4 && storageArrays[var7] != null && storageArrays[var7].getBlockMSBArray() != null)
			{
				storageArrays[var7].clearMSBArray();
			}
		}
		if(par4)
		{
			System.arraycopy(par1ArrayOfByte, var5, blockBiomeArray, 0, blockBiomeArray.length);
			int var10000 = var5 + blockBiomeArray.length;
		}
		for(var7 = 0; var7 < storageArrays.length; ++var7)
		{
			if(storageArrays[var7] != null && (par2 & 1 << var7) != 0)
			{
				storageArrays[var7].removeInvalidBlocks();
			}
		}
		generateHeightMap();
		Iterator var10 = chunkTileEntityMap.values().iterator();
		while(var10.hasNext())
		{
			TileEntity var11 = (TileEntity) var10.next();
			var11.updateContainingBlockInfo();
		}
	}
	
	public void generateHeightMap()
	{
		int var1 = getTopFilledSegment();
		for(int var2 = 0; var2 < 16; ++var2)
		{
			int var3 = 0;
			while(var3 < 16)
			{
				precipitationHeightMap[var2 + (var3 << 4)] = -999;
				int var4 = var1 + 16 - 1;
				while(true)
				{
					if(var4 > 0)
					{
						int var5 = getBlockID(var2, var4 - 1, var3);
						if(Block.lightOpacity[var5] == 0)
						{
							--var4;
							continue;
						}
						heightMap[var3 << 4 | var2] = var4;
					}
					++var3;
					break;
				}
			}
		}
		isModified = true;
	}
	
	public void generateSkylightMap()
	{
		int var1 = getTopFilledSegment();
		heightMapMinimum = Integer.MAX_VALUE;
		int var2;
		int var3;
		for(var2 = 0; var2 < 16; ++var2)
		{
			var3 = 0;
			while(var3 < 16)
			{
				precipitationHeightMap[var2 + (var3 << 4)] = -999;
				int var4 = var1 + 16 - 1;
				while(true)
				{
					if(var4 > 0)
					{
						if(getBlockLightOpacity(var2, var4 - 1, var3) == 0)
						{
							--var4;
							continue;
						}
						heightMap[var3 << 4 | var2] = var4;
						if(var4 < heightMapMinimum)
						{
							heightMapMinimum = var4;
						}
					}
					if(!worldObj.provider.hasNoSky)
					{
						var4 = 15;
						int var5 = var1 + 16 - 1;
						do
						{
							var4 -= getBlockLightOpacity(var2, var5, var3);
							if(var4 > 0)
							{
								ExtendedBlockStorage var6 = storageArrays[var5 >> 4];
								if(var6 != null)
								{
									var6.setExtSkylightValue(var2, var5 & 15, var3, var4);
									worldObj.markBlockForRenderUpdate((xPosition << 4) + var2, var5, (zPosition << 4) + var3);
								}
							}
							--var5;
						} while(var5 > 0 && var4 > 0);
					}
					++var3;
					break;
				}
			}
		}
		isModified = true;
		for(var2 = 0; var2 < 16; ++var2)
		{
			for(var3 = 0; var3 < 16; ++var3)
			{
				propagateSkylightOcclusion(var2, var3);
			}
		}
	}
	
	public boolean getAreLevelsEmpty(int p_76606_1_, int p_76606_2_)
	{
		if(p_76606_1_ < 0)
		{
			p_76606_1_ = 0;
		}
		if(p_76606_2_ >= 256)
		{
			p_76606_2_ = 255;
		}
		for(int var3 = p_76606_1_; var3 <= p_76606_2_; var3 += 16)
		{
			ExtendedBlockStorage var4 = storageArrays[var3 >> 4];
			if(var4 != null && !var4.isEmpty()) return false;
		}
		return true;
	}
	
	public byte[] getBiomeArray()
	{
		return blockBiomeArray;
	}
	
	public BiomeGenBase getBiomeGenForWorldCoords(int p_76591_1_, int p_76591_2_, WorldChunkManager p_76591_3_)
	{
		int var4 = blockBiomeArray[p_76591_2_ << 4 | p_76591_1_] & 255;
		if(var4 == 255)
		{
			BiomeGenBase var5 = p_76591_3_.getBiomeGenAt((xPosition << 4) + p_76591_1_, (zPosition << 4) + p_76591_2_);
			var4 = var5.biomeID;
			blockBiomeArray[p_76591_2_ << 4 | p_76591_1_] = (byte) (var4 & 255);
		}
		return BiomeGenBase.biomeList[var4] == null ? BiomeGenBase.plains : BiomeGenBase.biomeList[var4];
	}
	
	public int getBlockID(int p_76610_1_, int p_76610_2_, int p_76610_3_)
	{
		if(p_76610_2_ >> 4 >= storageArrays.length) return 0;
		else
		{
			ExtendedBlockStorage var4 = storageArrays[p_76610_2_ >> 4];
			return var4 != null ? var4.getExtBlockID(p_76610_1_, p_76610_2_ & 15, p_76610_3_) : 0;
		}
	}
	
	public int getBlockLightOpacity(int p_76596_1_, int p_76596_2_, int p_76596_3_)
	{
		return Block.lightOpacity[getBlockID(p_76596_1_, p_76596_2_, p_76596_3_)];
	}
	
	public int getBlockLightValue(int p_76629_1_, int p_76629_2_, int p_76629_3_, int p_76629_4_)
	{
		ExtendedBlockStorage var5 = storageArrays[p_76629_2_ >> 4];
		if(var5 == null) return !worldObj.provider.hasNoSky && p_76629_4_ < EnumSkyBlock.Sky.defaultLightValue ? EnumSkyBlock.Sky.defaultLightValue - p_76629_4_ : 0;
		else
		{
			int var6 = worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(p_76629_1_, p_76629_2_ & 15, p_76629_3_);
			if(var6 > 0)
			{
				isLit = true;
			}
			var6 -= p_76629_4_;
			int var7 = var5.getExtBlocklightValue(p_76629_1_, p_76629_2_ & 15, p_76629_3_);
			if(var7 > var6)
			{
				var6 = var7;
			}
			return var6;
		}
	}
	
	public int getBlockMetadata(int p_76628_1_, int p_76628_2_, int p_76628_3_)
	{
		if(p_76628_2_ >> 4 >= storageArrays.length) return 0;
		else
		{
			ExtendedBlockStorage var4 = storageArrays[p_76628_2_ >> 4];
			return var4 != null ? var4.getExtBlockMetadata(p_76628_1_, p_76628_2_ & 15, p_76628_3_) : 0;
		}
	}
	
	public ExtendedBlockStorage[] getBlockStorageArray()
	{
		return storageArrays;
	}
	
	public TileEntity getChunkBlockTileEntity(int p_76597_1_, int p_76597_2_, int p_76597_3_)
	{
		ChunkPosition var4 = new ChunkPosition(p_76597_1_, p_76597_2_, p_76597_3_);
		TileEntity var5 = (TileEntity) chunkTileEntityMap.get(var4);
		if(var5 == null)
		{
			int var6 = getBlockID(p_76597_1_, p_76597_2_, p_76597_3_);
			if(var6 <= 0 || !Block.blocksList[var6].hasTileEntity()) return null;
			if(var5 == null)
			{
				var5 = ((ITileEntityProvider) Block.blocksList[var6]).createNewTileEntity(worldObj);
				worldObj.setBlockTileEntity(xPosition * 16 + p_76597_1_, p_76597_2_, zPosition * 16 + p_76597_3_, var5);
			}
			var5 = (TileEntity) chunkTileEntityMap.get(var4);
		}
		if(var5 != null && var5.isInvalid())
		{
			chunkTileEntityMap.remove(var4);
			return null;
		} else return var5;
	}
	
	public ChunkCoordIntPair getChunkCoordIntPair()
	{
		return new ChunkCoordIntPair(xPosition, zPosition);
	}
	
	public void getEntitiesOfTypeWithinAAAB(Class p_76618_1_, AxisAlignedBB p_76618_2_, List p_76618_3_, IEntitySelector p_76618_4_)
	{
		int var5 = MathHelper.floor_double((p_76618_2_.minY - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((p_76618_2_.maxY + 2.0D) / 16.0D);
		if(var5 < 0)
		{
			var5 = 0;
		} else if(var5 >= entityLists.length)
		{
			var5 = entityLists.length - 1;
		}
		if(var6 >= entityLists.length)
		{
			var6 = entityLists.length - 1;
		} else if(var6 < 0)
		{
			var6 = 0;
		}
		for(int var7 = var5; var7 <= var6; ++var7)
		{
			List var8 = entityLists[var7];
			for(int var9 = 0; var9 < var8.size(); ++var9)
			{
				Entity var10 = (Entity) var8.get(var9);
				if(p_76618_1_.isAssignableFrom(var10.getClass()) && var10.boundingBox.intersectsWith(p_76618_2_) && (p_76618_4_ == null || p_76618_4_.isEntityApplicable(var10)))
				{
					p_76618_3_.add(var10);
				}
			}
		}
	}
	
	public void getEntitiesWithinAABBForEntity(Entity p_76588_1_, AxisAlignedBB p_76588_2_, List p_76588_3_, IEntitySelector p_76588_4_)
	{
		int var5 = MathHelper.floor_double((p_76588_2_.minY - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((p_76588_2_.maxY + 2.0D) / 16.0D);
		if(var5 < 0)
		{
			var5 = 0;
			var6 = Math.max(var5, var6);
		}
		if(var6 >= entityLists.length)
		{
			var6 = entityLists.length - 1;
			var5 = Math.min(var5, var6);
		}
		for(int var7 = var5; var7 <= var6; ++var7)
		{
			List var8 = entityLists[var7];
			for(int var9 = 0; var9 < var8.size(); ++var9)
			{
				Entity var10 = (Entity) var8.get(var9);
				if(var10 != p_76588_1_ && var10.boundingBox.intersectsWith(p_76588_2_) && (p_76588_4_ == null || p_76588_4_.isEntityApplicable(var10)))
				{
					p_76588_3_.add(var10);
					Entity[] var11 = var10.getParts();
					if(var11 != null)
					{
						for(Entity element : var11)
						{
							var10 = element;
							if(var10 != p_76588_1_ && var10.boundingBox.intersectsWith(p_76588_2_) && (p_76588_4_ == null || p_76588_4_.isEntityApplicable(var10)))
							{
								p_76588_3_.add(var10);
							}
						}
					}
				}
			}
		}
	}
	
	public int getHeightValue(int p_76611_1_, int p_76611_2_)
	{
		return heightMap[p_76611_2_ << 4 | p_76611_1_];
	}
	
	public int getPrecipitationHeight(int p_76626_1_, int p_76626_2_)
	{
		int var3 = p_76626_1_ | p_76626_2_ << 4;
		int var4 = precipitationHeightMap[var3];
		if(var4 == -999)
		{
			int var5 = getTopFilledSegment() + 15;
			var4 = -1;
			while(var5 > 0 && var4 == -1)
			{
				int var6 = getBlockID(p_76626_1_, var5, p_76626_2_);
				Material var7 = var6 == 0 ? Material.air : Block.blocksList[var6].blockMaterial;
				if(!var7.blocksMovement() && !var7.isLiquid())
				{
					--var5;
				} else
				{
					var4 = var5 + 1;
				}
			}
			precipitationHeightMap[var3] = var4;
		}
		return var4;
	}
	
	public Random getRandomWithSeed(long p_76617_1_)
	{
		return new Random(worldObj.getSeed() + xPosition * xPosition * 4987142 + xPosition * 5947611 + zPosition * zPosition * 4392871L + zPosition * 389711 ^ p_76617_1_);
	}
	
	public int getSavedLightValue(EnumSkyBlock p_76614_1_, int p_76614_2_, int p_76614_3_, int p_76614_4_)
	{
		ExtendedBlockStorage var5 = storageArrays[p_76614_3_ >> 4];
		return var5 == null ? canBlockSeeTheSky(p_76614_2_, p_76614_3_, p_76614_4_) ? p_76614_1_.defaultLightValue : 0 : p_76614_1_ == EnumSkyBlock.Sky ? worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(p_76614_2_, p_76614_3_ & 15, p_76614_4_) : p_76614_1_ == EnumSkyBlock.Block ? var5.getExtBlocklightValue(p_76614_2_, p_76614_3_ & 15, p_76614_4_) : p_76614_1_.defaultLightValue;
	}
	
	public int getTopFilledSegment()
	{
		for(int var1 = storageArrays.length - 1; var1 >= 0; --var1)
		{
			if(storageArrays[var1] != null) return storageArrays[var1].getYLocation();
		}
		return 0;
	}
	
	public boolean isAtLocation(int p_76600_1_, int p_76600_2_)
	{
		return p_76600_1_ == xPosition && p_76600_2_ == zPosition;
	}
	
	public boolean isEmpty()
	{
		return false;
	}
	
	public boolean needsSaving(boolean p_76601_1_)
	{
		if(p_76601_1_)
		{
			if(hasEntities && worldObj.getTotalWorldTime() != lastSaveTime || isModified) return true;
		} else if(hasEntities && worldObj.getTotalWorldTime() >= lastSaveTime + 600L) return true;
		return isModified;
	}
	
	public void onChunkLoad()
	{
		isChunkLoaded = true;
		worldObj.addTileEntity(chunkTileEntityMap.values());
		for(List entityList : entityLists)
		{
			worldObj.addLoadedEntities(entityList);
		}
	}
	
	public void onChunkUnload()
	{
		isChunkLoaded = false;
		Iterator var1 = chunkTileEntityMap.values().iterator();
		while(var1.hasNext())
		{
			TileEntity var2 = (TileEntity) var1.next();
			worldObj.markTileEntityForDespawn(var2);
		}
		for(List entityList : entityLists)
		{
			worldObj.unloadEntities(entityList);
		}
	}
	
	public void populateChunk(IChunkProvider p_76624_1_, IChunkProvider p_76624_2_, int p_76624_3_, int p_76624_4_)
	{
		if(!isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_))
		{
			p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_);
		}
		if(p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_) && !p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_).isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ + 1))
		{
			p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_);
		}
		if(p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ - 1) && !p_76624_1_.provideChunk(p_76624_3_, p_76624_4_ - 1).isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ - 1) && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ - 1) && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_))
		{
			p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_ - 1);
		}
		if(p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ - 1) && !p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_ - 1).isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ - 1) && p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_))
		{
			p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_ - 1);
		}
	}
	
	private void propagateSkylightOcclusion(int p_76595_1_, int p_76595_2_)
	{
		updateSkylightColumns[p_76595_1_ + p_76595_2_ * 16] = true;
		isGapLightingUpdated = true;
	}
	
	private void relightBlock(int p_76615_1_, int p_76615_2_, int p_76615_3_)
	{
		int var4 = heightMap[p_76615_3_ << 4 | p_76615_1_] & 255;
		int var5 = var4;
		if(p_76615_2_ > var4)
		{
			var5 = p_76615_2_;
		}
		while(var5 > 0 && getBlockLightOpacity(p_76615_1_, var5 - 1, p_76615_3_) == 0)
		{
			--var5;
		}
		if(var5 != var4)
		{
			worldObj.markBlocksDirtyVertical(p_76615_1_ + xPosition * 16, p_76615_3_ + zPosition * 16, var5, var4);
			heightMap[p_76615_3_ << 4 | p_76615_1_] = var5;
			int var6 = xPosition * 16 + p_76615_1_;
			int var7 = zPosition * 16 + p_76615_3_;
			int var8;
			int var12;
			if(!worldObj.provider.hasNoSky)
			{
				ExtendedBlockStorage var9;
				if(var5 < var4)
				{
					for(var8 = var5; var8 < var4; ++var8)
					{
						var9 = storageArrays[var8 >> 4];
						if(var9 != null)
						{
							var9.setExtSkylightValue(p_76615_1_, var8 & 15, p_76615_3_, 15);
							worldObj.markBlockForRenderUpdate((xPosition << 4) + p_76615_1_, var8, (zPosition << 4) + p_76615_3_);
						}
					}
				} else
				{
					for(var8 = var4; var8 < var5; ++var8)
					{
						var9 = storageArrays[var8 >> 4];
						if(var9 != null)
						{
							var9.setExtSkylightValue(p_76615_1_, var8 & 15, p_76615_3_, 0);
							worldObj.markBlockForRenderUpdate((xPosition << 4) + p_76615_1_, var8, (zPosition << 4) + p_76615_3_);
						}
					}
				}
				var8 = 15;
				while(var5 > 0 && var8 > 0)
				{
					--var5;
					var12 = getBlockLightOpacity(p_76615_1_, var5, p_76615_3_);
					if(var12 == 0)
					{
						var12 = 1;
					}
					var8 -= var12;
					if(var8 < 0)
					{
						var8 = 0;
					}
					ExtendedBlockStorage var10 = storageArrays[var5 >> 4];
					if(var10 != null)
					{
						var10.setExtSkylightValue(p_76615_1_, var5 & 15, p_76615_3_, var8);
					}
				}
			}
			var8 = heightMap[p_76615_3_ << 4 | p_76615_1_];
			var12 = var4;
			int var13 = var8;
			if(var8 < var4)
			{
				var12 = var8;
				var13 = var4;
			}
			if(var8 < heightMapMinimum)
			{
				heightMapMinimum = var8;
			}
			if(!worldObj.provider.hasNoSky)
			{
				updateSkylightNeighborHeight(var6 - 1, var7, var12, var13);
				updateSkylightNeighborHeight(var6 + 1, var7, var12, var13);
				updateSkylightNeighborHeight(var6, var7 - 1, var12, var13);
				updateSkylightNeighborHeight(var6, var7 + 1, var12, var13);
				updateSkylightNeighborHeight(var6, var7, var12, var13);
			}
			isModified = true;
		}
	}
	
	public void removeChunkBlockTileEntity(int p_76627_1_, int p_76627_2_, int p_76627_3_)
	{
		ChunkPosition var4 = new ChunkPosition(p_76627_1_, p_76627_2_, p_76627_3_);
		if(isChunkLoaded)
		{
			TileEntity var5 = (TileEntity) chunkTileEntityMap.remove(var4);
			if(var5 != null)
			{
				var5.invalidate();
			}
		}
	}
	
	public void removeEntity(Entity p_76622_1_)
	{
		removeEntityAtIndex(p_76622_1_, p_76622_1_.chunkCoordY);
	}
	
	public void removeEntityAtIndex(Entity p_76608_1_, int p_76608_2_)
	{
		if(p_76608_2_ < 0)
		{
			p_76608_2_ = 0;
		}
		if(p_76608_2_ >= entityLists.length)
		{
			p_76608_2_ = entityLists.length - 1;
		}
		entityLists[p_76608_2_].remove(p_76608_1_);
	}
	
	public void resetRelightChecks()
	{
		queuedLightChecks = 0;
	}
	
	public void setBiomeArray(byte[] p_76616_1_)
	{
		blockBiomeArray = p_76616_1_;
	}
	
	public boolean setBlockIDWithMetadata(int p_76592_1_, int p_76592_2_, int p_76592_3_, int p_76592_4_, int p_76592_5_)
	{
		int var6 = p_76592_3_ << 4 | p_76592_1_;
		if(p_76592_2_ >= precipitationHeightMap[var6] - 1)
		{
			precipitationHeightMap[var6] = -999;
		}
		int var7 = heightMap[var6];
		int var8 = getBlockID(p_76592_1_, p_76592_2_, p_76592_3_);
		int var9 = getBlockMetadata(p_76592_1_, p_76592_2_, p_76592_3_);
		if(var8 == p_76592_4_ && var9 == p_76592_5_) return false;
		else
		{
			ExtendedBlockStorage var10 = storageArrays[p_76592_2_ >> 4];
			boolean var11 = false;
			if(var10 == null)
			{
				if(p_76592_4_ == 0) return false;
				var10 = storageArrays[p_76592_2_ >> 4] = new ExtendedBlockStorage(p_76592_2_ >> 4 << 4, !worldObj.provider.hasNoSky);
				var11 = p_76592_2_ >= var7;
			}
			int var12 = xPosition * 16 + p_76592_1_;
			int var13 = zPosition * 16 + p_76592_3_;
			if(var8 != 0 && !worldObj.isRemote)
			{
				Block.blocksList[var8].onSetBlockIDWithMetaData(worldObj, var12, p_76592_2_, var13, var9);
			}
			var10.setExtBlockID(p_76592_1_, p_76592_2_ & 15, p_76592_3_, p_76592_4_);
			if(var8 != 0)
			{
				if(!worldObj.isRemote)
				{
					Block.blocksList[var8].breakBlock(worldObj, var12, p_76592_2_, var13, var8, var9);
				} else if(Block.blocksList[var8] instanceof ITileEntityProvider && var8 != p_76592_4_)
				{
					worldObj.removeBlockTileEntity(var12, p_76592_2_, var13);
				}
			}
			if(var10.getExtBlockID(p_76592_1_, p_76592_2_ & 15, p_76592_3_) != p_76592_4_) return false;
			else
			{
				var10.setExtBlockMetadata(p_76592_1_, p_76592_2_ & 15, p_76592_3_, p_76592_5_);
				if(var11)
				{
					generateSkylightMap();
				} else
				{
					if(Block.lightOpacity[p_76592_4_ & 4095] > 0)
					{
						if(p_76592_2_ >= var7)
						{
							relightBlock(p_76592_1_, p_76592_2_ + 1, p_76592_3_);
						}
					} else if(p_76592_2_ == var7 - 1)
					{
						relightBlock(p_76592_1_, p_76592_2_, p_76592_3_);
					}
					propagateSkylightOcclusion(p_76592_1_, p_76592_3_);
				}
				TileEntity var14;
				if(p_76592_4_ != 0)
				{
					if(!worldObj.isRemote)
					{
						Block.blocksList[p_76592_4_].onBlockAdded(worldObj, var12, p_76592_2_, var13);
					}
					if(Block.blocksList[p_76592_4_] instanceof ITileEntityProvider)
					{
						var14 = getChunkBlockTileEntity(p_76592_1_, p_76592_2_, p_76592_3_);
						if(var14 == null)
						{
							var14 = ((ITileEntityProvider) Block.blocksList[p_76592_4_]).createNewTileEntity(worldObj);
							worldObj.setBlockTileEntity(var12, p_76592_2_, var13, var14);
						}
						if(var14 != null)
						{
							var14.updateContainingBlockInfo();
						}
					}
				} else if(var8 > 0 && Block.blocksList[var8] instanceof ITileEntityProvider)
				{
					var14 = getChunkBlockTileEntity(p_76592_1_, p_76592_2_, p_76592_3_);
					if(var14 != null)
					{
						var14.updateContainingBlockInfo();
					}
				}
				isModified = true;
				return true;
			}
		}
	}
	
	public boolean setBlockMetadata(int p_76589_1_, int p_76589_2_, int p_76589_3_, int p_76589_4_)
	{
		ExtendedBlockStorage var5 = storageArrays[p_76589_2_ >> 4];
		if(var5 == null) return false;
		else
		{
			int var6 = var5.getExtBlockMetadata(p_76589_1_, p_76589_2_ & 15, p_76589_3_);
			if(var6 == p_76589_4_) return false;
			else
			{
				isModified = true;
				var5.setExtBlockMetadata(p_76589_1_, p_76589_2_ & 15, p_76589_3_, p_76589_4_);
				int var7 = var5.getExtBlockID(p_76589_1_, p_76589_2_ & 15, p_76589_3_);
				if(var7 > 0 && Block.blocksList[var7] instanceof ITileEntityProvider)
				{
					TileEntity var8 = getChunkBlockTileEntity(p_76589_1_, p_76589_2_, p_76589_3_);
					if(var8 != null)
					{
						var8.updateContainingBlockInfo();
						var8.blockMetadata = p_76589_4_;
					}
				}
				return true;
			}
		}
	}
	
	public void setChunkBlockTileEntity(int p_76604_1_, int p_76604_2_, int p_76604_3_, TileEntity p_76604_4_)
	{
		ChunkPosition var5 = new ChunkPosition(p_76604_1_, p_76604_2_, p_76604_3_);
		p_76604_4_.setWorldObj(worldObj);
		p_76604_4_.xCoord = xPosition * 16 + p_76604_1_;
		p_76604_4_.yCoord = p_76604_2_;
		p_76604_4_.zCoord = zPosition * 16 + p_76604_3_;
		if(getBlockID(p_76604_1_, p_76604_2_, p_76604_3_) != 0 && Block.blocksList[getBlockID(p_76604_1_, p_76604_2_, p_76604_3_)] instanceof ITileEntityProvider)
		{
			if(chunkTileEntityMap.containsKey(var5))
			{
				((TileEntity) chunkTileEntityMap.get(var5)).invalidate();
			}
			p_76604_4_.validate();
			chunkTileEntityMap.put(var5, p_76604_4_);
		}
	}
	
	public void setChunkModified()
	{
		isModified = true;
	}
	
	public void setLightValue(EnumSkyBlock p_76633_1_, int p_76633_2_, int p_76633_3_, int p_76633_4_, int p_76633_5_)
	{
		ExtendedBlockStorage var6 = storageArrays[p_76633_3_ >> 4];
		if(var6 == null)
		{
			var6 = storageArrays[p_76633_3_ >> 4] = new ExtendedBlockStorage(p_76633_3_ >> 4 << 4, !worldObj.provider.hasNoSky);
			generateSkylightMap();
		}
		isModified = true;
		if(p_76633_1_ == EnumSkyBlock.Sky)
		{
			if(!worldObj.provider.hasNoSky)
			{
				var6.setExtSkylightValue(p_76633_2_, p_76633_3_ & 15, p_76633_4_, p_76633_5_);
			}
		} else if(p_76633_1_ == EnumSkyBlock.Block)
		{
			var6.setExtBlocklightValue(p_76633_2_, p_76633_3_ & 15, p_76633_4_, p_76633_5_);
		}
	}
	
	public void setStorageArrays(ExtendedBlockStorage[] p_76602_1_)
	{
		storageArrays = p_76602_1_;
	}
	
	public void updateSkylight()
	{
		if(isGapLightingUpdated && !worldObj.provider.hasNoSky)
		{
			updateSkylight_do();
		}
	}
	
	private void updateSkylight_do()
	{
		worldObj.theProfiler.startSection("recheckGaps");
		if(worldObj.doChunksNearChunkExist(xPosition * 16 + 8, 0, zPosition * 16 + 8, 16))
		{
			for(int var1 = 0; var1 < 16; ++var1)
			{
				for(int var2 = 0; var2 < 16; ++var2)
				{
					if(updateSkylightColumns[var1 + var2 * 16])
					{
						updateSkylightColumns[var1 + var2 * 16] = false;
						int var3 = getHeightValue(var1, var2);
						int var4 = xPosition * 16 + var1;
						int var5 = zPosition * 16 + var2;
						int var6 = worldObj.getChunkHeightMapMinimum(var4 - 1, var5);
						int var7 = worldObj.getChunkHeightMapMinimum(var4 + 1, var5);
						int var8 = worldObj.getChunkHeightMapMinimum(var4, var5 - 1);
						int var9 = worldObj.getChunkHeightMapMinimum(var4, var5 + 1);
						if(var7 < var6)
						{
							var6 = var7;
						}
						if(var8 < var6)
						{
							var6 = var8;
						}
						if(var9 < var6)
						{
							var6 = var9;
						}
						checkSkylightNeighborHeight(var4, var5, var6);
						checkSkylightNeighborHeight(var4 - 1, var5, var3);
						checkSkylightNeighborHeight(var4 + 1, var5, var3);
						checkSkylightNeighborHeight(var4, var5 - 1, var3);
						checkSkylightNeighborHeight(var4, var5 + 1, var3);
					}
				}
			}
			isGapLightingUpdated = false;
		}
		worldObj.theProfiler.endSection();
	}
	
	private void updateSkylightNeighborHeight(int p_76609_1_, int p_76609_2_, int p_76609_3_, int p_76609_4_)
	{
		if(p_76609_4_ > p_76609_3_ && worldObj.doChunksNearChunkExist(p_76609_1_, 0, p_76609_2_, 16))
		{
			for(int var5 = p_76609_3_; var5 < p_76609_4_; ++var5)
			{
				worldObj.updateLightByType(EnumSkyBlock.Sky, p_76609_1_, var5, p_76609_2_);
			}
			isModified = true;
		}
	}
}
