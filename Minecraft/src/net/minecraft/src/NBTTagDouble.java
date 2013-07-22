package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase
{
	public double data;
	
	public NBTTagDouble(String p_i3266_1_)
	{
		super(p_i3266_1_);
	}
	
	public NBTTagDouble(String p_i3267_1_, double p_i3267_2_)
	{
		super(p_i3267_1_);
		data = p_i3267_2_;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagDouble(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagDouble var2 = (NBTTagDouble) p_equals_1_;
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
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readDouble();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeDouble(data);
	}
}
