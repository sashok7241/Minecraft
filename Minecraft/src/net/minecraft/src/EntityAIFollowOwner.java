package net.minecraft.src;

public class EntityAIFollowOwner extends EntityAIBase
{
	private EntityTameable thePet;
	private EntityLiving theOwner;
	World theWorld;
	private float field_75336_f;
	private PathNavigate petPathfinder;
	private int field_75343_h;
	float maxDist;
	float minDist;
	private boolean field_75344_i;
	
	public EntityAIFollowOwner(EntityTameable p_i3466_1_, float p_i3466_2_, float p_i3466_3_, float p_i3466_4_)
	{
		thePet = p_i3466_1_;
		theWorld = p_i3466_1_.worldObj;
		field_75336_f = p_i3466_2_;
		petPathfinder = p_i3466_1_.getNavigator();
		minDist = p_i3466_3_;
		maxDist = p_i3466_4_;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		return !petPathfinder.noPath() && thePet.getDistanceSqToEntity(theOwner) > maxDist * maxDist && !thePet.isSitting();
	}
	
	@Override public void resetTask()
	{
		theOwner = null;
		petPathfinder.clearPathEntity();
		thePet.getNavigator().setAvoidsWater(field_75344_i);
	}
	
	@Override public boolean shouldExecute()
	{
		EntityLiving var1 = thePet.getOwner();
		if(var1 == null) return false;
		else if(thePet.isSitting()) return false;
		else if(thePet.getDistanceSqToEntity(var1) < minDist * minDist) return false;
		else
		{
			theOwner = var1;
			return true;
		}
	}
	
	@Override public void startExecuting()
	{
		field_75343_h = 0;
		field_75344_i = thePet.getNavigator().getAvoidsWater();
		thePet.getNavigator().setAvoidsWater(false);
	}
	
	@Override public void updateTask()
	{
		thePet.getLookHelper().setLookPositionWithEntity(theOwner, 10.0F, thePet.getVerticalFaceSpeed());
		if(!thePet.isSitting())
		{
			if(--field_75343_h <= 0)
			{
				field_75343_h = 10;
				if(!petPathfinder.tryMoveToEntityLiving(theOwner, field_75336_f))
				{
					if(thePet.getDistanceSqToEntity(theOwner) >= 144.0D)
					{
						int var1 = MathHelper.floor_double(theOwner.posX) - 2;
						int var2 = MathHelper.floor_double(theOwner.posZ) - 2;
						int var3 = MathHelper.floor_double(theOwner.boundingBox.minY);
						for(int var4 = 0; var4 <= 4; ++var4)
						{
							for(int var5 = 0; var5 <= 4; ++var5)
							{
								if((var4 < 1 || var5 < 1 || var4 > 3 || var5 > 3) && theWorld.doesBlockHaveSolidTopSurface(var1 + var4, var3 - 1, var2 + var5) && !theWorld.isBlockNormalCube(var1 + var4, var3, var2 + var5) && !theWorld.isBlockNormalCube(var1 + var4, var3 + 1, var2 + var5))
								{
									thePet.setLocationAndAngles(var1 + var4 + 0.5F, var3, var2 + var5 + 0.5F, thePet.rotationYaw, thePet.rotationPitch);
									petPathfinder.clearPathEntity();
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}
