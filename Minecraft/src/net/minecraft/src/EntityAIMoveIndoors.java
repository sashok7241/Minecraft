package net.minecraft.src;

public class EntityAIMoveIndoors extends EntityAIBase
{
	private EntityCreature entityObj;
	private VillageDoorInfo doorInfo;
	private int insidePosX = -1;
	private int insidePosZ = -1;
	
	public EntityAIMoveIndoors(EntityCreature p_i3478_1_)
	{
		entityObj = p_i3478_1_;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !entityObj.getNavigator().noPath();
	}
	
	@Override public void resetTask()
	{
		insidePosX = doorInfo.getInsidePosX();
		insidePosZ = doorInfo.getInsidePosZ();
		doorInfo = null;
	}
	
	@Override public boolean shouldExecute()
	{
		if((!entityObj.worldObj.isDaytime() || entityObj.worldObj.isRaining()) && !entityObj.worldObj.provider.hasNoSky)
		{
			if(entityObj.getRNG().nextInt(50) != 0) return false;
			else if(insidePosX != -1 && entityObj.getDistanceSq(insidePosX, entityObj.posY, insidePosZ) < 4.0D) return false;
			else
			{
				Village var1 = entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ), 14);
				if(var1 == null) return false;
				else
				{
					doorInfo = var1.findNearestDoorUnrestricted(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ));
					return doorInfo != null;
				}
			}
		} else return false;
	}
	
	@Override public void startExecuting()
	{
		insidePosX = -1;
		if(entityObj.getDistanceSq(doorInfo.getInsidePosX(), doorInfo.posY, doorInfo.getInsidePosZ()) > 256.0D)
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(entityObj, 14, 3, entityObj.worldObj.getWorldVec3Pool().getVecFromPool(doorInfo.getInsidePosX() + 0.5D, doorInfo.getInsidePosY(), doorInfo.getInsidePosZ() + 0.5D));
			if(var1 != null)
			{
				entityObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, 0.3F);
			}
		} else
		{
			entityObj.getNavigator().tryMoveToXYZ(doorInfo.getInsidePosX() + 0.5D, doorInfo.getInsidePosY(), doorInfo.getInsidePosZ() + 0.5D, 0.3F);
		}
	}
}
