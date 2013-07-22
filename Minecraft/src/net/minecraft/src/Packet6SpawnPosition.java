package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(yPosition);
		par1DataOutput.writeInt(zPosition);
	}
}
