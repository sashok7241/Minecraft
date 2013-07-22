package net.minecraft.src;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.server.MinecraftServer;

public abstract class ServerConfigurationManager
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd \'at\' HH:mm:ss z");
	private final MinecraftServer mcServer;
	public final List playerEntityList = new ArrayList();
	private final BanList bannedPlayers = new BanList(new File("banned-players.txt"));
	private final BanList bannedIPs = new BanList(new File("banned-ips.txt"));
	private Set ops = new HashSet();
	private Set whiteListedPlayers = new HashSet();
	private IPlayerFileData playerNBTManagerObj;
	private boolean whiteListEnforced;
	protected int maxPlayers;
	protected int viewDistance;
	private EnumGameType gameType;
	private boolean commandsAllowedForAll;
	private int playerPingIndex = 0;
	
	public ServerConfigurationManager(MinecraftServer p_i3376_1_)
	{
		mcServer = p_i3376_1_;
		bannedPlayers.setListActive(false);
		bannedIPs.setListActive(false);
		maxPlayers = 8;
	}
	
	public void addOp(String p_72386_1_)
	{
		ops.add(p_72386_1_.toLowerCase());
	}
	
	public void addToWhiteList(String p_72359_1_)
	{
		whiteListedPlayers.add(p_72359_1_);
	}
	
	public String allowUserToConnect(SocketAddress p_72399_1_, String p_72399_2_)
	{
		if(bannedPlayers.isBanned(p_72399_2_))
		{
			BanEntry var6 = (BanEntry) bannedPlayers.getBannedList().get(p_72399_2_);
			String var7 = "You are banned from this server!\nReason: " + var6.getBanReason();
			if(var6.getBanEndDate() != null)
			{
				var7 = var7 + "\nYour ban will be removed on " + dateFormat.format(var6.getBanEndDate());
			}
			return var7;
		} else if(!isAllowedToLogin(p_72399_2_)) return "You are not white-listed on this server!";
		else
		{
			String var3 = p_72399_1_.toString();
			var3 = var3.substring(var3.indexOf("/") + 1);
			var3 = var3.substring(0, var3.indexOf(":"));
			if(bannedIPs.isBanned(var3))
			{
				BanEntry var4 = (BanEntry) bannedIPs.getBannedList().get(var3);
				String var5 = "Your IP address is banned from this server!\nReason: " + var4.getBanReason();
				if(var4.getBanEndDate() != null)
				{
					var5 = var5 + "\nYour ban will be removed on " + dateFormat.format(var4.getBanEndDate());
				}
				return var5;
			} else return playerEntityList.size() >= maxPlayers ? "The server is full!" : null;
		}
	}
	
	public boolean areCommandsAllowed(String p_72353_1_)
	{
		return ops.contains(p_72353_1_.trim().toLowerCase()) || mcServer.isSinglePlayer() && mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && mcServer.getServerOwner().equalsIgnoreCase(p_72353_1_) || commandsAllowedForAll;
	}
	
	public EntityPlayerMP createPlayerForUser(String p_72366_1_)
	{
		ArrayList var2 = new ArrayList();
		EntityPlayerMP var4;
		for(int var3 = 0; var3 < playerEntityList.size(); ++var3)
		{
			var4 = (EntityPlayerMP) playerEntityList.get(var3);
			if(var4.username.equalsIgnoreCase(p_72366_1_))
			{
				var2.add(var4);
			}
		}
		Iterator var5 = var2.iterator();
		while(var5.hasNext())
		{
			var4 = (EntityPlayerMP) var5.next();
			var4.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
		}
		Object var6;
		if(mcServer.isDemo())
		{
			var6 = new DemoWorldManager(mcServer.worldServerForDimension(0));
		} else
		{
			var6 = new ItemInWorldManager(mcServer.worldServerForDimension(0));
		}
		return new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(0), p_72366_1_, (ItemInWorldManager) var6);
	}
	
	public List findPlayers(ChunkCoordinates p_82449_1_, int p_82449_2_, int p_82449_3_, int p_82449_4_, int p_82449_5_, int p_82449_6_, int p_82449_7_, Map p_82449_8_, String p_82449_9_, String p_82449_10_)
	{
		if(playerEntityList.isEmpty()) return null;
		else
		{
			Object var11 = new ArrayList();
			boolean var12 = p_82449_4_ < 0;
			int var13 = p_82449_2_ * p_82449_2_;
			int var14 = p_82449_3_ * p_82449_3_;
			p_82449_4_ = MathHelper.abs_int(p_82449_4_);
			for(int var15 = 0; var15 < playerEntityList.size(); ++var15)
			{
				EntityPlayerMP var16 = (EntityPlayerMP) playerEntityList.get(var15);
				boolean var17;
				if(p_82449_9_ != null)
				{
					var17 = p_82449_9_.startsWith("!");
					if(var17)
					{
						p_82449_9_ = p_82449_9_.substring(1);
					}
					if(var17 == p_82449_9_.equalsIgnoreCase(var16.getEntityName()))
					{
						continue;
					}
				}
				if(p_82449_10_ != null)
				{
					var17 = p_82449_10_.startsWith("!");
					if(var17)
					{
						p_82449_10_ = p_82449_10_.substring(1);
					}
					ScorePlayerTeam var18 = var16.getTeam();
					String var19 = var18 == null ? "" : var18.func_96661_b();
					if(var17 == p_82449_10_.equalsIgnoreCase(var19))
					{
						continue;
					}
				}
				if(p_82449_1_ != null && (p_82449_2_ > 0 || p_82449_3_ > 0))
				{
					float var20 = p_82449_1_.getDistanceSquaredToChunkCoordinates(var16.getPlayerCoordinates());
					if(p_82449_2_ > 0 && var20 < var13 || p_82449_3_ > 0 && var20 > var14)
					{
						continue;
					}
				}
				if(func_96457_a(var16, p_82449_8_) && (p_82449_5_ == EnumGameType.NOT_SET.getID() || p_82449_5_ == var16.theItemInWorldManager.getGameType().getID()) && (p_82449_6_ <= 0 || var16.experienceLevel >= p_82449_6_) && var16.experienceLevel <= p_82449_7_)
				{
					((List) var11).add(var16);
				}
			}
			if(p_82449_1_ != null)
			{
				Collections.sort((List) var11, new PlayerPositionComparator(p_82449_1_));
			}
			if(var12)
			{
				Collections.reverse((List) var11);
			}
			if(p_82449_4_ > 0)
			{
				var11 = ((List) var11).subList(0, Math.min(p_82449_4_, ((List) var11).size()));
			}
			return (List) var11;
		}
	}
	
	public void func_72375_a(EntityPlayerMP p_72375_1_, WorldServer p_72375_2_)
	{
		WorldServer var3 = p_72375_1_.getServerForPlayer();
		if(p_72375_2_ != null)
		{
			p_72375_2_.getPlayerManager().removePlayer(p_72375_1_);
		}
		var3.getPlayerManager().addPlayer(p_72375_1_);
		var3.theChunkProviderServer.loadChunk((int) p_72375_1_.posX >> 4, (int) p_72375_1_.posZ >> 4);
	}
	
	private void func_72381_a(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World p_72381_3_)
	{
		if(p_72381_2_ != null)
		{
			p_72381_1_.theItemInWorldManager.setGameType(p_72381_2_.theItemInWorldManager.getGameType());
		} else if(gameType != null)
		{
			p_72381_1_.theItemInWorldManager.setGameType(gameType);
		}
		p_72381_1_.theItemInWorldManager.initializeGameType(p_72381_3_.getWorldInfo().getGameType());
	}
	
	protected void func_96456_a(ServerScoreboard p_96456_1_, EntityPlayerMP p_96456_2_)
	{
		HashSet var3 = new HashSet();
		Iterator var4 = p_96456_1_.func_96525_g().iterator();
		while(var4.hasNext())
		{
			ScorePlayerTeam var5 = (ScorePlayerTeam) var4.next();
			p_96456_2_.playerNetServerHandler.sendPacketToPlayer(new Packet209SetPlayerTeam(var5, 0));
		}
		for(int var9 = 0; var9 < 3; ++var9)
		{
			ScoreObjective var10 = p_96456_1_.func_96539_a(var9);
			if(var10 != null && !var3.contains(var10))
			{
				List var6 = p_96456_1_.func_96550_d(var10);
				Iterator var7 = var6.iterator();
				while(var7.hasNext())
				{
					Packet var8 = (Packet) var7.next();
					p_96456_2_.playerNetServerHandler.sendPacketToPlayer(var8);
				}
				var3.add(var10);
			}
		}
	}
	
	private boolean func_96457_a(EntityPlayer p_96457_1_, Map p_96457_2_)
	{
		if(p_96457_2_ != null && p_96457_2_.size() != 0)
		{
			Iterator var3 = p_96457_2_.entrySet().iterator();
			Entry var4;
			boolean var6;
			int var10;
			do
			{
				if(!var3.hasNext()) return true;
				var4 = (Entry) var3.next();
				String var5 = (String) var4.getKey();
				var6 = false;
				if(var5.endsWith("_min") && var5.length() > 4)
				{
					var6 = true;
					var5 = var5.substring(0, var5.length() - 4);
				}
				Scoreboard var7 = p_96457_1_.getWorldScoreboard();
				ScoreObjective var8 = var7.getObjective(var5);
				if(var8 == null) return false;
				Score var9 = p_96457_1_.getWorldScoreboard().func_96529_a(p_96457_1_.getEntityName(), var8);
				var10 = var9.getScorePoints();
				if(var10 < ((Integer) var4.getValue()).intValue() && var6) return false;
			} while(var10 <= ((Integer) var4.getValue()).intValue() || var6);
			return false;
		} else return true;
	}
	
	public String[] getAllUsernames()
	{
		String[] var1 = new String[playerEntityList.size()];
		for(int var2 = 0; var2 < playerEntityList.size(); ++var2)
		{
			var1[var2] = ((EntityPlayerMP) playerEntityList.get(var2)).username;
		}
		return var1;
	}
	
	public String[] getAvailablePlayerDat()
	{
		return mcServer.worldServers[0].getSaveHandler().getSaveHandler().getAvailablePlayerDat();
	}
	
	public BanList getBannedIPs()
	{
		return bannedIPs;
	}
	
	public BanList getBannedPlayers()
	{
		return bannedPlayers;
	}
	
	public int getCurrentPlayerCount()
	{
		return playerEntityList.size();
	}
	
	public int getEntityViewDistance()
	{
		return PlayerManager.getFurthestViewableBlock(getViewDistance());
	}
	
	public NBTTagCompound getHostPlayerData()
	{
		return null;
	}
	
	public int getMaxPlayers()
	{
		return maxPlayers;
	}
	
	public Set getOps()
	{
		return ops;
	}
	
	public EntityPlayerMP getPlayerForUsername(String p_72361_1_)
	{
		Iterator var2 = playerEntityList.iterator();
		EntityPlayerMP var3;
		do
		{
			if(!var2.hasNext()) return null;
			var3 = (EntityPlayerMP) var2.next();
		} while(!var3.username.equalsIgnoreCase(p_72361_1_));
		return var3;
	}
	
	public List getPlayerList(String p_72382_1_)
	{
		ArrayList var2 = new ArrayList();
		Iterator var3 = playerEntityList.iterator();
		while(var3.hasNext())
		{
			EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
			if(var4.getPlayerIP().equals(p_72382_1_))
			{
				var2.add(var4);
			}
		}
		return var2;
	}
	
	public String getPlayerListAsString()
	{
		String var1 = "";
		for(int var2 = 0; var2 < playerEntityList.size(); ++var2)
		{
			if(var2 > 0)
			{
				var1 = var1 + ", ";
			}
			var1 = var1 + ((EntityPlayerMP) playerEntityList.get(var2)).username;
		}
		return var1;
	}
	
	public MinecraftServer getServerInstance()
	{
		return mcServer;
	}
	
	public int getViewDistance()
	{
		return viewDistance;
	}
	
	public Set getWhiteListedPlayers()
	{
		return whiteListedPlayers;
	}
	
	public void initializeConnectionToPlayer(INetworkManager p_72355_1_, EntityPlayerMP p_72355_2_)
	{
		NBTTagCompound var3 = readPlayerDataFromFile(p_72355_2_);
		p_72355_2_.setWorld(mcServer.worldServerForDimension(p_72355_2_.dimension));
		p_72355_2_.theItemInWorldManager.setWorld((WorldServer) p_72355_2_.worldObj);
		String var4 = "local";
		if(p_72355_1_.getSocketAddress() != null)
		{
			var4 = p_72355_1_.getSocketAddress().toString();
		}
		mcServer.getLogAgent().logInfo(p_72355_2_.username + "[" + var4 + "] logged in with entity id " + p_72355_2_.entityId + " at (" + p_72355_2_.posX + ", " + p_72355_2_.posY + ", " + p_72355_2_.posZ + ")");
		WorldServer var5 = mcServer.worldServerForDimension(p_72355_2_.dimension);
		ChunkCoordinates var6 = var5.getSpawnPoint();
		func_72381_a(p_72355_2_, (EntityPlayerMP) null, var5);
		NetServerHandler var7 = new NetServerHandler(mcServer, p_72355_1_, p_72355_2_);
		var7.sendPacketToPlayer(new Packet1Login(p_72355_2_.entityId, var5.getWorldInfo().getTerrainType(), p_72355_2_.theItemInWorldManager.getGameType(), var5.getWorldInfo().isHardcoreModeEnabled(), var5.provider.dimensionId, var5.difficultySetting, var5.getHeight(), getMaxPlayers()));
		var7.sendPacketToPlayer(new Packet6SpawnPosition(var6.posX, var6.posY, var6.posZ));
		var7.sendPacketToPlayer(new Packet202PlayerAbilities(p_72355_2_.capabilities));
		var7.sendPacketToPlayer(new Packet16BlockItemSwitch(p_72355_2_.inventory.currentItem));
		func_96456_a((ServerScoreboard) var5.getScoreboard(), p_72355_2_);
		updateTimeAndWeatherForPlayer(p_72355_2_, var5);
		sendPacketToAllPlayers(new Packet3Chat(EnumChatFormatting.YELLOW + p_72355_2_.getTranslatedEntityName() + EnumChatFormatting.YELLOW + " joined the game."));
		playerLoggedIn(p_72355_2_);
		var7.setPlayerLocation(p_72355_2_.posX, p_72355_2_.posY, p_72355_2_.posZ, p_72355_2_.rotationYaw, p_72355_2_.rotationPitch);
		mcServer.getNetworkThread().addPlayer(var7);
		var7.sendPacketToPlayer(new Packet4UpdateTime(var5.getTotalWorldTime(), var5.getWorldTime()));
		if(mcServer.getTexturePack().length() > 0)
		{
			p_72355_2_.requestTexturePackLoad(mcServer.getTexturePack(), mcServer.textureSize());
		}
		Iterator var8 = p_72355_2_.getActivePotionEffects().iterator();
		while(var8.hasNext())
		{
			PotionEffect var9 = (PotionEffect) var8.next();
			var7.sendPacketToPlayer(new Packet41EntityEffect(p_72355_2_.entityId, var9));
		}
		p_72355_2_.addSelfToInternalCraftingInventory();
		if(var3 != null && var3.hasKey("Riding"))
		{
			Entity var10 = EntityList.createEntityFromNBT(var3.getCompoundTag("Riding"), var5);
			if(var10 != null)
			{
				var10.field_98038_p = true;
				var5.spawnEntityInWorld(var10);
				p_72355_2_.mountEntity(var10);
				var10.field_98038_p = false;
			}
		}
	}
	
	public boolean isAllowedToLogin(String p_72370_1_)
	{
		p_72370_1_ = p_72370_1_.trim().toLowerCase();
		return !whiteListEnforced || ops.contains(p_72370_1_) || whiteListedPlayers.contains(p_72370_1_);
	}
	
	public boolean isWhiteListEnabled()
	{
		return whiteListEnforced;
	}
	
	public void loadWhiteList()
	{
	}
	
	public void playerLoggedIn(EntityPlayerMP p_72377_1_)
	{
		sendPacketToAllPlayers(new Packet201PlayerInfo(p_72377_1_.username, true, 1000));
		playerEntityList.add(p_72377_1_);
		WorldServer var2 = mcServer.worldServerForDimension(p_72377_1_.dimension);
		var2.spawnEntityInWorld(p_72377_1_);
		func_72375_a(p_72377_1_, (WorldServer) null);
		for(int var3 = 0; var3 < playerEntityList.size(); ++var3)
		{
			EntityPlayerMP var4 = (EntityPlayerMP) playerEntityList.get(var3);
			p_72377_1_.playerNetServerHandler.sendPacketToPlayer(new Packet201PlayerInfo(var4.username, true, var4.ping));
		}
	}
	
	public void playerLoggedOut(EntityPlayerMP p_72367_1_)
	{
		writePlayerData(p_72367_1_);
		WorldServer var2 = p_72367_1_.getServerForPlayer();
		if(p_72367_1_.ridingEntity != null)
		{
			var2.removeEntity(p_72367_1_.ridingEntity);
			System.out.println("removing player mount");
		}
		var2.removeEntity(p_72367_1_);
		var2.getPlayerManager().removePlayer(p_72367_1_);
		playerEntityList.remove(p_72367_1_);
		sendPacketToAllPlayers(new Packet201PlayerInfo(p_72367_1_.username, false, 9999));
	}
	
	public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP p_72380_1_)
	{
		NBTTagCompound var2 = mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
		NBTTagCompound var3;
		if(p_72380_1_.getCommandSenderName().equals(mcServer.getServerOwner()) && var2 != null)
		{
			p_72380_1_.readFromNBT(var2);
			var3 = var2;
			System.out.println("loading single player");
		} else
		{
			var3 = playerNBTManagerObj.readPlayerData(p_72380_1_);
		}
		return var3;
	}
	
	public void removeAllPlayers()
	{
		while(!playerEntityList.isEmpty())
		{
			((EntityPlayerMP) playerEntityList.get(0)).playerNetServerHandler.kickPlayerFromServer("Server closed");
		}
	}
	
	public void removeFromWhitelist(String p_72379_1_)
	{
		whiteListedPlayers.remove(p_72379_1_);
	}
	
	public void removeOp(String p_72360_1_)
	{
		ops.remove(p_72360_1_.toLowerCase());
	}
	
	public EntityPlayerMP respawnPlayer(EntityPlayerMP p_72368_1_, int p_72368_2_, boolean p_72368_3_)
	{
		p_72368_1_.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(p_72368_1_);
		p_72368_1_.getServerForPlayer().getEntityTracker().removeEntityFromAllTrackingPlayers(p_72368_1_);
		p_72368_1_.getServerForPlayer().getPlayerManager().removePlayer(p_72368_1_);
		playerEntityList.remove(p_72368_1_);
		mcServer.worldServerForDimension(p_72368_1_.dimension).removePlayerEntityDangerously(p_72368_1_);
		ChunkCoordinates var4 = p_72368_1_.getBedLocation();
		boolean var5 = p_72368_1_.isSpawnForced();
		p_72368_1_.dimension = p_72368_2_;
		Object var6;
		if(mcServer.isDemo())
		{
			var6 = new DemoWorldManager(mcServer.worldServerForDimension(p_72368_1_.dimension));
		} else
		{
			var6 = new ItemInWorldManager(mcServer.worldServerForDimension(p_72368_1_.dimension));
		}
		EntityPlayerMP var7 = new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(p_72368_1_.dimension), p_72368_1_.username, (ItemInWorldManager) var6);
		var7.playerNetServerHandler = p_72368_1_.playerNetServerHandler;
		var7.clonePlayer(p_72368_1_, p_72368_3_);
		var7.entityId = p_72368_1_.entityId;
		WorldServer var8 = mcServer.worldServerForDimension(p_72368_1_.dimension);
		func_72381_a(var7, p_72368_1_, var8);
		ChunkCoordinates var9;
		if(var4 != null)
		{
			var9 = EntityPlayer.verifyRespawnCoordinates(mcServer.worldServerForDimension(p_72368_1_.dimension), var4, var5);
			if(var9 != null)
			{
				var7.setLocationAndAngles(var9.posX + 0.5F, var9.posY + 0.1F, var9.posZ + 0.5F, 0.0F, 0.0F);
				var7.setSpawnChunk(var4, var5);
			} else
			{
				var7.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(0, 0));
			}
		}
		var8.theChunkProviderServer.loadChunk((int) var7.posX >> 4, (int) var7.posZ >> 4);
		while(!var8.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty())
		{
			var7.setPosition(var7.posX, var7.posY + 1.0D, var7.posZ);
		}
		var7.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(var7.dimension, (byte) var7.worldObj.difficultySetting, var7.worldObj.getWorldInfo().getTerrainType(), var7.worldObj.getHeight(), var7.theItemInWorldManager.getGameType()));
		var9 = var8.getSpawnPoint();
		var7.playerNetServerHandler.setPlayerLocation(var7.posX, var7.posY, var7.posZ, var7.rotationYaw, var7.rotationPitch);
		var7.playerNetServerHandler.sendPacketToPlayer(new Packet6SpawnPosition(var9.posX, var9.posY, var9.posZ));
		var7.playerNetServerHandler.sendPacketToPlayer(new Packet43Experience(var7.experience, var7.experienceTotal, var7.experienceLevel));
		updateTimeAndWeatherForPlayer(var7, var8);
		var8.getPlayerManager().addPlayer(var7);
		var8.spawnEntityInWorld(var7);
		playerEntityList.add(var7);
		var7.addSelfToInternalCraftingInventory();
		var7.setEntityHealth(var7.getHealth());
		return var7;
	}
	
	public void saveAllPlayerData()
	{
		for(int var1 = 0; var1 < playerEntityList.size(); ++var1)
		{
			writePlayerData((EntityPlayerMP) playerEntityList.get(var1));
		}
	}
	
	public void sendChatMsg(String p_92062_1_)
	{
		mcServer.logInfo(p_92062_1_);
		sendPacketToAllPlayers(new Packet3Chat(p_92062_1_));
	}
	
	public void sendPacketToAllPlayers(Packet p_72384_1_)
	{
		for(int var2 = 0; var2 < playerEntityList.size(); ++var2)
		{
			((EntityPlayerMP) playerEntityList.get(var2)).playerNetServerHandler.sendPacketToPlayer(p_72384_1_);
		}
	}
	
	public void sendPacketToAllPlayersInDimension(Packet p_72396_1_, int p_72396_2_)
	{
		for(int var3 = 0; var3 < playerEntityList.size(); ++var3)
		{
			EntityPlayerMP var4 = (EntityPlayerMP) playerEntityList.get(var3);
			if(var4.dimension == p_72396_2_)
			{
				var4.playerNetServerHandler.sendPacketToPlayer(p_72396_1_);
			}
		}
	}
	
	public void sendPlayerInfoToAllPlayers()
	{
		if(++playerPingIndex > 600)
		{
			playerPingIndex = 0;
		}
		if(playerPingIndex < playerEntityList.size())
		{
			EntityPlayerMP var1 = (EntityPlayerMP) playerEntityList.get(playerPingIndex);
			sendPacketToAllPlayers(new Packet201PlayerInfo(var1.username, true, var1.ping));
		}
	}
	
	public void sendToAllNear(double p_72393_1_, double p_72393_3_, double p_72393_5_, double p_72393_7_, int p_72393_9_, Packet p_72393_10_)
	{
		sendToAllNearExcept((EntityPlayer) null, p_72393_1_, p_72393_3_, p_72393_5_, p_72393_7_, p_72393_9_, p_72393_10_);
	}
	
	public void sendToAllNearExcept(EntityPlayer p_72397_1_, double p_72397_2_, double p_72397_4_, double p_72397_6_, double p_72397_8_, int p_72397_10_, Packet p_72397_11_)
	{
		for(int var12 = 0; var12 < playerEntityList.size(); ++var12)
		{
			EntityPlayerMP var13 = (EntityPlayerMP) playerEntityList.get(var12);
			if(var13 != p_72397_1_ && var13.dimension == p_72397_10_)
			{
				double var14 = p_72397_2_ - var13.posX;
				double var16 = p_72397_4_ - var13.posY;
				double var18 = p_72397_6_ - var13.posZ;
				if(var14 * var14 + var16 * var16 + var18 * var18 < p_72397_8_ * p_72397_8_)
				{
					var13.playerNetServerHandler.sendPacketToPlayer(p_72397_11_);
				}
			}
		}
	}
	
	public void serverUpdateMountedMovingPlayer(EntityPlayerMP p_72358_1_)
	{
		p_72358_1_.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(p_72358_1_);
	}
	
	public void setCommandsAllowedForAll(boolean par1)
	{
		commandsAllowedForAll = par1;
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
		gameType = par1EnumGameType;
	}
	
	public void setPlayerManager(WorldServer[] p_72364_1_)
	{
		playerNBTManagerObj = p_72364_1_[0].getSaveHandler().getSaveHandler();
	}
	
	public void setWhiteListEnabled(boolean p_72371_1_)
	{
		whiteListEnforced = p_72371_1_;
	}
	
	public void syncPlayerInventory(EntityPlayerMP p_72385_1_)
	{
		p_72385_1_.sendContainerToPlayer(p_72385_1_.inventoryContainer);
		p_72385_1_.setPlayerHealthUpdated();
		p_72385_1_.playerNetServerHandler.sendPacketToPlayer(new Packet16BlockItemSwitch(p_72385_1_.inventory.currentItem));
	}
	
	public void transferEntityToWorld(Entity p_82448_1_, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_)
	{
		double var5 = p_82448_1_.posX;
		double var7 = p_82448_1_.posZ;
		double var9 = 8.0D;
		double var11 = p_82448_1_.posX;
		double var13 = p_82448_1_.posY;
		double var15 = p_82448_1_.posZ;
		float var17 = p_82448_1_.rotationYaw;
		p_82448_3_.theProfiler.startSection("moving");
		if(p_82448_1_.dimension == -1)
		{
			var5 /= var9;
			var7 /= var9;
			p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var7, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
			if(p_82448_1_.isEntityAlive())
			{
				p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
			}
		} else if(p_82448_1_.dimension == 0)
		{
			var5 *= var9;
			var7 *= var9;
			p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var7, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
			if(p_82448_1_.isEntityAlive())
			{
				p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
			}
		} else
		{
			ChunkCoordinates var18;
			if(p_82448_2_ == 1)
			{
				var18 = p_82448_4_.getSpawnPoint();
			} else
			{
				var18 = p_82448_4_.getEntrancePortalLocation();
			}
			var5 = var18.posX;
			p_82448_1_.posY = var18.posY;
			var7 = var18.posZ;
			p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var7, 90.0F, 0.0F);
			if(p_82448_1_.isEntityAlive())
			{
				p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
			}
		}
		p_82448_3_.theProfiler.endSection();
		if(p_82448_2_ != 1)
		{
			p_82448_3_.theProfiler.startSection("placing");
			var5 = MathHelper.clamp_int((int) var5, -29999872, 29999872);
			var7 = MathHelper.clamp_int((int) var7, -29999872, 29999872);
			if(p_82448_1_.isEntityAlive())
			{
				p_82448_4_.spawnEntityInWorld(p_82448_1_);
				p_82448_1_.setLocationAndAngles(var5, p_82448_1_.posY, var7, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
				p_82448_4_.updateEntityWithOptionalForce(p_82448_1_, false);
				p_82448_4_.getDefaultTeleporter().placeInPortal(p_82448_1_, var11, var13, var15, var17);
			}
			p_82448_3_.theProfiler.endSection();
		}
		p_82448_1_.setWorld(p_82448_4_);
	}
	
	public void transferPlayerToDimension(EntityPlayerMP p_72356_1_, int p_72356_2_)
	{
		int var3 = p_72356_1_.dimension;
		WorldServer var4 = mcServer.worldServerForDimension(p_72356_1_.dimension);
		p_72356_1_.dimension = p_72356_2_;
		WorldServer var5 = mcServer.worldServerForDimension(p_72356_1_.dimension);
		p_72356_1_.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(p_72356_1_.dimension, (byte) p_72356_1_.worldObj.difficultySetting, var5.getWorldInfo().getTerrainType(), var5.getHeight(), p_72356_1_.theItemInWorldManager.getGameType()));
		var4.removePlayerEntityDangerously(p_72356_1_);
		p_72356_1_.isDead = false;
		transferEntityToWorld(p_72356_1_, var3, var4, var5);
		func_72375_a(p_72356_1_, var4);
		p_72356_1_.playerNetServerHandler.setPlayerLocation(p_72356_1_.posX, p_72356_1_.posY, p_72356_1_.posZ, p_72356_1_.rotationYaw, p_72356_1_.rotationPitch);
		p_72356_1_.theItemInWorldManager.setWorld(var5);
		updateTimeAndWeatherForPlayer(p_72356_1_, var5);
		syncPlayerInventory(p_72356_1_);
		Iterator var6 = p_72356_1_.getActivePotionEffects().iterator();
		while(var6.hasNext())
		{
			PotionEffect var7 = (PotionEffect) var6.next();
			p_72356_1_.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(p_72356_1_.entityId, var7));
		}
	}
	
	public void updateTimeAndWeatherForPlayer(EntityPlayerMP p_72354_1_, WorldServer p_72354_2_)
	{
		p_72354_1_.playerNetServerHandler.sendPacketToPlayer(new Packet4UpdateTime(p_72354_2_.getTotalWorldTime(), p_72354_2_.getWorldTime()));
		if(p_72354_2_.isRaining())
		{
			p_72354_1_.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(1, 0));
		}
	}
	
	protected void writePlayerData(EntityPlayerMP p_72391_1_)
	{
		playerNBTManagerObj.writePlayerData(p_72391_1_);
	}
}
