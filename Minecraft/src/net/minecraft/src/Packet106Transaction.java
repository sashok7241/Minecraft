package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet106Transaction extends Packet
{
	public int windowId;
	public short shortWindowId;
	public boolean accepted;
	
	public Packet106Transaction()
	{
	}
	
	public Packet106Transaction(int par1, short par2, boolean par3)
	{
		windowId = par1;
		shortWindowId = par2;
		accepted = par3;
	}
	
	@Override public int getPacketSize()
	{
		return 4;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleTransaction(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		windowId = par1DataInput.readByte();
		shortWindowId = par1DataInput.readShort();
		accepted = par1DataInput.readByte() != 0;
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(windowId);
		par1DataOutput.writeShort(shortWindowId);
		par1DataOutput.writeByte(accepted ? 1 : 0);
	}
}
