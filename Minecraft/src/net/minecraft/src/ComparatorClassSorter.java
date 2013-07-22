package net.minecraft.src;

import java.util.Comparator;

class ComparatorClassSorter implements Comparator
{
	final CallableSuspiciousClasses theSuspiciousClasses;
	
	ComparatorClassSorter(CallableSuspiciousClasses par1CallableSuspiciousClasses)
	{
		theSuspiciousClasses = par1CallableSuspiciousClasses;
	}
	
	@Override public int compare(Object par1Obj, Object par2Obj)
	{
		return func_85081_a((Class) par1Obj, (Class) par2Obj);
	}
	
	public int func_85081_a(Class par1Class, Class par2Class)
	{
		String var3 = par1Class.getPackage() == null ? "" : par1Class.getPackage().getName();
		String var4 = par2Class.getPackage() == null ? "" : par2Class.getPackage().getName();
		return var3.compareTo(var4);
	}
}
