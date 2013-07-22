package net.minecraft.src;

public class EntityAIEatGrass extends EntityAIBase
{
	private EntityLiving theEntity;
	private World theWorld;
	int eatGrassTick;
	
	public EntityAIEatGrass(EntityLiving par1EntityLiving)
	{
		theEntity = par1EntityLiving;
		theWorld = par1EntityLiving.worldObj;
		setMutexBits(7);
	}
	
	@Override public boolean continueExecuting()
	{
		return eatGrassTick > 0;
	}
	
	public int getEatGrassTick()
	{
		return eatGrassTick;
	}
	
	@Override public void resetTask()
	{
		eatGrassTick = 0;
	}
	
	@Override public boolean shouldExecute()
	{
		if(theEntity.getRNG().nextInt(theEntity.isChild() ? 50 : 1000) != 0) return false;
		else
		{
			int var1 = MathHelper.floor_double(theEntity.posX);
			int var2 = MathHelper.floor_double(theEntity.posY);
			int var3 = MathHelper.floor_double(theEntity.posZ);
			return theWorld.getBlockId(var1, var2, var3) == Block.tallGrass.blockID && theWorld.getBlockMetadata(var1, var2, var3) == 1 ? true : theWorld.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID;
		}
	}
	
	@Override public void startExecuting()
	{
		eatGrassTick = 40;
		theWorld.setEntityState(theEntity, (byte) 10);
		theEntity.getNavigator().clearPathEntity();
	}
	
	@Override public void updateTask()
	{
		eatGrassTick = Math.max(0, eatGrassTick - 1);
		if(eatGrassTick == 4)
		{
			int var1 = MathHelper.floor_double(theEntity.posX);
			int var2 = MathHelper.floor_double(theEntity.posY);
			int var3 = MathHelper.floor_double(theEntity.posZ);
			if(theWorld.getBlockId(var1, var2, var3) == Block.tallGrass.blockID)
			{
				theWorld.destroyBlock(var1, var2, var3, false);
				theEntity.eatGrassBonus();
			} else if(theWorld.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID)
			{
				theWorld.playAuxSFX(2001, var1, var2 - 1, var3, Block.grass.blockID);
				theWorld.setBlock(var1, var2 - 1, var3, Block.dirt.blockID, 0, 2);
				theEntity.eatGrassBonus();
			}
		}
	}
}
