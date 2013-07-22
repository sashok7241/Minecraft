package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.server.MinecraftServer;

public class NetServerHandler extends NetHandler
{
	public final INetworkManager netManager;
	private final MinecraftServer mcServer;
	public boolean connectionClosed = false;
	public EntityPlayerMP playerEntity;
	private int currentTicks;
	private int ticksForFloatKick;
	private boolean field_72584_h;
	private int keepAliveRandomID;
	private long keepAliveTimeSent;
	private static Random randomGenerator = new Random();
	private long ticksOfLastKeepAlive;
	private int chatSpamThresholdCount = 0;
	private int creativeItemCreationSpamThresholdTally = 0;
	private double lastPosX;
	private double lastPosY;
	private double lastPosZ;
	private boolean hasMoved = true;
	private IntHashMap field_72586_s = new IntHashMap();
	
	public NetServerHandler(MinecraftServer p_i5010_1_, INetworkManager p_i5010_2_, EntityPlayerMP p_i5010_3_)
	{
		mcServer = p_i5010_1_;
		netManager = p_i5010_2_;
		p_i5010_2_.setNetHandler(this);
		playerEntity = p_i5010_3_;
		p_i5010_3_.playerNetServerHandler = this;
	}
	
	@Override public boolean canProcessPacketsAsync()
	{
		return true;
	}
	
	@Override public void handleAnimation(Packet18Animation p_72524_1_)
	{
		if(p_72524_1_.animate == 1)
		{
			playerEntity.swingItem();
		}
	}
	
	@Override public void handleAutoComplete(Packet203AutoComplete p_72461_1_)
	{
		StringBuilder var2 = new StringBuilder();
		String var4;
		for(Iterator var3 = mcServer.getPossibleCompletions(playerEntity, p_72461_1_.getText()).iterator(); var3.hasNext(); var2.append(var4))
		{
			var4 = (String) var3.next();
			if(var2.length() > 0)
			{
				var2.append("\u0000");
			}
		}
		playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet203AutoComplete(var2.toString()));
	}
	
	@Override public void handleBlockDig(Packet14BlockDig p_72510_1_)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		if(p_72510_1_.status == 4)
		{
			playerEntity.dropOneItem(false);
		} else if(p_72510_1_.status == 3)
		{
			playerEntity.dropOneItem(true);
		} else if(p_72510_1_.status == 5)
		{
			playerEntity.stopUsingItem();
		} else
		{
			boolean var3 = false;
			if(p_72510_1_.status == 0)
			{
				var3 = true;
			}
			if(p_72510_1_.status == 1)
			{
				var3 = true;
			}
			if(p_72510_1_.status == 2)
			{
				var3 = true;
			}
			int var4 = p_72510_1_.xPosition;
			int var5 = p_72510_1_.yPosition;
			int var6 = p_72510_1_.zPosition;
			if(var3)
			{
				double var7 = playerEntity.posX - (var4 + 0.5D);
				double var9 = playerEntity.posY - (var5 + 0.5D) + 1.5D;
				double var11 = playerEntity.posZ - (var6 + 0.5D);
				double var13 = var7 * var7 + var9 * var9 + var11 * var11;
				if(var13 > 36.0D) return;
				if(var5 >= mcServer.getBuildLimit()) return;
			}
			if(p_72510_1_.status == 0)
			{
				if(!mcServer.func_96290_a(var2, var4, var5, var6, playerEntity))
				{
					playerEntity.theItemInWorldManager.onBlockClicked(var4, var5, var6, p_72510_1_.face);
				} else
				{
					playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
				}
			} else if(p_72510_1_.status == 2)
			{
				playerEntity.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);
				if(var2.getBlockId(var4, var5, var6) != 0)
				{
					playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
				}
			} else if(p_72510_1_.status == 1)
			{
				playerEntity.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);
				if(var2.getBlockId(var4, var5, var6) != 0)
				{
					playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
				}
			}
		}
	}
	
	@Override public void handleBlockItemSwitch(Packet16BlockItemSwitch p_72502_1_)
	{
		if(p_72502_1_.id >= 0 && p_72502_1_.id < InventoryPlayer.getHotbarSize())
		{
			playerEntity.inventory.currentItem = p_72502_1_.id;
		} else
		{
			mcServer.getLogAgent().logWarning(playerEntity.username + " tried to set an invalid carried item");
		}
	}
	
	@Override public void handleChat(Packet3Chat p_72481_1_)
	{
		if(playerEntity.getChatVisibility() == 2)
		{
			sendPacketToPlayer(new Packet3Chat("Cannot send chat message."));
		} else
		{
			String var2 = p_72481_1_.message;
			if(var2.length() > 100)
			{
				kickPlayerFromServer("Chat message too long");
			} else
			{
				var2 = var2.trim();
				for(int var3 = 0; var3 < var2.length(); ++var3)
				{
					if(!ChatAllowedCharacters.isAllowedCharacter(var2.charAt(var3)))
					{
						kickPlayerFromServer("Illegal characters in chat");
						return;
					}
				}
				if(var2.startsWith("/"))
				{
					handleSlashCommand(var2);
				} else
				{
					if(playerEntity.getChatVisibility() == 1)
					{
						sendPacketToPlayer(new Packet3Chat("Cannot send chat message."));
						return;
					}
					var2 = "<" + playerEntity.getTranslatedEntityName() + "> " + var2;
					mcServer.getLogAgent().logInfo(var2);
					mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(var2, false));
				}
				chatSpamThresholdCount += 20;
				if(chatSpamThresholdCount > 200 && !mcServer.getConfigurationManager().areCommandsAllowed(playerEntity.username))
				{
					kickPlayerFromServer("disconnect.spam");
				}
			}
		}
	}
	
	@Override public void handleClientCommand(Packet205ClientCommand p_72458_1_)
	{
		if(p_72458_1_.forceRespawn == 1)
		{
			if(playerEntity.playerConqueredTheEnd)
			{
				playerEntity = mcServer.getConfigurationManager().respawnPlayer(playerEntity, 0, true);
			} else if(playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled())
			{
				if(mcServer.isSinglePlayer() && playerEntity.username.equals(mcServer.getServerOwner()))
				{
					playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
					mcServer.deleteWorldAndStopServer();
				} else
				{
					BanEntry var2 = new BanEntry(playerEntity.username);
					var2.setBanReason("Death in Hardcore");
					mcServer.getConfigurationManager().getBannedPlayers().put(var2);
					playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
				}
			} else
			{
				if(playerEntity.getHealth() > 0) return;
				playerEntity = mcServer.getConfigurationManager().respawnPlayer(playerEntity, 0, false);
			}
		}
	}
	
	@Override public void handleClientInfo(Packet204ClientInfo p_72504_1_)
	{
		playerEntity.updateClientInfo(p_72504_1_);
	}
	
	@Override public void handleCloseWindow(Packet101CloseWindow p_72474_1_)
	{
		playerEntity.closeContainer();
	}
	
	@Override public void handleCreativeSetSlot(Packet107CreativeSetSlot p_72464_1_)
	{
		if(playerEntity.theItemInWorldManager.isCreative())
		{
			boolean var2 = p_72464_1_.slot < 0;
			ItemStack var3 = p_72464_1_.itemStack;
			boolean var4 = p_72464_1_.slot >= 1 && p_72464_1_.slot < 36 + InventoryPlayer.getHotbarSize();
			boolean var5 = var3 == null || var3.itemID < Item.itemsList.length && var3.itemID >= 0 && Item.itemsList[var3.itemID] != null;
			boolean var6 = var3 == null || var3.getItemDamage() >= 0 && var3.getItemDamage() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;
			if(var4 && var5 && var6)
			{
				if(var3 == null)
				{
					playerEntity.inventoryContainer.putStackInSlot(p_72464_1_.slot, (ItemStack) null);
				} else
				{
					playerEntity.inventoryContainer.putStackInSlot(p_72464_1_.slot, var3);
				}
				playerEntity.inventoryContainer.setPlayerIsPresent(playerEntity, true);
			} else if(var2 && var5 && var6 && creativeItemCreationSpamThresholdTally < 200)
			{
				creativeItemCreationSpamThresholdTally += 20;
				EntityItem var7 = playerEntity.dropPlayerItem(var3);
				if(var7 != null)
				{
					var7.setAgeToCreativeDespawnTime();
				}
			}
		}
	}
	
	@Override public void handleCustomPayload(Packet250CustomPayload p_72501_1_)
	{
		DataInputStream var2;
		ItemStack var3;
		ItemStack var4;
		if("MC|BEdit".equals(p_72501_1_.channel))
		{
			try
			{
				var2 = new DataInputStream(new ByteArrayInputStream(p_72501_1_.data));
				var3 = Packet.readItemStack(var2);
				if(!ItemWritableBook.validBookTagPages(var3.getTagCompound())) throw new IOException("Invalid book tag!");
				var4 = playerEntity.inventory.getCurrentItem();
				if(var3 != null && var3.itemID == Item.writableBook.itemID && var3.itemID == var4.itemID)
				{
					var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages"));
				}
			} catch(Exception var12)
			{
				var12.printStackTrace();
			}
		} else if("MC|BSign".equals(p_72501_1_.channel))
		{
			try
			{
				var2 = new DataInputStream(new ByteArrayInputStream(p_72501_1_.data));
				var3 = Packet.readItemStack(var2);
				if(!ItemEditableBook.validBookTagContents(var3.getTagCompound())) throw new IOException("Invalid book tag!");
				var4 = playerEntity.inventory.getCurrentItem();
				if(var3 != null && var3.itemID == Item.writtenBook.itemID && var4.itemID == Item.writableBook.itemID)
				{
					var4.setTagInfo("author", new NBTTagString("author", playerEntity.username));
					var4.setTagInfo("title", new NBTTagString("title", var3.getTagCompound().getString("title")));
					var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages"));
					var4.itemID = Item.writtenBook.itemID;
				}
			} catch(Exception var11)
			{
				var11.printStackTrace();
			}
		} else
		{
			int var14;
			if("MC|TrSel".equals(p_72501_1_.channel))
			{
				try
				{
					var2 = new DataInputStream(new ByteArrayInputStream(p_72501_1_.data));
					var14 = var2.readInt();
					Container var15 = playerEntity.openContainer;
					if(var15 instanceof ContainerMerchant)
					{
						((ContainerMerchant) var15).setCurrentRecipeIndex(var14);
					}
				} catch(Exception var10)
				{
					var10.printStackTrace();
				}
			} else
			{
				int var18;
				if("MC|AdvCdm".equals(p_72501_1_.channel))
				{
					if(!mcServer.isCommandBlockEnabled())
					{
						playerEntity.sendChatToPlayer(playerEntity.translateString("advMode.notEnabled", new Object[0]));
					} else if(playerEntity.canCommandSenderUseCommand(2, "") && playerEntity.capabilities.isCreativeMode)
					{
						try
						{
							var2 = new DataInputStream(new ByteArrayInputStream(p_72501_1_.data));
							var14 = var2.readInt();
							var18 = var2.readInt();
							int var5 = var2.readInt();
							String var6 = Packet.readString(var2, 256);
							TileEntity var7 = playerEntity.worldObj.getBlockTileEntity(var14, var18, var5);
							if(var7 != null && var7 instanceof TileEntityCommandBlock)
							{
								((TileEntityCommandBlock) var7).setCommand(var6);
								playerEntity.worldObj.markBlockForUpdate(var14, var18, var5);
								playerEntity.sendChatToPlayer("Command set: " + var6);
							}
						} catch(Exception var9)
						{
							var9.printStackTrace();
						}
					} else
					{
						playerEntity.sendChatToPlayer(playerEntity.translateString("advMode.notAllowed", new Object[0]));
					}
				} else if("MC|Beacon".equals(p_72501_1_.channel))
				{
					if(playerEntity.openContainer instanceof ContainerBeacon)
					{
						try
						{
							var2 = new DataInputStream(new ByteArrayInputStream(p_72501_1_.data));
							var14 = var2.readInt();
							var18 = var2.readInt();
							ContainerBeacon var17 = (ContainerBeacon) playerEntity.openContainer;
							Slot var19 = var17.getSlot(0);
							if(var19.getHasStack())
							{
								var19.decrStackSize(1);
								TileEntityBeacon var20 = var17.getBeacon();
								var20.setPrimaryEffect(var14);
								var20.setSecondaryEffect(var18);
								var20.onInventoryChanged();
							}
						} catch(Exception var8)
						{
							var8.printStackTrace();
						}
					}
				} else if("MC|ItemName".equals(p_72501_1_.channel) && playerEntity.openContainer instanceof ContainerRepair)
				{
					ContainerRepair var13 = (ContainerRepair) playerEntity.openContainer;
					if(p_72501_1_.data != null && p_72501_1_.data.length >= 1)
					{
						String var16 = ChatAllowedCharacters.filerAllowedCharacters(new String(p_72501_1_.data));
						if(var16.length() <= 30)
						{
							var13.updateItemName(var16);
						}
					} else
					{
						var13.updateItemName("");
					}
				}
			}
		}
	}
	
	@Override public void handleEnchantItem(Packet108EnchantItem p_72479_1_)
	{
		if(playerEntity.openContainer.windowId == p_72479_1_.windowId && playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity))
		{
			playerEntity.openContainer.enchantItem(playerEntity, p_72479_1_.enchantment);
			playerEntity.openContainer.detectAndSendChanges();
		}
	}
	
	@Override public void handleEntityAction(Packet19EntityAction p_72473_1_)
	{
		if(p_72473_1_.state == 1)
		{
			playerEntity.setSneaking(true);
		} else if(p_72473_1_.state == 2)
		{
			playerEntity.setSneaking(false);
		} else if(p_72473_1_.state == 4)
		{
			playerEntity.setSprinting(true);
		} else if(p_72473_1_.state == 5)
		{
			playerEntity.setSprinting(false);
		} else if(p_72473_1_.state == 3)
		{
			playerEntity.wakeUpPlayer(false, true, true);
			hasMoved = false;
		}
	}
	
	@Override public void handleErrorMessage(String p_72515_1_, Object[] p_72515_2_)
	{
		mcServer.getLogAgent().logInfo(playerEntity.username + " lost connection: " + p_72515_1_);
		mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(EnumChatFormatting.YELLOW + playerEntity.getTranslatedEntityName() + " left the game."));
		mcServer.getConfigurationManager().playerLoggedOut(playerEntity);
		connectionClosed = true;
		if(mcServer.isSinglePlayer() && playerEntity.username.equals(mcServer.getServerOwner()))
		{
			mcServer.getLogAgent().logInfo("Stopping singleplayer server as player logged out");
			mcServer.initiateShutdown();
		}
	}
	
	@Override public void handleFlying(Packet10Flying p_72498_1_)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		field_72584_h = true;
		if(!playerEntity.playerConqueredTheEnd)
		{
			double var3;
			if(!hasMoved)
			{
				var3 = p_72498_1_.yPosition - lastPosY;
				if(p_72498_1_.xPosition == lastPosX && var3 * var3 < 0.01D && p_72498_1_.zPosition == lastPosZ)
				{
					hasMoved = true;
				}
			}
			if(hasMoved)
			{
				double var5;
				double var7;
				double var9;
				double var13;
				if(playerEntity.ridingEntity != null)
				{
					float var34 = playerEntity.rotationYaw;
					float var4 = playerEntity.rotationPitch;
					playerEntity.ridingEntity.updateRiderPosition();
					var5 = playerEntity.posX;
					var7 = playerEntity.posY;
					var9 = playerEntity.posZ;
					double var35 = 0.0D;
					var13 = 0.0D;
					if(p_72498_1_.rotating)
					{
						var34 = p_72498_1_.yaw;
						var4 = p_72498_1_.pitch;
					}
					if(p_72498_1_.moving && p_72498_1_.yPosition == -999.0D && p_72498_1_.stance == -999.0D)
					{
						if(Math.abs(p_72498_1_.xPosition) > 1.0D || Math.abs(p_72498_1_.zPosition) > 1.0D)
						{
							System.err.println(playerEntity.username + " was caught trying to crash the server with an invalid position.");
							kickPlayerFromServer("Nope!");
							return;
						}
						var35 = p_72498_1_.xPosition;
						var13 = p_72498_1_.zPosition;
					}
					playerEntity.onGround = p_72498_1_.onGround;
					playerEntity.onUpdateEntity();
					playerEntity.moveEntity(var35, 0.0D, var13);
					playerEntity.setPositionAndRotation(var5, var7, var9, var34, var4);
					playerEntity.motionX = var35;
					playerEntity.motionZ = var13;
					if(playerEntity.ridingEntity != null)
					{
						var2.uncheckedUpdateEntity(playerEntity.ridingEntity, true);
					}
					if(playerEntity.ridingEntity != null)
					{
						playerEntity.ridingEntity.updateRiderPosition();
					}
					mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(playerEntity);
					lastPosX = playerEntity.posX;
					lastPosY = playerEntity.posY;
					lastPosZ = playerEntity.posZ;
					var2.updateEntity(playerEntity);
					return;
				}
				if(playerEntity.isPlayerSleeping())
				{
					playerEntity.onUpdateEntity();
					playerEntity.setPositionAndRotation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw, playerEntity.rotationPitch);
					var2.updateEntity(playerEntity);
					return;
				}
				var3 = playerEntity.posY;
				lastPosX = playerEntity.posX;
				lastPosY = playerEntity.posY;
				lastPosZ = playerEntity.posZ;
				var5 = playerEntity.posX;
				var7 = playerEntity.posY;
				var9 = playerEntity.posZ;
				float var11 = playerEntity.rotationYaw;
				float var12 = playerEntity.rotationPitch;
				if(p_72498_1_.moving && p_72498_1_.yPosition == -999.0D && p_72498_1_.stance == -999.0D)
				{
					p_72498_1_.moving = false;
				}
				if(p_72498_1_.moving)
				{
					var5 = p_72498_1_.xPosition;
					var7 = p_72498_1_.yPosition;
					var9 = p_72498_1_.zPosition;
					var13 = p_72498_1_.stance - p_72498_1_.yPosition;
					if(!playerEntity.isPlayerSleeping() && (var13 > 1.65D || var13 < 0.1D))
					{
						kickPlayerFromServer("Illegal stance");
						mcServer.getLogAgent().logWarning(playerEntity.username + " had an illegal stance: " + var13);
						return;
					}
					if(Math.abs(p_72498_1_.xPosition) > 3.2E7D || Math.abs(p_72498_1_.zPosition) > 3.2E7D)
					{
						kickPlayerFromServer("Illegal position");
						return;
					}
				}
				if(p_72498_1_.rotating)
				{
					var11 = p_72498_1_.yaw;
					var12 = p_72498_1_.pitch;
				}
				playerEntity.onUpdateEntity();
				playerEntity.ySize = 0.0F;
				playerEntity.setPositionAndRotation(lastPosX, lastPosY, lastPosZ, var11, var12);
				if(!hasMoved) return;
				var13 = var5 - playerEntity.posX;
				double var15 = var7 - playerEntity.posY;
				double var17 = var9 - playerEntity.posZ;
				double var19 = Math.min(Math.abs(var13), Math.abs(playerEntity.motionX));
				double var21 = Math.min(Math.abs(var15), Math.abs(playerEntity.motionY));
				double var23 = Math.min(Math.abs(var17), Math.abs(playerEntity.motionZ));
				double var25 = var19 * var19 + var21 * var21 + var23 * var23;
				if(var25 > 100.0D && (!mcServer.isSinglePlayer() || !mcServer.getServerOwner().equals(playerEntity.username)))
				{
					mcServer.getLogAgent().logWarning(playerEntity.username + " moved too quickly! " + var13 + "," + var15 + "," + var17 + " (" + var19 + ", " + var21 + ", " + var23 + ")");
					setPlayerLocation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw, playerEntity.rotationPitch);
					return;
				}
				float var27 = 0.0625F;
				boolean var28 = var2.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
				if(playerEntity.onGround && !p_72498_1_.onGround && var15 > 0.0D)
				{
					playerEntity.addExhaustion(0.2F);
				}
				playerEntity.moveEntity(var13, var15, var17);
				playerEntity.onGround = p_72498_1_.onGround;
				playerEntity.addMovementStat(var13, var15, var17);
				double var29 = var15;
				var13 = var5 - playerEntity.posX;
				var15 = var7 - playerEntity.posY;
				if(var15 > -0.5D || var15 < 0.5D)
				{
					var15 = 0.0D;
				}
				var17 = var9 - playerEntity.posZ;
				var25 = var13 * var13 + var15 * var15 + var17 * var17;
				boolean var31 = false;
				if(var25 > 0.0625D && !playerEntity.isPlayerSleeping() && !playerEntity.theItemInWorldManager.isCreative())
				{
					var31 = true;
					mcServer.getLogAgent().logWarning(playerEntity.username + " moved wrongly!");
				}
				playerEntity.setPositionAndRotation(var5, var7, var9, var11, var12);
				boolean var32 = var2.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
				if(var28 && (var31 || !var32) && !playerEntity.isPlayerSleeping())
				{
					setPlayerLocation(lastPosX, lastPosY, lastPosZ, var11, var12);
					return;
				}
				AxisAlignedBB var33 = playerEntity.boundingBox.copy().expand(var27, var27, var27).addCoord(0.0D, -0.55D, 0.0D);
				if(!mcServer.isFlightAllowed() && !playerEntity.theItemInWorldManager.isCreative() && !var2.checkBlockCollision(var33))
				{
					if(var29 >= -0.03125D)
					{
						++ticksForFloatKick;
						if(ticksForFloatKick > 80)
						{
							mcServer.getLogAgent().logWarning(playerEntity.username + " was kicked for floating too long!");
							kickPlayerFromServer("Flying is not enabled on this server");
							return;
						}
					}
				} else
				{
					ticksForFloatKick = 0;
				}
				playerEntity.onGround = p_72498_1_.onGround;
				mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(playerEntity);
				playerEntity.updateFlyingState(playerEntity.posY - var3, p_72498_1_.onGround);
			}
		}
	}
	
	@Override public void handleKeepAlive(Packet0KeepAlive p_72477_1_)
	{
		if(p_72477_1_.randomId == keepAliveRandomID)
		{
			int var2 = (int) (System.nanoTime() / 1000000L - keepAliveTimeSent);
			playerEntity.ping = (playerEntity.ping * 3 + var2) / 4;
		}
	}
	
	@Override public void handleKickDisconnect(Packet255KickDisconnect p_72492_1_)
	{
		netManager.networkShutdown("disconnect.quitting", new Object[0]);
	}
	
	@Override public void handlePlace(Packet15Place p_72472_1_)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		ItemStack var3 = playerEntity.inventory.getCurrentItem();
		boolean var4 = false;
		int var5 = p_72472_1_.getXPosition();
		int var6 = p_72472_1_.getYPosition();
		int var7 = p_72472_1_.getZPosition();
		int var8 = p_72472_1_.getDirection();
		if(p_72472_1_.getDirection() == 255)
		{
			if(var3 == null) return;
			playerEntity.theItemInWorldManager.tryUseItem(playerEntity, var2, var3);
		} else if(p_72472_1_.getYPosition() >= mcServer.getBuildLimit() - 1 && (p_72472_1_.getDirection() == 1 || p_72472_1_.getYPosition() >= mcServer.getBuildLimit()))
		{
			playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat("" + EnumChatFormatting.GRAY + "Height limit for building is " + mcServer.getBuildLimit()));
			var4 = true;
		} else
		{
			if(hasMoved && playerEntity.getDistanceSq(var5 + 0.5D, var6 + 0.5D, var7 + 0.5D) < 64.0D && !mcServer.func_96290_a(var2, var5, var6, var7, playerEntity))
			{
				playerEntity.theItemInWorldManager.activateBlockOrUseItem(playerEntity, var2, var3, var5, var6, var7, var8, p_72472_1_.getXOffset(), p_72472_1_.getYOffset(), p_72472_1_.getZOffset());
			}
			var4 = true;
		}
		if(var4)
		{
			playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var5, var6, var7, var2));
			if(var8 == 0)
			{
				--var6;
			}
			if(var8 == 1)
			{
				++var6;
			}
			if(var8 == 2)
			{
				--var7;
			}
			if(var8 == 3)
			{
				++var7;
			}
			if(var8 == 4)
			{
				--var5;
			}
			if(var8 == 5)
			{
				++var5;
			}
			playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var5, var6, var7, var2));
		}
		var3 = playerEntity.inventory.getCurrentItem();
		if(var3 != null && var3.stackSize == 0)
		{
			playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = null;
			var3 = null;
		}
		if(var3 == null || var3.getMaxItemUseDuration() == 0)
		{
			playerEntity.playerInventoryBeingManipulated = true;
			playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem] = ItemStack.copyItemStack(playerEntity.inventory.mainInventory[playerEntity.inventory.currentItem]);
			Slot var9 = playerEntity.openContainer.getSlotFromInventory(playerEntity.inventory, playerEntity.inventory.currentItem);
			playerEntity.openContainer.detectAndSendChanges();
			playerEntity.playerInventoryBeingManipulated = false;
			if(!ItemStack.areItemStacksEqual(playerEntity.inventory.getCurrentItem(), p_72472_1_.getItemStack()))
			{
				sendPacketToPlayer(new Packet103SetSlot(playerEntity.openContainer.windowId, var9.slotNumber, playerEntity.inventory.getCurrentItem()));
			}
		}
	}
	
	@Override public void handlePlayerAbilities(Packet202PlayerAbilities p_72471_1_)
	{
		playerEntity.capabilities.isFlying = p_72471_1_.getFlying() && playerEntity.capabilities.allowFlying;
	}
	
	@Override public void handleRespawn(Packet9Respawn p_72483_1_)
	{
	}
	
	private void handleSlashCommand(String p_72566_1_)
	{
		mcServer.getCommandManager().executeCommand(playerEntity, p_72566_1_);
	}
	
	@Override public void handleTransaction(Packet106Transaction p_72476_1_)
	{
		Short var2 = (Short) field_72586_s.lookup(playerEntity.openContainer.windowId);
		if(var2 != null && p_72476_1_.shortWindowId == var2.shortValue() && playerEntity.openContainer.windowId == p_72476_1_.windowId && !playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity))
		{
			playerEntity.openContainer.setPlayerIsPresent(playerEntity, true);
		}
	}
	
	@Override public void handleUpdateSign(Packet130UpdateSign p_72487_1_)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		if(var2.blockExists(p_72487_1_.xPosition, p_72487_1_.yPosition, p_72487_1_.zPosition))
		{
			TileEntity var3 = var2.getBlockTileEntity(p_72487_1_.xPosition, p_72487_1_.yPosition, p_72487_1_.zPosition);
			if(var3 instanceof TileEntitySign)
			{
				TileEntitySign var4 = (TileEntitySign) var3;
				if(!var4.isEditable())
				{
					mcServer.logWarning("Player " + playerEntity.username + " just tried to change non-editable sign");
					return;
				}
			}
			int var6;
			int var8;
			for(var8 = 0; var8 < 4; ++var8)
			{
				boolean var5 = true;
				if(p_72487_1_.signLines[var8].length() > 15)
				{
					var5 = false;
				} else
				{
					for(var6 = 0; var6 < p_72487_1_.signLines[var8].length(); ++var6)
					{
						if(ChatAllowedCharacters.allowedCharacters.indexOf(p_72487_1_.signLines[var8].charAt(var6)) < 0)
						{
							var5 = false;
						}
					}
				}
				if(!var5)
				{
					p_72487_1_.signLines[var8] = "!?";
				}
			}
			if(var3 instanceof TileEntitySign)
			{
				var8 = p_72487_1_.xPosition;
				int var9 = p_72487_1_.yPosition;
				var6 = p_72487_1_.zPosition;
				TileEntitySign var7 = (TileEntitySign) var3;
				System.arraycopy(p_72487_1_.signLines, 0, var7.signText, 0, 4);
				var7.onInventoryChanged();
				var2.markBlockForUpdate(var8, var9, var6);
			}
		}
	}
	
	@Override public void handleUseEntity(Packet7UseEntity p_72507_1_)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		Entity var3 = var2.getEntityByID(p_72507_1_.targetEntity);
		if(var3 != null)
		{
			boolean var4 = playerEntity.canEntityBeSeen(var3);
			double var5 = 36.0D;
			if(!var4)
			{
				var5 = 9.0D;
			}
			if(playerEntity.getDistanceSqToEntity(var3) < var5)
			{
				if(p_72507_1_.isLeftClick == 0)
				{
					playerEntity.interactWith(var3);
				} else if(p_72507_1_.isLeftClick == 1)
				{
					playerEntity.attackTargetEntityWithCurrentItem(var3);
				}
			}
		}
	}
	
	@Override public void handleWindowClick(Packet102WindowClick p_72523_1_)
	{
		if(playerEntity.openContainer.windowId == p_72523_1_.window_Id && playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity))
		{
			ItemStack var2 = playerEntity.openContainer.slotClick(p_72523_1_.inventorySlot, p_72523_1_.mouseClick, p_72523_1_.holdingShift, playerEntity);
			if(ItemStack.areItemStacksEqual(p_72523_1_.itemStack, var2))
			{
				playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet106Transaction(p_72523_1_.window_Id, p_72523_1_.action, true));
				playerEntity.playerInventoryBeingManipulated = true;
				playerEntity.openContainer.detectAndSendChanges();
				playerEntity.updateHeldItem();
				playerEntity.playerInventoryBeingManipulated = false;
			} else
			{
				field_72586_s.addKey(playerEntity.openContainer.windowId, Short.valueOf(p_72523_1_.action));
				playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet106Transaction(p_72523_1_.window_Id, p_72523_1_.action, false));
				playerEntity.openContainer.setPlayerIsPresent(playerEntity, false);
				ArrayList var3 = new ArrayList();
				for(int var4 = 0; var4 < playerEntity.openContainer.inventorySlots.size(); ++var4)
				{
					var3.add(((Slot) playerEntity.openContainer.inventorySlots.get(var4)).getStack());
				}
				playerEntity.sendContainerAndContentsToPlayer(playerEntity.openContainer, var3);
			}
		}
	}
	
	@Override public boolean isServerHandler()
	{
		return true;
	}
	
	public void kickPlayerFromServer(String p_72565_1_)
	{
		if(!connectionClosed)
		{
			playerEntity.mountEntityAndWakeUp();
			sendPacketToPlayer(new Packet255KickDisconnect(p_72565_1_));
			netManager.serverShutdown();
			mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet3Chat(EnumChatFormatting.YELLOW + playerEntity.username + " left the game."));
			mcServer.getConfigurationManager().playerLoggedOut(playerEntity);
			connectionClosed = true;
		}
	}
	
	public void networkTick()
	{
		field_72584_h = false;
		++currentTicks;
		mcServer.theProfiler.startSection("packetflow");
		netManager.processReadPackets();
		mcServer.theProfiler.endStartSection("keepAlive");
		if(currentTicks - ticksOfLastKeepAlive > 20L)
		{
			ticksOfLastKeepAlive = currentTicks;
			keepAliveTimeSent = System.nanoTime() / 1000000L;
			keepAliveRandomID = randomGenerator.nextInt();
			sendPacketToPlayer(new Packet0KeepAlive(keepAliveRandomID));
		}
		if(chatSpamThresholdCount > 0)
		{
			--chatSpamThresholdCount;
		}
		if(creativeItemCreationSpamThresholdTally > 0)
		{
			--creativeItemCreationSpamThresholdTally;
		}
		mcServer.theProfiler.endStartSection("playerTick");
		mcServer.theProfiler.endSection();
	}
	
	public int packetSize()
	{
		return netManager.packetSize();
	}
	
	public void sendPacketToPlayer(Packet p_72567_1_)
	{
		if(p_72567_1_ instanceof Packet3Chat)
		{
			Packet3Chat var2 = (Packet3Chat) p_72567_1_;
			int var3 = playerEntity.getChatVisibility();
			if(var3 == 2) return;
			if(var3 == 1 && !var2.getIsServer()) return;
		}
		try
		{
			netManager.addToSendQueue(p_72567_1_);
		} catch(Throwable var5)
		{
			CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
			CrashReportCategory var4 = var6.makeCategory("Packet being sent");
			var4.addCrashSectionCallable("Packet ID", new CallablePacketID(this, p_72567_1_));
			var4.addCrashSectionCallable("Packet class", new CallablePacketClass(this, p_72567_1_));
			throw new ReportedException(var6);
		}
	}
	
	public void setPlayerLocation(double p_72569_1_, double p_72569_3_, double p_72569_5_, float p_72569_7_, float p_72569_8_)
	{
		hasMoved = false;
		lastPosX = p_72569_1_;
		lastPosY = p_72569_3_;
		lastPosZ = p_72569_5_;
		playerEntity.setPositionAndRotation(p_72569_1_, p_72569_3_, p_72569_5_, p_72569_7_, p_72569_8_);
		playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet13PlayerLookMove(p_72569_1_, p_72569_3_ + 1.6200000047683716D, p_72569_3_, p_72569_5_, p_72569_7_, p_72569_8_, false));
	}
	
	@Override public void unexpectedPacket(Packet p_72509_1_)
	{
		mcServer.getLogAgent().logWarning(this.getClass() + " wasn\'t prepared to deal with a " + p_72509_1_.getClass());
		kickPlayerFromServer("Protocol error, unexpected packet");
	}
}
