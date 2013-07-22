package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class EntityLiving extends EntityLivingBase
{
	public int livingSoundTime;
	protected int experienceValue;
	private EntityLookHelper lookHelper;
	private EntityMoveHelper moveHelper;
	private EntityJumpHelper jumpHelper;
	private EntityBodyHelper bodyHelper;
	private PathNavigate navigator;
	protected final EntityAITasks tasks;
	protected final EntityAITasks targetTasks;
	private EntityLivingBase attackTarget;
	private EntitySenses senses;
	private ItemStack[] equipment = new ItemStack[5];
	protected float[] equipmentDropChances = new float[5];
	private boolean canPickUpLoot;
	private boolean persistenceRequired;
	protected float defaultPitch;
	private Entity currentTarget;
	protected int numTicksToChaseTarget;
	private boolean field_110169_bv;
	private Entity field_110168_bw;
	private NBTTagCompound field_110170_bx;
	
	public EntityLiving(World par1World)
	{
		super(par1World);
		tasks = new EntityAITasks(par1World != null && par1World.theProfiler != null ? par1World.theProfiler : null);
		targetTasks = new EntityAITasks(par1World != null && par1World.theProfiler != null ? par1World.theProfiler : null);
		lookHelper = new EntityLookHelper(this);
		moveHelper = new EntityMoveHelper(this);
		jumpHelper = new EntityJumpHelper(this);
		bodyHelper = new EntityBodyHelper(this);
		navigator = new PathNavigate(this, par1World);
		senses = new EntitySenses(this);
		for(int var2 = 0; var2 < equipmentDropChances.length; ++var2)
		{
			equipmentDropChances[var2] = 0.085F;
		}
	}
	
	protected void addRandomArmor()
	{
		if(rand.nextFloat() < 0.15F * worldObj.func_110746_b(posX, posY, posZ))
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
				ItemStack var4 = func_130225_q(var3);
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
	
	public boolean canAttackClass(Class par1Class)
	{
		return EntityCreeper.class != par1Class && EntityGhast.class != par1Class;
	}
	
	public boolean canBeSteered()
	{
		return false;
	}
	
	protected boolean canDespawn()
	{
		return true;
	}
	
	public boolean canPickUpLoot()
	{
		return canPickUpLoot;
	}
	
	protected void despawnEntity()
	{
		if(persistenceRequired)
		{
			entityAge = 0;
		} else
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
	
	@Override protected void dropEquipment(boolean par1, int par2)
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
	
	@Override protected void dropFewItems(boolean par1, int par2)
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
	
	public void eatGrassBonus()
	{
	}
	
	protected void enchantEquipment()
	{
		float var1 = worldObj.func_110746_b(posX, posY, posZ);
		if(getHeldItem() != null && rand.nextFloat() < 0.25F * var1)
		{
			EnchantmentHelper.addRandomEnchantment(rand, getHeldItem(), (int) (5.0F + var1 * rand.nextInt(18)));
		}
		for(int var2 = 0; var2 < 4; ++var2)
		{
			ItemStack var3 = func_130225_q(var2);
			if(var3 != null && rand.nextFloat() < 0.5F * var1)
			{
				EnchantmentHelper.addRandomEnchantment(rand, var3, (int) (5.0F + var1 * rand.nextInt(18)));
			}
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(11, Byte.valueOf((byte) 0));
		dataWatcher.addObject(10, "");
	}
	
	public void faceEntity(Entity par1Entity, float par2, float par3)
	{
		double var4 = par1Entity.posX - posX;
		double var8 = par1Entity.posZ - posZ;
		double var6;
		if(par1Entity instanceof EntityLivingBase)
		{
			EntityLivingBase var10 = (EntityLivingBase) par1Entity;
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
	
	public boolean func_104002_bU()
	{
		return persistenceRequired;
	}
	
	@Override protected float func_110146_f(float par1, float par2)
	{
		if(isAIEnabled())
		{
			bodyHelper.func_75664_a();
			return par2;
		} else return super.func_110146_f(par1, par2);
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D);
	}
	
	protected void func_110159_bB()
	{
		if(field_110170_bx != null)
		{
			func_110165_bF();
		}
		if(field_110169_bv)
		{
			if(field_110168_bw == null || field_110168_bw.isDead)
			{
				func_110160_i(true, true);
			}
		}
	}
	
	public void func_110160_i(boolean par1, boolean par2)
	{
		if(field_110169_bv)
		{
			field_110169_bv = false;
			field_110168_bw = null;
			if(!worldObj.isRemote && par2)
			{
				dropItem(Item.field_111214_ch.itemID, 1);
			}
			if(!worldObj.isRemote && par1 && worldObj instanceof WorldServer)
			{
				((WorldServer) worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet39AttachEntity(1, this, (Entity) null));
			}
		}
	}
	
	public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random spawn bonus", rand.nextGaussian() * 0.05D, 1));
		return par1EntityLivingData;
	}
	
	public void func_110162_b(Entity par1Entity, boolean par2)
	{
		field_110169_bv = true;
		field_110168_bw = par1Entity;
		if(!worldObj.isRemote && par2 && worldObj instanceof WorldServer)
		{
			((WorldServer) worldObj).getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, new Packet39AttachEntity(1, this, field_110168_bw));
		}
	}
	
	public void func_110163_bv()
	{
		persistenceRequired = true;
	}
	
	public boolean func_110164_bC()
	{
		return !func_110167_bD() && !(this instanceof IMob);
	}
	
	private void func_110165_bF()
	{
		if(field_110169_bv && field_110170_bx != null)
		{
			if(field_110170_bx.hasKey("UUIDMost") && field_110170_bx.hasKey("UUIDLeast"))
			{
				UUID var5 = new UUID(field_110170_bx.getLong("UUIDMost"), field_110170_bx.getLong("UUIDLeast"));
				List var6 = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox.expand(10.0D, 10.0D, 10.0D));
				Iterator var7 = var6.iterator();
				while(var7.hasNext())
				{
					EntityLivingBase var8 = (EntityLivingBase) var7.next();
					if(var8.func_110124_au().equals(var5))
					{
						field_110168_bw = var8;
						break;
					}
				}
			} else if(field_110170_bx.hasKey("X") && field_110170_bx.hasKey("Y") && field_110170_bx.hasKey("Z"))
			{
				int var1 = field_110170_bx.getInteger("X");
				int var2 = field_110170_bx.getInteger("Y");
				int var3 = field_110170_bx.getInteger("Z");
				EntityLeashKnot var4 = EntityLeashKnot.func_110130_b(worldObj, var1, var2, var3);
				if(var4 == null)
				{
					var4 = EntityLeashKnot.func_110129_a(worldObj, var1, var2, var3);
				}
				field_110168_bw = var4;
			} else
			{
				func_110160_i(false, true);
			}
		}
		field_110170_bx = null;
	}
	
	public Entity func_110166_bE()
	{
		return field_110168_bw;
	}
	
	public boolean func_110167_bD()
	{
		return field_110169_bv;
	}
	
	@Override public final boolean func_130002_c(EntityPlayer par1EntityPlayer)
	{
		if(func_110167_bD() && func_110166_bE() == par1EntityPlayer)
		{
			func_110160_i(true, !par1EntityPlayer.capabilities.isCreativeMode);
			return true;
		} else
		{
			ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
			if(var2 != null && var2.itemID == Item.field_111214_ch.itemID && func_110164_bC())
			{
				if(!(this instanceof EntityTameable) || !((EntityTameable) this).isTamed())
				{
					func_110162_b(par1EntityPlayer, true);
					--var2.stackSize;
					return true;
				}
				if(par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(((EntityTameable) this).getOwnerName()))
				{
					func_110162_b(par1EntityPlayer, true);
					--var2.stackSize;
					return true;
				}
			}
			return interact(par1EntityPlayer) ? true : super.func_130002_c(par1EntityPlayer);
		}
	}
	
	public ItemStack func_130225_q(int var1)
	{
		return equipment[var1 + 1];
	}
	
	@Override public int func_82143_as()
	{
		if(getAttackTarget() == null) return 3;
		else
		{
			int var1 = (int) (func_110143_aJ() - func_110138_aP() * 0.33F);
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
		dataWatcher.updateObject(10, par1Str);
	}
	
	public void func_94061_f(boolean par1)
	{
		dataWatcher.updateObject(11, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
	
	public boolean func_94062_bN()
	{
		return dataWatcher.getWatchableObjectByte(11) == 1;
	}
	
	@Override public boolean getAlwaysRenderNameTagForRender()
	{
		return func_94062_bN();
	}
	
	public EntityLivingBase getAttackTarget()
	{
		return attackTarget;
	}
	
	public boolean getCanSpawnHere()
	{
		return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}
	
	@Override public ItemStack getCurrentItemOrArmor(int par1)
	{
		return equipment[par1];
	}
	
	public String getCustomNameTag()
	{
		return dataWatcher.getWatchableObjectString(10);
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
	
	@Override protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
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
	
	@Override public ItemStack getHeldItem()
	{
		return equipment[0];
	}
	
	public EntityJumpHelper getJumpHelper()
	{
		return jumpHelper;
	}
	
	@Override public ItemStack[] getLastActiveItems()
	{
		return equipment;
	}
	
	protected String getLivingSound()
	{
		return null;
	}
	
	public EntityLookHelper getLookHelper()
	{
		return lookHelper;
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
	
	public float getRenderSizeModifier()
	{
		return 1.0F;
	}
	
	public int getTalkInterval()
	{
		return 80;
	}
	
	public int getVerticalFaceSpeed()
	{
		return 40;
	}
	
	public boolean hasCustomNameTag()
	{
		return dataWatcher.getWatchableObjectString(10).length() > 0;
	}
	
	protected boolean interact(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
	
	@Override protected boolean isAIEnabled()
	{
		return false;
	}
	
	@Override public void onEntityUpdate()
	{
		super.onEntityUpdate();
		worldObj.theProfiler.startSection("mobBaseTick");
		if(isEntityAlive() && rand.nextInt(1000) < livingSoundTime++)
		{
			livingSoundTime = -getTalkInterval();
			playLivingSound();
		}
		worldObj.theProfiler.endSection();
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		worldObj.theProfiler.startSection("looting");
		if(!worldObj.isRemote && canPickUpLoot() && !dead && worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			List var1 = worldObj.getEntitiesWithinAABB(EntityItem.class, boundingBox.expand(1.0D, 0.0D, 1.0D));
			Iterator var2 = var1.iterator();
			while(var2.hasNext())
			{
				EntityItem var3 = (EntityItem) var2.next();
				if(!var3.isDead && var3.getEntityItem() != null)
				{
					ItemStack var4 = var3.getEntityItem();
					int var5 = getArmorPosition(var4);
					if(var5 > -1)
					{
						boolean var6 = true;
						ItemStack var7 = getCurrentItemOrArmor(var5);
						if(var7 != null)
						{
							if(var5 == 0)
							{
								if(var4.getItem() instanceof ItemSword && !(var7.getItem() instanceof ItemSword))
								{
									var6 = true;
								} else if(var4.getItem() instanceof ItemSword && var7.getItem() instanceof ItemSword)
								{
									ItemSword var8 = (ItemSword) var4.getItem();
									ItemSword var9 = (ItemSword) var7.getItem();
									if(var8.func_82803_g() == var9.func_82803_g())
									{
										var6 = var4.getItemDamage() > var7.getItemDamage() || var4.hasTagCompound() && !var7.hasTagCompound();
									} else
									{
										var6 = var8.func_82803_g() > var9.func_82803_g();
									}
								} else
								{
									var6 = false;
								}
							} else if(var4.getItem() instanceof ItemArmor && !(var7.getItem() instanceof ItemArmor))
							{
								var6 = true;
							} else if(var4.getItem() instanceof ItemArmor && var7.getItem() instanceof ItemArmor)
							{
								ItemArmor var10 = (ItemArmor) var4.getItem();
								ItemArmor var11 = (ItemArmor) var7.getItem();
								if(var10.damageReduceAmount == var11.damageReduceAmount)
								{
									var6 = var4.getItemDamage() > var7.getItemDamage() || var4.hasTagCompound() && !var7.hasTagCompound();
								} else
								{
									var6 = var10.damageReduceAmount > var11.damageReduceAmount;
								}
							} else
							{
								var6 = false;
							}
						}
						if(var6)
						{
							if(var7 != null && rand.nextFloat() - 0.1F < equipmentDropChances[var5])
							{
								entityDropItem(var7, 0.0F);
							}
							setCurrentItemOrArmor(var5, var4);
							equipmentDropChances[var5] = 2.0F;
							persistenceRequired = true;
							onItemPickup(var3, 1);
							var3.setDead();
						}
					}
				}
			}
		}
		worldObj.theProfiler.endSection();
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote)
		{
			func_110159_bB();
		}
	}
	
	public void playLivingSound()
	{
		String var1 = getLivingSound();
		if(var1 != null)
		{
			playSound(var1, getSoundVolume(), getSoundPitch());
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
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
		if(par1NBTTagCompound.hasKey("DropChances"))
		{
			var2 = par1NBTTagCompound.getTagList("DropChances");
			for(var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				equipmentDropChances[var3] = ((NBTTagFloat) var2.tagAt(var3)).data;
			}
		}
		field_110169_bv = par1NBTTagCompound.getBoolean("Leashed");
		if(field_110169_bv && par1NBTTagCompound.hasKey("Leash"))
		{
			field_110170_bx = par1NBTTagCompound.getCompoundTag("Leash");
		}
	}
	
	@Override public void setAIMoveSpeed(float par1)
	{
		super.setAIMoveSpeed(par1);
		setMoveForward(par1);
	}
	
	public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
	{
		attackTarget = par1EntityLivingBase;
	}
	
	public void setCanPickUpLoot(boolean par1)
	{
		canPickUpLoot = par1;
	}
	
	@Override public void setCurrentItemOrArmor(int par1, ItemStack par2ItemStack)
	{
		equipment[par1] = par2ItemStack;
	}
	
	public void setEquipmentDropChance(int par1, float par2)
	{
		equipmentDropChances[par1] = par2;
	}
	
	public void setMoveForward(float par1)
	{
		moveForward = par1;
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
	
	@Override protected void updateAITasks()
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
	
	@Override protected void updateEntityActionState()
	{
		super.updateEntityActionState();
		moveStrafing = 0.0F;
		moveForward = 0.0F;
		despawnEntity();
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
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("CanPickUpLoot", canPickUpLoot());
		par1NBTTagCompound.setBoolean("PersistenceRequired", persistenceRequired);
		NBTTagList var2 = new NBTTagList();
		NBTTagCompound var4;
		for(ItemStack element : equipment)
		{
			var4 = new NBTTagCompound();
			if(element != null)
			{
				element.writeToNBT(var4);
			}
			var2.appendTag(var4);
		}
		par1NBTTagCompound.setTag("Equipment", var2);
		NBTTagList var6 = new NBTTagList();
		for(int var7 = 0; var7 < equipmentDropChances.length; ++var7)
		{
			var6.appendTag(new NBTTagFloat(var7 + "", equipmentDropChances[var7]));
		}
		par1NBTTagCompound.setTag("DropChances", var6);
		par1NBTTagCompound.setString("CustomName", getCustomNameTag());
		par1NBTTagCompound.setBoolean("CustomNameVisible", func_94062_bN());
		par1NBTTagCompound.setBoolean("Leashed", field_110169_bv);
		if(field_110168_bw != null)
		{
			var4 = new NBTTagCompound("Leash");
			if(field_110168_bw instanceof EntityLivingBase)
			{
				var4.setLong("UUIDMost", field_110168_bw.func_110124_au().getMostSignificantBits());
				var4.setLong("UUIDLeast", field_110168_bw.func_110124_au().getLeastSignificantBits());
			} else if(field_110168_bw instanceof EntityHanging)
			{
				EntityHanging var5 = (EntityHanging) field_110168_bw;
				var4.setInteger("X", var5.xPosition);
				var4.setInteger("Y", var5.yPosition);
				var4.setInteger("Z", var5.zPosition);
			}
			par1NBTTagCompound.setTag("Leash", var4);
		}
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
