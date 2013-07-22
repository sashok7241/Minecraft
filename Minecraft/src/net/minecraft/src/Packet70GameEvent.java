package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet70GameEvent extends Packet
{
	public static final String[] clientMessage = new String[] { "tile.bed.notValid", null, null, "gameMode.changed" };
	public int eventType;
	public int gameMode;
	
	public Packet70GameEvent()
	{
	}
	
	public Packet70GameEvent(int par1, int par2)
	{
		eventType = par1;
		gameMode = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleGameEvent(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		eventType = par1DataInput.readByte();
		gameMode = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(eventType);
		par1DataOutput.writeByte(gameMode);
	}
}
