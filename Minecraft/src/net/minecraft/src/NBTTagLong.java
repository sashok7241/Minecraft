package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase
{
	public long data;
	
	public NBTTagLong(String par1Str)
	{
		super(par1Str);
	}
	
	public NBTTagLong(String par1Str, long par2)
	{
		super(par1Str);
		data = par2;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagLong(getName(), data);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(super.equals(par1Obj))
		{
			NBTTagLong var2 = (NBTTagLong) par1Obj;
			return data == var2.data;
		} else return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 4;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ (int) (data ^ data >>> 32);
	}
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
		data = par1DataInput.readLong();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeLong(data);
	}
}
