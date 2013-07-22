package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet101CloseWindow extends Packet
{
	public int windowId;
	
	public Packet101CloseWindow()
	{
	}
	
	public Packet101CloseWindow(int par1)
	{
		windowId = par1;
	}
	
	@Override public int getPacketSize()
	{
		return 1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleCloseWindow(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		windowId = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(windowId);
	}
}
