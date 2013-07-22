package net.minecraft.src;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.SecretKey;

import net.minecraft.client.Minecraft;

public class NetClientHandler extends NetHandler
{
	private boolean disconnected = false;
	private INetworkManager netManager;
	public String field_72560_a;
	private Minecraft mc;
	private WorldClient worldClient;
	private boolean doneLoadingTerrain = false;
	public MapStorage mapStorage = new MapStorage((ISaveHandler) null);
	private Map playerInfoMap = new HashMap();
	public List playerInfoList = new ArrayList();
	public int currentServerMaxPlayers = 20;
	private GuiScreen field_98183_l = null;
	Random rand = new Random();
	
	public NetClientHandler(Minecraft p_i3104_1_, IntegratedServer p_i3104_2_) throws IOException
	{
		mc = p_i3104_1_;
		netManager = new MemoryConnection(p_i3104_1_.getLogAgent(), this);
		p_i3104_2_.getServerListeningThread().func_71754_a((MemoryConnection) netManager, p_i3104_1_.session.username);
	}
	
	public NetClientHandler(Minecraft p_i3103_1_, String p_i3103_2_, int p_i3103_3_) throws IOException
	{
		mc = p_i3103_1_;
		Socket var4 = new Socket(InetAddress.getByName(p_i3103_2_), p_i3103_3_);
		netManager = new TcpConnection(p_i3103_1_.getLogAgent(), var4, "Client", this);
	}
	
	public NetClientHandler(Minecraft p_i11012_1_, String p_i11012_2_, int p_i11012_3_, GuiScreen p_i11012_4_) throws IOException
	{
		mc = p_i11012_1_;
		field_98183_l = p_i11012_4_;
		Socket var5 = new Socket(InetAddress.getByName(p_i11012_2_), p_i11012_3_);
		netManager = new TcpConnection(p_i11012_1_.getLogAgent(), var5, "Client", this);
	}
	
	public void addToSendQueue(Packet par1Packet)
	{
		if(!disconnected)
		{
			netManager.addToSendQueue(par1Packet);
		}
	}
	
	@Override public boolean canProcessPacketsAsync()
	{
		return mc != null && mc.theWorld != null && mc.thePlayer != null && worldClient != null;
	}
	
	public void cleanup()
	{
		if(netManager != null)
		{
			netManager.wakeThreads();
		}
		netManager = null;
		worldClient = null;
	}
	
	public void disconnect()
	{
		disconnected = true;
		netManager.wakeThreads();
		netManager.networkShutdown("disconnect.closed", new Object[0]);
	}
	
	private Entity getEntityByID(int par1)
	{
		return par1 == mc.thePlayer.entityId ? mc.thePlayer : worldClient.getEntityByID(par1);
	}
	
	public INetworkManager getNetManager()
	{
		return netManager;
	}
	
	@Override public void handleAnimation(Packet18Animation p_72524_1_)
	{
		Entity var2 = getEntityByID(p_72524_1_.entityId);
		if(var2 != null)
		{
			if(p_72524_1_.animate == 1)
			{
				EntityLiving var3 = (EntityLiving) var2;
				var3.swingItem();
			} else if(p_72524_1_.animate == 2)
			{
				var2.performHurtAnimation();
			} else if(p_72524_1_.animate == 3)
			{
				EntityPlayer var4 = (EntityPlayer) var2;
				var4.wakeUpPlayer(false, false, false);
			} else if(p_72524_1_.animate != 4)
			{
				if(p_72524_1_.animate == 6)
				{
					mc.effectRenderer.addEffect(new EntityCrit2FX(mc.theWorld, var2));
				} else if(p_72524_1_.animate == 7)
				{
					EntityCrit2FX var5 = new EntityCrit2FX(mc.theWorld, var2, "magicCrit");
					mc.effectRenderer.addEffect(var5);
				} else if(p_72524_1_.animate == 5 && var2 instanceof EntityOtherPlayerMP)
				{
					;
				}
			}
		}
	}
	
	@Override public void handleAttachEntity(Packet39AttachEntity p_72484_1_)
	{
		Object var2 = getEntityByID(p_72484_1_.entityId);
		Entity var3 = getEntityByID(p_72484_1_.vehicleEntityId);
		if(p_72484_1_.entityId == mc.thePlayer.entityId)
		{
			var2 = mc.thePlayer;
			if(var3 instanceof EntityBoat)
			{
				((EntityBoat) var3).func_70270_d(false);
			}
		} else if(var3 instanceof EntityBoat)
		{
			((EntityBoat) var3).func_70270_d(true);
		}
		if(var2 != null)
		{
			((Entity) var2).mountEntity(var3);
		}
	}
	
	@Override public void handleAutoComplete(Packet203AutoComplete p_72461_1_)
	{
		String[] var2 = p_72461_1_.getText().split("\u0000");
		if(mc.currentScreen instanceof GuiChat)
		{
			GuiChat var3 = (GuiChat) mc.currentScreen;
			var3.func_73894_a(var2);
		}
	}
	
	@Override public void handleBlockChange(Packet53BlockChange p_72456_1_)
	{
		worldClient.setBlockAndMetadataAndInvalidate(p_72456_1_.xPosition, p_72456_1_.yPosition, p_72456_1_.zPosition, p_72456_1_.type, p_72456_1_.metadata);
	}
	
	@Override public void handleBlockDestroy(Packet55BlockDestroy p_72465_1_)
	{
		mc.theWorld.destroyBlockInWorldPartially(p_72465_1_.getEntityId(), p_72465_1_.getPosX(), p_72465_1_.getPosY(), p_72465_1_.getPosZ(), p_72465_1_.getDestroyedStage());
	}
	
	@Override public void handleBlockEvent(Packet54PlayNoteBlock p_72454_1_)
	{
		mc.theWorld.addBlockEvent(p_72454_1_.xLocation, p_72454_1_.yLocation, p_72454_1_.zLocation, p_72454_1_.blockId, p_72454_1_.instrumentType, p_72454_1_.pitch);
	}
	
	@Override public void handleBlockItemSwitch(Packet16BlockItemSwitch p_72502_1_)
	{
		if(p_72502_1_.id >= 0 && p_72502_1_.id < InventoryPlayer.getHotbarSize())
		{
			mc.thePlayer.inventory.currentItem = p_72502_1_.id;
		}
	}
	
	@Override public void handleChat(Packet3Chat p_72481_1_)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(p_72481_1_.message);
	}
	
	@Override public void handleCloseWindow(Packet101CloseWindow p_72474_1_)
	{
		mc.thePlayer.func_92015_f();
	}
	
	@Override public void handleCollect(Packet22Collect p_72475_1_)
	{
		Entity var2 = getEntityByID(p_72475_1_.collectedEntityId);
		Object var3 = getEntityByID(p_72475_1_.collectorEntityId);
		if(var3 == null)
		{
			var3 = mc.thePlayer;
		}
		if(var2 != null)
		{
			if(var2 instanceof EntityXPOrb)
			{
				worldClient.playSoundAtEntity(var2, "random.orb", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			} else
			{
				worldClient.playSoundAtEntity(var2, "random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			}
			mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, var2, (Entity) var3, -0.5F));
			worldClient.removeEntityFromWorld(p_72475_1_.collectedEntityId);
		}
	}
	
	@Override public void handleCustomPayload(Packet250CustomPayload p_72501_1_)
	{
		if("MC|TPack".equals(p_72501_1_.channel))
		{
			String[] var2 = new String(p_72501_1_.data).split("\u0000");
			String var3 = var2[0];
			if(var2[1].equals("16"))
			{
				if(mc.texturePackList.getAcceptsTextures())
				{
					mc.texturePackList.requestDownloadOfTexture(var3);
				} else if(mc.texturePackList.func_77300_f())
				{
					mc.displayGuiScreen(new GuiYesNo(new NetClientWebTextures(this, var3), StringTranslate.getInstance().translateKey("multiplayer.texturePrompt.line1"), StringTranslate.getInstance().translateKey("multiplayer.texturePrompt.line2"), 0));
				}
			}
		} else if("MC|TrList".equals(p_72501_1_.channel))
		{
			DataInputStream var8 = new DataInputStream(new ByteArrayInputStream(p_72501_1_.data));
			try
			{
				int var9 = var8.readInt();
				GuiScreen var4 = mc.currentScreen;
				if(var4 != null && var4 instanceof GuiMerchant && var9 == mc.thePlayer.openContainer.windowId)
				{
					IMerchant var5 = ((GuiMerchant) var4).getIMerchant();
					MerchantRecipeList var6 = MerchantRecipeList.readRecipiesFromStream(var8);
					var5.setRecipes(var6);
				}
			} catch(IOException var7)
			{
				var7.printStackTrace();
			}
		}
	}
	
	@Override public void handleDestroyEntity(Packet29DestroyEntity p_72491_1_)
	{
		for(int element : p_72491_1_.entityId)
		{
			worldClient.removeEntityFromWorld(element);
		}
	}
	
	@Override public void handleDoorChange(Packet61DoorChange p_72462_1_)
	{
		if(p_72462_1_.getRelativeVolumeDisabled())
		{
			mc.theWorld.func_82739_e(p_72462_1_.sfxID, p_72462_1_.posX, p_72462_1_.posY, p_72462_1_.posZ, p_72462_1_.auxData);
		} else
		{
			mc.theWorld.playAuxSFX(p_72462_1_.sfxID, p_72462_1_.posX, p_72462_1_.posY, p_72462_1_.posZ, p_72462_1_.auxData);
		}
	}
	
	@Override public void handleEntity(Packet30Entity p_72482_1_)
	{
		Entity var2 = getEntityByID(p_72482_1_.entityId);
		if(var2 != null)
		{
			var2.serverPosX += p_72482_1_.xPosition;
			var2.serverPosY += p_72482_1_.yPosition;
			var2.serverPosZ += p_72482_1_.zPosition;
			double var3 = var2.serverPosX / 32.0D;
			double var5 = var2.serverPosY / 32.0D;
			double var7 = var2.serverPosZ / 32.0D;
			float var9 = p_72482_1_.rotating ? p_72482_1_.yaw * 360 / 256.0F : var2.rotationYaw;
			float var10 = p_72482_1_.rotating ? p_72482_1_.pitch * 360 / 256.0F : var2.rotationPitch;
			var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
		}
	}
	
	@Override public void handleEntityEffect(Packet41EntityEffect p_72503_1_)
	{
		Entity var2 = getEntityByID(p_72503_1_.entityId);
		if(var2 instanceof EntityLiving)
		{
			PotionEffect var3 = new PotionEffect(p_72503_1_.effectId, p_72503_1_.duration, p_72503_1_.effectAmplifier);
			var3.setPotionDurationMax(p_72503_1_.isDurationMax());
			((EntityLiving) var2).addPotionEffect(var3);
		}
	}
	
	@Override public void handleEntityExpOrb(Packet26EntityExpOrb p_72514_1_)
	{
		EntityXPOrb var2 = new EntityXPOrb(worldClient, p_72514_1_.posX, p_72514_1_.posY, p_72514_1_.posZ, p_72514_1_.xpValue);
		var2.serverPosX = p_72514_1_.posX;
		var2.serverPosY = p_72514_1_.posY;
		var2.serverPosZ = p_72514_1_.posZ;
		var2.rotationYaw = 0.0F;
		var2.rotationPitch = 0.0F;
		var2.entityId = p_72514_1_.entityId;
		worldClient.addEntityToWorld(p_72514_1_.entityId, var2);
	}
	
	@Override public void handleEntityHeadRotation(Packet35EntityHeadRotation p_72478_1_)
	{
		Entity var2 = getEntityByID(p_72478_1_.entityId);
		if(var2 != null)
		{
			float var3 = p_72478_1_.headRotationYaw * 360 / 256.0F;
			var2.setRotationYawHead(var3);
		}
	}
	
	@Override public void handleEntityMetadata(Packet40EntityMetadata p_72493_1_)
	{
		Entity var2 = getEntityByID(p_72493_1_.entityId);
		if(var2 != null && p_72493_1_.getMetadata() != null)
		{
			var2.getDataWatcher().updateWatchedObjectsFromList(p_72493_1_.getMetadata());
		}
	}
	
	@Override public void handleEntityPainting(Packet25EntityPainting p_72495_1_)
	{
		EntityPainting var2 = new EntityPainting(worldClient, p_72495_1_.xPosition, p_72495_1_.yPosition, p_72495_1_.zPosition, p_72495_1_.direction, p_72495_1_.title);
		worldClient.addEntityToWorld(p_72495_1_.entityId, var2);
	}
	
	@Override public void handleEntityStatus(Packet38EntityStatus p_72485_1_)
	{
		Entity var2 = getEntityByID(p_72485_1_.entityId);
		if(var2 != null)
		{
			var2.handleHealthUpdate(p_72485_1_.entityStatus);
		}
	}
	
	@Override public void handleEntityTeleport(Packet34EntityTeleport p_72512_1_)
	{
		Entity var2 = getEntityByID(p_72512_1_.entityId);
		if(var2 != null)
		{
			var2.serverPosX = p_72512_1_.xPosition;
			var2.serverPosY = p_72512_1_.yPosition;
			var2.serverPosZ = p_72512_1_.zPosition;
			double var3 = var2.serverPosX / 32.0D;
			double var5 = var2.serverPosY / 32.0D + 0.015625D;
			double var7 = var2.serverPosZ / 32.0D;
			float var9 = p_72512_1_.yaw * 360 / 256.0F;
			float var10 = p_72512_1_.pitch * 360 / 256.0F;
			var2.setPositionAndRotation2(var3, var5, var7, var9, var10, 3);
		}
	}
	
	@Override public void handleEntityVelocity(Packet28EntityVelocity p_72520_1_)
	{
		Entity var2 = getEntityByID(p_72520_1_.entityId);
		if(var2 != null)
		{
			var2.setVelocity(p_72520_1_.motionX / 8000.0D, p_72520_1_.motionY / 8000.0D, p_72520_1_.motionZ / 8000.0D);
		}
	}
	
	@Override public void handleErrorMessage(String p_72515_1_, Object[] p_72515_2_)
	{
		if(!disconnected)
		{
			disconnected = true;
			mc.loadWorld((WorldClient) null);
			if(field_98183_l != null)
			{
				mc.displayGuiScreen(new GuiScreenDisconnectedOnline(field_98183_l, "disconnect.lost", p_72515_1_, p_72515_2_));
			} else
			{
				mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", p_72515_1_, p_72515_2_));
			}
		}
	}
	
	@Override public void handleExperience(Packet43Experience p_72522_1_)
	{
		mc.thePlayer.setXPStats(p_72522_1_.experience, p_72522_1_.experienceTotal, p_72522_1_.experienceLevel);
	}
	
	@Override public void handleExplosion(Packet60Explosion p_72499_1_)
	{
		Explosion var2 = new Explosion(mc.theWorld, (Entity) null, p_72499_1_.explosionX, p_72499_1_.explosionY, p_72499_1_.explosionZ, p_72499_1_.explosionSize);
		var2.affectedBlockPositions = p_72499_1_.chunkPositionRecords;
		var2.doExplosionB(true);
		mc.thePlayer.motionX += p_72499_1_.getPlayerVelocityX();
		mc.thePlayer.motionY += p_72499_1_.getPlayerVelocityY();
		mc.thePlayer.motionZ += p_72499_1_.getPlayerVelocityZ();
	}
	
	@Override public void handleFlying(Packet10Flying p_72498_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		double var3 = var2.posX;
		double var5 = var2.posY;
		double var7 = var2.posZ;
		float var9 = var2.rotationYaw;
		float var10 = var2.rotationPitch;
		if(p_72498_1_.moving)
		{
			var3 = p_72498_1_.xPosition;
			var5 = p_72498_1_.yPosition;
			var7 = p_72498_1_.zPosition;
		}
		if(p_72498_1_.rotating)
		{
			var9 = p_72498_1_.yaw;
			var10 = p_72498_1_.pitch;
		}
		var2.ySize = 0.0F;
		var2.motionX = var2.motionY = var2.motionZ = 0.0D;
		var2.setPositionAndRotation(var3, var5, var7, var9, var10);
		p_72498_1_.xPosition = var2.posX;
		p_72498_1_.yPosition = var2.boundingBox.minY;
		p_72498_1_.zPosition = var2.posZ;
		p_72498_1_.stance = var2.posY;
		netManager.addToSendQueue(p_72498_1_);
		if(!doneLoadingTerrain)
		{
			mc.thePlayer.prevPosX = mc.thePlayer.posX;
			mc.thePlayer.prevPosY = mc.thePlayer.posY;
			mc.thePlayer.prevPosZ = mc.thePlayer.posZ;
			doneLoadingTerrain = true;
			mc.displayGuiScreen((GuiScreen) null);
		}
	}
	
	@Override public void handleGameEvent(Packet70GameEvent p_72488_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		int var3 = p_72488_1_.eventType;
		int var4 = p_72488_1_.gameMode;
		if(var3 >= 0 && var3 < Packet70GameEvent.clientMessage.length && Packet70GameEvent.clientMessage[var3] != null)
		{
			var2.addChatMessage(Packet70GameEvent.clientMessage[var3]);
		}
		if(var3 == 1)
		{
			worldClient.getWorldInfo().setRaining(true);
			worldClient.setRainStrength(0.0F);
		} else if(var3 == 2)
		{
			worldClient.getWorldInfo().setRaining(false);
			worldClient.setRainStrength(1.0F);
		} else if(var3 == 3)
		{
			mc.playerController.setGameType(EnumGameType.getByID(var4));
		} else if(var3 == 4)
		{
			mc.displayGuiScreen(new GuiWinGame());
		} else if(var3 == 5)
		{
			GameSettings var5 = mc.gameSettings;
			if(var4 == 0)
			{
				mc.displayGuiScreen(new GuiScreenDemo());
			} else if(var4 == 101)
			{
				mc.ingameGUI.getChatGUI().addTranslatedMessage("demo.help.movement", new Object[] { Keyboard.getKeyName(var5.keyBindForward.keyCode), Keyboard.getKeyName(var5.keyBindLeft.keyCode), Keyboard.getKeyName(var5.keyBindBack.keyCode), Keyboard.getKeyName(var5.keyBindRight.keyCode) });
			} else if(var4 == 102)
			{
				mc.ingameGUI.getChatGUI().addTranslatedMessage("demo.help.jump", new Object[] { Keyboard.getKeyName(var5.keyBindJump.keyCode) });
			} else if(var4 == 103)
			{
				mc.ingameGUI.getChatGUI().addTranslatedMessage("demo.help.inventory", new Object[] { Keyboard.getKeyName(var5.keyBindInventory.keyCode) });
			}
		} else if(var3 == 6)
		{
			worldClient.playSound(var2.posX, var2.posY + var2.getEyeHeight(), var2.posZ, "random.successful_hit", 0.18F, 0.45F, false);
		}
	}
	
	@Override public void handleKeepAlive(Packet0KeepAlive p_72477_1_)
	{
		addToSendQueue(new Packet0KeepAlive(p_72477_1_.randomId));
	}
	
	@Override public void handleKickDisconnect(Packet255KickDisconnect p_72492_1_)
	{
		netManager.networkShutdown("disconnect.kicked", new Object[0]);
		disconnected = true;
		mc.loadWorld((WorldClient) null);
		if(field_98183_l != null)
		{
			mc.displayGuiScreen(new GuiScreenDisconnectedOnline(field_98183_l, "disconnect.disconnected", "disconnect.genericReason", new Object[] { p_72492_1_.reason }));
		} else
		{
			mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.disconnected", "disconnect.genericReason", new Object[] { p_72492_1_.reason }));
		}
	}
	
	@Override public void handleLevelSound(Packet62LevelSound p_72457_1_)
	{
		mc.theWorld.playSound(p_72457_1_.getEffectX(), p_72457_1_.getEffectY(), p_72457_1_.getEffectZ(), p_72457_1_.getSoundName(), p_72457_1_.getVolume(), p_72457_1_.getPitch(), false);
	}
	
	@Override public void handleLogin(Packet1Login p_72455_1_)
	{
		mc.playerController = new PlayerControllerMP(mc, this);
		mc.statFileWriter.readStat(StatList.joinMultiplayerStat, 1);
		worldClient = new WorldClient(this, new WorldSettings(0L, p_72455_1_.gameType, false, p_72455_1_.hardcoreMode, p_72455_1_.terrainType), p_72455_1_.dimension, p_72455_1_.difficultySetting, mc.mcProfiler, mc.getLogAgent());
		worldClient.isRemote = true;
		mc.loadWorld(worldClient);
		mc.thePlayer.dimension = p_72455_1_.dimension;
		mc.displayGuiScreen(new GuiDownloadTerrain(this));
		mc.thePlayer.entityId = p_72455_1_.clientEntityId;
		currentServerMaxPlayers = p_72455_1_.maxPlayers;
		mc.playerController.setGameType(p_72455_1_.gameType);
		mc.gameSettings.sendSettingsToServer();
	}
	
	@Override public void handleMapChunk(Packet51MapChunk p_72463_1_)
	{
		if(p_72463_1_.includeInitialize)
		{
			if(p_72463_1_.yChMin == 0)
			{
				worldClient.doPreChunk(p_72463_1_.xCh, p_72463_1_.zCh, false);
				return;
			}
			worldClient.doPreChunk(p_72463_1_.xCh, p_72463_1_.zCh, true);
		}
		worldClient.invalidateBlockReceiveRegion(p_72463_1_.xCh << 4, 0, p_72463_1_.zCh << 4, (p_72463_1_.xCh << 4) + 15, 256, (p_72463_1_.zCh << 4) + 15);
		Chunk var2 = worldClient.getChunkFromChunkCoords(p_72463_1_.xCh, p_72463_1_.zCh);
		if(p_72463_1_.includeInitialize && var2 == null)
		{
			worldClient.doPreChunk(p_72463_1_.xCh, p_72463_1_.zCh, true);
			var2 = worldClient.getChunkFromChunkCoords(p_72463_1_.xCh, p_72463_1_.zCh);
		}
		if(var2 != null)
		{
			var2.fillChunk(p_72463_1_.getCompressedChunkData(), p_72463_1_.yChMin, p_72463_1_.yChMax, p_72463_1_.includeInitialize);
			worldClient.markBlockRangeForRenderUpdate(p_72463_1_.xCh << 4, 0, p_72463_1_.zCh << 4, (p_72463_1_.xCh << 4) + 15, 256, (p_72463_1_.zCh << 4) + 15);
			if(!p_72463_1_.includeInitialize || !(worldClient.provider instanceof WorldProviderSurface))
			{
				var2.resetRelightChecks();
			}
		}
	}
	
	@Override public void handleMapChunks(Packet56MapChunks p_72453_1_)
	{
		for(int var2 = 0; var2 < p_72453_1_.getNumberOfChunkInPacket(); ++var2)
		{
			int var3 = p_72453_1_.getChunkPosX(var2);
			int var4 = p_72453_1_.getChunkPosZ(var2);
			worldClient.doPreChunk(var3, var4, true);
			worldClient.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
			Chunk var5 = worldClient.getChunkFromChunkCoords(var3, var4);
			if(var5 == null)
			{
				worldClient.doPreChunk(var3, var4, true);
				var5 = worldClient.getChunkFromChunkCoords(var3, var4);
			}
			if(var5 != null)
			{
				var5.fillChunk(p_72453_1_.getChunkCompressedData(var2), p_72453_1_.field_73590_a[var2], p_72453_1_.field_73588_b[var2], true);
				worldClient.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
				if(!(worldClient.provider instanceof WorldProviderSurface))
				{
					var5.resetRelightChecks();
				}
			}
		}
	}
	
	@Override public void handleMapData(Packet131MapData p_72494_1_)
	{
		if(p_72494_1_.itemID == Item.map.itemID)
		{
			ItemMap.getMPMapData(p_72494_1_.uniqueID, mc.theWorld).updateMPMapData(p_72494_1_.itemData);
		} else
		{
			mc.getLogAgent().logWarning("Unknown itemid: " + p_72494_1_.uniqueID);
		}
	}
	
	@Override public void handleMobSpawn(Packet24MobSpawn p_72519_1_)
	{
		double var2 = p_72519_1_.xPosition / 32.0D;
		double var4 = p_72519_1_.yPosition / 32.0D;
		double var6 = p_72519_1_.zPosition / 32.0D;
		float var8 = p_72519_1_.yaw * 360 / 256.0F;
		float var9 = p_72519_1_.pitch * 360 / 256.0F;
		EntityLiving var10 = (EntityLiving) EntityList.createEntityByID(p_72519_1_.type, mc.theWorld);
		var10.serverPosX = p_72519_1_.xPosition;
		var10.serverPosY = p_72519_1_.yPosition;
		var10.serverPosZ = p_72519_1_.zPosition;
		var10.rotationYawHead = p_72519_1_.headYaw * 360 / 256.0F;
		Entity[] var11 = var10.getParts();
		if(var11 != null)
		{
			int var12 = p_72519_1_.entityId - var10.entityId;
			for(int var13 = 0; var13 < var11.length; ++var13)
			{
				var11[var13].entityId += var12;
			}
		}
		var10.entityId = p_72519_1_.entityId;
		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		var10.motionX = p_72519_1_.velocityX / 8000.0F;
		var10.motionY = p_72519_1_.velocityY / 8000.0F;
		var10.motionZ = p_72519_1_.velocityZ / 8000.0F;
		worldClient.addEntityToWorld(p_72519_1_.entityId, var10);
		List var14 = p_72519_1_.getMetadata();
		if(var14 != null)
		{
			var10.getDataWatcher().updateWatchedObjectsFromList(var14);
		}
	}
	
	@Override public void handleMultiBlockChange(Packet52MultiBlockChange p_72496_1_)
	{
		int var2 = p_72496_1_.xPosition * 16;
		int var3 = p_72496_1_.zPosition * 16;
		if(p_72496_1_.metadataArray != null)
		{
			DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(p_72496_1_.metadataArray));
			try
			{
				for(int var5 = 0; var5 < p_72496_1_.size; ++var5)
				{
					short var6 = var4.readShort();
					short var7 = var4.readShort();
					int var8 = var7 >> 4 & 4095;
					int var9 = var7 & 15;
					int var10 = var6 >> 12 & 15;
					int var11 = var6 >> 8 & 15;
					int var12 = var6 & 255;
					worldClient.setBlockAndMetadataAndInvalidate(var10 + var2, var12, var11 + var3, var8, var9);
				}
			} catch(IOException var13)
			{
				;
			}
		}
	}
	
	@Override public void handleNamedEntitySpawn(Packet20NamedEntitySpawn p_72518_1_)
	{
		double var2 = p_72518_1_.xPosition / 32.0D;
		double var4 = p_72518_1_.yPosition / 32.0D;
		double var6 = p_72518_1_.zPosition / 32.0D;
		float var8 = p_72518_1_.rotation * 360 / 256.0F;
		float var9 = p_72518_1_.pitch * 360 / 256.0F;
		EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(mc.theWorld, p_72518_1_.name);
		var10.prevPosX = var10.lastTickPosX = var10.serverPosX = p_72518_1_.xPosition;
		var10.prevPosY = var10.lastTickPosY = var10.serverPosY = p_72518_1_.yPosition;
		var10.prevPosZ = var10.lastTickPosZ = var10.serverPosZ = p_72518_1_.zPosition;
		int var11 = p_72518_1_.currentItem;
		if(var11 == 0)
		{
			var10.inventory.mainInventory[var10.inventory.currentItem] = null;
		} else
		{
			var10.inventory.mainInventory[var10.inventory.currentItem] = new ItemStack(var11, 1, 0);
		}
		var10.setPositionAndRotation(var2, var4, var6, var8, var9);
		worldClient.addEntityToWorld(p_72518_1_.entityId, var10);
		List var12 = p_72518_1_.getWatchedMetadata();
		if(var12 != null)
		{
			var10.getDataWatcher().updateWatchedObjectsFromList(var12);
		}
	}
	
	@Override public void handleOpenWindow(Packet100OpenWindow p_72516_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		switch(p_72516_1_.inventoryType)
		{
			case 0:
				var2.displayGUIChest(new InventoryBasic(p_72516_1_.windowTitle, p_72516_1_.useProvidedWindowTitle, p_72516_1_.slotsCount));
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 1:
				var2.displayGUIWorkbench(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 2:
				TileEntityFurnace var4 = new TileEntityFurnace();
				if(p_72516_1_.useProvidedWindowTitle)
				{
					var4.setGuiDisplayName(p_72516_1_.windowTitle);
				}
				var2.displayGUIFurnace(var4);
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 3:
				TileEntityDispenser var7 = new TileEntityDispenser();
				if(p_72516_1_.useProvidedWindowTitle)
				{
					var7.setCustomName(p_72516_1_.windowTitle);
				}
				var2.displayGUIDispenser(var7);
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 4:
				var2.displayGUIEnchantment(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ), p_72516_1_.useProvidedWindowTitle ? p_72516_1_.windowTitle : null);
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 5:
				TileEntityBrewingStand var5 = new TileEntityBrewingStand();
				if(p_72516_1_.useProvidedWindowTitle)
				{
					var5.func_94131_a(p_72516_1_.windowTitle);
				}
				var2.displayGUIBrewingStand(var5);
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 6:
				var2.displayGUIMerchant(new NpcMerchant(var2), p_72516_1_.useProvidedWindowTitle ? p_72516_1_.windowTitle : null);
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 7:
				TileEntityBeacon var8 = new TileEntityBeacon();
				var2.displayGUIBeacon(var8);
				if(p_72516_1_.useProvidedWindowTitle)
				{
					var8.func_94047_a(p_72516_1_.windowTitle);
				}
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 8:
				var2.displayGUIAnvil(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 9:
				TileEntityHopper var3 = new TileEntityHopper();
				if(p_72516_1_.useProvidedWindowTitle)
				{
					var3.setInventoryName(p_72516_1_.windowTitle);
				}
				var2.displayGUIHopper(var3);
				var2.openContainer.windowId = p_72516_1_.windowId;
				break;
			case 10:
				TileEntityDropper var6 = new TileEntityDropper();
				if(p_72516_1_.useProvidedWindowTitle)
				{
					var6.setCustomName(p_72516_1_.windowTitle);
				}
				var2.displayGUIDispenser(var6);
				var2.openContainer.windowId = p_72516_1_.windowId;
		}
	}
	
	@Override public void handlePlayerAbilities(Packet202PlayerAbilities p_72471_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		var2.capabilities.isFlying = p_72471_1_.getFlying();
		var2.capabilities.isCreativeMode = p_72471_1_.isCreativeMode();
		var2.capabilities.disableDamage = p_72471_1_.getDisableDamage();
		var2.capabilities.allowFlying = p_72471_1_.getAllowFlying();
		var2.capabilities.setFlySpeed(p_72471_1_.getFlySpeed());
		var2.capabilities.setPlayerWalkSpeed(p_72471_1_.getWalkSpeed());
	}
	
	@Override public void handlePlayerInfo(Packet201PlayerInfo p_72480_1_)
	{
		GuiPlayerInfo var2 = (GuiPlayerInfo) playerInfoMap.get(p_72480_1_.playerName);
		if(var2 == null && p_72480_1_.isConnected)
		{
			var2 = new GuiPlayerInfo(p_72480_1_.playerName);
			playerInfoMap.put(p_72480_1_.playerName, var2);
			playerInfoList.add(var2);
		}
		if(var2 != null && !p_72480_1_.isConnected)
		{
			playerInfoMap.remove(p_72480_1_.playerName);
			playerInfoList.remove(var2);
		}
		if(p_72480_1_.isConnected && var2 != null)
		{
			var2.responseTime = p_72480_1_.ping;
		}
	}
	
	@Override public void handlePlayerInventory(Packet5PlayerInventory p_72506_1_)
	{
		Entity var2 = getEntityByID(p_72506_1_.entityID);
		if(var2 != null)
		{
			var2.setCurrentItemOrArmor(p_72506_1_.slot, p_72506_1_.getItemSlot());
		}
	}
	
	@Override public void handleRemoveEntityEffect(Packet42RemoveEntityEffect p_72452_1_)
	{
		Entity var2 = getEntityByID(p_72452_1_.entityId);
		if(var2 instanceof EntityLiving)
		{
			((EntityLiving) var2).removePotionEffectClient(p_72452_1_.effectId);
		}
	}
	
	@Override public void handleRespawn(Packet9Respawn p_72483_1_)
	{
		if(p_72483_1_.respawnDimension != mc.thePlayer.dimension)
		{
			doneLoadingTerrain = false;
			Scoreboard var2 = worldClient.getScoreboard();
			worldClient = new WorldClient(this, new WorldSettings(0L, p_72483_1_.gameType, false, mc.theWorld.getWorldInfo().isHardcoreModeEnabled(), p_72483_1_.terrainType), p_72483_1_.respawnDimension, p_72483_1_.difficulty, mc.mcProfiler, mc.getLogAgent());
			worldClient.func_96443_a(var2);
			worldClient.isRemote = true;
			mc.loadWorld(worldClient);
			mc.thePlayer.dimension = p_72483_1_.respawnDimension;
			mc.displayGuiScreen(new GuiDownloadTerrain(this));
		}
		mc.setDimensionAndSpawnPlayer(p_72483_1_.respawnDimension);
		mc.playerController.setGameType(p_72483_1_.gameType);
	}
	
	@Override public void handleServerAuthData(Packet253ServerAuthData p_72470_1_)
	{
		String var2 = p_72470_1_.getServerId().trim();
		PublicKey var3 = p_72470_1_.getPublicKey();
		SecretKey var4 = CryptManager.createNewSharedKey();
		if(!"-".equals(var2))
		{
			String var5 = new BigInteger(CryptManager.getServerIdHash(var2, var3, var4)).toString(16);
			String var6 = sendSessionRequest(mc.session.username, mc.session.sessionId, var5);
			if(!"ok".equalsIgnoreCase(var6))
			{
				netManager.networkShutdown("disconnect.loginFailedInfo", new Object[] { var6 });
				return;
			}
		}
		addToSendQueue(new Packet252SharedKey(var4, var3, p_72470_1_.getVerifyToken()));
	}
	
	@Override public void handleSetDisplayObjective(Packet208SetDisplayObjective p_96438_1_)
	{
		Scoreboard var2 = worldClient.getScoreboard();
		if(p_96438_1_.scoreName.length() == 0)
		{
			var2.func_96530_a(p_96438_1_.scoreboardPosition, (ScoreObjective) null);
		} else
		{
			ScoreObjective var3 = var2.getObjective(p_96438_1_.scoreName);
			var2.func_96530_a(p_96438_1_.scoreboardPosition, var3);
		}
	}
	
	@Override public void handleSetObjective(Packet206SetObjective p_96436_1_)
	{
		Scoreboard var2 = worldClient.getScoreboard();
		ScoreObjective var3;
		if(p_96436_1_.change == 0)
		{
			var3 = var2.func_96535_a(p_96436_1_.objectiveName, ScoreObjectiveCriteria.field_96641_b);
			var3.setDisplayName(p_96436_1_.objectiveDisplayName);
		} else
		{
			var3 = var2.getObjective(p_96436_1_.objectiveName);
			if(p_96436_1_.change == 1)
			{
				var2.func_96519_k(var3);
			} else if(p_96436_1_.change == 2)
			{
				var3.setDisplayName(p_96436_1_.objectiveDisplayName);
			}
		}
	}
	
	@Override public void handleSetPlayerTeam(Packet209SetPlayerTeam p_96435_1_)
	{
		Scoreboard var2 = worldClient.getScoreboard();
		ScorePlayerTeam var3;
		if(p_96435_1_.mode == 0)
		{
			var3 = var2.func_96527_f(p_96435_1_.teamName);
		} else
		{
			var3 = var2.func_96508_e(p_96435_1_.teamName);
		}
		if(p_96435_1_.mode == 0 || p_96435_1_.mode == 2)
		{
			var3.func_96664_a(p_96435_1_.teamDisplayName);
			var3.func_96666_b(p_96435_1_.teamPrefix);
			var3.func_96662_c(p_96435_1_.teamSuffix);
			var3.func_98298_a(p_96435_1_.friendlyFire);
		}
		Iterator var4;
		String var5;
		if(p_96435_1_.mode == 0 || p_96435_1_.mode == 3)
		{
			var4 = p_96435_1_.playerNames.iterator();
			while(var4.hasNext())
			{
				var5 = (String) var4.next();
				var2.func_96521_a(var5, var3);
			}
		}
		if(p_96435_1_.mode == 4)
		{
			var4 = p_96435_1_.playerNames.iterator();
			while(var4.hasNext())
			{
				var5 = (String) var4.next();
				var2.removePlayerFromTeam(var5, var3);
			}
		}
		if(p_96435_1_.mode == 1)
		{
			var2.func_96511_d(var3);
		}
	}
	
	@Override public void handleSetScore(Packet207SetScore p_96437_1_)
	{
		Scoreboard var2 = worldClient.getScoreboard();
		ScoreObjective var3 = var2.getObjective(p_96437_1_.scoreName);
		if(p_96437_1_.updateOrRemove == 0)
		{
			Score var4 = var2.func_96529_a(p_96437_1_.itemName, var3);
			var4.func_96647_c(p_96437_1_.value);
		} else if(p_96437_1_.updateOrRemove == 1)
		{
			var2.func_96515_c(p_96437_1_.itemName);
		}
	}
	
	@Override public void handleSetSlot(Packet103SetSlot p_72490_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		if(p_72490_1_.windowId == -1)
		{
			var2.inventory.setItemStack(p_72490_1_.myItemStack);
		} else
		{
			boolean var3 = false;
			if(mc.currentScreen instanceof GuiContainerCreative)
			{
				GuiContainerCreative var4 = (GuiContainerCreative) mc.currentScreen;
				var3 = var4.getCurrentTabIndex() != CreativeTabs.tabInventory.getTabIndex();
			}
			if(p_72490_1_.windowId == 0 && p_72490_1_.itemSlot >= 36 && p_72490_1_.itemSlot < 45)
			{
				ItemStack var5 = var2.inventoryContainer.getSlot(p_72490_1_.itemSlot).getStack();
				if(p_72490_1_.myItemStack != null && (var5 == null || var5.stackSize < p_72490_1_.myItemStack.stackSize))
				{
					p_72490_1_.myItemStack.animationsToGo = 5;
				}
				var2.inventoryContainer.putStackInSlot(p_72490_1_.itemSlot, p_72490_1_.myItemStack);
			} else if(p_72490_1_.windowId == var2.openContainer.windowId && (p_72490_1_.windowId != 0 || !var3))
			{
				var2.openContainer.putStackInSlot(p_72490_1_.itemSlot, p_72490_1_.myItemStack);
			}
		}
	}
	
	@Override public void handleSharedKey(Packet252SharedKey p_72513_1_)
	{
		addToSendQueue(new Packet205ClientCommand(0));
	}
	
	@Override public void handleSleep(Packet17Sleep p_72460_1_)
	{
		Entity var2 = getEntityByID(p_72460_1_.entityID);
		if(var2 != null)
		{
			if(p_72460_1_.field_73622_e == 0)
			{
				EntityPlayer var3 = (EntityPlayer) var2;
				var3.sleepInBedAt(p_72460_1_.bedX, p_72460_1_.bedY, p_72460_1_.bedZ);
			}
		}
	}
	
	@Override public void handleSpawnPosition(Packet6SpawnPosition p_72466_1_)
	{
		mc.thePlayer.setSpawnChunk(new ChunkCoordinates(p_72466_1_.xPosition, p_72466_1_.yPosition, p_72466_1_.zPosition), true);
		mc.theWorld.getWorldInfo().setSpawnPosition(p_72466_1_.xPosition, p_72466_1_.yPosition, p_72466_1_.zPosition);
	}
	
	@Override public void handleStatistic(Packet200Statistic p_72517_1_)
	{
		mc.thePlayer.incrementStat(StatList.getOneShotStat(p_72517_1_.statisticId), p_72517_1_.amount);
	}
	
	@Override public void handleTileEntityData(Packet132TileEntityData p_72468_1_)
	{
		if(mc.theWorld.blockExists(p_72468_1_.xPosition, p_72468_1_.yPosition, p_72468_1_.zPosition))
		{
			TileEntity var2 = mc.theWorld.getBlockTileEntity(p_72468_1_.xPosition, p_72468_1_.yPosition, p_72468_1_.zPosition);
			if(var2 != null)
			{
				if(p_72468_1_.actionType == 1 && var2 instanceof TileEntityMobSpawner)
				{
					var2.readFromNBT(p_72468_1_.customParam1);
				} else if(p_72468_1_.actionType == 2 && var2 instanceof TileEntityCommandBlock)
				{
					var2.readFromNBT(p_72468_1_.customParam1);
				} else if(p_72468_1_.actionType == 3 && var2 instanceof TileEntityBeacon)
				{
					var2.readFromNBT(p_72468_1_.customParam1);
				} else if(p_72468_1_.actionType == 4 && var2 instanceof TileEntitySkull)
				{
					var2.readFromNBT(p_72468_1_.customParam1);
				}
			}
		}
	}
	
	@Override public void handleTransaction(Packet106Transaction p_72476_1_)
	{
		Container var2 = null;
		EntityClientPlayerMP var3 = mc.thePlayer;
		if(p_72476_1_.windowId == 0)
		{
			var2 = var3.inventoryContainer;
		} else if(p_72476_1_.windowId == var3.openContainer.windowId)
		{
			var2 = var3.openContainer;
		}
		if(var2 != null && !p_72476_1_.accepted)
		{
			addToSendQueue(new Packet106Transaction(p_72476_1_.windowId, p_72476_1_.shortWindowId, true));
		}
	}
	
	@Override public void handleUpdateHealth(Packet8UpdateHealth p_72521_1_)
	{
		mc.thePlayer.setHealth(p_72521_1_.healthMP);
		mc.thePlayer.getFoodStats().setFoodLevel(p_72521_1_.food);
		mc.thePlayer.getFoodStats().setFoodSaturationLevel(p_72521_1_.foodSaturation);
	}
	
	@Override public void handleUpdateProgressbar(Packet105UpdateProgressbar p_72505_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		unexpectedPacket(p_72505_1_);
		if(var2.openContainer != null && var2.openContainer.windowId == p_72505_1_.windowId)
		{
			var2.openContainer.updateProgressBar(p_72505_1_.progressBar, p_72505_1_.progressBarValue);
		}
	}
	
	@Override public void handleUpdateSign(Packet130UpdateSign p_72487_1_)
	{
		boolean var2 = false;
		if(mc.theWorld.blockExists(p_72487_1_.xPosition, p_72487_1_.yPosition, p_72487_1_.zPosition))
		{
			TileEntity var3 = mc.theWorld.getBlockTileEntity(p_72487_1_.xPosition, p_72487_1_.yPosition, p_72487_1_.zPosition);
			if(var3 instanceof TileEntitySign)
			{
				TileEntitySign var4 = (TileEntitySign) var3;
				if(var4.isEditable())
				{
					for(int var5 = 0; var5 < 4; ++var5)
					{
						var4.signText[var5] = p_72487_1_.signLines[var5];
					}
					var4.onInventoryChanged();
				}
				var2 = true;
			}
		}
		if(!var2 && mc.thePlayer != null)
		{
			mc.thePlayer.sendChatToPlayer("Unable to locate sign at " + p_72487_1_.xPosition + ", " + p_72487_1_.yPosition + ", " + p_72487_1_.zPosition);
		}
	}
	
	@Override public void handleUpdateTime(Packet4UpdateTime p_72497_1_)
	{
		mc.theWorld.func_82738_a(p_72497_1_.worldAge);
		mc.theWorld.setWorldTime(p_72497_1_.time);
	}
	
	@Override public void handleVehicleSpawn(Packet23VehicleSpawn p_72511_1_)
	{
		double var2 = p_72511_1_.xPosition / 32.0D;
		double var4 = p_72511_1_.yPosition / 32.0D;
		double var6 = p_72511_1_.zPosition / 32.0D;
		Object var8 = null;
		if(p_72511_1_.type == 10)
		{
			var8 = EntityMinecart.createMinecart(worldClient, var2, var4, var6, p_72511_1_.throwerEntityId);
		} else if(p_72511_1_.type == 90)
		{
			Entity var9 = getEntityByID(p_72511_1_.throwerEntityId);
			if(var9 instanceof EntityPlayer)
			{
				var8 = new EntityFishHook(worldClient, var2, var4, var6, (EntityPlayer) var9);
			}
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 60)
		{
			var8 = new EntityArrow(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 61)
		{
			var8 = new EntitySnowball(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 71)
		{
			var8 = new EntityItemFrame(worldClient, (int) var2, (int) var4, (int) var6, p_72511_1_.throwerEntityId);
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 65)
		{
			var8 = new EntityEnderPearl(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 72)
		{
			var8 = new EntityEnderEye(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 76)
		{
			var8 = new EntityFireworkRocket(worldClient, var2, var4, var6, (ItemStack) null);
		} else if(p_72511_1_.type == 63)
		{
			var8 = new EntityLargeFireball(worldClient, var2, var4, var6, p_72511_1_.speedX / 8000.0D, p_72511_1_.speedY / 8000.0D, p_72511_1_.speedZ / 8000.0D);
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 64)
		{
			var8 = new EntitySmallFireball(worldClient, var2, var4, var6, p_72511_1_.speedX / 8000.0D, p_72511_1_.speedY / 8000.0D, p_72511_1_.speedZ / 8000.0D);
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 66)
		{
			var8 = new EntityWitherSkull(worldClient, var2, var4, var6, p_72511_1_.speedX / 8000.0D, p_72511_1_.speedY / 8000.0D, p_72511_1_.speedZ / 8000.0D);
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 62)
		{
			var8 = new EntityEgg(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 73)
		{
			var8 = new EntityPotion(worldClient, var2, var4, var6, p_72511_1_.throwerEntityId);
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 75)
		{
			var8 = new EntityExpBottle(worldClient, var2, var4, var6);
			p_72511_1_.throwerEntityId = 0;
		} else if(p_72511_1_.type == 1)
		{
			var8 = new EntityBoat(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 50)
		{
			var8 = new EntityTNTPrimed(worldClient, var2, var4, var6, (EntityLiving) null);
		} else if(p_72511_1_.type == 51)
		{
			var8 = new EntityEnderCrystal(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 2)
		{
			var8 = new EntityItem(worldClient, var2, var4, var6);
		} else if(p_72511_1_.type == 70)
		{
			var8 = new EntityFallingSand(worldClient, var2, var4, var6, p_72511_1_.throwerEntityId & 65535, p_72511_1_.throwerEntityId >> 16);
			p_72511_1_.throwerEntityId = 0;
		}
		if(var8 != null)
		{
			((Entity) var8).serverPosX = p_72511_1_.xPosition;
			((Entity) var8).serverPosY = p_72511_1_.yPosition;
			((Entity) var8).serverPosZ = p_72511_1_.zPosition;
			((Entity) var8).rotationPitch = p_72511_1_.pitch * 360 / 256.0F;
			((Entity) var8).rotationYaw = p_72511_1_.yaw * 360 / 256.0F;
			Entity[] var12 = ((Entity) var8).getParts();
			if(var12 != null)
			{
				int var10 = p_72511_1_.entityId - ((Entity) var8).entityId;
				for(int var11 = 0; var11 < var12.length; ++var11)
				{
					var12[var11].entityId += var10;
				}
			}
			((Entity) var8).entityId = p_72511_1_.entityId;
			worldClient.addEntityToWorld(p_72511_1_.entityId, (Entity) var8);
			if(p_72511_1_.throwerEntityId > 0)
			{
				if(p_72511_1_.type == 60)
				{
					Entity var13 = getEntityByID(p_72511_1_.throwerEntityId);
					if(var13 instanceof EntityLiving)
					{
						EntityArrow var14 = (EntityArrow) var8;
						var14.shootingEntity = var13;
					}
				}
				((Entity) var8).setVelocity(p_72511_1_.speedX / 8000.0D, p_72511_1_.speedY / 8000.0D, p_72511_1_.speedZ / 8000.0D);
			}
		}
	}
	
	@Override public void handleWeather(Packet71Weather p_72508_1_)
	{
		double var2 = p_72508_1_.posX / 32.0D;
		double var4 = p_72508_1_.posY / 32.0D;
		double var6 = p_72508_1_.posZ / 32.0D;
		EntityLightningBolt var8 = null;
		if(p_72508_1_.isLightningBolt == 1)
		{
			var8 = new EntityLightningBolt(worldClient, var2, var4, var6);
		}
		if(var8 != null)
		{
			var8.serverPosX = p_72508_1_.posX;
			var8.serverPosY = p_72508_1_.posY;
			var8.serverPosZ = p_72508_1_.posZ;
			var8.rotationYaw = 0.0F;
			var8.rotationPitch = 0.0F;
			var8.entityId = p_72508_1_.entityID;
			worldClient.addWeatherEffect(var8);
		}
	}
	
	@Override public void handleWindowItems(Packet104WindowItems p_72486_1_)
	{
		EntityClientPlayerMP var2 = mc.thePlayer;
		if(p_72486_1_.windowId == 0)
		{
			var2.inventoryContainer.putStacksInSlots(p_72486_1_.itemStack);
		} else if(p_72486_1_.windowId == var2.openContainer.windowId)
		{
			var2.openContainer.putStacksInSlots(p_72486_1_.itemStack);
		}
	}
	
	@Override public void handleWorldParticles(Packet63WorldParticles p_98182_1_)
	{
		for(int var2 = 0; var2 < p_98182_1_.getQuantity(); ++var2)
		{
			double var3 = rand.nextGaussian() * p_98182_1_.getOffsetX();
			double var5 = rand.nextGaussian() * p_98182_1_.getOffsetY();
			double var7 = rand.nextGaussian() * p_98182_1_.getOffsetZ();
			double var9 = rand.nextGaussian() * p_98182_1_.getSpeed();
			double var11 = rand.nextGaussian() * p_98182_1_.getSpeed();
			double var13 = rand.nextGaussian() * p_98182_1_.getSpeed();
			worldClient.spawnParticle(p_98182_1_.getParticleName(), p_98182_1_.getPositionX() + var3, p_98182_1_.getPositionY() + var5, p_98182_1_.getPositionZ() + var7, var9, var11, var13);
		}
	}
	
	@Override public boolean isServerHandler()
	{
		return false;
	}
	
	public void processReadPackets()
	{
		if(!disconnected && netManager != null)
		{
			netManager.processReadPackets();
		}
		if(netManager != null)
		{
			netManager.wakeThreads();
		}
	}
	
	public void quitWithPacket(Packet par1Packet)
	{
		if(!disconnected)
		{
			netManager.addToSendQueue(par1Packet);
			netManager.serverShutdown();
		}
	}
	
	private String sendSessionRequest(String par1Str, String par2Str, String par3Str)
	{
		try
		{
			URL var4 = new URL("http://session.minecraft.net/game/joinserver.jsp?user=" + urlEncode(par1Str) + "&sessionId=" + urlEncode(par2Str) + "&serverId=" + urlEncode(par3Str));
			BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.openStream()));
			String var6 = var5.readLine();
			var5.close();
			return var6;
		} catch(IOException var7)
		{
			return var7.toString();
		}
	}
	
	private static String urlEncode(String par0Str) throws IOException
	{
		return URLEncoder.encode(par0Str, "UTF-8");
	}
}
