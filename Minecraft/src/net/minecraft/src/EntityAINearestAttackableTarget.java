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
	
	public EntityAINearestAttackableTarget(EntityLiving p_i3500_1_, Class p_i3500_2_, float p_i3500_3_, int p_i3500_4_, boolean p_i3500_5_)
	{
		this(p_i3500_1_, p_i3500_2_, p_i3500_3_, p_i3500_4_, p_i3500_5_, false);
	}
	
	public EntityAINearestAttackableTarget(EntityLiving p_i3501_1_, Class p_i3501_2_, float p_i3501_3_, int p_i3501_4_, boolean p_i3501_5_, boolean p_i3501_6_)
	{
		this(p_i3501_1_, p_i3501_2_, p_i3501_3_, p_i3501_4_, p_i3501_5_, p_i3501_6_, (IEntitySelector) null);
	}
	
	public EntityAINearestAttackableTarget(EntityLiving p_i5011_1_, Class p_i5011_2_, float p_i5011_3_, int p_i5011_4_, boolean p_i5011_5_, boolean p_i5011_6_, IEntitySelector p_i5011_7_)
	{
		super(p_i5011_1_, p_i5011_3_, p_i5011_5_, p_i5011_6_);
		targetClass = p_i5011_2_;
		targetDistance = p_i5011_3_;
		targetChance = p_i5011_4_;
		theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetSorter(this, p_i5011_1_);
		field_82643_g = p_i5011_7_;
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
