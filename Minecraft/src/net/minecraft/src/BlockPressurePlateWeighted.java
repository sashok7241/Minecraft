package net.minecraft.src;

import java.util.Iterator;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate
{
	private final int maxItemsWeighted;
	
	protected BlockPressurePlateWeighted(int par1, String par2Str, Material par3Material, int par4)
	{
		super(par1, par2Str, par3Material);
		maxItemsWeighted = par4;
	}
	
	@Override protected int getMetaFromWeight(int par1)
	{
		return par1;
	}
	
	@Override protected int getPlateState(World par1World, int par2, int par3, int par4)
	{
		int var5 = 0;
		Iterator var6 = par1World.getEntitiesWithinAABB(EntityItem.class, getSensitiveAABB(par2, par3, par4)).iterator();
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
	
	@Override protected int getPowerSupply(int par1)
	{
		return par1;
	}
	
	@Override public int tickRate(World par1World)
	{
		return 10;
	}
}
