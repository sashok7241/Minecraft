package net.minecraft.src;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
	private final EntityVillager theMerchant;
	
	public EntityAILookAtTradePlayer(EntityVillager par1EntityVillager)
	{
		super(par1EntityVillager, EntityPlayer.class, 8.0F);
		theMerchant = par1EntityVillager;
	}
	
	@Override public boolean shouldExecute()
	{
		if(theMerchant.isTrading())
		{
			closestEntity = theMerchant.getCustomer();
			return true;
		} else return false;
	}
}
