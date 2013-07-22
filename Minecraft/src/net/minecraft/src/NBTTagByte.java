package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase
{
	public byte data;
	
	public NBTTagByte(String p_i3263_1_)
	{
		super(p_i3263_1_);
	}
	
	public NBTTagByte(String p_i3264_1_, byte p_i3264_2_)
	{
		super(p_i3264_1_);
		data = p_i3264_2_;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagByte(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagByte var2 = (NBTTagByte) p_equals_1_;
			return data == var2.data;
		} else return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 1;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ data;
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readByte();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeByte(data);
	}
}
