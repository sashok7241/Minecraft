package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator
{
	private double entityPosX;
	private double entityPosY;
	private double entityPosZ;
	
	public EntitySorter(Entity p_i3189_1_)
	{
		entityPosX = -p_i3189_1_.posX;
		entityPosY = -p_i3189_1_.posY;
		entityPosZ = -p_i3189_1_.posZ;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return sortByDistanceToEntity((WorldRenderer) p_compare_1_, (WorldRenderer) p_compare_2_);
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
