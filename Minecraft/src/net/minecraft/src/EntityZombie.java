package net.minecraft.src;

import java.util.Calendar;

public class EntityZombie extends EntityMob
{
	private int conversionTime = 0;
	
	public EntityZombie(World p_i3558_1_)
	{
		super(p_i3558_1_);
		texture = "/mob/zombie.png";
		moveSpeed = 0.23F;
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIBreakDoor(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, moveSpeed, false));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, moveSpeed, true));
		tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, moveSpeed));
		tasks.addTask(5, new EntityAIMoveThroughVillage(this, moveSpeed, false));
		tasks.addTask(6, new EntityAIWander(this, moveSpeed));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false));
	}
	
	@Override protected void addRandomArmor()
	{
		super.addRandomArmor();
		if(rand.nextFloat() < (worldObj.difficultySetting == 3 ? 0.05F : 0.01F))
		{
			int var1 = rand.nextInt(3);
			if(var1 == 0)
			{
				setCurrentItemOrArmor(0, new ItemStack(Item.swordIron));
			} else
			{
				setCurrentItemOrArmor(0, new ItemStack(Item.shovelIron));
			}
		}
	}
	
	@Override public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		boolean var2 = super.attackEntityAsMob(p_70652_1_);
		if(var2 && getHeldItem() == null && isBurning() && rand.nextFloat() < worldObj.difficultySetting * 0.3F)
		{
			p_70652_1_.setFire(2 * worldObj.difficultySetting);
		}
		return var2;
	}
	
	protected void convertToVillager()
	{
		EntityVillager var1 = new EntityVillager(worldObj);
		var1.copyLocationAndAnglesFrom(this);
		var1.initCreature();
		var1.func_82187_q();
		if(isChild())
		{
			var1.setGrowingAge(-24000);
		}
		worldObj.removeEntity(this);
		worldObj.spawnEntityInWorld(var1);
		var1.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
		worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1017, (int) posX, (int) posY, (int) posZ, 0);
	}
	
	@Override protected void dropRareDrop(int p_70600_1_)
	{
		switch(rand.nextInt(3))
		{
			case 0:
				dropItem(Item.ingotIron.itemID, 1);
				break;
			case 1:
				dropItem(Item.carrot.itemID, 1);
				break;
			case 2:
				dropItem(Item.potato.itemID, 1);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte) 0));
		getDataWatcher().addObject(13, Byte.valueOf((byte) 0));
		getDataWatcher().addObject(14, Byte.valueOf((byte) 0));
	}
	
	@Override public int getAttackStrength(Entity p_82193_1_)
	{
		ItemStack var2 = getHeldItem();
		float var3 = (float) (getMaxHealth() - getHealth()) / (float) getMaxHealth();
		int var4 = 3 + MathHelper.floor_float(var3 * 4.0F);
		if(var2 != null)
		{
			var4 += var2.getDamageVsEntity(this);
		}
		return var4;
	}
	
	protected int getConversionTimeBoost()
	{
		int var1 = 1;
		if(rand.nextFloat() < 0.01F)
		{
			int var2 = 0;
			for(int var3 = (int) posX - 4; var3 < (int) posX + 4 && var2 < 14; ++var3)
			{
				for(int var4 = (int) posY - 4; var4 < (int) posY + 4 && var2 < 14; ++var4)
				{
					for(int var5 = (int) posZ - 4; var5 < (int) posZ + 4 && var2 < 14; ++var5)
					{
						int var6 = worldObj.getBlockId(var3, var4, var5);
						if(var6 == Block.fenceIron.blockID || var6 == Block.bed.blockID)
						{
							if(rand.nextFloat() < 0.3F)
							{
								++var1;
							}
							++var2;
						}
					}
				}
			}
		}
		return var1;
	}
	
	@Override public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.zombie.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.rottenFlesh.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.zombie.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 20;
	}
	
	@Override protected int getPathSearchRange()
	{
		return 40;
	}
	
	@Override public float getSpeedModifier()
	{
		return super.getSpeedModifier() * (isChild() ? 1.5F : 1.0F);
	}
	
	@Override public String getTexture()
	{
		return isVillager() ? "/mob/zombie_villager.png" : "/mob/zombie.png";
	}
	
	@Override public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;
		if(var1 > 20)
		{
			var1 = 20;
		}
		return var1;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 16)
		{
			worldObj.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "mob.zombie.remedy", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public void initCreature()
	{
		setCanPickUpLoot(rand.nextFloat() < pickUpLootProability[worldObj.difficultySetting]);
		if(worldObj.rand.nextFloat() < 0.05F)
		{
			setVillager(true);
		}
		addRandomArmor();
		enchantEquipment();
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
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.getCurrentEquippedItem();
		if(var2 != null && var2.getItem() == Item.appleGold && var2.getItemDamage() == 0 && isVillager() && this.isPotionActive(Potion.weakness))
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
				startConversion(rand.nextInt(2401) + 3600);
			}
			return true;
		} else return false;
	}
	
	@Override protected boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}
	
	public boolean isConverting()
	{
		return getDataWatcher().getWatchableObjectByte(14) == 1;
	}
	
	public boolean isVillager()
	{
		return getDataWatcher().getWatchableObjectByte(13) == 1;
	}
	
	@Override public void onKillEntity(EntityLiving p_70074_1_)
	{
		super.onKillEntity(p_70074_1_);
		if(worldObj.difficultySetting >= 2 && p_70074_1_ instanceof EntityVillager)
		{
			if(worldObj.difficultySetting == 2 && rand.nextBoolean()) return;
			EntityZombie var2 = new EntityZombie(worldObj);
			var2.copyLocationAndAnglesFrom(p_70074_1_);
			worldObj.removeEntity(p_70074_1_);
			var2.initCreature();
			var2.setVillager(true);
			if(p_70074_1_.isChild())
			{
				var2.setChild(true);
			}
			worldObj.spawnEntityInWorld(var2);
			worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1016, (int) posX, (int) posY, (int) posZ, 0);
		}
	}
	
	@Override public void onLivingUpdate()
	{
		if(worldObj.isDaytime() && !worldObj.isRemote && !isChild())
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
		super.onLivingUpdate();
	}
	
	@Override public void onUpdate()
	{
		if(!worldObj.isRemote && isConverting())
		{
			int var1 = getConversionTimeBoost();
			conversionTime -= var1;
			if(conversionTime <= 0)
			{
				convertToVillager();
			}
		}
		super.onUpdate();
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.getBoolean("IsBaby"))
		{
			setChild(true);
		}
		if(p_70037_1_.getBoolean("IsVillager"))
		{
			setVillager(true);
		}
		if(p_70037_1_.hasKey("ConversionTime") && p_70037_1_.getInteger("ConversionTime") > -1)
		{
			startConversion(p_70037_1_.getInteger("ConversionTime"));
		}
	}
	
	public void setChild(boolean p_82227_1_)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte) 1));
	}
	
	public void setVillager(boolean p_82229_1_)
	{
		getDataWatcher().updateObject(13, Byte.valueOf((byte) (p_82229_1_ ? 1 : 0)));
	}
	
	protected void startConversion(int p_82228_1_)
	{
		conversionTime = p_82228_1_;
		getDataWatcher().updateObject(14, Byte.valueOf((byte) 1));
		removePotionEffect(Potion.weakness.id);
		addPotionEffect(new PotionEffect(Potion.damageBoost.id, p_82228_1_, Math.min(worldObj.difficultySetting - 1, 0)));
		worldObj.setEntityState(this, (byte) 16);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		if(isChild())
		{
			p_70014_1_.setBoolean("IsBaby", true);
		}
		if(isVillager())
		{
			p_70014_1_.setBoolean("IsVillager", true);
		}
		p_70014_1_.setInteger("ConversionTime", isConverting() ? conversionTime : -1);
	}
}
