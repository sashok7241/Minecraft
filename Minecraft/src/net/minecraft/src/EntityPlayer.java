package net.minecraft.src;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class EntityPlayer extends EntityLivingBase implements ICommandSender
{
	public InventoryPlayer inventory = new InventoryPlayer(this);
	private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
	public Container inventoryContainer;
	public Container openContainer;
	protected FoodStats foodStats = new FoodStats();
	protected int flyToggleTimer;
	public float prevCameraYaw;
	public float cameraYaw;
	protected final String username;
	public int xpCooldown;
	public double field_71091_bM;
	public double field_71096_bN;
	public double field_71097_bO;
	public double field_71094_bP;
	public double field_71095_bQ;
	public double field_71085_bR;
	protected boolean sleeping;
	public ChunkCoordinates playerLocation;
	private int sleepTimer;
	public float field_71079_bU;
	public float field_71082_cx;
	public float field_71089_bV;
	private ChunkCoordinates spawnChunk;
	private boolean spawnForced;
	private ChunkCoordinates startMinecartRidingCoordinate;
	public PlayerCapabilities capabilities = new PlayerCapabilities();
	public int experienceLevel;
	public int experienceTotal;
	public float experience;
	private ItemStack itemInUse;
	private int itemInUseCount;
	protected float speedOnGround = 0.1F;
	protected float speedInAir = 0.02F;
	private int field_82249_h;
	public EntityFishHook fishEntity;
	
	public EntityPlayer(World par1World, String par2Str)
	{
		super(par1World);
		username = par2Str;
		inventoryContainer = new ContainerPlayer(inventory, !par1World.isRemote, this);
		openContainer = inventoryContainer;
		yOffset = 1.62F;
		ChunkCoordinates var3 = par1World.getSpawnPoint();
		setLocationAndAngles(var3.posX + 0.5D, var3.posY + 1, var3.posZ + 0.5D, 0.0F, 0.0F);
		field_70741_aB = 180.0F;
		fireResistance = 20;
	}
	
	public void addChatMessage(String par1Str)
	{
	}
	
	public void addExhaustion(float par1)
	{
		if(!capabilities.disableDamage)
		{
			if(!worldObj.isRemote)
			{
				foodStats.addExhaustion(par1);
			}
		}
	}
	
	public void addExperience(int par1)
	{
		addScore(par1);
		int var2 = Integer.MAX_VALUE - experienceTotal;
		if(par1 > var2)
		{
			par1 = var2;
		}
		experience += (float) par1 / (float) xpBarCap();
		for(experienceTotal += par1; experience >= 1.0F; experience /= xpBarCap())
		{
			experience = (experience - 1.0F) * xpBarCap();
			addExperienceLevel(1);
		}
	}
	
	public void addExperienceLevel(int par1)
	{
		experienceLevel += par1;
		if(experienceLevel < 0)
		{
			experienceLevel = 0;
			experience = 0.0F;
			experienceTotal = 0;
		}
		if(par1 > 0 && experienceLevel % 5 == 0 && field_82249_h < ticksExisted - 100.0F)
		{
			float var2 = experienceLevel > 30 ? 1.0F : experienceLevel / 30.0F;
			worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75F, 1.0F);
			field_82249_h = ticksExisted;
		}
	}
	
	private void addMountedMovementStat(double par1, double par3, double par5)
	{
		if(ridingEntity != null)
		{
			int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);
			if(var7 > 0)
			{
				if(ridingEntity instanceof EntityMinecart)
				{
					addStat(StatList.distanceByMinecartStat, var7);
					if(startMinecartRidingCoordinate == null)
					{
						startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
					} else if(startMinecartRidingCoordinate.getDistanceSquared(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) >= 1000000.0D)
					{
						addStat(AchievementList.onARail, 1);
					}
				} else if(ridingEntity instanceof EntityBoat)
				{
					addStat(StatList.distanceByBoatStat, var7);
				} else if(ridingEntity instanceof EntityPig)
				{
					addStat(StatList.distanceByPigStat, var7);
				}
			}
		}
	}
	
	public void addMovementStat(double par1, double par3, double par5)
	{
		if(ridingEntity == null)
		{
			int var7;
			if(isInsideOfMaterial(Material.water))
			{
				var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0F);
				if(var7 > 0)
				{
					addStat(StatList.distanceDoveStat, var7);
					addExhaustion(0.015F * var7 * 0.01F);
				}
			} else if(isInWater())
			{
				var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0F);
				if(var7 > 0)
				{
					addStat(StatList.distanceSwumStat, var7);
					addExhaustion(0.015F * var7 * 0.01F);
				}
			} else if(isOnLadder())
			{
				if(par3 > 0.0D)
				{
					addStat(StatList.distanceClimbedStat, (int) Math.round(par3 * 100.0D));
				}
			} else if(onGround)
			{
				var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0F);
				if(var7 > 0)
				{
					addStat(StatList.distanceWalkedStat, var7);
					if(isSprinting())
					{
						addExhaustion(0.099999994F * var7 * 0.01F);
					} else
					{
						addExhaustion(0.01F * var7 * 0.01F);
					}
				}
			} else
			{
				var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0F);
				if(var7 > 25)
				{
					addStat(StatList.distanceFlownStat, var7);
				}
			}
		}
	}
	
	public void addScore(int par1)
	{
		int var2 = getScore();
		dataWatcher.updateObject(18, Integer.valueOf(var2 + par1));
	}
	
	public void addStat(StatBase par1StatBase, int par2)
	{
	}
	
	@Override public void addToPlayerScore(Entity par1Entity, int par2)
	{
		addScore(par2);
		Collection var3 = getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96640_e);
		if(par1Entity instanceof EntityPlayer)
		{
			addStat(StatList.playerKillsStat, 1);
			var3.addAll(getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96639_d));
		} else
		{
			addStat(StatList.mobKillsStat, 1);
		}
		Iterator var4 = var3.iterator();
		while(var4.hasNext())
		{
			ScoreObjective var5 = (ScoreObjective) var4.next();
			Score var6 = getWorldScoreboard().func_96529_a(getEntityName(), var5);
			var6.func_96648_a();
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else if(capabilities.disableDamage && !par1DamageSource.canHarmInCreative()) return false;
		else
		{
			entityAge = 0;
			if(func_110143_aJ() <= 0.0F) return false;
			else
			{
				if(isPlayerSleeping() && !worldObj.isRemote)
				{
					wakeUpPlayer(true, true, false);
				}
				if(par1DamageSource.isDifficultyScaled())
				{
					if(worldObj.difficultySetting == 0)
					{
						par2 = 0.0F;
					}
					if(worldObj.difficultySetting == 1)
					{
						par2 = par2 / 2.0F + 1.0F;
					}
					if(worldObj.difficultySetting == 3)
					{
						par2 = par2 * 3.0F / 2.0F;
					}
				}
				if(par2 == 0.0F) return false;
				else
				{
					Entity var3 = par1DamageSource.getEntity();
					if(var3 instanceof EntityArrow && ((EntityArrow) var3).shootingEntity != null)
					{
						var3 = ((EntityArrow) var3).shootingEntity;
					}
					addStat(StatList.damageTakenStat, Math.round(par2 * 10.0F));
					return super.attackEntityFrom(par1DamageSource, par2);
				}
			}
		}
	}
	
	public void attackTargetEntityWithCurrentItem(Entity par1Entity)
	{
		if(par1Entity.canAttackWithItem())
		{
			if(!par1Entity.func_85031_j(this))
			{
				float var2 = (float) func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
				int var3 = 0;
				float var4 = 0.0F;
				if(par1Entity instanceof EntityLivingBase)
				{
					var4 = EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) par1Entity);
					var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) par1Entity);
				}
				if(isSprinting())
				{
					++var3;
				}
				if(var2 > 0.0F || var4 > 0.0F)
				{
					boolean var5 = fallDistance > 0.0F && !onGround && !isOnLadder() && !isInWater() && !this.isPotionActive(Potion.blindness) && ridingEntity == null && par1Entity instanceof EntityLivingBase;
					if(var5 && var2 > 0.0F)
					{
						var2 *= 1.5F;
					}
					var2 += var4;
					boolean var6 = false;
					int var7 = EnchantmentHelper.getFireAspectModifier(this);
					if(par1Entity instanceof EntityLivingBase && var7 > 0 && !par1Entity.isBurning())
					{
						var6 = true;
						par1Entity.setFire(1);
					}
					boolean var8 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(this), var2);
					if(var8)
					{
						if(var3 > 0)
						{
							par1Entity.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F, 0.1D, MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F);
							motionX *= 0.6D;
							motionZ *= 0.6D;
							setSprinting(false);
						}
						if(var5)
						{
							onCriticalHit(par1Entity);
						}
						if(var4 > 0.0F)
						{
							onEnchantmentCritical(par1Entity);
						}
						if(var2 >= 18.0F)
						{
							triggerAchievement(AchievementList.overkill);
						}
						func_130011_c(par1Entity);
						if(par1Entity instanceof EntityLivingBase)
						{
							EnchantmentThorns.func_92096_a(this, (EntityLivingBase) par1Entity, rand);
						}
					}
					ItemStack var9 = getCurrentEquippedItem();
					Object var10 = par1Entity;
					if(par1Entity instanceof EntityDragonPart)
					{
						IEntityMultiPart var11 = ((EntityDragonPart) par1Entity).entityDragonObj;
						if(var11 != null && var11 instanceof EntityLivingBase)
						{
							var10 = var11;
						}
					}
					if(var9 != null && var10 instanceof EntityLivingBase)
					{
						var9.hitEntity((EntityLivingBase) var10, this);
						if(var9.stackSize <= 0)
						{
							destroyCurrentEquippedItem();
						}
					}
					if(par1Entity instanceof EntityLivingBase)
					{
						addStat(StatList.damageDealtStat, Math.round(var2 * 10.0F));
						if(var7 > 0 && var8)
						{
							par1Entity.setFire(var7 * 4);
						} else if(var6)
						{
							par1Entity.extinguish();
						}
					}
					addExhaustion(0.3F);
				}
			}
		}
	}
	
	public boolean canEat(boolean par1)
	{
		return (par1 || foodStats.needFood()) && !capabilities.disableDamage;
	}
	
	public boolean canHarvestBlock(Block par1Block)
	{
		return inventory.canHarvestBlock(par1Block);
	}
	
	public boolean canPlayerEdit(int par1, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
		return capabilities.allowEdit ? true : par5ItemStack != null ? par5ItemStack.canEditBlocks() : false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return !capabilities.isFlying;
	}
	
	public void clearItemInUse()
	{
		itemInUse = null;
		itemInUseCount = 0;
		if(!worldObj.isRemote)
		{
			setEating(false);
		}
	}
	
	public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2)
	{
		if(par2)
		{
			inventory.copyInventory(par1EntityPlayer.inventory);
			setEntityHealth(par1EntityPlayer.func_110143_aJ());
			foodStats = par1EntityPlayer.foodStats;
			experienceLevel = par1EntityPlayer.experienceLevel;
			experienceTotal = par1EntityPlayer.experienceTotal;
			experience = par1EntityPlayer.experience;
			setScore(par1EntityPlayer.getScore());
			teleportDirection = par1EntityPlayer.teleportDirection;
		} else if(worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
		{
			inventory.copyInventory(par1EntityPlayer.inventory);
			experienceLevel = par1EntityPlayer.experienceLevel;
			experienceTotal = par1EntityPlayer.experienceTotal;
			experience = par1EntityPlayer.experience;
			setScore(par1EntityPlayer.getScore());
		}
		theInventoryEnderChest = par1EntityPlayer.theInventoryEnderChest;
	}
	
	protected void closeScreen()
	{
		openContainer = inventoryContainer;
	}
	
	private void collideWithPlayer(Entity par1Entity)
	{
		par1Entity.onCollideWithPlayer(this);
	}
	
	@Override protected void damageArmor(float par1)
	{
		inventory.damageArmor(par1);
	}
	
	@Override protected void damageEntity(DamageSource par1DamageSource, float par2)
	{
		if(!isEntityInvulnerable())
		{
			if(!par1DamageSource.isUnblockable() && isBlocking() && par2 > 0.0F)
			{
				par2 = (1.0F + par2) * 0.5F;
			}
			par2 = applyArmorCalculations(par1DamageSource, par2);
			par2 = applyPotionDamageCalculations(par1DamageSource, par2);
			float var3 = par2;
			par2 = Math.max(par2 - func_110139_bj(), 0.0F);
			func_110149_m(func_110139_bj() - (var3 - par2));
			if(par2 != 0.0F)
			{
				addExhaustion(par1DamageSource.getHungerDamage());
				float var4 = func_110143_aJ();
				setEntityHealth(func_110143_aJ() - par2);
				func_110142_aN().func_94547_a(par1DamageSource, var4, par2);
			}
		}
	}
	
	public void destroyCurrentEquippedItem()
	{
		inventory.setInventorySlotContents(inventory.currentItem, (ItemStack) null);
	}
	
	public void displayGUIAnvil(int par1, int par2, int par3)
	{
	}
	
	public void displayGUIBeacon(TileEntityBeacon par1TileEntityBeacon)
	{
	}
	
	public void displayGUIBook(ItemStack par1ItemStack)
	{
	}
	
	public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
	{
	}
	
	public void displayGUIChest(IInventory par1IInventory)
	{
	}
	
	public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
	{
	}
	
	public void displayGUIEditSign(TileEntity par1TileEntity)
	{
	}
	
	public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str)
	{
	}
	
	public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
	{
	}
	
	public void displayGUIHopper(TileEntityHopper par1TileEntityHopper)
	{
	}
	
	public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper)
	{
	}
	
	public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str)
	{
	}
	
	public void displayGUIWorkbench(int par1, int par2, int par3)
	{
	}
	
	public EntityItem dropOneItem(boolean par1)
	{
		return dropPlayerItemWithRandomChoice(inventory.decrStackSize(inventory.currentItem, par1 && inventory.getCurrentItem() != null ? inventory.getCurrentItem().stackSize : 1), false);
	}
	
	public EntityItem dropPlayerItem(ItemStack par1ItemStack)
	{
		return dropPlayerItemWithRandomChoice(par1ItemStack, false);
	}
	
	public EntityItem dropPlayerItemWithRandomChoice(ItemStack par1ItemStack, boolean par2)
	{
		if(par1ItemStack == null) return null;
		else if(par1ItemStack.stackSize == 0) return null;
		else
		{
			EntityItem var3 = new EntityItem(worldObj, posX, posY - 0.30000001192092896D + getEyeHeight(), posZ, par1ItemStack);
			var3.delayBeforeCanPickup = 40;
			float var4 = 0.1F;
			float var5;
			if(par2)
			{
				var5 = rand.nextFloat() * 0.5F;
				float var6 = rand.nextFloat() * (float) Math.PI * 2.0F;
				var3.motionX = -MathHelper.sin(var6) * var5;
				var3.motionZ = MathHelper.cos(var6) * var5;
				var3.motionY = 0.20000000298023224D;
			} else
			{
				var4 = 0.3F;
				var3.motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var4;
				var3.motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var4;
				var3.motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * var4 + 0.1F;
				var4 = 0.02F;
				var5 = rand.nextFloat() * (float) Math.PI * 2.0F;
				var4 *= rand.nextFloat();
				var3.motionX += Math.cos(var5) * var4;
				var3.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				var3.motionZ += Math.sin(var5) * var4;
			}
			joinEntityItemWithWorld(var3);
			addStat(StatList.dropStat, 1);
			return var3;
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Float.valueOf(0.0F));
		dataWatcher.addObject(18, Integer.valueOf(0));
	}
	
	@Override protected void fall(float par1)
	{
		if(!capabilities.allowFlying)
		{
			if(par1 >= 2.0F)
			{
				addStat(StatList.distanceFallenStat, (int) Math.round(par1 * 100.0D));
			}
			super.fall(par1);
		}
	}
	
	@Override public float func_110139_bj()
	{
		return getDataWatcher().func_111145_d(17);
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
	}
	
	@Override public void func_110149_m(float par1)
	{
		if(par1 < 0.0F)
		{
			par1 = 0.0F;
		}
		getDataWatcher().updateObject(17, Float.valueOf(par1));
	}
	
	public void func_110298_a(EntityHorse par1EntityHorse, IInventory par2IInventory)
	{
	}
	
	@Override public World func_130014_f_()
	{
		return worldObj;
	}
	
	private void func_71013_b(int par1)
	{
		field_71079_bU = 0.0F;
		field_71089_bV = 0.0F;
		switch(par1)
		{
			case 0:
				field_71089_bV = -1.8F;
				break;
			case 1:
				field_71079_bU = 1.8F;
				break;
			case 2:
				field_71089_bV = 1.8F;
				break;
			case 3:
				field_71079_bU = -1.8F;
		}
	}
	
	public float func_82243_bO()
	{
		int var1 = 0;
		ItemStack[] var2 = inventory.armorInventory;
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			ItemStack var5 = var2[var4];
			if(var5 != null)
			{
				++var1;
			}
		}
		return (float) var1 / (float) inventory.armorInventory.length;
	}
	
	@Override public boolean func_96092_aw()
	{
		return !capabilities.isFlying;
	}
	
	public boolean func_96122_a(EntityPlayer par1EntityPlayer)
	{
		Team var2 = getTeam();
		Team var3 = par1EntityPlayer.getTeam();
		return var2 == null ? true : !var2.func_142054_a(var3) ? true : var2.func_96665_g();
	}
	
	@Override public boolean func_98034_c(EntityPlayer par1EntityPlayer)
	{
		if(!isInvisible()) return false;
		else
		{
			Team var2 = getTeam();
			return var2 == null || par1EntityPlayer == null || par1EntityPlayer.getTeam() != var2 || !var2.func_98297_h();
		}
	}
	
	@Override public float getAIMoveSpeed()
	{
		return (float) func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
	}
	
	@Override public boolean getAlwaysRenderNameTagForRender()
	{
		return true;
	}
	
	public ChunkCoordinates getBedLocation()
	{
		return spawnChunk;
	}
	
	public float getBedOrientationInDegrees()
	{
		if(playerLocation != null)
		{
			int var1 = worldObj.getBlockMetadata(playerLocation.posX, playerLocation.posY, playerLocation.posZ);
			int var2 = BlockDirectional.getDirection(var1);
			switch(var2)
			{
				case 0:
					return 90.0F;
				case 1:
					return 0.0F;
				case 2:
					return 270.0F;
				case 3:
					return 180.0F;
			}
		}
		return 0.0F;
	}
	
	@Override public String getCommandSenderName()
	{
		return username;
	}
	
	public ItemStack getCurrentArmor(int par1)
	{
		return inventory.armorItemInSlot(par1);
	}
	
	public ItemStack getCurrentEquippedItem()
	{
		return inventory.getCurrentItem();
	}
	
	@Override public ItemStack getCurrentItemOrArmor(int par1)
	{
		return par1 == 0 ? inventory.getCurrentItem() : inventory.armorInventory[par1 - 1];
	}
	
	public float getCurrentPlayerStrVsBlock(Block par1Block, boolean par2)
	{
		float var3 = inventory.getStrVsBlock(par1Block);
		if(var3 > 1.0F)
		{
			int var4 = EnchantmentHelper.getEfficiencyModifier(this);
			ItemStack var5 = inventory.getCurrentItem();
			if(var4 > 0 && var5 != null)
			{
				float var6 = var4 * var4 + 1;
				if(!var5.canHarvestBlock(par1Block) && var3 <= 1.0F)
				{
					var3 += var6 * 0.08F;
				} else
				{
					var3 += var6;
				}
			}
		}
		if(this.isPotionActive(Potion.digSpeed))
		{
			var3 *= 1.0F + (getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
		}
		if(this.isPotionActive(Potion.digSlowdown))
		{
			var3 *= 1.0F - (getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2F;
		}
		if(isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this))
		{
			var3 /= 5.0F;
		}
		if(!onGround)
		{
			var3 /= 5.0F;
		}
		return var3;
	}
	
	@Override public String getEntityName()
	{
		return username;
	}
	
	@Override protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		if(worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) return 0;
		else
		{
			int var2 = experienceLevel * 7;
			return var2 > 100 ? 100 : var2;
		}
	}
	
	@Override public float getEyeHeight()
	{
		return 0.12F;
	}
	
	public FoodStats getFoodStats()
	{
		return foodStats;
	}
	
	@Override public ItemStack getHeldItem()
	{
		return inventory.getCurrentItem();
	}
	
	public boolean getHideCape()
	{
		return this.getHideCape(1);
	}
	
	protected boolean getHideCape(int par1)
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1 << par1) != 0;
	}
	
	public InventoryEnderChest getInventoryEnderChest()
	{
		return theInventoryEnderChest;
	}
	
	@Override public Icon getItemIcon(ItemStack par1ItemStack, int par2)
	{
		Icon var3 = super.getItemIcon(par1ItemStack, par2);
		if(par1ItemStack.itemID == Item.fishingRod.itemID && fishEntity != null)
		{
			var3 = Item.fishingRod.func_94597_g();
		} else
		{
			if(par1ItemStack.getItem().requiresMultipleRenderPasses()) return par1ItemStack.getItem().getIconFromDamageForRenderPass(par1ItemStack.getItemDamage(), par2);
			if(itemInUse != null && par1ItemStack.itemID == Item.bow.itemID)
			{
				int var4 = par1ItemStack.getMaxItemUseDuration() - itemInUseCount;
				if(var4 >= 18) return Item.bow.getItemIconForUseDuration(2);
				if(var4 > 13) return Item.bow.getItemIconForUseDuration(1);
				if(var4 > 0) return Item.bow.getItemIconForUseDuration(0);
			}
		}
		return var3;
	}
	
	public ItemStack getItemInUse()
	{
		return itemInUse;
	}
	
	public int getItemInUseCount()
	{
		return itemInUseCount;
	}
	
	public int getItemInUseDuration()
	{
		return isUsingItem() ? itemInUse.getMaxItemUseDuration() - itemInUseCount : 0;
	}
	
	@Override public ItemStack[] getLastActiveItems()
	{
		return inventory.armorInventory;
	}
	
	@Override public int getMaxInPortalTime()
	{
		return capabilities.disableDamage ? 0 : 80;
	}
	
	@Override public int getPortalCooldown()
	{
		return 10;
	}
	
	public int getScore()
	{
		return dataWatcher.getWatchableObjectInt(18);
	}
	
	public int getSleepTimer()
	{
		return sleepTimer;
	}
	
	@Override public Team getTeam()
	{
		return getWorldScoreboard().getPlayersTeam(username);
	}
	
	@Override public int getTotalArmorValue()
	{
		return inventory.getTotalArmorValue();
	}
	
	@Override public String getTranslatedEntityName()
	{
		return ScorePlayerTeam.formatPlayerName(getTeam(), username);
	}
	
	public Scoreboard getWorldScoreboard()
	{
		return worldObj.getScoreboard();
	}
	
	@Override public double getYOffset()
	{
		return yOffset - 0.5F;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 9)
		{
			onItemUseFinish();
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	public boolean interactWith(Entity par1Entity)
	{
		ItemStack var2 = getCurrentEquippedItem();
		ItemStack var3 = var2 != null ? var2.copy() : null;
		if(!par1Entity.func_130002_c(this))
		{
			if(var2 != null && par1Entity instanceof EntityLivingBase)
			{
				if(capabilities.isCreativeMode)
				{
					var2 = var3;
				}
				if(var2.func_111282_a(this, (EntityLivingBase) par1Entity))
				{
					if(var2.stackSize <= 0 && !capabilities.isCreativeMode)
					{
						destroyCurrentEquippedItem();
					}
					return true;
				}
			}
			return false;
		} else
		{
			if(var2 != null && var2 == getCurrentEquippedItem())
			{
				if(var2.stackSize <= 0 && !capabilities.isCreativeMode)
				{
					destroyCurrentEquippedItem();
				} else if(var2.stackSize < var3.stackSize && capabilities.isCreativeMode)
				{
					var2.stackSize = var3.stackSize;
				}
			}
			return true;
		}
	}
	
	public boolean isBlocking()
	{
		return isUsingItem() && Item.itemsList[itemInUse.itemID].getItemUseAction(itemInUse) == EnumAction.block;
	}
	
	public boolean isCurrentToolAdventureModeExempt(int par1, int par2, int par3)
	{
		if(capabilities.allowEdit) return true;
		else
		{
			int var4 = worldObj.getBlockId(par1, par2, par3);
			if(var4 > 0)
			{
				Block var5 = Block.blocksList[var4];
				if(var5.blockMaterial.isAdventureModeExempt()) return true;
				if(getCurrentEquippedItem() != null)
				{
					ItemStack var6 = getCurrentEquippedItem();
					if(var6.canHarvestBlock(var5) || var6.getStrVsBlock(var5) > 1.0F) return true;
				}
			}
			return false;
		}
	}
	
	@Override public boolean isEntityInsideOpaqueBlock()
	{
		return !sleeping && super.isEntityInsideOpaqueBlock();
	}
	
	private boolean isInBed()
	{
		return worldObj.getBlockId(playerLocation.posX, playerLocation.posY, playerLocation.posZ) == Block.bed.blockID;
	}
	
	@Override protected boolean isMovementBlocked()
	{
		return func_110143_aJ() <= 0.0F || isPlayerSleeping();
	}
	
	@Override protected boolean isPlayer()
	{
		return true;
	}
	
	public boolean isPlayerFullyAsleep()
	{
		return sleeping && sleepTimer >= 100;
	}
	
	@Override public boolean isPlayerSleeping()
	{
		return sleeping;
	}
	
	public boolean isSpawnForced()
	{
		return spawnForced;
	}
	
	public boolean isUsingItem()
	{
		return itemInUse != null;
	}
	
	protected void joinEntityItemWithWorld(EntityItem par1EntityItem)
	{
		worldObj.spawnEntityInWorld(par1EntityItem);
	}
	
	@Override protected void jump()
	{
		super.jump();
		addStat(StatList.jumpStat, 1);
		if(isSprinting())
		{
			addExhaustion(0.8F);
		} else
		{
			addExhaustion(0.2F);
		}
	}
	
	@Override public void mountEntity(Entity par1Entity)
	{
		if(ridingEntity != null && par1Entity == null)
		{
			if(!worldObj.isRemote)
			{
				func_110145_l(ridingEntity);
			}
			if(ridingEntity != null)
			{
				ridingEntity.riddenByEntity = null;
			}
			ridingEntity = null;
		} else
		{
			super.mountEntity(par1Entity);
		}
	}
	
	@Override public void moveEntityWithHeading(float par1, float par2)
	{
		double var3 = posX;
		double var5 = posY;
		double var7 = posZ;
		if(capabilities.isFlying && ridingEntity == null)
		{
			double var9 = motionY;
			float var11 = jumpMovementFactor;
			jumpMovementFactor = capabilities.getFlySpeed();
			super.moveEntityWithHeading(par1, par2);
			motionY = var9 * 0.6D;
			jumpMovementFactor = var11;
		} else
		{
			super.moveEntityWithHeading(par1, par2);
		}
		addMovementStat(posX - var3, posY - var5, posZ - var7);
	}
	
	public void onCriticalHit(Entity par1Entity)
	{
	}
	
	@Override public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		setSize(0.2F, 0.2F);
		setPosition(posX, posY, posZ);
		motionY = 0.10000000149011612D;
		if(username.equals("Notch"))
		{
			dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
		}
		if(!worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
		{
			inventory.dropAllItems();
		}
		if(par1DamageSource != null)
		{
			motionX = -MathHelper.cos((attackedAtYaw + rotationYaw) * (float) Math.PI / 180.0F) * 0.1F;
			motionZ = -MathHelper.sin((attackedAtYaw + rotationYaw) * (float) Math.PI / 180.0F) * 0.1F;
		} else
		{
			motionX = motionZ = 0.0D;
		}
		yOffset = 0.1F;
		addStat(StatList.deathsStat, 1);
	}
	
	public void onEnchantmentCritical(Entity par1Entity)
	{
	}
	
	protected void onItemUseFinish()
	{
		if(itemInUse != null)
		{
			updateItemUse(itemInUse, 16);
			int var1 = itemInUse.stackSize;
			ItemStack var2 = itemInUse.onFoodEaten(worldObj, this);
			if(var2 != itemInUse || var2 != null && var2.stackSize != var1)
			{
				inventory.mainInventory[inventory.currentItem] = var2;
				if(var2.stackSize == 0)
				{
					inventory.mainInventory[inventory.currentItem] = null;
				}
			}
			clearItemInUse();
		}
	}
	
	@Override public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		if(par1EntityLivingBase instanceof IMob)
		{
			triggerAchievement(AchievementList.killEnemy);
		}
	}
	
	@Override public void onLivingUpdate()
	{
		if(flyToggleTimer > 0)
		{
			--flyToggleTimer;
		}
		if(worldObj.difficultySetting == 0 && func_110143_aJ() < func_110138_aP() && worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && ticksExisted % 20 * 12 == 0)
		{
			heal(1.0F);
		}
		inventory.decrementAnimations();
		prevCameraYaw = cameraYaw;
		super.onLivingUpdate();
		AttributeInstance var1 = func_110148_a(SharedMonsterAttributes.field_111263_d);
		if(!worldObj.isRemote)
		{
			var1.func_111128_a(capabilities.getWalkSpeed());
		}
		jumpMovementFactor = speedInAir;
		if(isSprinting())
		{
			jumpMovementFactor = (float) (jumpMovementFactor + speedInAir * 0.3D);
		}
		setAIMoveSpeed((float) var1.func_111126_e());
		float var2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		float var3 = (float) Math.atan(-motionY * 0.20000000298023224D) * 15.0F;
		if(var2 > 0.1F)
		{
			var2 = 0.1F;
		}
		if(!onGround || func_110143_aJ() <= 0.0F)
		{
			var2 = 0.0F;
		}
		if(onGround || func_110143_aJ() <= 0.0F)
		{
			var3 = 0.0F;
		}
		cameraYaw += (var2 - cameraYaw) * 0.4F;
		cameraPitch += (var3 - cameraPitch) * 0.8F;
		if(func_110143_aJ() > 0.0F)
		{
			AxisAlignedBB var4 = null;
			if(ridingEntity != null && !ridingEntity.isDead)
			{
				var4 = boundingBox.func_111270_a(ridingEntity.boundingBox).expand(1.0D, 0.0D, 1.0D);
			} else
			{
				var4 = boundingBox.expand(1.0D, 0.5D, 1.0D);
			}
			List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, var4);
			if(var5 != null)
			{
				for(int var6 = 0; var6 < var5.size(); ++var6)
				{
					Entity var7 = (Entity) var5.get(var6);
					if(!var7.isDead)
					{
						collideWithPlayer(var7);
					}
				}
			}
		}
	}
	
	@Override public void onUpdate()
	{
		if(itemInUse != null)
		{
			ItemStack var1 = inventory.getCurrentItem();
			if(var1 == itemInUse)
			{
				if(itemInUseCount <= 25 && itemInUseCount % 4 == 0)
				{
					updateItemUse(var1, 5);
				}
				if(--itemInUseCount == 0 && !worldObj.isRemote)
				{
					onItemUseFinish();
				}
			} else
			{
				clearItemInUse();
			}
		}
		if(xpCooldown > 0)
		{
			--xpCooldown;
		}
		if(isPlayerSleeping())
		{
			++sleepTimer;
			if(sleepTimer > 100)
			{
				sleepTimer = 100;
			}
			if(!worldObj.isRemote)
			{
				if(!isInBed())
				{
					wakeUpPlayer(true, true, false);
				} else if(worldObj.isDaytime())
				{
					wakeUpPlayer(false, true, true);
				}
			}
		} else if(sleepTimer > 0)
		{
			++sleepTimer;
			if(sleepTimer >= 110)
			{
				sleepTimer = 0;
			}
		}
		super.onUpdate();
		if(!worldObj.isRemote && openContainer != null && !openContainer.canInteractWith(this))
		{
			closeScreen();
			openContainer = inventoryContainer;
		}
		if(isBurning() && capabilities.disableDamage)
		{
			extinguish();
		}
		field_71091_bM = field_71094_bP;
		field_71096_bN = field_71095_bQ;
		field_71097_bO = field_71085_bR;
		double var9 = posX - field_71094_bP;
		double var3 = posY - field_71095_bQ;
		double var5 = posZ - field_71085_bR;
		double var7 = 10.0D;
		if(var9 > var7)
		{
			field_71091_bM = field_71094_bP = posX;
		}
		if(var5 > var7)
		{
			field_71097_bO = field_71085_bR = posZ;
		}
		if(var3 > var7)
		{
			field_71096_bN = field_71095_bQ = posY;
		}
		if(var9 < -var7)
		{
			field_71091_bM = field_71094_bP = posX;
		}
		if(var5 < -var7)
		{
			field_71097_bO = field_71085_bR = posZ;
		}
		if(var3 < -var7)
		{
			field_71096_bN = field_71095_bQ = posY;
		}
		field_71094_bP += var9 * 0.25D;
		field_71085_bR += var5 * 0.25D;
		field_71095_bQ += var3 * 0.25D;
		addStat(StatList.minutesPlayedStat, 1);
		if(ridingEntity == null)
		{
			startMinecartRidingCoordinate = null;
		}
		if(!worldObj.isRemote)
		{
			foodStats.onUpdate(this);
		}
	}
	
	@Override public void playSound(String par1Str, float par2, float par3)
	{
		worldObj.playSoundToNearExcept(this, par1Str, par2, par3);
	}
	
	@Override public void preparePlayerToSpawn()
	{
		yOffset = 1.62F;
		setSize(0.6F, 1.8F);
		super.preparePlayerToSpawn();
		setEntityHealth(func_110138_aP());
		deathTime = 0;
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Inventory");
		inventory.readFromNBT(var2);
		inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
		sleeping = par1NBTTagCompound.getBoolean("Sleeping");
		sleepTimer = par1NBTTagCompound.getShort("SleepTimer");
		experience = par1NBTTagCompound.getFloat("XpP");
		experienceLevel = par1NBTTagCompound.getInteger("XpLevel");
		experienceTotal = par1NBTTagCompound.getInteger("XpTotal");
		setScore(par1NBTTagCompound.getInteger("Score"));
		if(sleeping)
		{
			playerLocation = new ChunkCoordinates(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
			wakeUpPlayer(true, true, false);
		}
		if(par1NBTTagCompound.hasKey("SpawnX") && par1NBTTagCompound.hasKey("SpawnY") && par1NBTTagCompound.hasKey("SpawnZ"))
		{
			spawnChunk = new ChunkCoordinates(par1NBTTagCompound.getInteger("SpawnX"), par1NBTTagCompound.getInteger("SpawnY"), par1NBTTagCompound.getInteger("SpawnZ"));
			spawnForced = par1NBTTagCompound.getBoolean("SpawnForced");
		}
		foodStats.readNBT(par1NBTTagCompound);
		capabilities.readCapabilitiesFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("EnderItems"))
		{
			NBTTagList var3 = par1NBTTagCompound.getTagList("EnderItems");
			theInventoryEnderChest.loadInventoryFromNBT(var3);
		}
	}
	
	protected void resetHeight()
	{
		yOffset = 1.62F;
	}
	
	public void respawnPlayer()
	{
	}
	
	public void sendPlayerAbilities()
	{
	}
	
	@Override public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
	{
		inventory.armorInventory[par1] = par2ItemStack;
	}
	
	@Override public void setDead()
	{
		super.setDead();
		inventoryContainer.onContainerClosed(this);
		if(openContainer != null)
		{
			openContainer.onContainerClosed(this);
		}
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
	}
	
	protected void setHideCape(int par1, boolean par2)
	{
		byte var3 = dataWatcher.getWatchableObjectByte(16);
		if(par2)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var3 | 1 << par1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var3 & ~(1 << par1))));
		}
	}
	
	@Override public void setInWeb()
	{
		if(!capabilities.isFlying)
		{
			super.setInWeb();
		}
	}
	
	public void setItemInUse(ItemStack par1ItemStack, int par2)
	{
		if(par1ItemStack != itemInUse)
		{
			itemInUse = par1ItemStack;
			itemInUseCount = par2;
			if(!worldObj.isRemote)
			{
				setEating(true);
			}
		}
	}
	
	public void setScore(int par1)
	{
		dataWatcher.updateObject(18, Integer.valueOf(par1));
	}
	
	public void setSpawnChunk(ChunkCoordinates par1ChunkCoordinates, boolean par2)
	{
		if(par1ChunkCoordinates != null)
		{
			spawnChunk = new ChunkCoordinates(par1ChunkCoordinates);
			spawnForced = par2;
		} else
		{
			spawnChunk = null;
			spawnForced = false;
		}
	}
	
	public boolean shouldHeal()
	{
		return func_110143_aJ() > 0.0F && func_110143_aJ() < func_110138_aP();
	}
	
	public EnumStatus sleepInBedAt(int par1, int par2, int par3)
	{
		if(!worldObj.isRemote)
		{
			if(isPlayerSleeping() || !isEntityAlive()) return EnumStatus.OTHER_PROBLEM;
			if(!worldObj.provider.isSurfaceWorld()) return EnumStatus.NOT_POSSIBLE_HERE;
			if(worldObj.isDaytime()) return EnumStatus.NOT_POSSIBLE_NOW;
			if(Math.abs(posX - par1) > 3.0D || Math.abs(posY - par2) > 2.0D || Math.abs(posZ - par3) > 3.0D) return EnumStatus.TOO_FAR_AWAY;
			double var4 = 8.0D;
			double var6 = 5.0D;
			List var8 = worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB(par1 - var4, par2 - var6, par3 - var4, par1 + var4, par2 + var6, par3 + var4));
			if(!var8.isEmpty()) return EnumStatus.NOT_SAFE;
		}
		if(isRiding())
		{
			mountEntity((Entity) null);
		}
		setSize(0.2F, 0.2F);
		yOffset = 0.2F;
		if(worldObj.blockExists(par1, par2, par3))
		{
			int var9 = worldObj.getBlockMetadata(par1, par2, par3);
			int var5 = BlockDirectional.getDirection(var9);
			float var10 = 0.5F;
			float var7 = 0.5F;
			switch(var5)
			{
				case 0:
					var7 = 0.9F;
					break;
				case 1:
					var10 = 0.1F;
					break;
				case 2:
					var7 = 0.1F;
					break;
				case 3:
					var10 = 0.9F;
			}
			func_71013_b(var5);
			setPosition(par1 + var10, par2 + 0.9375F, par3 + var7);
		} else
		{
			setPosition(par1 + 0.5F, par2 + 0.9375F, par3 + 0.5F);
		}
		sleeping = true;
		sleepTimer = 0;
		playerLocation = new ChunkCoordinates(par1, par2, par3);
		motionX = motionZ = motionY = 0.0D;
		if(!worldObj.isRemote)
		{
			worldObj.updateAllPlayersSleepingFlag();
		}
		return EnumStatus.OK;
	}
	
	public void stopUsingItem()
	{
		if(itemInUse != null)
		{
			itemInUse.onPlayerStoppedUsing(worldObj, this, itemInUseCount);
		}
		clearItemInUse();
	}
	
	public void triggerAchievement(StatBase par1StatBase)
	{
		addStat(par1StatBase, 1);
	}
	
	@Override protected void updateEntityActionState()
	{
		super.updateEntityActionState();
		updateArmSwingProgress();
	}
	
	protected void updateItemUse(ItemStack par1ItemStack, int par2)
	{
		if(par1ItemStack.getItemUseAction() == EnumAction.drink)
		{
			playSound("random.drink", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if(par1ItemStack.getItemUseAction() == EnumAction.eat)
		{
			for(int var3 = 0; var3 < par2; ++var3)
			{
				Vec3 var4 = worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
				var4.rotateAroundX(-rotationPitch * (float) Math.PI / 180.0F);
				var4.rotateAroundY(-rotationYaw * (float) Math.PI / 180.0F);
				Vec3 var5 = worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.3D, -rand.nextFloat() * 0.6D - 0.3D, 0.6D);
				var5.rotateAroundX(-rotationPitch * (float) Math.PI / 180.0F);
				var5.rotateAroundY(-rotationYaw * (float) Math.PI / 180.0F);
				var5 = var5.addVector(posX, posY + getEyeHeight(), posZ);
				worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord);
			}
			playSound("random.eat", 0.5F + 0.5F * rand.nextInt(2), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
		}
	}
	
	@Override public void updateRidden()
	{
		if(!worldObj.isRemote && isSneaking())
		{
			mountEntity((Entity) null);
			setSneaking(false);
		} else
		{
			double var1 = posX;
			double var3 = posY;
			double var5 = posZ;
			float var7 = rotationYaw;
			float var8 = rotationPitch;
			super.updateRidden();
			prevCameraYaw = cameraYaw;
			cameraYaw = 0.0F;
			addMountedMovementStat(posX - var1, posY - var3, posZ - var5);
			if(ridingEntity instanceof EntityPig)
			{
				rotationPitch = var8;
				rotationYaw = var7;
				renderYawOffset = ((EntityPig) ridingEntity).renderYawOffset;
			}
		}
	}
	
	public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
	{
		setSize(0.6F, 1.8F);
		resetHeight();
		ChunkCoordinates var4 = playerLocation;
		ChunkCoordinates var5 = playerLocation;
		if(var4 != null && worldObj.getBlockId(var4.posX, var4.posY, var4.posZ) == Block.bed.blockID)
		{
			BlockBed.setBedOccupied(worldObj, var4.posX, var4.posY, var4.posZ, false);
			var5 = BlockBed.getNearestEmptyChunkCoordinates(worldObj, var4.posX, var4.posY, var4.posZ, 0);
			if(var5 == null)
			{
				var5 = new ChunkCoordinates(var4.posX, var4.posY + 1, var4.posZ);
			}
			setPosition(var5.posX + 0.5F, var5.posY + yOffset + 0.1F, var5.posZ + 0.5F);
		}
		sleeping = false;
		if(!worldObj.isRemote && par2)
		{
			worldObj.updateAllPlayersSleepingFlag();
		}
		if(par1)
		{
			sleepTimer = 0;
		} else
		{
			sleepTimer = 100;
		}
		if(par3)
		{
			setSpawnChunk(playerLocation, false);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setTag("Inventory", inventory.writeToNBT(new NBTTagList()));
		par1NBTTagCompound.setInteger("SelectedItemSlot", inventory.currentItem);
		par1NBTTagCompound.setBoolean("Sleeping", sleeping);
		par1NBTTagCompound.setShort("SleepTimer", (short) sleepTimer);
		par1NBTTagCompound.setFloat("XpP", experience);
		par1NBTTagCompound.setInteger("XpLevel", experienceLevel);
		par1NBTTagCompound.setInteger("XpTotal", experienceTotal);
		par1NBTTagCompound.setInteger("Score", getScore());
		if(spawnChunk != null)
		{
			par1NBTTagCompound.setInteger("SpawnX", spawnChunk.posX);
			par1NBTTagCompound.setInteger("SpawnY", spawnChunk.posY);
			par1NBTTagCompound.setInteger("SpawnZ", spawnChunk.posZ);
			par1NBTTagCompound.setBoolean("SpawnForced", spawnForced);
		}
		foodStats.writeNBT(par1NBTTagCompound);
		capabilities.writeCapabilitiesToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setTag("EnderItems", theInventoryEnderChest.saveInventoryToNBT());
	}
	
	public int xpBarCap()
	{
		return experienceLevel >= 30 ? 62 + (experienceLevel - 30) * 7 : experienceLevel >= 15 ? 17 + (experienceLevel - 15) * 3 : 17;
	}
	
	public static ChunkCoordinates verifyRespawnCoordinates(World par0World, ChunkCoordinates par1ChunkCoordinates, boolean par2)
	{
		IChunkProvider var3 = par0World.getChunkProvider();
		var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
		var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
		var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
		var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
		if(par0World.getBlockId(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ) == Block.bed.blockID)
		{
			ChunkCoordinates var8 = BlockBed.getNearestEmptyChunkCoordinates(par0World, par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ, 0);
			return var8;
		} else
		{
			Material var4 = par0World.getBlockMaterial(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
			Material var5 = par0World.getBlockMaterial(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY + 1, par1ChunkCoordinates.posZ);
			boolean var6 = !var4.isSolid() && !var4.isLiquid();
			boolean var7 = !var5.isSolid() && !var5.isLiquid();
			return par2 && var6 && var7 ? par1ChunkCoordinates : null;
		}
	}
}
