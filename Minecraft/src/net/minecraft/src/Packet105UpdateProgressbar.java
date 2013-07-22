package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet105UpdateProgressbar extends Packet
{
	public int windowId;
	public int progressBar;
	public int progressBarValue;
	
	public Packet105UpdateProgressbar()
	{
	}
	
	public Packet105UpdateProgressbar(int par1, int par2, int par3)
	{
		windowId = par1;
		progressBar = par2;
		progressBarValue = par3;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleUpdateProgressbar(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		windowId = par1DataInput.readByte();
		progressBar = par1DataInput.readShort();
		progressBarValue = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(windowId);
		par1DataOutput.writeShort(progressBar);
		par1DataOutput.writeShort(progressBarValue);
	}
}
