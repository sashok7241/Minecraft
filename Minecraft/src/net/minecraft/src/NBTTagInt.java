package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase
{
	public int data;
	
	public NBTTagInt(String p_i3272_1_)
	{
		super(p_i3272_1_);
	}
	
	public NBTTagInt(String p_i3273_1_, int p_i3273_2_)
	{
		super(p_i3273_1_);
		data = p_i3273_2_;
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagInt(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(super.equals(p_equals_1_))
		{
			NBTTagInt var2 = (NBTTagInt) p_equals_1_;
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
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readInt();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeInt(data);
	}
}
