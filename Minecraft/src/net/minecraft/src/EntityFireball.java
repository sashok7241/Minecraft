package net.minecraft.src;

import java.util.List;

public abstract class EntityFireball extends Entity
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private boolean inGround = false;
	public EntityLiving shootingEntity;
	private int ticksAlive;
	private int ticksInAir = 0;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	
	public EntityFireball(World p_i3571_1_)
	{
		super(p_i3571_1_);
		setSize(1.0F, 1.0F);
	}
	
	public EntityFireball(World p_i3572_1_, double p_i3572_2_, double p_i3572_4_, double p_i3572_6_, double p_i3572_8_, double p_i3572_10_, double p_i3572_12_)
	{
		super(p_i3572_1_);
		setSize(1.0F, 1.0F);
		setLocationAndAngles(p_i3572_2_, p_i3572_4_, p_i3572_6_, rotationYaw, rotationPitch);
		setPosition(p_i3572_2_, p_i3572_4_, p_i3572_6_);
		double var14 = MathHelper.sqrt_double(p_i3572_8_ * p_i3572_8_ + p_i3572_10_ * p_i3572_10_ + p_i3572_12_ * p_i3572_12_);
		accelerationX = p_i3572_8_ / var14 * 0.1D;
		accelerationY = p_i3572_10_ / var14 * 0.1D;
		accelerationZ = p_i3572_12_ / var14 * 0.1D;
	}
	
	public EntityFireball(World p_i3573_1_, EntityLiving p_i3573_2_, double p_i3573_3_, double p_i3573_5_, double p_i3573_7_)
	{
		super(p_i3573_1_);
		shootingEntity = p_i3573_2_;
		setSize(1.0F, 1.0F);
		setLocationAndAngles(p_i3573_2_.posX, p_i3573_2_.posY, p_i3573_2_.posZ, p_i3573_2_.rotationYaw, p_i3573_2_.rotationPitch);
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = motionY = motionZ = 0.0D;
		p_i3573_3_ += rand.nextGaussian() * 0.4D;
		p_i3573_5_ += rand.nextGaussian() * 0.4D;
		p_i3573_7_ += rand.nextGaussian() * 0.4D;
		double var9 = MathHelper.sqrt_double(p_i3573_3_ * p_i3573_3_ + p_i3573_5_ * p_i3573_5_ + p_i3573_7_ * p_i3573_7_);
		accelerationX = p_i3573_3_ / var9 * 0.1D;
		accelerationY = p_i3573_5_ / var9 * 0.1D;
		accelerationZ = p_i3573_7_ / var9 * 0.1D;
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			setBeenAttacked();
			if(p_70097_1_.getEntity() != null)
			{
				Vec3 var3 = p_70097_1_.getEntity().getLookVec();
				if(var3 != null)
				{
					motionX = var3.xCoord;
					motionY = var3.yCoord;
					motionZ = var3.zCoord;
					accelerationX = motionX * 0.1D;
					accelerationY = motionY * 0.1D;
					accelerationZ = motionZ * 0.1D;
				}
				if(p_70097_1_.getEntity() instanceof EntityLiving)
				{
					shootingEntity = (EntityLiving) p_70097_1_.getEntity();
				}
				return true;
			} else return false;
		}
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public float getBrightness(float p_70013_1_)
	{
		return 1.0F;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}
	
	@Override public float getCollisionBorderSize()
	{
		return 1.0F;
	}
	
	protected float getMotionFactor()
	{
		return 0.95F;
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	@Override public boolean isInRangeToRenderDist(double par1)
	{
		double var3 = boundingBox.getAverageEdgeLength() * 4.0D;
		var3 *= 64.0D;
		return par1 < var3 * var3;
	}
	
	protected abstract void onImpact(MovingObjectPosition var1);
	
	@Override public void onUpdate()
	{
		if(!worldObj.isRemote && (shootingEntity != null && shootingEntity.isDead || !worldObj.blockExists((int) posX, (int) posY, (int) posZ)))
		{
			setDead();
		} else
		{
			super.onUpdate();
			setFire(1);
			if(inGround)
			{
				int var1 = worldObj.getBlockId(xTile, yTile, zTile);
				if(var1 == inTile)
				{
					++ticksAlive;
					if(ticksAlive == 600)
					{
						setDead();
					}
					return;
				}
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksAlive = 0;
				ticksInAir = 0;
			} else
			{
				++ticksInAir;
			}
			Vec3 var15 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var3 = worldObj.clip(var15, var2);
			var15 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			if(var3 != null)
			{
				var2 = worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
			}
			Entity var4 = null;
			List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			for(int var8 = 0; var8 < var5.size(); ++var8)
			{
				Entity var9 = (Entity) var5.get(var8);
				if(var9.canBeCollidedWith() && (!var9.isEntityEqual(shootingEntity) || ticksInAir >= 25))
				{
					float var10 = 0.3F;
					AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
					MovingObjectPosition var12 = var11.calculateIntercept(var15, var2);
					if(var12 != null)
					{
						double var13 = var15.distanceTo(var12.hitVec);
						if(var13 < var6 || var6 == 0.0D)
						{
							var4 = var9;
							var6 = var13;
						}
					}
				}
			}
			if(var4 != null)
			{
				var3 = new MovingObjectPosition(var4);
			}
			if(var3 != null)
			{
				onImpact(var3);
			}
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			float var16 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) + 90.0F;
			for(rotationPitch = (float) (Math.atan2(var16, motionY) * 180.0D / Math.PI) - 90.0F; rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			{
				;
			}
			while(rotationPitch - prevRotationPitch >= 180.0F)
			{
				prevRotationPitch += 360.0F;
			}
			while(rotationYaw - prevRotationYaw < -180.0F)
			{
				prevRotationYaw -= 360.0F;
			}
			while(rotationYaw - prevRotationYaw >= 180.0F)
			{
				prevRotationYaw += 360.0F;
			}
			rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
			rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
			float var17 = getMotionFactor();
			if(isInWater())
			{
				for(int var19 = 0; var19 < 4; ++var19)
				{
					float var18 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * var18, posY - motionY * var18, posZ - motionZ * var18, motionX, motionY, motionZ);
				}
				var17 = 0.8F;
			}
			motionX += accelerationX;
			motionY += accelerationY;
			motionZ += accelerationZ;
			motionX *= var17;
			motionY *= var17;
			motionZ *= var17;
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, 0.0D, 0.0D, 0.0D);
			setPosition(posX, posY, posZ);
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		xTile = p_70037_1_.getShort("xTile");
		yTile = p_70037_1_.getShort("yTile");
		zTile = p_70037_1_.getShort("zTile");
		inTile = p_70037_1_.getByte("inTile") & 255;
		inGround = p_70037_1_.getByte("inGround") == 1;
		if(p_70037_1_.hasKey("direction"))
		{
			NBTTagList var2 = p_70037_1_.getTagList("direction");
			motionX = ((NBTTagDouble) var2.tagAt(0)).data;
			motionY = ((NBTTagDouble) var2.tagAt(1)).data;
			motionZ = ((NBTTagDouble) var2.tagAt(2)).data;
		} else
		{
			setDead();
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setShort("xTile", (short) xTile);
		p_70014_1_.setShort("yTile", (short) yTile);
		p_70014_1_.setShort("zTile", (short) zTile);
		p_70014_1_.setByte("inTile", (byte) inTile);
		p_70014_1_.setByte("inGround", (byte) (inGround ? 1 : 0));
		p_70014_1_.setTag("direction", newDoubleNBTList(new double[] { motionX, motionY, motionZ }));
	}
}
