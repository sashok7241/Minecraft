package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase
{
	public byte[] byteArray;
	
	public NBTTagByteArray(String p_i3261_1_)
	{
		super(p_i3261_1_);
	}
	
	public NBTTagByteArray(String p_i3262_1_, byte[] p_i3262_2_)
	{
		super(p_i3262_1_);
		byteArray = p_i3262_2_;
	}
	
	@Override public NBTBase copy()
	{
		byte[] var1 = new byte[byteArray.length];
		System.arraycopy(byteArray, 0, var1, 0, byteArray.length);
		return new NBTTagByteArray(getName(), var1);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		return super.equals(p_equals_1_) ? Arrays.equals(byteArray, ((NBTTagByteArray) p_equals_1_).byteArray) : false;
	}
	
	@Override public byte getId()
	{
		return (byte) 7;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ Arrays.hashCode(byteArray);
	}
	
	@Override void load(DataInput p_74735_1_) throws IOException
	{
		int var2 = p_74735_1_.readInt();
		byteArray = new byte[var2];
		p_74735_1_.readFully(byteArray);
	}
	
	@Override public String toString()
	{
		return "[" + byteArray.length + " bytes]";
	}
	
	@Override void write(DataOutput p_74734_1_) throws IOException
	{
		p_74734_1_.writeInt(byteArray.length);
		p_74734_1_.write(byteArray);
	}
}
