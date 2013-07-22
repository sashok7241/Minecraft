package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import net.minecraft.client.Minecraft;

public class WorldClient extends World
{
	private NetClientHandler sendQueue;
	private ChunkProviderClient clientChunkProvider;
	private IntHashMap entityHashSet = new IntHashMap();
	private Set entityList = new HashSet();
	private Set entitySpawnQueue = new HashSet();
	private final Minecraft mc = Minecraft.getMinecraft();
	private final Set previousActiveChunkSet = new HashSet();
	
	public WorldClient(NetClientHandler par1NetClientHandler, WorldSettings par2WorldSettings, int par3, int par4, Profiler par5Profiler, ILogAgent par6ILogAgent)
	{
		super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(par3), par2WorldSettings, par5Profiler, par6ILogAgent);
		sendQueue = par1NetClientHandler;
		difficultySetting = par4;
		this.setSpawnLocation(8, 64, 8);
		mapStorage = par1NetClientHandler.mapStorage;
	}
	
	public void addEntityToWorld(int par1, Entity par2Entity)
	{
		Entity var3 = getEntityByID(par1);
		if(var3 != null)
		{
			removeEntity(var3);
		}
		entityList.add(par2Entity);
		par2Entity.entityId = par1;
		if(!spawnEntityInWorld(par2Entity))
		{
			entitySpawnQueue.add(par2Entity);
		}
		entityHashSet.addKey(par1, par2Entity);
	}
	
	@Override public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport)
	{
		CrashReportCategory var2 = super.addWorldInfoToCrashReport(par1CrashReport);
		var2.addCrashSectionCallable("Forced entities", new CallableMPL1(this));
		var2.addCrashSectionCallable("Retry entities", new CallableMPL2(this));
		return var2;
	}
	
	@Override protected IChunkProvider createChunkProvider()
	{
		clientChunkProvider = new ChunkProviderClient(this);
		return clientChunkProvider;
	}
	
	public void doPreChunk(int par1, int par2, boolean par3)
	{
		if(par3)
		{
			clientChunkProvider.loadChunk(par1, par2);
		} else
		{
			clientChunkProvider.unloadChunk(par1, par2);
		}
		if(!par3)
		{
			markBlockRangeForRenderUpdate(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
		}
	}
	
	public void doVoidFogParticles(int par1, int par2, int par3)
	{
		byte var4 = 16;
		Random var5 = new Random();
		for(int var6 = 0; var6 < 1000; ++var6)
		{
			int var7 = par1 + rand.nextInt(var4) - rand.nextInt(var4);
			int var8 = par2 + rand.nextInt(var4) - rand.nextInt(var4);
			int var9 = par3 + rand.nextInt(var4) - rand.nextInt(var4);
			int var10 = getBlockId(var7, var8, var9);
			if(var10 == 0 && rand.nextInt(8) > var8 && provider.getWorldHasVoidParticles())
			{
				spawnParticle("depthsuspend", var7 + rand.nextFloat(), var8 + rand.nextFloat(), var9 + rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			} else if(var10 > 0)
			{
				Block.blocksList[var10].randomDisplayTick(this, var7, var8, var9, var5);
			}
		}
	}
	
	@Override public IUpdatePlayerListBox func_82735_a(EntityMinecart par1EntityMinecart)
	{
		return new SoundUpdaterMinecart(mc.sndManager, par1EntityMinecart, mc.thePlayer);
	}
	
	@Override public void func_92088_a(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound)
	{
		mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, par1, par3, par5, par7, par9, par11, mc.effectRenderer, par13NBTTagCompound));
	}
	
	public void func_96443_a(Scoreboard par1Scoreboard)
	{
		worldScoreboard = par1Scoreboard;
	}
	
	@Override public Entity getEntityByID(int par1)
	{
		return par1 == mc.thePlayer.entityId ? mc.thePlayer : (Entity) entityHashSet.lookup(par1);
	}
	
	public void invalidateBlockReceiveRegion(int par1, int par2, int par3, int par4, int par5, int par6)
	{
	}
	
	@Override protected void onEntityAdded(Entity par1Entity)
	{
		super.onEntityAdded(par1Entity);
		if(entitySpawnQueue.contains(par1Entity))
		{
			entitySpawnQueue.remove(par1Entity);
		}
	}
	
	@Override protected void onEntityRemoved(Entity par1Entity)
	{
		super.onEntityRemoved(par1Entity);
		if(entityList.contains(par1Entity))
		{
			if(par1Entity.isEntityAlive())
			{
				entitySpawnQueue.add(par1Entity);
			} else
			{
				entityList.remove(par1Entity);
			}
		}
	}
	
	@Override public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10)
	{
		float var11 = 16.0F;
		if(par8 > 1.0F)
		{
			var11 *= par8;
		}
		double var12 = mc.renderViewEntity.getDistanceSq(par1, par3, par5);
		if(var12 < var11 * var11)
		{
			if(par10 && var12 > 100.0D)
			{
				double var14 = Math.sqrt(var12) / 40.0D;
				mc.sndManager.func_92070_a(par7Str, (float) par1, (float) par3, (float) par5, par8, par9, (int) Math.round(var14 * 20.0D));
			} else
			{
				mc.sndManager.playSound(par7Str, (float) par1, (float) par3, (float) par5, par8, par9);
			}
		}
	}
	
	public void removeAllEntities()
	{
		loadedEntityList.removeAll(unloadedEntityList);
		int var1;
		Entity var2;
		int var3;
		int var4;
		for(var1 = 0; var1 < unloadedEntityList.size(); ++var1)
		{
			var2 = (Entity) unloadedEntityList.get(var1);
			var3 = var2.chunkCoordX;
			var4 = var2.chunkCoordZ;
			if(var2.addedToChunk && chunkExists(var3, var4))
			{
				getChunkFromChunkCoords(var3, var4).removeEntity(var2);
			}
		}
		for(var1 = 0; var1 < unloadedEntityList.size(); ++var1)
		{
			onEntityRemoved((Entity) unloadedEntityList.get(var1));
		}
		unloadedEntityList.clear();
		for(var1 = 0; var1 < loadedEntityList.size(); ++var1)
		{
			var2 = (Entity) loadedEntityList.get(var1);
			if(var2.ridingEntity != null)
			{
				if(!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2)
				{
					continue;
				}
				var2.ridingEntity.riddenByEntity = null;
				var2.ridingEntity = null;
			}
			if(var2.isDead)
			{
				var3 = var2.chunkCoordX;
				var4 = var2.chunkCoordZ;
				if(var2.addedToChunk && chunkExists(var3, var4))
				{
					getChunkFromChunkCoords(var3, var4).removeEntity(var2);
				}
				loadedEntityList.remove(var1--);
				onEntityRemoved(var2);
			}
		}
	}
	
	@Override public void removeEntity(Entity par1Entity)
	{
		super.removeEntity(par1Entity);
		entityList.remove(par1Entity);
	}
	
	public Entity removeEntityFromWorld(int par1)
	{
		Entity var2 = (Entity) entityHashSet.removeObject(par1);
		if(var2 != null)
		{
			entityList.remove(var2);
			removeEntity(var2);
		}
		return var2;
	}
	
	@Override public void sendQuittingDisconnectingPacket()
	{
		sendQueue.quitWithPacket(new Packet255KickDisconnect("Quitting"));
	}
	
	public boolean setBlockAndMetadataAndInvalidate(int par1, int par2, int par3, int par4, int par5)
	{
		invalidateBlockReceiveRegion(par1, par2, par3, par1, par2, par3);
		return super.setBlock(par1, par2, par3, par4, par5, 3);
	}
	
	@Override public boolean spawnEntityInWorld(Entity par1Entity)
	{
		boolean var2 = super.spawnEntityInWorld(par1Entity);
		entityList.add(par1Entity);
		if(!var2)
		{
			entitySpawnQueue.add(par1Entity);
		}
		return var2;
	}
	
	@Override public void tick()
	{
		super.tick();
		func_82738_a(getTotalWorldTime() + 1L);
		setWorldTime(getWorldTime() + 1L);
		theProfiler.startSection("reEntryProcessing");
		for(int var1 = 0; var1 < 10 && !entitySpawnQueue.isEmpty(); ++var1)
		{
			Entity var2 = (Entity) entitySpawnQueue.iterator().next();
			entitySpawnQueue.remove(var2);
			if(!loadedEntityList.contains(var2))
			{
				spawnEntityInWorld(var2);
			}
		}
		theProfiler.endStartSection("connection");
		sendQueue.processReadPackets();
		theProfiler.endStartSection("chunkCache");
		clientChunkProvider.unloadQueuedChunks();
		theProfiler.endStartSection("tiles");
		tickBlocksAndAmbiance();
		theProfiler.endSection();
	}
	
	@Override protected void tickBlocksAndAmbiance()
	{
		super.tickBlocksAndAmbiance();
		previousActiveChunkSet.retainAll(activeChunkSet);
		if(previousActiveChunkSet.size() == activeChunkSet.size())
		{
			previousActiveChunkSet.clear();
		}
		int var1 = 0;
		Iterator var2 = activeChunkSet.iterator();
		while(var2.hasNext())
		{
			ChunkCoordIntPair var3 = (ChunkCoordIntPair) var2.next();
			if(!previousActiveChunkSet.contains(var3))
			{
				int var4 = var3.chunkXPos * 16;
				int var5 = var3.chunkZPos * 16;
				theProfiler.startSection("getChunk");
				Chunk var6 = getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
				moodSoundAndLightCheck(var4, var5, var6);
				theProfiler.endSection();
				previousActiveChunkSet.add(var3);
				++var1;
				if(var1 >= 10) return;
			}
		}
	}
	
	@Override protected void updateWeather()
	{
		if(!provider.hasNoSky)
		{
			prevRainingStrength = rainingStrength;
			if(worldInfo.isRaining())
			{
				rainingStrength = (float) (rainingStrength + 0.01D);
			} else
			{
				rainingStrength = (float) (rainingStrength - 0.01D);
			}
			if(rainingStrength < 0.0F)
			{
				rainingStrength = 0.0F;
			}
			if(rainingStrength > 1.0F)
			{
				rainingStrength = 1.0F;
			}
			prevThunderingStrength = thunderingStrength;
			if(worldInfo.isThundering())
			{
				thunderingStrength = (float) (thunderingStrength + 0.01D);
			} else
			{
				thunderingStrength = (float) (thunderingStrength - 0.01D);
			}
			if(thunderingStrength < 0.0F)
			{
				thunderingStrength = 0.0F;
			}
			if(thunderingStrength > 1.0F)
			{
				thunderingStrength = 1.0F;
			}
		}
	}
	
	static Set getEntityList(WorldClient par0WorldClient)
	{
		return par0WorldClient.entityList;
	}
	
	static Set getEntitySpawnQueue(WorldClient par0WorldClient)
	{
		return par0WorldClient.entitySpawnQueue;
	}
}
