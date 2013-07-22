package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderClient implements IChunkProvider
{
	private Chunk blankChunk;
	private LongHashMap chunkMapping = new LongHashMap();
	private List chunkListing = new ArrayList();
	private World worldObj;
	
	public ChunkProviderClient(World par1World)
	{
		blankChunk = new EmptyChunk(par1World, 0, 0);
		worldObj = par1World;
	}
	
	@Override public boolean canSave()
	{
		return false;
	}
	
	@Override public boolean chunkExists(int par1, int par2)
	{
		return true;
	}
	
	@Override public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
	{
		return null;
	}
	
	@Override public void func_104112_b()
	{
	}
	
	@Override public int getLoadedChunkCount()
	{
		return chunkListing.size();
	}
	
	@Override public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		return null;
	}
	
	@Override public Chunk loadChunk(int par1, int par2)
	{
		Chunk var3 = new Chunk(worldObj, par1, par2);
		chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(par1, par2), var3);
		var3.isChunkLoaded = true;
		return var3;
	}
	
	@Override public String makeString()
	{
		return "MultiplayerChunkCache: " + chunkMapping.getNumHashElements();
	}
	
	@Override public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
	{
	}
	
	@Override public Chunk provideChunk(int par1, int par2)
	{
		Chunk var3 = (Chunk) chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
		return var3 == null ? blankChunk : var3;
	}
	
	@Override public void recreateStructures(int par1, int par2)
	{
	}
	
	@Override public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
	{
		return true;
	}
	
	public void unloadChunk(int par1, int par2)
	{
		Chunk var3 = provideChunk(par1, par2);
		if(!var3.isEmpty())
		{
			var3.onChunkUnload();
		}
		chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(par1, par2));
		chunkListing.remove(var3);
	}
	
	@Override public boolean unloadQueuedChunks()
	{
		return false;
	}
}
