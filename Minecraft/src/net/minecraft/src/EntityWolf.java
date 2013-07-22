package net.minecraft.src;

public class EntityWolf extends EntityTameable
{
	private float field_70926_e;
	private float field_70924_f;
	private boolean isShaking;
	private boolean field_70928_h;
	private float timeWolfIsShaking;
	private float prevTimeWolfIsShaking;
	
	public EntityWolf(World par1World)
	{
		super(par1World);
		setSize(0.6F, 0.8F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
		tasks.addTask(6, new EntityAIMate(this, 1.0D));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIBeg(this, 8.0F));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(9, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(4, new EntityAITargetNonTamed(this, EntitySheep.class, 200, false));
		setTamed(false);
	}
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		int var2 = isTamed() ? 4 : 2;
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			Entity var3 = par1DamageSource.getEntity();
			aiSit.setSitting(false);
			if(var3 != null && !(var3 instanceof EntityPlayer) && !(var3 instanceof EntityArrow))
			{
				par2 = (par2 + 1.0F) / 2.0F;
			}
			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return !isTamed() && ticksExisted > 2400;
	}
	
	@Override public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if(par1EntityAnimal == this) return false;
		else if(!isTamed()) return false;
		else if(!(par1EntityAnimal instanceof EntityWolf)) return false;
		else
		{
			EntityWolf var2 = (EntityWolf) par1EntityAnimal;
			return !var2.isTamed() ? false : var2.isSitting() ? false : isInLove() && var2.isInLove();
		}
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return spawnBabyAnimal(par1EntityAgeable);
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(18, new Float(func_110143_aJ()));
		dataWatcher.addObject(19, new Byte((byte) 0));
		dataWatcher.addObject(20, new Byte((byte) BlockColored.getBlockFromDye(1)));
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
		if(isTamed())
		{
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
		} else
		{
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
		}
	}
	
	@Override public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase)
	{
		if(!(par1EntityLivingBase instanceof EntityCreeper) && !(par1EntityLivingBase instanceof EntityGhast))
		{
			if(par1EntityLivingBase instanceof EntityWolf)
			{
				EntityWolf var3 = (EntityWolf) par1EntityLivingBase;
				if(var3.isTamed() && var3.func_130012_q() == par2EntityLivingBase) return false;
			}
			return par1EntityLivingBase instanceof EntityPlayer && par2EntityLivingBase instanceof EntityPlayer && !((EntityPlayer) par2EntityLivingBase).func_96122_a((EntityPlayer) par1EntityLivingBase) ? false : !(par1EntityLivingBase instanceof EntityHorse) || !((EntityHorse) par1EntityLivingBase).func_110248_bS();
		} else return false;
	}
	
	public void func_70918_i(boolean par1)
	{
		if(par1)
		{
			dataWatcher.updateObject(19, Byte.valueOf((byte) 1));
		} else
		{
			dataWatcher.updateObject(19, Byte.valueOf((byte) 0));
		}
	}
	
	public boolean func_70922_bv()
	{
		return dataWatcher.getWatchableObjectByte(19) == 1;
	}
	
	public int getCollarColor()
	{
		return dataWatcher.getWatchableObjectByte(20) & 15;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.wolf.death";
	}
	
	@Override protected int getDropItemId()
	{
		return -1;
	}
	
	@Override public float getEyeHeight()
	{
		return height * 0.8F;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.wolf.hurt";
	}
	
	public float getInterestedAngle(float par1)
	{
		return (field_70924_f + (field_70926_e - field_70924_f) * par1) * 0.15F * (float) Math.PI;
	}
	
	@Override protected String getLivingSound()
	{
		return isAngry() ? "mob.wolf.growl" : rand.nextInt(3) == 0 ? isTamed() && dataWatcher.func_111145_d(18) < 10.0F ? "mob.wolf.whine" : "mob.wolf.panting" : "mob.wolf.bark";
	}
	
	@Override public int getMaxSpawnedInChunk()
	{
		return 8;
	}
	
	public float getShadingWhileShaking(float par1)
	{
		return 0.75F + (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * par1) / 2.0F * 0.25F;
	}
	
	public float getShakeAngle(float par1, float par2)
	{
		float var3 = (prevTimeWolfIsShaking + (timeWolfIsShaking - prevTimeWolfIsShaking) * par1 + par2) / 1.8F;
		if(var3 < 0.0F)
		{
			var3 = 0.0F;
		} else if(var3 > 1.0F)
		{
			var3 = 1.0F;
		}
		return MathHelper.sin(var3 * (float) Math.PI) * MathHelper.sin(var3 * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	public float getTailRotation()
	{
		return isAngry() ? 1.5393804F : isTamed() ? (0.55F - (20.0F - dataWatcher.func_111145_d(18)) * 0.02F) * (float) Math.PI : (float) Math.PI / 5F;
	}
	
	@Override public int getVerticalFaceSpeed()
	{
		return isSitting() ? 20 : super.getVerticalFaceSpeed();
	}
	
	public boolean getWolfShaking()
	{
		return isShaking;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 8)
		{
			field_70928_h = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(isTamed())
		{
			if(var2 != null)
			{
				if(Item.itemsList[var2.itemID] instanceof ItemFood)
				{
					ItemFood var3 = (ItemFood) Item.itemsList[var2.itemID];
					if(var3.isWolfsFavoriteMeat() && dataWatcher.func_111145_d(18) < 20.0F)
					{
						if(!par1EntityPlayer.capabilities.isCreativeMode)
						{
							--var2.stackSize;
						}
						heal(var3.getHealAmount());
						if(var2.stackSize <= 0)
						{
							par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
						}
						return true;
					}
				} else if(var2.itemID == Item.dyePowder.itemID)
				{
					int var4 = BlockColored.getBlockFromDye(var2.getItemDamage());
					if(var4 != getCollarColor())
					{
						setCollarColor(var4);
						if(!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize <= 0)
						{
							par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
						}
						return true;
					}
				}
			}
			if(par1EntityPlayer.getCommandSenderName().equalsIgnoreCase(getOwnerName()) && !worldObj.isRemote && !isBreedingItem(var2))
			{
				aiSit.setSitting(!isSitting());
				isJumping = false;
				setPathToEntity((PathEntity) null);
				setTarget((Entity) null);
				setAttackTarget((EntityLivingBase) null);
			}
		} else if(var2 != null && var2.itemID == Item.bone.itemID && !isAngry())
		{
			if(!par1EntityPlayer.capabilities.isCreativeMode)
			{
				--var2.stackSize;
			}
			if(var2.stackSize <= 0)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
			}
			if(!worldObj.isRemote)
			{
				if(rand.nextInt(3) == 0)
				{
					setTamed(true);
					setPathToEntity((PathEntity) null);
					setAttackTarget((EntityLivingBase) null);
					aiSit.setSitting(true);
					setEntityHealth(20.0F);
					setOwner(par1EntityPlayer.getCommandSenderName());
					playTameEffect(true);
					worldObj.setEntityState(this, (byte) 7);
				} else
				{
					playTameEffect(false);
					worldObj.setEntityState(this, (byte) 6);
				}
			}
			return true;
		}
		return super.interact(par1EntityPlayer);
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	public boolean isAngry()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 2) != 0;
	}
	
	@Override public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? false : !(Item.itemsList[par1ItemStack.itemID] instanceof ItemFood) ? false : ((ItemFood) Item.itemsList[par1ItemStack.itemID]).isWolfsFavoriteMeat();
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(!worldObj.isRemote && isShaking && !field_70928_h && !hasPath() && onGround)
		{
			field_70928_h = true;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
			worldObj.setEntityState(this, (byte) 8);
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		field_70924_f = field_70926_e;
		if(func_70922_bv())
		{
			field_70926_e += (1.0F - field_70926_e) * 0.4F;
		} else
		{
			field_70926_e += (0.0F - field_70926_e) * 0.4F;
		}
		if(func_70922_bv())
		{
			numTicksToChaseTarget = 10;
		}
		if(isWet())
		{
			isShaking = true;
			field_70928_h = false;
			timeWolfIsShaking = 0.0F;
			prevTimeWolfIsShaking = 0.0F;
		} else if((isShaking || field_70928_h) && field_70928_h)
		{
			if(timeWolfIsShaking == 0.0F)
			{
				playSound("mob.wolf.shake", getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}
			prevTimeWolfIsShaking = timeWolfIsShaking;
			timeWolfIsShaking += 0.05F;
			if(prevTimeWolfIsShaking >= 2.0F)
			{
				isShaking = false;
				field_70928_h = false;
				prevTimeWolfIsShaking = 0.0F;
				timeWolfIsShaking = 0.0F;
			}
			if(timeWolfIsShaking > 0.4F)
			{
				float var1 = (float) boundingBox.minY;
				int var2 = (int) (MathHelper.sin((timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);
				for(int var3 = 0; var3 < var2; ++var3)
				{
					float var4 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
					float var5 = (rand.nextFloat() * 2.0F - 1.0F) * width * 0.5F;
					worldObj.spawnParticle("splash", posX + var4, var1 + 0.8F, posZ + var5, motionX, motionY, motionZ);
				}
			}
		}
	}
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.wolf.step", 0.15F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setAngry(par1NBTTagCompound.getBoolean("Angry"));
		if(par1NBTTagCompound.hasKey("CollarColor"))
		{
			setCollarColor(par1NBTTagCompound.getByte("CollarColor"));
		}
	}
	
	public void setAngry(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(par1)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 2)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -3)));
		}
	}
	
	@Override public void setAttackTarget(EntityLivingBase par1EntityLivingBase)
	{
		super.setAttackTarget(par1EntityLivingBase);
		if(par1EntityLivingBase == null)
		{
			setAngry(false);
		} else if(!isTamed())
		{
			setAngry(true);
		}
	}
	
	public void setCollarColor(int par1)
	{
		dataWatcher.updateObject(20, Byte.valueOf((byte) (par1 & 15)));
	}
	
	@Override public void setTamed(boolean par1)
	{
		super.setTamed(par1);
		if(par1)
		{
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
		} else
		{
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
		}
	}
	
	public EntityWolf spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		EntityWolf var2 = new EntityWolf(worldObj);
		String var3 = getOwnerName();
		if(var3 != null && var3.trim().length() > 0)
		{
			var2.setOwner(var3);
			var2.setTamed(true);
		}
		return var2;
	}
	
	@Override protected void updateAITick()
	{
		dataWatcher.updateObject(18, Float.valueOf(func_110143_aJ()));
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Angry", isAngry());
		par1NBTTagCompound.setByte("CollarColor", (byte) getCollarColor());
	}
}
