package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.server.MinecraftServer;

public class WorldServer extends World
{
	private final MinecraftServer mcServer;
	private final EntityTracker theEntityTracker;
	private final PlayerManager thePlayerManager;
	private Set pendingTickListEntriesHashSet;
	private TreeSet pendingTickListEntriesTreeSet;
	public ChunkProviderServer theChunkProviderServer;
	public boolean canNotSave;
	private boolean allPlayersSleeping;
	private int updateEntityTick;
	private final Teleporter field_85177_Q;
	private final SpawnerAnimals field_135059_Q = new SpawnerAnimals();
	private ServerBlockEventList[] blockEventCache = new ServerBlockEventList[] { new ServerBlockEventList((ServerBlockEvent) null), new ServerBlockEventList((ServerBlockEvent) null) };
	private int blockEventCacheIndex;
	private static final WeightedRandomChestContent[] bonusChestContent = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.stick.itemID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.planks.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.wood.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Item.axeStone.itemID, 0, 1, 1, 3), new WeightedRandomChestContent(Item.axeWood.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.pickaxeStone.itemID, 0, 1, 1, 3), new WeightedRandomChestContent(Item.pickaxeWood.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 2, 3, 5), new WeightedRandomChestContent(Item.bread.itemID, 0, 2, 3, 3) };
	private List pendingTickListEntriesThisTick = new ArrayList();
	private IntHashMap entityIdMap;
	
	public WorldServer(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, Profiler par6Profiler, ILogAgent par7ILogAgent)
	{
		super(par2ISaveHandler, par3Str, par5WorldSettings, WorldProvider.getProviderForDimension(par4), par6Profiler, par7ILogAgent);
		mcServer = par1MinecraftServer;
		theEntityTracker = new EntityTracker(this);
		thePlayerManager = new PlayerManager(this, par1MinecraftServer.getConfigurationManager().getViewDistance());
		if(entityIdMap == null)
		{
			entityIdMap = new IntHashMap();
		}
		if(pendingTickListEntriesHashSet == null)
		{
			pendingTickListEntriesHashSet = new HashSet();
		}
		if(pendingTickListEntriesTreeSet == null)
		{
			pendingTickListEntriesTreeSet = new TreeSet();
		}
		field_85177_Q = new Teleporter(this);
		worldScoreboard = new ServerScoreboard(par1MinecraftServer);
		ScoreboardSaveData var8 = (ScoreboardSaveData) mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
		if(var8 == null)
		{
			var8 = new ScoreboardSaveData();
			mapStorage.setData("scoreboard", var8);
		}
		var8.func_96499_a(worldScoreboard);
		((ServerScoreboard) worldScoreboard).func_96547_a(var8);
	}
	
	@Override public void addBlockEvent(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		BlockEventData var7 = new BlockEventData(par1, par2, par3, par4, par5, par6);
		Iterator var8 = blockEventCache[blockEventCacheIndex].iterator();
		BlockEventData var9;
		do
		{
			if(!var8.hasNext())
			{
				blockEventCache[blockEventCacheIndex].add(var7);
				return;
			}
			var9 = (BlockEventData) var8.next();
		} while(!var9.equals(var7));
	}
	
	@Override public boolean addWeatherEffect(Entity par1Entity)
	{
		if(super.addWeatherEffect(par1Entity))
		{
			mcServer.getConfigurationManager().sendToAllNear(par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512.0D, provider.dimensionId, new Packet71Weather(par1Entity));
			return true;
		} else return false;
	}
	
	public boolean areAllPlayersAsleep()
	{
		if(allPlayersSleeping && !isRemote)
		{
			Iterator var1 = playerEntities.iterator();
			EntityPlayer var2;
			do
			{
				if(!var1.hasNext()) return true;
				var2 = (EntityPlayer) var1.next();
			} while(var2.isPlayerFullyAsleep());
			return false;
		} else return false;
	}
	
	@Override public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
	{
		return !mcServer.func_96290_a(this, par2, par3, par4, par1EntityPlayer);
	}
	
	protected void createBonusChest()
	{
		WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(bonusChestContent, 10);
		for(int var2 = 0; var2 < 10; ++var2)
		{
			int var3 = worldInfo.getSpawnX() + rand.nextInt(6) - rand.nextInt(6);
			int var4 = worldInfo.getSpawnZ() + rand.nextInt(6) - rand.nextInt(6);
			int var5 = getTopSolidOrLiquidBlock(var3, var4) + 1;
			if(var1.generate(this, rand, var3, var5, var4))
			{
				break;
			}
		}
	}
	
	@Override protected IChunkProvider createChunkProvider()
	{
		IChunkLoader var1 = saveHandler.getChunkLoader(provider);
		theChunkProviderServer = new ChunkProviderServer(this, var1, provider.createChunkGenerator());
		return theChunkProviderServer;
	}
	
	protected void createSpawnPosition(WorldSettings par1WorldSettings)
	{
		if(!provider.canRespawnHere())
		{
			worldInfo.setSpawnPosition(0, provider.getAverageGroundLevel(), 0);
		} else
		{
			findingSpawnPoint = true;
			WorldChunkManager var2 = provider.worldChunkMgr;
			List var3 = var2.getBiomesToSpawnIn();
			Random var4 = new Random(getSeed());
			ChunkPosition var5 = var2.findBiomePosition(0, 0, 256, var3, var4);
			int var6 = 0;
			int var7 = provider.getAverageGroundLevel();
			int var8 = 0;
			if(var5 != null)
			{
				var6 = var5.x;
				var8 = var5.z;
			} else
			{
				getWorldLogAgent().logWarning("Unable to find spawn biome");
			}
			int var9 = 0;
			while(!provider.canCoordinateBeSpawn(var6, var8))
			{
				var6 += var4.nextInt(64) - var4.nextInt(64);
				var8 += var4.nextInt(64) - var4.nextInt(64);
				++var9;
				if(var9 == 1000)
				{
					break;
				}
			}
			worldInfo.setSpawnPosition(var6, var7, var8);
			findingSpawnPoint = false;
			if(par1WorldSettings.isBonusChestEnabled())
			{
				createBonusChest();
			}
		}
	}
	
	public void flush()
	{
		saveHandler.flush();
	}
	
	public void func_104140_m()
	{
		if(chunkProvider.canSave())
		{
			chunkProvider.func_104112_b();
		}
	}
	
	public List getAllTileEntityInBox(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		ArrayList var7 = new ArrayList();
		for(int var8 = 0; var8 < loadedTileEntityList.size(); ++var8)
		{
			TileEntity var9 = (TileEntity) loadedTileEntityList.get(var8);
			if(var9.xCoord >= par1 && var9.yCoord >= par2 && var9.zCoord >= par3 && var9.xCoord < par4 && var9.yCoord < par5 && var9.zCoord < par6)
			{
				var7.add(var9);
			}
		}
		return var7;
	}
	
	public Teleporter getDefaultTeleporter()
	{
		return field_85177_Q;
	}
	
	@Override public Entity getEntityByID(int par1)
	{
		return (Entity) entityIdMap.lookup(par1);
	}
	
	public EntityTracker getEntityTracker()
	{
		return theEntityTracker;
	}
	
	public ChunkCoordinates getEntrancePortalLocation()
	{
		return provider.getEntrancePortalLocation();
	}
	
	public MinecraftServer getMinecraftServer()
	{
		return mcServer;
	}
	
	@Override public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
	{
		ArrayList var3 = null;
		ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
		int var5 = (var4.chunkXPos << 4) - 2;
		int var6 = var5 + 16 + 2;
		int var7 = (var4.chunkZPos << 4) - 2;
		int var8 = var7 + 16 + 2;
		for(int var9 = 0; var9 < 2; ++var9)
		{
			Iterator var10;
			if(var9 == 0)
			{
				var10 = pendingTickListEntriesTreeSet.iterator();
			} else
			{
				var10 = pendingTickListEntriesThisTick.iterator();
				if(!pendingTickListEntriesThisTick.isEmpty())
				{
					System.out.println(pendingTickListEntriesThisTick.size());
				}
			}
			while(var10.hasNext())
			{
				NextTickListEntry var11 = (NextTickListEntry) var10.next();
				if(var11.xCoord >= var5 && var11.xCoord < var6 && var11.zCoord >= var7 && var11.zCoord < var8)
				{
					if(par2)
					{
						pendingTickListEntriesHashSet.remove(var11);
						var10.remove();
					}
					if(var3 == null)
					{
						var3 = new ArrayList();
					}
					var3.add(var11);
				}
			}
		}
		return var3;
	}
	
	public PlayerManager getPlayerManager()
	{
		return thePlayerManager;
	}
	
	@Override protected void initialize(WorldSettings par1WorldSettings)
	{
		if(entityIdMap == null)
		{
			entityIdMap = new IntHashMap();
		}
		if(pendingTickListEntriesHashSet == null)
		{
			pendingTickListEntriesHashSet = new HashSet();
		}
		if(pendingTickListEntriesTreeSet == null)
		{
			pendingTickListEntriesTreeSet = new TreeSet();
		}
		createSpawnPosition(par1WorldSettings);
		super.initialize(par1WorldSettings);
	}
	
	@Override public boolean isBlockTickScheduledThisTick(int par1, int par2, int par3, int par4)
	{
		NextTickListEntry var5 = new NextTickListEntry(par1, par2, par3, par4);
		return pendingTickListEntriesThisTick.contains(var5);
	}
	
	@Override public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
	{
		Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
		var11.isFlaming = par9;
		var11.isSmoking = par10;
		var11.doExplosionA();
		var11.doExplosionB(false);
		if(!par10)
		{
			var11.affectedBlockPositions.clear();
		}
		Iterator var12 = playerEntities.iterator();
		while(var12.hasNext())
		{
			EntityPlayer var13 = (EntityPlayer) var12.next();
			if(var13.getDistanceSq(par2, par4, par6) < 4096.0D)
			{
				((EntityPlayerMP) var13).playerNetServerHandler.sendPacketToPlayer(new Packet60Explosion(par2, par4, par6, par8, var11.affectedBlockPositions, (Vec3) var11.func_77277_b().get(var13)));
			}
		}
		return var11;
	}
	
	private boolean onBlockEventReceived(BlockEventData par1BlockEventData)
	{
		int var2 = getBlockId(par1BlockEventData.getX(), par1BlockEventData.getY(), par1BlockEventData.getZ());
		return var2 == par1BlockEventData.getBlockID() ? Block.blocksList[var2].onBlockEventReceived(this, par1BlockEventData.getX(), par1BlockEventData.getY(), par1BlockEventData.getZ(), par1BlockEventData.getEventID(), par1BlockEventData.getEventParameter()) : false;
	}
	
	@Override protected void onEntityAdded(Entity par1Entity)
	{
		super.onEntityAdded(par1Entity);
		entityIdMap.addKey(par1Entity.entityId, par1Entity);
		Entity[] var2 = par1Entity.getParts();
		if(var2 != null)
		{
			for(Entity element : var2)
			{
				entityIdMap.addKey(element.entityId, element);
			}
		}
	}
	
	@Override protected void onEntityRemoved(Entity par1Entity)
	{
		super.onEntityRemoved(par1Entity);
		entityIdMap.removeObject(par1Entity.entityId);
		Entity[] var2 = par1Entity.getParts();
		if(var2 != null)
		{
			for(Entity element : var2)
			{
				entityIdMap.removeObject(element.entityId);
			}
		}
	}
	
	private void resetRainAndThunder()
	{
		worldInfo.setRainTime(0);
		worldInfo.setRaining(false);
		worldInfo.setThunderTime(0);
		worldInfo.setThundering(false);
	}
	
	public void resetUpdateEntityTick()
	{
		updateEntityTick = 0;
	}
	
	public void saveAllChunks(boolean par1, IProgressUpdate par2IProgressUpdate) throws MinecraftException
	{
		if(chunkProvider.canSave())
		{
			if(par2IProgressUpdate != null)
			{
				par2IProgressUpdate.displayProgressMessage("Saving level");
			}
			saveLevel();
			if(par2IProgressUpdate != null)
			{
				par2IProgressUpdate.resetProgresAndWorkingMessage("Saving chunks");
			}
			chunkProvider.saveChunks(par1, par2IProgressUpdate);
		}
	}
	
	protected void saveLevel() throws MinecraftException
	{
		checkSessionLock();
		saveHandler.saveWorldInfoWithPlayer(worldInfo, mcServer.getConfigurationManager().getHostPlayerData());
		mapStorage.saveAllData();
	}
	
	@Override public void scheduleBlockUpdate(int par1, int par2, int par3, int par4, int par5)
	{
		scheduleBlockUpdateWithPriority(par1, par2, par3, par4, par5, 0);
	}
	
	@Override public void scheduleBlockUpdateFromLoad(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
		var7.setPriority(par6);
		if(par4 > 0)
		{
			var7.setScheduledTime(par5 + worldInfo.getWorldTotalTime());
		}
		if(!pendingTickListEntriesHashSet.contains(var7))
		{
			pendingTickListEntriesHashSet.add(var7);
			pendingTickListEntriesTreeSet.add(var7);
		}
	}
	
	@Override public void scheduleBlockUpdateWithPriority(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
		byte var8 = 0;
		if(scheduledUpdatesAreImmediate && par4 > 0)
		{
			if(Block.blocksList[par4].func_82506_l())
			{
				var8 = 8;
				if(checkChunksExist(var7.xCoord - var8, var7.yCoord - var8, var7.zCoord - var8, var7.xCoord + var8, var7.yCoord + var8, var7.zCoord + var8))
				{
					int var9 = getBlockId(var7.xCoord, var7.yCoord, var7.zCoord);
					if(var9 == var7.blockID && var9 > 0)
					{
						Block.blocksList[var9].updateTick(this, var7.xCoord, var7.yCoord, var7.zCoord, rand);
					}
				}
				return;
			}
			par5 = 1;
		}
		if(checkChunksExist(par1 - var8, par2 - var8, par3 - var8, par1 + var8, par2 + var8, par3 + var8))
		{
			if(par4 > 0)
			{
				var7.setScheduledTime(par5 + worldInfo.getWorldTotalTime());
				var7.setPriority(par6);
			}
			if(!pendingTickListEntriesHashSet.contains(var7))
			{
				pendingTickListEntriesHashSet.add(var7);
				pendingTickListEntriesTreeSet.add(var7);
			}
		}
	}
	
	private void sendAndApplyBlockEvents()
	{
		while(!blockEventCache[blockEventCacheIndex].isEmpty())
		{
			int var1 = blockEventCacheIndex;
			blockEventCacheIndex ^= 1;
			Iterator var2 = blockEventCache[var1].iterator();
			while(var2.hasNext())
			{
				BlockEventData var3 = (BlockEventData) var2.next();
				if(onBlockEventReceived(var3))
				{
					mcServer.getConfigurationManager().sendToAllNear(var3.getX(), var3.getY(), var3.getZ(), 64.0D, provider.dimensionId, new Packet54PlayNoteBlock(var3.getX(), var3.getY(), var3.getZ(), var3.getBlockID(), var3.getEventID(), var3.getEventParameter()));
				}
			}
			blockEventCache[var1].clear();
		}
	}
	
	@Override public void setEntityState(Entity par1Entity, byte par2)
	{
		Packet38EntityStatus var3 = new Packet38EntityStatus(par1Entity.entityId, par2);
		getEntityTracker().sendPacketToAllAssociatedPlayers(par1Entity, var3);
	}
	
	@Override public void setSpawnLocation()
	{
		if(worldInfo.getSpawnY() <= 0)
		{
			worldInfo.setSpawnY(64);
		}
		int var1 = worldInfo.getSpawnX();
		int var2 = worldInfo.getSpawnZ();
		int var3 = 0;
		while(getFirstUncoveredBlock(var1, var2) == 0)
		{
			var1 += rand.nextInt(8) - rand.nextInt(8);
			var2 += rand.nextInt(8) - rand.nextInt(8);
			++var3;
			if(var3 == 10000)
			{
				break;
			}
		}
		worldInfo.setSpawnX(var1);
		worldInfo.setSpawnZ(var2);
	}
	
	public SpawnListEntry spawnRandomCreature(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		List var5 = getChunkProvider().getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
		return var5 != null && !var5.isEmpty() ? (SpawnListEntry) WeightedRandom.getRandomItem(rand, var5) : null;
	}
	
	@Override public void tick()
	{
		super.tick();
		if(getWorldInfo().isHardcoreModeEnabled() && difficultySetting < 3)
		{
			difficultySetting = 3;
		}
		provider.worldChunkMgr.cleanupCache();
		if(areAllPlayersAsleep())
		{
			if(getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
			{
				long var1 = worldInfo.getWorldTime() + 24000L;
				worldInfo.setWorldTime(var1 - var1 % 24000L);
			}
			wakeAllPlayers();
		}
		theProfiler.startSection("mobSpawner");
		if(getGameRules().getGameRuleBooleanValue("doMobSpawning"))
		{
			field_135059_Q.findChunksForSpawning(this, spawnHostileMobs, spawnPeacefulMobs, worldInfo.getWorldTotalTime() % 400L == 0L);
		}
		theProfiler.endStartSection("chunkSource");
		chunkProvider.unloadQueuedChunks();
		int var3 = calculateSkylightSubtracted(1.0F);
		if(var3 != skylightSubtracted)
		{
			skylightSubtracted = var3;
		}
		worldInfo.incrementTotalWorldTime(worldInfo.getWorldTotalTime() + 1L);
		if(getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
		{
			worldInfo.setWorldTime(worldInfo.getWorldTime() + 1L);
		}
		theProfiler.endStartSection("tickPending");
		tickUpdates(false);
		theProfiler.endStartSection("tickTiles");
		tickBlocksAndAmbiance();
		theProfiler.endStartSection("chunkMap");
		thePlayerManager.updatePlayerInstances();
		theProfiler.endStartSection("village");
		villageCollectionObj.tick();
		villageSiegeObj.tick();
		theProfiler.endStartSection("portalForcer");
		field_85177_Q.removeStalePortalLocations(getTotalWorldTime());
		theProfiler.endSection();
		sendAndApplyBlockEvents();
	}
	
	@Override protected void tickBlocksAndAmbiance()
	{
		super.tickBlocksAndAmbiance();
		int var1 = 0;
		int var2 = 0;
		Iterator var3 = activeChunkSet.iterator();
		while(var3.hasNext())
		{
			ChunkCoordIntPair var4 = (ChunkCoordIntPair) var3.next();
			int var5 = var4.chunkXPos * 16;
			int var6 = var4.chunkZPos * 16;
			theProfiler.startSection("getChunk");
			Chunk var7 = getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
			moodSoundAndLightCheck(var5, var6, var7);
			theProfiler.endStartSection("tickChunk");
			var7.updateSkylight();
			theProfiler.endStartSection("thunder");
			int var8;
			int var9;
			int var10;
			int var11;
			if(rand.nextInt(100000) == 0 && isRaining() && isThundering())
			{
				updateLCG = updateLCG * 3 + 1013904223;
				var8 = updateLCG >> 2;
				var9 = var5 + (var8 & 15);
				var10 = var6 + (var8 >> 8 & 15);
				var11 = getPrecipitationHeight(var9, var10);
				if(canLightningStrikeAt(var9, var11, var10))
				{
					addWeatherEffect(new EntityLightningBolt(this, var9, var11, var10));
				}
			}
			theProfiler.endStartSection("iceandsnow");
			int var13;
			if(rand.nextInt(16) == 0)
			{
				updateLCG = updateLCG * 3 + 1013904223;
				var8 = updateLCG >> 2;
				var9 = var8 & 15;
				var10 = var8 >> 8 & 15;
				var11 = getPrecipitationHeight(var9 + var5, var10 + var6);
				if(isBlockFreezableNaturally(var9 + var5, var11 - 1, var10 + var6))
				{
					this.setBlock(var9 + var5, var11 - 1, var10 + var6, Block.ice.blockID);
				}
				if(isRaining() && canSnowAt(var9 + var5, var11, var10 + var6))
				{
					this.setBlock(var9 + var5, var11, var10 + var6, Block.snow.blockID);
				}
				if(isRaining())
				{
					BiomeGenBase var12 = getBiomeGenForCoords(var9 + var5, var10 + var6);
					if(var12.canSpawnLightningBolt())
					{
						var13 = getBlockId(var9 + var5, var11 - 1, var10 + var6);
						if(var13 != 0)
						{
							Block.blocksList[var13].fillWithRain(this, var9 + var5, var11 - 1, var10 + var6);
						}
					}
				}
			}
			theProfiler.endStartSection("tickTiles");
			ExtendedBlockStorage[] var19 = var7.getBlockStorageArray();
			var9 = var19.length;
			for(var10 = 0; var10 < var9; ++var10)
			{
				ExtendedBlockStorage var21 = var19[var10];
				if(var21 != null && var21.getNeedsRandomTick())
				{
					for(int var20 = 0; var20 < 3; ++var20)
					{
						updateLCG = updateLCG * 3 + 1013904223;
						var13 = updateLCG >> 2;
						int var14 = var13 & 15;
						int var15 = var13 >> 8 & 15;
						int var16 = var13 >> 16 & 15;
						int var17 = var21.getExtBlockID(var14, var16, var15);
						++var2;
						Block var18 = Block.blocksList[var17];
						if(var18 != null && var18.getTickRandomly())
						{
							++var1;
							var18.updateTick(this, var14 + var5, var16 + var21.getYLocation(), var15 + var6, rand);
						}
					}
				}
			}
			theProfiler.endSection();
		}
	}
	
	@Override public boolean tickUpdates(boolean par1)
	{
		int var2 = pendingTickListEntriesTreeSet.size();
		if(var2 != pendingTickListEntriesHashSet.size()) throw new IllegalStateException("TickNextTick list out of synch");
		else
		{
			if(var2 > 1000)
			{
				var2 = 1000;
			}
			theProfiler.startSection("cleaning");
			NextTickListEntry var4;
			for(int var3 = 0; var3 < var2; ++var3)
			{
				var4 = (NextTickListEntry) pendingTickListEntriesTreeSet.first();
				if(!par1 && var4.scheduledTime > worldInfo.getWorldTotalTime())
				{
					break;
				}
				pendingTickListEntriesTreeSet.remove(var4);
				pendingTickListEntriesHashSet.remove(var4);
				pendingTickListEntriesThisTick.add(var4);
			}
			theProfiler.endSection();
			theProfiler.startSection("ticking");
			Iterator var14 = pendingTickListEntriesThisTick.iterator();
			while(var14.hasNext())
			{
				var4 = (NextTickListEntry) var14.next();
				var14.remove();
				byte var5 = 0;
				if(checkChunksExist(var4.xCoord - var5, var4.yCoord - var5, var4.zCoord - var5, var4.xCoord + var5, var4.yCoord + var5, var4.zCoord + var5))
				{
					int var6 = getBlockId(var4.xCoord, var4.yCoord, var4.zCoord);
					if(var6 > 0 && Block.isAssociatedBlockID(var6, var4.blockID))
					{
						try
						{
							Block.blocksList[var6].updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, rand);
						} catch(Throwable var13)
						{
							CrashReport var8 = CrashReport.makeCrashReport(var13, "Exception while ticking a block");
							CrashReportCategory var9 = var8.makeCategory("Block being ticked");
							int var10;
							try
							{
								var10 = getBlockMetadata(var4.xCoord, var4.yCoord, var4.zCoord);
							} catch(Throwable var12)
							{
								var10 = -1;
							}
							CrashReportCategory.func_85068_a(var9, var4.xCoord, var4.yCoord, var4.zCoord, var6, var10);
							throw new ReportedException(var8);
						}
					}
				} else
				{
					scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.blockID, 0);
				}
			}
			theProfiler.endSection();
			pendingTickListEntriesThisTick.clear();
			return !pendingTickListEntriesTreeSet.isEmpty();
		}
	}
	
	@Override public void updateAllPlayersSleepingFlag()
	{
		allPlayersSleeping = !playerEntities.isEmpty();
		Iterator var1 = playerEntities.iterator();
		while(var1.hasNext())
		{
			EntityPlayer var2 = (EntityPlayer) var1.next();
			if(!var2.isPlayerSleeping())
			{
				allPlayersSleeping = false;
				break;
			}
		}
	}
	
	@Override public void updateEntities()
	{
		if(playerEntities.isEmpty())
		{
			if(updateEntityTick++ >= 1200) return;
		} else
		{
			resetUpdateEntityTick();
		}
		super.updateEntities();
	}
	
	@Override public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
	{
		if(!mcServer.getCanSpawnAnimals() && (par1Entity instanceof EntityAnimal || par1Entity instanceof EntityWaterMob))
		{
			par1Entity.setDead();
		}
		if(!mcServer.getCanSpawnNPCs() && par1Entity instanceof INpc)
		{
			par1Entity.setDead();
		}
		super.updateEntityWithOptionalForce(par1Entity, par2);
	}
	
	@Override protected void updateWeather()
	{
		boolean var1 = isRaining();
		super.updateWeather();
		if(var1 != isRaining())
		{
			if(var1)
			{
				mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(2, 0));
			} else
			{
				mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(1, 0));
			}
		}
	}
	
	protected void wakeAllPlayers()
	{
		allPlayersSleeping = false;
		Iterator var1 = playerEntities.iterator();
		while(var1.hasNext())
		{
			EntityPlayer var2 = (EntityPlayer) var1.next();
			if(var2.isPlayerSleeping())
			{
				var2.wakeUpPlayer(false, false, true);
			}
		}
		resetRainAndThunder();
	}
}
