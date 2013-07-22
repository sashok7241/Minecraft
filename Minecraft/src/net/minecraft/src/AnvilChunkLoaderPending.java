package net.minecraft.src;

class AnvilChunkLoaderPending
{
	public final ChunkCoordIntPair chunkCoordinate;
	public final NBTTagCompound nbtTags;
	
	public AnvilChunkLoaderPending(ChunkCoordIntPair par1ChunkCoordIntPair, NBTTagCompound par2NBTTagCompound)
	{
		chunkCoordinate = par1ChunkCoordIntPair;
		nbtTags = par2NBTTagCompound;
	}
}
