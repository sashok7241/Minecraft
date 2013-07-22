package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		effectId = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(effectId);
	}
}
