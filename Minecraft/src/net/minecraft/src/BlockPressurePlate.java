package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate
{
	private EnumMobType triggerMobType;
	
	protected BlockPressurePlate(int par1, String par2Str, Material par3Material, EnumMobType par4EnumMobType)
	{
		super(par1, par2Str, par3Material);
		triggerMobType = par4EnumMobType;
	}
	
	@Override protected int getMetaFromWeight(int par1)
	{
		return par1 > 0 ? 1 : 0;
	}
	
	@Override protected int getPlateState(World par1World, int par2, int par3, int par4)
	{
		List var5 = null;
		if(triggerMobType == EnumMobType.everything)
		{
			var5 = par1World.getEntitiesWithinAABBExcludingEntity((Entity) null, getSensitiveAABB(par2, par3, par4));
		}
		if(triggerMobType == EnumMobType.mobs)
		{
			var5 = par1World.getEntitiesWithinAABB(EntityLiving.class, getSensitiveAABB(par2, par3, par4));
		}
		if(triggerMobType == EnumMobType.players)
		{
			var5 = par1World.getEntitiesWithinAABB(EntityPlayer.class, getSensitiveAABB(par2, par3, par4));
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
	
	@Override protected int getPowerSupply(int par1)
	{
		return par1 == 1 ? 15 : 0;
	}
}
