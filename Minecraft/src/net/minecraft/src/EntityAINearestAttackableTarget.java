package net.minecraft.src;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EntityAINearestAttackableTarget extends EntityAITarget
{
	EntityLiving targetEntity;
	Class targetClass;
	int targetChance;
	private final IEntitySelector field_82643_g;
	private EntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;
	
	public EntityAINearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5)
	{
		this(par1EntityLiving, par2Class, par3, par4, par5, false);
	}
	
	public EntityAINearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5, boolean par6)
	{
		this(par1EntityLiving, par2Class, par3, par4, par5, par6, (IEntitySelector) null);
	}
	
	public EntityAINearestAttackableTarget(EntityLiving par1, Class par2, float par3, int par4, boolean par5, boolean par6, IEntitySelector par7IEntitySelector)
	{
		super(par1, par3, par5, par6);
		targetClass = par2;
		targetDistance = par3;
		targetChance = par4;
		theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(this, par1);
		field_82643_g = par7IEntitySelector;
		setMutexBits(1);
	}
	
	@Override public boolean shouldExecute()
	{
		if(targetChance > 0 && taskOwner.getRNG().nextInt(targetChance) != 0) return false;
		else
		{
			if(targetClass == EntityPlayer.class)
			{
				EntityPlayer var1 = taskOwner.worldObj.getClosestVulnerablePlayerToEntity(taskOwner, targetDistance);
				if(isSuitableTarget(var1, false))
				{
					targetEntity = var1;
					return true;
				}
			} else
			{
				List var5 = taskOwner.worldObj.selectEntitiesWithinAABB(targetClass, taskOwner.boundingBox.expand(targetDistance, 4.0D, targetDistance), field_82643_g);
				Collections.sort(var5, theNearestAttackableTargetSorter);
				Iterator var2 = var5.iterator();
				while(var2.hasNext())
				{
					Entity var3 = (Entity) var2.next();
					EntityLiving var4 = (EntityLiving) var3;
					if(isSuitableTarget(var4, false))
					{
						targetEntity = var4;
						return true;
					}
				}
			}
			return false;
		}
	}
	
	@Override public void startExecuting()
	{
		taskOwner.setAttackTarget(targetEntity);
		super.startExecuting();
	}
}
