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
	
	public Packet250CustomPayload(String p_i3315_1_, byte[] p_i3315_2_)
	{
		channel = p_i3315_1_;
		data = p_i3315_2_;
		if(p_i3315_2_ != null)
		{
			length = p_i3315_2_.length;
			if(length > 32767) throw new IllegalArgumentException("Payload may not be larger than 32k");
		}
	}
	
	@Override public int getPacketSize()
	{
		return 2 + channel.length() * 2 + 2 + length;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleCustomPayload(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		channel = readString(p_73267_1_, 20);
		length = p_73267_1_.readShort();
		if(length > 0 && length < 32767)
		{
			data = new byte[length];
			p_73267_1_.readFully(data);
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(channel, p_73273_1_);
		p_73273_1_.writeShort((short) length);
		if(data != null)
		{
			p_73273_1_.write(data);
		}
	}
}
