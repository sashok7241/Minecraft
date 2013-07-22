package net.minecraft.src;

public class EntityAILookAtTradePlayer extends EntityAIWatchClosest
{
	private final EntityVillager theMerchant;
	
	public EntityAILookAtTradePlayer(EntityVillager p_i3474_1_)
	{
		super(p_i3474_1_, EntityPlayer.class, 8.0F);
		theMerchant = p_i3474_1_;
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
