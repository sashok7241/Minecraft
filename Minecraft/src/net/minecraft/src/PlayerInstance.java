package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

class PlayerInstance
{
	private final List playersInChunk;
	private final ChunkCoordIntPair chunkLocation;
	private short[] locationOfBlockChange;
	private int numberOfTilesToUpdate;
	private int field_73260_f;
	final PlayerManager thePlayerManager;
	
	public PlayerInstance(PlayerManager par1PlayerManager, int par2, int par3)
	{
		thePlayerManager = par1PlayerManager;
		playersInChunk = new ArrayList();
		locationOfBlockChange = new short[64];
		numberOfTilesToUpdate = 0;
		chunkLocation = new ChunkCoordIntPair(par2, par3);
		par1PlayerManager.getWorldServer().theChunkProviderServer.loadChunk(par2, par3);
	}
	
	public void addPlayer(EntityPlayerMP par1EntityPlayerMP)
	{
		if(playersInChunk.contains(par1EntityPlayerMP)) throw new IllegalStateException("Failed to add player. " + par1EntityPlayerMP + " already is in chunk " + chunkLocation.chunkXPos + ", " + chunkLocation.chunkZPos);
		else
		{
			playersInChunk.add(par1EntityPlayerMP);
			par1EntityPlayerMP.loadedChunks.add(chunkLocation);
		}
	}
	
	public void flagChunkForUpdate(int par1, int par2, int par3)
	{
		if(numberOfTilesToUpdate == 0)
		{
			PlayerManager.getChunkWatchersWithPlayers(thePlayerManager).add(this);
		}
		field_73260_f |= 1 << (par2 >> 4);
		if(numberOfTilesToUpdate < 64)
		{
			short var4 = (short) (par1 << 12 | par3 << 8 | par2);
			for(int var5 = 0; var5 < numberOfTilesToUpdate; ++var5)
			{
				if(locationOfBlockChange[var5] == var4) return;
			}
			locationOfBlockChange[numberOfTilesToUpdate++] = var4;
		}
	}
	
	public void removePlayer(EntityPlayerMP par1EntityPlayerMP)
	{
		if(playersInChunk.contains(par1EntityPlayerMP))
		{
			par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet51MapChunk(PlayerManager.getWorldServer(thePlayerManager).getChunkFromChunkCoords(chunkLocation.chunkXPos, chunkLocation.chunkZPos), true, 0));
			playersInChunk.remove(par1EntityPlayerMP);
			par1EntityPlayerMP.loadedChunks.remove(chunkLocation);
			if(playersInChunk.isEmpty())
			{
				long var2 = chunkLocation.chunkXPos + 2147483647L | chunkLocation.chunkZPos + 2147483647L << 32;
				PlayerManager.getChunkWatchers(thePlayerManager).remove(var2);
				if(numberOfTilesToUpdate > 0)
				{
					PlayerManager.getChunkWatchersWithPlayers(thePlayerManager).remove(this);
				}
				thePlayerManager.getWorldServer().theChunkProviderServer.unloadChunksIfNotNearSpawn(chunkLocation.chunkXPos, chunkLocation.chunkZPos);
			}
		}
	}
	
	public void sendChunkUpdate()
	{
		if(numberOfTilesToUpdate != 0)
		{
			int var1;
			int var2;
			int var3;
			if(numberOfTilesToUpdate == 1)
			{
				var1 = chunkLocation.chunkXPos * 16 + (locationOfBlockChange[0] >> 12 & 15);
				var2 = locationOfBlockChange[0] & 255;
				var3 = chunkLocation.chunkZPos * 16 + (locationOfBlockChange[0] >> 8 & 15);
				sendToAllPlayersWatchingChunk(new Packet53BlockChange(var1, var2, var3, PlayerManager.getWorldServer(thePlayerManager)));
				if(PlayerManager.getWorldServer(thePlayerManager).blockHasTileEntity(var1, var2, var3))
				{
					sendTileToAllPlayersWatchingChunk(PlayerManager.getWorldServer(thePlayerManager).getBlockTileEntity(var1, var2, var3));
				}
			} else
			{
				int var4;
				if(numberOfTilesToUpdate == 64)
				{
					var1 = chunkLocation.chunkXPos * 16;
					var2 = chunkLocation.chunkZPos * 16;
					sendToAllPlayersWatchingChunk(new Packet51MapChunk(PlayerManager.getWorldServer(thePlayerManager).getChunkFromChunkCoords(chunkLocation.chunkXPos, chunkLocation.chunkZPos), false, field_73260_f));
					for(var3 = 0; var3 < 16; ++var3)
					{
						if((field_73260_f & 1 << var3) != 0)
						{
							var4 = var3 << 4;
							List var5 = PlayerManager.getWorldServer(thePlayerManager).getAllTileEntityInBox(var1, var4, var2, var1 + 16, var4 + 16, var2 + 16);
							for(int var6 = 0; var6 < var5.size(); ++var6)
							{
								sendTileToAllPlayersWatchingChunk((TileEntity) var5.get(var6));
							}
						}
					}
				} else
				{
					sendToAllPlayersWatchingChunk(new Packet52MultiBlockChange(chunkLocation.chunkXPos, chunkLocation.chunkZPos, locationOfBlockChange, numberOfTilesToUpdate, PlayerManager.getWorldServer(thePlayerManager)));
					for(var1 = 0; var1 < numberOfTilesToUpdate; ++var1)
					{
						var2 = chunkLocation.chunkXPos * 16 + (locationOfBlockChange[var1] >> 12 & 15);
						var3 = locationOfBlockChange[var1] & 255;
						var4 = chunkLocation.chunkZPos * 16 + (locationOfBlockChange[var1] >> 8 & 15);
						if(PlayerManager.getWorldServer(thePlayerManager).blockHasTileEntity(var2, var3, var4))
						{
							sendTileToAllPlayersWatchingChunk(PlayerManager.getWorldServer(thePlayerManager).getBlockTileEntity(var2, var3, var4));
						}
					}
				}
			}
			numberOfTilesToUpdate = 0;
			field_73260_f = 0;
		}
	}
	
	private void sendTileToAllPlayersWatchingChunk(TileEntity par1TileEntity)
	{
		if(par1TileEntity != null)
		{
			Packet var2 = par1TileEntity.getDescriptionPacket();
			if(var2 != null)
			{
				sendToAllPlayersWatchingChunk(var2);
			}
		}
	}
	
	public void sendToAllPlayersWatchingChunk(Packet par1Packet)
	{
		for(int var2 = 0; var2 < playersInChunk.size(); ++var2)
		{
			EntityPlayerMP var3 = (EntityPlayerMP) playersInChunk.get(var2);
			if(!var3.loadedChunks.contains(chunkLocation))
			{
				var3.playerNetServerHandler.sendPacketToPlayer(par1Packet);
			}
		}
	}
	
	static ChunkCoordIntPair getChunkLocation(PlayerInstance par0PlayerInstance)
	{
		return par0PlayerInstance.chunkLocation;
	}
	
	static List getPlayersInChunk(PlayerInstance par0PlayerInstance)
	{
		return par0PlayerInstance.playersInChunk;
	}
}
