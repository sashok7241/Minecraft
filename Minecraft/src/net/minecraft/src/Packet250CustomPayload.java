package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet250CustomPayload extends Packet
{
	public String channel;
	public int length;
	public byte[] data;
	
	public Packet250CustomPayload()
	{
	}
	
	public Packet250CustomPayload(String par1Str, byte[] par2ArrayOfByte)
	{
		channel = par1Str;
		data = par2ArrayOfByte;
		if(par2ArrayOfByte != null)
		{
			length = par2ArrayOfByte.length;
			if(length > 32767) throw new IllegalArgumentException("Payload may not be larger than 32k");
		}
	}
	
	@Override public int getPacketSize()
	{
		return 2 + channel.length() * 2 + 2 + length;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleCustomPayload(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		channel = readString(par1DataInputStream, 20);
		length = par1DataInputStream.readShort();
		if(length > 0 && length < 32767)
		{
			data = new byte[length];
			par1DataInputStream.readFully(data);
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(channel, par1DataOutputStream);
		par1DataOutputStream.writeShort((short) length);
		if(data != null)
		{
			par1DataOutputStream.write(data);
		}
	}
}
