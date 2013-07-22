package net.minecraft.src;

public class EntityAIRestrictOpenDoor extends EntityAIBase
{
	private EntityCreature entityObj;
	private VillageDoorInfo frontDoor;
	
	public EntityAIRestrictOpenDoor(EntityCreature p_i3490_1_)
	{
		entityObj = p_i3490_1_;
	}
	
	@Override public boolean continueExecuting()
	{
		return entityObj.worldObj.isDaytime() ? false : !frontDoor.isDetachedFromVillageFlag && frontDoor.isInside(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posZ));
	}
	
	@Override public void resetTask()
	{
		entityObj.getNavigator().setBreakDoors(true);
		entityObj.getNavigator().setEnterDoors(true);
		frontDoor = null;
	}
	
	@Override public boolean shouldExecute()
	{
		if(entityObj.worldObj.isDaytime()) return false;
		else
		{
			Village var1 = entityObj.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ), 16);
			if(var1 == null) return false;
			else
			{
				frontDoor = var1.findNearestDoor(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ));
				return frontDoor == null ? false : frontDoor.getInsideDistanceSquare(MathHelper.floor_double(entityObj.posX), MathHelper.floor_double(entityObj.posY), MathHelper.floor_double(entityObj.posZ)) < 2.25D;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		entityObj.getNavigator().setBreakDoors(false);
		entityObj.getNavigator().setEnterDoors(false);
	}
	
	@Override public void updateTask()
	{
		frontDoor.incrementDoorOpeningRestrictionCounter();
	}
}
