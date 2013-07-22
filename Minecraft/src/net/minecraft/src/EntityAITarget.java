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
	
	public EntityAITarget(EntityLiving par1EntityLiving, float par2, boolean par3)
	{
		this(par1EntityLiving, par2, par3, false);
	}
	
	public EntityAITarget(EntityLiving par1EntityLiving, float par2, boolean par3, boolean par4)
	{
		field_75301_b = 0;
		field_75302_c = 0;
		field_75298_g = 0;
		taskOwner = par1EntityLiving;
		targetDistance = par2;
		shouldCheckSight = par3;
		field_75303_a = par4;
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
	
	private boolean func_75295_a(EntityLiving par1EntityLiving)
	{
		field_75302_c = 10 + taskOwner.getRNG().nextInt(5);
		PathEntity var2 = taskOwner.getNavigator().getPathToEntityLiving(par1EntityLiving);
		if(var2 == null) return false;
		else
		{
			PathPoint var3 = var2.getFinalPathPoint();
			if(var3 == null) return false;
			else
			{
				int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLiving.posX);
				int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLiving.posZ);
				return var4 * var4 + var5 * var5 <= 2.25D;
			}
		}
	}
	
	protected boolean isSuitableTarget(EntityLiving par1EntityLiving, boolean par2)
	{
		if(par1EntityLiving == null) return false;
		else if(par1EntityLiving == taskOwner) return false;
		else if(!par1EntityLiving.isEntityAlive()) return false;
		else if(!taskOwner.canAttackClass(par1EntityLiving.getClass())) return false;
		else
		{
			if(taskOwner instanceof EntityTameable && ((EntityTameable) taskOwner).isTamed())
			{
				if(par1EntityLiving instanceof EntityTameable && ((EntityTameable) par1EntityLiving).isTamed()) return false;
				if(par1EntityLiving == ((EntityTameable) taskOwner).getOwner()) return false;
			} else if(par1EntityLiving instanceof EntityPlayer && !par2 && ((EntityPlayer) par1EntityLiving).capabilities.disableDamage) return false;
			if(!taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ))) return false;
			else if(shouldCheckSight && !taskOwner.getEntitySenses().canSee(par1EntityLiving)) return false;
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
						field_75301_b = func_75295_a(par1EntityLiving) ? 1 : 2;
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
