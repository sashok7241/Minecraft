package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet61DoorChange extends Packet
{
	public int sfxID;
	public int auxData;
	public int posX;
	public int posY;
	public int posZ;
	private boolean disableRelativeVolume;
	
	public Packet61DoorChange()
	{
	}
	
	public Packet61DoorChange(int p_i5033_1_, int p_i5033_2_, int p_i5033_3_, int p_i5033_4_, int p_i5033_5_, boolean p_i5033_6_)
	{
		sfxID = p_i5033_1_;
		posX = p_i5033_2_;
		posY = p_i5033_3_;
		posZ = p_i5033_4_;
		auxData = p_i5033_5_;
		disableRelativeVolume = p_i5033_6_;
	}
	
	@Override public int getPacketSize()
	{
		return 21;
	}
	
	public boolean getRelativeVolumeDisabled()
	{
		return disableRelativeVolume;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleDoorChange(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		sfxID = p_73267_1_.readInt();
		posX = p_73267_1_.readInt();
		posY = p_73267_1_.readByte() & 255;
		posZ = p_73267_1_.readInt();
		auxData = p_73267_1_.readInt();
		disableRelativeVolume = p_73267_1_.readBoolean();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(sfxID);
		p_73273_1_.writeInt(posX);
		p_73273_1_.writeByte(posY & 255);
		p_73273_1_.writeInt(posZ);
		p_73273_1_.writeInt(auxData);
		p_73273_1_.writeBoolean(disableRelativeVolume);
	}
}
