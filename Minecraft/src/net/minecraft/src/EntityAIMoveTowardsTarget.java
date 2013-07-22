package net.minecraft.src;

public class EntityAIMoveTowardsTarget extends EntityAIBase
{
	private EntityCreature theEntity;
	private EntityLiving targetEntity;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private float field_75425_f;
	private float field_75426_g;
	
	public EntityAIMoveTowardsTarget(EntityCreature p_i3481_1_, float p_i3481_2_, float p_i3481_3_)
	{
		theEntity = p_i3481_1_;
		field_75425_f = p_i3481_2_;
		field_75426_g = p_i3481_3_;
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
