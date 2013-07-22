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
	
	public PlayerManager(WorldServer par1WorldServer, int par2)
	{
		if(par2 > 15) throw new IllegalArgumentException("Too big view radius!");
		else if(par2 < 3) throw new IllegalArgumentException("Too small view radius!");
		else
		{
			playerViewRadius = par2;
			theWorldServer = par1WorldServer;
		}
	}
	
	public void addPlayer(EntityPlayerMP par1EntityPlayerMP)
	{
		int var2 = (int) par1EntityPlayerMP.posX >> 4;
		int var3 = (int) par1EntityPlayerMP.posZ >> 4;
		par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
		par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
		for(int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; ++var4)
		{
			for(int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; ++var5)
			{
				getOrCreateChunkWatcher(var4, var5, true).addPlayer(par1EntityPlayerMP);
			}
		}
		players.add(par1EntityPlayerMP);
		filterChunkLoadQueue(par1EntityPlayerMP);
	}
	
	public void filterChunkLoadQueue(EntityPlayerMP par1EntityPlayerMP)
	{
		ArrayList var2 = new ArrayList(par1EntityPlayerMP.loadedChunks);
		int var3 = 0;
		int var4 = playerViewRadius;
		int var5 = (int) par1EntityPlayerMP.posX >> 4;
		int var6 = (int) par1EntityPlayerMP.posZ >> 4;
		int var7 = 0;
		int var8 = 0;
		ChunkCoordIntPair var9 = PlayerInstance.getChunkLocation(getOrCreateChunkWatcher(var5, var6, true));
		par1EntityPlayerMP.loadedChunks.clear();
		if(var2.contains(var9))
		{
			par1EntityPlayerMP.loadedChunks.add(var9);
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
						par1EntityPlayerMP.loadedChunks.add(var9);
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
				par1EntityPlayerMP.loadedChunks.add(var9);
			}
		}
	}
	
	private PlayerInstance getOrCreateChunkWatcher(int par1, int par2, boolean par3)
	{
		long var4 = par1 + 2147483647L | par2 + 2147483647L << 32;
		PlayerInstance var6 = (PlayerInstance) playerInstances.getValueByKey(var4);
		if(var6 == null && par3)
		{
			var6 = new PlayerInstance(this, par1, par2);
			playerInstances.add(var4, var6);
		}
		return var6;
	}
	
	public WorldServer getWorldServer()
	{
		return theWorldServer;
	}
	
	public boolean isPlayerWatchingChunk(EntityPlayerMP par1EntityPlayerMP, int par2, int par3)
	{
		PlayerInstance var4 = getOrCreateChunkWatcher(par2, par3, false);
		return var4 == null ? false : PlayerInstance.getPlayersInChunk(var4).contains(par1EntityPlayerMP) && !par1EntityPlayerMP.loadedChunks.contains(PlayerInstance.getChunkLocation(var4));
	}
	
	public void markBlockForUpdate(int par1, int par2, int par3)
	{
		int var4 = par1 >> 4;
		int var5 = par3 >> 4;
		PlayerInstance var6 = getOrCreateChunkWatcher(var4, var5, false);
		if(var6 != null)
		{
			var6.flagChunkForUpdate(par1 & 15, par2, par3 & 15);
		}
	}
	
	private boolean overlaps(int par1, int par2, int par3, int par4, int par5)
	{
		int var6 = par1 - par3;
		int var7 = par2 - par4;
		return var6 >= -par5 && var6 <= par5 ? var7 >= -par5 && var7 <= par5 : false;
	}
	
	public void removePlayer(EntityPlayerMP par1EntityPlayerMP)
	{
		int var2 = (int) par1EntityPlayerMP.managedPosX >> 4;
		int var3 = (int) par1EntityPlayerMP.managedPosZ >> 4;
		for(int var4 = var2 - playerViewRadius; var4 <= var2 + playerViewRadius; ++var4)
		{
			for(int var5 = var3 - playerViewRadius; var5 <= var3 + playerViewRadius; ++var5)
			{
				PlayerInstance var6 = getOrCreateChunkWatcher(var4, var5, false);
				if(var6 != null)
				{
					var6.removePlayer(par1EntityPlayerMP);
				}
			}
		}
		players.remove(par1EntityPlayerMP);
	}
	
	public void updateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP)
	{
		int var2 = (int) par1EntityPlayerMP.posX >> 4;
		int var3 = (int) par1EntityPlayerMP.posZ >> 4;
		double var4 = par1EntityPlayerMP.managedPosX - par1EntityPlayerMP.posX;
		double var6 = par1EntityPlayerMP.managedPosZ - par1EntityPlayerMP.posZ;
		double var8 = var4 * var4 + var6 * var6;
		if(var8 >= 64.0D)
		{
			int var10 = (int) par1EntityPlayerMP.managedPosX >> 4;
			int var11 = (int) par1EntityPlayerMP.managedPosZ >> 4;
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
							getOrCreateChunkWatcher(var15, var16, true).addPlayer(par1EntityPlayerMP);
						}
						if(!overlaps(var15 - var13, var16 - var14, var2, var3, var12))
						{
							PlayerInstance var17 = getOrCreateChunkWatcher(var15 - var13, var16 - var14, false);
							if(var17 != null)
							{
								var17.removePlayer(par1EntityPlayerMP);
							}
						}
					}
				}
				filterChunkLoadQueue(par1EntityPlayerMP);
				par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
				par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
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
	
	static LongHashMap getChunkWatchers(PlayerManager par0PlayerManager)
	{
		return par0PlayerManager.playerInstances;
	}
	
	static List getChunkWatchersWithPlayers(PlayerManager par0PlayerManager)
	{
		return par0PlayerManager.chunkWatcherWithPlayers;
	}
	
	public static int getFurthestViewableBlock(int par0)
	{
		return par0 * 16 - 16;
	}
	
	static WorldServer getWorldServer(PlayerManager par0PlayerManager)
	{
		return par0PlayerManager.theWorldServer;
	}
}
