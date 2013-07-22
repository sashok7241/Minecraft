package net.minecraft.src;

public class EntityIronGolem extends EntityGolem
{
	private int homeCheckTimer = 0;
	Village villageObj = null;
	private int attackTimer;
	private int holdRoseTick;
	
	public EntityIronGolem(World p_i3524_1_)
	{
		super(p_i3524_1_);
		texture = "/mob/villager_golem.png";
		setSize(1.4F, 2.9F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAIAttackOnCollide(this, 0.25F, true));
		tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.22F, 32.0F));
		tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.16F, true));
		tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, 0.16F));
		tasks.addTask(5, new EntityAILookAtVillager(this));
		tasks.addTask(6, new EntityAIWander(this, 0.16F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIDefendVillage(this));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 16.0F, 0, false, true, IMob.mobSelector));
	}
	
	@Override public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		attackTimer = 10;
		worldObj.setEntityState(this, (byte) 4);
		boolean var2 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + rand.nextInt(15));
		if(var2)
		{
			p_70652_1_.motionY += 0.4000000059604645D;
		}
		playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return var2;
	}
	
	@Override public boolean canAttackClass(Class p_70686_1_)
	{
		return isPlayerCreated() && EntityPlayer.class.isAssignableFrom(p_70686_1_) ? false : super.canAttackClass(p_70686_1_);
	}
	
	@Override protected void collideWithEntity(Entity p_82167_1_)
	{
		if(p_82167_1_ instanceof IMob && getRNG().nextInt(20) == 0)
		{
			setAttackTarget((EntityLiving) p_82167_1_);
		}
		super.collideWithEntity(p_82167_1_);
	}
	
	@Override protected int decreaseAirSupply(int p_70682_1_)
	{
		return p_70682_1_;
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = rand.nextInt(3);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Block.plantRed.blockID, 1);
		}
		var4 = 3 + rand.nextInt(3);
		for(int var5 = 0; var5 < var4; ++var5)
		{
			dropItem(Item.ingotIron.itemID, 1);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	public int getAttackTimer()
	{
		return attackTimer;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.irongolem.death";
	}
	
	public int getHoldRoseTick()
	{
		return holdRoseTick;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.irongolem.hit";
	}
	
	@Override protected String getLivingSound()
	{
		return "none";
	}
	
	@Override public int getMaxHealth()
	{
		return 100;
	}
	
	public Village getVillage()
	{
		return villageObj;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 4)
		{
			attackTimer = 10;
			playSound("mob.irongolem.throw", 1.0F, 1.0F);
		} else if(par1 == 11)
		{
			holdRoseTick = 400;
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	public boolean isPlayerCreated()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override public void onDeath(DamageSource p_70645_1_)
	{
		if(!isPlayerCreated() && attackingPlayer != null && villageObj != null)
		{
			villageObj.setReputationForPlayer(attackingPlayer.getCommandSenderName(), -5);
		}
		super.onDeath(p_70645_1_);
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(attackTimer > 0)
		{
			--attackTimer;
		}
		if(holdRoseTick > 0)
		{
			--holdRoseTick;
		}
		if(motionX * motionX + motionZ * motionZ > 2.500000277905201E-7D && rand.nextInt(5) == 0)
		{
			int var1 = MathHelper.floor_double(posX);
			int var2 = MathHelper.floor_double(posY - 0.20000000298023224D - yOffset);
			int var3 = MathHelper.floor_double(posZ);
			int var4 = worldObj.getBlockId(var1, var2, var3);
			if(var4 > 0)
			{
				worldObj.spawnParticle("tilecrack_" + var4 + "_" + worldObj.getBlockMetadata(var1, var2, var3), posX + (rand.nextFloat() - 0.5D) * width, boundingBox.minY + 0.1D, posZ + (rand.nextFloat() - 0.5D) * width, 4.0D * (rand.nextFloat() - 0.5D), 0.5D, (rand.nextFloat() - 0.5D) * 4.0D);
			}
		}
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		setPlayerCreated(p_70037_1_.getBoolean("PlayerCreated"));
	}
	
	public void setHoldingRose(boolean p_70851_1_)
	{
		holdRoseTick = p_70851_1_ ? 400 : 0;
		worldObj.setEntityState(this, (byte) 11);
	}
	
	public void setPlayerCreated(boolean p_70849_1_)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(p_70849_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}
	
	@Override protected void updateAITick()
	{
		if(--homeCheckTimer <= 0)
		{
			homeCheckTimer = 70 + rand.nextInt(50);
			villageObj = worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 32);
			if(villageObj == null)
			{
				detachHome();
			} else
			{
				ChunkCoordinates var1 = villageObj.getCenter();
				setHomeArea(var1.posX, var1.posY, var1.posZ, (int) (villageObj.getVillageRadius() * 0.6F));
			}
		}
		super.updateAITick();
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setBoolean("PlayerCreated", isPlayerCreated());
	}
}
