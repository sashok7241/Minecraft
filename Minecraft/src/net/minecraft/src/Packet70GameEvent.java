package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		eventType = par1DataInputStream.readByte();
		gameMode = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(eventType);
		par1DataOutputStream.writeByte(gameMode);
	}
}
