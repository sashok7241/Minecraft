package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		playerEntityId = par1DataInput.readInt();
		targetEntity = par1DataInput.readInt();
		isLeftClick = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(playerEntityId);
		par1DataOutput.writeInt(targetEntity);
		par1DataOutput.writeByte(isLeftClick);
	}
}
