package net.minecraft.src;

public class EntityAITradePlayer extends EntityAIBase
{
	private EntityVillager villager;
	
	public EntityAITradePlayer(EntityVillager par1EntityVillager)
	{
		villager = par1EntityVillager;
		setMutexBits(5);
	}
	
	@Override public void resetTask()
	{
		villager.setCustomer((EntityPlayer) null);
	}
	
	@Override public boolean shouldExecute()
	{
		if(!villager.isEntityAlive()) return false;
		else if(villager.isInWater()) return false;
		else if(!villager.onGround) return false;
		else if(villager.velocityChanged) return false;
		else
		{
			EntityPlayer var1 = villager.getCustomer();
			return var1 == null ? false : villager.getDistanceSqToEntity(var1) > 16.0D ? false : var1.openContainer instanceof Container;
		}
	}
	
	@Override public void startExecuting()
	{
		villager.getNavigator().clearPathEntity();
	}
}
