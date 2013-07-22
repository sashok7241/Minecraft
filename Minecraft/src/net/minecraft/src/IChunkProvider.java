package net.minecraft.src;

import java.util.List;

public interface IChunkProvider
{
	boolean canSave();
	
	boolean chunkExists(int var1, int var2);
	
	ChunkPosition findClosestStructure(World var1, String var2, int var3, int var4, int var5);
	
	void func_104112_b();
	
	int getLoadedChunkCount();
	
	List getPossibleCreatures(EnumCreatureType var1, int var2, int var3, int var4);
	
	Chunk loadChunk(int var1, int var2);
	
	String makeString();
	
	void populate(IChunkProvider var1, int var2, int var3);
	
	Chunk provideChunk(int var1, int var2);
	
	void recreateStructures(int var1, int var2);
	
	boolean saveChunks(boolean var1, IProgressUpdate var2);
	
	boolean unloadQueuedChunks();
}
