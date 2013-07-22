package net.minecraft.src;

public class EntityAIBeg extends EntityAIBase
{
	private EntityWolf theWolf;
	private EntityPlayer thePlayer;
	private World worldObject;
	private float minPlayerDistance;
	private int field_75384_e;
	
	public EntityAIBeg(EntityWolf p_i3459_1_, float p_i3459_2_)
	{
		theWolf = p_i3459_1_;
		worldObject = p_i3459_1_.worldObj;
		minPlayerDistance = p_i3459_2_;
		setMutexBits(2);
	}
	
	@Override public boolean continueExecuting()
	{
		return !thePlayer.isEntityAlive() ? false : theWolf.getDistanceSqToEntity(thePlayer) > minPlayerDistance * minPlayerDistance ? false : field_75384_e > 0 && hasPlayerGotBoneInHand(thePlayer);
	}
	
	private boolean hasPlayerGotBoneInHand(EntityPlayer p_75382_1_)
	{
		ItemStack var2 = p_75382_1_.inventory.getCurrentItem();
		return var2 == null ? false : !theWolf.isTamed() && var2.itemID == Item.bone.itemID ? true : theWolf.isBreedingItem(var2);
	}
	
	@Override public void resetTask()
	{
		theWolf.func_70918_i(false);
		thePlayer = null;
	}
	
	@Override public boolean shouldExecute()
	{
		thePlayer = worldObject.getClosestPlayerToEntity(theWolf, minPlayerDistance);
		return thePlayer == null ? false : hasPlayerGotBoneInHand(thePlayer);
	}
	
	@Override public void startExecuting()
	{
		theWolf.func_70918_i(true);
		field_75384_e = 40 + theWolf.getRNG().nextInt(40);
	}
	
	@Override public void updateTask()
	{
		theWolf.getLookHelper().setLookPosition(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ, 10.0F, theWolf.getVerticalFaceSpeed());
		--field_75384_e;
	}
}
