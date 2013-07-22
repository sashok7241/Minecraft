package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager
{
	private final WorldServer theWorldServer;
	private final List players = new ArrayList();
	private final LongHashMap playerInstances = new LongHashMap();
	private final List chunkWatcherWithPlayers = new ArrayList();
	private final int playerViewRadius;
	private final int[][] xzDirectionsConst = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
	
	public PlayerManager(WorldServer p_i3392_1_, int p_i3392_2_)
	{
		if(p_i3392_2_ > 15) throw new IllegalArgumentException("Too big view radius!");
		else if(p_i3392_2_ < 3) throw new IllegalArgumentException("Too small view radius!");
		else
		{
			playerViewRadius = p_i3392_2_;
			theWorldServer = p_i3392_1_;
		}
	}
	
	public void addPlayer(EntityPlayerMP p_72683_1_)
	{
		int var2 = (int) p_72683_1_.posX >> 4;
		int var3 = (int) p_72683_1_.posZ >> 4;
		p_72683_1_.managedPosX = p_72683_1_.posX;
		p_72683_1_.managedPosZ = p_72683_1_.posZ;
		for(int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; ++var4)
		{
			for(int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; ++var5)
			{
				getOrCreateChunkWatcher(var4, var5, true).addPlayer(p_72683_1_);
			}
		}
		players.add(p_72683_1_);
		filterChunkLoadQueue(p_72683_1_);
	}
	
	public void filterChunkLoadQueue(EntityPlayerMP p_72691_1_)
	{
		ArrayList var2 = new ArrayList(p_72691_1_.loadedChunks);
		int var3 = 0;
		int var4 = playerViewRadius;
		int var5 = (int) p_72691_1_.posX >> 4;
		int var6 = (int) p_72691_1_.posZ >> 4;
		int var7 = 0;
		int var8 = 0;
		ChunkCoordIntPair var9 = PlayerInstance.getChunkLocation(getOrCreateChunkWatcher(var5, var6, true));
		p_72691_1_.loadedChunks.clear();
		if(var2.contains(var9))
		{
			p_72691_1_.loadedChunks.add(var9);
		}
		int var10;
		for(var10 = 1; var10 <= var4 * 2; ++var10)
		{
			for(int var11 = 0; var11 < 2; ++var11)
			{
				int[] var12 = xzDirectionsConst[var3++ % 4];
				for(int var13 = 0; var13 < var10; ++var13)
				{
					var7 += var12[0];
					var8 += var12[1];
					var9 = PlayerInstance.getChunkLocation(getOrCreateChunkWatcher(var5 + var7, var6 + var8, true));
					if(var2.contains(var9))
					{
						p_72691_1_.loadedChunks.add(var9);
					}
				}
			}
		}
		var3 %= 4;
		for(var10 = 0; var10 < var4 * 2; ++var10)
		{
			var7 += xzDirectionsConst[var3][0];
			var8 += xzDirectionsConst[var3][1];
			var9 = PlayerInstance.getChunkLocation(getOrCreateChunkWatcher(var5 + var7, var6 + var8, true));
			if(var2.contains(var9))
			{
				p_72691_1_.loadedChunks.add(var9);
			}
		}
	}
	
	private PlayerInstance getOrCreateChunkWatcher(int p_72690_1_, int p_72690_2_, boolean p_72690_3_)
	{
		long var4 = p_72690_1_ + 2147483647L | p_72690_2_ + 2147483647L << 32;
		PlayerInstance var6 = (PlayerInstance) playerInstances.getValueByKey(var4);
		if(var6 == null && p_72690_3_)
		{
			var6 = new PlayerInstance(this, p_72690_1_, p_72690_2_);
			playerInstances.add(var4, var6);
		}
		return var6;
	}
	
	public WorldServer getWorldServer()
	{
		return theWorldServer;
	}
	
	public boolean isPlayerWatchingChunk(EntityPlayerMP p_72694_1_, int p_72694_2_, int p_72694_3_)
	{
		PlayerInstance var4 = getOrCreateChunkWatcher(p_72694_2_, p_72694_3_, false);
		return var4 == null ? false : PlayerInstance.getPlayersInChunk(var4).contains(p_72694_1_) && !p_72694_1_.loadedChunks.contains(PlayerInstance.getChunkLocation(var4));
	}
	
	public void markBlockForUpdate(int p_72687_1_, int p_72687_2_, int p_72687_3_)
	{
		int var4 = p_72687_1_ >> 4;
		int var5 = p_72687_3_ >> 4;
		PlayerInstance var6 = getOrCreateChunkWatcher(var4, var5, false);
		if(var6 != null)
		{
			var6.flagChunkForUpdate(p_72687_1_ & 15, p_72687_2_, p_72687_3_ & 15);
		}
	}
	
	private boolean overlaps(int p_72684_1_, int p_72684_2_, int p_72684_3_, int p_72684_4_, int p_72684_5_)
	{
		int var6 = p_72684_1_ - p_72684_3_;
		int var7 = p_72684_2_ - p_72684_4_;
		return var6 >= -p_72684_5_ && var6 <= p_72684_5_ ? var7 >= -p_72684_5_ && var7 <= p_72684_5_ : false;
	}
	
	public void removePlayer(EntityPlayerMP p_72695_1_)
	{
		int var2 = (int) p_72695_1_.managedPosX >> 4;
		int var3 = (int) p_72695_1_.managedPosZ >> 4;
		for(int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; ++var4)
		{
			for(int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; ++var5)
			{
				PlayerInstance var6 = getOrCreateChunkWatcher(var4, var5, false);
				if(var6 != null)
				{
					var6.removePlayer(p_72695_1_);
				}
			}
		}
		players.remove(p_72695_1_);
	}
	
	public void updateMountedMovingPlayer(EntityPlayerMP p_72685_1_)
	{
		int var2 = (int) p_72685_1_.posX >> 4;
		int var3 = (int) p_72685_1_.posZ >> 4;
		double var4 = p_72685_1_.managedPosX - p_72685_1_.posX;
		double var6 = p_72685_1_.managedPosZ - p_72685_1_.posZ;
		double var8 = var4 * var4 + var6 * var6;
		if(var8 >= 64.0D)
		{
			int var10 = (int) p_72685_1_.managedPosX >> 4;
			int var11 = (int) p_72685_1_.managedPosZ >> 4;
			int var12 = playerViewRadius;
			int var13 = var2 - var10;
			int var14 = var3 - var11;
			if(var13 != 0 || var14 != 0)
			{
				for(int var15 = var2 - var12; var15 <= var2 + var12; ++var15)
				{
					for(int var16 = var3 - var12; var16 <= var3 + var12; ++var16)
					{
						if(!overlaps(var15, var16, var10, var11, var12))
						{
							getOrCreateChunkWatcher(var15, var16, true).addPlayer(p_72685_1_);
						}
						if(!overlaps(var15 - var13, var16 - var14, var2, var3, var12))
						{
							PlayerInstance var17 = getOrCreateChunkWatcher(var15 - var13, var16 - var14, false);
							if(var17 != null)
							{
								var17.removePlayer(p_72685_1_);
							}
						}
					}
				}
				filterChunkLoadQueue(p_72685_1_);
				p_72685_1_.managedPosX = p_72685_1_.posX;
				p_72685_1_.managedPosZ = p_72685_1_.posZ;
			}
		}
	}
	
	public void updatePlayerInstances()
	{
		for(int var1 = 0; var1 < chunkWatcherWithPlayers.size(); ++var1)
		{
			((PlayerInstance) chunkWatcherWithPlayers.get(var1)).sendChunkUpdate();
		}
		chunkWatcherWithPlayers.clear();
		if(players.isEmpty())
		{
			WorldProvider var2 = theWorldServer.provider;
			if(!var2.canRespawnHere())
			{
				theWorldServer.theChunkProviderServer.unloadAllChunks();
			}
		}
	}
	
	static LongHashMap getChunkWatchers(PlayerManager p_72689_0_)
	{
		return p_72689_0_.playerInstances;
	}
	
	static List getChunkWatchersWithPlayers(PlayerManager p_72682_0_)
	{
		return p_72682_0_.chunkWatcherWithPlayers;
	}
	
	public static int getFurthestViewableBlock(int p_72686_0_)
	{
		return p_72686_0_ * 16 - 16;
	}
	
	static WorldServer getWorldServer(PlayerManager p_72692_0_)
	{
		return p_72692_0_.theWorldServer;
	}
}
