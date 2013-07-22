package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase
{
	public String data;
	
	public NBTTagString(String p_i3279_1_)
	{
		super(p_i3279_1_);
	}
	
	public NBTTagString(String p_i3280_1_, String p_i3280_2_)
	{
		super(p_i3280_1_);
		data = p_i3280_2_;
		if(p_i3280_2_ == null) throw new IllegalArgumentException("Empty string not allowed");
	}
	
	@Override public NBTBase copy()
	{
		return new NBTTagString(getName(), data);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!super.equals(p_equals_1_)) return false;
		else
		{
			NBTTagString var2 = (NBTTagString) p_equals_1_;
			return data == null && var2.data == null || data != null && data.equals(var2.data);
		}
	}
	
	@Override public byte getId()
	{
		return (byte) 8;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ data.hashCode();
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		data = p_74735_1_.readUTF();
	}
	
	@Override public String toString()
	{
		return "" + data;
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeUTF(data);
	}
}
