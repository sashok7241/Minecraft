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
	private int playerPingIndex;
	
	public ServerConfigurationManager(MinecraftServer par1MinecraftServer)
	{
		mcServer = par1MinecraftServer;
		bannedPlayers.setListActive(false);
		bannedIPs.setListActive(false);
		maxPlayers = 8;
	}
	
	public void addOp(String par1Str)
	{
		ops.add(par1Str.toLowerCase());
	}
	
	public void addToWhiteList(String par1Str)
	{
		whiteListedPlayers.add(par1Str);
	}
	
	public String allowUserToConnect(SocketAddress par1SocketAddress, String par2Str)
	{
		if(bannedPlayers.isBanned(par2Str))
		{
			BanEntry var6 = (BanEntry) bannedPlayers.getBannedList().get(par2Str);
			String var7 = "You are banned from this server!\nReason: " + var6.getBanReason();
			if(var6.getBanEndDate() != null)
			{
				var7 = var7 + "\nYour ban will be removed on " + dateFormat.format(var6.getBanEndDate());
			}
			return var7;
		} else if(!isAllowedToLogin(par2Str)) return "You are not white-listed on this server!";
		else
		{
			String var3 = par1SocketAddress.toString();
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
	
	public boolean areCommandsAllowed(String par1Str)
	{
		return ops.contains(par1Str.trim().toLowerCase()) || mcServer.isSinglePlayer() && mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && mcServer.getServerOwner().equalsIgnoreCase(par1Str) || commandsAllowedForAll;
	}
	
	public EntityPlayerMP createPlayerForUser(String par1Str)
	{
		ArrayList var2 = new ArrayList();
		EntityPlayerMP var4;
		for(int var3 = 0; var3 < playerEntityList.size(); ++var3)
		{
			var4 = (EntityPlayerMP) playerEntityList.get(var3);
			if(var4.getCommandSenderName().equalsIgnoreCase(par1Str))
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
		return new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(0), par1Str, (ItemInWorldManager) var6);
	}
	
	public List findPlayers(ChunkCoordinates par1ChunkCoordinates, int par2, int par3, int par4, int par5, int par6, int par7, Map par8Map, String par9Str, String par10Str, World par11World)
	{
		if(playerEntityList.isEmpty()) return null;
		else
		{
			Object var12 = new ArrayList();
			boolean var13 = par4 < 0;
			boolean var14 = par9Str != null && par9Str.startsWith("!");
			boolean var15 = par10Str != null && par10Str.startsWith("!");
			int var16 = par2 * par2;
			int var17 = par3 * par3;
			par4 = MathHelper.abs_int(par4);
			if(var14)
			{
				par9Str = par9Str.substring(1);
			}
			if(var15)
			{
				par10Str = par10Str.substring(1);
			}
			for(int var18 = 0; var18 < playerEntityList.size(); ++var18)
			{
				EntityPlayerMP var19 = (EntityPlayerMP) playerEntityList.get(var18);
				if((par11World == null || var19.worldObj == par11World) && (par9Str == null || var14 != par9Str.equalsIgnoreCase(var19.getEntityName())))
				{
					if(par10Str != null)
					{
						Team var20 = var19.getTeam();
						String var21 = var20 == null ? "" : var20.func_96661_b();
						if(var15 == par10Str.equalsIgnoreCase(var21))
						{
							continue;
						}
					}
					if(par1ChunkCoordinates != null && (par2 > 0 || par3 > 0))
					{
						float var22 = par1ChunkCoordinates.getDistanceSquaredToChunkCoordinates(var19.getPlayerCoordinates());
						if(par2 > 0 && var22 < var16 || par3 > 0 && var22 > var17)
						{
							continue;
						}
					}
					if(func_96457_a(var19, par8Map) && (par5 == EnumGameType.NOT_SET.getID() || par5 == var19.theItemInWorldManager.getGameType().getID()) && (par6 <= 0 || var19.experienceLevel >= par6) && var19.experienceLevel <= par7)
					{
						((List) var12).add(var19);
					}
				}
			}
			if(par1ChunkCoordinates != null)
			{
				Collections.sort((List) var12, new PlayerPositionComparator(par1ChunkCoordinates));
			}
			if(var13)
			{
				Collections.reverse((List) var12);
			}
			if(par4 > 0)
			{
				var12 = ((List) var12).subList(0, Math.min(par4, ((List) var12).size()));
			}
			return (List) var12;
		}
	}
	
	public void func_110459_a(ChatMessageComponent par1ChatMessageComponent, boolean par2)
	{
		mcServer.sendChatToPlayer(par1ChatMessageComponent);
		sendPacketToAllPlayers(new Packet3Chat(par1ChatMessageComponent, par2));
	}
	
	public void func_72375_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
	{
		WorldServer var3 = par1EntityPlayerMP.getServerForPlayer();
		if(par2WorldServer != null)
		{
			par2WorldServer.getPlayerManager().removePlayer(par1EntityPlayerMP);
		}
		var3.getPlayerManager().addPlayer(par1EntityPlayerMP);
		var3.theChunkProviderServer.loadChunk((int) par1EntityPlayerMP.posX >> 4, (int) par1EntityPlayerMP.posZ >> 4);
	}
	
	private void func_72381_a(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP, World par3World)
	{
		if(par2EntityPlayerMP != null)
		{
			par1EntityPlayerMP.theItemInWorldManager.setGameType(par2EntityPlayerMP.theItemInWorldManager.getGameType());
		} else if(gameType != null)
		{
			par1EntityPlayerMP.theItemInWorldManager.setGameType(gameType);
		}
		par1EntityPlayerMP.theItemInWorldManager.initializeGameType(par3World.getWorldInfo().getGameType());
	}
	
	protected void func_96456_a(ServerScoreboard par1ServerScoreboard, EntityPlayerMP par2EntityPlayerMP)
	{
		HashSet var3 = new HashSet();
		Iterator var4 = par1ServerScoreboard.func_96525_g().iterator();
		while(var4.hasNext())
		{
			ScorePlayerTeam var5 = (ScorePlayerTeam) var4.next();
			par2EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet209SetPlayerTeam(var5, 0));
		}
		for(int var9 = 0; var9 < 3; ++var9)
		{
			ScoreObjective var10 = par1ServerScoreboard.func_96539_a(var9);
			if(var10 != null && !var3.contains(var10))
			{
				List var6 = par1ServerScoreboard.func_96550_d(var10);
				Iterator var7 = var6.iterator();
				while(var7.hasNext())
				{
					Packet var8 = (Packet) var7.next();
					par2EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(var8);
				}
				var3.add(var10);
			}
		}
	}
	
	private boolean func_96457_a(EntityPlayer par1EntityPlayer, Map par2Map)
	{
		if(par2Map != null && par2Map.size() != 0)
		{
			Iterator var3 = par2Map.entrySet().iterator();
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
				Scoreboard var7 = par1EntityPlayer.getWorldScoreboard();
				ScoreObjective var8 = var7.getObjective(var5);
				if(var8 == null) return false;
				Score var9 = par1EntityPlayer.getWorldScoreboard().func_96529_a(par1EntityPlayer.getEntityName(), var8);
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
			var1[var2] = ((EntityPlayerMP) playerEntityList.get(var2)).getCommandSenderName();
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
	
	public EntityPlayerMP getPlayerForUsername(String par1Str)
	{
		Iterator var2 = playerEntityList.iterator();
		EntityPlayerMP var3;
		do
		{
			if(!var2.hasNext()) return null;
			var3 = (EntityPlayerMP) var2.next();
		} while(!var3.getCommandSenderName().equalsIgnoreCase(par1Str));
		return var3;
	}
	
	public List getPlayerList(String par1Str)
	{
		ArrayList var2 = new ArrayList();
		Iterator var3 = playerEntityList.iterator();
		while(var3.hasNext())
		{
			EntityPlayerMP var4 = (EntityPlayerMP) var3.next();
			if(var4.getPlayerIP().equals(par1Str))
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
			var1 = var1 + ((EntityPlayerMP) playerEntityList.get(var2)).getCommandSenderName();
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
	
	public void initializeConnectionToPlayer(INetworkManager par1INetworkManager, EntityPlayerMP par2EntityPlayerMP)
	{
		NBTTagCompound var3 = readPlayerDataFromFile(par2EntityPlayerMP);
		par2EntityPlayerMP.setWorld(mcServer.worldServerForDimension(par2EntityPlayerMP.dimension));
		par2EntityPlayerMP.theItemInWorldManager.setWorld((WorldServer) par2EntityPlayerMP.worldObj);
		String var4 = "local";
		if(par1INetworkManager.getSocketAddress() != null)
		{
			var4 = par1INetworkManager.getSocketAddress().toString();
		}
		mcServer.getLogAgent().logInfo(par2EntityPlayerMP.getCommandSenderName() + "[" + var4 + "] logged in with entity id " + par2EntityPlayerMP.entityId + " at (" + par2EntityPlayerMP.posX + ", " + par2EntityPlayerMP.posY + ", " + par2EntityPlayerMP.posZ + ")");
		WorldServer var5 = mcServer.worldServerForDimension(par2EntityPlayerMP.dimension);
		ChunkCoordinates var6 = var5.getSpawnPoint();
		func_72381_a(par2EntityPlayerMP, (EntityPlayerMP) null, var5);
		NetServerHandler var7 = new NetServerHandler(mcServer, par1INetworkManager, par2EntityPlayerMP);
		var7.sendPacketToPlayer(new Packet1Login(par2EntityPlayerMP.entityId, var5.getWorldInfo().getTerrainType(), par2EntityPlayerMP.theItemInWorldManager.getGameType(), var5.getWorldInfo().isHardcoreModeEnabled(), var5.provider.dimensionId, var5.difficultySetting, var5.getHeight(), getMaxPlayers()));
		var7.sendPacketToPlayer(new Packet250CustomPayload("MC|Brand", getServerInstance().getServerModName().getBytes(Charsets.UTF_8)));
		var7.sendPacketToPlayer(new Packet6SpawnPosition(var6.posX, var6.posY, var6.posZ));
		var7.sendPacketToPlayer(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
		var7.sendPacketToPlayer(new Packet16BlockItemSwitch(par2EntityPlayerMP.inventory.currentItem));
		func_96456_a((ServerScoreboard) var5.getScoreboard(), par2EntityPlayerMP);
		updateTimeAndWeatherForPlayer(par2EntityPlayerMP, var5);
		sendChatMsg(ChatMessageComponent.func_111082_b("multiplayer.player.joined", new Object[] { par2EntityPlayerMP.getTranslatedEntityName() }).func_111059_a(EnumChatFormatting.YELLOW));
		playerLoggedIn(par2EntityPlayerMP);
		var7.setPlayerLocation(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
		mcServer.getNetworkThread().addPlayer(var7);
		var7.sendPacketToPlayer(new Packet4UpdateTime(var5.getTotalWorldTime(), var5.getWorldTime(), var5.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
		if(mcServer.getTexturePack().length() > 0)
		{
			par2EntityPlayerMP.requestTexturePackLoad(mcServer.getTexturePack(), mcServer.textureSize());
		}
		Iterator var8 = par2EntityPlayerMP.getActivePotionEffects().iterator();
		while(var8.hasNext())
		{
			PotionEffect var9 = (PotionEffect) var8.next();
			var7.sendPacketToPlayer(new Packet41EntityEffect(par2EntityPlayerMP.entityId, var9));
		}
		par2EntityPlayerMP.addSelfToInternalCraftingInventory();
		if(var3 != null && var3.hasKey("Riding"))
		{
			Entity var10 = EntityList.createEntityFromNBT(var3.getCompoundTag("Riding"), var5);
			if(var10 != null)
			{
				var10.field_98038_p = true;
				var5.spawnEntityInWorld(var10);
				par2EntityPlayerMP.mountEntity(var10);
				var10.field_98038_p = false;
			}
		}
	}
	
	public boolean isAllowedToLogin(String par1Str)
	{
		par1Str = par1Str.trim().toLowerCase();
		return !whiteListEnforced || ops.contains(par1Str) || whiteListedPlayers.contains(par1Str);
	}
	
	public boolean isWhiteListEnabled()
	{
		return whiteListEnforced;
	}
	
	public void loadWhiteList()
	{
	}
	
	public void playerLoggedIn(EntityPlayerMP par1EntityPlayerMP)
	{
		sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.getCommandSenderName(), true, 1000));
		playerEntityList.add(par1EntityPlayerMP);
		WorldServer var2 = mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
		var2.spawnEntityInWorld(par1EntityPlayerMP);
		func_72375_a(par1EntityPlayerMP, (WorldServer) null);
		for(int var3 = 0; var3 < playerEntityList.size(); ++var3)
		{
			EntityPlayerMP var4 = (EntityPlayerMP) playerEntityList.get(var3);
			par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet201PlayerInfo(var4.getCommandSenderName(), true, var4.ping));
		}
	}
	
	public void playerLoggedOut(EntityPlayerMP par1EntityPlayerMP)
	{
		writePlayerData(par1EntityPlayerMP);
		WorldServer var2 = par1EntityPlayerMP.getServerForPlayer();
		if(par1EntityPlayerMP.ridingEntity != null)
		{
			var2.removePlayerEntityDangerously(par1EntityPlayerMP.ridingEntity);
			System.out.println("removing player mount");
		}
		var2.removeEntity(par1EntityPlayerMP);
		var2.getPlayerManager().removePlayer(par1EntityPlayerMP);
		playerEntityList.remove(par1EntityPlayerMP);
		sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.getCommandSenderName(), false, 9999));
	}
	
	public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP par1EntityPlayerMP)
	{
		NBTTagCompound var2 = mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
		NBTTagCompound var3;
		if(par1EntityPlayerMP.getCommandSenderName().equals(mcServer.getServerOwner()) && var2 != null)
		{
			par1EntityPlayerMP.readFromNBT(var2);
			var3 = var2;
			System.out.println("loading single player");
		} else
		{
			var3 = playerNBTManagerObj.readPlayerData(par1EntityPlayerMP);
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
	
	public void removeFromWhitelist(String par1Str)
	{
		whiteListedPlayers.remove(par1Str);
	}
	
	public void removeOp(String par1Str)
	{
		ops.remove(par1Str.toLowerCase());
	}
	
	public EntityPlayerMP respawnPlayer(EntityPlayerMP par1EntityPlayerMP, int par2, boolean par3)
	{
		par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(par1EntityPlayerMP);
		par1EntityPlayerMP.getServerForPlayer().getEntityTracker().removeEntityFromAllTrackingPlayers(par1EntityPlayerMP);
		par1EntityPlayerMP.getServerForPlayer().getPlayerManager().removePlayer(par1EntityPlayerMP);
		playerEntityList.remove(par1EntityPlayerMP);
		mcServer.worldServerForDimension(par1EntityPlayerMP.dimension).removePlayerEntityDangerously(par1EntityPlayerMP);
		ChunkCoordinates var4 = par1EntityPlayerMP.getBedLocation();
		boolean var5 = par1EntityPlayerMP.isSpawnForced();
		par1EntityPlayerMP.dimension = par2;
		Object var6;
		if(mcServer.isDemo())
		{
			var6 = new DemoWorldManager(mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
		} else
		{
			var6 = new ItemInWorldManager(mcServer.worldServerForDimension(par1EntityPlayerMP.dimension));
		}
		EntityPlayerMP var7 = new EntityPlayerMP(mcServer, mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), par1EntityPlayerMP.getCommandSenderName(), (ItemInWorldManager) var6);
		var7.playerNetServerHandler = par1EntityPlayerMP.playerNetServerHandler;
		var7.clonePlayer(par1EntityPlayerMP, par3);
		var7.entityId = par1EntityPlayerMP.entityId;
		WorldServer var8 = mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
		func_72381_a(var7, par1EntityPlayerMP, var8);
		ChunkCoordinates var9;
		if(var4 != null)
		{
			var9 = EntityPlayer.verifyRespawnCoordinates(mcServer.worldServerForDimension(par1EntityPlayerMP.dimension), var4, var5);
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
		var7.setEntityHealth(var7.func_110143_aJ());
		return var7;
	}
	
	public void saveAllPlayerData()
	{
		for(int var1 = 0; var1 < playerEntityList.size(); ++var1)
		{
			writePlayerData((EntityPlayerMP) playerEntityList.get(var1));
		}
	}
	
	public void sendChatMsg(ChatMessageComponent par1ChatMessageComponent)
	{
		func_110459_a(par1ChatMessageComponent, true);
	}
	
	public void sendPacketToAllPlayers(Packet par1Packet)
	{
		for(int var2 = 0; var2 < playerEntityList.size(); ++var2)
		{
			((EntityPlayerMP) playerEntityList.get(var2)).playerNetServerHandler.sendPacketToPlayer(par1Packet);
		}
	}
	
	public void sendPacketToAllPlayersInDimension(Packet par1Packet, int par2)
	{
		for(int var3 = 0; var3 < playerEntityList.size(); ++var3)
		{
			EntityPlayerMP var4 = (EntityPlayerMP) playerEntityList.get(var3);
			if(var4.dimension == par2)
			{
				var4.playerNetServerHandler.sendPacketToPlayer(par1Packet);
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
			sendPacketToAllPlayers(new Packet201PlayerInfo(var1.getCommandSenderName(), true, var1.ping));
		}
	}
	
	public void sendToAllNear(double par1, double par3, double par5, double par7, int par9, Packet par10Packet)
	{
		sendToAllNearExcept((EntityPlayer) null, par1, par3, par5, par7, par9, par10Packet);
	}
	
	public void sendToAllNearExcept(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, double par8, int par10, Packet par11Packet)
	{
		for(int var12 = 0; var12 < playerEntityList.size(); ++var12)
		{
			EntityPlayerMP var13 = (EntityPlayerMP) playerEntityList.get(var12);
			if(var13 != par1EntityPlayer && var13.dimension == par10)
			{
				double var14 = par2 - var13.posX;
				double var16 = par4 - var13.posY;
				double var18 = par6 - var13.posZ;
				if(var14 * var14 + var16 * var16 + var18 * var18 < par8 * par8)
				{
					var13.playerNetServerHandler.sendPacketToPlayer(par11Packet);
				}
			}
		}
	}
	
	public void serverUpdateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP)
	{
		par1EntityPlayerMP.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(par1EntityPlayerMP);
	}
	
	public void setCommandsAllowedForAll(boolean par1)
	{
		commandsAllowedForAll = par1;
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
		gameType = par1EnumGameType;
	}
	
	public void setPlayerManager(WorldServer[] par1ArrayOfWorldServer)
	{
		playerNBTManagerObj = par1ArrayOfWorldServer[0].getSaveHandler().getSaveHandler();
	}
	
	public void setWhiteListEnabled(boolean par1)
	{
		whiteListEnforced = par1;
	}
	
	public void syncPlayerInventory(EntityPlayerMP par1EntityPlayerMP)
	{
		par1EntityPlayerMP.sendContainerToPlayer(par1EntityPlayerMP.inventoryContainer);
		par1EntityPlayerMP.setPlayerHealthUpdated();
		par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet16BlockItemSwitch(par1EntityPlayerMP.inventory.currentItem));
	}
	
	public void transferEntityToWorld(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer)
	{
		double var5 = par1Entity.posX;
		double var7 = par1Entity.posZ;
		double var9 = 8.0D;
		double var11 = par1Entity.posX;
		double var13 = par1Entity.posY;
		double var15 = par1Entity.posZ;
		float var17 = par1Entity.rotationYaw;
		par3WorldServer.theProfiler.startSection("moving");
		if(par1Entity.dimension == -1)
		{
			var5 /= var9;
			var7 /= var9;
			par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
			if(par1Entity.isEntityAlive())
			{
				par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
			}
		} else if(par1Entity.dimension == 0)
		{
			var5 *= var9;
			var7 *= var9;
			par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
			if(par1Entity.isEntityAlive())
			{
				par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
			}
		} else
		{
			ChunkCoordinates var18;
			if(par2 == 1)
			{
				var18 = par4WorldServer.getSpawnPoint();
			} else
			{
				var18 = par4WorldServer.getEntrancePortalLocation();
			}
			var5 = var18.posX;
			par1Entity.posY = var18.posY;
			var7 = var18.posZ;
			par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, 90.0F, 0.0F);
			if(par1Entity.isEntityAlive())
			{
				par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
			}
		}
		par3WorldServer.theProfiler.endSection();
		if(par2 != 1)
		{
			par3WorldServer.theProfiler.startSection("placing");
			var5 = MathHelper.clamp_int((int) var5, -29999872, 29999872);
			var7 = MathHelper.clamp_int((int) var7, -29999872, 29999872);
			if(par1Entity.isEntityAlive())
			{
				par4WorldServer.spawnEntityInWorld(par1Entity);
				par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
				par4WorldServer.updateEntityWithOptionalForce(par1Entity, false);
				par4WorldServer.getDefaultTeleporter().placeInPortal(par1Entity, var11, var13, var15, var17);
			}
			par3WorldServer.theProfiler.endSection();
		}
		par1Entity.setWorld(par4WorldServer);
	}
	
	public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2)
	{
		int var3 = par1EntityPlayerMP.dimension;
		WorldServer var4 = mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
		par1EntityPlayerMP.dimension = par2;
		WorldServer var5 = mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
		par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte) par1EntityPlayerMP.worldObj.difficultySetting, var5.getWorldInfo().getTerrainType(), var5.getHeight(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
		var4.removePlayerEntityDangerously(par1EntityPlayerMP);
		par1EntityPlayerMP.isDead = false;
		transferEntityToWorld(par1EntityPlayerMP, var3, var4, var5);
		func_72375_a(par1EntityPlayerMP, var4);
		par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
		par1EntityPlayerMP.theItemInWorldManager.setWorld(var5);
		updateTimeAndWeatherForPlayer(par1EntityPlayerMP, var5);
		syncPlayerInventory(par1EntityPlayerMP);
		Iterator var6 = par1EntityPlayerMP.getActivePotionEffects().iterator();
		while(var6.hasNext())
		{
			PotionEffect var7 = (PotionEffect) var6.next();
			par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(par1EntityPlayerMP.entityId, var7));
		}
	}
	
	public void updateTimeAndWeatherForPlayer(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
	{
		par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet4UpdateTime(par2WorldServer.getTotalWorldTime(), par2WorldServer.getWorldTime(), par2WorldServer.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
		if(par2WorldServer.isRaining())
		{
			par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(1, 0));
		}
	}
	
	protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP)
	{
		playerNBTManagerObj.writePlayerData(par1EntityPlayerMP);
	}
}
