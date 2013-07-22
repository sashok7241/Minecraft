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
	
	public Packet7UseEntity(int par1, int par2, int par3)
	{
		playerEntityId = par1;
		targetEntity = par2;
		isLeftClick = par3;
	}
	
	@Override public int getPacketSize()
	{
		return 9;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleUseEntity(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		playerEntityId = par1DataInputStream.readInt();
		targetEntity = par1DataInputStream.readInt();
		isLeftClick = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(playerEntityId);
		par1DataOutputStream.writeInt(targetEntity);
		par1DataOutputStream.writeByte(isLeftClick);
	}
}
