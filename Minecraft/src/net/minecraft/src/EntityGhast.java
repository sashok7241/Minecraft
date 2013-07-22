package net.minecraft.src;

public class EntityGhast extends EntityFlying implements IMob
{
	public int courseChangeCooldown;
	public double waypointX;
	public double waypointY;
	public double waypointZ;
	private Entity targetedEntity;
	private int aggroCooldown;
	public int prevAttackCounter;
	public int attackCounter;
	private int explosionStrength = 1;
	
	public EntityGhast(World par1World)
	{
		super(par1World);
		setSize(4.0F, 4.0F);
		isImmuneToFire = true;
		experienceValue = 5;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else if("fireball".equals(par1DamageSource.getDamageType()) && par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			super.attackEntityFrom(par1DamageSource, 1000.0F);
			((EntityPlayer) par1DamageSource.getEntity()).triggerAchievement(AchievementList.ghast);
			return true;
		} else return super.attackEntityFrom(par1DamageSource, par2);
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(2) + rand.nextInt(1 + par2);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.ghastTear.itemID, 1);
		}
		var3 = rand.nextInt(3) + rand.nextInt(1 + par2);
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.gunpowder.itemID, 1);
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
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
	}
	
	public boolean func_110182_bF()
	{
		return dataWatcher.getWatchableObjectByte(16) != 0;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return rand.nextInt(20) == 0 && super.getCanSpawnHere() && worldObj.difficultySetting > 0;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.ghast.death";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.gunpowder.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.ghast.moan";
	}
	
	@Override public int getMaxSpawnedInChunk()
	{
		return 1;
	}
	
	@Override protected float getSoundVolume()
	{
		return 10.0F;
	}
	
	private boolean isCourseTraversable(double par1, double par3, double par5, double par7)
	{
		double var9 = (waypointX - posX) / par7;
		double var11 = (waypointY - posY) / par7;
		double var13 = (waypointZ - posZ) / par7;
		AxisAlignedBB var15 = boundingBox.copy();
		for(int var16 = 1; var16 < par7; ++var16)
		{
			var15.offset(var9, var11, var13);
			if(!worldObj.getCollidingBoundingBoxes(this, var15).isEmpty()) return false;
		}
		return true;
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("ExplosionPower"))
		{
			explosionStrength = par1NBTTagCompound.getInteger("ExplosionPower");
		}
	}
	
	@Override protected void updateEntityActionState()
	{
		if(!worldObj.isRemote && worldObj.difficultySetting == 0)
		{
			setDead();
		}
		despawnEntity();
		prevAttackCounter = attackCounter;
		double var1 = waypointX - posX;
		double var3 = waypointY - posY;
		double var5 = waypointZ - posZ;
		double var7 = var1 * var1 + var3 * var3 + var5 * var5;
		if(var7 < 1.0D || var7 > 3600.0D)
		{
			waypointX = posX + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			waypointY = posY + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
			waypointZ = posZ + (rand.nextFloat() * 2.0F - 1.0F) * 16.0F;
		}
		if(courseChangeCooldown-- <= 0)
		{
			courseChangeCooldown += rand.nextInt(5) + 2;
			var7 = MathHelper.sqrt_double(var7);
			if(isCourseTraversable(waypointX, waypointY, waypointZ, var7))
			{
				motionX += var1 / var7 * 0.1D;
				motionY += var3 / var7 * 0.1D;
				motionZ += var5 / var7 * 0.1D;
			} else
			{
				waypointX = posX;
				waypointY = posY;
				waypointZ = posZ;
			}
		}
		if(targetedEntity != null && targetedEntity.isDead)
		{
			targetedEntity = null;
		}
		if(targetedEntity == null || aggroCooldown-- <= 0)
		{
			targetedEntity = worldObj.getClosestVulnerablePlayerToEntity(this, 100.0D);
			if(targetedEntity != null)
			{
				aggroCooldown = 20;
			}
		}
		double var9 = 64.0D;
		if(targetedEntity != null && targetedEntity.getDistanceSqToEntity(this) < var9 * var9)
		{
			double var11 = targetedEntity.posX - posX;
			double var13 = targetedEntity.boundingBox.minY + targetedEntity.height / 2.0F - (posY + height / 2.0F);
			double var15 = targetedEntity.posZ - posZ;
			renderYawOffset = rotationYaw = -((float) Math.atan2(var11, var15)) * 180.0F / (float) Math.PI;
			if(canEntityBeSeen(targetedEntity))
			{
				if(attackCounter == 10)
				{
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1007, (int) posX, (int) posY, (int) posZ, 0);
				}
				++attackCounter;
				if(attackCounter == 20)
				{
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1008, (int) posX, (int) posY, (int) posZ, 0);
					EntityLargeFireball var17 = new EntityLargeFireball(worldObj, this, var11, var13, var15);
					var17.field_92057_e = explosionStrength;
					double var18 = 4.0D;
					Vec3 var20 = getLook(1.0F);
					var17.posX = posX + var20.xCoord * var18;
					var17.posY = posY + height / 2.0F + 0.5D;
					var17.posZ = posZ + var20.zCoord * var18;
					worldObj.spawnEntityInWorld(var17);
					attackCounter = -40;
				}
			} else if(attackCounter > 0)
			{
				--attackCounter;
			}
		} else
		{
			renderYawOffset = rotationYaw = -((float) Math.atan2(motionX, motionZ)) * 180.0F / (float) Math.PI;
			if(attackCounter > 0)
			{
				--attackCounter;
			}
		}
		if(!worldObj.isRemote)
		{
			byte var21 = dataWatcher.getWatchableObjectByte(16);
			byte var12 = (byte) (attackCounter > 10 ? 1 : 0);
			if(var21 != var12)
			{
				dataWatcher.updateObject(16, Byte.valueOf(var12));
			}
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("ExplosionPower", explosionStrength);
	}
}
