package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet35EntityHeadRotation extends Packet
{
	public int entityId;
	public byte headRotationYaw;
	
	public Packet35EntityHeadRotation()
	{
	}
	
	public Packet35EntityHeadRotation(int p_i3343_1_, byte p_i3343_2_)
	{
		entityId = p_i3343_1_;
		headRotationYaw = p_i3343_2_;
	}
	
	@Override public boolean canProcessAsync()
	{
		return true;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet35EntityHeadRotation var2 = (Packet35EntityHeadRotation) p_73268_1_;
		return var2.entityId == entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityHeadRotation(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		headRotationYaw = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(headRotationYaw);
	}
}
