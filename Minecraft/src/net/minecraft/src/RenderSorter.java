package net.minecraft.src;

import java.util.Comparator;

public class RenderSorter implements Comparator
{
	private EntityLiving baseEntity;
	
	public RenderSorter(EntityLiving p_i3190_1_)
	{
		baseEntity = p_i3190_1_;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return doCompare((WorldRenderer) p_compare_1_, (WorldRenderer) p_compare_2_);
	}
	
	public int doCompare(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer)
	{
		if(par1WorldRenderer.isInFrustum && !par2WorldRenderer.isInFrustum) return 1;
		else if(par2WorldRenderer.isInFrustum && !par1WorldRenderer.isInFrustum) return -1;
		else
		{
			double var3 = par1WorldRenderer.distanceToEntitySquared(baseEntity);
			double var5 = par2WorldRenderer.distanceToEntitySquared(baseEntity);
			return var3 < var5 ? 1 : var3 > var5 ? -1 : par1WorldRenderer.chunkIndex < par2WorldRenderer.chunkIndex ? 1 : -1;
		}
	}
}
