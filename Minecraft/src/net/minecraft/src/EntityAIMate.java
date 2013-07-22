package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EntityAIMate extends EntityAIBase
{
	private EntityAnimal theAnimal;
	World theWorld;
	private EntityAnimal targetMate;
	int spawnBabyDelay = 0;
	float moveSpeed;
	
	public EntityAIMate(EntityAnimal par1EntityAnimal, float par2)
	{
		theAnimal = par1EntityAnimal;
		theWorld = par1EntityAnimal.worldObj;
		moveSpeed = par2;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		return targetMate.isEntityAlive() && targetMate.isInLove() && spawnBabyDelay < 60;
	}
	
	private EntityAnimal getNearbyMate()
	{
		float var1 = 8.0F;
		List var2 = theWorld.getEntitiesWithinAABB(theAnimal.getClass(), theAnimal.boundingBox.expand(var1, var1, var1));
		double var3 = Double.MAX_VALUE;
		EntityAnimal var5 = null;
		Iterator var6 = var2.iterator();
		while(var6.hasNext())
		{
			EntityAnimal var7 = (EntityAnimal) var6.next();
			if(theAnimal.canMateWith(var7) && theAnimal.getDistanceSqToEntity(var7) < var3)
			{
				var5 = var7;
				var3 = theAnimal.getDistanceSqToEntity(var7);
			}
		}
		return var5;
	}
	
	@Override public void resetTask()
	{
		targetMate = null;
		spawnBabyDelay = 0;
	}
	
	@Override public boolean shouldExecute()
	{
		if(!theAnimal.isInLove()) return false;
		else
		{
			targetMate = getNearbyMate();
			return targetMate != null;
		}
	}
	
	private void spawnBaby()
	{
		EntityAgeable var1 = theAnimal.createChild(targetMate);
		if(var1 != null)
		{
			theAnimal.setGrowingAge(6000);
			targetMate.setGrowingAge(6000);
			theAnimal.resetInLove();
			targetMate.resetInLove();
			var1.setGrowingAge(-24000);
			var1.setLocationAndAngles(theAnimal.posX, theAnimal.posY, theAnimal.posZ, 0.0F, 0.0F);
			theWorld.spawnEntityInWorld(var1);
			Random var2 = theAnimal.getRNG();
			for(int var3 = 0; var3 < 7; ++var3)
			{
				double var4 = var2.nextGaussian() * 0.02D;
				double var6 = var2.nextGaussian() * 0.02D;
				double var8 = var2.nextGaussian() * 0.02D;
				theWorld.spawnParticle("heart", theAnimal.posX + var2.nextFloat() * theAnimal.width * 2.0F - theAnimal.width, theAnimal.posY + 0.5D + var2.nextFloat() * theAnimal.height, theAnimal.posZ + var2.nextFloat() * theAnimal.width * 2.0F - theAnimal.width, var4, var6, var8);
			}
			theWorld.spawnEntityInWorld(new EntityXPOrb(theWorld, theAnimal.posX, theAnimal.posY, theAnimal.posZ, var2.nextInt(7) + 1));
		}
	}
	
	@Override public void updateTask()
	{
		theAnimal.getLookHelper().setLookPositionWithEntity(targetMate, 10.0F, theAnimal.getVerticalFaceSpeed());
		theAnimal.getNavigator().tryMoveToEntityLiving(targetMate, moveSpeed);
		++spawnBabyDelay;
		if(spawnBabyDelay >= 60 && theAnimal.getDistanceSqToEntity(targetMate) < 9.0D)
		{
			spawnBaby();
		}
	}
}
