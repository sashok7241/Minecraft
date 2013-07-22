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
	public long field_111204_q;
	private int queuedLightChecks;
	
	public Chunk(World par1World, byte[] par2ArrayOfByte, int par3, int par4)
	{
		this(par1World, par3, par4);
		int var5 = par2ArrayOfByte.length / 256;
		for(int var6 = 0; var6 < 16; ++var6)
		{
			for(int var7 = 0; var7 < 16; ++var7)
			{
				for(int var8 = 0; var8 < var5; ++var8)
				{
					byte var9 = par2ArrayOfByte[var6 << 11 | var7 << 7 | var8];
					if(var9 != 0)
					{
						int var10 = var8 >> 4;
						if(storageArrays[var10] == null)
						{
							storageArrays[var10] = new ExtendedBlockStorage(var10 << 4, !par1World.provider.hasNoSky);
						}
						storageArrays[var10].setExtBlockID(var6, var8 & 15, var7, var9);
					}
				}
			}
		}
	}
	
	public Chunk(World par1World, int par2, int par3)
	{
		storageArrays = new ExtendedBlockStorage[16];
		blockBiomeArray = new byte[256];
		precipitationHeightMap = new int[256];
		updateSkylightColumns = new boolean[256];
		chunkTileEntityMap = new HashMap();
		queuedLightChecks = 4096;
		entityLists = new List[16];
		worldObj = par1World;
		xPosition = par2;
		zPosition = par3;
		heightMap = new int[256];
		for(int var4 = 0; var4 < entityLists.length; ++var4)
		{
			entityLists[var4] = new ArrayList();
		}
		Arrays.fill(precipitationHeightMap, -999);
		Arrays.fill(blockBiomeArray, (byte) -1);
	}
	
	public void addEntity(Entity par1Entity)
	{
		hasEntities = true;
		int var2 = MathHelper.floor_double(par1Entity.posX / 16.0D);
		int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
		if(var2 != xPosition || var3 != zPosition)
		{
			worldObj.getWorldLogAgent().logSevere("Wrong location! " + par1Entity);
			Thread.dumpStack();
		}
		int var4 = MathHelper.floor_double(par1Entity.posY / 16.0D);
		if(var4 < 0)
		{
			var4 = 0;
		}
		if(var4 >= entityLists.length)
		{
			var4 = entityLists.length - 1;
		}
		par1Entity.addedToChunk = true;
		par1Entity.chunkCoordX = xPosition;
		par1Entity.chunkCoordY = var4;
		par1Entity.chunkCoordZ = zPosition;
		entityLists[var4].add(par1Entity);
	}
	
	public void addTileEntity(TileEntity par1TileEntity)
	{
		int var2 = par1TileEntity.xCoord - xPosition * 16;
		int var3 = par1TileEntity.yCoord;
		int var4 = par1TileEntity.zCoord - zPosition * 16;
		setChunkBlockTileEntity(var2, var3, var4, par1TileEntity);
		if(isChunkLoaded)
		{
			worldObj.loadedTileEntityList.add(par1TileEntity);
		}
	}
	
	public boolean canBlockSeeTheSky(int par1, int par2, int par3)
	{
		return par2 >= heightMap[par3 << 4 | par1];
	}
	
	private void checkSkylightNeighborHeight(int par1, int par2, int par3)
	{
		int var4 = worldObj.getHeightValue(par1, par2);
		if(var4 > par3)
		{
			updateSkylightNeighborHeight(par1, par2, par3, var4 + 1);
		} else if(var4 < par3)
		{
			updateSkylightNeighborHeight(par1, par2, var4, par3 + 1);
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
	
	public boolean getAreLevelsEmpty(int par1, int par2)
	{
		if(par1 < 0)
		{
			par1 = 0;
		}
		if(par2 >= 256)
		{
			par2 = 255;
		}
		for(int var3 = par1; var3 <= par2; var3 += 16)
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
	
	public BiomeGenBase getBiomeGenForWorldCoords(int par1, int par2, WorldChunkManager par3WorldChunkManager)
	{
		int var4 = blockBiomeArray[par2 << 4 | par1] & 255;
		if(var4 == 255)
		{
			BiomeGenBase var5 = par3WorldChunkManager.getBiomeGenAt((xPosition << 4) + par1, (zPosition << 4) + par2);
			var4 = var5.biomeID;
			blockBiomeArray[par2 << 4 | par1] = (byte) (var4 & 255);
		}
		return BiomeGenBase.biomeList[var4] == null ? BiomeGenBase.plains : BiomeGenBase.biomeList[var4];
	}
	
	public int getBlockID(int par1, int par2, int par3)
	{
		if(par2 >> 4 >= storageArrays.length) return 0;
		else
		{
			ExtendedBlockStorage var4 = storageArrays[par2 >> 4];
			return var4 != null ? var4.getExtBlockID(par1, par2 & 15, par3) : 0;
		}
	}
	
	public int getBlockLightOpacity(int par1, int par2, int par3)
	{
		return Block.lightOpacity[getBlockID(par1, par2, par3)];
	}
	
	public int getBlockLightValue(int par1, int par2, int par3, int par4)
	{
		ExtendedBlockStorage var5 = storageArrays[par2 >> 4];
		if(var5 == null) return !worldObj.provider.hasNoSky && par4 < EnumSkyBlock.Sky.defaultLightValue ? EnumSkyBlock.Sky.defaultLightValue - par4 : 0;
		else
		{
			int var6 = worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par1, par2 & 15, par3);
			if(var6 > 0)
			{
				isLit = true;
			}
			var6 -= par4;
			int var7 = var5.getExtBlocklightValue(par1, par2 & 15, par3);
			if(var7 > var6)
			{
				var6 = var7;
			}
			return var6;
		}
	}
	
	public int getBlockMetadata(int par1, int par2, int par3)
	{
		if(par2 >> 4 >= storageArrays.length) return 0;
		else
		{
			ExtendedBlockStorage var4 = storageArrays[par2 >> 4];
			return var4 != null ? var4.getExtBlockMetadata(par1, par2 & 15, par3) : 0;
		}
	}
	
	public ExtendedBlockStorage[] getBlockStorageArray()
	{
		return storageArrays;
	}
	
	public TileEntity getChunkBlockTileEntity(int par1, int par2, int par3)
	{
		ChunkPosition var4 = new ChunkPosition(par1, par2, par3);
		TileEntity var5 = (TileEntity) chunkTileEntityMap.get(var4);
		if(var5 == null)
		{
			int var6 = getBlockID(par1, par2, par3);
			if(var6 <= 0 || !Block.blocksList[var6].hasTileEntity()) return null;
			if(var5 == null)
			{
				var5 = ((ITileEntityProvider) Block.blocksList[var6]).createNewTileEntity(worldObj);
				worldObj.setBlockTileEntity(xPosition * 16 + par1, par2, zPosition * 16 + par3, var5);
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
	
	public void getEntitiesOfTypeWithinAAAB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
	{
		int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0D) / 16.0D);
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
				if(par1Class.isAssignableFrom(var10.getClass()) && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10)))
				{
					par3List.add(var10);
				}
			}
		}
	}
	
	public void getEntitiesWithinAABBForEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
	{
		int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0D) / 16.0D);
		int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0D) / 16.0D);
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
				if(var10 != par1Entity && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10)))
				{
					par3List.add(var10);
					Entity[] var11 = var10.getParts();
					if(var11 != null)
					{
						for(Entity element : var11)
						{
							var10 = element;
							if(var10 != par1Entity && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10)))
							{
								par3List.add(var10);
							}
						}
					}
				}
			}
		}
	}
	
	public int getHeightValue(int par1, int par2)
	{
		return heightMap[par2 << 4 | par1];
	}
	
	public int getPrecipitationHeight(int par1, int par2)
	{
		int var3 = par1 | par2 << 4;
		int var4 = precipitationHeightMap[var3];
		if(var4 == -999)
		{
			int var5 = getTopFilledSegment() + 15;
			var4 = -1;
			while(var5 > 0 && var4 == -1)
			{
				int var6 = getBlockID(par1, var5, par2);
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
	
	public Random getRandomWithSeed(long par1)
	{
		return new Random(worldObj.getSeed() + xPosition * xPosition * 4987142 + xPosition * 5947611 + zPosition * zPosition * 4392871L + zPosition * 389711 ^ par1);
	}
	
	public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
	{
		ExtendedBlockStorage var5 = storageArrays[par3 >> 4];
		return var5 == null ? canBlockSeeTheSky(par2, par3, par4) ? par1EnumSkyBlock.defaultLightValue : 0 : par1EnumSkyBlock == EnumSkyBlock.Sky ? worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par2, par3 & 15, par4) : par1EnumSkyBlock == EnumSkyBlock.Block ? var5.getExtBlocklightValue(par2, par3 & 15, par4) : par1EnumSkyBlock.defaultLightValue;
	}
	
	public int getTopFilledSegment()
	{
		for(int var1 = storageArrays.length - 1; var1 >= 0; --var1)
		{
			if(storageArrays[var1] != null) return storageArrays[var1].getYLocation();
		}
		return 0;
	}
	
	public boolean isAtLocation(int par1, int par2)
	{
		return par1 == xPosition && par2 == zPosition;
	}
	
	public boolean isEmpty()
	{
		return false;
	}
	
	public boolean needsSaving(boolean par1)
	{
		if(par1)
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
			Iterator var2 = entityList.iterator();
			while(var2.hasNext())
			{
				Entity var3 = (Entity) var2.next();
				var3.func_110123_P();
			}
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
	
	public void populateChunk(IChunkProvider par1IChunkProvider, IChunkProvider par2IChunkProvider, int par3, int par4)
	{
		if(!isTerrainPopulated && par1IChunkProvider.chunkExists(par3 + 1, par4 + 1) && par1IChunkProvider.chunkExists(par3, par4 + 1) && par1IChunkProvider.chunkExists(par3 + 1, par4))
		{
			par1IChunkProvider.populate(par2IChunkProvider, par3, par4);
		}
		if(par1IChunkProvider.chunkExists(par3 - 1, par4) && !par1IChunkProvider.provideChunk(par3 - 1, par4).isTerrainPopulated && par1IChunkProvider.chunkExists(par3 - 1, par4 + 1) && par1IChunkProvider.chunkExists(par3, par4 + 1) && par1IChunkProvider.chunkExists(par3 - 1, par4 + 1))
		{
			par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4);
		}
		if(par1IChunkProvider.chunkExists(par3, par4 - 1) && !par1IChunkProvider.provideChunk(par3, par4 - 1).isTerrainPopulated && par1IChunkProvider.chunkExists(par3 + 1, par4 - 1) && par1IChunkProvider.chunkExists(par3 + 1, par4 - 1) && par1IChunkProvider.chunkExists(par3 + 1, par4))
		{
			par1IChunkProvider.populate(par2IChunkProvider, par3, par4 - 1);
		}
		if(par1IChunkProvider.chunkExists(par3 - 1, par4 - 1) && !par1IChunkProvider.provideChunk(par3 - 1, par4 - 1).isTerrainPopulated && par1IChunkProvider.chunkExists(par3, par4 - 1) && par1IChunkProvider.chunkExists(par3 - 1, par4))
		{
			par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4 - 1);
		}
	}
	
	private void propagateSkylightOcclusion(int par1, int par2)
	{
		updateSkylightColumns[par1 + par2 * 16] = true;
		isGapLightingUpdated = true;
	}
	
	private void relightBlock(int par1, int par2, int par3)
	{
		int var4 = heightMap[par3 << 4 | par1] & 255;
		int var5 = var4;
		if(par2 > var4)
		{
			var5 = par2;
		}
		while(var5 > 0 && getBlockLightOpacity(par1, var5 - 1, par3) == 0)
		{
			--var5;
		}
		if(var5 != var4)
		{
			worldObj.markBlocksDirtyVertical(par1 + xPosition * 16, par3 + zPosition * 16, var5, var4);
			heightMap[par3 << 4 | par1] = var5;
			int var6 = xPosition * 16 + par1;
			int var7 = zPosition * 16 + par3;
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
							var9.setExtSkylightValue(par1, var8 & 15, par3, 15);
							worldObj.markBlockForRenderUpdate((xPosition << 4) + par1, var8, (zPosition << 4) + par3);
						}
					}
				} else
				{
					for(var8 = var4; var8 < var5; ++var8)
					{
						var9 = storageArrays[var8 >> 4];
						if(var9 != null)
						{
							var9.setExtSkylightValue(par1, var8 & 15, par3, 0);
							worldObj.markBlockForRenderUpdate((xPosition << 4) + par1, var8, (zPosition << 4) + par3);
						}
					}
				}
				var8 = 15;
				while(var5 > 0 && var8 > 0)
				{
					--var5;
					var12 = getBlockLightOpacity(par1, var5, par3);
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
						var10.setExtSkylightValue(par1, var5 & 15, par3, var8);
					}
				}
			}
			var8 = heightMap[par3 << 4 | par1];
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
	
	public void removeChunkBlockTileEntity(int par1, int par2, int par3)
	{
		ChunkPosition var4 = new ChunkPosition(par1, par2, par3);
		if(isChunkLoaded)
		{
			TileEntity var5 = (TileEntity) chunkTileEntityMap.remove(var4);
			if(var5 != null)
			{
				var5.invalidate();
			}
		}
	}
	
	public void removeEntity(Entity par1Entity)
	{
		removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
	}
	
	public void removeEntityAtIndex(Entity par1Entity, int par2)
	{
		if(par2 < 0)
		{
			par2 = 0;
		}
		if(par2 >= entityLists.length)
		{
			par2 = entityLists.length - 1;
		}
		entityLists[par2].remove(par1Entity);
	}
	
	public void resetRelightChecks()
	{
		queuedLightChecks = 0;
	}
	
	public void setBiomeArray(byte[] par1ArrayOfByte)
	{
		blockBiomeArray = par1ArrayOfByte;
	}
	
	public boolean setBlockIDWithMetadata(int par1, int par2, int par3, int par4, int par5)
	{
		int var6 = par3 << 4 | par1;
		if(par2 >= precipitationHeightMap[var6] - 1)
		{
			precipitationHeightMap[var6] = -999;
		}
		int var7 = heightMap[var6];
		int var8 = getBlockID(par1, par2, par3);
		int var9 = getBlockMetadata(par1, par2, par3);
		if(var8 == par4 && var9 == par5) return false;
		else
		{
			ExtendedBlockStorage var10 = storageArrays[par2 >> 4];
			boolean var11 = false;
			if(var10 == null)
			{
				if(par4 == 0) return false;
				var10 = storageArrays[par2 >> 4] = new ExtendedBlockStorage(par2 >> 4 << 4, !worldObj.provider.hasNoSky);
				var11 = par2 >= var7;
			}
			int var12 = xPosition * 16 + par1;
			int var13 = zPosition * 16 + par3;
			if(var8 != 0 && !worldObj.isRemote)
			{
				Block.blocksList[var8].onSetBlockIDWithMetaData(worldObj, var12, par2, var13, var9);
			}
			var10.setExtBlockID(par1, par2 & 15, par3, par4);
			if(var8 != 0)
			{
				if(!worldObj.isRemote)
				{
					Block.blocksList[var8].breakBlock(worldObj, var12, par2, var13, var8, var9);
				} else if(Block.blocksList[var8] instanceof ITileEntityProvider && var8 != par4)
				{
					worldObj.removeBlockTileEntity(var12, par2, var13);
				}
			}
			if(var10.getExtBlockID(par1, par2 & 15, par3) != par4) return false;
			else
			{
				var10.setExtBlockMetadata(par1, par2 & 15, par3, par5);
				if(var11)
				{
					generateSkylightMap();
				} else
				{
					if(Block.lightOpacity[par4 & 4095] > 0)
					{
						if(par2 >= var7)
						{
							relightBlock(par1, par2 + 1, par3);
						}
					} else if(par2 == var7 - 1)
					{
						relightBlock(par1, par2, par3);
					}
					propagateSkylightOcclusion(par1, par3);
				}
				TileEntity var14;
				if(par4 != 0)
				{
					if(!worldObj.isRemote)
					{
						Block.blocksList[par4].onBlockAdded(worldObj, var12, par2, var13);
					}
					if(Block.blocksList[par4] instanceof ITileEntityProvider)
					{
						var14 = getChunkBlockTileEntity(par1, par2, par3);
						if(var14 == null)
						{
							var14 = ((ITileEntityProvider) Block.blocksList[par4]).createNewTileEntity(worldObj);
							worldObj.setBlockTileEntity(var12, par2, var13, var14);
						}
						if(var14 != null)
						{
							var14.updateContainingBlockInfo();
						}
					}
				} else if(var8 > 0 && Block.blocksList[var8] instanceof ITileEntityProvider)
				{
					var14 = getChunkBlockTileEntity(par1, par2, par3);
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
	
	public boolean setBlockMetadata(int par1, int par2, int par3, int par4)
	{
		ExtendedBlockStorage var5 = storageArrays[par2 >> 4];
		if(var5 == null) return false;
		else
		{
			int var6 = var5.getExtBlockMetadata(par1, par2 & 15, par3);
			if(var6 == par4) return false;
			else
			{
				isModified = true;
				var5.setExtBlockMetadata(par1, par2 & 15, par3, par4);
				int var7 = var5.getExtBlockID(par1, par2 & 15, par3);
				if(var7 > 0 && Block.blocksList[var7] instanceof ITileEntityProvider)
				{
					TileEntity var8 = getChunkBlockTileEntity(par1, par2, par3);
					if(var8 != null)
					{
						var8.updateContainingBlockInfo();
						var8.blockMetadata = par4;
					}
				}
				return true;
			}
		}
	}
	
	public void setChunkBlockTileEntity(int par1, int par2, int par3, TileEntity par4TileEntity)
	{
		ChunkPosition var5 = new ChunkPosition(par1, par2, par3);
		par4TileEntity.setWorldObj(worldObj);
		par4TileEntity.xCoord = xPosition * 16 + par1;
		par4TileEntity.yCoord = par2;
		par4TileEntity.zCoord = zPosition * 16 + par3;
		if(getBlockID(par1, par2, par3) != 0 && Block.blocksList[getBlockID(par1, par2, par3)] instanceof ITileEntityProvider)
		{
			if(chunkTileEntityMap.containsKey(var5))
			{
				((TileEntity) chunkTileEntityMap.get(var5)).invalidate();
			}
			par4TileEntity.validate();
			chunkTileEntityMap.put(var5, par4TileEntity);
		}
	}
	
	public void setChunkModified()
	{
		isModified = true;
	}
	
	public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
	{
		ExtendedBlockStorage var6 = storageArrays[par3 >> 4];
		if(var6 == null)
		{
			var6 = storageArrays[par3 >> 4] = new ExtendedBlockStorage(par3 >> 4 << 4, !worldObj.provider.hasNoSky);
			generateSkylightMap();
		}
		isModified = true;
		if(par1EnumSkyBlock == EnumSkyBlock.Sky)
		{
			if(!worldObj.provider.hasNoSky)
			{
				var6.setExtSkylightValue(par2, par3 & 15, par4, par5);
			}
		} else if(par1EnumSkyBlock == EnumSkyBlock.Block)
		{
			var6.setExtBlocklightValue(par2, par3 & 15, par4, par5);
		}
	}
	
	public void setStorageArrays(ExtendedBlockStorage[] par1ArrayOfExtendedBlockStorage)
	{
		storageArrays = par1ArrayOfExtendedBlockStorage;
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
	
	private void updateSkylightNeighborHeight(int par1, int par2, int par3, int par4)
	{
		if(par4 > par3 && worldObj.doChunksNearChunkExist(par1, 0, par2, 16))
		{
			for(int var5 = par3; var5 < par4; ++var5)
			{
				worldObj.updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
			}
			isModified = true;
		}
	}
}
