package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		collectedEntityId = par1DataInput.readInt();
		collectorEntityId = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(collectedEntityId);
		par1DataOutput.writeInt(collectorEntityId);
	}
}
