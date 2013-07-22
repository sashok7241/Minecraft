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
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
	}
	
	@Override public String toString()
	{
		return "END";
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
	}
}
