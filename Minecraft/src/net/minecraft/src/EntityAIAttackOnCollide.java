package net.minecraft.src;

public class EntityAIAttackOnCollide extends EntityAIBase
{
	World worldObj;
	EntityCreature attacker;
	int attackTick;
	double field_75440_e;
	boolean field_75437_f;
	PathEntity entityPathEntity;
	Class classTarget;
	private int field_75445_i;
	
	public EntityAIAttackOnCollide(EntityCreature par1EntityCreature, Class par2Class, double par3, boolean par5)
	{
		this(par1EntityCreature, par3, par5);
		classTarget = par2Class;
	}
	
	public EntityAIAttackOnCollide(EntityCreature par1EntityCreature, double par2, boolean par4)
	{
		attacker = par1EntityCreature;
		worldObj = par1EntityCreature.worldObj;
		field_75440_e = par2;
		field_75437_f = par4;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		EntityLivingBase var1 = attacker.getAttackTarget();
		return var1 == null ? false : !var1.isEntityAlive() ? false : !field_75437_f ? !attacker.getNavigator().noPath() : attacker.func_110176_b(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ));
	}
	
	@Override public void resetTask()
	{
		attacker.getNavigator().clearPathEntity();
	}
	
	@Override public boolean shouldExecute()
	{
		EntityLivingBase var1 = attacker.getAttackTarget();
		if(var1 == null) return false;
		else if(!var1.isEntityAlive()) return false;
		else if(classTarget != null && !classTarget.isAssignableFrom(var1.getClass())) return false;
		else
		{
			entityPathEntity = attacker.getNavigator().getPathToEntityLiving(var1);
			return entityPathEntity != null;
		}
	}
	
	@Override public void startExecuting()
	{
		attacker.getNavigator().setPath(entityPathEntity, field_75440_e);
		field_75445_i = 0;
	}
	
	@Override public void updateTask()
	{
		EntityLivingBase var1 = attacker.getAttackTarget();
		attacker.getLookHelper().setLookPositionWithEntity(var1, 30.0F, 30.0F);
		if((field_75437_f || attacker.getEntitySenses().canSee(var1)) && --field_75445_i <= 0)
		{
			field_75445_i = 4 + attacker.getRNG().nextInt(7);
			attacker.getNavigator().tryMoveToEntityLiving(var1, field_75440_e);
		}
		attackTick = Math.max(attackTick - 1, 0);
		double var2 = attacker.width * 2.0F * attacker.width * 2.0F + var1.width;
		if(attacker.getDistanceSq(var1.posX, var1.boundingBox.minY, var1.posZ) <= var2)
		{
			if(attackTick <= 0)
			{
				attackTick = 20;
				if(attacker.getHeldItem() != null)
				{
					attacker.swingItem();
				}
				attacker.attackEntityAsMob(var1);
			}
		}
	}
}
