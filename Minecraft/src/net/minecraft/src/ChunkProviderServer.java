package net.minecraft.src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChunkProviderServer implements IChunkProvider
{
	private Set chunksToUnload = new HashSet();
	private Chunk defaultEmptyChunk;
	private IChunkProvider currentChunkProvider;
	private IChunkLoader currentChunkLoader;
	public boolean loadChunkOnProvideRequest = true;
	private LongHashMap loadedChunkHashMap = new LongHashMap();
	private List loadedChunks = new ArrayList();
	private WorldServer worldObj;
	
	public ChunkProviderServer(WorldServer p_i3393_1_, IChunkLoader p_i3393_2_, IChunkProvider p_i3393_3_)
	{
		defaultEmptyChunk = new EmptyChunk(p_i3393_1_, 0, 0);
		worldObj = p_i3393_1_;
		currentChunkLoader = p_i3393_2_;
		currentChunkProvider = p_i3393_3_;
	}
	
	@Override public boolean canSave()
	{
		return !worldObj.canNotSave;
	}
	
	@Override public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return loadedChunkHashMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(p_73149_1_, p_73149_2_));
	}
	
	@Override public ChunkPosition findClosestStructure(World p_73150_1_, String p_73150_2_, int p_73150_3_, int p_73150_4_, int p_73150_5_)
	{
		return currentChunkProvider.findClosestStructure(p_73150_1_, p_73150_2_, p_73150_3_, p_73150_4_, p_73150_5_);
	}
	
	@Override public void func_104112_b()
	{
		if(currentChunkLoader != null)
		{
			currentChunkLoader.saveExtraData();
		}
	}
	
	@Override public int getLoadedChunkCount()
	{
		return loadedChunkHashMap.getNumHashElements();
	}
	
	@Override public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
	{
		return currentChunkProvider.getPossibleCreatures(p_73155_1_, p_73155_2_, p_73155_3_, p_73155_4_);
	}
	
	@Override public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		long var3 = ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_);
		chunksToUnload.remove(Long.valueOf(var3));
		Chunk var5 = (Chunk) loadedChunkHashMap.getValueByKey(var3);
		if(var5 == null)
		{
			var5 = safeLoadChunk(p_73158_1_, p_73158_2_);
			if(var5 == null)
			{
				if(currentChunkProvider == null)
				{
					var5 = defaultEmptyChunk;
				} else
				{
					try
					{
						var5 = currentChunkProvider.provideChunk(p_73158_1_, p_73158_2_);
					} catch(Throwable var9)
					{
						CrashReport var7 = CrashReport.makeCrashReport(var9, "Exception generating new chunk");
						CrashReportCategory var8 = var7.makeCategory("Chunk to be generated");
						var8.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(p_73158_1_), Integer.valueOf(p_73158_2_) }));
						var8.addCrashSection("Position hash", Long.valueOf(var3));
						var8.addCrashSection("Generator", currentChunkProvider.makeString());
						throw new ReportedException(var7);
					}
				}
			}
			loadedChunkHashMap.add(var3, var5);
			loadedChunks.add(var5);
			if(var5 != null)
			{
				var5.onChunkLoad();
			}
			var5.populateChunk(this, this, p_73158_1_, p_73158_2_);
		}
		return var5;
	}
	
	@Override public String makeString()
	{
		return "ServerChunkCache: " + loadedChunkHashMap.getNumHashElements() + " Drop: " + chunksToUnload.size();
	}
	
	@Override public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
		Chunk var4 = provideChunk(p_73153_2_, p_73153_3_);
		if(!var4.isTerrainPopulated)
		{
			var4.isTerrainPopulated = true;
			if(currentChunkProvider != null)
			{
				currentChunkProvider.populate(p_73153_1_, p_73153_2_, p_73153_3_);
				var4.setChunkModified();
			}
		}
	}
	
	@Override public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		Chunk var3 = (Chunk) loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
		return var3 == null ? !worldObj.findingSpawnPoint && !loadChunkOnProvideRequest ? defaultEmptyChunk : loadChunk(p_73154_1_, p_73154_2_) : var3;
	}
	
	@Override public void recreateStructures(int p_82695_1_, int p_82695_2_)
	{
	}
	
	private Chunk safeLoadChunk(int p_73239_1_, int p_73239_2_)
	{
		if(currentChunkLoader == null) return null;
		else
		{
			try
			{
				Chunk var3 = currentChunkLoader.loadChunk(worldObj, p_73239_1_, p_73239_2_);
				if(var3 != null)
				{
					var3.lastSaveTime = worldObj.getTotalWorldTime();
					if(currentChunkProvider != null)
					{
						currentChunkProvider.recreateStructures(p_73239_1_, p_73239_2_);
					}
				}
				return var3;
			} catch(Exception var4)
			{
				var4.printStackTrace();
				return null;
			}
		}
	}
	
	private void safeSaveChunk(Chunk p_73242_1_)
	{
		if(currentChunkLoader != null)
		{
			try
			{
				p_73242_1_.lastSaveTime = worldObj.getTotalWorldTime();
				currentChunkLoader.saveChunk(worldObj, p_73242_1_);
			} catch(IOException var3)
			{
				var3.printStackTrace();
			} catch(MinecraftException var4)
			{
				var4.printStackTrace();
			}
		}
	}
	
	private void safeSaveExtraChunkData(Chunk p_73243_1_)
	{
		if(currentChunkLoader != null)
		{
			try
			{
				currentChunkLoader.saveExtraChunkData(worldObj, p_73243_1_);
			} catch(Exception var3)
			{
				var3.printStackTrace();
			}
		}
	}
	
	@Override public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
	{
		int var3 = 0;
		for(int var4 = 0; var4 < loadedChunks.size(); ++var4)
		{
			Chunk var5 = (Chunk) loadedChunks.get(var4);
			if(p_73151_1_)
			{
				safeSaveExtraChunkData(var5);
			}
			if(var5.needsSaving(p_73151_1_))
			{
				safeSaveChunk(var5);
				var5.isModified = false;
				++var3;
				if(var3 == 24 && !p_73151_1_) return false;
			}
		}
		return true;
	}
	
	public void unloadAllChunks()
	{
		Iterator var1 = loadedChunks.iterator();
		while(var1.hasNext())
		{
			Chunk var2 = (Chunk) var1.next();
			unloadChunksIfNotNearSpawn(var2.xPosition, var2.zPosition);
		}
	}
	
	public void unloadChunksIfNotNearSpawn(int p_73241_1_, int p_73241_2_)
	{
		if(worldObj.provider.canRespawnHere())
		{
			ChunkCoordinates var3 = worldObj.getSpawnPoint();
			int var4 = p_73241_1_ * 16 + 8 - var3.posX;
			int var5 = p_73241_2_ * 16 + 8 - var3.posZ;
			short var6 = 128;
			if(var4 < -var6 || var4 > var6 || var5 < -var6 || var5 > var6)
			{
				chunksToUnload.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_)));
			}
		} else
		{
			chunksToUnload.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_73241_1_, p_73241_2_)));
		}
	}
	
	@Override public boolean unloadQueuedChunks()
	{
		if(!worldObj.canNotSave)
		{
			for(int var1 = 0; var1 < 100; ++var1)
			{
				if(!chunksToUnload.isEmpty())
				{
					Long var2 = (Long) chunksToUnload.iterator().next();
					Chunk var3 = (Chunk) loadedChunkHashMap.getValueByKey(var2.longValue());
					var3.onChunkUnload();
					safeSaveChunk(var3);
					safeSaveExtraChunkData(var3);
					chunksToUnload.remove(var2);
					loadedChunkHashMap.remove(var2.longValue());
					loadedChunks.remove(var3);
				}
			}
			if(currentChunkLoader != null)
			{
				currentChunkLoader.chunkTick();
			}
		}
		return currentChunkProvider.unloadQueuedChunks();
	}
}
