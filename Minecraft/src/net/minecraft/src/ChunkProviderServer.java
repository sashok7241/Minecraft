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
	
	public ChunkProviderServer(WorldServer par1WorldServer, IChunkLoader par2IChunkLoader, IChunkProvider par3IChunkProvider)
	{
		defaultEmptyChunk = new EmptyChunk(par1WorldServer, 0, 0);
		worldObj = par1WorldServer;
		currentChunkLoader = par2IChunkLoader;
		currentChunkProvider = par3IChunkProvider;
	}
	
	@Override public boolean canSave()
	{
		return !worldObj.canNotSave;
	}
	
	@Override public boolean chunkExists(int par1, int par2)
	{
		return loadedChunkHashMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
	}
	
	@Override public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
	{
		return currentChunkProvider.findClosestStructure(par1World, par2Str, par3, par4, par5);
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
	
	@Override public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		return currentChunkProvider.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
	}
	
	@Override public Chunk loadChunk(int par1, int par2)
	{
		long var3 = ChunkCoordIntPair.chunkXZ2Int(par1, par2);
		chunksToUnload.remove(Long.valueOf(var3));
		Chunk var5 = (Chunk) loadedChunkHashMap.getValueByKey(var3);
		if(var5 == null)
		{
			var5 = safeLoadChunk(par1, par2);
			if(var5 == null)
			{
				if(currentChunkProvider == null)
				{
					var5 = defaultEmptyChunk;
				} else
				{
					try
					{
						var5 = currentChunkProvider.provideChunk(par1, par2);
					} catch(Throwable var9)
					{
						CrashReport var7 = CrashReport.makeCrashReport(var9, "Exception generating new chunk");
						CrashReportCategory var8 = var7.makeCategory("Chunk to be generated");
						var8.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(par1), Integer.valueOf(par2) }));
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
			var5.populateChunk(this, this, par1, par2);
		}
		return var5;
	}
	
	@Override public String makeString()
	{
		return "ServerChunkCache: " + loadedChunkHashMap.getNumHashElements() + " Drop: " + chunksToUnload.size();
	}
	
	@Override public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
	{
		Chunk var4 = provideChunk(par2, par3);
		if(!var4.isTerrainPopulated)
		{
			var4.isTerrainPopulated = true;
			if(currentChunkProvider != null)
			{
				currentChunkProvider.populate(par1IChunkProvider, par2, par3);
				var4.setChunkModified();
			}
		}
	}
	
	@Override public Chunk provideChunk(int par1, int par2)
	{
		Chunk var3 = (Chunk) loadedChunkHashMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
		return var3 == null ? !worldObj.findingSpawnPoint && !loadChunkOnProvideRequest ? defaultEmptyChunk : loadChunk(par1, par2) : var3;
	}
	
	@Override public void recreateStructures(int par1, int par2)
	{
	}
	
	private Chunk safeLoadChunk(int par1, int par2)
	{
		if(currentChunkLoader == null) return null;
		else
		{
			try
			{
				Chunk var3 = currentChunkLoader.loadChunk(worldObj, par1, par2);
				if(var3 != null)
				{
					var3.lastSaveTime = worldObj.getTotalWorldTime();
					if(currentChunkProvider != null)
					{
						currentChunkProvider.recreateStructures(par1, par2);
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
	
	private void safeSaveChunk(Chunk par1Chunk)
	{
		if(currentChunkLoader != null)
		{
			try
			{
				par1Chunk.lastSaveTime = worldObj.getTotalWorldTime();
				currentChunkLoader.saveChunk(worldObj, par1Chunk);
			} catch(IOException var3)
			{
				var3.printStackTrace();
			} catch(MinecraftException var4)
			{
				var4.printStackTrace();
			}
		}
	}
	
	private void safeSaveExtraChunkData(Chunk par1Chunk)
	{
		if(currentChunkLoader != null)
		{
			try
			{
				currentChunkLoader.saveExtraChunkData(worldObj, par1Chunk);
			} catch(Exception var3)
			{
				var3.printStackTrace();
			}
		}
	}
	
	@Override public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
	{
		int var3 = 0;
		for(int var4 = 0; var4 < loadedChunks.size(); ++var4)
		{
			Chunk var5 = (Chunk) loadedChunks.get(var4);
			if(par1)
			{
				safeSaveExtraChunkData(var5);
			}
			if(var5.needsSaving(par1))
			{
				safeSaveChunk(var5);
				var5.isModified = false;
				++var3;
				if(var3 == 24 && !par1) return false;
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
	
	public void unloadChunksIfNotNearSpawn(int par1, int par2)
	{
		if(worldObj.provider.canRespawnHere())
		{
			ChunkCoordinates var3 = worldObj.getSpawnPoint();
			int var4 = par1 * 16 + 8 - var3.posX;
			int var5 = par2 * 16 + 8 - var3.posZ;
			short var6 = 128;
			if(var4 < -var6 || var4 > var6 || var5 < -var6 || var5 > var6)
			{
				chunksToUnload.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
			}
		} else
		{
			chunksToUnload.add(Long.valueOf(ChunkCoordIntPair.chunkXZ2Int(par1, par2)));
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
