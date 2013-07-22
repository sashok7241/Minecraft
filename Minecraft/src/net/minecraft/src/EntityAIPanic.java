package net.minecraft.src;

public class EntityAIPanic extends EntityAIBase
{
	private EntityCreature theEntityCreature;
	private double speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;
	
	public EntityAIPanic(EntityCreature par1EntityCreature, double par2)
	{
		theEntityCreature = par1EntityCreature;
		speed = par2;
		setMutexBits(1);
	}
	
	@Override public boolean continueExecuting()
	{
		return !theEntityCreature.getNavigator().noPath();
	}
	
	@Override public boolean shouldExecute()
	{
		if(theEntityCreature.getAITarget() == null && !theEntityCreature.isBurning()) return false;
		else
		{
			Vec3 var1 = RandomPositionGenerator.findRandomTarget(theEntityCreature, 5, 4);
			if(var1 == null) return false;
			else
			{
				randPosX = var1.xCoord;
				randPosY = var1.yCoord;
				randPosZ = var1.zCoord;
				return true;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		theEntityCreature.getNavigator().tryMoveToXYZ(randPosX, randPosY, randPosZ, speed);
	}
}
