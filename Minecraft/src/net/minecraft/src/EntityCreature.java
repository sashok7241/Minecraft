package net.minecraft.src;

import java.util.UUID;

public abstract class EntityCreature extends EntityLiving
{
	public static final UUID field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
	public static final AttributeModifier field_110181_i = new AttributeModifier(field_110179_h, "Fleeing speed bonus", 2.0D, 2).func_111168_a(false);
	private PathEntity pathToEntity;
	protected Entity entityToAttack;
	protected boolean hasAttacked;
	protected int fleeingTick;
	private ChunkCoordinates homePosition = new ChunkCoordinates(0, 0, 0);
	private float maximumHomeDistance = -1.0F;
	private EntityAIBase field_110178_bs = new EntityAIMoveTowardsRestriction(this, 1.0D);
	private boolean field_110180_bt;
	
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
	
	@Override protected void func_110159_bB()
	{
		super.func_110159_bB();
		if(func_110167_bD() && func_110166_bE() != null && func_110166_bE().worldObj == worldObj)
		{
			Entity var1 = func_110166_bE();
			func_110171_b((int) var1.posX, (int) var1.posY, (int) var1.posZ, 5);
			float var2 = getDistanceToEntity(var1);
			if(this instanceof EntityTameable && ((EntityTameable) this).isSitting())
			{
				if(var2 > 10.0F)
				{
					func_110160_i(true, true);
				}
				return;
			}
			if(!field_110180_bt)
			{
				tasks.addTask(2, field_110178_bs);
				getNavigator().setAvoidsWater(false);
				field_110180_bt = true;
			}
			func_142017_o(var2);
			if(var2 > 4.0F)
			{
				getNavigator().tryMoveToEntityLiving(var1, 1.0D);
			}
			if(var2 > 6.0F)
			{
				double var3 = (var1.posX - posX) / var2;
				double var5 = (var1.posY - posY) / var2;
				double var7 = (var1.posZ - posZ) / var2;
				motionX += var3 * Math.abs(var3) * 0.4D;
				motionY += var5 * Math.abs(var5) * 0.4D;
				motionZ += var7 * Math.abs(var7) * 0.4D;
			}
			if(var2 > 10.0F)
			{
				func_110160_i(true, true);
			}
		} else if(!func_110167_bD() && field_110180_bt)
		{
			field_110180_bt = false;
			tasks.removeTask(field_110178_bs);
			getNavigator().setAvoidsWater(true);
			func_110177_bN();
		}
	}
	
	public void func_110171_b(int par1, int par2, int par3, int par4)
	{
		homePosition.set(par1, par2, par3);
		maximumHomeDistance = par4;
	}
	
	public ChunkCoordinates func_110172_bL()
	{
		return homePosition;
	}
	
	public boolean func_110173_bK()
	{
		return func_110176_b(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
	}
	
	public float func_110174_bM()
	{
		return maximumHomeDistance;
	}
	
	public boolean func_110175_bO()
	{
		return maximumHomeDistance != -1.0F;
	}
	
	public boolean func_110176_b(int par1, int par2, int par3)
	{
		return maximumHomeDistance == -1.0F ? true : homePosition.getDistanceSquared(par1, par2, par3) < maximumHomeDistance * maximumHomeDistance;
	}
	
	public void func_110177_bN()
	{
		maximumHomeDistance = -1.0F;
	}
	
	protected void func_142017_o(float par1)
	{
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
		if(fleeingTick > 0 && --fleeingTick == 0)
		{
			AttributeInstance var1 = func_110148_a(SharedMonsterAttributes.field_111263_d);
			var1.func_111124_b(field_110181_i);
		}
		hasAttacked = isMovementCeased();
		float var21 = 16.0F;
		if(entityToAttack == null)
		{
			entityToAttack = findPlayerToAttack();
			if(entityToAttack != null)
			{
				pathToEntity = worldObj.getPathEntityToEntity(this, entityToAttack, var21, true, false, false, true);
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
			pathToEntity = worldObj.getPathEntityToEntity(this, entityToAttack, var21, true, false, false, true);
		} else if(!hasAttacked && (pathToEntity == null && rand.nextInt(180) == 0 || rand.nextInt(120) == 0 || fleeingTick > 0) && entityAge < 100)
		{
			updateWanderPath();
		}
		int var22 = MathHelper.floor_double(boundingBox.minY + 0.5D);
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
				double var12 = var5.yCoord - var22;
				float var14 = (float) (Math.atan2(var10, var8) * 180.0D / Math.PI) - 90.0F;
				float var15 = MathHelper.wrapAngleTo180_float(var14 - rotationYaw);
				moveForward = (float) func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e();
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
