package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet205ClientCommand extends Packet
{
	public int forceRespawn;
	
	public Packet205ClientCommand()
	{
	}
	
	public Packet205ClientCommand(int p_i3303_1_)
	{
		forceRespawn = p_i3303_1_;
	}
	
	@Override public int getPacketSize()
	{
		return 1;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleClientCommand(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		forceRespawn = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(forceRespawn & 255);
	}
}
