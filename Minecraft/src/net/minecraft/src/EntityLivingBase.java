package net.minecraft.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class EntityLivingBase extends Entity
{
	private static final UUID field_110156_b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
	private static final AttributeModifier field_110157_c = new AttributeModifier(field_110156_b, "Sprinting speed boost", 0.30000001192092896D, 2).func_111168_a(false);
	private BaseAttributeMap field_110155_d;
	private final CombatTracker _combatTracker = new CombatTracker(this);
	private final HashMap activePotionsMap = new HashMap();
	private final ItemStack[] previousEquipment = new ItemStack[5];
	public boolean isSwingInProgress;
	public int field_110158_av;
	public int arrowHitTimer;
	public float prevHealth;
	public int hurtTime;
	public int maxHurtTime;
	public float attackedAtYaw;
	public int deathTime;
	public int attackTime;
	public float prevSwingProgress;
	public float swingProgress;
	public float prevLimbYaw;
	public float limbYaw;
	public float limbSwing;
	public int maxHurtResistantTime = 20;
	public float prevCameraPitch;
	public float cameraPitch;
	public float field_70769_ao;
	public float field_70770_ap;
	public float renderYawOffset;
	public float prevRenderYawOffset;
	public float rotationYawHead;
	public float prevRotationYawHead;
	public float jumpMovementFactor = 0.02F;
	protected EntityPlayer attackingPlayer;
	protected int recentlyHit;
	protected boolean dead;
	protected int entityAge;
	protected float field_70768_au;
	protected float field_110154_aX;
	protected float field_70764_aw;
	protected float field_70763_ax;
	protected float field_70741_aB;
	protected int scoreValue;
	protected float field_110153_bc;
	protected boolean isJumping;
	public float moveStrafing;
	public float moveForward;
	protected float randomYawVelocity;
	protected int newPosRotationIncrements;
	protected double newPosX;
	protected double newPosY;
	protected double field_110152_bk;
	protected double newRotationYaw;
	protected double newRotationPitch;
	private boolean potionsNeedUpdate = true;
	private EntityLivingBase entityLivingToAttack;
	private int revengeTimer;
	private EntityLivingBase field_110150_bn;
	private int field_142016_bo;
	private float landMovementFactor;
	private int jumpTicks;
	private float field_110151_bq;
	
	public EntityLivingBase(World par1World)
	{
		super(par1World);
		func_110147_ax();
		setEntityHealth(func_110138_aP());
		preventEntitySpawning = true;
		field_70770_ap = (float) (Math.random() + 1.0D) * 0.01F;
		setPosition(posX, posY, posZ);
		field_70769_ao = (float) Math.random() * 12398.0F;
		rotationYaw = (float) (Math.random() * Math.PI * 2.0D);
		rotationYawHead = rotationYaw;
		stepHeight = 0.5F;
	}
	
	public void addPotionEffect(PotionEffect par1PotionEffect)
	{
		if(isPotionApplicable(par1PotionEffect))
		{
			if(activePotionsMap.containsKey(Integer.valueOf(par1PotionEffect.getPotionID())))
			{
				((PotionEffect) activePotionsMap.get(Integer.valueOf(par1PotionEffect.getPotionID()))).combine(par1PotionEffect);
				onChangedPotionEffect((PotionEffect) activePotionsMap.get(Integer.valueOf(par1PotionEffect.getPotionID())), true);
			} else
			{
				activePotionsMap.put(Integer.valueOf(par1PotionEffect.getPotionID()), par1PotionEffect);
				onNewPotionEffect(par1PotionEffect);
			}
		}
	}
	
	protected float applyArmorCalculations(DamageSource par1DamageSource, float par2)
	{
		if(!par1DamageSource.isUnblockable())
		{
			int var3 = 25 - getTotalArmorValue();
			float var4 = par2 * var3;
			damageArmor(par2);
			par2 = var4 / 25.0F;
		}
		return par2;
	}
	
	protected float applyPotionDamageCalculations(DamageSource par1DamageSource, float par2)
	{
		if(this instanceof EntityZombie)
		{
			par2 = par2;
		}
		int var3;
		int var4;
		float var5;
		if(this.isPotionActive(Potion.resistance) && par1DamageSource != DamageSource.outOfWorld)
		{
			var3 = (getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
			var4 = 25 - var3;
			var5 = par2 * var4;
			par2 = var5 / 25.0F;
		}
		if(par2 <= 0.0F) return 0.0F;
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
				var5 = par2 * var4;
				par2 = var5 / 25.0F;
			}
			return par2;
		}
	}
	
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		func_130011_c(par1Entity);
		return false;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else if(worldObj.isRemote) return false;
		else
		{
			entityAge = 0;
			if(func_110143_aJ() <= 0.0F) return false;
			else if(par1DamageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) return false;
			else
			{
				if((par1DamageSource == DamageSource.anvil || par1DamageSource == DamageSource.fallingBlock) && getCurrentItemOrArmor(4) != null)
				{
					getCurrentItemOrArmor(4).damageItem((int) (par2 * 4.0F + rand.nextFloat() * par2 * 2.0F), this);
					par2 *= 0.75F;
				}
				limbYaw = 1.5F;
				boolean var3 = true;
				if(hurtResistantTime > maxHurtResistantTime / 2.0F)
				{
					if(par2 <= field_110153_bc) return false;
					damageEntity(par1DamageSource, par2 - field_110153_bc);
					field_110153_bc = par2;
					var3 = false;
				} else
				{
					field_110153_bc = par2;
					prevHealth = func_110143_aJ();
					hurtResistantTime = maxHurtResistantTime;
					damageEntity(par1DamageSource, par2);
					hurtTime = maxHurtTime = 10;
				}
				attackedAtYaw = 0.0F;
				Entity var4 = par1DamageSource.getEntity();
				if(var4 != null)
				{
					if(var4 instanceof EntityLivingBase)
					{
						setRevengeTarget((EntityLivingBase) var4);
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
				if(func_110143_aJ() <= 0.0F)
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
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override public boolean canBePushed()
	{
		return !isDead;
	}
	
	public boolean canBreatheUnderwater()
	{
		return false;
	}
	
	public boolean canEntityBeSeen(Entity par1Entity)
	{
		return worldObj.clip(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY + getEyeHeight(), posZ), worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY + par1Entity.getEyeHeight(), par1Entity.posZ)) == null;
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
	
	protected void damageArmor(float par1)
	{
	}
	
	protected void damageEntity(DamageSource par1DamageSource, float par2)
	{
		if(!isEntityInvulnerable())
		{
			par2 = applyArmorCalculations(par1DamageSource, par2);
			par2 = applyPotionDamageCalculations(par1DamageSource, par2);
			float var3 = par2;
			par2 = Math.max(par2 - func_110139_bj(), 0.0F);
			func_110149_m(func_110139_bj() - (var3 - par2));
			if(par2 != 0.0F)
			{
				float var4 = func_110143_aJ();
				setEntityHealth(var4 - par2);
				func_110142_aN().func_94547_a(par1DamageSource, var4, par2);
				func_110149_m(func_110139_bj() - par2);
			}
		}
	}
	
	protected int decreaseAirSupply(int par1)
	{
		int var2 = EnchantmentHelper.getRespiration(this);
		return var2 > 0 && rand.nextInt(var2 + 1) > 0 ? par1 : par1 - 1;
	}
	
	protected void dropEquipment(boolean par1, int par2)
	{
	}
	
	protected void dropFewItems(boolean par1, int par2)
	{
	}
	
	protected void dropRareDrop(int par1)
	{
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(7, Integer.valueOf(0));
		dataWatcher.addObject(8, Byte.valueOf((byte) 0));
		dataWatcher.addObject(9, Byte.valueOf((byte) 0));
		dataWatcher.addObject(6, Float.valueOf(1.0F));
	}
	
	@Override protected void fall(float par1)
	{
		super.fall(par1);
		PotionEffect var2 = getActivePotionEffect(Potion.jump);
		float var3 = var2 != null ? (float) (var2.getAmplifier() + 1) : 0.0F;
		int var4 = MathHelper.ceiling_float_int(par1 - 3.0F - var3);
		if(var4 > 0)
		{
			if(var4 > 4)
			{
				playSound("damage.fallbig", 1.0F, 1.0F);
			} else
			{
				playSound("damage.fallsmall", 1.0F, 1.0F);
			}
			attackEntityFrom(DamageSource.fall, var4);
			int var5 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023224D - yOffset), MathHelper.floor_double(posZ));
			if(var5 > 0)
			{
				StepSound var6 = Block.blocksList[var5].stepSound;
				playSound(var6.getStepSound(), var6.getVolume() * 0.5F, var6.getPitch() * 0.75F);
			}
		}
	}
	
	public final float func_110138_aP()
	{
		return (float) func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
	}
	
	public float func_110139_bj()
	{
		return field_110151_bq;
	}
	
	public BaseAttributeMap func_110140_aT()
	{
		if(field_110155_d == null)
		{
			field_110155_d = new ServersideAttributeMap();
		}
		return field_110155_d;
	}
	
	public CombatTracker func_110142_aN()
	{
		return _combatTracker;
	}
	
	public final float func_110143_aJ()
	{
		return dataWatcher.func_111145_d(6);
	}
	
	public EntityLivingBase func_110144_aD()
	{
		return field_110150_bn;
	}
	
	public void func_110145_l(Entity par1Entity)
	{
		double var3 = par1Entity.posX;
		double var5 = par1Entity.boundingBox.minY + par1Entity.height;
		double var7 = par1Entity.posZ;
		for(double var9 = -1.5D; var9 < 2.0D; ++var9)
		{
			for(double var11 = -1.5D; var11 < 2.0D; ++var11)
			{
				if(var9 != 0.0D || var11 != 0.0D)
				{
					int var13 = (int) (posX + var9);
					int var14 = (int) (posZ + var11);
					AxisAlignedBB var2 = boundingBox.getOffsetBoundingBox(var9, 1.0D, var11);
					if(worldObj.getCollidingBlockBounds(var2).isEmpty())
					{
						if(worldObj.doesBlockHaveSolidTopSurface(var13, (int) posY, var14))
						{
							setPositionAndUpdate(posX + var9, posY + 1.0D, posZ + var11);
							return;
						}
						if(worldObj.doesBlockHaveSolidTopSurface(var13, (int) posY - 1, var14) || worldObj.getBlockMaterial(var13, (int) posY - 1, var14) == Material.water)
						{
							var3 = posX + var9;
							var5 = posY + 1.0D;
							var7 = posZ + var11;
						}
					}
				}
			}
		}
		setPositionAndUpdate(var3, var5, var7);
	}
	
	protected float func_110146_f(float par1, float par2)
	{
		float var3 = MathHelper.wrapAngleTo180_float(par1 - renderYawOffset);
		renderYawOffset += var3 * 0.3F;
		float var4 = MathHelper.wrapAngleTo180_float(rotationYaw - renderYawOffset);
		boolean var5 = var4 < -90.0F || var4 >= 90.0F;
		if(var4 < -75.0F)
		{
			var4 = -75.0F;
		}
		if(var4 >= 75.0F)
		{
			var4 = 75.0F;
		}
		renderYawOffset = rotationYaw - var4;
		if(var4 * var4 > 2500.0F)
		{
			renderYawOffset += var4 * 0.2F;
		}
		if(var5)
		{
			par2 *= -1.0F;
		}
		return par2;
	}
	
	protected void func_110147_ax()
	{
		func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111267_a);
		func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111266_c);
		func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111263_d);
		if(!isAIEnabled())
		{
			func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.10000000149011612D);
		}
	}
	
	public AttributeInstance func_110148_a(Attribute par1Attribute)
	{
		return func_110140_aT().func_111151_a(par1Attribute);
	}
	
	public void func_110149_m(float par1)
	{
		if(par1 < 0.0F)
		{
			par1 = 0.0F;
		}
		field_110151_bq = par1;
	}
	
	public void func_130011_c(Entity par1Entity)
	{
		if(par1Entity instanceof EntityLivingBase)
		{
			field_110150_bn = (EntityLivingBase) par1Entity;
		} else
		{
			field_110150_bn = null;
		}
		field_142016_bo = ticksExisted;
	}
	
	public boolean func_142012_a(Team par1Team)
	{
		return getTeam() != null ? getTeam().func_142054_a(par1Team) : false;
	}
	
	public int func_142013_aG()
	{
		return field_142016_bo;
	}
	
	public boolean func_142014_c(EntityLivingBase par1EntityLivingBase)
	{
		return func_142012_a(par1EntityLivingBase.getTeam());
	}
	
	public int func_142015_aE()
	{
		return revengeTimer;
	}
	
	public EntityLivingBase func_94060_bK()
	{
		return _combatTracker.func_94550_c() != null ? _combatTracker.func_94550_c() : attackingPlayer != null ? attackingPlayer : entityLivingToAttack != null ? entityLivingToAttack : null;
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
		return isAIEnabled() ? landMovementFactor : 0.1F;
	}
	
	public EntityLivingBase getAITarget()
	{
		return entityLivingToAttack;
	}
	
	public boolean getAlwaysRenderNameTagForRender()
	{
		return false;
	}
	
	private int getArmSwingAnimationEnd()
	{
		return this.isPotionActive(Potion.digSpeed) ? 6 - (1 + getActivePotionEffect(Potion.digSpeed).getAmplifier()) * 1 : this.isPotionActive(Potion.digSlowdown) ? 6 + (1 + getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6;
	}
	
	public final int getArrowCountInEntity()
	{
		return dataWatcher.getWatchableObjectByte(9);
	}
	
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}
	
	public abstract ItemStack getCurrentItemOrArmor(int var1);
	
	protected String getDeathSound()
	{
		return "damage.hit";
	}
	
	protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		return 0;
	}
	
	@Override public float getEyeHeight()
	{
		return height * 0.85F;
	}
	
	public abstract ItemStack getHeldItem();
	
	protected String getHurtSound()
	{
		return "damage.hit";
	}
	
	public Icon getItemIcon(ItemStack par1ItemStack, int par2)
	{
		return par1ItemStack.getIconIndex();
	}
	
	@Override public abstract ItemStack[] getLastActiveItems();
	
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
	
	@Override public Vec3 getLookVec()
	{
		return getLook(1.0F);
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
	
	public float getSwingProgress(float par1)
	{
		float var2 = swingProgress - prevSwingProgress;
		if(var2 < 0.0F)
		{
			++var2;
		}
		return prevSwingProgress + var2 * par1;
	}
	
	public Team getTeam()
	{
		return null;
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
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 2)
		{
			limbYaw = 1.5F;
			hurtResistantTime = maxHurtResistantTime;
			hurtTime = maxHurtTime = 10;
			attackedAtYaw = 0.0F;
			playSound(getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			attackEntityFrom(DamageSource.generic, 0.0F);
		} else if(par1 == 3)
		{
			playSound(getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			setEntityHealth(0.0F);
			onDeath(DamageSource.generic);
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	public void heal(float par1)
	{
		float var2 = func_110143_aJ();
		if(var2 > 0.0F)
		{
			setEntityHealth(var2 + par1);
		}
	}
	
	protected boolean isAIEnabled()
	{
		return false;
	}
	
	public boolean isChild()
	{
		return false;
	}
	
	public boolean isClientWorld()
	{
		return !worldObj.isRemote;
	}
	
	@Override public boolean isEntityAlive()
	{
		return !isDead && func_110143_aJ() > 0.0F;
	}
	
	public boolean isEntityUndead()
	{
		return getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}
	
	protected boolean isMovementBlocked()
	{
		return func_110143_aJ() <= 0.0F;
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
		attackEntityFrom(DamageSource.outOfWorld, 4.0F);
	}
	
	public void knockBack(Entity par1Entity, float par2, double par3, double par5)
	{
		if(rand.nextDouble() >= func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e())
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
	}
	
	public void moveEntityWithHeading(float par1, float par2)
	{
		double var10;
		if(isInWater() && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).capabilities.isFlying))
		{
			var10 = posY;
			moveFlying(par1, par2, isAIEnabled() ? 0.04F : 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.800000011920929D;
			motionY *= 0.800000011920929D;
			motionZ *= 0.800000011920929D;
			motionY -= 0.02D;
			if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, motionY + 0.6000000238418579D - posY + var10, motionZ))
			{
				motionY = 0.30000001192092896D;
			}
		} else if(handleLavaMovement() && (!(this instanceof EntityPlayer) || !((EntityPlayer) this).capabilities.isFlying))
		{
			var10 = posY;
			moveFlying(par1, par2, 0.02F);
			moveEntity(motionX, motionY, motionZ);
			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
			motionY -= 0.02D;
			if(isCollidedHorizontally && isOffsetPositionInLiquid(motionX, motionY + 0.6000000238418579D - posY + var10, motionZ))
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
				var5 = getAIMoveSpeed() * var8;
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
				float var11 = 0.15F;
				if(motionX < -var11)
				{
					motionX = -var11;
				}
				if(motionX > var11)
				{
					motionX = var11;
				}
				if(motionZ < -var11)
				{
					motionZ = -var11;
				}
				if(motionZ > var11)
				{
					motionZ = var11;
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
		var10 = posX - prevPosX;
		double var9 = posZ - prevPosZ;
		float var12 = MathHelper.sqrt_double(var10 * var10 + var9 * var9) * 4.0F;
		if(var12 > 1.0F)
		{
			var12 = 1.0F;
		}
		limbYaw += (var12 - limbYaw) * 0.4F;
		limbSwing += limbYaw;
	}
	
	protected void onChangedPotionEffect(PotionEffect par1PotionEffect, boolean par2)
	{
		potionsNeedUpdate = true;
		if(par2 && !worldObj.isRemote)
		{
			Potion.potionTypes[par1PotionEffect.getPotionID()].func_111187_a(this, func_110140_aT(), par1PotionEffect.getAmplifier());
			Potion.potionTypes[par1PotionEffect.getPotionID()].func_111185_a(this, func_110140_aT(), par1PotionEffect.getAmplifier());
		}
	}
	
	public void onDeath(DamageSource par1DamageSource)
	{
		Entity var2 = par1DamageSource.getEntity();
		EntityLivingBase var3 = func_94060_bK();
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
				var4 = EnchantmentHelper.getLootingModifier((EntityLivingBase) var2);
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
		worldObj.theProfiler.startSection("livingEntityBaseTick");
		if(isEntityAlive() && isEntityInsideOpaqueBlock())
		{
			attackEntityFrom(DamageSource.inWall, 1.0F);
		}
		if(isImmuneToFire() || worldObj.isRemote)
		{
			extinguish();
		}
		boolean var1 = this instanceof EntityPlayer && ((EntityPlayer) this).capabilities.disableDamage;
		if(isEntityAlive() && isInsideOfMaterial(Material.water))
		{
			if(!canBreatheUnderwater() && !this.isPotionActive(Potion.waterBreathing.id) && !var1)
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
					attackEntityFrom(DamageSource.drown, 2.0F);
				}
			}
			extinguish();
			if(!worldObj.isRemote && isRiding() && ridingEntity instanceof EntityLivingBase)
			{
				mountEntity((Entity) null);
			}
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
		if(func_110143_aJ() <= 0.0F)
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
		if(field_110150_bn != null && !field_110150_bn.isEntityAlive())
		{
			field_110150_bn = null;
		}
		if(entityLivingToAttack != null && !entityLivingToAttack.isEntityAlive())
		{
			setRevengeTarget((EntityLivingBase) null);
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
		if(!worldObj.isRemote)
		{
			Potion.potionTypes[par1PotionEffect.getPotionID()].func_111187_a(this, func_110140_aT(), par1PotionEffect.getAmplifier());
		}
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
			double var5 = posZ + (field_110152_bk - posZ) / newPosRotationIncrements;
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
		moveEntityWithHeading(moveStrafing, moveForward);
		worldObj.theProfiler.endSection();
		worldObj.theProfiler.startSection("push");
		if(!worldObj.isRemote)
		{
			collideWithNearbyEntities();
		}
		worldObj.theProfiler.endSection();
	}
	
	protected void onNewPotionEffect(PotionEffect par1PotionEffect)
	{
		potionsNeedUpdate = true;
		if(!worldObj.isRemote)
		{
			Potion.potionTypes[par1PotionEffect.getPotionID()].func_111185_a(this, func_110140_aT(), par1PotionEffect.getAmplifier());
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote)
		{
			int var1 = getArrowCountInEntity();
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
			for(int var2 = 0; var2 < 5; ++var2)
			{
				ItemStack var3 = previousEquipment[var2];
				ItemStack var4 = getCurrentItemOrArmor(var2);
				if(!ItemStack.areItemStacksEqual(var4, var3))
				{
					((WorldServer) worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet5PlayerInventory(entityId, var2, var4));
					if(var3 != null)
					{
						field_110155_d.func_111148_a(var3.func_111283_C());
					}
					if(var4 != null)
					{
						field_110155_d.func_111147_b(var4.func_111283_C());
					}
					previousEquipment[var2] = var4 == null ? null : var4.copy();
				}
			}
		}
		onLivingUpdate();
		double var9 = posX - prevPosX;
		double var10 = posZ - prevPosZ;
		float var5 = (float) (var9 * var9 + var10 * var10);
		float var6 = renderYawOffset;
		float var7 = 0.0F;
		field_70768_au = field_110154_aX;
		float var8 = 0.0F;
		if(var5 > 0.0025000002F)
		{
			var8 = 1.0F;
			var7 = (float) Math.sqrt(var5) * 3.0F;
			var6 = (float) Math.atan2(var10, var9) * 180.0F / (float) Math.PI - 90.0F;
		}
		if(swingProgress > 0.0F)
		{
			var6 = rotationYaw;
		}
		if(!onGround)
		{
			var8 = 0.0F;
		}
		field_110154_aX += (var8 - field_110154_aX) * 0.3F;
		worldObj.theProfiler.startSection("headTurn");
		var7 = func_110146_f(var6, var7);
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
	
	public MovingObjectPosition rayTrace(double par1, float par3)
	{
		Vec3 var4 = getPosition(par3);
		Vec3 var5 = getLook(par3);
		Vec3 var6 = var4.addVector(var5.xCoord * par1, var5.yCoord * par1, var5.zCoord * par1);
		return worldObj.clip(var4, var6);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		func_110149_m(par1NBTTagCompound.getFloat("AbsorptionAmount"));
		if(par1NBTTagCompound.hasKey("Attributes") && worldObj != null && !worldObj.isRemote)
		{
			SharedMonsterAttributes.func_111260_a(func_110140_aT(), par1NBTTagCompound.getTagList("Attributes"), worldObj == null ? null : worldObj.getWorldLogAgent());
		}
		if(par1NBTTagCompound.hasKey("ActiveEffects"))
		{
			NBTTagList var2 = par1NBTTagCompound.getTagList("ActiveEffects");
			for(int var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
				PotionEffect var5 = PotionEffect.readCustomPotionEffectFromNBT(var4);
				activePotionsMap.put(Integer.valueOf(var5.getPotionID()), var5);
			}
		}
		if(par1NBTTagCompound.hasKey("HealF"))
		{
			setEntityHealth(par1NBTTagCompound.getFloat("HealF"));
		} else
		{
			NBTBase var6 = par1NBTTagCompound.getTag("Health");
			if(var6 == null)
			{
				setEntityHealth(func_110138_aP());
			} else if(var6.getId() == 5)
			{
				setEntityHealth(((NBTTagFloat) var6).data);
			} else if(var6.getId() == 2)
			{
				setEntityHealth(((NBTTagShort) var6).data);
			}
		}
		hurtTime = par1NBTTagCompound.getShort("HurtTime");
		deathTime = par1NBTTagCompound.getShort("DeathTime");
		attackTime = par1NBTTagCompound.getShort("AttackTime");
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
		landMovementFactor = par1;
	}
	
	public final void setArrowCountInEntity(int par1)
	{
		dataWatcher.updateObject(9, Byte.valueOf((byte) par1));
	}
	
	@Override protected void setBeenAttacked()
	{
		velocityChanged = rand.nextDouble() >= func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e();
	}
	
	@Override public abstract void setCurrentItemOrArmor(int var1, ItemStack var2);
	
	public void setEntityHealth(float par1)
	{
		dataWatcher.updateObject(6, Float.valueOf(MathHelper.clamp_float(par1, 0.0F, func_110138_aP())));
	}
	
	public void setJumping(boolean par1)
	{
		isJumping = par1;
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		yOffset = 0.0F;
		newPosX = par1;
		newPosY = par3;
		field_110152_bk = par5;
		newRotationYaw = par7;
		newRotationPitch = par8;
		newPosRotationIncrements = par9;
	}
	
	public void setPositionAndUpdate(double par1, double par3, double par5)
	{
		setLocationAndAngles(par1, par3, par5, rotationYaw, rotationPitch);
	}
	
	public void setRevengeTarget(EntityLivingBase par1EntityLivingBase)
	{
		entityLivingToAttack = par1EntityLivingBase;
		revengeTimer = ticksExisted;
	}
	
	@Override public void setRotationYawHead(float par1)
	{
		rotationYawHead = par1;
	}
	
	@Override public void setSprinting(boolean par1)
	{
		super.setSprinting(par1);
		AttributeInstance var2 = func_110148_a(SharedMonsterAttributes.field_111263_d);
		if(var2.func_111127_a(field_110156_b) != null)
		{
			var2.func_111124_b(field_110157_c);
		}
		if(par1)
		{
			var2.func_111121_a(field_110157_c);
		}
	}
	
	public void swingItem()
	{
		if(!isSwingInProgress || field_110158_av >= getArmSwingAnimationEnd() / 2 || field_110158_av < 0)
		{
			field_110158_av = -1;
			isSwingInProgress = true;
			if(worldObj instanceof WorldServer)
			{
				((WorldServer) worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet18Animation(this, 1));
			}
		}
	}
	
	protected void updateAITasks()
	{
	}
	
	protected void updateAITick()
	{
	}
	
	protected void updateArmSwingProgress()
	{
		int var1 = getArmSwingAnimationEnd();
		if(isSwingInProgress)
		{
			++field_110158_av;
			if(field_110158_av >= var1)
			{
				field_110158_av = 0;
				isSwingInProgress = false;
			}
		} else
		{
			field_110158_av = 0;
		}
		swingProgress = (float) field_110158_av / (float) var1;
	}
	
	protected void updateEntityActionState()
	{
		++entityAge;
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
			if(!var3.onUpdate(this))
			{
				if(!worldObj.isRemote)
				{
					var1.remove();
					onFinishedPotionEffect(var3);
				}
			} else if(var3.getDuration() % 600 == 0)
			{
				onChangedPotionEffect(var3, false);
			}
		}
		int var11;
		if(potionsNeedUpdate)
		{
			if(!worldObj.isRemote)
			{
				if(activePotionsMap.isEmpty())
				{
					dataWatcher.updateObject(8, Byte.valueOf((byte) 0));
					dataWatcher.updateObject(7, Integer.valueOf(0));
					setInvisible(false);
				} else
				{
					var11 = PotionHelper.calcPotionLiquidColor(activePotionsMap.values());
					dataWatcher.updateObject(8, Byte.valueOf((byte) (PotionHelper.func_82817_b(activePotionsMap.values()) ? 1 : 0)));
					dataWatcher.updateObject(7, Integer.valueOf(var11));
					setInvisible(this.isPotionActive(Potion.invisibility.id));
				}
			}
			potionsNeedUpdate = false;
		}
		var11 = dataWatcher.getWatchableObjectInt(7);
		boolean var12 = dataWatcher.getWatchableObjectByte(8) > 0;
		if(var11 > 0)
		{
			boolean var4 = false;
			if(!isInvisible())
			{
				var4 = rand.nextBoolean();
			} else
			{
				var4 = rand.nextInt(15) == 0;
			}
			if(var12)
			{
				var4 &= rand.nextInt(5) == 0;
			}
			if(var4 && var11 > 0)
			{
				double var5 = (var11 >> 16 & 255) / 255.0D;
				double var7 = (var11 >> 8 & 255) / 255.0D;
				double var9 = (var11 >> 0 & 255) / 255.0D;
				worldObj.spawnParticle(var12 ? "mobSpellAmbient" : "mobSpell", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - yOffset, posZ + (rand.nextDouble() - 0.5D) * width, var5, var7, var9);
			}
		}
	}
	
	@Override public void updateRidden()
	{
		super.updateRidden();
		field_70768_au = field_110154_aX;
		field_110154_aX = 0.0F;
		fallDistance = 0.0F;
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setFloat("HealF", func_110143_aJ());
		par1NBTTagCompound.setShort("Health", (short) (int) Math.ceil(func_110143_aJ()));
		par1NBTTagCompound.setShort("HurtTime", (short) hurtTime);
		par1NBTTagCompound.setShort("DeathTime", (short) deathTime);
		par1NBTTagCompound.setShort("AttackTime", (short) attackTime);
		par1NBTTagCompound.setFloat("AbsorptionAmount", func_110139_bj());
		ItemStack[] var2 = getLastActiveItems();
		int var3 = var2.length;
		int var4;
		ItemStack var5;
		for(var4 = 0; var4 < var3; ++var4)
		{
			var5 = var2[var4];
			if(var5 != null)
			{
				field_110155_d.func_111148_a(var5.func_111283_C());
			}
		}
		par1NBTTagCompound.setTag("Attributes", SharedMonsterAttributes.func_111257_a(func_110140_aT()));
		var2 = getLastActiveItems();
		var3 = var2.length;
		for(var4 = 0; var4 < var3; ++var4)
		{
			var5 = var2[var4];
			if(var5 != null)
			{
				field_110155_d.func_111147_b(var5.func_111283_C());
			}
		}
		if(!activePotionsMap.isEmpty())
		{
			NBTTagList var6 = new NBTTagList();
			Iterator var7 = activePotionsMap.values().iterator();
			while(var7.hasNext())
			{
				PotionEffect var8 = (PotionEffect) var7.next();
				var6.appendTag(var8.writeCustomPotionEffectToNBT(new NBTTagCompound()));
			}
			par1NBTTagCompound.setTag("ActiveEffects", var6);
		}
	}
}
