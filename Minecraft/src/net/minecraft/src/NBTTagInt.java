package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase
{
	public int data;
	
	public NBTTagInt(String par1Str)
	{
		super(par1Str);
	}
	
	public NBTTagInt(String par1Str, int par2)
	{
		super(par1Str);
		data = par2;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagInt(getName(), data);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(super.equals(par1Obj))
		{
			NBTTagInt var2 = (NBTTagInt) par1Obj;
			return data == var2.data;
		} else return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 3;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ data;
	}
	
	@Override void load(DataInput par1DataInput, int par2) throws IOException
	{
		data = par1DataInput.readInt();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(data);
	}
}
