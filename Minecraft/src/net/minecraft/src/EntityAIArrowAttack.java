package net.minecraft.src;

public class EntityAIArrowAttack extends EntityAIBase
{
	private final EntityLiving entityHost;
	private final IRangedAttackMob rangedAttackEntityHost;
	private EntityLiving attackTarget;
	private int rangedAttackTime;
	private float entityMoveSpeed;
	private int field_75318_f;
	private int field_96561_g;
	private int maxRangedAttackTime;
	private float field_96562_i;
	private float field_82642_h;
	
	public EntityAIArrowAttack(IRangedAttackMob par1IRangedAttackMob, float par2, int par3, float par4)
	{
		this(par1IRangedAttackMob, par2, par3, par3, par4);
	}
	
	public EntityAIArrowAttack(IRangedAttackMob par1IRangedAttackMob, float par2, int par3, int par4, float par5)
	{
		rangedAttackTime = -1;
		field_75318_f = 0;
		if(!(par1IRangedAttackMob instanceof EntityLiving)) throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		else
		{
			rangedAttackEntityHost = par1IRangedAttackMob;
			entityHost = (EntityLiving) par1IRangedAttackMob;
			entityMoveSpeed = par2;
			field_96561_g = par3;
			maxRangedAttackTime = par4;
			field_96562_i = par5;
			field_82642_h = par5 * par5;
			setMutexBits(3);
		}
	}
	
	@Override public boolean continueExecuting()
	{
		return shouldExecute() || !entityHost.getNavigator().noPath();
	}
	
	@Override public void resetTask()
	{
		attackTarget = null;
		field_75318_f = 0;
		rangedAttackTime = -1;
	}
	
	@Override public boolean shouldExecute()
	{
		EntityLiving var1 = entityHost.getAttackTarget();
		if(var1 == null) return false;
		else
		{
			attackTarget = var1;
			return true;
		}
	}
	
	@Override public void updateTask()
	{
		double var1 = entityHost.getDistanceSq(attackTarget.posX, attackTarget.boundingBox.minY, attackTarget.posZ);
		boolean var3 = entityHost.getEntitySenses().canSee(attackTarget);
		if(var3)
		{
			++field_75318_f;
		} else
		{
			field_75318_f = 0;
		}
		if(var1 <= field_82642_h && field_75318_f >= 20)
		{
			entityHost.getNavigator().clearPathEntity();
		} else
		{
			entityHost.getNavigator().tryMoveToEntityLiving(attackTarget, entityMoveSpeed);
		}
		entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);
		float var4;
		if(--rangedAttackTime == 0)
		{
			if(var1 > field_82642_h || !var3) return;
			var4 = MathHelper.sqrt_double(var1) / field_96562_i;
			float var5 = var4;
			if(var4 < 0.1F)
			{
				var5 = 0.1F;
			}
			if(var5 > 1.0F)
			{
				var5 = 1.0F;
			}
			rangedAttackEntityHost.attackEntityWithRangedAttack(attackTarget, var5);
			rangedAttackTime = MathHelper.floor_float(var4 * (maxRangedAttackTime - field_96561_g) + field_96561_g);
		} else if(rangedAttackTime < 0)
		{
			var4 = MathHelper.sqrt_double(var1) / field_96562_i;
			rangedAttackTime = MathHelper.floor_float(var4 * (maxRangedAttackTime - field_96561_g) + field_96561_g);
		}
	}
}
