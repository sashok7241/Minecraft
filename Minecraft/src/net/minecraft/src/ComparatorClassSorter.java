package net.minecraft.src;

import java.util.Comparator;

class ComparatorClassSorter implements Comparator
{
	final CallableSuspiciousClasses theSuspiciousClasses;
	
	ComparatorClassSorter(CallableSuspiciousClasses p_i6802_1_)
	{
		theSuspiciousClasses = p_i6802_1_;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return func_85081_a((Class) p_compare_1_, (Class) p_compare_2_);
	}
	
	public int func_85081_a(Class p_85081_1_, Class p_85081_2_)
	{
		String var3 = p_85081_1_.getPackage() == null ? "" : p_85081_1_.getPackage().getName();
		String var4 = p_85081_2_.getPackage() == null ? "" : p_85081_2_.getPackage().getName();
		return var3.compareTo(var4);
	}
}
