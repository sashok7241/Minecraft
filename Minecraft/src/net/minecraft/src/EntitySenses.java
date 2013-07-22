package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class EntitySenses
{
	EntityLiving entityObj;
	List seenEntities = new ArrayList();
	List unseenEntities = new ArrayList();
	
	public EntitySenses(EntityLiving p_i3508_1_)
	{
		entityObj = p_i3508_1_;
	}
	
	public boolean canSee(Entity p_75522_1_)
	{
		if(seenEntities.contains(p_75522_1_)) return true;
		else if(unseenEntities.contains(p_75522_1_)) return false;
		else
		{
			entityObj.worldObj.theProfiler.startSection("canSee");
			boolean var2 = entityObj.canEntityBeSeen(p_75522_1_);
			entityObj.worldObj.theProfiler.endSection();
			if(var2)
			{
				seenEntities.add(p_75522_1_);
			} else
			{
				unseenEntities.add(p_75522_1_);
			}
			return var2;
		}
	}
	
	public void clearSensingCache()
	{
		seenEntities.clear();
		unseenEntities.clear();
	}
}
