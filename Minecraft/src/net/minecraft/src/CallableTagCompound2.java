package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableTagCompound2 implements Callable
{
	final int field_82588_a;
	final NBTTagCompound theNBTTagCompound;
	
	CallableTagCompound2(NBTTagCompound p_i5030_1_, int p_i5030_2_)
	{
		theNBTTagCompound = p_i5030_1_;
		field_82588_a = p_i5030_2_;
	}
	
	@Override public Object call()
	{
		return func_82586_a();
	}
	
	public String func_82586_a()
	{
		return NBTBase.NBTTypes[field_82588_a];
	}
}
