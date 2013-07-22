package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RConOutputStream
{
	private ByteArrayOutputStream byteArrayOutput;
	private DataOutputStream output;
	
	public RConOutputStream(int p_i3403_1_)
	{
		byteArrayOutput = new ByteArrayOutputStream(p_i3403_1_);
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
	
	public void writeByteArray(byte[] p_72670_1_) throws IOException
	{
		output.write(p_72670_1_, 0, p_72670_1_.length);
	}
	
	public void writeInt(int p_72667_1_) throws IOException
	{
		output.write(p_72667_1_);
	}
	
	public void writeShort(short p_72668_1_) throws IOException
	{
		output.writeShort(Short.reverseBytes(p_72668_1_));
	}
	
	public void writeString(String p_72671_1_) throws IOException
	{
		output.writeBytes(p_72671_1_);
		output.write(0);
	}
}
