package net.minecraft.src;

public class EntityPig extends EntityAnimal
{
	private final EntityAIControlledByPlayer aiControlledByPlayer;
	
	public EntityPig(World p_i3520_1_)
	{
		super(p_i3520_1_);
		texture = "/mob/pig.png";
		setSize(0.9F, 0.9F);
		getNavigator().setAvoidsWater(true);
		float var2 = 0.25F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		tasks.addTask(2, aiControlledByPlayer = new EntityAIControlledByPlayer(this, 0.34F));
		tasks.addTask(3, new EntityAIMate(this, var2));
		tasks.addTask(4, new EntityAITempt(this, 0.3F, Item.carrotOnAStick.itemID, false));
		tasks.addTask(4, new EntityAITempt(this, 0.3F, Item.carrot.itemID, false));
		tasks.addTask(5, new EntityAIFollowParent(this, 0.28F));
		tasks.addTask(6, new EntityAIWander(this, var2));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
	}
	
	@Override public boolean canBeSteered()
	{
		ItemStack var1 = ((EntityPlayer) riddenByEntity).getHeldItem();
		return var1 != null && var1.itemID == Item.carrotOnAStick.itemID;
	}
	
	@Override public EntityAgeable createChild(EntityAgeable p_90011_1_)
	{
		return spawnBabyAnimal(p_90011_1_);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + p_70628_2_);
		for(int var4 = 0; var4 < var3; ++var4)
		{
			if(isBurning())
			{
				dropItem(Item.porkCooked.itemID, 1);
			} else
			{
				dropItem(Item.porkRaw.itemID, 1);
			}
		}
		if(getSaddled())
		{
			dropItem(Item.saddle.itemID, 1);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	@Override protected void fall(float p_70069_1_)
	{
		super.fall(p_70069_1_);
		if(p_70069_1_ > 5.0F && riddenByEntity instanceof EntityPlayer)
		{
			((EntityPlayer) riddenByEntity).triggerAchievement(AchievementList.flyPig);
		}
	}
	
	public EntityAIControlledByPlayer getAIControlledByPlayer()
	{
		return aiControlledByPlayer;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.pig.death";
	}
	
	@Override protected int getDropItemId()
	{
		return isBurning() ? Item.porkCooked.itemID : Item.porkRaw.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.pig.say";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.pig.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 10;
	}
	
	public boolean getSaddled()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		if(super.interact(p_70085_1_)) return true;
		else if(getSaddled() && !worldObj.isRemote && (riddenByEntity == null || riddenByEntity == p_70085_1_))
		{
			p_70085_1_.mountEntity(this);
			return true;
		} else return false;
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public boolean isBreedingItem(ItemStack p_70877_1_)
	{
		return p_70877_1_ != null && p_70877_1_.itemID == Item.carrot.itemID;
	}
	
	@Override public void onStruckByLightning(EntityLightningBolt p_70077_1_)
	{
		if(!worldObj.isRemote)
		{
			EntityPigZombie var2 = new EntityPigZombie(worldObj);
			var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			worldObj.spawnEntityInWorld(var2);
			setDead();
		}
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.pig.step", 0.15F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		setSaddled(p_70037_1_.getBoolean("Saddle"));
	}
	
	public void setSaddled(boolean p_70900_1_)
	{
		if(p_70900_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) 1));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) 0));
		}
	}
	
	public EntityPig spawnBabyAnimal(EntityAgeable p_70879_1_)
	{
		return new EntityPig(worldObj);
	}
	
	@Override protected void updateAITasks()
	{
		super.updateAITasks();
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setBoolean("Saddle", getSaddled());
	}
}
