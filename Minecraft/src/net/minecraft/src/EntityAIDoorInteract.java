package net.minecraft.src;

public abstract class EntityAIDoorInteract extends EntityAIBase
{
	protected EntityLiving theEntity;
	protected int entityPosX;
	protected int entityPosY;
	protected int entityPosZ;
	protected BlockDoor targetDoor;
	boolean hasStoppedDoorInteraction;
	float entityPositionX;
	float entityPositionZ;
	
	public EntityAIDoorInteract(EntityLiving par1EntityLiving)
	{
		theEntity = par1EntityLiving;
	}
	
	@Override public boolean continueExecuting()
	{
		return !hasStoppedDoorInteraction;
	}
	
	private BlockDoor findUsableDoor(int par1, int par2, int par3)
	{
		int var4 = theEntity.worldObj.getBlockId(par1, par2, par3);
		return var4 != Block.doorWood.blockID ? null : (BlockDoor) Block.blocksList[var4];
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theEntity.isCollidedHorizontally) return false;
		else
		{
			PathNavigate var1 = theEntity.getNavigator();
			PathEntity var2 = var1.getPath();
			if(var2 != null && !var2.isFinished() && var1.getCanBreakDoors())
			{
				for(int var3 = 0; var3 < Math.min(var2.getCurrentPathIndex() + 2, var2.getCurrentPathLength()); ++var3)
				{
					PathPoint var4 = var2.getPathPointFromIndex(var3);
					entityPosX = var4.xCoord;
					entityPosY = var4.yCoord + 1;
					entityPosZ = var4.zCoord;
					if(theEntity.getDistanceSq(entityPosX, theEntity.posY, entityPosZ) <= 2.25D)
					{
						targetDoor = findUsableDoor(entityPosX, entityPosY, entityPosZ);
						if(targetDoor != null) return true;
					}
				}
				entityPosX = MathHelper.floor_double(theEntity.posX);
				entityPosY = MathHelper.floor_double(theEntity.posY + 1.0D);
				entityPosZ = MathHelper.floor_double(theEntity.posZ);
				targetDoor = findUsableDoor(entityPosX, entityPosY, entityPosZ);
				return targetDoor != null;
			} else return false;
		}
	}
	
	@Override public void startExecuting()
	{
		hasStoppedDoorInteraction = false;
		entityPositionX = (float) (entityPosX + 0.5F - theEntity.posX);
		entityPositionZ = (float) (entityPosZ + 0.5F - theEntity.posZ);
	}
	
	@Override public void updateTask()
	{
		float var1 = (float) (entityPosX + 0.5F - theEntity.posX);
		float var2 = (float) (entityPosZ + 0.5F - theEntity.posZ);
		float var3 = entityPositionX * var1 + entityPositionZ * var2;
		if(var3 < 0.0F)
		{
			hasStoppedDoorInteraction = true;
		}
	}
}
