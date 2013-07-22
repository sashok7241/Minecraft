package net.minecraft.src;

class AnvilChunkLoaderPending
{
	public final ChunkCoordIntPair chunkCoordinate;
	public final NBTTagCompound nbtTags;
	
	public AnvilChunkLoaderPending(ChunkCoordIntPair p_i3778_1_, NBTTagCompound p_i3778_2_)
	{
		chunkCoordinate = p_i3778_1_;
		nbtTags = p_i3778_2_;
	}
}
