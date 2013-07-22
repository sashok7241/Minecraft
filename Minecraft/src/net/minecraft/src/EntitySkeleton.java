package net.minecraft.src;

import java.util.Calendar;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 0.25F, 20, 60, 15.0F);
	private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.31F, false);
	
	public EntitySkeleton(World p_i3555_1_)
	{
		super(p_i3555_1_);
		texture = "/mob/skeleton.png";
		moveSpeed = 0.25F;
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIRestrictSun(this));
		tasks.addTask(3, new EntityAIFleeSun(this, moveSpeed));
		tasks.addTask(5, new EntityAIWander(this, moveSpeed));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		if(p_i3555_1_ != null && !p_i3555_1_.isRemote)
		{
			setCombatTask();
		}
	}
	
	@Override protected void addRandomArmor()
	{
		super.addRandomArmor();
		setCurrentItemOrArmor(0, new ItemStack(Item.bow));
	}
	
	@Override public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if(super.attackEntityAsMob(p_70652_1_))
		{
			if(getSkeletonType() == 1 && p_70652_1_ instanceof EntityLiving)
			{
				((EntityLiving) p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
			}
			return true;
		} else return false;
	}
	
	@Override public void attackEntityWithRangedAttack(EntityLiving p_82196_1_, float p_82196_2_)
	{
		EntityArrow var3 = new EntityArrow(worldObj, this, p_82196_1_, 1.6F, 14 - worldObj.difficultySetting * 4);
		int var4 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
		int var5 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
		var3.setDamage(p_82196_2_ * 2.0F + rand.nextGaussian() * 0.25D + worldObj.difficultySetting * 0.11F);
		if(var4 > 0)
		{
			var3.setDamage(var3.getDamage() + var4 * 0.5D + 0.5D);
		}
		if(var5 > 0)
		{
			var3.setKnockbackStrength(var5);
		}
		if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0 || getSkeletonType() == 1)
		{
			var3.setFire(100);
		}
		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(var3);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3;
		int var4;
		if(getSkeletonType() == 1)
		{
			var3 = rand.nextInt(3 + p_70628_2_) - 1;
			for(var4 = 0; var4 < var3; ++var4)
			{
				dropItem(Item.coal.itemID, 1);
			}
		} else
		{
			var3 = rand.nextInt(3 + p_70628_2_);
			for(var4 = 0; var4 < var3; ++var4)
			{
				dropItem(Item.arrow.itemID, 1);
			}
		}
		var3 = rand.nextInt(3 + p_70628_2_);
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.bone.itemID, 1);
		}
	}
	
	@Override protected void dropRareDrop(int p_70600_1_)
	{
		if(getSkeletonType() == 1)
		{
			entityDropItem(new ItemStack(Item.skull.itemID, 1, 1), 0.0F);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(13, new Byte((byte) 0));
	}
	
	@Override public int getAttackStrength(Entity p_82193_1_)
	{
		if(getSkeletonType() == 1)
		{
			ItemStack var2 = getHeldItem();
			int var3 = 4;
			if(var2 != null)
			{
				var3 += var2.getDamageVsEntity(this);
			}
			return var3;
		} else return super.getAttackStrength(p_82193_1_);
	}
	
	@Override public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.arrow.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 20;
	}
	
	public int getSkeletonType()
	{
		return dataWatcher.getWatchableObjectByte(13);
	}
	
	@Override public String getTexture()
	{
		return getSkeletonType() == 1 ? "/mob/skeleton_wither.png" : super.getTexture();
	}
	
	@Override public void initCreature()
	{
		if(worldObj.provider instanceof WorldProviderHell && getRNG().nextInt(5) > 0)
		{
			tasks.addTask(4, aiAttackOnCollide);
			setSkeletonType(1);
			setCurrentItemOrArmor(0, new ItemStack(Item.swordStone));
		} else
		{
			tasks.addTask(4, aiArrowAttack);
			addRandomArmor();
			enchantEquipment();
		}
		setCanPickUpLoot(rand.nextFloat() < pickUpLootProability[worldObj.difficultySetting]);
		if(getCurrentItemOrArmor(4) == null)
		{
			Calendar var1 = worldObj.getCurrentDate();
			if(var1.get(2) + 1 == 10 && var1.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public void onDeath(DamageSource p_70645_1_)
	{
		super.onDeath(p_70645_1_);
		if(p_70645_1_.getSourceOfDamage() instanceof EntityArrow && p_70645_1_.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer var2 = (EntityPlayer) p_70645_1_.getEntity();
			double var3 = var2.posX - posX;
			double var5 = var2.posZ - posZ;
			if(var3 * var3 + var5 * var5 >= 2500.0D)
			{
				var2.triggerAchievement(AchievementList.snipeSkeleton);
			}
		}
	}
	
	@Override public void onLivingUpdate()
	{
		if(worldObj.isDaytime() && !worldObj.isRemote)
		{
			float var1 = getBrightness(1.0F);
			if(var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
			{
				boolean var2 = true;
				ItemStack var3 = getCurrentItemOrArmor(4);
				if(var3 != null)
				{
					if(var3.isItemStackDamageable())
					{
						var3.setItemDamage(var3.getItemDamageForDisplay() + rand.nextInt(2));
						if(var3.getItemDamageForDisplay() >= var3.getMaxDamage())
						{
							renderBrokenItemStack(var3);
							setCurrentItemOrArmor(4, (ItemStack) null);
						}
					}
					var2 = false;
				}
				if(var2)
				{
					setFire(8);
				}
			}
		}
		if(worldObj.isRemote && getSkeletonType() == 1)
		{
			setSize(0.72F, 2.34F);
		}
		super.onLivingUpdate();
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.skeleton.step", 0.15F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.hasKey("SkeletonType"))
		{
			byte var2 = p_70037_1_.getByte("SkeletonType");
			setSkeletonType(var2);
		}
		setCombatTask();
	}
	
	public void setCombatTask()
	{
		tasks.removeTask(aiAttackOnCollide);
		tasks.removeTask(aiArrowAttack);
		ItemStack var1 = getHeldItem();
		if(var1 != null && var1.itemID == Item.bow.itemID)
		{
			tasks.addTask(4, aiArrowAttack);
		} else
		{
			tasks.addTask(4, aiAttackOnCollide);
		}
	}
	
	@Override public void setCurrentItemOrArmor(int p_70062_1_, ItemStack p_70062_2_)
	{
		super.setCurrentItemOrArmor(p_70062_1_, p_70062_2_);
		if(!worldObj.isRemote && p_70062_1_ == 0)
		{
			setCombatTask();
		}
	}
	
	public void setSkeletonType(int p_82201_1_)
	{
		dataWatcher.updateObject(13, Byte.valueOf((byte) p_82201_1_));
		isImmuneToFire = p_82201_1_ == 1;
		if(p_82201_1_ == 1)
		{
			setSize(0.72F, 2.34F);
		} else
		{
			setSize(0.6F, 1.8F);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setByte("SkeletonType", (byte) getSkeletonType());
	}
}
