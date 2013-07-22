package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet0KeepAlive extends Packet
{
	public int randomId;
	
	public Packet0KeepAlive()
	{
	}
	
	public Packet0KeepAlive(int par1)
	{
		randomId = par1;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 4;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleKeepAlive(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		randomId = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(randomId);
	}
}
