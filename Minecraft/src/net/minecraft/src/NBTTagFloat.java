package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase
{
	public float data;
	
	public NBTTagFloat(String par1Str)
	{
		super(par1Str);
	}
	
	public NBTTagFloat(String par1Str, float par2)
	{
		super(par1Str);
		data = par2;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagFloat(getName(), data);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(super.equals(par1Obj))
		{
			NBTTagFloat var2 = (NBTTagFloat) par1Obj;
			return data == var2.data;
		} else return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 5;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ Float.floatToIntBits(data);
	}
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
		data = par1DataInput.readFloat();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeFloat(data);
	}
}
