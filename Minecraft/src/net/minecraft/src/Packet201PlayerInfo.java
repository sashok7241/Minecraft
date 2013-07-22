package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		playerName = readString(par1DataInputStream, 16);
		isConnected = par1DataInputStream.readByte() != 0;
		ping = par1DataInputStream.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(playerName, par1DataOutputStream);
		par1DataOutputStream.writeByte(isConnected ? 1 : 0);
		par1DataOutputStream.writeShort(ping);
	}
}
