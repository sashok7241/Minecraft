package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase
{
	public float data;
	
	public NBTTagFloat(String p_i3268_1_)
	{
		super(p_i3268_1_);
	}
	
	public NBTTagFloat(String p_i3269_1_, float p_i3269_2_)
	{
		super(p_i3269_1_);
		data = p_i3269_2_;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagFloat(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagFloat var2 = (NBTTagFloat) p_equals_1_;
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
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readFloat();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeFloat(data);
	}
}
