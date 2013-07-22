package net.minecraft.src;

public class EntityIronGolem extends EntityGolem
{
	private int homeCheckTimer;
	Village villageObj;
	private int attackTimer;
	private int holdRoseTick;
	
	public EntityIronGolem(World par1World)
	{
		super(par1World);
		setSize(1.4F, 2.9F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
		tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
		tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAILookAtVillager(this));
		tasks.addTask(6, new EntityAIWander(this, 0.6D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIDefendVillage(this));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, true, IMob.mobSelector));
	}
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		attackTimer = 10;
		worldObj.setEntityState(this, (byte) 4);
		boolean var2 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + rand.nextInt(15));
		if(var2)
		{
			par1Entity.motionY += 0.4000000059604645D;
		}
		playSound("mob.irongolem.throw", 1.0F, 1.0F);
		return var2;
	}
	
	@Override public boolean canAttackClass(Class par1Class)
	{
		return isPlayerCreated() && EntityPlayer.class.isAssignableFrom(par1Class) ? false : super.canAttackClass(par1Class);
	}
	
	@Override protected void collideWithEntity(Entity par1Entity)
	{
		if(par1Entity instanceof IMob && getRNG().nextInt(20) == 0)
		{
			setAttackTarget((EntityLivingBase) par1Entity);
		}
		super.collideWithEntity(par1Entity);
	}
	
	@Override protected int decreaseAirSupply(int par1)
	{
		return par1;
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
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
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
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
	
	@Override public void onDeath(DamageSource par1DamageSource)
	{
		if(!isPlayerCreated() && attackingPlayer != null && villageObj != null)
		{
			villageObj.setReputationForPlayer(attackingPlayer.getCommandSenderName(), -5);
		}
		super.onDeath(par1DamageSource);
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
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.irongolem.walk", 1.0F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setPlayerCreated(par1NBTTagCompound.getBoolean("PlayerCreated"));
	}
	
	public void setHoldingRose(boolean par1)
	{
		holdRoseTick = par1 ? 400 : 0;
		worldObj.setEntityState(this, (byte) 11);
	}
	
	public void setPlayerCreated(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(par1)
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
				func_110177_bN();
			} else
			{
				ChunkCoordinates var1 = villageObj.getCenter();
				func_110171_b(var1.posX, var1.posY, var1.posZ, (int) (villageObj.getVillageRadius() * 0.6F));
			}
		}
		super.updateAITick();
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("PlayerCreated", isPlayerCreated());
	}
}
