package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableTagCompound2 implements Callable
{
	final int field_82588_a;
	final NBTTagCompound theNBTTagCompound;
	
	CallableTagCompound2(NBTTagCompound par1NBTTagCompound, int par2)
	{
		theNBTTagCompound = par1NBTTagCompound;
		field_82588_a = par2;
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
