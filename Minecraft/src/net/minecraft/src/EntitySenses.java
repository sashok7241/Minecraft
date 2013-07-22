package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class EntitySenses
{
	EntityLiving entityObj;
	List seenEntities = new ArrayList();
	List unseenEntities = new ArrayList();
	
	public EntitySenses(EntityLiving par1EntityLiving)
	{
		entityObj = par1EntityLiving;
	}
	
	public boolean canSee(Entity par1Entity)
	{
		if(seenEntities.contains(par1Entity)) return true;
		else if(unseenEntities.contains(par1Entity)) return false;
		else
		{
			entityObj.worldObj.theProfiler.startSection("canSee");
			boolean var2 = entityObj.canEntityBeSeen(par1Entity);
			entityObj.worldObj.theProfiler.endSection();
			if(var2)
			{
				seenEntities.add(par1Entity);
			} else
			{
				unseenEntities.add(par1Entity);
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
