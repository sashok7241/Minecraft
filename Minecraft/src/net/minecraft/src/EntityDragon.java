package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityDragon extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob
{
	public double targetX;
	public double targetY;
	public double targetZ;
	public double[][] ringBuffer = new double[64][3];
	public int ringBufferIndex = -1;
	public EntityDragonPart[] dragonPartArray;
	public EntityDragonPart dragonPartHead;
	public EntityDragonPart dragonPartBody;
	public EntityDragonPart dragonPartTail1;
	public EntityDragonPart dragonPartTail2;
	public EntityDragonPart dragonPartTail3;
	public EntityDragonPart dragonPartWing1;
	public EntityDragonPart dragonPartWing2;
	public float prevAnimTime;
	public float animTime;
	public boolean forceNewTarget;
	public boolean slowed;
	private Entity target;
	public int deathTicks;
	public EntityEnderCrystal healingEnderCrystal;
	
	public EntityDragon(World par1World)
	{
		super(par1World);
		dragonPartArray = new EntityDragonPart[] { dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F), dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F), dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F), dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F) };
		setEntityHealth(func_110138_aP());
		setSize(16.0F, 8.0F);
		noClip = true;
		isImmuneToFire = true;
		targetY = 100.0D;
		ignoreFrustumCheck = true;
	}
	
	private void attackEntitiesInList(List par1List)
	{
		for(int var2 = 0; var2 < par1List.size(); ++var2)
		{
			Entity var3 = (Entity) par1List.get(var2);
			if(var3 instanceof EntityLivingBase)
			{
				var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		return false;
	}
	
	@Override public boolean attackEntityFromPart(EntityDragonPart par1EntityDragonPart, DamageSource par2DamageSource, float par3)
	{
		if(par1EntityDragonPart != dragonPartHead)
		{
			par3 = par3 / 4.0F + 1.0F;
		}
		float var4 = rotationYaw * (float) Math.PI / 180.0F;
		float var5 = MathHelper.sin(var4);
		float var6 = MathHelper.cos(var4);
		targetX = posX + var5 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		targetY = posY + rand.nextFloat() * 3.0F + 1.0D;
		targetZ = posZ - var6 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F;
		target = null;
		if(par2DamageSource.getEntity() instanceof EntityPlayer || par2DamageSource.isExplosion())
		{
			func_82195_e(par2DamageSource, par3);
		}
		return true;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return false;
	}
	
	private void collideWithEntities(List par1List)
	{
		double var2 = (dragonPartBody.boundingBox.minX + dragonPartBody.boundingBox.maxX) / 2.0D;
		double var4 = (dragonPartBody.boundingBox.minZ + dragonPartBody.boundingBox.maxZ) / 2.0D;
		Iterator var6 = par1List.iterator();
		while(var6.hasNext())
		{
			Entity var7 = (Entity) var6.next();
			if(var7 instanceof EntityLivingBase)
			{
				double var8 = var7.posX - var2;
				double var10 = var7.posZ - var4;
				double var12 = var8 * var8 + var10 * var10;
				var7.addVelocity(var8 / var12 * 4.0D, 0.20000000298023224D, var10 / var12 * 4.0D);
			}
		}
	}
	
	private void createEnderPortal(int par1, int par2)
	{
		byte var3 = 64;
		BlockEndPortal.bossDefeated = true;
		byte var4 = 4;
		for(int var5 = var3 - 1; var5 <= var3 + 32; ++var5)
		{
			for(int var6 = par1 - var4; var6 <= par1 + var4; ++var6)
			{
				for(int var7 = par2 - var4; var7 <= par2 + var4; ++var7)
				{
					double var8 = var6 - par1;
					double var10 = var7 - par2;
					double var12 = var8 * var8 + var10 * var10;
					if(var12 <= (var4 - 0.5D) * (var4 - 0.5D))
					{
						if(var5 < var3)
						{
							if(var12 <= (var4 - 1 - 0.5D) * (var4 - 1 - 0.5D))
							{
								worldObj.setBlock(var6, var5, var7, Block.bedrock.blockID);
							}
						} else if(var5 > var3)
						{
							worldObj.setBlock(var6, var5, var7, 0);
						} else if(var12 > (var4 - 1 - 0.5D) * (var4 - 1 - 0.5D))
						{
							worldObj.setBlock(var6, var5, var7, Block.bedrock.blockID);
						} else
						{
							worldObj.setBlock(var6, var5, var7, Block.endPortal.blockID);
						}
					}
				}
			}
		}
		worldObj.setBlock(par1, var3 + 0, par2, Block.bedrock.blockID);
		worldObj.setBlock(par1, var3 + 1, par2, Block.bedrock.blockID);
		worldObj.setBlock(par1, var3 + 2, par2, Block.bedrock.blockID);
		worldObj.setBlock(par1 - 1, var3 + 2, par2, Block.torchWood.blockID);
		worldObj.setBlock(par1 + 1, var3 + 2, par2, Block.torchWood.blockID);
		worldObj.setBlock(par1, var3 + 2, par2 - 1, Block.torchWood.blockID);
		worldObj.setBlock(par1, var3 + 2, par2 + 1, Block.torchWood.blockID);
		worldObj.setBlock(par1, var3 + 3, par2, Block.bedrock.blockID);
		worldObj.setBlock(par1, var3 + 4, par2, Block.dragonEgg.blockID);
		BlockEndPortal.bossDefeated = false;
	}
	
	@Override protected void despawnEntity()
	{
	}
	
	private boolean destroyBlocksInAABB(AxisAlignedBB par1AxisAlignedBB)
	{
		int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		int var3 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		int var4 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX);
		int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY);
		int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ);
		boolean var8 = false;
		boolean var9 = false;
		for(int var10 = var2; var10 <= var5; ++var10)
		{
			for(int var11 = var3; var11 <= var6; ++var11)
			{
				for(int var12 = var4; var12 <= var7; ++var12)
				{
					int var13 = worldObj.getBlockId(var10, var11, var12);
					if(var13 != 0)
					{
						if(var13 != Block.obsidian.blockID && var13 != Block.whiteStone.blockID && var13 != Block.bedrock.blockID && worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
						{
							var9 = worldObj.setBlockToAir(var10, var11, var12) || var9;
						} else
						{
							var8 = true;
						}
					}
				}
			}
		}
		if(var9)
		{
			double var16 = par1AxisAlignedBB.minX + (par1AxisAlignedBB.maxX - par1AxisAlignedBB.minX) * rand.nextFloat();
			double var17 = par1AxisAlignedBB.minY + (par1AxisAlignedBB.maxY - par1AxisAlignedBB.minY) * rand.nextFloat();
			double var14 = par1AxisAlignedBB.minZ + (par1AxisAlignedBB.maxZ - par1AxisAlignedBB.minZ) * rand.nextFloat();
			worldObj.spawnParticle("largeexplode", var16, var17, var14, 0.0D, 0.0D, 0.0D);
		}
		return var8;
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
	}
	
	@Override public World func_82194_d()
	{
		return worldObj;
	}
	
	protected boolean func_82195_e(DamageSource par1DamageSource, float par2)
	{
		return super.attackEntityFrom(par1DamageSource, par2);
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.enderdragon.hit";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.enderdragon.growl";
	}
	
	public double[] getMovementOffsets(int par1, float par2)
	{
		if(func_110143_aJ() <= 0.0F)
		{
			par2 = 0.0F;
		}
		par2 = 1.0F - par2;
		int var3 = ringBufferIndex - par1 * 1 & 63;
		int var4 = ringBufferIndex - par1 * 1 - 1 & 63;
		double[] var5 = new double[3];
		double var6 = ringBuffer[var3][0];
		double var8 = MathHelper.wrapAngleTo180_double(ringBuffer[var4][0] - var6);
		var5[0] = var6 + var8 * par2;
		var6 = ringBuffer[var3][1];
		var8 = ringBuffer[var4][1] - var6;
		var5[1] = var6 + var8 * par2;
		var5[2] = ringBuffer[var3][2] + (ringBuffer[var4][2] - ringBuffer[var3][2]) * par2;
		return var5;
	}
	
	@Override public Entity[] getParts()
	{
		return dragonPartArray;
	}
	
	@Override protected float getSoundVolume()
	{
		return 5.0F;
	}
	
	@Override protected void onDeathUpdate()
	{
		++deathTicks;
		if(deathTicks >= 180 && deathTicks <= 200)
		{
			float var1 = (rand.nextFloat() - 0.5F) * 8.0F;
			float var2 = (rand.nextFloat() - 0.5F) * 4.0F;
			float var3 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle("hugeexplosion", posX + var1, posY + 2.0D + var2, posZ + var3, 0.0D, 0.0D, 0.0D);
		}
		int var4;
		int var5;
		if(!worldObj.isRemote)
		{
			if(deathTicks > 150 && deathTicks % 5 == 0)
			{
				var4 = 1000;
				while(var4 > 0)
				{
					var5 = EntityXPOrb.getXPSplit(var4);
					var4 -= var5;
					worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var5));
				}
			}
			if(deathTicks == 1)
			{
				worldObj.func_82739_e(1018, (int) posX, (int) posY, (int) posZ, 0);
			}
		}
		moveEntity(0.0D, 0.10000000149011612D, 0.0D);
		renderYawOffset = rotationYaw += 20.0F;
		if(deathTicks == 200 && !worldObj.isRemote)
		{
			var4 = 2000;
			while(var4 > 0)
			{
				var5 = EntityXPOrb.getXPSplit(var4);
				var4 -= var5;
				worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var5));
			}
			createEnderPortal(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
			setDead();
		}
	}
	
	@Override public void onLivingUpdate()
	{
		float var1;
		float var2;
		if(worldObj.isRemote)
		{
			var1 = MathHelper.cos(animTime * (float) Math.PI * 2.0F);
			var2 = MathHelper.cos(prevAnimTime * (float) Math.PI * 2.0F);
			if(var2 <= -0.3F && var1 >= -0.3F)
			{
				worldObj.playSound(posX, posY, posZ, "mob.enderdragon.wings", 5.0F, 0.8F + rand.nextFloat() * 0.3F, false);
			}
		}
		prevAnimTime = animTime;
		float var3;
		if(func_110143_aJ() <= 0.0F)
		{
			var1 = (rand.nextFloat() - 0.5F) * 8.0F;
			var2 = (rand.nextFloat() - 0.5F) * 4.0F;
			var3 = (rand.nextFloat() - 0.5F) * 8.0F;
			worldObj.spawnParticle("largeexplode", posX + var1, posY + 2.0D + var2, posZ + var3, 0.0D, 0.0D, 0.0D);
		} else
		{
			updateDragonEnderCrystal();
			var1 = 0.2F / (MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 10.0F + 1.0F);
			var1 *= (float) Math.pow(2.0D, motionY);
			if(slowed)
			{
				animTime += var1 * 0.5F;
			} else
			{
				animTime += var1;
			}
			rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);
			if(ringBufferIndex < 0)
			{
				for(int var25 = 0; var25 < ringBuffer.length; ++var25)
				{
					ringBuffer[var25][0] = rotationYaw;
					ringBuffer[var25][1] = posY;
				}
			}
			if(++ringBufferIndex == ringBuffer.length)
			{
				ringBufferIndex = 0;
			}
			ringBuffer[ringBufferIndex][0] = rotationYaw;
			ringBuffer[ringBufferIndex][1] = posY;
			double var4;
			double var6;
			double var8;
			double var26;
			float var33;
			if(worldObj.isRemote)
			{
				if(newPosRotationIncrements > 0)
				{
					var26 = posX + (newPosX - posX) / newPosRotationIncrements;
					var4 = posY + (newPosY - posY) / newPosRotationIncrements;
					var6 = posZ + (field_110152_bk - posZ) / newPosRotationIncrements;
					var8 = MathHelper.wrapAngleTo180_double(newRotationYaw - rotationYaw);
					rotationYaw = (float) (rotationYaw + var8 / newPosRotationIncrements);
					rotationPitch = (float) (rotationPitch + (newRotationPitch - rotationPitch) / newPosRotationIncrements);
					--newPosRotationIncrements;
					setPosition(var26, var4, var6);
					setRotation(rotationYaw, rotationPitch);
				}
			} else
			{
				var26 = targetX - posX;
				var4 = targetY - posY;
				var6 = targetZ - posZ;
				var8 = var26 * var26 + var4 * var4 + var6 * var6;
				if(target != null)
				{
					targetX = target.posX;
					targetZ = target.posZ;
					double var10 = targetX - posX;
					double var12 = targetZ - posZ;
					double var14 = Math.sqrt(var10 * var10 + var12 * var12);
					double var16 = 0.4000000059604645D + var14 / 80.0D - 1.0D;
					if(var16 > 10.0D)
					{
						var16 = 10.0D;
					}
					targetY = target.boundingBox.minY + var16;
				} else
				{
					targetX += rand.nextGaussian() * 2.0D;
					targetZ += rand.nextGaussian() * 2.0D;
				}
				if(forceNewTarget || var8 < 100.0D || var8 > 22500.0D || isCollidedHorizontally || isCollidedVertically)
				{
					setNewTarget();
				}
				var4 /= MathHelper.sqrt_double(var26 * var26 + var6 * var6);
				var33 = 0.6F;
				if(var4 < -var33)
				{
					var4 = -var33;
				}
				if(var4 > var33)
				{
					var4 = var33;
				}
				motionY += var4 * 0.10000000149011612D;
				rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);
				double var11 = 180.0D - Math.atan2(var26, var6) * 180.0D / Math.PI;
				double var13 = MathHelper.wrapAngleTo180_double(var11 - rotationYaw);
				if(var13 > 50.0D)
				{
					var13 = 50.0D;
				}
				if(var13 < -50.0D)
				{
					var13 = -50.0D;
				}
				Vec3 var15 = worldObj.getWorldVec3Pool().getVecFromPool(targetX - posX, targetY - posY, targetZ - posZ).normalize();
				Vec3 var40 = worldObj.getWorldVec3Pool().getVecFromPool(MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F), motionY, -MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F)).normalize();
				float var17 = (float) (var40.dotProduct(var15) + 0.5D) / 1.5F;
				if(var17 < 0.0F)
				{
					var17 = 0.0F;
				}
				randomYawVelocity *= 0.8F;
				float var18 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 1.0F + 1.0F;
				double var19 = Math.sqrt(motionX * motionX + motionZ * motionZ) * 1.0D + 1.0D;
				if(var19 > 40.0D)
				{
					var19 = 40.0D;
				}
				randomYawVelocity = (float) (randomYawVelocity + var13 * (0.699999988079071D / var19 / var18));
				rotationYaw += randomYawVelocity * 0.1F;
				float var21 = (float) (2.0D / (var19 + 1.0D));
				float var22 = 0.06F;
				moveFlying(0.0F, -1.0F, var22 * (var17 * var21 + (1.0F - var21)));
				if(slowed)
				{
					moveEntity(motionX * 0.800000011920929D, motionY * 0.800000011920929D, motionZ * 0.800000011920929D);
				} else
				{
					moveEntity(motionX, motionY, motionZ);
				}
				Vec3 var23 = worldObj.getWorldVec3Pool().getVecFromPool(motionX, motionY, motionZ).normalize();
				float var24 = (float) (var23.dotProduct(var40) + 1.0D) / 2.0F;
				var24 = 0.8F + 0.15F * var24;
				motionX *= var24;
				motionZ *= var24;
				motionY *= 0.9100000262260437D;
			}
			renderYawOffset = rotationYaw;
			dragonPartHead.width = dragonPartHead.height = 3.0F;
			dragonPartTail1.width = dragonPartTail1.height = 2.0F;
			dragonPartTail2.width = dragonPartTail2.height = 2.0F;
			dragonPartTail3.width = dragonPartTail3.height = 2.0F;
			dragonPartBody.height = 3.0F;
			dragonPartBody.width = 5.0F;
			dragonPartWing1.height = 2.0F;
			dragonPartWing1.width = 4.0F;
			dragonPartWing2.height = 3.0F;
			dragonPartWing2.width = 4.0F;
			var2 = (float) (getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * (float) Math.PI;
			var3 = MathHelper.cos(var2);
			float var28 = -MathHelper.sin(var2);
			float var5 = rotationYaw * (float) Math.PI / 180.0F;
			float var27 = MathHelper.sin(var5);
			float var7 = MathHelper.cos(var5);
			dragonPartBody.onUpdate();
			dragonPartBody.setLocationAndAngles(posX + var27 * 0.5F, posY, posZ - var7 * 0.5F, 0.0F, 0.0F);
			dragonPartWing1.onUpdate();
			dragonPartWing1.setLocationAndAngles(posX + var7 * 4.5F, posY + 2.0D, posZ + var27 * 4.5F, 0.0F, 0.0F);
			dragonPartWing2.onUpdate();
			dragonPartWing2.setLocationAndAngles(posX - var7 * 4.5F, posY + 2.0D, posZ - var27 * 4.5F, 0.0F, 0.0F);
			if(!worldObj.isRemote && hurtTime == 0)
			{
				collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing1.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing2.boundingBox.expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
				attackEntitiesInList(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartHead.boundingBox.expand(1.0D, 1.0D, 1.0D)));
			}
			double[] var29 = getMovementOffsets(5, 1.0F);
			double[] var9 = getMovementOffsets(0, 1.0F);
			var33 = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F - randomYawVelocity * 0.01F);
			float var32 = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F - randomYawVelocity * 0.01F);
			dragonPartHead.onUpdate();
			dragonPartHead.setLocationAndAngles(posX + var33 * 5.5F * var3, posY + (var9[1] - var29[1]) * 1.0D + var28 * 5.5F, posZ - var32 * 5.5F * var3, 0.0F, 0.0F);
			for(int var30 = 0; var30 < 3; ++var30)
			{
				EntityDragonPart var31 = null;
				if(var30 == 0)
				{
					var31 = dragonPartTail1;
				}
				if(var30 == 1)
				{
					var31 = dragonPartTail2;
				}
				if(var30 == 2)
				{
					var31 = dragonPartTail3;
				}
				double[] var35 = getMovementOffsets(12 + var30 * 2, 1.0F);
				float var34 = rotationYaw * (float) Math.PI / 180.0F + simplifyAngle(var35[0] - var29[0]) * (float) Math.PI / 180.0F * 1.0F;
				float var38 = MathHelper.sin(var34);
				float var37 = MathHelper.cos(var34);
				float var36 = 1.5F;
				float var39 = (var30 + 1) * 2.0F;
				var31.onUpdate();
				var31.setLocationAndAngles(posX - (var27 * var36 + var38 * var39) * var3, posY + (var35[1] - var29[1]) * 1.0D - (var39 + var36) * var28 + 1.5D, posZ + (var7 * var36 + var37 * var39) * var3, 0.0F, 0.0F);
			}
			if(!worldObj.isRemote)
			{
				slowed = destroyBlocksInAABB(dragonPartHead.boundingBox) | destroyBlocksInAABB(dragonPartBody.boundingBox);
			}
		}
	}
	
	private void setNewTarget()
	{
		forceNewTarget = false;
		if(rand.nextInt(2) == 0 && !worldObj.playerEntities.isEmpty())
		{
			target = (Entity) worldObj.playerEntities.get(rand.nextInt(worldObj.playerEntities.size()));
		} else
		{
			boolean var1 = false;
			do
			{
				targetX = 0.0D;
				targetY = 70.0F + rand.nextFloat() * 50.0F;
				targetZ = 0.0D;
				targetX += rand.nextFloat() * 120.0F - 60.0F;
				targetZ += rand.nextFloat() * 120.0F - 60.0F;
				double var2 = posX - targetX;
				double var4 = posY - targetY;
				double var6 = posZ - targetZ;
				var1 = var2 * var2 + var4 * var4 + var6 * var6 > 100.0D;
			} while(!var1);
			target = null;
		}
	}
	
	private float simplifyAngle(double par1)
	{
		return (float) MathHelper.wrapAngleTo180_double(par1);
	}
	
	private void updateDragonEnderCrystal()
	{
		if(healingEnderCrystal != null)
		{
			if(healingEnderCrystal.isDead)
			{
				if(!worldObj.isRemote)
				{
					attackEntityFromPart(dragonPartHead, DamageSource.setExplosionSource((Explosion) null), 10.0F);
				}
				healingEnderCrystal = null;
			} else if(ticksExisted % 10 == 0 && func_110143_aJ() < func_110138_aP())
			{
				setEntityHealth(func_110143_aJ() + 1.0F);
			}
		}
		if(rand.nextInt(10) == 0)
		{
			float var1 = 32.0F;
			List var2 = worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, boundingBox.expand(var1, var1, var1));
			EntityEnderCrystal var3 = null;
			double var4 = Double.MAX_VALUE;
			Iterator var6 = var2.iterator();
			while(var6.hasNext())
			{
				EntityEnderCrystal var7 = (EntityEnderCrystal) var6.next();
				double var8 = var7.getDistanceSqToEntity(this);
				if(var8 < var4)
				{
					var4 = var8;
					var3 = var7;
				}
			}
			healingEnderCrystal = var3;
		}
	}
}
