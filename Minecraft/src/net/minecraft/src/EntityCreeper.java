package net.minecraft.src;

public class EntityCreeper extends EntityMob
{
	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 30;
	private int explosionRadius = 3;
	
	public EntityCreeper(World par1World)
	{
		super(par1World);
		texture = "/mob/creeper.png";
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAICreeperSwell(this));
		tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25F, 0.3F));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, 0.25F, false));
		tasks.addTask(5, new EntityAIWander(this, 0.2F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		return true;
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) -1));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}
	
	@Override protected void fall(float par1)
	{
		super.fall(par1);
		timeSinceIgnited = (int) (timeSinceIgnited + par1 * 1.5F);
		if(timeSinceIgnited > fuseTime - 5)
		{
			timeSinceIgnited = fuseTime - 5;
		}
	}
	
	@Override public int func_82143_as()
	{
		return getAttackTarget() == null ? 3 : 3 + health - 1;
	}
	
	public float getCreeperFlashIntensity(float par1)
	{
		return (lastActiveTime + (timeSinceIgnited - lastActiveTime) * par1) / (fuseTime - 2);
	}
	
	public int getCreeperState()
	{
		return dataWatcher.getWatchableObjectByte(16);
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.creeper.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.gunpowder.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.creeper.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 20;
	}
	
	public boolean getPowered()
	{
		return dataWatcher.getWatchableObjectByte(17) == 1;
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		if(par1DamageSource.getEntity() instanceof EntitySkeleton)
		{
			int var2 = Item.record13.itemID + rand.nextInt(Item.recordWait.itemID - Item.record13.itemID + 1);
			dropItem(var2, 1);
		}
	}
	
	@Override public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
	{
		super.onStruckByLightning(par1EntityLightningBolt);
		dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
	}
	
	@Override public void onUpdate()
	{
		if(isEntityAlive())
		{
			lastActiveTime = timeSinceIgnited;
			int var1 = getCreeperState();
			if(var1 > 0 && timeSinceIgnited == 0)
			{
				playSound("random.fuse", 1.0F, 0.5F);
			}
			timeSinceIgnited += var1;
			if(timeSinceIgnited < 0)
			{
				timeSinceIgnited = 0;
			}
			if(timeSinceIgnited >= fuseTime)
			{
				timeSinceIgnited = fuseTime;
				if(!worldObj.isRemote)
				{
					boolean var2 = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
					if(getPowered())
					{
						worldObj.createExplosion(this, posX, posY, posZ, explosionRadius * 2, var2);
					} else
					{
						worldObj.createExplosion(this, posX, posY, posZ, explosionRadius, var2);
					}
					setDead();
				}
			}
		}
		super.onUpdate();
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		dataWatcher.updateObject(17, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));
		if(par1NBTTagCompound.hasKey("Fuse"))
		{
			fuseTime = par1NBTTagCompound.getShort("Fuse");
		}
		if(par1NBTTagCompound.hasKey("ExplosionRadius"))
		{
			explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
		}
	}
	
	public void setCreeperState(int par1)
	{
		dataWatcher.updateObject(16, Byte.valueOf((byte) par1));
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		if(dataWatcher.getWatchableObjectByte(17) == 1)
		{
			par1NBTTagCompound.setBoolean("powered", true);
		}
		par1NBTTagCompound.setShort("Fuse", (short) fuseTime);
		par1NBTTagCompound.setByte("ExplosionRadius", (byte) explosionRadius);
	}
}
