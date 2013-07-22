package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet6SpawnPosition extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	
	public Packet6SpawnPosition()
	{
	}
	
	public Packet6SpawnPosition(int par1, int par2, int par3)
	{
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
	}
	
	@Override public boolean canProcessAsync()
	{
		return false;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 12;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSpawnPosition(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
	}
}
