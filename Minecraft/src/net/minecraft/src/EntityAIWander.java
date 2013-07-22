package net.minecraft.src;

public class EntityAIWander extends EntityAIBase
{
	private EntityCreature entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private float speed;
	
	public EntityAIWander(EntityCreature p_i3489_1_, float p_i3489_2_)
	{
		entity = p_i3489_1_;
		speed = p_i3489_2_;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !entity.getNavigator().noPath();
	}
	
	@Override public boolean shouldExecute()
	{
		if(entity.getAge() >= 100) return false;
		else if(entity.getRNG().nextInt(120) != 0) return false;
		else
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTarget(entity, 10, 7);
			if(var1 == null) return false;
			else
			{
				xPosition = var1.xCoord;
				yPosition = var1.yCoord;
				zPosition = var1.zCoord;
				return true;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		entity.getNavigator().tryMoveToXYZ(xPosition, yPosition, zPosition, speed);
	}
}
