package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityPlayerSP extends EntityPlayer
{
	public MovementInput movementInput;
	protected Minecraft mc;
	protected int sprintToggleTimer = 0;
	public int sprintingTicksLeft = 0;
	public float renderArmYaw;
	public float renderArmPitch;
	public float prevRenderArmYaw;
	public float prevRenderArmPitch;
	private MouseFilter field_71162_ch = new MouseFilter();
	private MouseFilter field_71160_ci = new MouseFilter();
	private MouseFilter field_71161_cj = new MouseFilter();
	public float timeInPortal;
	public float prevTimeInPortal;
	
	public EntityPlayerSP(Minecraft p_i3116_1_, World p_i3116_2_, Session p_i3116_3_, int p_i3116_4_)
	{
		super(p_i3116_2_);
		mc = p_i3116_1_;
		dimension = p_i3116_4_;
		if(p_i3116_3_ != null && p_i3116_3_.username != null && p_i3116_3_.username.length() > 0)
		{
			skinUrl = "http://skins.minecraft.net/MinecraftSkins/" + StringUtils.stripControlCodes(p_i3116_3_.username) + ".png";
		}
		username = p_i3116_3_.username;
	}
	
	@Override public void addChatMessage(String p_71035_1_)
	{
		mc.ingameGUI.getChatGUI().addTranslatedMessage(p_71035_1_, new Object[0]);
	}
	
	@Override public void addStat(StatBase p_71064_1_, int p_71064_2_)
	{
		if(p_71064_1_ != null)
		{
			if(p_71064_1_.isAchievement())
			{
				Achievement var3 = (Achievement) p_71064_1_;
				if(var3.parentAchievement == null || mc.statFileWriter.hasAchievementUnlocked(var3.parentAchievement))
				{
					if(!mc.statFileWriter.hasAchievementUnlocked(var3))
					{
						mc.guiAchievement.queueTakenAchievement(var3);
					}
					mc.statFileWriter.readStat(p_71064_1_, p_71064_2_);
				}
			} else
			{
				mc.statFileWriter.readStat(p_71064_1_, p_71064_2_);
			}
		}
	}
	
	@Override public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
	{
		return p_70003_1_ <= 0;
	}
	
	@Override public void closeScreen()
	{
		super.closeScreen();
		mc.displayGuiScreen((GuiScreen) null);
	}
	
	@Override public void displayGUIAnvil(int p_82244_1_, int p_82244_2_, int p_82244_3_)
	{
		mc.displayGuiScreen(new GuiRepair(inventory, worldObj, p_82244_1_, p_82244_2_, p_82244_3_));
	}
	
	@Override public void displayGUIBeacon(TileEntityBeacon p_82240_1_)
	{
		mc.displayGuiScreen(new GuiBeacon(inventory, p_82240_1_));
	}
	
	@Override public void displayGUIBook(ItemStack p_71048_1_)
	{
		Item var2 = p_71048_1_.getItem();
		if(var2 == Item.writtenBook)
		{
			mc.displayGuiScreen(new GuiScreenBook(this, p_71048_1_, false));
		} else if(var2 == Item.writableBook)
		{
			mc.displayGuiScreen(new GuiScreenBook(this, p_71048_1_, true));
		}
	}
	
	@Override public void displayGUIBrewingStand(TileEntityBrewingStand p_71017_1_)
	{
		mc.displayGuiScreen(new GuiBrewingStand(inventory, p_71017_1_));
	}
	
	@Override public void displayGUIChest(IInventory p_71007_1_)
	{
		mc.displayGuiScreen(new GuiChest(inventory, p_71007_1_));
	}
	
	@Override public void displayGUIDispenser(TileEntityDispenser p_71006_1_)
	{
		mc.displayGuiScreen(new GuiDispenser(inventory, p_71006_1_));
	}
	
	@Override public void displayGUIEditSign(TileEntity p_71014_1_)
	{
		if(p_71014_1_ instanceof TileEntitySign)
		{
			mc.displayGuiScreen(new GuiEditSign((TileEntitySign) p_71014_1_));
		} else if(p_71014_1_ instanceof TileEntityCommandBlock)
		{
			mc.displayGuiScreen(new GuiCommandBlock((TileEntityCommandBlock) p_71014_1_));
		}
	}
	
	@Override public void displayGUIEnchantment(int p_71002_1_, int p_71002_2_, int p_71002_3_, String p_71002_4_)
	{
		mc.displayGuiScreen(new GuiEnchantment(inventory, worldObj, p_71002_1_, p_71002_2_, p_71002_3_, p_71002_4_));
	}
	
	@Override public void displayGUIFurnace(TileEntityFurnace p_71042_1_)
	{
		mc.displayGuiScreen(new GuiFurnace(inventory, p_71042_1_));
	}
	
	@Override public void displayGUIHopper(TileEntityHopper p_94064_1_)
	{
		mc.displayGuiScreen(new GuiHopper(inventory, p_94064_1_));
	}
	
	@Override public void displayGUIHopperMinecart(EntityMinecartHopper p_96125_1_)
	{
		mc.displayGuiScreen(new GuiHopper(inventory, p_96125_1_));
	}
	
	@Override public void displayGUIMerchant(IMerchant p_71030_1_, String p_71030_2_)
	{
		mc.displayGuiScreen(new GuiMerchant(inventory, p_71030_1_, worldObj, p_71030_2_));
	}
	
	@Override public void displayGUIWorkbench(int p_71058_1_, int p_71058_2_, int p_71058_3_)
	{
		mc.displayGuiScreen(new GuiCrafting(inventory, worldObj, p_71058_1_, p_71058_2_, p_71058_3_));
	}
	
	public float getFOVMultiplier()
	{
		float var1 = 1.0F;
		if(capabilities.isFlying)
		{
			var1 *= 1.1F;
		}
		var1 *= (landMovementFactor * getSpeedModifier() / speedOnGround + 1.0F) / 2.0F;
		if(isUsingItem() && getItemInUse().itemID == Item.bow.itemID)
		{
			int var2 = getItemInUseDuration();
			float var3 = var2 / 20.0F;
			if(var3 > 1.0F)
			{
				var3 = 1.0F;
			} else
			{
				var3 *= var3;
			}
			var1 *= 1.0F - var3 * 0.15F;
		}
		return var1;
	}
	
	@Override public ItemStack getHeldItem()
	{
		return inventory.getCurrentItem();
	}
	
	@Override public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(MathHelper.floor_double(posX + 0.5D), MathHelper.floor_double(posY + 0.5D), MathHelper.floor_double(posZ + 0.5D));
	}
	
	private boolean isBlockTranslucent(int par1, int par2, int par3)
	{
		return worldObj.isBlockNormalCube(par1, par2, par3);
	}
	
	@Override protected boolean isClientWorld()
	{
		return true;
	}
	
	@Override public boolean isSneaking()
	{
		return movementInput.sneak && !sleeping;
	}
	
	@Override public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_)
	{
		super.moveEntity(p_70091_1_, p_70091_3_, p_70091_5_);
	}
	
	@Override public void onCriticalHit(Entity p_71009_1_)
	{
		mc.effectRenderer.addEffect(new EntityCrit2FX(mc.theWorld, p_71009_1_));
	}
	
	@Override public void onEnchantmentCritical(Entity p_71047_1_)
	{
		EntityCrit2FX var2 = new EntityCrit2FX(mc.theWorld, p_71047_1_, "magicCrit");
		mc.effectRenderer.addEffect(var2);
	}
	
	@Override public void onItemPickup(Entity p_71001_1_, int p_71001_2_)
	{
		mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, p_71001_1_, this, -0.5F));
	}
	
	@Override public void onLivingUpdate()
	{
		if(sprintingTicksLeft > 0)
		{
			--sprintingTicksLeft;
			if(sprintingTicksLeft == 0)
			{
				setSprinting(false);
			}
		}
		if(sprintToggleTimer > 0)
		{
			--sprintToggleTimer;
		}
		if(mc.playerController.enableEverythingIsScrewedUpMode())
		{
			posX = posZ = 0.5D;
			posX = 0.0D;
			posZ = 0.0D;
			rotationYaw = ticksExisted / 12.0F;
			rotationPitch = 10.0F;
			posY = 68.5D;
		} else
		{
			if(!mc.statFileWriter.hasAchievementUnlocked(AchievementList.openInventory))
			{
				mc.guiAchievement.queueAchievementInformation(AchievementList.openInventory);
			}
			prevTimeInPortal = timeInPortal;
			if(inPortal)
			{
				if(mc.currentScreen != null)
				{
					mc.displayGuiScreen((GuiScreen) null);
				}
				if(timeInPortal == 0.0F)
				{
					mc.sndManager.playSoundFX("portal.trigger", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
				}
				timeInPortal += 0.0125F;
				if(timeInPortal >= 1.0F)
				{
					timeInPortal = 1.0F;
				}
				inPortal = false;
			} else if(this.isPotionActive(Potion.confusion) && getActivePotionEffect(Potion.confusion).getDuration() > 60)
			{
				timeInPortal += 0.006666667F;
				if(timeInPortal > 1.0F)
				{
					timeInPortal = 1.0F;
				}
			} else
			{
				if(timeInPortal > 0.0F)
				{
					timeInPortal -= 0.05F;
				}
				if(timeInPortal < 0.0F)
				{
					timeInPortal = 0.0F;
				}
			}
			if(timeUntilPortal > 0)
			{
				--timeUntilPortal;
			}
			boolean var1 = movementInput.jump;
			float var2 = 0.8F;
			boolean var3 = movementInput.moveForward >= var2;
			movementInput.updatePlayerMoveState();
			if(isUsingItem())
			{
				movementInput.moveStrafe *= 0.2F;
				movementInput.moveForward *= 0.2F;
				sprintToggleTimer = 0;
			}
			if(movementInput.sneak && ySize < 0.2F)
			{
				ySize = 0.2F;
			}
			pushOutOfBlocks(posX - width * 0.35D, boundingBox.minY + 0.5D, posZ + width * 0.35D);
			pushOutOfBlocks(posX - width * 0.35D, boundingBox.minY + 0.5D, posZ - width * 0.35D);
			pushOutOfBlocks(posX + width * 0.35D, boundingBox.minY + 0.5D, posZ - width * 0.35D);
			pushOutOfBlocks(posX + width * 0.35D, boundingBox.minY + 0.5D, posZ + width * 0.35D);
			boolean var4 = getFoodStats().getFoodLevel() > 6.0F || capabilities.allowFlying;
			if(onGround && !var3 && movementInput.moveForward >= var2 && !isSprinting() && var4 && !isUsingItem() && !this.isPotionActive(Potion.blindness))
			{
				if(sprintToggleTimer == 0)
				{
					sprintToggleTimer = 7;
				} else
				{
					setSprinting(true);
					sprintToggleTimer = 0;
				}
			}
			if(isSneaking())
			{
				sprintToggleTimer = 0;
			}
			if(isSprinting() && (movementInput.moveForward < var2 || isCollidedHorizontally || !var4))
			{
				setSprinting(false);
			}
			if(capabilities.allowFlying && !var1 && movementInput.jump)
			{
				if(flyToggleTimer == 0)
				{
					flyToggleTimer = 7;
				} else
				{
					capabilities.isFlying = !capabilities.isFlying;
					sendPlayerAbilities();
					flyToggleTimer = 0;
				}
			}
			if(capabilities.isFlying)
			{
				if(movementInput.sneak)
				{
					motionY -= 0.15D;
				}
				if(movementInput.jump)
				{
					motionY += 0.15D;
				}
			}
			super.onLivingUpdate();
			if(onGround && capabilities.isFlying)
			{
				capabilities.isFlying = false;
				sendPlayerAbilities();
			}
		}
	}
	
	@Override public void playSound(String p_85030_1_, float p_85030_2_, float p_85030_3_)
	{
		worldObj.playSound(posX, posY - yOffset, posZ, p_85030_1_, p_85030_2_, p_85030_3_, false);
	}
	
	@Override protected boolean pushOutOfBlocks(double p_70048_1_, double p_70048_3_, double p_70048_5_)
	{
		int var7 = MathHelper.floor_double(p_70048_1_);
		int var8 = MathHelper.floor_double(p_70048_3_);
		int var9 = MathHelper.floor_double(p_70048_5_);
		double var10 = p_70048_1_ - var7;
		double var12 = p_70048_5_ - var9;
		if(isBlockTranslucent(var7, var8, var9) || isBlockTranslucent(var7, var8 + 1, var9))
		{
			boolean var14 = !isBlockTranslucent(var7 - 1, var8, var9) && !isBlockTranslucent(var7 - 1, var8 + 1, var9);
			boolean var15 = !isBlockTranslucent(var7 + 1, var8, var9) && !isBlockTranslucent(var7 + 1, var8 + 1, var9);
			boolean var16 = !isBlockTranslucent(var7, var8, var9 - 1) && !isBlockTranslucent(var7, var8 + 1, var9 - 1);
			boolean var17 = !isBlockTranslucent(var7, var8, var9 + 1) && !isBlockTranslucent(var7, var8 + 1, var9 + 1);
			byte var18 = -1;
			double var19 = 9999.0D;
			if(var14 && var10 < var19)
			{
				var19 = var10;
				var18 = 0;
			}
			if(var15 && 1.0D - var10 < var19)
			{
				var19 = 1.0D - var10;
				var18 = 1;
			}
			if(var16 && var12 < var19)
			{
				var19 = var12;
				var18 = 4;
			}
			if(var17 && 1.0D - var12 < var19)
			{
				var19 = 1.0D - var12;
				var18 = 5;
			}
			float var21 = 0.1F;
			if(var18 == 0)
			{
				motionX = -var21;
			}
			if(var18 == 1)
			{
				motionX = var21;
			}
			if(var18 == 4)
			{
				motionZ = -var21;
			}
			if(var18 == 5)
			{
				motionZ = var21;
			}
		}
		return false;
	}
	
	@Override public void sendChatToPlayer(String p_70006_1_)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(p_70006_1_);
	}
	
	public void setHealth(int par1)
	{
		int var2 = getHealth() - par1;
		if(var2 <= 0)
		{
			setEntityHealth(par1);
			if(var2 < 0)
			{
				hurtResistantTime = maxHurtResistantTime / 2;
			}
		} else
		{
			lastDamage = var2;
			setEntityHealth(getHealth());
			hurtResistantTime = maxHurtResistantTime;
			damageEntity(DamageSource.generic, var2);
			hurtTime = maxHurtTime = 10;
		}
	}
	
	@Override public void setSprinting(boolean p_70031_1_)
	{
		super.setSprinting(p_70031_1_);
		sprintingTicksLeft = p_70031_1_ ? 600 : 0;
	}
	
	public void setXPStats(float par1, int par2, int par3)
	{
		experience = par1;
		experienceTotal = par2;
		experienceLevel = par3;
	}
	
	@Override public void updateCloak()
	{
		cloakUrl = "http://skins.minecraft.net/MinecraftCloaks/" + StringUtils.stripControlCodes(username) + ".png";
	}
	
	@Override public void updateEntityActionState()
	{
		super.updateEntityActionState();
		moveStrafing = movementInput.moveStrafe;
		moveForward = movementInput.moveForward;
		isJumping = movementInput.jump;
		prevRenderArmYaw = renderArmYaw;
		prevRenderArmPitch = renderArmPitch;
		renderArmPitch = (float) (renderArmPitch + (rotationPitch - renderArmPitch) * 0.5D);
		renderArmYaw = (float) (renderArmYaw + (rotationYaw - renderArmYaw) * 0.5D);
	}
}
