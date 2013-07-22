package net.minecraft.src;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
	private EntityCreature theEntity;
	private EntityLivingBase targetEntity;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private double field_75425_f;
	private float field_75426_g;
	
	public EntityAIMoveTowardsTarget(EntityCreature par1EntityCreature, double par2, float par4)
	{
		theEntity = par1EntityCreature;
		field_75425_f = par2;
		field_75426_g = par4;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !theEntity.getNavigator().noPath() && targetEntity.isEntityAlive() && targetEntity.getDistanceSqToEntity(theEntity) < field_75426_g * field_75426_g;
	}
	
	@Override public void resetTask()
	{
		targetEntity = null;
	}
	
	@Override public boolean shouldExecute()
	{
		targetEntity = theEntity.getAttackTarget();
		if(targetEntity == null) return false;
		else if(targetEntity.getDistanceSqToEntity(theEntity) > field_75426_g * field_75426_g) return false;
		else
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 16, 7, theEntity.worldObj.getWorldVec3Pool().getVecFromPool(targetEntity.posX, targetEntity.posY, targetEntity.posZ));
			if(var1 == null) return false;
			else
			{
				movePosX = var1.xCoord;
				movePosY = var1.yCoord;
				movePosZ = var1.zCoord;
				return true;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, field_75425_f);
	}
}
