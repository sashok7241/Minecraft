package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RConOutputStream
{
	private ByteArrayOutputStream byteArrayOutput;
	private DataOutputStream output;
	
	public RConOutputStream(int par1)
	{
		byteArrayOutput = new ByteArrayOutputStream(par1);
		output = new DataOutputStream(byteArrayOutput);
	}
	
	public void reset()
	{
		byteArrayOutput.reset();
	}
	
	public byte[] toByteArray()
	{
		return byteArrayOutput.toByteArray();
	}
	
	public void writeByteArray(byte[] par1ArrayOfByte) throws IOException
	{
		output.write(par1ArrayOfByte, 0, par1ArrayOfByte.length);
	}
	
	public void writeInt(int par1) throws IOException
	{
		output.write(par1);
	}
	
	public void writeShort(short par1) throws IOException
	{
		output.writeShort(Short.reverseBytes(par1));
	}
	
	public void writeString(String par1Str) throws IOException
	{
		output.writeBytes(par1Str);
		output.write(0);
	}
}
