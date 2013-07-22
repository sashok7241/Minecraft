package net.minecraft.src;

public class EntityAITempt extends EntityAIBase
{
	private EntityCreature temptedEntity;
	private float field_75282_b;
	private double field_75283_c;
	private double field_75280_d;
	private double field_75281_e;
	private double field_75278_f;
	private double field_75279_g;
	private EntityPlayer temptingPlayer;
	private int delayTemptCounter = 0;
	private boolean field_75287_j;
	private int breedingFood;
	private boolean scaredByPlayerMovement;
	private boolean field_75286_m;
	
	public EntityAITempt(EntityCreature par1EntityCreature, float par2, int par3, boolean par4)
	{
		temptedEntity = par1EntityCreature;
		field_75282_b = par2;
		breedingFood = par3;
		scaredByPlayerMovement = par4;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		if(scaredByPlayerMovement)
		{
			if(temptedEntity.getDistanceSqToEntity(temptingPlayer) < 36.0D)
			{
				if(temptingPlayer.getDistanceSq(field_75283_c, field_75280_d, field_75281_e) > 0.010000000000000002D) return false;
				if(Math.abs(temptingPlayer.rotationPitch - field_75278_f) > 5.0D || Math.abs(temptingPlayer.rotationYaw - field_75279_g) > 5.0D) return false;
			} else
			{
				field_75283_c = temptingPlayer.posX;
				field_75280_d = temptingPlayer.posY;
				field_75281_e = temptingPlayer.posZ;
			}
			field_75278_f = temptingPlayer.rotationPitch;
			field_75279_g = temptingPlayer.rotationYaw;
		}
		return shouldExecute();
	}
	
	public boolean func_75277_f()
	{
		return field_75287_j;
	}
	
	@Override public void resetTask()
	{
		temptingPlayer = null;
		temptedEntity.getNavigator().clearPathEntity();
		delayTemptCounter = 100;
		field_75287_j = false;
		temptedEntity.getNavigator().setAvoidsWater(field_75286_m);
	}
	
	@Override public boolean shouldExecute()
	{
		if(delayTemptCounter > 0)
		{
			--delayTemptCounter;
			return false;
		} else
		{
			temptingPlayer = temptedEntity.worldObj.getClosestPlayerToEntity(temptedEntity, 10.0D);
			if(temptingPlayer == null) return false;
			else
			{
				ItemStack var1 = temptingPlayer.getCurrentEquippedItem();
				return var1 == null ? false : var1.itemID == breedingFood;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		field_75283_c = temptingPlayer.posX;
		field_75280_d = temptingPlayer.posY;
		field_75281_e = temptingPlayer.posZ;
		field_75287_j = true;
		field_75286_m = temptedEntity.getNavigator().getAvoidsWater();
		temptedEntity.getNavigator().setAvoidsWater(false);
	}
	
	@Override public void updateTask()
	{
		temptedEntity.getLookHelper().setLookPositionWithEntity(temptingPlayer, 30.0F, temptedEntity.getVerticalFaceSpeed());
		if(temptedEntity.getDistanceSqToEntity(temptingPlayer) < 6.25D)
		{
			temptedEntity.getNavigator().clearPathEntity();
		} else
		{
			temptedEntity.getNavigator().tryMoveToEntityLiving(temptingPlayer, field_75282_b);
		}
	}
}
