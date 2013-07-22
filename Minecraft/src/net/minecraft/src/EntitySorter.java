package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator
{
	private double entityPosX;
	private double entityPosY;
	private double entityPosZ;
	
	public EntitySorter(Entity par1Entity)
	{
		entityPosX = -par1Entity.posX;
		entityPosY = -par1Entity.posY;
		entityPosZ = -par1Entity.posZ;
	}
	
	@Override public int compare(Object par1Obj, Object par2Obj)
	{
		return sortByDistanceToEntity((WorldRenderer) par1Obj, (WorldRenderer) par2Obj);
	}
	
	public int sortByDistanceToEntity(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer)
	{
		double var3 = par1WorldRenderer.posXPlus + entityPosX;
		double var5 = par1WorldRenderer.posYPlus + entityPosY;
		double var7 = par1WorldRenderer.posZPlus + entityPosZ;
		double var9 = par2WorldRenderer.posXPlus + entityPosX;
		double var11 = par2WorldRenderer.posYPlus + entityPosY;
		double var13 = par2WorldRenderer.posZPlus + entityPosZ;
		return (int) ((var3 * var3 + var5 * var5 + var7 * var7 - (var9 * var9 + var11 * var11 + var13 * var13)) * 1024.0D);
	}
}
