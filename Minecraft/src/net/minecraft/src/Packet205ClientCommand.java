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
	
	public Packet205ClientCommand(int par1)
	{
		forceRespawn = par1;
	}
	
	@Override public int getPacketSize()
	{
		return 1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleClientCommand(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		forceRespawn = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(forceRespawn & 255);
	}
}
