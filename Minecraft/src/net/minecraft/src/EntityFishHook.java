package net.minecraft.src;

import java.util.List;

public class EntityFishHook extends Entity
{
	private int xTile;
	private int yTile;
	private int zTile;
	private int inTile;
	private boolean inGround;
	public int shake;
	public EntityPlayer angler;
	private int ticksInGround;
	private int ticksInAir;
	private int ticksCatchable;
	public Entity bobber;
	private int fishPosRotationIncrements;
	private double fishX;
	private double fishY;
	private double fishZ;
	private double fishYaw;
	private double fishPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;
	
	public EntityFishHook(World par1World)
	{
		super(par1World);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inGround = false;
		shake = 0;
		ticksInAir = 0;
		ticksCatchable = 0;
		bobber = null;
		setSize(0.25F, 0.25F);
		ignoreFrustumCheck = true;
	}
	
	public EntityFishHook(World par1World, double par2, double par4, double par6, EntityPlayer par8EntityPlayer)
	{
		this(par1World);
		setPosition(par2, par4, par6);
		ignoreFrustumCheck = true;
		angler = par8EntityPlayer;
		par8EntityPlayer.fishEntity = this;
	}
	
	public EntityFishHook(World par1World, EntityPlayer par2EntityPlayer)
	{
		super(par1World);
		xTile = -1;
		yTile = -1;
		zTile = -1;
		inTile = 0;
		inGround = false;
		shake = 0;
		ticksInAir = 0;
		ticksCatchable = 0;
		bobber = null;
		ignoreFrustumCheck = true;
		angler = par2EntityPlayer;
		angler.fishEntity = this;
		setSize(0.25F, 0.25F);
		setLocationAndAngles(par2EntityPlayer.posX, par2EntityPlayer.posY + 1.62D - par2EntityPlayer.yOffset, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
		posX -= MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		posY -= 0.10000000149011612D;
		posZ -= MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
		setPosition(posX, posY, posZ);
		yOffset = 0.0F;
		float var3 = 0.4F;
		motionX = -MathHelper.sin(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionZ = MathHelper.cos(rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float) Math.PI) * var3;
		motionY = -MathHelper.sin(rotationPitch / 180.0F * (float) Math.PI) * var3;
		calculateVelocity(motionX, motionY, motionZ, 1.5F, 1.0F);
	}
	
	public void calculateVelocity(double par1, double par3, double par5, float par7, float par8)
	{
		float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
		par1 /= var9;
		par3 /= var9;
		par5 /= var9;
		par1 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par3 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par5 += rand.nextGaussian() * 0.007499999832361937D * par8;
		par1 *= par7;
		par3 *= par7;
		par5 *= par7;
		motionX = par1;
		motionY = par3;
		motionZ = par5;
		float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
		prevRotationYaw = rotationYaw = (float) (Math.atan2(par1, par5) * 180.0D / Math.PI);
		prevRotationPitch = rotationPitch = (float) (Math.atan2(par3, var10) * 180.0D / Math.PI);
		ticksInGround = 0;
	}
	
	public int catchFish()
	{
		if(worldObj.isRemote) return 0;
		else
		{
			byte var1 = 0;
			if(bobber != null)
			{
				double var2 = angler.posX - posX;
				double var4 = angler.posY - posY;
				double var6 = angler.posZ - posZ;
				double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
				double var10 = 0.1D;
				bobber.motionX += var2 * var10;
				bobber.motionY += var4 * var10 + MathHelper.sqrt_double(var8) * 0.08D;
				bobber.motionZ += var6 * var10;
				var1 = 3;
			} else if(ticksCatchable > 0)
			{
				EntityItem var13 = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.fishRaw));
				double var3 = angler.posX - posX;
				double var5 = angler.posY - posY;
				double var7 = angler.posZ - posZ;
				double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
				double var11 = 0.1D;
				var13.motionX = var3 * var11;
				var13.motionY = var5 * var11 + MathHelper.sqrt_double(var9) * 0.08D;
				var13.motionZ = var7 * var11;
				worldObj.spawnEntityInWorld(var13);
				angler.addStat(StatList.fishCaughtStat, 1);
				angler.worldObj.spawnEntityInWorld(new EntityXPOrb(angler.worldObj, angler.posX, angler.posY + 0.5D, angler.posZ + 0.5D, rand.nextInt(6) + 1));
				var1 = 1;
			}
			if(inGround)
			{
				var1 = 2;
			}
			setDead();
			angler.fishEntity = null;
			return var1;
		}
	}
	
	@Override protected void entityInit()
	{
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
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(fishPosRotationIncrements > 0)
		{
			double var21 = posX + (fishX - posX) / fishPosRotationIncrements;
			double var22 = posY + (fishY - posY) / fishPosRotationIncrements;
			double var23 = posZ + (fishZ - posZ) / fishPosRotationIncrements;
			double var7 = MathHelper.wrapAngleTo180_double(fishYaw - rotationYaw);
			rotationYaw = (float) (rotationYaw + var7 / fishPosRotationIncrements);
			rotationPitch = (float) (rotationPitch + (fishPitch - rotationPitch) / fishPosRotationIncrements);
			--fishPosRotationIncrements;
			setPosition(var21, var22, var23);
			setRotation(rotationYaw, rotationPitch);
		} else
		{
			if(!worldObj.isRemote)
			{
				ItemStack var1 = angler.getCurrentEquippedItem();
				if(angler.isDead || !angler.isEntityAlive() || var1 == null || var1.getItem() != Item.fishingRod || getDistanceSqToEntity(angler) > 1024.0D)
				{
					setDead();
					angler.fishEntity = null;
					return;
				}
				if(bobber != null)
				{
					if(!bobber.isDead)
					{
						posX = bobber.posX;
						posY = bobber.boundingBox.minY + bobber.height * 0.8D;
						posZ = bobber.posZ;
						return;
					}
					bobber = null;
				}
			}
			if(shake > 0)
			{
				--shake;
			}
			if(inGround)
			{
				int var19 = worldObj.getBlockId(xTile, yTile, zTile);
				if(var19 == inTile)
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
			Vec3 var20 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			Vec3 var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			MovingObjectPosition var3 = worldObj.clip(var20, var2);
			var20 = worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ);
			var2 = worldObj.getWorldVec3Pool().getVecFromPool(posX + motionX, posY + motionY, posZ + motionZ);
			if(var3 != null)
			{
				var2 = worldObj.getWorldVec3Pool().getVecFromPool(var3.hitVec.xCoord, var3.hitVec.yCoord, var3.hitVec.zCoord);
			}
			Entity var4 = null;
			List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
			double var6 = 0.0D;
			double var13;
			for(int var8 = 0; var8 < var5.size(); ++var8)
			{
				Entity var9 = (Entity) var5.get(var8);
				if(var9.canBeCollidedWith() && (var9 != angler || ticksInAir >= 5))
				{
					float var10 = 0.3F;
					AxisAlignedBB var11 = var9.boundingBox.expand(var10, var10, var10);
					MovingObjectPosition var12 = var11.calculateIntercept(var20, var2);
					if(var12 != null)
					{
						var13 = var20.distanceTo(var12.hitVec);
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
				if(var3.entityHit != null)
				{
					if(var3.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, angler), 0))
					{
						bobber = var3.entityHit;
					}
				} else
				{
					inGround = true;
				}
			}
			if(!inGround)
			{
				moveEntity(motionX, motionY, motionZ);
				float var24 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
				rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
				for(rotationPitch = (float) (Math.atan2(motionY, var24) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
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
				float var25 = 0.92F;
				if(onGround || isCollidedHorizontally)
				{
					var25 = 0.5F;
				}
				byte var27 = 5;
				double var26 = 0.0D;
				for(int var29 = 0; var29 < var27; ++var29)
				{
					double var14 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (var29 + 0) / var27 - 0.125D + 0.125D;
					double var16 = boundingBox.minY + (boundingBox.maxY - boundingBox.minY) * (var29 + 1) / var27 - 0.125D + 0.125D;
					AxisAlignedBB var18 = AxisAlignedBB.getAABBPool().getAABB(boundingBox.minX, var14, boundingBox.minZ, boundingBox.maxX, var16, boundingBox.maxZ);
					if(worldObj.isAABBInMaterial(var18, Material.water))
					{
						var26 += 1.0D / var27;
					}
				}
				if(var26 > 0.0D)
				{
					if(ticksCatchable > 0)
					{
						--ticksCatchable;
					} else
					{
						short var28 = 500;
						if(worldObj.canLightningStrikeAt(MathHelper.floor_double(posX), MathHelper.floor_double(posY) + 1, MathHelper.floor_double(posZ)))
						{
							var28 = 300;
						}
						if(rand.nextInt(var28) == 0)
						{
							ticksCatchable = rand.nextInt(30) + 10;
							motionY -= 0.20000000298023224D;
							playSound("random.splash", 0.25F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
							float var30 = MathHelper.floor_double(boundingBox.minY);
							int var15;
							float var17;
							float var31;
							for(var15 = 0; var15 < 1.0F + width * 20.0F; ++var15)
							{
								var31 = (rand.nextFloat() * 2.0F - 1.0F) * width;
								var17 = (rand.nextFloat() * 2.0F - 1.0F) * width;
								worldObj.spawnParticle("bubble", posX + var31, var30 + 1.0F, posZ + var17, motionX, motionY - rand.nextFloat() * 0.2F, motionZ);
							}
							for(var15 = 0; var15 < 1.0F + width * 20.0F; ++var15)
							{
								var31 = (rand.nextFloat() * 2.0F - 1.0F) * width;
								var17 = (rand.nextFloat() * 2.0F - 1.0F) * width;
								worldObj.spawnParticle("splash", posX + var31, var30 + 1.0F, posZ + var17, motionX, motionY, motionZ);
							}
						}
					}
				}
				if(ticksCatchable > 0)
				{
					motionY -= rand.nextFloat() * rand.nextFloat() * rand.nextFloat() * 0.2D;
				}
				var13 = var26 * 2.0D - 1.0D;
				motionY += 0.03999999910593033D * var13;
				if(var26 > 0.0D)
				{
					var25 = (float) (var25 * 0.9D);
					motionY *= 0.8D;
				}
				motionX *= var25;
				motionY *= var25;
				motionZ *= var25;
				setPosition(posX, posY, posZ);
			}
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		xTile = par1NBTTagCompound.getShort("xTile");
		yTile = par1NBTTagCompound.getShort("yTile");
		zTile = par1NBTTagCompound.getShort("zTile");
		inTile = par1NBTTagCompound.getByte("inTile") & 255;
		shake = par1NBTTagCompound.getByte("shake") & 255;
		inGround = par1NBTTagCompound.getByte("inGround") == 1;
	}
	
	@Override public void setDead()
	{
		super.setDead();
		if(angler != null)
		{
			angler.fishEntity = null;
		}
	}
	
	@Override public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		fishX = par1;
		fishY = par3;
		fishZ = par5;
		fishYaw = par7;
		fishPitch = par8;
		fishPosRotationIncrements = par9;
		motionX = velocityX;
		motionY = velocityY;
		motionZ = velocityZ;
	}
	
	@Override public void setVelocity(double par1, double par3, double par5)
	{
		velocityX = motionX = par1;
		velocityY = motionY = par3;
		velocityZ = motionZ = par5;
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setShort("xTile", (short) xTile);
		par1NBTTagCompound.setShort("yTile", (short) yTile);
		par1NBTTagCompound.setShort("zTile", (short) zTile);
		par1NBTTagCompound.setByte("inTile", (byte) inTile);
		par1NBTTagCompound.setByte("shake", (byte) shake);
		par1NBTTagCompound.setByte("inGround", (byte) (inGround ? 1 : 0));
	}
}
