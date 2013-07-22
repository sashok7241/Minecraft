package net.minecraft.src;

import java.util.Iterator;

public class EntityItem extends Entity
{
	public int age;
	public int delayBeforeCanPickup;
	private int health;
	public float hoverStart;
	
	public EntityItem(World p_i3540_1_)
	{
		super(p_i3540_1_);
		age = 0;
		health = 5;
		hoverStart = (float) (Math.random() * Math.PI * 2.0D);
		setSize(0.25F, 0.25F);
		yOffset = height / 2.0F;
	}
	
	public EntityItem(World p_i8007_1_, double p_i8007_2_, double p_i8007_4_, double p_i8007_6_)
	{
		super(p_i8007_1_);
		age = 0;
		health = 5;
		hoverStart = (float) (Math.random() * Math.PI * 2.0D);
		setSize(0.25F, 0.25F);
		yOffset = height / 2.0F;
		setPosition(p_i8007_2_, p_i8007_4_, p_i8007_6_);
		rotationYaw = (float) (Math.random() * 360.0D);
		motionX = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
		motionY = 0.20000000298023224D;
		motionZ = (float) (Math.random() * 0.20000000298023224D - 0.10000000149011612D);
	}
	
	public EntityItem(World p_i3539_1_, double p_i3539_2_, double p_i3539_4_, double p_i3539_6_, ItemStack p_i3539_8_)
	{
		this(p_i3539_1_, p_i3539_2_, p_i3539_4_, p_i3539_6_);
		setEntityItemStack(p_i3539_8_);
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else if(getEntityItem() != null && getEntityItem().itemID == Item.netherStar.itemID && p_70097_1_.isExplosion()) return false;
		else
		{
			setBeenAttacked();
			health -= p_70097_2_;
			if(health <= 0)
			{
				setDead();
			}
			return false;
		}
	}
	
	@Override public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	public boolean combineItems(EntityItem p_70289_1_)
	{
		if(p_70289_1_ == this) return false;
		else if(p_70289_1_.isEntityAlive() && isEntityAlive())
		{
			ItemStack var2 = getEntityItem();
			ItemStack var3 = p_70289_1_.getEntityItem();
			if(var3.getItem() != var2.getItem()) return false;
			else if(var3.hasTagCompound() ^ var2.hasTagCompound()) return false;
			else if(var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) return false;
			else if(var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage()) return false;
			else if(var3.stackSize < var2.stackSize) return p_70289_1_.combineItems(this);
			else if(var3.stackSize + var2.stackSize > var3.getMaxStackSize()) return false;
			else
			{
				var3.stackSize += var2.stackSize;
				p_70289_1_.delayBeforeCanPickup = Math.max(p_70289_1_.delayBeforeCanPickup, delayBeforeCanPickup);
				p_70289_1_.age = Math.min(p_70289_1_.age, age);
				p_70289_1_.setEntityItemStack(var3);
				setDead();
				return true;
			}
		} else return false;
	}
	
	@Override protected void dealFireDamage(int p_70081_1_)
	{
		attackEntityFrom(DamageSource.inFire, p_70081_1_);
	}
	
	@Override protected void entityInit()
	{
		getDataWatcher().addObjectByDataType(10, 5);
	}
	
	public ItemStack getEntityItem()
	{
		ItemStack var1 = getDataWatcher().getWatchableObjectItemStack(10);
		if(var1 == null)
		{
			if(worldObj != null)
			{
				worldObj.getWorldLogAgent().logSevere("Item entity " + entityId + " has no item?!");
			}
			return new ItemStack(Block.stone);
		} else return var1;
	}
	
	@Override public String getEntityName()
	{
		return StatCollector.translateToLocal("item." + getEntityItem().getItemName());
	}
	
	@Override public boolean handleWaterMovement()
	{
		return worldObj.handleMaterialAcceleration(boundingBox, Material.water, this);
	}
	
	@Override public void onCollideWithPlayer(EntityPlayer p_70100_1_)
	{
		if(!worldObj.isRemote)
		{
			ItemStack var2 = getEntityItem();
			int var3 = var2.stackSize;
			if(delayBeforeCanPickup == 0 && p_70100_1_.inventory.addItemStackToInventory(var2))
			{
				if(var2.itemID == Block.wood.blockID)
				{
					p_70100_1_.triggerAchievement(AchievementList.mineWood);
				}
				if(var2.itemID == Item.leather.itemID)
				{
					p_70100_1_.triggerAchievement(AchievementList.killCow);
				}
				if(var2.itemID == Item.diamond.itemID)
				{
					p_70100_1_.triggerAchievement(AchievementList.diamonds);
				}
				if(var2.itemID == Item.blazeRod.itemID)
				{
					p_70100_1_.triggerAchievement(AchievementList.blazeRod);
				}
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				p_70100_1_.onItemPickup(this, var3);
				if(var2.stackSize <= 0)
				{
					setDead();
				}
			}
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(delayBeforeCanPickup > 0)
		{
			--delayBeforeCanPickup;
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		noClip = pushOutOfBlocks(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);
		moveEntity(motionX, motionY, motionZ);
		boolean var1 = (int) prevPosX != (int) posX || (int) prevPosY != (int) posY || (int) prevPosZ != (int) posZ;
		if(var1 || ticksExisted % 25 == 0)
		{
			if(worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) == Material.lava)
			{
				motionY = 0.20000000298023224D;
				motionX = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
				motionZ = (rand.nextFloat() - rand.nextFloat()) * 0.2F;
				playSound("random.fizz", 0.4F, 2.0F + rand.nextFloat() * 0.4F);
			}
			if(!worldObj.isRemote)
			{
				searchForOtherItemsNearby();
			}
		}
		float var2 = 0.98F;
		if(onGround)
		{
			var2 = 0.58800006F;
			int var3 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
			if(var3 > 0)
			{
				var2 = Block.blocksList[var3].slipperiness * 0.98F;
			}
		}
		motionX *= var2;
		motionY *= 0.9800000190734863D;
		motionZ *= var2;
		if(onGround)
		{
			motionY *= -0.5D;
		}
		++age;
		if(!worldObj.isRemote && age >= 6000)
		{
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		health = p_70037_1_.getShort("Health") & 255;
		age = p_70037_1_.getShort("Age");
		NBTTagCompound var2 = p_70037_1_.getCompoundTag("Item");
		setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
		if(getEntityItem() == null)
		{
			setDead();
		}
	}
	
	private void searchForOtherItemsNearby()
	{
		Iterator var1 = worldObj.getEntitiesWithinAABB(EntityItem.class, boundingBox.expand(0.5D, 0.0D, 0.5D)).iterator();
		while(var1.hasNext())
		{
			EntityItem var2 = (EntityItem) var1.next();
			combineItems(var2);
		}
	}
	
	public void setAgeToCreativeDespawnTime()
	{
		age = 4800;
	}
	
	public void setEntityItemStack(ItemStack p_92058_1_)
	{
		getDataWatcher().updateObject(10, p_92058_1_);
		getDataWatcher().setObjectWatched(10);
	}
	
	@Override public void travelToDimension(int p_71027_1_)
	{
		super.travelToDimension(p_71027_1_);
		if(!worldObj.isRemote)
		{
			searchForOtherItemsNearby();
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setShort("Health", (byte) health);
		p_70014_1_.setShort("Age", (short) age);
		if(getEntityItem() != null)
		{
			p_70014_1_.setCompoundTag("Item", getEntityItem().writeToNBT(new NBTTagCompound()));
		}
	}
}
