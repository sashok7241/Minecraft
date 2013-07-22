package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
	public int[] intArray;
	
	public NBTTagIntArray(String p_i3270_1_)
	{
		super(p_i3270_1_);
	}
	
	public NBTTagIntArray(String p_i3271_1_, int[] p_i3271_2_)
	{
		super(p_i3271_1_);
		intArray = p_i3271_2_;
	}
	
	@Override public NBTBase copy()
	{
		int[] var1 = new int[intArray.length];
		System.arraycopy(intArray, 0, var1, 0, intArray.length);
		return new NBTTagIntArray(getName(), var1);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!super.equals(p_equals_1_)) return false;
		else
		{
			NBTTagIntArray var2 = (NBTTagIntArray) p_equals_1_;
			return intArray == null && var2.intArray == null || intArray != null && Arrays.equals(intArray, var2.intArray);
		}
	}
	
	@Override public byte getId()
	{
		return (byte) 11;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ Arrays.hashCode(intArray);
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		int var2 = p_74735_1_.readInt();
		intArray = new int[var2];
		for(int var3 = 0; var3 < var2; ++var3)
		{
			intArray[var3] = p_74735_1_.readInt();
		}
	}
	
	@Override public String toString()
	{
		return "[" + intArray.length + " bytes]";
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeInt(intArray.length);
		for(int element : intArray)
		{
			p_74734_1_.writeInt(element);
		}
	}
}
