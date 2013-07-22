package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet22Collect extends Packet
{
	public int collectedEntityId;
	public int collectorEntityId;
	
	public Packet22Collect()
	{
	}
	
	public Packet22Collect(int par1, int par2)
	{
		collectedEntityId = par1;
		collectorEntityId = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleCollect(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		collectedEntityId = par1DataInputStream.readInt();
		collectorEntityId = par1DataInputStream.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(collectedEntityId);
		par1DataOutputStream.writeInt(collectorEntityId);
	}
}
