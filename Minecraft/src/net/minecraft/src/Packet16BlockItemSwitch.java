package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet16BlockItemSwitch extends Packet
{
	public int id;
	
	public Packet16BlockItemSwitch()
	{
	}
	
	public Packet16BlockItemSwitch(int par1)
	{
		id = par1;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleBlockItemSwitch(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		id = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeShort(id);
	}
}
