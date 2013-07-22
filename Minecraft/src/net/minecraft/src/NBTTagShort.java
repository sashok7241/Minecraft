package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase
{
	public short data;
	
	public NBTTagShort(String p_i3277_1_)
	{
		super(p_i3277_1_);
	}
	
	public NBTTagShort(String p_i3278_1_, short p_i3278_2_)
	{
		super(p_i3278_1_);
		data = p_i3278_2_;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagShort(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagShort var2 = (NBTTagShort) p_equals_1_;
			return data == var2.data;
		} else return false;
	}
	
	@Override public byte getId()
	{
		return (byte) 2;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ data;
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readShort();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeShort(data);
	}
}
