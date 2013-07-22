package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet205ClientCommand extends Packet
{
	public int forceRespawn;
	
	public Packet205ClientCommand()
	{
	}
	
	public Packet205ClientCommand(int par1)
	{
		forceRespawn = par1;
	}
	
	@Override public int getPacketSize()
	{
		return 1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleClientCommand(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		forceRespawn = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(forceRespawn & 255);
	}
}
