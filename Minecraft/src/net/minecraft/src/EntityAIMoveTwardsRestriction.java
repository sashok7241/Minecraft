package net.minecraft.src;

public class EntityAIMoveTwardsRestriction extends EntityAIBase
{
	private EntityCreature theEntity;
	private double movePosX;
	private double movePosY;
	private double movePosZ;
	private float movementSpeed;
	
	public EntityAIMoveTwardsRestriction(EntityCreature par1EntityCreature, float par2)
	{
		theEntity = par1EntityCreature;
		movementSpeed = par2;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !theEntity.getNavigator().noPath();
	}
	
	@Override public boolean shouldExecute()
	{
		if(theEntity.isWithinHomeDistanceCurrentPosition()) return false;
		else
		{
			ChunkCoordinates var1 = theEntity.getHomePosition();
			Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockTowards(theEntity, 16, 7, theEntity.worldObj.getWorldVec3Pool().getVecFromPool(var1.posX, var1.posY, var1.posZ));
			if(var2 == null) return false;
			else
			{
				movePosX = var2.xCoord;
				movePosY = var2.yCoord;
				movePosZ = var2.zCoord;
				return true;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		theEntity.getNavigator().tryMoveToXYZ(movePosX, movePosY, movePosZ, movementSpeed);
	}
}
