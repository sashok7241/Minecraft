package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase
{
	public long data;
	
	public NBTTagLong(String p_i3275_1_)
	{
		super(p_i3275_1_);
	}
	
	public NBTTagLong(String p_i3276_1_, long p_i3276_2_)
	{
		super(p_i3276_1_);
		data = p_i3276_2_;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagLong(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagLong var2 = (NBTTagLong) p_equals_1_;
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
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readLong();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeLong(data);
	}
}
