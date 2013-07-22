package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class EntityPlayerSP extends AbstractClientPlayer
{
	public MovementInput movementInput;
	protected Minecraft mc;
	protected int sprintToggleTimer;
	public int sprintingTicksLeft;
	public float renderArmYaw;
	public float renderArmPitch;
	public float prevRenderArmYaw;
	public float prevRenderArmPitch;
	private int field_110320_a;
	private float field_110321_bQ;
	private MouseFilter field_71162_ch = new MouseFilter();
	private MouseFilter field_71160_ci = new MouseFilter();
	private MouseFilter field_71161_cj = new MouseFilter();
	public float timeInPortal;
	public float prevTimeInPortal;
	
	public EntityPlayerSP(Minecraft par1Minecraft, World par2World, Session par3Session, int par4)
	{
		super(par2World, par3Session.func_111285_a());
		mc = par1Minecraft;
		dimension = par4;
	}
	
	@Override public void addChatMessage(String par1Str)
	{
		mc.ingameGUI.getChatGUI().addTranslatedMessage(par1Str, new Object[0]);
	}
	
	@Override public void addStat(StatBase par1StatBase, int par2)
	{
		if(par1StatBase != null)
		{
			if(par1StatBase.isAchievement())
			{
				Achievement var3 = (Achievement) par1StatBase;
				if(var3.parentAchievement == null || mc.statFileWriter.hasAchievementUnlocked(var3.parentAchievement))
				{
					if(!mc.statFileWriter.hasAchievementUnlocked(var3))
					{
						mc.guiAchievement.queueTakenAchievement(var3);
					}
					mc.statFileWriter.readStat(par1StatBase, par2);
				}
			} else
			{
				mc.statFileWriter.readStat(par1StatBase, par2);
			}
		}
	}
	
	@Override public boolean canCommandSenderUseCommand(int par1, String par2Str)
	{
		return par1 <= 0;
	}
	
	@Override public void closeScreen()
	{
		super.closeScreen();
		mc.displayGuiScreen((GuiScreen) null);
	}
	
	@Override public void displayGUIAnvil(int par1, int par2, int par3)
	{
		mc.displayGuiScreen(new GuiRepair(inventory, worldObj, par1, par2, par3));
	}
	
	@Override public void displayGUIBeacon(TileEntityBeacon par1TileEntityBeacon)
	{
		mc.displayGuiScreen(new GuiBeacon(inventory, par1TileEntityBeacon));
	}
	
	@Override public void displayGUIBook(ItemStack par1ItemStack)
	{
		Item var2 = par1ItemStack.getItem();
		if(var2 == Item.writtenBook)
		{
			mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, false));
		} else if(var2 == Item.writableBook)
		{
			mc.displayGuiScreen(new GuiScreenBook(this, par1ItemStack, true));
		}
	}
	
	@Override public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
	{
		mc.displayGuiScreen(new GuiBrewingStand(inventory, par1TileEntityBrewingStand));
	}
	
	@Override public void displayGUIChest(IInventory par1IInventory)
	{
		mc.displayGuiScreen(new GuiChest(inventory, par1IInventory));
	}
	
	@Override public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
	{
		mc.displayGuiScreen(new GuiDispenser(inventory, par1TileEntityDispenser));
	}
	
	@Override public void displayGUIEditSign(TileEntity par1TileEntity)
	{
		if(par1TileEntity instanceof TileEntitySign)
		{
			mc.displayGuiScreen(new GuiEditSign((TileEntitySign) par1TileEntity));
		} else if(par1TileEntity instanceof TileEntityCommandBlock)
		{
			mc.displayGuiScreen(new GuiCommandBlock((TileEntityCommandBlock) par1TileEntity));
		}
	}
	
	@Override public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str)
	{
		mc.displayGuiScreen(new GuiEnchantment(inventory, worldObj, par1, par2, par3, par4Str));
	}
	
	@Override public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
	{
		mc.displayGuiScreen(new GuiFurnace(inventory, par1TileEntityFurnace));
	}
	
	@Override public void displayGUIHopper(TileEntityHopper par1TileEntityHopper)
	{
		mc.displayGuiScreen(new GuiHopper(inventory, par1TileEntityHopper));
	}
	
	@Override public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper)
	{
		mc.displayGuiScreen(new GuiHopper(inventory, par1EntityMinecartHopper));
	}
	
	@Override public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str)
	{
		mc.displayGuiScreen(new GuiMerchant(inventory, par1IMerchant, worldObj, par2Str));
	}
	
	@Override public void displayGUIWorkbench(int par1, int par2, int par3)
	{
		mc.displayGuiScreen(new GuiCrafting(inventory, worldObj, par1, par2, par3));
	}
	
	@Override public void func_110298_a(EntityHorse par1EntityHorse, IInventory par2IInventory)
	{
		mc.displayGuiScreen(new GuiScreenHorseInventory(inventory, par2IInventory, par1EntityHorse));
	}
	
	public boolean func_110317_t()
	{
		return ridingEntity != null && ridingEntity instanceof EntityHorse;
	}
	
	protected void func_110318_g()
	{
	}
	
	public float func_110319_bJ()
	{
		return field_110321_bQ;
	}
	
	public float getFOVMultiplier()
	{
		float var1 = 1.0F;
		if(capabilities.isFlying)
		{
			var1 *= 1.1F;
		}
		AttributeInstance var2 = func_110148_a(SharedMonsterAttributes.field_111263_d);
		var1 = (float) (var1 * ((var2.func_111126_e() / capabilities.getWalkSpeed() + 1.0D) / 2.0D));
		if(isUsingItem() && getItemInUse().itemID == Item.bow.itemID)
		{
			int var3 = getItemInUseDuration();
			float var4 = var3 / 20.0F;
			if(var4 > 1.0F)
			{
				var4 = 1.0F;
			} else
			{
				var4 *= var4;
			}
			var1 *= 1.0F - var4 * 0.15F;
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
	
	@Override public boolean isClientWorld()
	{
		return true;
	}
	
	@Override public boolean isSneaking()
	{
		return movementInput.sneak && !sleeping;
	}
	
	@Override public void onCriticalHit(Entity par1Entity)
	{
		mc.effectRenderer.addEffect(new EntityCrit2FX(mc.theWorld, par1Entity));
	}
	
	@Override public void onEnchantmentCritical(Entity par1Entity)
	{
		EntityCrit2FX var2 = new EntityCrit2FX(mc.theWorld, par1Entity, "magicCrit");
		mc.effectRenderer.addEffect(var2);
	}
	
	@Override public void onItemPickup(Entity par1Entity, int par2)
	{
		mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, par1Entity, this, -0.5F));
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
			if(isUsingItem() && !isRiding())
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
			if(func_110317_t())
			{
				if(field_110320_a < 0)
				{
					++field_110320_a;
					if(field_110320_a == 0)
					{
						field_110321_bQ = 0.0F;
					}
				}
				if(var1 && !movementInput.jump)
				{
					field_110320_a = -10;
					func_110318_g();
				} else if(!var1 && movementInput.jump)
				{
					field_110320_a = 0;
					field_110321_bQ = 0.0F;
				} else if(var1)
				{
					++field_110320_a;
					if(field_110320_a < 10)
					{
						field_110321_bQ = field_110320_a * 0.1F;
					} else
					{
						field_110321_bQ = 0.8F + 2.0F / (field_110320_a - 9) * 0.1F;
					}
				}
			} else
			{
				field_110321_bQ = 0.0F;
			}
			super.onLivingUpdate();
			if(onGround && capabilities.isFlying)
			{
				capabilities.isFlying = false;
				sendPlayerAbilities();
			}
		}
	}
	
	@Override public void playSound(String par1Str, float par2, float par3)
	{
		worldObj.playSound(posX, posY - yOffset, posZ, par1Str, par2, par3, false);
	}
	
	@Override protected boolean pushOutOfBlocks(double par1, double par3, double par5)
	{
		int var7 = MathHelper.floor_double(par1);
		int var8 = MathHelper.floor_double(par3);
		int var9 = MathHelper.floor_double(par5);
		double var10 = par1 - var7;
		double var12 = par5 - var9;
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
	
	@Override public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(par1ChatMessageComponent.func_111068_a(true));
	}
	
	public void setHealth(float par1)
	{
		float var2 = func_110143_aJ() - par1;
		if(var2 <= 0.0F)
		{
			setEntityHealth(par1);
			if(var2 < 0.0F)
			{
				hurtResistantTime = maxHurtResistantTime / 2;
			}
		} else
		{
			field_110153_bc = var2;
			setEntityHealth(func_110143_aJ());
			hurtResistantTime = maxHurtResistantTime;
			damageEntity(DamageSource.generic, var2);
			hurtTime = maxHurtTime = 10;
		}
	}
	
	@Override public void setSprinting(boolean par1)
	{
		super.setSprinting(par1);
		sprintingTicksLeft = par1 ? 600 : 0;
	}
	
	public void setXPStats(float par1, int par2, int par3)
	{
		experience = par1;
		experienceTotal = par2;
		experienceLevel = par3;
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
