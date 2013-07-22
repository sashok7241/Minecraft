package net.minecraft.src;

public abstract class EntityAITarget extends EntityAIBase
{
	protected EntityCreature taskOwner;
	protected boolean shouldCheckSight;
	private boolean field_75303_a;
	private int field_75301_b;
	private int field_75302_c;
	private int field_75298_g;
	
	public EntityAITarget(EntityCreature par1EntityCreature, boolean par2)
	{
		this(par1EntityCreature, par2, false);
	}
	
	public EntityAITarget(EntityCreature par1EntityCreature, boolean par2, boolean par3)
	{
		taskOwner = par1EntityCreature;
		shouldCheckSight = par2;
		field_75303_a = par3;
	}
	
	@Override public boolean continueExecuting()
	{
		EntityLivingBase var1 = taskOwner.getAttackTarget();
		if(var1 == null) return false;
		else if(!var1.isEntityAlive()) return false;
		else
		{
			double var2 = func_111175_f();
			if(taskOwner.getDistanceSqToEntity(var1) > var2 * var2) return false;
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
	}
	
	protected double func_111175_f()
	{
		AttributeInstance var1 = taskOwner.func_110148_a(SharedMonsterAttributes.field_111265_b);
		return var1 == null ? 16.0D : var1.func_111126_e();
	}
	
	private boolean func_75295_a(EntityLivingBase par1EntityLivingBase)
	{
		field_75302_c = 10 + taskOwner.getRNG().nextInt(5);
		PathEntity var2 = taskOwner.getNavigator().getPathToEntityLiving(par1EntityLivingBase);
		if(var2 == null) return false;
		else
		{
			PathPoint var3 = var2.getFinalPathPoint();
			if(var3 == null) return false;
			else
			{
				int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLivingBase.posX);
				int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLivingBase.posZ);
				return var4 * var4 + var5 * var5 <= 2.25D;
			}
		}
	}
	
	protected boolean isSuitableTarget(EntityLivingBase par1EntityLivingBase, boolean par2)
	{
		if(par1EntityLivingBase == null) return false;
		else if(par1EntityLivingBase == taskOwner) return false;
		else if(!par1EntityLivingBase.isEntityAlive()) return false;
		else if(!taskOwner.canAttackClass(par1EntityLivingBase.getClass())) return false;
		else
		{
			if(taskOwner instanceof EntityOwnable && org.apache.commons.lang3.StringUtils.isNotEmpty(((EntityOwnable) taskOwner).getOwnerName()))
			{
				if(par1EntityLivingBase instanceof EntityOwnable && ((EntityOwnable) taskOwner).getOwnerName().equals(((EntityOwnable) par1EntityLivingBase).getOwnerName())) return false;
				if(par1EntityLivingBase == ((EntityOwnable) taskOwner).getOwner()) return false;
			} else if(par1EntityLivingBase instanceof EntityPlayer && !par2 && ((EntityPlayer) par1EntityLivingBase).capabilities.disableDamage) return false;
			if(!taskOwner.func_110176_b(MathHelper.floor_double(par1EntityLivingBase.posX), MathHelper.floor_double(par1EntityLivingBase.posY), MathHelper.floor_double(par1EntityLivingBase.posZ))) return false;
			else if(shouldCheckSight && !taskOwner.getEntitySenses().canSee(par1EntityLivingBase)) return false;
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
						field_75301_b = func_75295_a(par1EntityLivingBase) ? 1 : 2;
					}
					if(field_75301_b == 2) return false;
				}
				return true;
			}
		}
	}
	
	@Override public void resetTask()
	{
		taskOwner.setAttackTarget((EntityLivingBase) null);
	}
	
	@Override public void startExecuting()
	{
		field_75301_b = 0;
		field_75302_c = 0;
		field_75298_g = 0;
	}
}
