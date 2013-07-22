package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
	private List chunksToRemove = new ArrayList();
	private Set pendingAnvilChunksCoordinates = new HashSet();
	private Object syncLockObject = new Object();
	private final File chunkSaveLocation;
	
	public AnvilChunkLoader(File p_i3779_1_)
	{
		chunkSaveLocation = p_i3779_1_;
	}
	
	protected void addChunkToPending(ChunkCoordIntPair p_75824_1_, NBTTagCompound p_75824_2_)
	{
		Object var3 = syncLockObject;
		synchronized(syncLockObject)
		{
			if(pendingAnvilChunksCoordinates.contains(p_75824_1_))
			{
				for(int var4 = 0; var4 < chunksToRemove.size(); ++var4)
				{
					if(((AnvilChunkLoaderPending) chunksToRemove.get(var4)).chunkCoordinate.equals(p_75824_1_))
					{
						chunksToRemove.set(var4, new AnvilChunkLoaderPending(p_75824_1_, p_75824_2_));
						return;
					}
				}
			}
			chunksToRemove.add(new AnvilChunkLoaderPending(p_75824_1_, p_75824_2_));
			pendingAnvilChunksCoordinates.add(p_75824_1_);
			ThreadedFileIOBase.threadedIOInstance.queueIO(this);
		}
	}
	
	protected Chunk checkedReadChunkFromNBT(World p_75822_1_, int p_75822_2_, int p_75822_3_, NBTTagCompound p_75822_4_)
	{
		if(!p_75822_4_.hasKey("Level"))
		{
			p_75822_1_.getWorldLogAgent().logSevere("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing level data, skipping");
			return null;
		} else if(!p_75822_4_.getCompoundTag("Level").hasKey("Sections"))
		{
			p_75822_1_.getWorldLogAgent().logSevere("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is missing block data, skipping");
			return null;
		} else
		{
			Chunk var5 = readChunkFromNBT(p_75822_1_, p_75822_4_.getCompoundTag("Level"));
			if(!var5.isAtLocation(p_75822_2_, p_75822_3_))
			{
				p_75822_1_.getWorldLogAgent().logSevere("Chunk file at " + p_75822_2_ + "," + p_75822_3_ + " is in the wrong location; relocating. (Expected " + p_75822_2_ + ", " + p_75822_3_ + ", got " + var5.xPosition + ", " + var5.zPosition + ")");
				p_75822_4_.setInteger("xPos", p_75822_2_);
				p_75822_4_.setInteger("zPos", p_75822_3_);
				var5 = readChunkFromNBT(p_75822_1_, p_75822_4_.getCompoundTag("Level"));
			}
			return var5;
		}
	}
	
	@Override public void chunkTick()
	{
	}
	
	@Override public Chunk loadChunk(World p_75815_1_, int p_75815_2_, int p_75815_3_) throws IOException
	{
		NBTTagCompound var4 = null;
		ChunkCoordIntPair var5 = new ChunkCoordIntPair(p_75815_2_, p_75815_3_);
		Object var6 = syncLockObject;
		synchronized(syncLockObject)
		{
			if(pendingAnvilChunksCoordinates.contains(var5))
			{
				for(int var7 = 0; var7 < chunksToRemove.size(); ++var7)
				{
					if(((AnvilChunkLoaderPending) chunksToRemove.get(var7)).chunkCoordinate.equals(var5))
					{
						var4 = ((AnvilChunkLoaderPending) chunksToRemove.get(var7)).nbtTags;
						break;
					}
				}
			}
		}
		if(var4 == null)
		{
			DataInputStream var10 = RegionFileCache.getChunkInputStream(chunkSaveLocation, p_75815_2_, p_75815_3_);
			if(var10 == null) return null;
			var4 = CompressedStreamTools.read(var10);
		}
		return checkedReadChunkFromNBT(p_75815_1_, p_75815_2_, p_75815_3_, var4);
	}
	
	private Chunk readChunkFromNBT(World p_75823_1_, NBTTagCompound p_75823_2_)
	{
		int var3 = p_75823_2_.getInteger("xPos");
		int var4 = p_75823_2_.getInteger("zPos");
		Chunk var5 = new Chunk(p_75823_1_, var3, var4);
		var5.heightMap = p_75823_2_.getIntArray("HeightMap");
		var5.isTerrainPopulated = p_75823_2_.getBoolean("TerrainPopulated");
		NBTTagList var6 = p_75823_2_.getTagList("Sections");
		byte var7 = 16;
		ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
		boolean var9 = !p_75823_1_.provider.hasNoSky;
		for(int var10 = 0; var10 < var6.tagCount(); ++var10)
		{
			NBTTagCompound var11 = (NBTTagCompound) var6.tagAt(var10);
			byte var12 = var11.getByte("Y");
			ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
			var13.setBlockLSBArray(var11.getByteArray("Blocks"));
			if(var11.hasKey("Add"))
			{
				var13.setBlockMSBArray(new NibbleArray(var11.getByteArray("Add"), 4));
			}
			var13.setBlockMetadataArray(new NibbleArray(var11.getByteArray("Data"), 4));
			var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight"), 4));
			if(var9)
			{
				var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight"), 4));
			}
			var13.removeInvalidBlocks();
			var8[var12] = var13;
		}
		var5.setStorageArrays(var8);
		if(p_75823_2_.hasKey("Biomes"))
		{
			var5.setBiomeArray(p_75823_2_.getByteArray("Biomes"));
		}
		NBTTagList var18 = p_75823_2_.getTagList("Entities");
		if(var18 != null)
		{
			for(int var17 = 0; var17 < var18.tagCount(); ++var17)
			{
				NBTTagCompound var19 = (NBTTagCompound) var18.tagAt(var17);
				Entity var25 = EntityList.createEntityFromNBT(var19, p_75823_1_);
				var5.hasEntities = true;
				if(var25 != null)
				{
					var5.addEntity(var25);
					Entity var14 = var25;
					for(NBTTagCompound var15 = var19; var15.hasKey("Riding"); var15 = var15.getCompoundTag("Riding"))
					{
						Entity var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), p_75823_1_);
						if(var16 != null)
						{
							var5.addEntity(var16);
							var14.mountEntity(var16);
						}
						var14 = var16;
					}
				}
			}
		}
		NBTTagList var21 = p_75823_2_.getTagList("TileEntities");
		if(var21 != null)
		{
			for(int var20 = 0; var20 < var21.tagCount(); ++var20)
			{
				NBTTagCompound var22 = (NBTTagCompound) var21.tagAt(var20);
				TileEntity var27 = TileEntity.createAndLoadEntity(var22);
				if(var27 != null)
				{
					var5.addTileEntity(var27);
				}
			}
		}
		if(p_75823_2_.hasKey("TileTicks"))
		{
			NBTTagList var24 = p_75823_2_.getTagList("TileTicks");
			if(var24 != null)
			{
				for(int var23 = 0; var23 < var24.tagCount(); ++var23)
				{
					NBTTagCompound var26 = (NBTTagCompound) var24.tagAt(var23);
					p_75823_1_.scheduleBlockUpdateFromLoad(var26.getInteger("x"), var26.getInteger("y"), var26.getInteger("z"), var26.getInteger("i"), var26.getInteger("t"), var26.getInteger("p"));
				}
			}
		}
		return var5;
	}
	
	@Override public void saveChunk(World p_75816_1_, Chunk p_75816_2_) throws MinecraftException, IOException
	{
		p_75816_1_.checkSessionLock();
		try
		{
			NBTTagCompound var3 = new NBTTagCompound();
			NBTTagCompound var4 = new NBTTagCompound();
			var3.setTag("Level", var4);
			writeChunkToNBT(p_75816_2_, p_75816_1_, var4);
			addChunkToPending(p_75816_2_.getChunkCoordIntPair(), var3);
		} catch(Exception var5)
		{
			var5.printStackTrace();
		}
	}
	
	@Override public void saveExtraChunkData(World p_75819_1_, Chunk p_75819_2_)
	{
	}
	
	@Override public void saveExtraData()
	{
		while(writeNextIO())
		{
			;
		}
	}
	
	private void writeChunkNBTTags(AnvilChunkLoaderPending p_75821_1_) throws IOException
	{
		DataOutputStream var2 = RegionFileCache.getChunkOutputStream(chunkSaveLocation, p_75821_1_.chunkCoordinate.chunkXPos, p_75821_1_.chunkCoordinate.chunkZPos);
		CompressedStreamTools.write(p_75821_1_.nbtTags, var2);
		var2.close();
	}
	
	private void writeChunkToNBT(Chunk p_75820_1_, World p_75820_2_, NBTTagCompound p_75820_3_)
	{
		p_75820_3_.setInteger("xPos", p_75820_1_.xPosition);
		p_75820_3_.setInteger("zPos", p_75820_1_.zPosition);
		p_75820_3_.setLong("LastUpdate", p_75820_2_.getTotalWorldTime());
		p_75820_3_.setIntArray("HeightMap", p_75820_1_.heightMap);
		p_75820_3_.setBoolean("TerrainPopulated", p_75820_1_.isTerrainPopulated);
		ExtendedBlockStorage[] var4 = p_75820_1_.getBlockStorageArray();
		NBTTagList var5 = new NBTTagList("Sections");
		boolean var6 = !p_75820_2_.provider.hasNoSky;
		ExtendedBlockStorage[] var7 = var4;
		int var8 = var4.length;
		NBTTagCompound var11;
		for(int var9 = 0; var9 < var8; ++var9)
		{
			ExtendedBlockStorage var10 = var7[var9];
			if(var10 != null)
			{
				var11 = new NBTTagCompound();
				var11.setByte("Y", (byte) (var10.getYLocation() >> 4 & 255));
				var11.setByteArray("Blocks", var10.getBlockLSBArray());
				if(var10.getBlockMSBArray() != null)
				{
					var11.setByteArray("Add", var10.getBlockMSBArray().data);
				}
				var11.setByteArray("Data", var10.getMetadataArray().data);
				var11.setByteArray("BlockLight", var10.getBlocklightArray().data);
				if(var6)
				{
					var11.setByteArray("SkyLight", var10.getSkylightArray().data);
				} else
				{
					var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
				}
				var5.appendTag(var11);
			}
		}
		p_75820_3_.setTag("Sections", var5);
		p_75820_3_.setByteArray("Biomes", p_75820_1_.getBiomeArray());
		p_75820_1_.hasEntities = false;
		NBTTagList var16 = new NBTTagList();
		Iterator var18;
		for(var8 = 0; var8 < p_75820_1_.entityLists.length; ++var8)
		{
			var18 = p_75820_1_.entityLists[var8].iterator();
			while(var18.hasNext())
			{
				Entity var21 = (Entity) var18.next();
				var11 = new NBTTagCompound();
				if(var21.addEntityID(var11))
				{
					p_75820_1_.hasEntities = true;
					var16.appendTag(var11);
				}
			}
		}
		p_75820_3_.setTag("Entities", var16);
		NBTTagList var17 = new NBTTagList();
		var18 = p_75820_1_.chunkTileEntityMap.values().iterator();
		while(var18.hasNext())
		{
			TileEntity var22 = (TileEntity) var18.next();
			var11 = new NBTTagCompound();
			var22.writeToNBT(var11);
			var17.appendTag(var11);
		}
		p_75820_3_.setTag("TileEntities", var17);
		List var20 = p_75820_2_.getPendingBlockUpdates(p_75820_1_, false);
		if(var20 != null)
		{
			long var19 = p_75820_2_.getTotalWorldTime();
			NBTTagList var12 = new NBTTagList();
			Iterator var13 = var20.iterator();
			while(var13.hasNext())
			{
				NextTickListEntry var14 = (NextTickListEntry) var13.next();
				NBTTagCompound var15 = new NBTTagCompound();
				var15.setInteger("i", var14.blockID);
				var15.setInteger("x", var14.xCoord);
				var15.setInteger("y", var14.yCoord);
				var15.setInteger("z", var14.zCoord);
				var15.setInteger("t", (int) (var14.scheduledTime - var19));
				var15.setInteger("p", var14.priority);
				var12.appendTag(var15);
			}
			p_75820_3_.setTag("TileTicks", var12);
		}
	}
	
	@Override public boolean writeNextIO()
	{
		AnvilChunkLoaderPending var1 = null;
		Object var2 = syncLockObject;
		synchronized(syncLockObject)
		{
			if(chunksToRemove.isEmpty()) return false;
			var1 = (AnvilChunkLoaderPending) chunksToRemove.remove(0);
			pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
		}
		if(var1 != null)
		{
			try
			{
				writeChunkNBTTags(var1);
			} catch(Exception var4)
			{
				var4.printStackTrace();
			}
		}
		return true;
	}
}
