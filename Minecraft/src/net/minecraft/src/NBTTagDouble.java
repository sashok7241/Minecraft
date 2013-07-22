package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase
{
	public double data;
	
	public NBTTagDouble(String par1Str)
	{
		super(par1Str);
	}
	
	public NBTTagDouble(String par1Str, double par2)
	{
		super(par1Str);
		data = par2;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagDouble(getName(), data);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(super.equals(par1Obj))
		{
			NBTTagDouble var2 = (NBTTagDouble) par1Obj;
			return data == var2.data;
		} else return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 6;
	}
	
	@Override public int hashCode()
	{
		long var1 = Double.doubleToLongBits(data);
		return super.hashCode() ^ (int) (var1 ^ var1 >>> 32);
	}
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
		data = par1DataInput.readDouble();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeDouble(data);
	}
}
