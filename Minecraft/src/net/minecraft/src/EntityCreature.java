package net.minecraft.src;

public abstract class EntityCreature extends EntityLiving
{
	private PathEntity pathToEntity;
	protected Entity entityToAttack;
	protected boolean hasAttacked = false;
	protected int fleeingTick = 0;
	
	public EntityCreature(World par1World)
	{
		super(par1World);
	}
	
	protected void attackEntity(Entity par1Entity, float par2)
	{
	}
	
	protected Entity findPlayerToAttack()
	{
		return null;
	}
	
	public float getBlockPathWeight(int par1, int par2, int par3)
	{
		return 0.0F;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(boundingBox.minY);
		int var3 = MathHelper.floor_double(posZ);
		return super.getCanSpawnHere() && getBlockPathWeight(var1, var2, var3) >= 0.0F;
	}
	
	public Entity getEntityToAttack()
	{
		return entityToAttack;
	}
	
	@Override public float getSpeedModifier()
	{
		float var1 = super.getSpeedModifier();
		if(fleeingTick > 0 && !isAIEnabled())
		{
			var1 *= 2.0F;
		}
		return var1;
	}
	
	public boolean hasPath()
	{
		return pathToEntity != null;
	}
	
	protected boolean isMovementCeased()
	{
		return false;
	}
	
	public void setPathToEntity(PathEntity par1PathEntity)
	{
		pathToEntity = par1PathEntity;
	}
	
	public void setTarget(Entity par1Entity)
	{
		entityToAttack = par1Entity;
	}
	
	@Override protected void updateEntityActionState()
	{
		worldObj.theProfiler.startSection("ai");
		if(fleeingTick > 0)
		{
			--fleeingTick;
		}
		hasAttacked = isMovementCeased();
		float var1 = 16.0F;
		if(entityToAttack == null)
		{
			entityToAttack = findPlayerToAttack();
			if(entityToAttack != null)
			{
				pathToEntity = worldObj.getPathEntityToEntity(this, entityToAttack, var1, true, false, false, true);
			}
		} else if(entityToAttack.isEntityAlive())
		{
			float var2 = entityToAttack.getDistanceToEntity(this);
			if(canEntityBeSeen(entityToAttack))
			{
				attackEntity(entityToAttack, var2);
			}
		} else
		{
			entityToAttack = null;
		}
		worldObj.theProfiler.endSection();
		if(!hasAttacked && entityToAttack != null && (pathToEntity == null || rand.nextInt(20) == 0))
		{
			pathToEntity = worldObj.getPathEntityToEntity(this, entityToAttack, var1, true, false, false, true);
		} else if(!hasAttacked && (pathToEntity == null && rand.nextInt(180) == 0 || rand.nextInt(120) == 0 || fleeingTick > 0) && entityAge < 100)
		{
			updateWanderPath();
		}
		int var21 = MathHelper.floor_double(boundingBox.minY + 0.5D);
		boolean var3 = isInWater();
		boolean var4 = handleLavaMovement();
		rotationPitch = 0.0F;
		if(pathToEntity != null && rand.nextInt(100) != 0)
		{
			worldObj.theProfiler.startSection("followpath");
			Vec3 var5 = pathToEntity.getPosition(this);
			double var6 = width * 2.0F;
			while(var5 != null && var5.squareDistanceTo(posX, var5.yCoord, posZ) < var6 * var6)
			{
				pathToEntity.incrementPathIndex();
				if(pathToEntity.isFinished())
				{
					var5 = null;
					pathToEntity = null;
				} else
				{
					var5 = pathToEntity.getPosition(this);
				}
			}
			isJumping = false;
			if(var5 != null)
			{
				double var8 = var5.xCoord - posX;
				double var10 = var5.zCoord - posZ;
				double var12 = var5.yCoord - var21;
				float var14 = (float) (Math.atan2(var10, var8) * 180.0D / Math.PI) - 90.0F;
				float var15 = MathHelper.wrapAngleTo180_float(var14 - rotationYaw);
				moveForward = moveSpeed;
				if(var15 > 30.0F)
				{
					var15 = 30.0F;
				}
				if(var15 < -30.0F)
				{
					var15 = -30.0F;
				}
				rotationYaw += var15;
				if(hasAttacked && entityToAttack != null)
				{
					double var16 = entityToAttack.posX - posX;
					double var18 = entityToAttack.posZ - posZ;
					float var20 = rotationYaw;
					rotationYaw = (float) (Math.atan2(var18, var16) * 180.0D / Math.PI) - 90.0F;
					var15 = (var20 - rotationYaw + 90.0F) * (float) Math.PI / 180.0F;
					moveStrafing = -MathHelper.sin(var15) * moveForward * 1.0F;
					moveForward = MathHelper.cos(var15) * moveForward * 1.0F;
				}
				if(var12 > 0.0D)
				{
					isJumping = true;
				}
			}
			if(entityToAttack != null)
			{
				faceEntity(entityToAttack, 30.0F, 30.0F);
			}
			if(isCollidedHorizontally && !hasPath())
			{
				isJumping = true;
			}
			if(rand.nextFloat() < 0.8F && (var3 || var4))
			{
				isJumping = true;
			}
			worldObj.theProfiler.endSection();
		} else
		{
			super.updateEntityActionState();
			pathToEntity = null;
		}
	}
	
	protected void updateWanderPath()
	{
		worldObj.theProfiler.startSection("stroll");
		boolean var1 = false;
		int var2 = -1;
		int var3 = -1;
		int var4 = -1;
		float var5 = -99999.0F;
		for(int var6 = 0; var6 < 10; ++var6)
		{
			int var7 = MathHelper.floor_double(posX + rand.nextInt(13) - 6.0D);
			int var8 = MathHelper.floor_double(posY + rand.nextInt(7) - 3.0D);
			int var9 = MathHelper.floor_double(posZ + rand.nextInt(13) - 6.0D);
			float var10 = getBlockPathWeight(var7, var8, var9);
			if(var10 > var5)
			{
				var5 = var10;
				var2 = var7;
				var3 = var8;
				var4 = var9;
				var1 = true;
			}
		}
		if(var1)
		{
			pathToEntity = worldObj.getEntityPathToXYZ(this, var2, var3, var4, 10.0F, true, false, false, true);
		}
		worldObj.theProfiler.endSection();
	}
}
