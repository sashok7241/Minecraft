package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowParent extends EntityAIBase
{
	EntityAnimal childAnimal;
	EntityAnimal parentAnimal;
	double field_75347_c;
	private int field_75345_d;
	
	public EntityAIFollowParent(EntityAnimal par1EntityAnimal, double par2)
	{
		childAnimal = par1EntityAnimal;
		field_75347_c = par2;
	}
	
	@Override public boolean continueExecuting()
	{
		if(!parentAnimal.isEntityAlive()) return false;
		else
		{
			double var1 = childAnimal.getDistanceSqToEntity(parentAnimal);
			return var1 >= 9.0D && var1 <= 256.0D;
		}
	}
	
	@Override public void resetTask()
	{
		parentAnimal = null;
	}
	
	@Override public boolean shouldExecute()
	{
		if(childAnimal.getGrowingAge() >= 0) return false;
		else
		{
			List var1 = childAnimal.worldObj.getEntitiesWithinAABB(childAnimal.getClass(), childAnimal.boundingBox.expand(8.0D, 4.0D, 8.0D));
			EntityAnimal var2 = null;
			double var3 = Double.MAX_VALUE;
			Iterator var5 = var1.iterator();
			while(var5.hasNext())
			{
				EntityAnimal var6 = (EntityAnimal) var5.next();
				if(var6.getGrowingAge() >= 0)
				{
					double var7 = childAnimal.getDistanceSqToEntity(var6);
					if(var7 <= var3)
					{
						var3 = var7;
						var2 = var6;
					}
				}
			}
			if(var2 == null) return false;
			else if(var3 < 9.0D) return false;
			else
			{
				parentAnimal = var2;
				return true;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		field_75345_d = 0;
	}
	
	@Override public void updateTask()
	{
		if(--field_75345_d <= 0)
		{
			field_75345_d = 10;
			childAnimal.getNavigator().tryMoveToEntityLiving(parentAnimal, field_75347_c);
		}
	}
}
