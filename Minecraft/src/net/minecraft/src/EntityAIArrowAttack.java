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
	
	public EntityAIArrowAttack(IRangedAttackMob p_i5059_1_, float p_i5059_2_, int p_i5059_3_, float p_i5059_4_)
	{
		this(p_i5059_1_, p_i5059_2_, p_i5059_3_, p_i5059_3_, p_i5059_4_);
	}
	
	public EntityAIArrowAttack(IRangedAttackMob p_i10050_1_, float p_i10050_2_, int p_i10050_3_, int p_i10050_4_, float p_i10050_5_)
	{
		rangedAttackTime = -1;
		field_75318_f = 0;
		if(!(p_i10050_1_ instanceof EntityLiving)) throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		else
		{
			rangedAttackEntityHost = p_i10050_1_;
			entityHost = (EntityLiving) p_i10050_1_;
			entityMoveSpeed = p_i10050_2_;
			field_96561_g = p_i10050_3_;
			maxRangedAttackTime = p_i10050_4_;
			field_96562_i = p_i10050_5_;
			field_82642_h = p_i10050_5_ * p_i10050_5_;
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
