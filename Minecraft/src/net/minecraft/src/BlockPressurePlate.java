package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate
{
	private EnumMobType triggerMobType;
	
	protected BlockPressurePlate(int p_i9080_1_, String p_i9080_2_, Material p_i9080_3_, EnumMobType p_i9080_4_)
	{
		super(p_i9080_1_, p_i9080_2_, p_i9080_3_);
		triggerMobType = p_i9080_4_;
	}
	
	@Override protected int getMetaFromWeight(int p_94355_1_)
	{
		return p_94355_1_ > 0 ? 1 : 0;
	}
	
	@Override protected int getPlateState(World p_94351_1_, int p_94351_2_, int p_94351_3_, int p_94351_4_)
	{
		List var5 = null;
		if(triggerMobType == EnumMobType.everything)
		{
			var5 = p_94351_1_.getEntitiesWithinAABBExcludingEntity((Entity) null, getSensitiveAABB(p_94351_2_, p_94351_3_, p_94351_4_));
		}
		if(triggerMobType == EnumMobType.mobs)
		{
			var5 = p_94351_1_.getEntitiesWithinAABB(EntityLiving.class, getSensitiveAABB(p_94351_2_, p_94351_3_, p_94351_4_));
		}
		if(triggerMobType == EnumMobType.players)
		{
			var5 = p_94351_1_.getEntitiesWithinAABB(EntityPlayer.class, getSensitiveAABB(p_94351_2_, p_94351_3_, p_94351_4_));
		}
		if(!var5.isEmpty())
		{
			Iterator var6 = var5.iterator();
			while(var6.hasNext())
			{
				Entity var7 = (Entity) var6.next();
				if(!var7.doesEntityNotTriggerPressurePlate()) return 15;
			}
		}
		return 0;
	}
	
	@Override protected int getPowerSupply(int p_94350_1_)
	{
		return p_94350_1_ == 1 ? 15 : 0;
	}
}
