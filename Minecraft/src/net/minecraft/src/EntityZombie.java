package net.minecraft.src;

import java.util.Calendar;
import java.util.UUID;

public class EntityZombie extends EntityMob
{
	protected static final Attribute field_110186_bp = new RangedAttribute("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).func_111117_a("Spawn Reinforcements Chance");
	private static final UUID field_110187_bq = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier field_110188_br = new AttributeModifier(field_110187_bq, "Baby speed boost", 0.5D, 1);
	private int conversionTime;
	
	public EntityZombie(World par1World)
	{
		super(par1World);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIBreakDoor(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0D, true));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
		tasks.addTask(6, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
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
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		boolean var2 = super.attackEntityAsMob(par1Entity);
		if(var2 && getHeldItem() == null && isBurning() && rand.nextFloat() < worldObj.difficultySetting * 0.3F)
		{
			par1Entity.setFire(2 * worldObj.difficultySetting);
		}
		return var2;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(!super.attackEntityFrom(par1DamageSource, par2)) return false;
		else
		{
			EntityLivingBase var3 = getAttackTarget();
			if(var3 == null && getEntityToAttack() instanceof EntityLivingBase)
			{
				var3 = (EntityLivingBase) getEntityToAttack();
			}
			if(var3 == null && par1DamageSource.getEntity() instanceof EntityLivingBase)
			{
				var3 = (EntityLivingBase) par1DamageSource.getEntity();
			}
			if(var3 != null && worldObj.difficultySetting >= 3 && rand.nextFloat() < func_110148_a(field_110186_bp).func_111126_e())
			{
				int var4 = MathHelper.floor_double(posX);
				int var5 = MathHelper.floor_double(posY);
				int var6 = MathHelper.floor_double(posZ);
				EntityZombie var7 = new EntityZombie(worldObj);
				for(int var8 = 0; var8 < 50; ++var8)
				{
					int var9 = var4 + MathHelper.getRandomIntegerInRange(rand, 7, 40) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
					int var10 = var5 + MathHelper.getRandomIntegerInRange(rand, 7, 40) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
					int var11 = var6 + MathHelper.getRandomIntegerInRange(rand, 7, 40) * MathHelper.getRandomIntegerInRange(rand, -1, 1);
					if(worldObj.doesBlockHaveSolidTopSurface(var9, var10 - 1, var11) && worldObj.getBlockLightValue(var9, var10, var11) < 10)
					{
						var7.setPosition(var9, var10, var11);
						if(worldObj.checkNoEntityCollision(var7.boundingBox) && worldObj.getCollidingBoundingBoxes(var7, var7.boundingBox).isEmpty() && !worldObj.isAnyLiquid(var7.boundingBox))
						{
							worldObj.spawnEntityInWorld(var7);
							var7.setAttackTarget(var3);
							var7.func_110161_a((EntityLivingData) null);
							func_110148_a(field_110186_bp).func_111121_a(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
							var7.func_110148_a(field_110186_bp).func_111121_a(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
							break;
						}
					}
				}
			}
			return true;
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return !isConverting();
	}
	
	protected void convertToVillager()
	{
		EntityVillager var1 = new EntityVillager(worldObj);
		var1.copyLocationAndAnglesFrom(this);
		var1.func_110161_a((EntityLivingData) null);
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
	
	@Override protected void dropRareDrop(int par1)
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
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
		func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
		func_110140_aT().func_111150_b(field_110186_bp).func_111128_a(rand.nextDouble() * 0.10000000149011612D);
	}
	
	@Override public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		Object par1EntityLivingData1 = super.func_110161_a(par1EntityLivingData);
		float var2 = worldObj.func_110746_b(posX, posY, posZ);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * var2);
		if(par1EntityLivingData1 == null)
		{
			par1EntityLivingData1 = new EntityZombieGroupData(this, worldObj.rand.nextFloat() < 0.05F, worldObj.rand.nextFloat() < 0.05F, (EntityZombieINNER1) null);
		}
		if(par1EntityLivingData1 instanceof EntityZombieGroupData)
		{
			EntityZombieGroupData var3 = (EntityZombieGroupData) par1EntityLivingData1;
			if(var3.field_142046_b)
			{
				setVillager(true);
			}
			if(var3.field_142048_a)
			{
				setChild(true);
			}
		}
		addRandomArmor();
		enchantEquipment();
		if(getCurrentItemOrArmor(4) == null)
		{
			Calendar var5 = worldObj.getCurrentDate();
			if(var5.get(2) + 1 == 10 && var5.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Block.pumpkinLantern : Block.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}
		func_110148_a(SharedMonsterAttributes.field_111266_c).func_111121_a(new AttributeModifier("Random spawn bonus", rand.nextDouble() * 0.05000000074505806D, 0));
		func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random zombie-spawn bonus", rand.nextDouble() * 1.5D, 2));
		if(rand.nextFloat() < var2 * 0.05F)
		{
			func_110148_a(field_110186_bp).func_111121_a(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 0.25D + 0.5D, 0));
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111121_a(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 3.0D + 1.0D, 2));
		}
		return (EntityLivingData) par1EntityLivingData1;
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
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.getCurrentEquippedItem();
		if(var2 != null && var2.getItem() == Item.appleGold && var2.getItemDamage() == 0 && isVillager() && this.isPotionActive(Potion.weakness))
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
	
	@Override public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);
		if(worldObj.difficultySetting >= 2 && par1EntityLivingBase instanceof EntityVillager)
		{
			if(worldObj.difficultySetting == 2 && rand.nextBoolean()) return;
			EntityZombie var2 = new EntityZombie(worldObj);
			var2.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			var2.func_110161_a((EntityLivingData) null);
			var2.setVillager(true);
			if(par1EntityLivingBase.isChild())
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
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.getBoolean("IsBaby"))
		{
			setChild(true);
		}
		if(par1NBTTagCompound.getBoolean("IsVillager"))
		{
			setVillager(true);
		}
		if(par1NBTTagCompound.hasKey("ConversionTime") && par1NBTTagCompound.getInteger("ConversionTime") > -1)
		{
			startConversion(par1NBTTagCompound.getInteger("ConversionTime"));
		}
	}
	
	public void setChild(boolean par1)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte) (par1 ? 1 : 0)));
		if(worldObj != null && !worldObj.isRemote)
		{
			AttributeInstance var2 = func_110148_a(SharedMonsterAttributes.field_111263_d);
			var2.func_111124_b(field_110188_br);
			if(par1)
			{
				var2.func_111121_a(field_110188_br);
			}
		}
	}
	
	public void setVillager(boolean par1)
	{
		getDataWatcher().updateObject(13, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
	
	protected void startConversion(int par1)
	{
		conversionTime = par1;
		getDataWatcher().updateObject(14, Byte.valueOf((byte) 1));
		removePotionEffect(Potion.weakness.id);
		addPotionEffect(new PotionEffect(Potion.damageBoost.id, par1, Math.min(worldObj.difficultySetting - 1, 0)));
		worldObj.setEntityState(this, (byte) 16);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		if(isChild())
		{
			par1NBTTagCompound.setBoolean("IsBaby", true);
		}
		if(isVillager())
		{
			par1NBTTagCompound.setBoolean("IsVillager", true);
		}
		par1NBTTagCompound.setInteger("ConversionTime", isConverting() ? conversionTime : -1);
	}
}
