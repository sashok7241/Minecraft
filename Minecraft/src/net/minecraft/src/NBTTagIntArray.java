package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase
{
	public int[] intArray;
	
	public NBTTagIntArray(String par1Str)
	{
		super(par1Str);
	}
	
	public NBTTagIntArray(String par1Str, int[] par2ArrayOfInteger)
	{
		super(par1Str);
		intArray = par2ArrayOfInteger;
	}
	
	@Override public NBTBase copy()
	{
		int[] var1 = new int[intArray.length];
		System.arraycopy(intArray, 0, var1, 0, intArray.length);
		return new NBTTagIntArray(getName(), var1);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!super.equals(par1Obj)) return false;
		else
		{
			NBTTagIntArray var2 = (NBTTagIntArray) par1Obj;
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
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
		int var2 = par1DataInput.readInt();
		intArray = new int[var2];
		for(int var3 = 0; var3 < var2; ++var3)
		{
			intArray[var3] = par1DataInput.readInt();
		}
	}
	
	@Override public String toString()
	{
		return "[" + intArray.length + " bytes]";
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(intArray.length);
		for(int element : intArray)
		{
			par1DataOutput.writeInt(element);
		}
	}
}
