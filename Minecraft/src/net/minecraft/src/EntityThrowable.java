package net.minecraft.src;

import java.util.List;

public abstract class EntityThrowable extends Entity implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	protected boolean inGround = false;
	public int throwableShake = 0;
	private EntityLiving thrower;
	private String throwerName = null;
	private int ticksInGround;
	private int ticksInAir = 0;
	
	public EntityThrowable(World p_i3583_1_)
	{
		super(p_i3583_1_);
		setSize(0.25F, 0.25F);
	}
	
	public EntityThrowable(World p_i3585_1_, double p_i3585_2_, double p_i3585_4_, double p_i3585_6_)
	{
		super(p_i3585_1_);
		ticksInGround = 0;
		setSize(0.25F, 0.25F);
		setPosition(p_i3585_2_, p_i3585_4_, p_i3585_6_);
		yOffset = 0.0F;
	}
	
	public EntityThrowable(World p_i3584_1_, EntityLiving p_i3584_2_)
	{
		super(p_i3584_1_);
		thrower = p_i3584_2_;
		setSize(0.25F, 0.25F);
		setLocationAndAngles(p_i3584_2_.posX, p_i3584_2_.posY + p_i3584_2_.getEyeHeight(), p_i3584_2_.posZ, p_i3584_2_.rotationYaw, p_i3584_2_.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		float var3 = 0.4F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionY = -MathHelper.sin((rotationPitch + func_70183_g()) / 180.0F * (float) Math.PI) * var3;
		setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
	}
	
	@Override protected void entityInit()
	{
	}
	
	protected float func_70182_d()
	{
		return 1.5F;
	}
	
	protected float func_70183_g()
	{
		return 0.0F;
	}
	
	protected float getGravityVelocity()
	{
		return 0.03F;
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	public EntityLiving getThrower()
	{
		if(thrower == null && throwerName != null && throwerName.length() > 0)
		{
			thrower = worldObj.getPlayerEntityByName(throwerName);
		}
		return thrower;
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
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		if(throwableShake > 0)
		{
			--throwableShake;
		}
		if(inGround)
		{
			int var1 = worldObj.getBlockId(xTile, yTile, zTile);
			if(var1 == inTile)
			{
				++ticksInGround;
				if(ticksInGround == 1200)
				{
					setDead();
				}
				return;
			}
			inGround = false;
			motionX *= rand.nextFloat() * 0.2F;
			motionY *= rand.nextFloat() * 0.2F;
			motionZ *= rand.nextFloat() * 0.2F;
			ticksInGround = 0;
			ticksInAir = 0;
		} else
		{
			++ticksInAir;
		}
		Vec3 var16 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
		Vec3 var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
		MovingObjectPosition var3 = worldObj.clip(var16, var2);
		var16 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
		var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
		if(var3 != null)
		{
			var2 = worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
		}
		if(!worldObj.isRemote)
		{
			Entity var4 = null;
			List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			EntityLiving var8 = getThrower();
			for(int var9 = 0; var9 < var5.size(); ++var9)
			{
				Entity var10 = (Entity) var5.get(var9);
				if(var10.canBeCollidedWith() && (var10 != var8 || ticksInAir >= 5))
				{
					float var11 = 0.3F;
					AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
					MovingObjectPosition var13 = var12.calculateIntercept(var16, var2);
					if(var13 != null)
					{
						double var14 = var16.distanceTo(var13.hitVec);
						if(var14 < var6 || var6 == 0.0D)
						{
							var4 = var10;
							var6 = var14;
						}
					}
				}
			}
			if(var4 != null)
			{
				var3 = new MovingObjectPosition(var4);
			}
		}
		if(var3 != null)
		{
			if(var3.typeOfHit == EnumMovingObjectType.TILE && worldObj.getBlockId(var3.blockX, var3.blockY, var3.blockZ) == Block.portal.blockID)
			{
				setInPortal();
			} else
			{
				onImpact(var3);
			}
		}
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float var17 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
		for(rotationPitch = (float) (Math.atan2(motionY, var17) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
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
		float var18 = 0.99F;
		float var19 = getGravityVelocity();
		if(isInWater())
		{
			for(int var7 = 0; var7 < 4; ++var7)
			{
				float var20 = 0.25F;
				worldObj.spawnParticle("bubble", posX - motionX * var20, posY - motionY * var20, posZ - motionZ * var20, motionX, motionY, motionZ);
			}
			var18 = 0.8F;
		}
		motionX *= var18;
		motionY *= var18;
		motionZ *= var18;
		motionY -= var19;
		setPosition(posX, posY, posZ);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		xTile = p_70037_1_.getShort("xTile");
		yTile = p_70037_1_.getShort("yTile");
		zTile = p_70037_1_.getShort("zTile");
		inTile = p_70037_1_.getByte("inTile") & 255;
		throwableShake = p_70037_1_.getByte("shake") & 255;
		inGround = p_70037_1_.getByte("inGround") == 1;
		throwerName = p_70037_1_.getString("ownerName");
		if(throwerName != null && throwerName.length() == 0)
		{
			throwerName = null;
		}
	}
	
	@Override public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
	{
		float var9 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
		p_70186_1_ /= var9;
		p_70186_3_ /= var9;
		p_70186_5_ /= var9;
		p_70186_1_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
		p_70186_3_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
		p_70186_5_ += rand.nextGaussian() * 0.007499999832361937D * p_70186_8_;
		p_70186_1_ *= p_70186_7_;
		p_70186_3_ *= p_70186_7_;
		p_70186_5_ *= p_70186_7_;
		motionX = p_70186_1_;
		motionY = p_70186_3_;
		motionZ = p_70186_5_;
		float var10 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(p_70186_3_, var10) * 180.0D / Math.PI);
		ticksInGround = 0;
	}
	
	@Override public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var7 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var7) * 180.0D / Math.PI);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setShort("xTile", (short) xTile);
		p_70014_1_.setShort("yTile", (short) yTile);
		p_70014_1_.setShort("zTile", (short) zTile);
		p_70014_1_.setByte("inTile", (byte) inTile);
		p_70014_1_.setByte("shake", (byte) throwableShake);
		p_70014_1_.setByte("inGround", (byte) (inGround ? 1 : 0));
		if((throwerName == null || throwerName.length() == 0) && thrower != null && thrower instanceof EntityPlayer)
		{
			throwerName = thrower.getEntityName();
		}
		p_70014_1_.setString("ownerName", throwerName == null ? "" : throwerName);
	}
}
