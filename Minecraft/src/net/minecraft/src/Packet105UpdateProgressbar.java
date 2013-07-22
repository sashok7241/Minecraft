package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		windowId = par1DataInputStream.readByte();
		progressBar = par1DataInputStream.readShort();
		progressBarValue = par1DataInputStream.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(progressBar);
		par1DataOutputStream.writeShort(progressBarValue);
	}
}
