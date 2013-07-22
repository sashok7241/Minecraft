package net.minecraft.src;

import java.util.List;

public class EntityArrow extends Entity implements IProjectile
{
	private int xTile = -1;
	private int yTile = -1;
	private int zTile = -1;
	private int inTile = 0;
	private int inData = 0;
	private boolean inGround = false;
	public int canBePickedUp = 0;
	public int arrowShake = 0;
	public Entity shootingEntity;
	private int ticksInGround;
	private int ticksInAir = 0;
	private double damage = 2.0D;
	private int knockbackStrength;
	
	public EntityArrow(World p_i3565_1_)
	{
		super(p_i3565_1_);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
	}
	
	public EntityArrow(World p_i3566_1_, double p_i3566_2_, double p_i3566_4_, double p_i3566_6_)
	{
		super(p_i3566_1_);
		renderDistanceWeight = 10.0D;
		setSize(0.5F, 0.5F);
		setPosition(p_i3566_2_, p_i3566_4_, p_i3566_6_);
		yOffset = 0.0F;
	}
	
	public EntityArrow(World p_i3567_1_, EntityLiving p_i3567_2_, EntityLiving p_i3567_3_, float p_i3567_4_, float p_i3567_5_)
	{
		super(p_i3567_1_);
		renderDistanceWeight = 10.0D;
		shootingEntity = p_i3567_2_;
		if(p_i3567_2_ instanceof EntityPlayer)
		{
			canBePickedUp = 1;
		}
		posY = p_i3567_2_.posY + p_i3567_2_.getEyeHeight() - 0.10000000149011612D;
		double var6 = p_i3567_3_.posX - p_i3567_2_.posX;
		double var8 = p_i3567_3_.boundingBox.minY + p_i3567_3_.height / 3.0F - posY;
		double var10 = p_i3567_3_.posZ - p_i3567_2_.posZ;
		double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
		if(var12 >= 1.0E-7D)
		{
			float var14 = (float) (Math.atan2(var10, var6) * 180.0D / Math.PI) - 90.0F;
			float var15 = (float) -(Math.atan2(var8, var12) * 180.0D / Math.PI);
			double var16 = var6 / var12;
			double var18 = var10 / var12;
			setLocationAndAngles(p_i3567_2_.posX + var16, posY, p_i3567_2_.posZ + var18, var14, var15);
			yOffset = 0.0F;
			float var20 = (float) var12 * 0.2F;
			setThrowableHeading(var6, var8 + var20, var10, p_i3567_4_, p_i3567_5_);
		}
	}
	
	public EntityArrow(World p_i3568_1_, EntityLiving p_i3568_2_, float p_i3568_3_)
	{
		super(p_i3568_1_);
		renderDistanceWeight = 10.0D;
		shootingEntity = p_i3568_2_;
		if(p_i3568_2_ instanceof EntityPlayer)
		{
			canBePickedUp = 1;
		}
		setSize(0.5F, 0.5F);
		setLocationAndAngles(p_i3568_2_.posX, p_i3568_2_.posY + p_i3568_2_.getEyeHeight(), p_i3568_2_.posZ, p_i3568_2_.rotationYaw, p_i3568_2_.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI);
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI);
		setThrowableHeading(motionX, motionY, motionZ, p_i3568_3_ * 1.5F, 1.0F);
	}
	
	@Override public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}
	
	public double getDamage()
	{
		return damage;
	}
	
	public boolean getIsCritical()
	{
		byte var1 = dataWatcher.getWatchableObjectByte(16);
		return (var1 & 1) != 0;
	}
	
	@Override public float getShadowSize()
	{
		return 0.0F;
	}
	
	@Override public void onCollideWithPlayer(EntityPlayer p_70100_1_)
	{
		if(!worldObj.isRemote && inGround && arrowShake <= 0)
		{
			boolean var2 = canBePickedUp == 1 || canBePickedUp == 2 && p_70100_1_.capabilities.isCreativeMode;
			if(canBePickedUp == 1 && !p_70100_1_.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1)))
			{
				var2 = false;
			}
			if(var2)
			{
				playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				p_70100_1_.onItemPickup(this, 1);
				setDead();
			}
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, var1) * 180.0D / Math.PI);
		}
		int var16 = worldObj.getBlockId(xTile, yTile, zTile);
		if(var16 > 0)
		{
			Block.blocksList[var16].setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
			AxisAlignedBB var2 = Block.blocksList[var16].getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
			if(var2 != null && var2.isVecInside(worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ)))
			{
				inGround = true;
			}
		}
		if(arrowShake > 0)
		{
			--arrowShake;
		}
		if(inGround)
		{
			int var18 = worldObj.getBlockId(xTile, yTile, zTile);
			int var19 = worldObj.getBlockMetadata(xTile, yTile, zTile);
			if(var18 == inTile && var19 == inData)
			{
				++ticksInGround;
				if(ticksInGround == 1200)
				{
					setDead();
				}
			} else
			{
				inGround = false;
				motionX *= rand.nextFloat() * 0.2F;
				motionY *= rand.nextFloat() * 0.2F;
				motionZ *= rand.nextFloat() * 0.2F;
				ticksInGround = 0;
				ticksInAir = 0;
			}
		} else
		{
			++ticksInAir;
			Vec3 var17 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 var3 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var4 = worldObj.rayTraceBlocks_do_do(var17, var3, false, true);
			var17 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			var3 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			if(var4 != null)
			{
				var3 = worldObj.getWorldVec3Pool().getVecFromPool(var4.hitVec.xCoord, var4.hitVec.yCoord, var4.hitVec.zCoord);
			}
			Entity var5 = null;
			List var6 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var7 = 0.0D;
			int var9;
			float var11;
			for(var9 = 0; var9 < var6.size(); ++var9)
			{
				Entity var10 = (Entity) var6.get(var9);
				if(var10.canBeCollidedWith() && (var10 != shootingEntity || ticksInAir >= 5))
				{
					var11 = 0.3F;
					AxisAlignedBB var12 = var10.boundingBox.expand(var11, var11, var11);
					MovingObjectPosition var13 = var12.calculateIntercept(var17, var3);
					if(var13 != null)
					{
						double var14 = var17.distanceTo(var13.hitVec);
						if(var14 < var7 || var7 == 0.0D)
						{
							var5 = var10;
							var7 = var14;
						}
					}
				}
			}
			if(var5 != null)
			{
				var4 = new MovingObjectPosition(var5);
			}
			if(var4 != null && var4.entityHit != null && var4.entityHit instanceof EntityPlayer)
			{
				EntityPlayer var21 = (EntityPlayer) var4.entityHit;
				if(var21.capabilities.disableDamage || shootingEntity instanceof EntityPlayer && !((EntityPlayer) shootingEntity).func_96122_a(var21))
				{
					var4 = null;
				}
			}
			float var20;
			float var27;
			if(var4 != null)
			{
				if(var4.entityHit != null)
				{
					var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					int var24 = MathHelper.ceiling_double_int(var20 * damage);
					if(getIsCritical())
					{
						var24 += rand.nextInt(var24 / 2 + 2);
					}
					DamageSource var22 = null;
					if(shootingEntity == null)
					{
						var22 = DamageSource.causeArrowDamage(this, this);
					} else
					{
						var22 = DamageSource.causeArrowDamage(this, shootingEntity);
					}
					if(isBurning() && !(var4.entityHit instanceof EntityEnderman))
					{
						var4.entityHit.setFire(5);
					}
					if(var4.entityHit.attackEntityFrom(var22, var24))
					{
						if(var4.entityHit instanceof EntityLiving)
						{
							EntityLiving var25 = (EntityLiving) var4.entityHit;
							if(!worldObj.isRemote)
							{
								var25.setArrowCountInEntity(var25.getArrowCountInEntity() + 1);
							}
							if(knockbackStrength > 0)
							{
								var27 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
								if(var27 > 0.0F)
								{
									var4.entityHit.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / var27, 0.1D, motionZ * knockbackStrength * 0.6000000238418579D / var27);
								}
							}
							if(shootingEntity != null)
							{
								EnchantmentThorns.func_92096_a(shootingEntity, var25, rand);
							}
							if(shootingEntity != null && var4.entityHit != shootingEntity && var4.entityHit instanceof EntityPlayer && shootingEntity instanceof EntityPlayerMP)
							{
								((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(6, 0));
							}
						}
						playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
						if(!(var4.entityHit instanceof EntityEnderman))
						{
							setDead();
						}
					} else
					{
						motionX *= -0.10000000149011612D;
						motionY *= -0.10000000149011612D;
						motionZ *= -0.10000000149011612D;
						rotationYaw += 180.0F;
						prevRotationYaw += 180.0F;
						ticksInAir = 0;
					}
				} else
				{
					xTile = var4.blockX;
					yTile = var4.blockY;
					zTile = var4.blockZ;
					inTile = worldObj.getBlockId(xTile, yTile, zTile);
					inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
					motionX = (float) (var4.hitVec.xCoord - posX);
					motionY = (float) (var4.hitVec.yCoord - posY);
					motionZ = (float) (var4.hitVec.zCoord - posZ);
					var20 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
					posX -= motionX / var20 * 0.05000000074505806D;
					posY -= motionY / var20 * 0.05000000074505806D;
					posZ -= motionZ / var20 * 0.05000000074505806D;
					playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
					inGround = true;
					arrowShake = 7;
					setIsCritical(false);
					if(inTile != 0)
					{
						Block.blocksList[inTile].onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
					}
				}
			}
			if(getIsCritical())
			{
				for(var9 = 0; var9 < 4; ++var9)
				{
					worldObj.spawnParticle("crit", posX + motionX * var9 / 4.0D, posY + motionY * var9 / 4.0D, posZ + motionZ * var9 / 4.0D, -motionX, -motionY + 0.2D, -motionZ);
				}
			}
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
			rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
			for(rotationPitch = (float) (Math.atan2(motionY, var20) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
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
			float var23 = 0.99F;
			var11 = 0.05F;
			if(isInWater())
			{
				for(int var26 = 0; var26 < 4; ++var26)
				{
					var27 = 0.25F;
					worldObj.spawnParticle("bubble", posX - motionX * var27, posY - motionY * var27, posZ - motionZ * var27, motionX, motionY, motionZ);
				}
				var23 = 0.8F;
			}
			motionX *= var23;
			motionY *= var23;
			motionZ *= var23;
			motionY -= var11;
			setPosition(posX, posY, posZ);
			doBlockCollisions();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		xTile = p_70037_1_.getShort("xTile");
		yTile = p_70037_1_.getShort("yTile");
		zTile = p_70037_1_.getShort("zTile");
		inTile = p_70037_1_.getByte("inTile") & 255;
		inData = p_70037_1_.getByte("inData") & 255;
		arrowShake = p_70037_1_.getByte("shake") & 255;
		inGround = p_70037_1_.getByte("inGround") == 1;
		if(p_70037_1_.hasKey("damage"))
		{
			damage = p_70037_1_.getDouble("damage");
		}
		if(p_70037_1_.hasKey("pickup"))
		{
			canBePickedUp = p_70037_1_.getByte("pickup");
		} else if(p_70037_1_.hasKey("player"))
		{
			canBePickedUp = p_70037_1_.getBoolean("player") ? 1 : 0;
		}
	}
	
	public void setDamage(double p_70239_1_)
	{
		damage = p_70239_1_;
	}
	
	public void setIsCritical(boolean p_70243_1_)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(p_70243_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}
	
	public void setKnockbackStrength(int p_70240_1_)
	{
		knockbackStrength = p_70240_1_;
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		setPosition(par1, par3, par5);
		setRotation(par7, par8);
	}
	
	@Override public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
	{
		float var9 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
		p_70186_1_ /= var9;
		p_70186_3_ /= var9;
		p_70186_5_ /= var9;
		p_70186_1_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
		p_70186_3_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
		p_70186_5_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
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
			prevRotationPitch = rotationPitch;
			prevRotationYaw = rotationYaw;
			setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
			ticksInGround = 0;
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		p_70014_1_.setShort("xTile", (short) xTile);
		p_70014_1_.setShort("yTile", (short) yTile);
		p_70014_1_.setShort("zTile", (short) zTile);
		p_70014_1_.setByte("inTile", (byte) inTile);
		p_70014_1_.setByte("inData", (byte) inData);
		p_70014_1_.setByte("shake", (byte) arrowShake);
		p_70014_1_.setByte("inGround", (byte) (inGround ? 1 : 0));
		p_70014_1_.setByte("pickup", (byte) canBePickedUp);
		p_70014_1_.setDouble("damage", damage);
	}
}
