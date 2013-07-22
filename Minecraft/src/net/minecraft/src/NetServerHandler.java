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
	public boolean connectionClosed;
	public EntityPlayerMP playerEntity;
	private int currentTicks;
	private int ticksForFloatKick;
	private boolean field_72584_h;
	private int keepAliveRandomID;
	private long keepAliveTimeSent;
	private static Random randomGenerator = new Random();
	private long ticksOfLastKeepAlive;
	private int chatSpamThresholdCount;
	private int creativeItemCreationSpamThresholdTally;
	private double lastPosX;
	private double lastPosY;
	private double lastPosZ;
	private boolean hasMoved = true;
	private IntHashMap field_72586_s = new IntHashMap();
	
	public NetServerHandler(MinecraftServer par1MinecraftServer, INetworkManager par2INetworkManager, EntityPlayerMP par3EntityPlayerMP)
	{
		mcServer = par1MinecraftServer;
		netManager = par2INetworkManager;
		par2INetworkManager.setNetHandler(this);
		playerEntity = par3EntityPlayerMP;
		par3EntityPlayerMP.playerNetServerHandler = this;
	}
	
	@Override public boolean canProcessPacketsAsync()
	{
		return true;
	}
	
	@Override public void func_110774_a(Packet27PlayerInput par1Packet27PlayerInput)
	{
		playerEntity.func_110430_a(par1Packet27PlayerInput.func_111010_d(), par1Packet27PlayerInput.func_111012_f(), par1Packet27PlayerInput.func_111013_g(), par1Packet27PlayerInput.func_111011_h());
	}
	
	@Override public boolean func_142032_c()
	{
		return connectionClosed;
	}
	
	@Override public void handleAnimation(Packet18Animation par1Packet18Animation)
	{
		if(par1Packet18Animation.animate == 1)
		{
			playerEntity.swingItem();
		}
	}
	
	@Override public void handleAutoComplete(Packet203AutoComplete par1Packet203AutoComplete)
	{
		StringBuilder var2 = new StringBuilder();
		String var4;
		for(Iterator var3 = mcServer.getPossibleCompletions(playerEntity, par1Packet203AutoComplete.getText()).iterator(); var3.hasNext(); var2.append(var4))
		{
			var4 = (String) var3.next();
			if(var2.length() > 0)
			{
				var2.append("\u0000");
			}
		}
		playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet203AutoComplete(var2.toString()));
	}
	
	@Override public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		if(par1Packet14BlockDig.status == 4)
		{
			playerEntity.dropOneItem(false);
		} else if(par1Packet14BlockDig.status == 3)
		{
			playerEntity.dropOneItem(true);
		} else if(par1Packet14BlockDig.status == 5)
		{
			playerEntity.stopUsingItem();
		} else
		{
			boolean var3 = false;
			if(par1Packet14BlockDig.status == 0)
			{
				var3 = true;
			}
			if(par1Packet14BlockDig.status == 1)
			{
				var3 = true;
			}
			if(par1Packet14BlockDig.status == 2)
			{
				var3 = true;
			}
			int var4 = par1Packet14BlockDig.xPosition;
			int var5 = par1Packet14BlockDig.yPosition;
			int var6 = par1Packet14BlockDig.zPosition;
			if(var3)
			{
				double var7 = playerEntity.posX - (var4 + 0.5D);
				double var9 = playerEntity.posY - (var5 + 0.5D) + 1.5D;
				double var11 = playerEntity.posZ - (var6 + 0.5D);
				double var13 = var7 * var7 + var9 * var9 + var11 * var11;
				if(var13 > 36.0D) return;
				if(var5 >= mcServer.getBuildLimit()) return;
			}
			if(par1Packet14BlockDig.status == 0)
			{
				if(!mcServer.func_96290_a(var2, var4, var5, var6, playerEntity))
				{
					playerEntity.theItemInWorldManager.onBlockClicked(var4, var5, var6, par1Packet14BlockDig.face);
				} else
				{
					playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
				}
			} else if(par1Packet14BlockDig.status == 2)
			{
				playerEntity.theItemInWorldManager.uncheckedTryHarvestBlock(var4, var5, var6);
				if(var2.getBlockId(var4, var5, var6) != 0)
				{
					playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
				}
			} else if(par1Packet14BlockDig.status == 1)
			{
				playerEntity.theItemInWorldManager.cancelDestroyingBlock(var4, var5, var6);
				if(var2.getBlockId(var4, var5, var6) != 0)
				{
					playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(var4, var5, var6, var2));
				}
			}
		}
	}
	
	@Override public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
	{
		if(par1Packet16BlockItemSwitch.id >= 0 && par1Packet16BlockItemSwitch.id < InventoryPlayer.getHotbarSize())
		{
			playerEntity.inventory.currentItem = par1Packet16BlockItemSwitch.id;
		} else
		{
			mcServer.getLogAgent().logWarning(playerEntity.getCommandSenderName() + " tried to set an invalid carried item");
		}
	}
	
	@Override public void handleChat(Packet3Chat par1Packet3Chat)
	{
		if(playerEntity.getChatVisibility() == 2)
		{
			sendPacketToPlayer(new Packet3Chat(ChatMessageComponent.func_111077_e("chat.cannotSend").func_111059_a(EnumChatFormatting.RED)));
		} else
		{
			String var2 = par1Packet3Chat.message;
			if(var2.length() > 100)
			{
				kickPlayerFromServer("Chat message too long");
			} else
			{
				var2 = org.apache.commons.lang3.StringUtils.normalizeSpace(var2);
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
						sendPacketToPlayer(new Packet3Chat(ChatMessageComponent.func_111077_e("chat.cannotSend").func_111059_a(EnumChatFormatting.RED)));
						return;
					}
					ChatMessageComponent var4 = ChatMessageComponent.func_111082_b("chat.type.text", new Object[] { playerEntity.getTranslatedEntityName(), var2 });
					mcServer.getConfigurationManager().func_110459_a(var4, false);
				}
				chatSpamThresholdCount += 20;
				if(chatSpamThresholdCount > 200 && !mcServer.getConfigurationManager().areCommandsAllowed(playerEntity.getCommandSenderName()))
				{
					kickPlayerFromServer("disconnect.spam");
				}
			}
		}
	}
	
	@Override public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand)
	{
		if(par1Packet205ClientCommand.forceRespawn == 1)
		{
			if(playerEntity.playerConqueredTheEnd)
			{
				playerEntity = mcServer.getConfigurationManager().respawnPlayer(playerEntity, 0, true);
			} else if(playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled())
			{
				if(mcServer.isSinglePlayer() && playerEntity.getCommandSenderName().equals(mcServer.getServerOwner()))
				{
					playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
					mcServer.deleteWorldAndStopServer();
				} else
				{
					BanEntry var2 = new BanEntry(playerEntity.getCommandSenderName());
					var2.setBanReason("Death in Hardcore");
					mcServer.getConfigurationManager().getBannedPlayers().put(var2);
					playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
				}
			} else
			{
				if(playerEntity.func_110143_aJ() > 0.0F) return;
				playerEntity = mcServer.getConfigurationManager().respawnPlayer(playerEntity, 0, false);
			}
		}
	}
	
	@Override public void handleClientInfo(Packet204ClientInfo par1Packet204ClientInfo)
	{
		playerEntity.updateClientInfo(par1Packet204ClientInfo);
	}
	
	@Override public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow)
	{
		playerEntity.closeContainer();
	}
	
	@Override public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot)
	{
		if(playerEntity.theItemInWorldManager.isCreative())
		{
			boolean var2 = par1Packet107CreativeSetSlot.slot < 0;
			ItemStack var3 = par1Packet107CreativeSetSlot.itemStack;
			boolean var4 = par1Packet107CreativeSetSlot.slot >= 1 && par1Packet107CreativeSetSlot.slot < 36 + InventoryPlayer.getHotbarSize();
			boolean var5 = var3 == null || var3.itemID < Item.itemsList.length && var3.itemID >= 0 && Item.itemsList[var3.itemID] != null;
			boolean var6 = var3 == null || var3.getItemDamage() >= 0 && var3.getItemDamage() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;
			if(var4 && var5 && var6)
			{
				if(var3 == null)
				{
					playerEntity.inventoryContainer.putStackInSlot(par1Packet107CreativeSetSlot.slot, (ItemStack) null);
				} else
				{
					playerEntity.inventoryContainer.putStackInSlot(par1Packet107CreativeSetSlot.slot, var3);
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
	
	@Override public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload)
	{
		DataInputStream var2;
		ItemStack var3;
		ItemStack var4;
		if("MC|BEdit".equals(par1Packet250CustomPayload.channel))
		{
			try
			{
				var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
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
		} else if("MC|BSign".equals(par1Packet250CustomPayload.channel))
		{
			try
			{
				var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
				var3 = Packet.readItemStack(var2);
				if(!ItemEditableBook.validBookTagContents(var3.getTagCompound())) throw new IOException("Invalid book tag!");
				var4 = playerEntity.inventory.getCurrentItem();
				if(var3 != null && var3.itemID == Item.writtenBook.itemID && var4.itemID == Item.writableBook.itemID)
				{
					var4.setTagInfo("author", new NBTTagString("author", playerEntity.getCommandSenderName()));
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
			if("MC|TrSel".equals(par1Packet250CustomPayload.channel))
			{
				try
				{
					var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
					var14 = var2.readInt();
					Container var16 = playerEntity.openContainer;
					if(var16 instanceof ContainerMerchant)
					{
						((ContainerMerchant) var16).setCurrentRecipeIndex(var14);
					}
				} catch(Exception var10)
				{
					var10.printStackTrace();
				}
			} else
			{
				int var18;
				if("MC|AdvCdm".equals(par1Packet250CustomPayload.channel))
				{
					if(!mcServer.isCommandBlockEnabled())
					{
						playerEntity.sendChatToPlayer(ChatMessageComponent.func_111077_e("advMode.notEnabled"));
					} else if(playerEntity.canCommandSenderUseCommand(2, "") && playerEntity.capabilities.isCreativeMode)
					{
						try
						{
							var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
							var14 = var2.readInt();
							var18 = var2.readInt();
							int var5 = var2.readInt();
							String var6 = Packet.readString(var2, 256);
							TileEntity var7 = playerEntity.worldObj.getBlockTileEntity(var14, var18, var5);
							if(var7 != null && var7 instanceof TileEntityCommandBlock)
							{
								((TileEntityCommandBlock) var7).setCommand(var6);
								playerEntity.worldObj.markBlockForUpdate(var14, var18, var5);
								playerEntity.sendChatToPlayer(ChatMessageComponent.func_111082_b("advMode.setCommand.success", new Object[] { var6 }));
							}
						} catch(Exception var9)
						{
							var9.printStackTrace();
						}
					} else
					{
						playerEntity.sendChatToPlayer(ChatMessageComponent.func_111077_e("advMode.notAllowed"));
					}
				} else if("MC|Beacon".equals(par1Packet250CustomPayload.channel))
				{
					if(playerEntity.openContainer instanceof ContainerBeacon)
					{
						try
						{
							var2 = new DataInputStream(new ByteArrayInputStream(par1Packet250CustomPayload.data));
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
				} else if("MC|ItemName".equals(par1Packet250CustomPayload.channel) && playerEntity.openContainer instanceof ContainerRepair)
				{
					ContainerRepair var13 = (ContainerRepair) playerEntity.openContainer;
					if(par1Packet250CustomPayload.data != null && par1Packet250CustomPayload.data.length >= 1)
					{
						String var15 = ChatAllowedCharacters.filerAllowedCharacters(new String(par1Packet250CustomPayload.data));
						if(var15.length() <= 30)
						{
							var13.updateItemName(var15);
						}
					} else
					{
						var13.updateItemName("");
					}
				}
			}
		}
	}
	
	@Override public void handleEnchantItem(Packet108EnchantItem par1Packet108EnchantItem)
	{
		if(playerEntity.openContainer.windowId == par1Packet108EnchantItem.windowId && playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity))
		{
			playerEntity.openContainer.enchantItem(playerEntity, par1Packet108EnchantItem.enchantment);
			playerEntity.openContainer.detectAndSendChanges();
		}
	}
	
	@Override public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction)
	{
		if(par1Packet19EntityAction.state == 1)
		{
			playerEntity.setSneaking(true);
		} else if(par1Packet19EntityAction.state == 2)
		{
			playerEntity.setSneaking(false);
		} else if(par1Packet19EntityAction.state == 4)
		{
			playerEntity.setSprinting(true);
		} else if(par1Packet19EntityAction.state == 5)
		{
			playerEntity.setSprinting(false);
		} else if(par1Packet19EntityAction.state == 3)
		{
			playerEntity.wakeUpPlayer(false, true, true);
			hasMoved = false;
		} else if(par1Packet19EntityAction.state == 6)
		{
			if(playerEntity.ridingEntity != null && playerEntity.ridingEntity instanceof EntityHorse)
			{
				((EntityHorse) playerEntity.ridingEntity).func_110206_u(par1Packet19EntityAction.field_111009_c);
			}
		} else if(par1Packet19EntityAction.state == 7 && playerEntity.ridingEntity != null && playerEntity.ridingEntity instanceof EntityHorse)
		{
			((EntityHorse) playerEntity.ridingEntity).func_110199_f(playerEntity);
		}
	}
	
	@Override public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj)
	{
		mcServer.getLogAgent().logInfo(playerEntity.getCommandSenderName() + " lost connection: " + par1Str);
		mcServer.getConfigurationManager().sendChatMsg(ChatMessageComponent.func_111082_b("multiplayer.player.left", new Object[] { playerEntity.getTranslatedEntityName() }).func_111059_a(EnumChatFormatting.YELLOW));
		mcServer.getConfigurationManager().playerLoggedOut(playerEntity);
		connectionClosed = true;
		if(mcServer.isSinglePlayer() && playerEntity.getCommandSenderName().equals(mcServer.getServerOwner()))
		{
			mcServer.getLogAgent().logInfo("Stopping singleplayer server as player logged out");
			mcServer.initiateShutdown();
		}
	}
	
	@Override public void handleFlying(Packet10Flying par1Packet10Flying)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		field_72584_h = true;
		if(!playerEntity.playerConqueredTheEnd)
		{
			double var3;
			if(!hasMoved)
			{
				var3 = par1Packet10Flying.yPosition - lastPosY;
				if(par1Packet10Flying.xPosition == lastPosX && var3 * var3 < 0.01D && par1Packet10Flying.zPosition == lastPosZ)
				{
					hasMoved = true;
				}
			}
			if(hasMoved)
			{
				double var5;
				double var7;
				double var9;
				if(playerEntity.ridingEntity != null)
				{
					float var34 = playerEntity.rotationYaw;
					float var4 = playerEntity.rotationPitch;
					playerEntity.ridingEntity.updateRiderPosition();
					var5 = playerEntity.posX;
					var7 = playerEntity.posY;
					var9 = playerEntity.posZ;
					if(par1Packet10Flying.rotating)
					{
						var34 = par1Packet10Flying.yaw;
						var4 = par1Packet10Flying.pitch;
					}
					playerEntity.onGround = par1Packet10Flying.onGround;
					playerEntity.onUpdateEntity();
					playerEntity.ySize = 0.0F;
					playerEntity.setPositionAndRotation(var5, var7, var9, var34, var4);
					if(playerEntity.ridingEntity != null)
					{
						playerEntity.ridingEntity.updateRiderPosition();
					}
					mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(playerEntity);
					if(hasMoved)
					{
						lastPosX = playerEntity.posX;
						lastPosY = playerEntity.posY;
						lastPosZ = playerEntity.posZ;
					}
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
				if(par1Packet10Flying.moving && par1Packet10Flying.yPosition == -999.0D && par1Packet10Flying.stance == -999.0D)
				{
					par1Packet10Flying.moving = false;
				}
				double var13;
				if(par1Packet10Flying.moving)
				{
					var5 = par1Packet10Flying.xPosition;
					var7 = par1Packet10Flying.yPosition;
					var9 = par1Packet10Flying.zPosition;
					var13 = par1Packet10Flying.stance - par1Packet10Flying.yPosition;
					if(!playerEntity.isPlayerSleeping() && (var13 > 1.65D || var13 < 0.1D))
					{
						kickPlayerFromServer("Illegal stance");
						mcServer.getLogAgent().logWarning(playerEntity.getCommandSenderName() + " had an illegal stance: " + var13);
						return;
					}
					if(Math.abs(par1Packet10Flying.xPosition) > 3.2E7D || Math.abs(par1Packet10Flying.zPosition) > 3.2E7D)
					{
						kickPlayerFromServer("Illegal position");
						return;
					}
				}
				if(par1Packet10Flying.rotating)
				{
					var11 = par1Packet10Flying.yaw;
					var12 = par1Packet10Flying.pitch;
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
				if(var25 > 100.0D && (!mcServer.isSinglePlayer() || !mcServer.getServerOwner().equals(playerEntity.getCommandSenderName())))
				{
					mcServer.getLogAgent().logWarning(playerEntity.getCommandSenderName() + " moved too quickly! " + var13 + "," + var15 + "," + var17 + " (" + var19 + ", " + var21 + ", " + var23 + ")");
					setPlayerLocation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw, playerEntity.rotationPitch);
					return;
				}
				float var27 = 0.0625F;
				boolean var28 = var2.getCollidingBoundingBoxes(playerEntity, playerEntity.boundingBox.copy().contract(var27, var27, var27)).isEmpty();
				if(playerEntity.onGround && !par1Packet10Flying.onGround && var15 > 0.0D)
				{
					playerEntity.addExhaustion(0.2F);
				}
				playerEntity.moveEntity(var13, var15, var17);
				playerEntity.onGround = par1Packet10Flying.onGround;
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
					mcServer.getLogAgent().logWarning(playerEntity.getCommandSenderName() + " moved wrongly!");
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
							mcServer.getLogAgent().logWarning(playerEntity.getCommandSenderName() + " was kicked for floating too long!");
							kickPlayerFromServer("Flying is not enabled on this server");
							return;
						}
					}
				} else
				{
					ticksForFloatKick = 0;
				}
				playerEntity.onGround = par1Packet10Flying.onGround;
				mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(playerEntity);
				playerEntity.updateFlyingState(playerEntity.posY - var3, par1Packet10Flying.onGround);
			} else if(currentTicks % 20 == 0)
			{
				setPlayerLocation(lastPosX, lastPosY, lastPosZ, playerEntity.rotationYaw, playerEntity.rotationPitch);
			}
		}
	}
	
	@Override public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive)
	{
		if(par1Packet0KeepAlive.randomId == keepAliveRandomID)
		{
			int var2 = (int) (System.nanoTime() / 1000000L - keepAliveTimeSent);
			playerEntity.ping = (playerEntity.ping * 3 + var2) / 4;
		}
	}
	
	@Override public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect)
	{
		netManager.networkShutdown("disconnect.quitting", new Object[0]);
	}
	
	@Override public void handlePlace(Packet15Place par1Packet15Place)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		ItemStack var3 = playerEntity.inventory.getCurrentItem();
		boolean var4 = false;
		int var5 = par1Packet15Place.getXPosition();
		int var6 = par1Packet15Place.getYPosition();
		int var7 = par1Packet15Place.getZPosition();
		int var8 = par1Packet15Place.getDirection();
		if(par1Packet15Place.getDirection() == 255)
		{
			if(var3 == null) return;
			playerEntity.theItemInWorldManager.tryUseItem(playerEntity, var2, var3);
		} else if(par1Packet15Place.getYPosition() >= mcServer.getBuildLimit() - 1 && (par1Packet15Place.getDirection() == 1 || par1Packet15Place.getYPosition() >= mcServer.getBuildLimit()))
		{
			playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(ChatMessageComponent.func_111082_b("build.tooHigh", new Object[] { Integer.valueOf(mcServer.getBuildLimit()) }).func_111059_a(EnumChatFormatting.RED)));
			var4 = true;
		} else
		{
			if(hasMoved && playerEntity.getDistanceSq(var5 + 0.5D, var6 + 0.5D, var7 + 0.5D) < 64.0D && !mcServer.func_96290_a(var2, var5, var6, var7, playerEntity))
			{
				playerEntity.theItemInWorldManager.activateBlockOrUseItem(playerEntity, var2, var3, var5, var6, var7, var8, par1Packet15Place.getXOffset(), par1Packet15Place.getYOffset(), par1Packet15Place.getZOffset());
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
			if(!ItemStack.areItemStacksEqual(playerEntity.inventory.getCurrentItem(), par1Packet15Place.getItemStack()))
			{
				sendPacketToPlayer(new Packet103SetSlot(playerEntity.openContainer.windowId, var9.slotNumber, playerEntity.inventory.getCurrentItem()));
			}
		}
	}
	
	@Override public void handlePlayerAbilities(Packet202PlayerAbilities par1Packet202PlayerAbilities)
	{
		playerEntity.capabilities.isFlying = par1Packet202PlayerAbilities.getFlying() && playerEntity.capabilities.allowFlying;
	}
	
	@Override public void handleRespawn(Packet9Respawn par1Packet9Respawn)
	{
	}
	
	private void handleSlashCommand(String par1Str)
	{
		mcServer.getCommandManager().executeCommand(playerEntity, par1Str);
	}
	
	@Override public void handleTransaction(Packet106Transaction par1Packet106Transaction)
	{
		Short var2 = (Short) field_72586_s.lookup(playerEntity.openContainer.windowId);
		if(var2 != null && par1Packet106Transaction.shortWindowId == var2.shortValue() && playerEntity.openContainer.windowId == par1Packet106Transaction.windowId && !playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity))
		{
			playerEntity.openContainer.setPlayerIsPresent(playerEntity, true);
		}
	}
	
	@Override public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		if(var2.blockExists(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition))
		{
			TileEntity var3 = var2.getBlockTileEntity(par1Packet130UpdateSign.xPosition, par1Packet130UpdateSign.yPosition, par1Packet130UpdateSign.zPosition);
			if(var3 instanceof TileEntitySign)
			{
				TileEntitySign var4 = (TileEntitySign) var3;
				if(!var4.isEditable() || var4.func_142009_b() != playerEntity)
				{
					mcServer.logWarning("Player " + playerEntity.getCommandSenderName() + " just tried to change non-editable sign");
					return;
				}
			}
			int var6;
			int var8;
			for(var8 = 0; var8 < 4; ++var8)
			{
				boolean var5 = true;
				if(par1Packet130UpdateSign.signLines[var8].length() > 15)
				{
					var5 = false;
				} else
				{
					for(var6 = 0; var6 < par1Packet130UpdateSign.signLines[var8].length(); ++var6)
					{
						if(ChatAllowedCharacters.allowedCharacters.indexOf(par1Packet130UpdateSign.signLines[var8].charAt(var6)) < 0)
						{
							var5 = false;
						}
					}
				}
				if(!var5)
				{
					par1Packet130UpdateSign.signLines[var8] = "!?";
				}
			}
			if(var3 instanceof TileEntitySign)
			{
				var8 = par1Packet130UpdateSign.xPosition;
				int var9 = par1Packet130UpdateSign.yPosition;
				var6 = par1Packet130UpdateSign.zPosition;
				TileEntitySign var7 = (TileEntitySign) var3;
				System.arraycopy(par1Packet130UpdateSign.signLines, 0, var7.signText, 0, 4);
				var7.onInventoryChanged();
				var2.markBlockForUpdate(var8, var9, var6);
			}
		}
	}
	
	@Override public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity)
	{
		WorldServer var2 = mcServer.worldServerForDimension(playerEntity.dimension);
		Entity var3 = var2.getEntityByID(par1Packet7UseEntity.targetEntity);
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
				if(par1Packet7UseEntity.isLeftClick == 0)
				{
					playerEntity.interactWith(var3);
				} else if(par1Packet7UseEntity.isLeftClick == 1)
				{
					if(var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == playerEntity)
					{
						kickPlayerFromServer("Attempting to attack an invalid entity");
						mcServer.logWarning("Player " + playerEntity.getCommandSenderName() + " tried to attack an invalid entity");
						return;
					}
					playerEntity.attackTargetEntityWithCurrentItem(var3);
				}
			}
		}
	}
	
	@Override public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick)
	{
		if(playerEntity.openContainer.windowId == par1Packet102WindowClick.window_Id && playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity))
		{
			ItemStack var2 = playerEntity.openContainer.slotClick(par1Packet102WindowClick.inventorySlot, par1Packet102WindowClick.mouseClick, par1Packet102WindowClick.holdingShift, playerEntity);
			if(ItemStack.areItemStacksEqual(par1Packet102WindowClick.itemStack, var2))
			{
				playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, true));
				playerEntity.playerInventoryBeingManipulated = true;
				playerEntity.openContainer.detectAndSendChanges();
				playerEntity.updateHeldItem();
				playerEntity.playerInventoryBeingManipulated = false;
			} else
			{
				field_72586_s.addKey(playerEntity.openContainer.windowId, Short.valueOf(par1Packet102WindowClick.action));
				playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet106Transaction(par1Packet102WindowClick.window_Id, par1Packet102WindowClick.action, false));
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
	
	public void kickPlayerFromServer(String par1Str)
	{
		if(!connectionClosed)
		{
			playerEntity.mountEntityAndWakeUp();
			sendPacketToPlayer(new Packet255KickDisconnect(par1Str));
			netManager.serverShutdown();
			mcServer.getConfigurationManager().sendChatMsg(ChatMessageComponent.func_111082_b("multiplayer.player.left", new Object[] { playerEntity.getTranslatedEntityName() }).func_111059_a(EnumChatFormatting.YELLOW));
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
	
	public void sendPacketToPlayer(Packet par1Packet)
	{
		if(par1Packet instanceof Packet3Chat)
		{
			Packet3Chat var2 = (Packet3Chat) par1Packet;
			int var3 = playerEntity.getChatVisibility();
			if(var3 == 2) return;
			if(var3 == 1 && !var2.getIsServer()) return;
		}
		try
		{
			netManager.addToSendQueue(par1Packet);
		} catch(Throwable var5)
		{
			CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
			CrashReportCategory var4 = var6.makeCategory("Packet being sent");
			var4.addCrashSectionCallable("Packet ID", new CallablePacketID(this, par1Packet));
			var4.addCrashSectionCallable("Packet class", new CallablePacketClass(this, par1Packet));
			throw new ReportedException(var6);
		}
	}
	
	public void setPlayerLocation(double par1, double par3, double par5, float par7, float par8)
	{
		hasMoved = false;
		lastPosX = par1;
		lastPosY = par3;
		lastPosZ = par5;
		playerEntity.setPositionAndRotation(par1, par3, par5, par7, par8);
		playerEntity.playerNetServerHandler.sendPacketToPlayer(new Packet13PlayerLookMove(par1, par3 + 1.6200000047683716D, par3, par5, par7, par8, false));
	}
	
	@Override public void unexpectedPacket(Packet par1Packet)
	{
		mcServer.getLogAgent().logWarning(this.getClass() + " wasn\'t prepared to deal with a " + par1Packet.getClass());
		kickPlayerFromServer("Protocol error, unexpected packet");
	}
}
