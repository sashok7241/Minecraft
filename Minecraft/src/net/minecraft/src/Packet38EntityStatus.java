package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet38EntityStatus extends Packet
{
	public int entityId;
	public byte entityStatus;
	
	public Packet38EntityStatus()
	{
	}
	
	public Packet38EntityStatus(int p_i3318_1_, byte p_i3318_2_)
	{
		entityId = p_i3318_1_;
		entityStatus = p_i3318_2_;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityStatus(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		entityStatus = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(entityStatus);
	}
}
