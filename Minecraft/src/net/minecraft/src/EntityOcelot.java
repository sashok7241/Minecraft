package net.minecraft.src;

public class EntityOcelot extends EntityTameable
{
	private EntityAITempt aiTempt;
	
	public EntityOcelot(World p_i3519_1_)
	{
		super(p_i3519_1_);
		texture = "/mob/ozelot.png";
		setSize(0.6F, 0.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, aiTempt = new EntityAITempt(this, 0.18F, Item.fishRaw.itemID, true));
		tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.23F, 0.4F));
		tasks.addTask(5, new EntityAIFollowOwner(this, 0.3F, 10.0F, 5.0F));
		tasks.addTask(6, new EntityAIOcelotSit(this, 0.4F));
		tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
		tasks.addTask(8, new EntityAIOcelotAttack(this));
		tasks.addTask(9, new EntityAIMate(this, 0.23F));
		tasks.addTask(10, new EntityAIWander(this, 0.23F));
		tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 14.0F, 750, false));
	}
	
	@Override public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		return p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			aiSit.setSitting(false);
			return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return !isTamed();
	}
	
	@Override public boolean canMateWith(EntityAnimal p_70878_1_)
	{
		if(p_70878_1_ == this) return false;
		else if(!isTamed()) return false;
		else if(!(p_70878_1_ instanceof EntityOcelot)) return false;
		else
		{
			EntityOcelot var2 = (EntityOcelot) p_70878_1_;
			return !var2.isTamed() ? false : isInLove() && var2.isInLove();
		}
	}
	
	@Override public EntityAgeable createChild(EntityAgeable p_90011_1_)
	{
		return spawnBabyAnimal(p_90011_1_);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(18, Byte.valueOf((byte) 0));
	}
	
	@Override protected void fall(float p_70069_1_)
	{
	}
	
	@Override public boolean getCanSpawnHere()
	{
		if(worldObj.rand.nextInt(3) == 0) return false;
		else
		{
			if(worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox))
			{
				int var1 = MathHelper.floor_double(posX);
				int var2 = MathHelper.floor_double(boundingBox.minY);
				int var3 = MathHelper.floor_double(posZ);
				if(var2 < 63) return false;
				int var4 = worldObj.getBlockId(var1, var2 - 1, var3);
				if(var4 == Block.grass.blockID || var4 == Block.leaves.blockID) return true;
			}
			return false;
		}
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.cat.hitt";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.leather.itemID;
	}
	
	@Override public String getEntityName()
	{
		return hasCustomNameTag() ? getCustomNameTag() : isTamed() ? "entity.Cat.name" : super.getEntityName();
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.cat.hitt";
	}
	
	@Override protected String getLivingSound()
	{
		return isTamed() ? isInLove() ? "mob.cat.purr" : rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow" : "";
	}
	
	@Override public int getMaxHealth()
	{
		return 10;
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	public int getTameSkin()
	{
		return dataWatcher.getWatchableObjectByte(18);
	}
	
	@Override public String getTexture()
	{
		switch(getTameSkin())
		{
			case 0:
				return "/mob/ozelot.png";
			case 1:
				return "/mob/cat_black.png";
			case 2:
				return "/mob/cat_red.png";
			case 3:
				return "/mob/cat_siamese.png";
			default:
				return super.getTexture();
		}
	}
	
	@Override public void initCreature()
	{
		if(worldObj.rand.nextInt(7) == 0)
		{
			for(int var1 = 0; var1 < 2; ++var1)
			{
				EntityOcelot var2 = new EntityOcelot(worldObj);
				var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				var2.setGrowingAge(-24000);
				worldObj.spawnEntityInWorld(var2);
			}
		}
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
		if(isTamed())
		{
			if(p_70085_1_.username.equalsIgnoreCase(getOwnerName()) && !worldObj.isRemote && !isBreedingItem(var2))
			{
				aiSit.setSitting(!isSitting());
			}
		} else if(aiTempt.func_75277_f() && var2 != null && var2.itemID == Item.fishRaw.itemID && p_70085_1_.getDistanceSqToEntity(this) < 9.0D)
		{
			if(!p_70085_1_.capabilities.isCreativeMode)
			{
				--var2.stackSize;
			}
			if(var2.stackSize <= 0)
			{
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, (ItemStack) null);
			}
			if(!worldObj.isRemote)
			{
				if(rand.nextInt(3) == 0)
				{
					setTamed(true);
					setTameSkin(1 + worldObj.rand.nextInt(3));
					setOwner(p_70085_1_.username);
					playTameEffect(true);
					aiSit.setSitting(true);
					worldObj.setEntityState(this, (byte) 7);
				} else
				{
					playTameEffect(false);
					worldObj.setEntityState(this, (byte) 6);
				}
			}
			return true;
		}
		return super.interact(p_70085_1_);
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public boolean isBreedingItem(ItemStack p_70877_1_)
	{
		return p_70877_1_ != null && p_70877_1_.itemID == Item.fishRaw.itemID;
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		setTameSkin(p_70037_1_.getInteger("CatType"));
	}
	
	public void setTameSkin(int p_70912_1_)
	{
		dataWatcher.updateObject(18, Byte.valueOf((byte) p_70912_1_));
	}
	
	public EntityOcelot spawnBabyAnimal(EntityAgeable p_70879_1_)
	{
		EntityOcelot var2 = new EntityOcelot(worldObj);
		if(isTamed())
		{
			var2.setOwner(getOwnerName());
			var2.setTamed(true);
			var2.setTameSkin(getTameSkin());
		}
		return var2;
	}
	
	@Override public void updateAITick()
	{
		if(getMoveHelper().isUpdating())
		{
			float var1 = getMoveHelper().getSpeed();
			if(var1 == 0.18F)
			{
				setSneaking(true);
				setSprinting(false);
			} else if(var1 == 0.4F)
			{
				setSneaking(false);
				setSprinting(true);
			} else
			{
				setSneaking(false);
				setSprinting(false);
			}
		} else
		{
			setSneaking(false);
			setSprinting(false);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("CatType", getTameSkin());
	}
}
