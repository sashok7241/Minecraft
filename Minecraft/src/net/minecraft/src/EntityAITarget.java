package net.minecraft.src;

public abstract class EntityAITarget extends EntityAIBase
{
	protected EntityLiving taskOwner;
	protected float targetDistance;
	protected boolean shouldCheckSight;
	private boolean field_75303_a;
	private int field_75301_b;
	private int field_75302_c;
	private int field_75298_g;
	
	public EntityAITarget(EntityLiving p_i3505_1_, float p_i3505_2_, boolean p_i3505_3_)
	{
		this(p_i3505_1_, p_i3505_2_, p_i3505_3_, false);
	}
	
	public EntityAITarget(EntityLiving p_i3506_1_, float p_i3506_2_, boolean p_i3506_3_, boolean p_i3506_4_)
	{
		field_75301_b = 0;
		field_75302_c = 0;
		field_75298_g = 0;
		taskOwner = p_i3506_1_;
		targetDistance = p_i3506_2_;
		shouldCheckSight = p_i3506_3_;
		field_75303_a = p_i3506_4_;
	}
	
	@Override public boolean continueExecuting()
	{
		EntityLiving var1 = taskOwner.getAttackTarget();
		if(var1 == null) return false;
		else if(!var1.isEntityAlive()) return false;
		else if(taskOwner.getDistanceSqToEntity(var1) > targetDistance * targetDistance) return false;
		else
		{
			if(shouldCheckSight)
			{
				if(taskOwner.getEntitySenses().canSee(var1))
				{
					field_75298_g = 0;
				} else if(++field_75298_g > 60) return false;
			}
			return true;
		}
	}
	
	private boolean func_75295_a(EntityLiving p_75295_1_)
	{
		field_75302_c = 10 + taskOwner.getRNG().nextInt(5);
		PathEntity var2 = taskOwner.getNavigator().getPathToEntityLiving(p_75295_1_);
		if(var2 == null) return false;
		else
		{
			PathPoint var3 = var2.getFinalPathPoint();
			if(var3 == null) return false;
			else
			{
				int var4 = var3.xCoord - MathHelper.floor_double(p_75295_1_.posX);
				int var5 = var3.zCoord - MathHelper.floor_double(p_75295_1_.posZ);
				return var4 * var4 + var5 * var5 <= 2.25D;
			}
		}
	}
	
	protected boolean isSuitableTarget(EntityLiving p_75296_1_, boolean p_75296_2_)
	{
		if(p_75296_1_ == null) return false;
		else if(p_75296_1_ == taskOwner) return false;
		else if(!p_75296_1_.isEntityAlive()) return false;
		else if(!taskOwner.canAttackClass(p_75296_1_.getClass())) return false;
		else
		{
			if(taskOwner instanceof EntityTameable && ((EntityTameable) taskOwner).isTamed())
			{
				if(p_75296_1_ instanceof EntityTameable && ((EntityTameable) p_75296_1_).isTamed()) return false;
				if(p_75296_1_ == ((EntityTameable) taskOwner).getOwner()) return false;
			} else if(p_75296_1_ instanceof EntityPlayer && !p_75296_2_ && ((EntityPlayer) p_75296_1_).capabilities.disableDamage) return false;
			if(!taskOwner.isWithinHomeDistance(MathHelper.floor_double(p_75296_1_.posX), MathHelper.floor_double(p_75296_1_.posY), MathHelper.floor_double(p_75296_1_.posZ))) return false;
			else if(shouldCheckSight && !taskOwner.getEntitySenses().canSee(p_75296_1_)) return false;
			else
			{
				if(field_75303_a)
				{
					if(--field_75302_c <= 0)
					{
						field_75301_b = 0;
					}
					if(field_75301_b == 0)
					{
						field_75301_b = func_75295_a(p_75296_1_) ? 1 : 2;
					}
					if(field_75301_b == 2) return false;
				}
				return true;
			}
		}
	}
	
	@Override public void resetTask()
	{
		taskOwner.setAttackTarget((EntityLiving) null);
	}
	
	@Override public void startExecuting()
	{
		field_75301_b = 0;
		field_75302_c = 0;
		field_75298_g = 0;
	}
}
