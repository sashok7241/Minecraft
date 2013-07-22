package net.minecraft.src;

import java.util.List;

public class EntityBoat extends Entity
{
	private boolean field_70279_a;
	private double speedMultiplier;
	private int boatPosRotationIncrements;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	
	public EntityBoat(World par1World)
	{
		super(par1World);
		field_70279_a = true;
		speedMultiplier = 0.07D;
		preventEntitySpawning = true;
		setSize(1.5F, 0.6F);
		yOffset = height / 2.0F;
	}
	
	public EntityBoat(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		setPosition(par2, par4 + yOffset, par6);
		motionX = 0.0D;
		motionY = 0.0D;
		motionZ = 0.0D;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		if(isEntityInvulnerable()) return false;
		else if(!worldObj.isRemote && !isDead)
		{
			setForwardDirection(-getForwardDirection());
			setTimeSinceHit(10);
			setDamageTaken(getDamageTaken() + par2 * 10);
			setBeenAttacked();
			boolean var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode;
			if(var3 || getDamageTaken() > 40)
			{
				if(riddenByEntity != null)
				{
					riddenByEntity.mountEntity(this);
				}
				if(!var3)
				{
					dropItemWithOffset(Item.boat.itemID, 1, 0.0F);
				}
				setDead();
			}
			return true;
		} else return true;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override public boolean canBePushed()
	{
		return true;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(1));
		dataWatcher.addObject(19, new Integer(0));
	}
	
	public void func_70270_d(boolean par1)
	{
		field_70279_a = par1;
	}
	
	@Override public AxisAlignedBB getBoundingBox()
	{
		return boundingBox;
	}
	
	@Override public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.boundingBox;
	}
	
	public int getDamageTaken()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}
	
	public int getForwardDirection()
	{
		return dataWatcher.getWatchableObjectInt(18);
	}
	
	@Override public double getMountedYOffset()
	{
		return height * 0.0D - 0.30000001192092896D;
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	public int getTimeSinceHit()
	{
		return dataWatcher.getWatchableObjectInt(17);
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(riddenByEntity != null && riddenByEntity instanceof EntityPlayer && riddenByEntity != par1EntityPlayer) return true;
		else
		{
			if(!worldObj.isRemote)
			{
				par1EntityPlayer.mountEntity(this);
			}
			return true;
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(getTimeSinceHit() > 0)
		{
			setTimeSinceHit(getTimeSinceHit() - 1);
		}
		if(getDamageTaken() > 0)
		{
			setDamageTaken(getDamageTaken() - 1);
		}
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		byte var1 = 5;
		double var2 = 0.0D;
		for(int var4 = 0; var4 < var1; ++var4)
		{
			double var5 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (var4 + 0) / var1 - 0.125D;
			double var7 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (var4 + 1) / var1 - 0.125D;
			AxisAlignedBB var9 = AxisAlignedBB.getAABBPool().getAABB(boundingBox.minX, var5, boundingBox.minZ, boundingBox.maxX, var7, boundingBox.maxZ);
			if(worldObj.isAABBInMaterial(var9, Material.water))
			{
				var2 += 1.0D / var1;
			}
		}
		double var23 = Math.sqrt(motionX * motionX + motionZ * motionZ);
		double var6;
		double var8;
		if(var23 > 0.26249999999999996D)
		{
			var6 = Math.cos(rotationYaw * Math.PI / 180.0D);
			var8 = Math.sin(rotationYaw * Math.PI / 180.0D);
			for(int var10 = 0; var10 < 1.0D + var23 * 60.0D; ++var10)
			{
				double var11 = rand.nextFloat() * 2.0F - 1.0F;
				double var13 = (rand.nextInt(2) * 2 - 1) * 0.7D;
				double var15;
				double var17;
				if(rand.nextBoolean())
				{
					var15 = posX - var6 * var11 * 0.8D + var8 * var13;
					var17 = posZ - var8 * var11 * 0.8D - var6 * var13;
					worldObj.spawnParticle("splash", var15, posY - 0.125D, var17, motionX, motionY, motionZ);
				} else
				{
					var15 = posX + var6 + var8 * var11 * 0.7D;
					var17 = posZ + var8 - var6 * var11 * 0.7D;
					worldObj.spawnParticle("splash", var15, posY - 0.125D, var17, motionX, motionY, motionZ);
				}
			}
		}
		double var12;
		double var25;
		if(worldObj.isRemote && field_70279_a)
		{
			if(boatPosRotationIncrements > 0)
			{
				var6 = posX + (boatX - posX) / boatPosRotationIncrements;
				var8 = posY + (boatY - posY) / boatPosRotationIncrements;
				var25 = posZ + (boatZ - posZ) / boatPosRotationIncrements;
				var12 = MathHelper.wrapAngleTo180_double(boatYaw - rotationYaw);
				rotationYaw = (float) (rotationYaw + var12 / boatPosRotationIncrements);
				rotationPitch = (float) (rotationPitch + (boatPitch - rotationPitch) / boatPosRotationIncrements);
				--boatPosRotationIncrements;
				setPosition(var6, var8, var25);
				setRotation(rotationYaw, rotationPitch);
			} else
			{
				var6 = posX + motionX;
				var8 = posY + motionY;
				var25 = posZ + motionZ;
				setPosition(var6, var8, var25);
				if(onGround)
				{
					motionX *= 0.5D;
					motionY *= 0.5D;
					motionZ *= 0.5D;
				}
				motionX *= 0.9900000095367432D;
				motionY *= 0.949999988079071D;
				motionZ *= 0.9900000095367432D;
			}
		} else
		{
			if(var2 < 1.0D)
			{
				var6 = var2 * 2.0D - 1.0D;
				motionY += 0.03999999910593033D * var6;
			} else
			{
				if(motionY < 0.0D)
				{
					motionY /= 2.0D;
				}
				motionY += 0.007000000216066837D;
			}
			if(riddenByEntity != null)
			{
				motionX += riddenByEntity.motionX * speedMultiplier;
				motionZ += riddenByEntity.motionZ * speedMultiplier;
			}
			var6 = Math.sqrt(motionX * motionX + motionZ * motionZ);
			if(var6 > 0.35D)
			{
				var8 = 0.35D / var6;
				motionX *= var8;
				motionZ *= var8;
				var6 = 0.35D;
			}
			if(var6 > var23 && speedMultiplier < 0.35D)
			{
				speedMultiplier += (0.35D - speedMultiplier) / 35.0D;
				if(speedMultiplier > 0.35D)
				{
					speedMultiplier = 0.35D;
				}
			} else
			{
				speedMultiplier -= (speedMultiplier - 0.07D) / 35.0D;
				if(speedMultiplier < 0.07D)
				{
					speedMultiplier = 0.07D;
				}
			}
			if(onGround)
			{
				motionX *= 0.5D;
				motionY *= 0.5D;
				motionZ *= 0.5D;
			}
			moveEntity(motionX, motionY, motionZ);
			if(isCollidedHorizontally && var23 > 0.2D)
			{
				if(!worldObj.isRemote && !isDead)
				{
					setDead();
					int var24;
					for(var24 = 0; var24 < 3; ++var24)
					{
						dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
					}
					for(var24 = 0; var24 < 2; ++var24)
					{
						dropItemWithOffset(Item.stick.itemID, 1, 0.0F);
					}
				}
			} else
			{
				motionX *= 0.9900000095367432D;
				motionY *= 0.949999988079071D;
				motionZ *= 0.9900000095367432D;
			}
			rotationPitch = 0.0F;
			var8 = rotationYaw;
			var25 = prevPosX - posX;
			var12 = prevPosZ - posZ;
			if(var25 * var25 + var12 * var12 > 0.001D)
			{
				var8 = (float) (Math.atan2(var12, var25) * 180.0D / Math.PI);
			}
			double var14 = MathHelper.wrapAngleTo180_double(var8 - rotationYaw);
			if(var14 > 20.0D)
			{
				var14 = 20.0D;
			}
			if(var14 < -20.0D)
			{
				var14 = -20.0D;
			}
			rotationYaw = (float) (rotationYaw + var14);
			setRotation(rotationYaw, rotationPitch);
			if(!worldObj.isRemote)
			{
				List var16 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));
				int var26;
				if(var16 != null && !var16.isEmpty())
				{
					for(var26 = 0; var26 < var16.size(); ++var26)
					{
						Entity var18 = (Entity) var16.get(var26);
						if(var18 != riddenByEntity && var18.canBePushed() && var18 instanceof EntityBoat)
						{
							var18.applyEntityCollision(this);
						}
					}
				}
				for(var26 = 0; var26 < 4; ++var26)
				{
					int var27 = MathHelper.floor_double(posX + (var26 % 2 - 0.5D) * 0.8D);
					int var19 = MathHelper.floor_double(posZ + (var26 / 2 - 0.5D) * 0.8D);
					for(int var20 = 0; var20 < 2; ++var20)
					{
						int var21 = MathHelper.floor_double(posY) + var20;
						int var22 = worldObj.getBlockId(var27, var21, var19);
						if(var22 == Block.snow.blockID)
						{
							worldObj.setBlockToAir(var27, var21, var19);
						} else if(var22 == Block.waterlily.blockID)
						{
							worldObj.destroyBlock(var27, var21, var19, true);
						}
					}
				}
				if(riddenByEntity != null && riddenByEntity.isDead)
				{
					riddenByEntity = null;
				}
			}
		}
	}
	
	@Override public void performHurtAnimation()
	{
		setForwardDirection(-getForwardDirection());
		setTimeSinceHit(10);
		setDamageTaken(getDamageTaken() * 11);
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
	
	public void setDamageTaken(int par1)
	{
		dataWatcher.updateObject(19, Integer.valueOf(par1));
	}
	
	public void setForwardDirection(int par1)
	{
		dataWatcher.updateObject(18, Integer.valueOf(par1));
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		if(field_70279_a)
		{
			boatPosRotationIncrements = par9 + 5;
		} else
		{
			double var10 = par1 - posX;
			double var12 = par3 - posY;
			double var14 = par5 - posZ;
			double var16 = var10 * var10 + var12 * var12 + var14 * var14;
			if(var16 <= 1.0D) return;
			boatPosRotationIncrements = 3;
		}
		boatX = par1;
		boatY = par3;
		boatZ = par5;
		boatYaw = par7;
		boatPitch = par8;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}
	
	public void setTimeSinceHit(int par1)
	{
		dataWatcher.updateObject(17, Integer.valueOf(par1));
	}
	
	@Override public void setVelocity(double par1, double par3, double par5)
	{
		velocityX = motionX = par1;
		velocityY = motionY = par3;
		velocityZ = motionZ = par5;
	}
	
	@Override public void updateRiderPosition()
	{
		if(riddenByEntity != null)
		{
			double var1 = Math.cos(rotationYaw * Math.PI / 180.0D) * 0.4D;
			double var3 = Math.sin(rotationYaw * Math.PI / 180.0D) * 0.4D;
			riddenByEntity.setPosition(posX + var1, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + var3);
		}
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
}
