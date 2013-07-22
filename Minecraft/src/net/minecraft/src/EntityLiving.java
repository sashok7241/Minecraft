package net.minecraft.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class EntityLiving extends Entity
{
	private static final float[] enchantmentProbability = new float[] { 0.0F, 0.0F, 0.1F, 0.2F };
	private static final float[] armorEnchantmentProbability = new float[] { 0.0F, 0.0F, 0.25F, 0.5F };
	private static final float[] armorProbability = new float[] { 0.0F, 0.0F, 0.05F, 0.07F };
	public static final float[] pickUpLootProability = new float[] { 0.0F, 0.1F, 0.15F, 0.45F };
	public int maxHurtResistantTime = 20;
	public float field_70769_ao;
	public float field_70770_ap;
	public float renderYawOffset = 0.0F;
	public float prevRenderYawOffset = 0.0F;
	public float rotationYawHead = 0.0F;
	public float prevRotationYawHead = 0.0F;
	protected float field_70768_au;
	protected float field_70766_av;
	protected float field_70764_aw;
	protected float field_70763_ax;
	protected boolean field_70753_ay = true;
	protected String texture = "/mob/char.png";
	protected boolean field_70740_aA = true;
	protected float field_70741_aB = 0.0F;
	protected String entityType = null;
	protected float field_70743_aD = 1.0F;
	protected int scoreValue = 0;
	protected float field_70745_aF = 0.0F;
	public float landMovementFactor = 0.1F;
	public float jumpMovementFactor = 0.02F;
	public float prevSwingProgress;
	public float swingProgress;
	protected int health = getMaxHealth();
	public int prevHealth;
	protected int carryoverDamage;
	public int livingSoundTime;
	public int hurtTime;
	public int maxHurtTime;
	public float attackedAtYaw = 0.0F;
	public int deathTime = 0;
	public int attackTime = 0;
	public float prevCameraPitch;
	public float cameraPitch;
	protected boolean dead = false;
	protected int experienceValue;
	public int field_70731_aW = -1;
	public float field_70730_aX = (float) (Math.random() * 0.8999999761581421D + 0.10000000149011612D);
	public float prevLimbYaw;
	public float limbYaw;
	public float limbSwing;
	protected EntityPlayer attackingPlayer = null;
	protected int recentlyHit = 0;
	private EntityLiving entityLivingToAttack = null;
	private int revengeTimer = 0;
	private EntityLiving lastAttackingEntity = null;
	public int arrowHitTimer = 0;
	protected HashMap activePotionsMap = new HashMap();
	private boolean potionsNeedUpdate = true;
	private int field_70748_f;
	private EntityLookHelper lookHelper;
	private EntityMoveHelper moveHelper;
	private EntityJumpHelper jumpHelper;
	private EntityBodyHelper bodyHelper;
	private PathNavigate navigator;
	protected final EntityAITasks tasks;
	protected final EntityAITasks targetTasks;
	private EntityLiving attackTarget;
	private EntitySenses senses;
	private float AIMoveSpeed;
	private ChunkCoordinates homePosition = new ChunkCoordinates(0, 0, 0);
	private float maximumHomeDistance = -1.0F;
	private ItemStack[] equipment = new ItemStack[5];
	protected float[] equipmentDropChances = new float[5];
	private ItemStack[] previousEquipment = new ItemStack[5];
	public boolean isSwingInProgress = false;
	public int swingProgressInt = 0;
	private boolean canPickUpLoot = false;
	private boolean persistenceRequired = false;
	protected final CombatTracker _combatTracker = new CombatTracker(this);
	protected int newPosRotationIncrements;
	protected double newPosX;
	protected double newPosY;
	protected double newPosZ;
	protected double newRotationYaw;
	protected double newRotationPitch;
	float field_70706_bo = 0.0F;
	protected int lastDamage = 0;
	protected int entityAge = 0;
	protected float moveStrafing;
	protected float moveForward;
	protected float randomYawVelocity;
	protected boolean isJumping = false;
	protected float defaultPitch = 0.0F;
	protected float moveSpeed = 0.7F;
	private int jumpTicks = 0;
	private Entity currentTarget;
	protected int numTicksToChaseTarget = 0;
	
	public EntityLiving(World par1World)
	{
		super(par1World);
		preventEntitySpawning = true;
		tasks = new EntityAITasks(par1World != null && par1World.theProfiler != null ? par1World.theProfiler : null);
		targetTasks = new EntityAITasks(par1World != null && par1World.theProfiler != null ? par1World.theProfiler : null);
		lookHelper = new EntityLookHelper(this);
		moveHelper = new EntityMoveHelper(this);
		jumpHelper = new EntityJumpHelper(this);
		bodyHelper = new EntityBodyHelper(this);
		navigator = new PathNavigate(this, par1World, getPathSearchRange());
		senses = new EntitySenses(this);
		field_70770_ap = (float) (Math.random() + 1.0D) * 0.01F;
		setPosition(posX, posY, posZ);
		field_70769_ao = (float) Math.random() * 12398.0F;
		rotationYaw = (float) (Math.random() * Math.PI * 2.0D);
		rotationYawHead = rotationYaw;
		for(int var2 = 0; var2 < equipmentDropChances.length; ++var2)
		{
			equipmentDropChances[var2] = 0.085F;
		}
		stepHeight = 0.5F;
	}
	
	public void addPotionEffect(PotionEffect par1PotionEffect)
	{
		if(isPotionApplicable(par1PotionEffect))
		{
			if(activePotionsMap.containsKey(Integer.valueOf(par1PotionEffect.getPotionID())))
			{
				((PotionEffect) activePotionsMap.get(Integer.valueOf(par1PotionEffect.getPotionID()))).combine(par1PotionEffect);
				onChangedPotionEffect((PotionEffect) activePotionsMap.get(Integer.valueOf(par1PotionEffect.getPotionID())));
			} else
			{
				activePotionsMap.put(Integer.valueOf(par1PotionEffect.getPotionID()), par1PotionEffect);
				onNewPotionEffect(par1PotionEffect);
			}
		}
	}
	
	protected void addRandomArmor()
	{
		if(rand.nextFloat() < armorProbability[worldObj.difficultySetting])
		{
			int var1 = rand.nextInt(2);
			float var2 = worldObj.difficultySetting == 3 ? 0.1F : 0.25F;
			if(rand.nextFloat() < 0.095F)
			{
				++var1;
			}
			if(rand.nextFloat() < 0.095F)
			{
				++var1;
			}
			if(rand.nextFloat() < 0.095F)
			{
				++var1;
			}
			for(int var3 = 3; var3 >= 0; --var3)
			{
				ItemStack var4 = getCurrentArmor(var3);
				if(var3 < 3 && rand.nextFloat() < var2)
				{
					break;
				}
				if(var4 == null)
				{
					Item var5 = getArmorItemForSlot(var3 + 1, var1);
					if(var5 != null)
					{
						setCurrentItemOrArmor(var3 + 1, new ItemStack(var5));
					}
				}
			}
		}
	}
	
	protected int applyArmorCalculations(DamageSource par1DamageSource, int par2)
	{
		if(!par1DamageSource.isUnblockable())
		{
			int var3 = 25 - getTotalArmorValue();
			int var4 = par2 * var3 + carryoverDamage;
			damageArmor(par2);
			par2 = var4 / 25;
			carryoverDamage = var4 % 25;
		}
		return par2;
	}
	
	protected int applyPotionDamageCalculations(DamageSource par1DamageSource, int par2)
	{
		int var3;
		int var4;
		int var5;
		if(this.isPotionActive(Potion.resistance))
		{
			var3 = (getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
			var4 = 25 - var3;
			var5 = par2 * var4 + carryoverDamage;
			par2 = var5 / 25;
			carryoverDamage = var5 % 25;
		}
		if(par2 <= 0) return 0;
		else
		{
			var3 = EnchantmentHelper.getEnchantmentModifierDamage(getLastActiveItems(), par1DamageSource);
			if(var3 > 20)
			{
				var3 = 20;
			}
			if(var3 > 0 && var3 <= 20)
			{
				var4 = 25 - var3;
				var5 = par2 * var4 + carryoverDamage;
				par2 = var5 / 25;
				carryoverDamage = var5 % 25;
			}
			return par2;
		}
	}
	
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		setLastAttackingEntity(par1Entity);
		return false;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else if(worldObj.isRemote) return false;
		else
		{
			entityAge = 0;
			if(health <= 0) return false;
			else if(par1DamageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) return false;
			else
			{
				if((par1DamageSource == DamageSource.anvil || par1DamageSource == DamageSource.fallingBlock) && getCurrentItemOrArmor(4) != null)
				{
					getCurrentItemOrArmor(4).damageItem(par2 * 4 + rand.nextInt(par2 * 2), this);
					par2 = (int) (par2 * 0.75F);
				}
				limbYaw = 1.5F;
				boolean var3 = true;
				if(hurtResistantTime > maxHurtResistantTime / 2.0F)
				{
					if(par2 <= lastDamage) return false;
					damageEntity(par1DamageSource, par2 - lastDamage);
					lastDamage = par2;
					var3 = false;
				} else
				{
					lastDamage = par2;
					prevHealth = health;
					hurtResistantTime = maxHurtResistantTime;
					damageEntity(par1DamageSource, par2);
					hurtTime = maxHurtTime = 10;
				}
				attackedAtYaw = 0.0F;
				Entity var4 = par1DamageSource.getEntity();
				if(var4 != null)
				{
					if(var4 instanceof EntityLiving)
					{
						setRevengeTarget((EntityLiving) var4);
					}
					if(var4 instanceof EntityPlayer)
					{
						recentlyHit = 100;
						attackingPlayer = (EntityPlayer) var4;
					} else if(var4 instanceof EntityWolf)
					{
						EntityWolf var5 = (EntityWolf) var4;
						if(var5.isTamed())
						{
							recentlyHit = 100;
							attackingPlayer = null;
						}
					}
				}
				if(var3)
				{
					worldObj.setEntityState(this, (byte) 2);
					if(par1DamageSource != DamageSource.drown)
					{
						setBeenAttacked();
					}
					if(var4 != null)
					{
						double var9 = var4.posX - posX;
						double var7;
						for(var7 = var4.posZ - posZ; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D)
						{
							var9 = (Math.random() - Math.random()) * 0.01D;
						}
						attackedAtYaw = (float) (Math.atan2(var7, var9) * 180.0D / Math.PI) - rotationYaw;
						knockBack(var4, par2, var9, var7);
					} else
					{
						attackedAtYaw = (int) (Math.random() * 2.0D) * 180;
					}
				}
				if(health <= 0)
				{
					if(var3)
					{
						playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
					}
					onDeath(par1DamageSource);
				} else if(var3)
				{
					playSound(getHurtSound(), getSoundVolume(), getSoundPitch());
				}
				return true;
			}
		}
	}
	
	public boolean canAttackClass(Class par1Class)
	{
		return EntityCreeper.class != par1Class && EntityGhast.class != par1Class;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override public boolean canBePushed()
	{
		return !isDead;
	}
	
	public boolean canBeSteered()
	{
		return false;
	}
	
	public boolean canBreatheUnderwater()
	{
		return false;
	}
	
	protected boolean canDespawn()
	{
		return true;
	}
	
	public boolean canEntityBeSeen(Entity par1Entity)
	{
		return worldObj.clip(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY + getEyeHeight(), posZ), worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY + par1Entity.getEyeHeight(), par1Entity.posZ)) == null;
	}
	
	public boolean canPickUpLoot()
	{
		return canPickUpLoot;
	}
	
	public void clearActivePotions()
	{
		Iterator var1 = activePotionsMap.keySet().iterator();
		while(var1.hasNext())
		{
			Integer var2 = (Integer) var1.next();
			PotionEffect var3 = (PotionEffect) activePotionsMap.get(var2);
			if(!worldObj.isRemote)
			{
				var1.remove();
				onFinishedPotionEffect(var3);
			}
		}
	}
	
	protected void collideWithEntity(Entity par1Entity)
	{
		par1Entity.applyEntityCollision(this);
	}
	
	protected void collideWithNearbyEntities()
	{
		List var1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
		if(var1 != null && !var1.isEmpty())
		{
			for(int var2 = 0; var2 < var1.size(); ++var2)
			{
				Entity var3 = (Entity) var1.get(var2);
				if(var3.canBePushed())
				{
					collideWithEntity(var3);
				}
			}
		}
	}
	
	protected void damageArmor(int par1)
	{
	}
	
	protected void damageEntity(DamageSource par1DamageSource, int par2)
	{
		if(!isEntityInvulnerable())
		{
			par2 = applyArmorCalculations(par1DamageSource, par2);
			par2 = applyPotionDamageCalculations(par1DamageSource, par2);
			int var3 = getHealth();
			health -= par2;
			_combatTracker.func_94547_a(par1DamageSource, var3, par2);
		}
	}
	
	protected int decreaseAirSupply(int par1)
	{
		int var2 = EnchantmentHelper.getRespiration(this);
		return var2 > 0 && rand.nextInt(var2 + 1) > 0 ? par1 : par1 - 1;
	}
	
	protected void despawnEntity()
	{
		if(!persistenceRequired)
		{
			EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, -1.0D);
			if(var1 != null)
			{
				double var2 = var1.posX - posX;
				double var4 = var1.posY - posY;
				double var6 = var1.posZ - posZ;
				double var8 = var2 * var2 + var4 * var4 + var6 * var6;
				if(canDespawn() && var8 > 16384.0D)
				{
					setDead();
				}
				if(entityAge > 600 && rand.nextInt(800) == 0 && var8 > 1024.0D && canDespawn())
				{
					setDead();
				} else if(var8 < 1024.0D)
				{
					entityAge = 0;
				}
			}
		}
	}
	
	public void detachHome()
	{
		maximumHomeDistance = -1.0F;
	}
	
	protected void dropEquipment(boolean par1, int par2)
	{
		for(int var3 = 0; var3 < getLastActiveItems().length; ++var3)
		{
			ItemStack var4 = getCurrentItemOrArmor(var3);
			boolean var5 = equipmentDropChances[var3] > 1.0F;
			if(var4 != null && (par1 || var5) && rand.nextFloat() - par2 * 0.01F < equipmentDropChances[var3])
			{
				if(!var5 && var4.isItemStackDamageable())
				{
					int var6 = Math.max(var4.getMaxDamage() - 25, 1);
					int var7 = var4.getMaxDamage() - rand.nextInt(rand.nextInt(var6) + 1);
					if(var7 > var6)
					{
						var7 = var6;
					}
					if(var7 < 1)
					{
						var7 = 1;
					}
					var4.setItemDamage(var7);
				}
				entityDropItem(var4, 0.0F);
			}
		}
	}
	
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = getDropItemId();
		if(var3 > 0)
		{
			int var4 = rand.nextInt(3);
			if(par2 > 0)
			{
				var4 += rand.nextInt(par2 + 1);
			}
			for(int var5 = 0; var5 < var4; ++var5)
			{
				dropItem(var3, 1);
			}
		}
	}
	
	protected void dropRareDrop(int par1)
	{
	}
	
	public void eatGrassBonus()
	{
	}
	
	protected void enchantEquipment()
	{
		if(getHeldItem() != null && rand.nextFloat() < enchantmentProbability[worldObj.difficultySetting])
		{
			EnchantmentHelper.addRandomEnchantment(rand, getHeldItem(), 5 + worldObj.difficultySetting * rand.nextInt(6));
		}
		for(int var1 = 0; var1 < 4; ++var1)
		{
			ItemStack var2 = getCurrentArmor(var1);
			if(var2 != null && rand.nextFloat() < armorEnchantmentProbability[worldObj.difficultySetting])
			{
				EnchantmentHelper.addRandomEnchantment(rand, var2, 5 + worldObj.difficultySetting * rand.nextInt(6));
			}
		}
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(8, Integer.valueOf(field_70748_f));
		dataWatcher.addObject(9, Byte.valueOf((byte) 0));
		dataWatcher.addObject(10, Byte.valueOf((byte) 0));
		dataWatcher.addObject(6, Byte.valueOf((byte) 0));
		dataWatcher.addObject(5, "");
	}
	
	public void faceEntity(Entity par1Entity, float par2, float par3)
	{
		double var4 = par1Entity.posX - posX;
		double var8 = par1Entity.posZ - posZ;
		double var6;
		if(par1Entity instanceof EntityLiving)
		{
			EntityLiving var10 = (EntityLiving) par1Entity;
			var6 = var10.posY + var10.getEyeHeight() - (posY + getEyeHeight());
		} else
		{
			var6 = (par1Entity.boundingBox.minY + par1Entity.boundingBox.maxY) / 2.0D - (posY + getEyeHeight());
		}
		double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
		float var13 = (float) -(Math.atan2(var6, var14) * 180.0D / Math.PI);
		rotationPitch = updateRotation(rotationPitch, var13, par3);
		rotationYaw = updateRotation(rotationYaw, var12, par2);
	}
	
	@Override protected void fall(float par1)
	{
		super.fall(par1);
		int var2 = MathHelper.ceiling_float_int(par1 - 3.0F);
		if(var2 > 0)
		{
			if(var2 > 4)
			{
				playSound("damage.fallbig", 1.0F, 1.0F);
			} else
			{
				playSound("damage.fallsmall", 1.0F, 1.0F);
			}
			attackEntityFrom(DamageSource.fall, var2);
			int var3 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - yOffset), MathHelper.floor_double(posZ));
			if(var3 > 0)
			{
				StepSound var4 = Block.blocksList[var3].stepSound;
				playSound(var4.getStepSound(), var4.getVolume() * 0.5F, var4.getPitch() * 0.75F);
			}
		}
	}
	
	public boolean func_104002_bU()
	{
		return persistenceRequired;
	}
	
	@Override public int func_82143_as()
	{
		if(getAttackTarget() == null) return 3;
		else
		{
			int var1 = (int) (health - getMaxHealth() * 0.33F);
			var1 -= (3 - worldObj.difficultySetting) * 4;
			if(var1 < 0)
			{
				var1 = 0;
			}
			return var1 + 3;
		}
	}
	
	public void func_94058_c(String par1Str)
	{
		dataWatcher.updateObject(5, par1Str);
	}
	
	public EntityLiving func_94060_bK()
	{
		return _combatTracker.func_94550_c() != null ? _combatTracker.func_94550_c() : attackingPlayer != null ? attackingPlayer : entityLivingToAttack != null ? entityLivingToAttack : null;
	}
	
	public void func_94061_f(boolean par1)
	{
		dataWatcher.updateObject(6, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
	
	public boolean func_94062_bN()
	{
		return dataWatcher.getWatchableObjectByte(6) == 1;
	}
	
	public PotionEffect getActivePotionEffect(Potion par1Potion)
	{
		return (PotionEffect) activePotionsMap.get(Integer.valueOf(par1Potion.id));
	}
	
	public Collection getActivePotionEffects()
	{
		return activePotionsMap.values();
	}
	
	public int getAge()
	{
		return entityAge;
	}
	
	public float getAIMoveSpeed()
	{
		return AIMoveSpeed;
	}
	
	public EntityLiving getAITarget()
	{
		return entityLivingToAttack;
	}
	
	public boolean getAlwaysRenderNameTagForRender()
	{
		return func_94062_bN();
	}
	
	private int getArmSwingAnimationEnd()
	{
		return this.isPotionActive(Potion.digSpeed) ? 6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1 : this.isPotionActive(Potion.digSlowdown) ? 6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6;
	}
	
	public final int getArrowCountInEntity()
	{
		return dataWatcher.getWatchableObjectByte(10);
	}
	
	public EntityLiving getAttackTarget()
	{
		return attackTarget;
	}
	
	public boolean getCanSpawnHere()
	{
		return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}
	
	public ItemStack getCurrentArmor(int par1)
	{
		return equipment[par1 + 1];
	}
	
	public ItemStack getCurrentItemOrArmor(int par1)
	{
		return equipment[par1];
	}
	
	public String getCustomNameTag()
	{
		return dataWatcher.getWatchableObjectString(5);
	}
	
	protected String getDeathSound()
	{
		return "damage.hit";
	}
	
	protected int getDropItemId()
	{
		return 0;
	}
	
	@Override public String getEntityName()
	{
		return hasCustomNameTag() ? getCustomNameTag() : super.getEntityName();
	}
	
	public EntitySenses getEntitySenses()
	{
		return senses;
	}
	
	protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		if(experienceValue > 0)
		{
			int var2 = experienceValue;
			ItemStack[] var3 = getLastActiveItems();
			for(int var4 = 0; var4 < var3.length; ++var4)
			{
				if(var3[var4] != null && equipmentDropChances[var4] <= 1.0F)
				{
					var2 += 1 + rand.nextInt(3);
				}
			}
			return var2;
		} else return experienceValue;
	}
	
	@Override public float getEyeHeight()
	{
		return height * 0.85F;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public ItemStack getHeldItem()
	{
		return equipment[0];
	}
	
	public ChunkCoordinates getHomePosition()
	{
		return homePosition;
	}
	
	protected String getHurtSound()
	{
		return "damage.hit";
	}
	
	public Icon getItemIcon(ItemStack par1ItemStack, int par2)
	{
		return par1ItemStack.getIconIndex();
	}
	
	public EntityJumpHelper getJumpHelper()
	{
		return jumpHelper;
	}
	
	@Override public ItemStack[] getLastActiveItems()
	{
		return equipment;
	}
	
	public EntityLiving getLastAttackingEntity()
	{
		return lastAttackingEntity;
	}
	
	protected String getLivingSound()
	{
		return null;
	}
	
	public Vec3 getLook(float par1)
	{
		float var2;
		float var3;
		float var4;
		float var5;
		if(par1 == 1.0F)
		{
			var2 = MathHelper.cos(-rotationYaw * 0.017453292F - (float) Math.PI);
			var3 = MathHelper.sin(-rotationYaw * 0.017453292F - (float) Math.PI);
			var4 = -MathHelper.cos(-rotationPitch * 0.017453292F);
			var5 = MathHelper.sin(-rotationPitch * 0.017453292F);
			return worldObj.getWorldVec3Pool().getVecFromPool(var3 * var4, var5, var2 * var4);
		} else
		{
			var2 = prevRotationPitch + (rotationPitch - prevRotationPitch) * par1;
			var3 = prevRotationYaw + (rotationYaw - prevRotationYaw) * par1;
			var4 = MathHelper.cos(-var3 * 0.017453292F - (float) Math.PI);
			var5 = MathHelper.sin(-var3 * 0.017453292F - (float) Math.PI);
			float var6 = -MathHelper.cos(-var2 * 0.017453292F);
			float var7 = MathHelper.sin(-var2 * 0.017453292F);
			return worldObj.getWorldVec3Pool().getVecFromPool(var5 * var6, var7, var4 * var6);
		}
	}
	
	public EntityLookHelper getLookHelper()
	{
		return lookHelper;
	}
	
	@Override public Vec3 getLookVec()
	{
		return getLook(1.0F);
	}
	
	public abstract int getMaxHealth();
	
	public float getMaximumHomeDistance()
	{
		return maximumHomeDistance;
	}
	
	public int getMaxSpawnedInChunk()
	{
		return 4;
	}
	
	public EntityMoveHelper getMoveHelper()
	{
		return moveHelper;
	}
	
	public PathNavigate getNavigator()
	{
		return navigator;
	}
	
	protected int getPathSearchRange()
	{
		return 16;
	}
	
	public Vec3 getPosition(float par1)
	{
		if(par1 == 1.0F) return worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
		else
		{
			double var2 = prevPosX + (posX - prevPosX) * par1;
			double var4 = prevPosY + (posY - prevPosY) * par1;
			double var6 = prevPosZ + (posZ - prevPosZ) * par1;
			return worldObj.getWorldVec3Pool().getVecFromPool(var2, var4, var6);
		}
	}
	
	public float getRenderSizeModifier()
	{
		return 1.0F;
	}
	
	public Random getRNG()
	{
		return rand;
	}
	
	@Override public float getRotationYawHead()
	{
		return rotationYawHead;
	}
	
	protected float getSoundPitch()
	{
		return isChild() ? (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.5F : (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F;
	}
	
	protected float getSoundVolume()
	{
		return 1.0F;
	}
	
	public float getSpeedModifier()
	{
		float var1 = 1.0F;
		if(this.isPotionActive(Potion.moveSpeed))
		{
			var1 *= 1.0F + 0.2F * (getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}
		if(this.isPotionActive(Potion.moveSlowdown))
		{
			var1 *= 1.0F - 0.15F * (getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
		}
		if(var1 < 0.0F)
		{
			var1 = 0.0F;
		}
		return var1;
	}
	
	public float getSwingProgress(float par1)
	{
		float var2 = swingProgress - prevSwingProgress;
		if(var2 < 0.0F)
		{
			++var2;
		}
		return prevSwingProgress + var2 * par1;
	}
	
	public int getTalkInterval()
	{
		return 80;
	}
	
	@Override public String getTexture()
	{
		return texture;
	}
	
	public int getTotalArmorValue()
	{
		int var1 = 0;
		ItemStack[] var2 = getLastActiveItems();
		int var3 = var2.length;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			ItemStack var5 = var2[var4];
			if(var5 != null && var5.getItem() instanceof ItemArmor)
			{
				int var6 = ((ItemArmor) var5.getItem()).damageReduceAmount;
				var1 += var6;
			}
		}
		return var1;
	}
	
	public int getVerticalFaceSpeed()
	{
		return 40;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 2)
		{
			limbYaw = 1.5F;
			hurtResistantTime = maxHurtResistantTime;
			hurtTime = maxHurtTime = 10;
			attackedAtYaw = 0.0F;
			playSound(getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			attackEntityFrom(DamageSource.generic, 0);
		} else if(par1 == 3)
		{
			playSound(getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			health = 0;
			onDeath(DamageSource.generic);
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	public boolean hasCustomNameTag()
	{
		return dataWatcher.getWatchableObjectString(5).length() > 0;
	}
	
	public boolean hasHome()
	{
		return maximumHomeDistance != -1.0F;
	}
	
	public void heal(int par1)
	{
		if(health > 0)
		{
			setEntityHealth(getHealth() + par1);
			if(health > getMaxHealth())
			{
				setEntityHealth(getMaxHealth());
			}
			hurtResistantTime = maxHurtResistantTime / 2;
		}
	}
	
	public void initCreature()
	{
	}
	
	protected boolean isAIEnabled()
	{
		return false;
	}
	
	public boolean isBlocking()
	{
		return false;
	}
	
	public boolean isChild()
	{
		return false;
	}
	
	protected boolean isClientWorld()
	{
		return !worldObj.isRemote;
	}
	
	@Override public boolean isEntityAlive()
	{
		return !isDead && health > 0;
	}
	
	public boolean isEntityUndead()
	{
		return getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}
	
	protected boolean isMovementBlocked()
	{
		return health <= 0;
	}
	
	public boolean isOnLadder()
	{
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(boundingBox.minY);
		int var3 = MathHelper.floor_double(posZ);
		int var4 = worldObj.getBlockId(var1, var2, var3);
		return var4 == Block.ladder.blockID || var4 == Block.vine.blockID;
	}
	
	protected boolean isPlayer()
	{
		return false;
	}
	
	public boolean isPlayerSleeping()
	{
		return false;
	}
	
	public boolean isPotionActive(int par1)
	{
		return activePotionsMap.containsKey(Integer.valueOf(par1));
	}
	
	public boolean isPotionActive(Potion par1Potion)
	{
		return activePotionsMap.containsKey(Integer.valueOf(par1Potion.id));
	}
	
	public boolean isPotionApplicable(PotionEffect par1PotionEffect)
	{
		if(getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
		{
			int var2 = par1PotionEffect.getPotionID();
			if(var2 == Potion.regeneration.id || var2 == Potion.poison.id) return false;
		}
		return true;
	}
	
	public boolean isWithinHomeDistance(int par1, int par2, int par3)
	{
		return maximumHomeDistance == -1.0F ? true : homePosition.getDistanceSquared(par1, par2, par3) < maximumHomeDistance * maximumHomeDistance;
	}
	
	public boolean isWithinHomeDistanceCurrentPosition()
	{
		return isWithinHomeDistance(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
	}
	
	protected void jump()
	{
		motionY = 0.41999998688697815D;
		if(this.isPotionActive(Potion.jump))
		{
			motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
		}
		if(isSprinting())
		{
			float var1 = rotationYaw * 0.017453292F;
			motionX -= MathHelper.sin(var1) * 0.2F;
			motionZ += MathHelper.cos(var1) * 0.2F;
		}
		isAirBorne = true;
	}
	
	@Override protected void kill()
	{
		attackEntityFrom(DamageSource.outOfWorld, 4);
	}
	
	public void knockBack(Entity par1Entity, int par2, double par3, double par5)
	{
		isAirBorne = true;
		float var7 = MathHelper.sqrt_double(par3 * par3 + par5 * par5);
		float var8 = 0.4F;
		motionX /= 2.0D;
		motionY /= 2.0D;
		motionZ /= 2.0D;
		motionX -= par3 / var7 * var8;
		motionY += var8;
		motionZ -= par5 / var7 * var8;
		if(motionY > 0.4000000059604645D)
		{
			motionY = 0.4000000059604645D;
		}
	}
	
	public void moveEntityWithHeading(float par1, float par2)
	{
		double var9;
		if(isInWater() && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).capabilities.isFlying))
		{
			var9 = posY;
			moveFlying(par1, par2, isAIEnabled() ? 0.04F : 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.800000011920929D;
			motionY *= 0.800000011920929D;
			motionZ *= 0.800000011920929D;
			motionY -= 0.02D;
			if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, motionY + 0.6000000238418579D - posY + var9, motionZ))
			{
				motionY = 0.30000001192092896D;
			}
		} else if(handleLavaMovement() && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).capabilities.isFlying))
		{
			var9 = posY;
			moveFlying(par1, par2, 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
			motionY -= 0.02D;
			if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, motionY + 0.6000000238418579D - posY + var9, motionZ))
			{
				motionY = 0.30000001192092896D;
			}
		} else
		{
			float var3 = 0.91F;
			if(onGround)
			{
				var3 = 0.54600006F;
				int var4 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
				if(var4 > 0)
				{
					var3 = Block.blocksList[var4].slipperiness * 0.91F;
				}
			}
			float var8 = 0.16277136F / (var3 * var3 * var3);
			float var5;
			if(onGround)
			{
				if(isAIEnabled())
				{
					var5 = getAIMoveSpeed();
				} else
				{
					var5 = landMovementFactor;
				}
				var5 *= var8;
			} else
			{
				var5 = jumpMovementFactor;
			}
			moveFlying(par1, par2, var5);
			var3 = 0.91F;
			if(onGround)
			{
				var3 = 0.54600006F;
				int var6 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
				if(var6 > 0)
				{
					var3 = Block.blocksList[var6].slipperiness * 0.91F;
				}
			}
			if(isOnLadder())
			{
				float var10 = 0.15F;
				if(motionX < -var10)
				{
					motionX = -var10;
				}
				if(motionX > var10)
				{
					motionX = var10;
				}
				if(motionZ < -var10)
				{
					motionZ = -var10;
				}
				if(motionZ > var10)
				{
					motionZ = var10;
				}
				fallDistance = 0.0F;
				if(motionY < -0.15D)
				{
					motionY = -0.15D;
				}
				boolean var7 = isSneaking() && this instanceof EntityPlayer;
				if(var7 && motionY < 0.0D)
				{
					motionY = 0.0D;
				}
			}
			moveEntity(motionX, motionY, motionZ);
			if(isCollidedHorizontally && isOnLadder())
			{
				motionY = 0.2D;
			}
			if(worldObj.isRemote && (!worldObj.blockExists((int) posX, 0, (int) posZ) || !worldObj.getChunkFromBlockCoords((int) posX, (int) posZ).isChunkLoaded))
			{
				if(posY > 0.0D)
				{
					motionY = -0.1D;
				} else
				{
					motionY = 0.0D;
				}
			} else
			{
				motionY -= 0.08D;
			}
			motionY *= 0.9800000190734863D;
			motionX *= var3;
			motionZ *= var3;
		}
		prevLimbYaw = limbYaw;
		var9 = posX - prevPosX;
		double var12 = posZ - prevPosZ;
		float var11 = MathHelper.sqrt_double(var9 * var9 + var12 * var12) * 4.0F;
		if(var11 > 1.0F)
		{
			var11 = 1.0F;
		}
		limbYaw += (var11 - limbYaw) * 0.4F;
		limbSwing += limbYaw;
	}
	
	protected void onChangedPotionEffect(PotionEffect par1PotionEffect)
	{
		potionsNeedUpdate = true;
	}
	
	public void onDeath(DamageSource par1DamageSource)
	{
		Entity var2 = par1DamageSource.getEntity();
		EntityLiving var3 = func_94060_bK();
		if(scoreValue >= 0 && var3 != null)
		{
			var3.addToPlayerScore(this, scoreValue);
		}
		if(var2 != null)
		{
			var2.onKillEntity(this);
		}
		dead = true;
		if(!worldObj.isRemote)
		{
			int var4 = 0;
			if(var2 instanceof EntityPlayer)
			{
				var4 = EnchantmentHelper.getLootingModifier((EntityLiving) var2);
			}
			if(!isChild() && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
			{
				dropFewItems(recentlyHit > 0, var4);
				dropEquipment(recentlyHit > 0, var4);
				if(recentlyHit > 0)
				{
					int var5 = rand.nextInt(200) - var4;
					if(var5 < 5)
					{
						dropRareDrop(var5 <= 0 ? 1 : 0);
					}
				}
			}
		}
		worldObj.setEntityState(this, (byte) 3);
	}
	
	protected void onDeathUpdate()
	{
		++deathTime;
		if(deathTime == 20)
		{
			int var1;
			if(!worldObj.isRemote && (recentlyHit > 0 || isPlayer()) && !isChild() && worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot"))
			{
				var1 = getExperiencePoints(attackingPlayer);
				while(var1 > 0)
				{
					int var2 = EntityXPOrb.getXPSplit(var1);
					var1 -= var2;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var2));
				}
			}
			setDead();
			for(var1 = 0; var1 < 20; ++var1)
			{
				double var8 = rand.nextGaussian() * 0.02D;
				double var4 = rand.nextGaussian() * 0.02D;
				double var6 = rand.nextGaussian() * 0.02D;
				worldObj.spawnParticle("explode", posX + rand.nextFloat() * width * 2.0F - width, posY + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var8, var4, var6);
			}
		}
	}
	
	@Override public void onEntityUpdate()
	{
		prevSwingProgress = swingProgress;
		super.onEntityUpdate();
		worldObj.theProfiler.startSection("mobBaseTick");
		if(isEntityAlive() && rand.nextInt(1000) < livingSoundTime++)
		{
			livingSoundTime = -getTalkInterval();
			playLivingSound();
		}
		if(isEntityAlive() && isEntityInsideOpaqueBlock())
		{
			attackEntityFrom(DamageSource.inWall, 1);
		}
		if(isImmuneToFire() || worldObj.isRemote)
		{
			extinguish();
		}
		boolean var1 = this instanceof EntityPlayer && ((EntityPlayer) this).capabilities.disableDamage;
		if(isEntityAlive() && isInsideOfMaterial(Material.water) && !canBreatheUnderwater() && !activePotionsMap.containsKey(Integer.valueOf(Potion.waterBreathing.id)) && !var1)
		{
			setAir(decreaseAirSupply(getAir()));
			if(getAir() == -20)
			{
				setAir(0);
				for(int var2 = 0; var2 < 8; ++var2)
				{
					float var3 = rand.nextFloat() - rand.nextFloat();
					float var4 = rand.nextFloat() - rand.nextFloat();
					float var5 = rand.nextFloat() - rand.nextFloat();
					worldObj.spawnParticle("bubble", posX + var3, posY + var4, posZ + var5, motionX, motionY, motionZ);
				}
				attackEntityFrom(DamageSource.drown, 2);
			}
			extinguish();
		} else
		{
			setAir(300);
		}
		prevCameraPitch = cameraPitch;
		if(attackTime > 0)
		{
			--attackTime;
		}
		if(hurtTime > 0)
		{
			--hurtTime;
		}
		if(hurtResistantTime > 0)
		{
			--hurtResistantTime;
		}
		if(health <= 0)
		{
			onDeathUpdate();
		}
		if(recentlyHit > 0)
		{
			--recentlyHit;
		} else
		{
			attackingPlayer = null;
		}
		if(lastAttackingEntity != null && !lastAttackingEntity.isEntityAlive())
		{
			lastAttackingEntity = null;
		}
		if(entityLivingToAttack != null)
		{
			if(!entityLivingToAttack.isEntityAlive())
			{
				setRevengeTarget((EntityLiving) null);
			} else if(revengeTimer > 0)
			{
				--revengeTimer;
			} else
			{
				setRevengeTarget((EntityLiving) null);
			}
		}
		updatePotionEffects();
		field_70763_ax = field_70764_aw;
		prevRenderYawOffset = renderYawOffset;
		prevRotationYawHead = rotationYawHead;
		prevRotationYaw = rotationYaw;
		prevRotationPitch = rotationPitch;
		worldObj.theProfiler.endSection();
	}
	
	protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
	{
		potionsNeedUpdate = true;
	}
	
	public void onItemPickup(Entity par1Entity, int par2)
	{
		if(!par1Entity.isDead && !worldObj.isRemote)
		{
			EntityTracker var3 = ((WorldServer) worldObj).getEntityTracker();
			if(par1Entity instanceof EntityItem)
			{
				var3.sendPacketToAllPlayersTrackingEntity(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
			}
			if(par1Entity instanceof EntityArrow)
			{
				var3.sendPacketToAllPlayersTrackingEntity(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
			}
			if(par1Entity instanceof EntityXPOrb)
			{
				var3.sendPacketToAllPlayersTrackingEntity(par1Entity, new Packet22Collect(par1Entity.entityId, entityId));
			}
		}
	}
	
	public void onLivingUpdate()
	{
		if(jumpTicks > 0)
		{
			--jumpTicks;
		}
		if(newPosRotationIncrements > 0)
		{
			double var1 = posX + (newPosX - posX) / newPosRotationIncrements;
			double var3 = posY + (newPosY - posY) / newPosRotationIncrements;
			double var5 = posZ + (newPosZ - posZ) / newPosRotationIncrements;
			double var7 = MathHelper.wrapAngleTo180_double(newRotationYaw - rotationYaw);
			rotationYaw = (float) (rotationYaw + var7 / newPosRotationIncrements);
			rotationPitch = (float) (rotationPitch + (newRotationPitch - rotationPitch) / newPosRotationIncrements);
			--newPosRotationIncrements;
			setPosition(var1, var3, var5);
			setRotation(rotationYaw, rotationPitch);
		} else if(!isClientWorld())
		{
			motionX *= 0.98D;
			motionY *= 0.98D;
			motionZ *= 0.98D;
		}
		if(Math.abs(motionX) < 0.005D)
		{
			motionX = 0.0D;
		}
		if(Math.abs(motionY) < 0.005D)
		{
			motionY = 0.0D;
		}
		if(Math.abs(motionZ) < 0.005D)
		{
			motionZ = 0.0D;
		}
		worldObj.theProfiler.startSection("ai");
		if(isMovementBlocked())
		{
			isJumping = false;
			moveStrafing = 0.0F;
			moveForward = 0.0F;
			randomYawVelocity = 0.0F;
		} else if(isClientWorld())
		{
			if(isAIEnabled())
			{
				worldObj.theProfiler.startSection("newAi");
				updateAITasks();
				worldObj.theProfiler.endSection();
			} else
			{
				worldObj.theProfiler.startSection("oldAi");
				updateEntityActionState();
				worldObj.theProfiler.endSection();
				rotationYawHead = rotationYaw;
			}
		}
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("jump");
		if(isJumping)
		{
			if(!isInWater() && !handleLavaMovement())
			{
				if(onGround && jumpTicks == 0)
				{
					jump();
					jumpTicks = 10;
				}
			} else
			{
				motionY += 0.03999999910593033D;
			}
		} else
		{
			jumpTicks = 0;
		}
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("travel");
		moveStrafing *= 0.98F;
		moveForward *= 0.98F;
		randomYawVelocity *= 0.9F;
		float var11 = landMovementFactor;
		landMovementFactor *= getSpeedModifier();
		moveEntityWithHeading(moveStrafing, moveForward);
		landMovementFactor = var11;
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("push");
		if(!worldObj.isRemote)
		{
			collideWithNearbyEntities();
		}
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("looting");
		if(!worldObj.isRemote && canPickUpLoot() && !dead && worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			List var2 = worldObj.getEntitiesWithinAABB(EntityItem.class, boundingBox.expand(1.0D, 0.0D, 1.0D));
			Iterator var12 = var2.iterator();
			while(var12.hasNext())
			{
				EntityItem var4 = (EntityItem) var12.next();
				if(!var4.isDead && var4.getEntityItem() != null)
				{
					ItemStack var13 = var4.getEntityItem();
					int var6 = getArmorPosition(var13);
					if(var6 > -1)
					{
						boolean var14 = true;
						ItemStack var8 = getCurrentItemOrArmor(var6);
						if(var8 != null)
						{
							if(var6 == 0)
							{
								if(var13.getItem() instanceof ItemSword && !(var8.getItem() instanceof ItemSword))
								{
									var14 = true;
								} else if(var13.getItem() instanceof ItemSword && var8.getItem() instanceof ItemSword)
								{
									ItemSword var9 = (ItemSword) var13.getItem();
									ItemSword var10 = (ItemSword) var8.getItem();
									if(var9.func_82803_g() == var10.func_82803_g())
									{
										var14 = var13.getItemDamage() > var8.getItemDamage() || var13.hasTagCompound() && !var8.hasTagCompound();
									} else
									{
										var14 = var9.func_82803_g() > var10.func_82803_g();
									}
								} else
								{
									var14 = false;
								}
							} else if(var13.getItem() instanceof ItemArmor && !(var8.getItem() instanceof ItemArmor))
							{
								var14 = true;
							} else if(var13.getItem() instanceof ItemArmor && var8.getItem() instanceof ItemArmor)
							{
								ItemArmor var15 = (ItemArmor) var13.getItem();
								ItemArmor var16 = (ItemArmor) var8.getItem();
								if(var15.damageReduceAmount == var16.damageReduceAmount)
								{
									var14 = var13.getItemDamage() > var8.getItemDamage() || var13.hasTagCompound() && !var8.hasTagCompound();
								} else
								{
									var14 = var15.damageReduceAmount > var16.damageReduceAmount;
								}
							} else
							{
								var14 = false;
							}
						}
						if(var14)
						{
							if(var8 != null && rand.nextFloat() - 0.1F < equipmentDropChances[var6])
							{
								entityDropItem(var8, 0.0F);
							}
							setCurrentItemOrArmor(var6, var13);
							equipmentDropChances[var6] = 2.0F;
							persistenceRequired = true;
							onItemPickup(var4, 1);
							var4.setDead();
						}
					}
				}
			}
		}
		worldObj.theProfiler.endSection();
	}
	
	protected void onNewPotionEffect(PotionEffect par1PotionEffect)
	{
		potionsNeedUpdate = true;
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote)
		{
			int var1;
			for(var1 = 0; var1 < 5; ++var1)
			{
				ItemStack var2 = getCurrentItemOrArmor(var1);
				if(!ItemStack.areItemStacksEqual(var2, previousEquipment[var1]))
				{
					((WorldServer) worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet5PlayerInventory(entityId, var1, var2));
					previousEquipment[var1] = var2 == null ? null : var2.copy();
				}
			}
			var1 = getArrowCountInEntity();
			if(var1 > 0)
			{
				if(arrowHitTimer <= 0)
				{
					arrowHitTimer = 20 * (30 - var1);
				}
				--arrowHitTimer;
				if(arrowHitTimer <= 0)
				{
					setArrowCountInEntity(var1 - 1);
				}
			}
		}
		onLivingUpdate();
		double var12 = posX - prevPosX;
		double var3 = posZ - prevPosZ;
		float var5 = (float) (var12 * var12 + var3 * var3);
		float var6 = renderYawOffset;
		float var7 = 0.0F;
		field_70768_au = field_70766_av;
		float var8 = 0.0F;
		if(var5 > 0.0025000002F)
		{
			var8 = 1.0F;
			var7 = (float) Math.sqrt(var5) * 3.0F;
			var6 = (float) Math.atan2(var3, var12) * 180.0F / (float) Math.PI - 90.0F;
		}
		if(swingProgress > 0.0F)
		{
			var6 = rotationYaw;
		}
		if(!onGround)
		{
			var8 = 0.0F;
		}
		field_70766_av += (var8 - field_70766_av) * 0.3F;
		worldObj.theProfiler.startSection("headTurn");
		if(isAIEnabled())
		{
			bodyHelper.func_75664_a();
		} else
		{
			float var9 = MathHelper.wrapAngleTo180_float(var6 - renderYawOffset);
			renderYawOffset += var9 * 0.3F;
			float var10 = MathHelper.wrapAngleTo180_float(rotationYaw - renderYawOffset);
			boolean var11 = var10 < -90.0F || var10 >= 90.0F;
			if(var10 < -75.0F)
			{
				var10 = -75.0F;
			}
			if(var10 >= 75.0F)
			{
				var10 = 75.0F;
			}
			renderYawOffset = rotationYaw - var10;
			if(var10 * var10 > 2500.0F)
			{
				renderYawOffset += var10 * 0.2F;
			}
			if(var11)
			{
				var7 *= -1.0F;
			}
		}
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("rangeChecks");
		while(rotationYaw - prevRotationYaw < -180.0F)
		{
			prevRotationYaw -= 360.0F;
		}
		while(rotationYaw - prevRotationYaw >= 180.0F)
		{
			prevRotationYaw += 360.0F;
		}
		while(renderYawOffset - prevRenderYawOffset < -180.0F)
		{
			prevRenderYawOffset -= 360.0F;
		}
		while(renderYawOffset - prevRenderYawOffset >= 180.0F)
		{
			prevRenderYawOffset += 360.0F;
		}
		while(rotationPitch - prevRotationPitch < -180.0F)
		{
			prevRotationPitch -= 360.0F;
		}
		while(rotationPitch - prevRotationPitch >= 180.0F)
		{
			prevRotationPitch += 360.0F;
		}
		while(rotationYawHead - prevRotationYawHead < -180.0F)
		{
			prevRotationYawHead -= 360.0F;
		}
		while(rotationYawHead - prevRotationYawHead >= 180.0F)
		{
			prevRotationYawHead += 360.0F;
		}
		worldObj.theProfiler.endSection();
		field_70764_aw += var7;
	}
	
	@Override public void performHurtAnimation()
	{
		hurtTime = maxHurtTime = 10;
		attackedAtYaw = 0.0F;
	}
	
	public void playLivingSound()
	{
		String var1 = getLivingSound();
		if(var1 != null)
		{
			playSound(var1, getSoundVolume(), getSoundPitch());
		}
	}
	
	public MovingObjectPosition rayTrace(double par1, float par3)
	{
		Vec3 var4 = getPosition(par3);
		Vec3 var5 = getLook(par3);
		Vec3 var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1, var5.zCoord * par1);
		return worldObj.clip(var4, var6);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		health = par1NBTTagCompound.getShort("Health");
		if(!par1NBTTagCompound.hasKey("Health"))
		{
			health = getMaxHealth();
		}
		hurtTime = par1NBTTagCompound.getShort("HurtTime");
		deathTime = par1NBTTagCompound.getShort("DeathTime");
		attackTime = par1NBTTagCompound.getShort("AttackTime");
		setCanPickUpLoot(par1NBTTagCompound.getBoolean("CanPickUpLoot"));
		persistenceRequired = par1NBTTagCompound.getBoolean("PersistenceRequired");
		if(par1NBTTagCompound.hasKey("CustomName") && par1NBTTagCompound.getString("CustomName").length() > 0)
		{
			func_94058_c(par1NBTTagCompound.getString("CustomName"));
		}
		func_94061_f(par1NBTTagCompound.getBoolean("CustomNameVisible"));
		NBTTagList var2;
		int var3;
		if(par1NBTTagCompound.hasKey("Equipment"))
		{
			var2 = par1NBTTagCompound.getTagList("Equipment");
			for(var3 = 0; var3 < equipment.length; ++var3)
			{
				equipment[var3] = ItemStack.loadItemStackFromNBT((NBTTagCompound) var2.tagAt(var3));
			}
		}
		if(par1NBTTagCompound.hasKey("ActiveEffects"))
		{
			var2 = par1NBTTagCompound.getTagList("ActiveEffects");
			for(var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
				PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
				activePotionsMap.put(Integer.valueOf(var5.getPotionID()), var5);
			}
		}
		if(par1NBTTagCompound.hasKey("DropChances"))
		{
			var2 = par1NBTTagCompound.getTagList("DropChances");
			for(var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				equipmentDropChances[var3] = ((NBTTagFloat) var2.tagAt(var3)).data;
			}
		}
	}
	
	public void removePotionEffect(int par1)
	{
		PotionEffect var2 = (PotionEffect) activePotionsMap.remove(Integer.valueOf(par1));
		if(var2 != null)
		{
			onFinishedPotionEffect(var2);
		}
	}
	
	public void removePotionEffectClient(int par1)
	{
		activePotionsMap.remove(Integer.valueOf(par1));
	}
	
	public void renderBrokenItemStack(ItemStack par1ItemStack)
	{
		playSound("random.break", 0.8F, 0.8F + worldObj.rand.nextFloat() * 0.4F);
		for(int var2 = 0; var2 < 5; ++var2)
		{
			Vec3 var3 = worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
			var3.rotateAroundX(-rotationPitch * (float) Math.PI / 180.0F);
			var3.rotateAroundY(-rotationYaw * (float) Math.PI / 180.0F);
			Vec3 var4 = worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.3D, -rand.nextFloat() * 0.6D - 0.3D, 0.6D);
			var4.rotateAroundX(-rotationPitch * (float) Math.PI / 180.0F);
			var4.rotateAroundY(-rotationYaw * (float) Math.PI / 180.0F);
			var4 = var4.addVector(posX, posY + getEyeHeight(), posZ);
			worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID, var4.xCoord, var4.yCoord, var4.zCoord, var3.xCoord, var3.yCoord + 0.05D, var3.zCoord);
		}
	}
	
	public void setAIMoveSpeed(float par1)
	{
		AIMoveSpeed = par1;
		setMoveForward(par1);
	}
	
	public final void setArrowCountInEntity(int par1)
	{
		dataWatcher.updateObject(10, Byte.valueOf((byte) par1));
	}
	
	public void setAttackTarget(EntityLiving par1EntityLiving)
	{
		attackTarget = par1EntityLiving;
	}
	
	public void setCanPickUpLoot(boolean par1)
	{
		canPickUpLoot = par1;
	}
	
	@Override public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
	{
		equipment[par1] = par2ItemStack;
	}
	
	public void setEntityHealth(int par1)
	{
		health = par1;
		if(par1 > getMaxHealth())
		{
			par1 = getMaxHealth();
		}
	}
	
	public void setEquipmentDropChance(int par1, float par2)
	{
		equipmentDropChances[par1] = par2;
	}
	
	public void setHomeArea(int par1, int par2, int par3, int par4)
	{
		homePosition.set(par1, par2, par3);
		maximumHomeDistance = par4;
	}
	
	public void setJumping(boolean par1)
	{
		isJumping = par1;
	}
	
	public void setLastAttackingEntity(Entity par1Entity)
	{
		if(par1Entity instanceof EntityLiving)
		{
			lastAttackingEntity = (EntityLiving) par1Entity;
		}
	}
	
	public void setMoveForward(float par1)
	{
		moveForward = par1;
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		yOffset = 0.0F;
		newPosX = par1;
		newPosY = par3;
		newPosZ = par5;
		newRotationYaw = par7;
		newRotationPitch = par8;
		newPosRotationIncrements = par9;
	}
	
	public void setPositionAndUpdate(double par1, double par3, double par5)
	{
		setLocationAndAngles(par1, par3, par5, rotationYaw, rotationPitch);
	}
	
	public void setRevengeTarget(EntityLiving par1EntityLiving)
	{
		entityLivingToAttack = par1EntityLiving;
		revengeTimer = entityLivingToAttack != null ? 100 : 0;
	}
	
	@Override public void setRotationYawHead(float par1)
	{
		rotationYawHead = par1;
	}
	
	public void spawnExplosionParticle()
	{
		for(int var1 = 0; var1 < 20; ++var1)
		{
			double var2 = rand.nextGaussian() * 0.02D;
			double var4 = rand.nextGaussian() * 0.02D;
			double var6 = rand.nextGaussian() * 0.02D;
			double var8 = 10.0D;
			worldObj.spawnParticle("explode", posX + rand.nextFloat() * width * 2.0F - width - var2 * var8, posY + rand.nextFloat() * height - var4 * var8, posZ + rand.nextFloat() * width * 2.0F - width - var6 * var8, var2, var4, var6);
		}
	}
	
	public void swingItem()
	{
		if(!isSwingInProgress || swingProgressInt >= getArmSwingAnimationEnd() / 2 || swingProgressInt < 0)
		{
			swingProgressInt = -1;
			isSwingInProgress = true;
			if(worldObj instanceof WorldServer)
			{
				((WorldServer) worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet18Animation(this, 1));
			}
		}
	}
	
	protected void updateAITasks()
	{
		++entityAge;
		worldObj.theProfiler.startSection("checkDespawn");
		despawnEntity();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("sensing");
		senses.clearSensingCache();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("targetSelector");
		targetTasks.onUpdateTasks();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("goalSelector");
		tasks.onUpdateTasks();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("navigation");
		navigator.onUpdateNavigation();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("mob tick");
		updateAITick();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("controls");
		worldObj.theProfiler.startSection("move");
		moveHelper.onUpdateMoveHelper();
		worldObj.theProfiler.endStartSection("look");
		lookHelper.onUpdateLook();
		worldObj.theProfiler.endStartSection("jump");
		jumpHelper.doJump();
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.endSection();
	}
	
	protected void updateAITick()
	{
	}
	
	protected void updateArmSwingProgress()
	{
		int var1 = getArmSwingAnimationEnd();
		if(isSwingInProgress)
		{
			++swingProgressInt;
			if(swingProgressInt >= var1)
			{
				swingProgressInt = 0;
				isSwingInProgress = false;
			}
		} else
		{
			swingProgressInt = 0;
		}
		swingProgress = (float) swingProgressInt / (float) var1;
	}
	
	protected void updateEntityActionState()
	{
		++entityAge;
		despawnEntity();
		moveStrafing = 0.0F;
		moveForward = 0.0F;
		float var1 = 8.0F;
		if(rand.nextFloat() < 0.02F)
		{
			EntityPlayer var2 = worldObj.getClosestPlayerToEntity(this, var1);
			if(var2 != null)
			{
				currentTarget = var2;
				numTicksToChaseTarget = 10 + rand.nextInt(20);
			} else
			{
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20.0F;
			}
		}
		if(currentTarget != null)
		{
			faceEntity(currentTarget, 10.0F, getVerticalFaceSpeed());
			if(numTicksToChaseTarget-- <= 0 || currentTarget.isDead || currentTarget.getDistanceSqToEntity(this) > var1 * var1)
			{
				currentTarget = null;
			}
		} else
		{
			if(rand.nextFloat() < 0.05F)
			{
				randomYawVelocity = (rand.nextFloat() - 0.5F) * 20.0F;
			}
			rotationYaw += randomYawVelocity;
			rotationPitch = defaultPitch;
		}
		boolean var4 = isInWater();
		boolean var3 = handleLavaMovement();
		if(var4 || var3)
		{
			isJumping = rand.nextFloat() < 0.8F;
		}
	}
	
	@Override protected void updateFallState(double par1, boolean par3)
	{
		if(!isInWater())
		{
			handleWaterMovement();
		}
		if(par3 && fallDistance > 0.0F)
		{
			int var4 = MathHelper.floor_double(posX);
			int var5 = MathHelper.floor_double(posY - 0.20000000298023224D - yOffset);
			int var6 = MathHelper.floor_double(posZ);
			int var7 = worldObj.getBlockId(var4, var5, var6);
			if(var7 == 0)
			{
				int var8 = worldObj.blockGetRenderType(var4, var5 - 1, var6);
				if(var8 == 11 || var8 == 32 || var8 == 21)
				{
					var7 = worldObj.getBlockId(var4, var5 - 1, var6);
				}
			}
			if(var7 > 0)
			{
				Block.blocksList[var7].onFallenUpon(worldObj, var4, var5, var6, this, fallDistance);
			}
		}
		super.updateFallState(par1, par3);
	}
	
	protected void updatePotionEffects()
	{
		Iterator var1 = activePotionsMap.keySet().iterator();
		while(var1.hasNext())
		{
			Integer var2 = (Integer) var1.next();
			PotionEffect var3 = (PotionEffect) activePotionsMap.get(var2);
			try
			{
				if(!var3.onUpdate(this))
				{
					if(!worldObj.isRemote)
					{
						var1.remove();
						onFinishedPotionEffect(var3);
					}
				} else if(var3.getDuration() % 600 == 0)
				{
					onChangedPotionEffect(var3);
				}
			} catch(Throwable var11)
			{
				CrashReport var5 = CrashReport.makeCrashReport(var11, "Ticking mob effect instance");
				CrashReportCategory var6 = var5.makeCategory("Mob effect being ticked");
				var6.addCrashSectionCallable("Effect Name", new CallableEffectName(this, var3));
				var6.addCrashSectionCallable("Effect ID", new CallableEffectID(this, var3));
				var6.addCrashSectionCallable("Effect Duration", new CallableEffectDuration(this, var3));
				var6.addCrashSectionCallable("Effect Amplifier", new CallableEffectAmplifier(this, var3));
				var6.addCrashSectionCallable("Effect is Splash", new CallableEffectIsSplash(this, var3));
				var6.addCrashSectionCallable("Effect is Ambient", new CallableEffectIsAmbient(this, var3));
				throw new ReportedException(var5);
			}
		}
		int var12;
		if(potionsNeedUpdate)
		{
			if(!worldObj.isRemote)
			{
				if(activePotionsMap.isEmpty())
				{
					dataWatcher.updateObject(9, Byte.valueOf((byte) 0));
					dataWatcher.updateObject(8, Integer.valueOf(0));
					setInvisible(false);
				} else
				{
					var12 = PotionHelper.calcPotionLiquidColor(activePotionsMap.values());
					dataWatcher.updateObject(9, Byte.valueOf((byte) (PotionHelper.func_82817_b(activePotionsMap.values()) ? 1 : 0)));
					dataWatcher.updateObject(8, Integer.valueOf(var12));
					setInvisible(this.isPotionActive(Potion.invisibility.id));
				}
			}
			potionsNeedUpdate = false;
		}
		var12 = dataWatcher.getWatchableObjectInt(8);
		boolean var13 = dataWatcher.getWatchableObjectByte(9) > 0;
		if(var12 > 0)
		{
			boolean var4 = false;
			if(!isInvisible())
			{
				var4 = rand.nextBoolean();
			} else
			{
				var4 = rand.nextInt(15) == 0;
			}
			if(var13)
			{
				var4 &= rand.nextInt(5) == 0;
			}
			if(var4 && var12 > 0)
			{
				double var14 = (var12 >> 16 & 255) / 255.0D;
				double var7 = (var12 >> 8 & 255) / 255.0D;
				double var9 = (var12 >> 0 & 255) / 255.0D;
				worldObj.spawnParticle(var13 ? "mobSpellAmbient" : "mobSpell", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - yOffset, posZ + (rand.nextDouble() - 0.5D) * width, var14, var7, var9);
			}
		}
	}
	
	@Override public void updateRidden()
	{
		super.updateRidden();
		field_70768_au = field_70766_av;
		field_70766_av = 0.0F;
		fallDistance = 0.0F;
	}
	
	private float updateRotation(float par1, float par2, float par3)
	{
		float var4 = MathHelper.wrapAngleTo180_float(par2 - par1);
		if(var4 > par3)
		{
			var4 = par3;
		}
		if(var4 < -par3)
		{
			var4 = -par3;
		}
		return par1 + var4;
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(health < -32768)
		{
			health = -32768;
		}
		par1NBTTagCompound.setShort("Health", (short) health);
		par1NBTTagCompound.setShort("HurtTime", (short) hurtTime);
		par1NBTTagCompound.setShort("DeathTime", (short) deathTime);
		par1NBTTagCompound.setShort("AttackTime", (short) attackTime);
		par1NBTTagCompound.setBoolean("CanPickUpLoot", canPickUpLoot());
		par1NBTTagCompound.setBoolean("PersistenceRequired", persistenceRequired);
		NBTTagList var2 = new NBTTagList();
		for(ItemStack element : equipment)
		{
			NBTTagCompound var4 = new NBTTagCompound();
			if(element != null)
			{
				element.writeToNBT(var4);
			}
			var2.appendTag(var4);
		}
		par1NBTTagCompound.setTag("Equipment", var2);
		NBTTagList var6;
		if(!activePotionsMap.isEmpty())
		{
			var6 = new NBTTagList();
			Iterator var7 = activePotionsMap.values().iterator();
			while(var7.hasNext())
			{
				PotionEffect var5 = (PotionEffect) var7.next();
				var6.appendTag(var5.writeCustomPotionEffectToNBT(new NBTTagCompound()));
			}
			par1NBTTagCompound.setTag("ActiveEffects", var6);
		}
		var6 = new NBTTagList();
		for(int var8 = 0; var8 < equipmentDropChances.length; ++var8)
		{
			var6.appendTag(new NBTTagFloat(var8 + "", equipmentDropChances[var8]));
		}
		par1NBTTagCompound.setTag("DropChances", var6);
		par1NBTTagCompound.setString("CustomName", getCustomNameTag());
		par1NBTTagCompound.setBoolean("CustomNameVisible", func_94062_bN());
	}
	
	public static Item getArmorItemForSlot(int par0, int par1)
	{
		switch(par0)
		{
			case 4:
				if(par1 == 0) return Item.helmetLeather;
				else if(par1 == 1) return Item.helmetGold;
				else if(par1 == 2) return Item.helmetChain;
				else if(par1 == 3) return Item.helmetIron;
				else if(par1 == 4) return Item.helmetDiamond;
			case 3:
				if(par1 == 0) return Item.plateLeather;
				else if(par1 == 1) return Item.plateGold;
				else if(par1 == 2) return Item.plateChain;
				else if(par1 == 3) return Item.plateIron;
				else if(par1 == 4) return Item.plateDiamond;
			case 2:
				if(par1 == 0) return Item.legsLeather;
				else if(par1 == 1) return Item.legsGold;
				else if(par1 == 2) return Item.legsChain;
				else if(par1 == 3) return Item.legsIron;
				else if(par1 == 4) return Item.legsDiamond;
			case 1:
				if(par1 == 0) return Item.bootsLeather;
				else if(par1 == 1) return Item.bootsGold;
				else if(par1 == 2) return Item.bootsChain;
				else if(par1 == 3) return Item.bootsIron;
				else if(par1 == 4) return Item.bootsDiamond;
			default:
				return null;
		}
	}
	
	public static int getArmorPosition(ItemStack par0ItemStack)
	{
		if(par0ItemStack.itemID != Block.pumpkin.blockID && par0ItemStack.itemID != Item.skull.itemID)
		{
			if(par0ItemStack.getItem() instanceof ItemArmor)
			{
				switch(((ItemArmor) par0ItemStack.getItem()).armorType)
				{
					case 0:
						return 4;
					case 1:
						return 3;
					case 2:
						return 2;
					case 3:
						return 1;
				}
			}
			return 0;
		} else return 4;
	}
}
