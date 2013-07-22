package net.minecraft.src;

import java.util.Comparator;

public class EntityAINearestAttackableTargetSorter implements Comparator
{
	private Entity theEntity;
	final EntityAINearestAttackableTarget parent;
	
	public EntityAINearestAttackableTargetSorter(EntityAINearestAttackableTarget p_i3499_1_, Entity p_i3499_2_)
	{
		parent = p_i3499_1_;
		theEntity = p_i3499_2_;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return compareDistanceSq((Entity) p_compare_1_, (Entity) p_compare_2_);
	}
	
	public int compareDistanceSq(Entity p_75458_1_, Entity p_75458_2_)
	{
		double var3 = theEntity.getDistanceSqToEntity(p_75458_1_);
		double var5 = theEntity.getDistanceSqToEntity(p_75458_2_);
		return var3 < var5 ? -1 : var3 > var5 ? 1 : 0;
	}
}
