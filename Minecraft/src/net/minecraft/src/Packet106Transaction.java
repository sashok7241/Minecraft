package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		windowId = par1DataInputStream.readByte();
		shortWindowId = par1DataInputStream.readShort();
		accepted = par1DataInputStream.readByte() != 0;
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(shortWindowId);
		par1DataOutputStream.writeByte(accepted ? 1 : 0);
	}
}
