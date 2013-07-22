package net.minecraft.src;

import java.util.Collections;
import java.util.List;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
	private final Class targetClass;
	private final int targetChance;
	private final EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;
	private final IEntitySelector field_82643_g;
	private EntityLivingBase targetEntity;
	
	public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4)
	{
		this(par1EntityCreature, par2Class, par3, par4, false);
	}
	
	public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5)
	{
		this(par1EntityCreature, par2Class, par3, par4, par5, (IEntitySelector) null);
	}
	
	public EntityAINearestAttackableTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4, boolean par5, IEntitySelector par6IEntitySelector)
	{
		super(par1EntityCreature, par4, par5);
		targetClass = par2Class;
		targetChance = par3;
		theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(par1EntityCreature);
		setMutexBits(1);
		field_82643_g = new EntityAINearestAttackableTargetSelector(this, par6IEntitySelector);
	}
	
	@Override public boolean shouldExecute()
	{
		if(targetChance > 0 && taskOwner.getRNG().nextInt(targetChance) != 0) return false;
		else
		{
			double var1 = func_111175_f();
			List var3 = taskOwner.worldObj.selectEntitiesWithinAABB(targetClass, taskOwner.boundingBox.expand(var1, 4.0D, var1), field_82643_g);
			Collections.sort(var3, theNearestAttackableTargetSorter);
			if(var3.isEmpty()) return false;
			else
			{
				targetEntity = (EntityLivingBase) var3.get(0);
				return true;
			}
		}
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(targetEntity);
		super.startExecuting();
	}
}
