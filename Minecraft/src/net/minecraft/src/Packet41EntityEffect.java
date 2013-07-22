package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	public Packet41EntityEffect(int p_i3365_1_, PotionEffect p_i3365_2_)
	{
		entityId = p_i3365_1_;
		effectId = (byte) (p_i3365_2_.getPotionID() & 255);
		effectAmplifier = (byte) (p_i3365_2_.getAmplifier() & 255);
		if(p_i3365_2_.getDuration() > 32767)
		{
			duration = 32767;
		} else
		{
			duration = (short) p_i3365_2_.getDuration();
		}
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet41EntityEffect var2 = (Packet41EntityEffect) p_73268_1_;
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityEffect(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		effectId = p_73267_1_.readByte();
		effectAmplifier = p_73267_1_.readByte();
		duration = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(effectId);
		p_73273_1_.writeByte(effectAmplifier);
		p_73273_1_.writeShort(duration);
	}
}
