package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet201PlayerInfo extends Packet
{
	public String playerName;
	public boolean isConnected;
	public int ping;
	
	public Packet201PlayerInfo()
	{
	}
	
	public Packet201PlayerInfo(String par1Str, boolean par2, int par3)
	{
		playerName = par1Str;
		isConnected = par2;
		ping = par3;
	}
	
	@Override public int getPacketSize()
	{
		return playerName.length() + 2 + 1 + 2;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handlePlayerInfo(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		playerName = readString(par1DataInput, 16);
		isConnected = par1DataInput.readByte() != 0;
		ping = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(playerName, par1DataOutput);
		par1DataOutput.writeByte(isConnected ? 1 : 0);
		par1DataOutput.writeShort(ping);
	}
}
