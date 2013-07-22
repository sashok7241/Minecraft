package net.minecraft.src;

public class EntityAIAttackOnCollide extends EntityAIBase
{
	World worldObj;
	EntityLiving attacker;
	EntityLiving entityTarget;
	int attackTick;
	float field_75440_e;
	boolean field_75437_f;
	PathEntity entityPathEntity;
	Class classTarget;
	private int field_75445_i;
	
	public EntityAIAttackOnCollide(EntityLiving p_i3476_1_, Class p_i3476_2_, float p_i3476_3_, boolean p_i3476_4_)
	{
		this(p_i3476_1_, p_i3476_3_, p_i3476_4_);
		classTarget = p_i3476_2_;
	}
	
	public EntityAIAttackOnCollide(EntityLiving p_i3477_1_, float p_i3477_2_, boolean p_i3477_3_)
	{
		attackTick = 0;
		attacker = p_i3477_1_;
		worldObj = p_i3477_1_.worldObj;
		field_75440_e = p_i3477_2_;
		field_75437_f = p_i3477_3_;
		setMutexBits(3);
	}
	
	@Override public boolean continueExecuting()
	{
		EntityLiving var1 = attacker.getAttackTarget();
		return var1 == null ? false : !entityTarget.isEntityAlive() ? false : !field_75437_f ? !attacker.getNavigator().noPath() : attacker.isWithinHomeDistance(MathHelper.floor_double(entityTarget.posX), MathHelper.floor_double(entityTarget.posY), MathHelper.floor_double(entityTarget.posZ));
	}
	
	@Override public void resetTask()
	{
		entityTarget = null;
		attacker.getNavigator().clearPathEntity();
	}
	
	@Override public boolean shouldExecute()
	{
		EntityLiving var1 = attacker.getAttackTarget();
		if(var1 == null) return false;
		else if(classTarget != null && !classTarget.isAssignableFrom(var1.getClass())) return false;
		else
		{
			entityTarget = var1;
			entityPathEntity = attacker.getNavigator().getPathToEntityLiving(entityTarget);
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
		attacker.getLookHelper().setLookPositionWithEntity(entityTarget, 30.0F, 30.0F);
		if((field_75437_f || attacker.getEntitySenses().canSee(entityTarget)) && --field_75445_i <= 0)
		{
			field_75445_i = 4 + attacker.getRNG().nextInt(7);
			attacker.getNavigator().tryMoveToEntityLiving(entityTarget, field_75440_e);
		}
		attackTick = Math.max(attackTick - 1, 0);
		double var1 = attacker.width * 2.0F * attacker.width * 2.0F;
		if(attacker.getDistanceSq(entityTarget.posX, entityTarget.boundingBox.minY, entityTarget.posZ) <= var1)
		{
			if(attackTick <= 0)
			{
				attackTick = 20;
				if(attacker.getHeldItem() != null)
				{
					attacker.swingItem();
				}
				attacker.attackEntityAsMob(entityTarget);
			}
		}
	}
}
