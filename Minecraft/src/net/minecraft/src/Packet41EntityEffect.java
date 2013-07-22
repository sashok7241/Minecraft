package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet41EntityEffect extends Packet
{
	public int entityId;
	public byte effectId;
	public byte effectAmplifier;
	public short duration;
	
	public Packet41EntityEffect()
	{
	}
	
	public Packet41EntityEffect(int par1, PotionEffect par2PotionEffect)
	{
		entityId = par1;
		effectId = (byte) (par2PotionEffect.getPotionID() & 255);
		effectAmplifier = (byte) (par2PotionEffect.getAmplifier() & 255);
		if(par2PotionEffect.getDuration() > 32767)
		{
			duration = 32767;
		} else
		{
			duration = (short) par2PotionEffect.getDuration();
		}
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet41EntityEffect var2 = (Packet41EntityEffect) par1Packet;
		return var2.entityId == entityId && var2.effectId == effectId;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	public boolean isDurationMax()
	{
		return duration == 32767;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityEffect(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		effectId = par1DataInput.readByte();
		effectAmplifier = par1DataInput.readByte();
		duration = par1DataInput.readShort();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(effectId);
		par1DataOutput.writeByte(effectAmplifier);
		par1DataOutput.writeShort(duration);
	}
}
