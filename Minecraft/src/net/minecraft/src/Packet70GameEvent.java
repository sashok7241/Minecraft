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
	
	public Packet70GameEvent(int p_i3320_1_, int p_i3320_2_)
	{
		eventType = p_i3320_1_;
		gameMode = p_i3320_2_;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleGameEvent(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		eventType = p_73267_1_.readByte();
		gameMode = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(eventType);
		p_73273_1_.writeByte(gameMode);
	}
}
