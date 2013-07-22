package net.minecraft.src;

import java.util.Iterator;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
	private final int maxItemsWeighted;
	
	protected BlockPressurePlateWeighted(int p_i9016_1_, String p_i9016_2_, Material p_i9016_3_, int p_i9016_4_)
	{
		super(p_i9016_1_, p_i9016_2_, p_i9016_3_);
		maxItemsWeighted = p_i9016_4_;
	}
	
	@Override protected int getMetaFromWeight(int p_94355_1_)
	{
		return p_94355_1_;
	}
	
	@Override protected int getPlateState(World p_94351_1_, int p_94351_2_, int p_94351_3_, int p_94351_4_)
	{
		int var5 = 0;
		Iterator var6 = p_94351_1_.getEntitiesWithinAABB(EntityItem.class, getSensitiveAABB(p_94351_2_, p_94351_3_, p_94351_4_)).iterator();
		while(var6.hasNext())
		{
			EntityItem var7 = (EntityItem) var6.next();
			var5 += var7.getEntityItem().stackSize;
			if(var5 >= maxItemsWeighted)
			{
				break;
			}
		}
		if(var5 <= 0) return 0;
		else
		{
			float var8 = (float) Math.min(maxItemsWeighted, var5) / (float) maxItemsWeighted;
			return MathHelper.ceiling_float_int(var8 * 15.0F);
		}
	}
	
	@Override protected int getPowerSupply(int p_94350_1_)
	{
		return p_94350_1_;
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 10;
	}
}
