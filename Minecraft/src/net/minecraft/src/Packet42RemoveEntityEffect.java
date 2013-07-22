package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet42RemoveEntityEffect extends Packet
{
	public int entityId;
	public byte effectId;
	
	public Packet42RemoveEntityEffect()
	{
	}
	
	public Packet42RemoveEntityEffect(int par1, PotionEffect par2PotionEffect)
	{
		entityId = par1;
		effectId = (byte) (par2PotionEffect.getPotionID() & 255);
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleRemoveEntityEffect(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		effectId = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(effectId);
	}
}
