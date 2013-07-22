package net.minecraft.src;

import java.util.Comparator;

public class RenderSorter implements Comparator
{
	private EntityLivingBase baseEntity;
	
	public RenderSorter(EntityLivingBase par1EntityLivingBase)
	{
		baseEntity = par1EntityLivingBase;
	}
	
	@Override public int compare(Object par1Obj, Object par2Obj)
	{
		return doCompare((WorldRenderer) par1Obj, (WorldRenderer) par2Obj);
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
