package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet18Animation extends Packet
{
	public int entityId;
	public int animate;
	
	public Packet18Animation()
	{
	}
	
	public Packet18Animation(Entity par1Entity, int par2)
	{
		entityId = par1Entity.entityId;
		animate = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleAnimation(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		animate = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(animate);
	}
}
