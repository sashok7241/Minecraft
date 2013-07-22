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
	
	public Packet6SpawnPosition(int p_i3354_1_, int p_i3354_2_, int p_i3354_3_)
	{
		xPosition = p_i3354_1_;
		yPosition = p_i3354_2_;
		zPosition = p_i3354_3_;
	}
	
	@Override public boolean canProcessAsync()
	{
		return false;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSpawnPosition(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(yPosition);
		p_73273_1_.writeInt(zPosition);
	}
}
