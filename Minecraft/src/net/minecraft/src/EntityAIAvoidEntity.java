package net.minecraft.src;

import java.util.List;

public class EntityAIAvoidEntity extends EntityAIBase
{
	public final IEntitySelector field_98218_a = new EntityAIAvoidEntitySelector(this);
	private EntityCreature theEntity;
	private float farSpeed;
	private float nearSpeed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;
	private PathEntity entityPathEntity;
	private PathNavigate entityPathNavigate;
	private Class targetEntityClass;
	
	public EntityAIAvoidEntity(EntityCreature p_i3458_1_, Class p_i3458_2_, float p_i3458_3_, float p_i3458_4_, float p_i3458_5_)
	{
		theEntity = p_i3458_1_;
		targetEntityClass = p_i3458_2_;
		distanceFromEntity = p_i3458_3_;
		farSpeed = p_i3458_4_;
		nearSpeed = p_i3458_5_;
		entityPathNavigate = p_i3458_1_.getNavigator();
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !entityPathNavigate.noPath();
	}
	
	@Override public void resetTask()
	{
		closestLivingEntity = null;
	}
	
	@Override public boolean shouldExecute()
	{
		if(targetEntityClass == EntityPlayer.class)
		{
			if(theEntity instanceof EntityTameable && ((EntityTameable) theEntity).isTamed()) return false;
			closestLivingEntity = theEntity.worldObj.getClosestPlayerToEntity(theEntity, distanceFromEntity);
			if(closestLivingEntity == null) return false;
		} else
		{
			List var1 = theEntity.worldObj.selectEntitiesWithinAABB(targetEntityClass, theEntity.boundingBox.expand(distanceFromEntity, 3.0D, distanceFromEntity), field_98218_a);
			if(var1.isEmpty()) return false;
			closestLivingEntity = (Entity) var1.get(0);
		}
		Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(theEntity, 16, 7, theEntity.worldObj.getWorldVec3Pool().getVecFromPool(closestLivingEntity.posX, closestLivingEntity.posY, closestLivingEntity.posZ));
		if(var2 == null) return false;
		else if(closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < closestLivingEntity.getDistanceSqToEntity(theEntity)) return false;
		else
		{
			entityPathEntity = entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
			return entityPathEntity == null ? false : entityPathEntity.isDestinationSame(var2);
		}
	}
	
	@Override public void startExecuting()
	{
		entityPathNavigate.setPath(entityPathEntity, farSpeed);
	}
	
	@Override public void updateTask()
	{
		if(theEntity.getDistanceSqToEntity(closestLivingEntity) < 49.0D)
		{
			theEntity.getNavigator().setSpeed(nearSpeed);
		} else
		{
			theEntity.getNavigator().setSpeed(farSpeed);
		}
	}
	
	static EntityCreature func_98217_a(EntityAIAvoidEntity p_98217_0_)
	{
		return p_98217_0_.theEntity;
	}
}
