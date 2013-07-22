package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderClient implements IChunkProvider
{
	private Chunk blankChunk;
	private LongHashMap chunkMapping = new LongHashMap();
	private List chunkListing = new ArrayList();
	private World worldObj;
	
	public ChunkProviderClient(World p_i3112_1_)
	{
		blankChunk = new EmptyChunk(p_i3112_1_, 0, 0);
		worldObj = p_i3112_1_;
	}
	
	@Override public boolean canSave()
	{
		return false;
	}
	
	@Override public boolean chunkExists(int p_73149_1_, int p_73149_2_)
	{
		return true;
	}
	
	@Override public ChunkPosition findClosestStructure(World p_73150_1_, String p_73150_2_, int p_73150_3_, int p_73150_4_, int p_73150_5_)
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
	
	@Override public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_)
	{
		return null;
	}
	
	@Override public Chunk loadChunk(int p_73158_1_, int p_73158_2_)
	{
		Chunk var3 = new Chunk(worldObj, p_73158_1_, p_73158_2_);
		chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(p_73158_1_, p_73158_2_), var3);
		var3.isChunkLoaded = true;
		return var3;
	}
	
	@Override public String makeString()
	{
		return "MultiplayerChunkCache: " + chunkMapping.getNumHashElements();
	}
	
	@Override public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_)
	{
	}
	
	@Override public Chunk provideChunk(int p_73154_1_, int p_73154_2_)
	{
		Chunk var3 = (Chunk) chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(p_73154_1_, p_73154_2_));
		return var3 == null ? blankChunk : var3;
	}
	
	@Override public void recreateStructures(int p_82695_1_, int p_82695_2_)
	{
	}
	
	@Override public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_)
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
