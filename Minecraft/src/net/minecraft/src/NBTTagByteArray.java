package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase
{
	public byte[] byteArray;
	
	public NBTTagByteArray(String par1Str)
	{
		super(par1Str);
	}
	
	public NBTTagByteArray(String par1Str, byte[] par2ArrayOfByte)
	{
		super(par1Str);
		byteArray = par2ArrayOfByte;
	}
	
	@Override public NBTBase copy()
	{
		byte[] var1 = new byte[byteArray.length];
		System.arraycopy(byteArray, 0, var1, 0, byteArray.length);
		return new NBTTagByteArray(getName(), var1);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		return super.equals(par1Obj) ? Arrays.equals(byteArray, ((NBTTagByteArray) par1Obj).byteArray) : false;
	}
	
	@Override public byte getId()
	{
		return (byte) 7;
	}
	
	@Override public int hashCode()
	{
		return super.hashCode() ^ Arrays.hashCode(byteArray);
	}
	
	@Override void load(DataInput par1DataInput) throws IOException
	{
		int var2 = par1DataInput.readInt();
		byteArray = new byte[var2];
		par1DataInput.readFully(byteArray);
	}
	
	@Override public String toString()
	{
		return "[" + byteArray.length + " bytes]";
	}
	
	@Override void write(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(byteArray.length);
		par1DataOutput.write(byteArray);
	}
}
