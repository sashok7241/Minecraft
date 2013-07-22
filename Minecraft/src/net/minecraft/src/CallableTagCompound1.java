package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableTagCompound1 implements Callable
{
	final String field_82585_a;
	final NBTTagCompound theNBTTagCompound;
	
	CallableTagCompound1(NBTTagCompound p_i5029_1_, String p_i5029_2_)
	{
		theNBTTagCompound = p_i5029_1_;
		field_82585_a = p_i5029_2_;
	}
	
	@Override public Object call()
	{
		return func_82583_a();
	}
	
	public String func_82583_a()
	{
		return NBTBase.NBTTypes[((NBTBase) NBTTagCompound.getTagMap(theNBTTagCompound).get(field_82585_a)).getId()];
	}
}
