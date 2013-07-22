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
	
	public Packet201PlayerInfo(String p_i3339_1_, boolean p_i3339_2_, int p_i3339_3_)
	{
		playerName = p_i3339_1_;
		isConnected = p_i3339_2_;
		ping = p_i3339_3_;
	}
	
	@Override public int getPacketSize()
	{
		return playerName.length() + 2 + 1 + 2;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handlePlayerInfo(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		playerName = readString(p_73267_1_, 16);
		isConnected = p_73267_1_.readByte() != 0;
		ping = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(playerName, p_73273_1_);
		p_73273_1_.writeByte(isConnected ? 1 : 0);
		p_73273_1_.writeShort(ping);
	}
}
