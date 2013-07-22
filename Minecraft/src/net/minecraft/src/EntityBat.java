package net.minecraft.src;

import java.util.Calendar;

public class EntityBat extends EntityAmbientCreature
{
	private ChunkCoordinates currentFlightTarget;
	
	public EntityBat(World p_i5063_1_)
	{
		super(p_i5063_1_);
		texture = "/mob/bat.png";
		setSize(0.5F, 0.9F);
		setIsBatHanging(true);
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			if(!worldObj.isRemote && getIsBatHanging())
			{
				setIsBatHanging(false);
			}
			return super.attackEntityFrom(p_70097_1_, p_70097_2_);
		}
	}
	
	@Override public boolean canBePushed()
	{
		return false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void collideWithEntity(Entity p_82167_1_)
	{
	}
	
	@Override protected void collideWithNearbyEntities()
	{
	}
	
	@Override public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}
	
	@Override protected void fall(float p_70069_1_)
	{
	}
	
	@Override public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(boundingBox.minY);
		if(var1 >= 63) return false;
		else
		{
			int var2 = MathHelper.floor_double(posX);
			int var3 = MathHelper.floor_double(posZ);
			int var4 = worldObj.getBlockLightValue(var2, var1, var3);
			byte var5 = 4;
			Calendar var6 = worldObj.getCurrentDate();
			if((var6.get(2) + 1 != 10 || var6.get(5) < 20) && (var6.get(2) + 1 != 11 || var6.get(5) > 3))
			{
				if(rand.nextBoolean()) return false;
			} else
			{
				var5 = 7;
			}
			return var4 > rand.nextInt(var5) ? false : super.getCanSpawnHere();
		}
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.bat.death";
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.bat.hurt";
	}
	
	public boolean getIsBatHanging()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	@Override protected String getLivingSound()
	{
		return getIsBatHanging() && rand.nextInt(4) != 0 ? null : "mob.bat.idle";
	}
	
	@Override public int getMaxHealth()
	{
		return 6;
	}
	
	@Override protected float getSoundPitch()
	{
		return super.getSoundPitch() * 0.95F;
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.1F;
	}
	
	@Override public void initCreature()
	{
	}
	
	@Override protected boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(getIsBatHanging())
		{
			motionX = motionY = motionZ = 0.0D;
			posY = MathHelper.floor_double(posY) + 1.0D - height;
		} else
		{
			motionY *= 0.6000000238418579D;
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		dataWatcher.updateObject(16, Byte.valueOf(p_70037_1_.getByte("BatFlags")));
	}
	
	public void setIsBatHanging(boolean p_82236_1_)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(p_82236_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}
	
	@Override protected void updateAITasks()
	{
		super.updateAITasks();
		if(getIsBatHanging())
		{
			if(!worldObj.isBlockNormalCube(MathHelper.floor_double(posX), (int) posY + 1, MathHelper.floor_double(posZ)))
			{
				setIsBatHanging(false);
				worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, (int) posX, (int) posY, (int) posZ, 0);
			} else
			{
				if(rand.nextInt(200) == 0)
				{
					rotationYawHead = rand.nextInt(360);
				}
				if(worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
				{
					setIsBatHanging(false);
					worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, (int) posX, (int) posY, (int) posZ, 0);
				}
			}
		} else
		{
			if(currentFlightTarget != null && (!worldObj.isAirBlock(currentFlightTarget.posX, currentFlightTarget.posY, currentFlightTarget.posZ) || currentFlightTarget.posY < 1))
			{
				currentFlightTarget = null;
			}
			if(currentFlightTarget == null || rand.nextInt(30) == 0 || currentFlightTarget.getDistanceSquared((int) posX, (int) posY, (int) posZ) < 4.0F)
			{
				currentFlightTarget = new ChunkCoordinates((int) posX + rand.nextInt(7) - rand.nextInt(7), (int) posY + rand.nextInt(6) - 2, (int) posZ + rand.nextInt(7) - rand.nextInt(7));
			}
			double var1 = currentFlightTarget.posX + 0.5D - posX;
			double var3 = currentFlightTarget.posY + 0.1D - posY;
			double var5 = currentFlightTarget.posZ + 0.5D - posZ;
			motionX += (Math.signum(var1) * 0.5D - motionX) * 0.10000000149011612D;
			motionY += (Math.signum(var3) * 0.699999988079071D - motionY) * 0.10000000149011612D;
			motionZ += (Math.signum(var5) * 0.5D - motionZ) * 0.10000000149011612D;
			float var7 = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
			float var8 = MathHelper.wrapAngleTo180_float(var7 - rotationYaw);
			moveForward = 0.5F;
			rotationYaw += var8;
			if(rand.nextInt(100) == 0 && worldObj.isBlockNormalCube(MathHelper.floor_double(posX), (int) posY + 1, MathHelper.floor_double(posZ)))
			{
				setIsBatHanging(true);
			}
		}
	}
	
	@Override protected void updateFallState(double p_70064_1_, boolean p_70064_3_)
	{
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setByte("BatFlags", dataWatcher.getWatchableObjectByte(16));
	}
}
