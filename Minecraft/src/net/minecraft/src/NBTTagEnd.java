package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase
{
	public NBTTagEnd()
	{
		super((String) null);
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagEnd();
	}
	
	@Override public byte getId()
	{
		return (byte) 0;
	}
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
	}
	
	@Override public String toString()
	{
		return "END";
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
	}
}
