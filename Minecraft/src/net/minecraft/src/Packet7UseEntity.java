package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet7UseEntity extends Packet
{
	public int playerEntityId;
	public int targetEntity;
	public int isLeftClick;
	
	public Packet7UseEntity()
	{
	}
	
	public Packet7UseEntity(int p_i3321_1_, int p_i3321_2_, int p_i3321_3_)
	{
		playerEntityId = p_i3321_1_;
		targetEntity = p_i3321_2_;
		isLeftClick = p_i3321_3_;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleUseEntity(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		playerEntityId = p_73267_1_.readInt();
		targetEntity = p_73267_1_.readInt();
		isLeftClick = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(playerEntityId);
		p_73273_1_.writeInt(targetEntity);
		p_73273_1_.writeByte(isLeftClick);
	}
}
