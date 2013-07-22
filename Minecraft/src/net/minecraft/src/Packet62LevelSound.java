package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet62LevelSound extends Packet
{
	private String soundName;
	private int effectX;
	private int effectY = Integer.MAX_VALUE;
	private int effectZ;
	private float volume;
	private int pitch;
	
	public Packet62LevelSound()
	{
	}
	
	public Packet62LevelSound(String p_i3326_1_, double p_i3326_2_, double p_i3326_4_, double p_i3326_6_, float p_i3326_8_, float p_i3326_9_)
	{
		soundName = p_i3326_1_;
		effectX = (int) (p_i3326_2_ * 8.0D);
		effectY = (int) (p_i3326_4_ * 8.0D);
		effectZ = (int) (p_i3326_6_ * 8.0D);
		volume = p_i3326_8_;
		pitch = (int) (p_i3326_9_ * 63.0F);
		if(pitch < 0)
		{
			pitch = 0;
		}
		if(pitch > 255)
		{
			pitch = 255;
		}
	}
	
	public double getEffectX()
	{
		return effectX / 8.0F;
	}
	
	public double getEffectY()
	{
		return effectY / 8.0F;
	}
	
	public double getEffectZ()
	{
		return effectZ / 8.0F;
	}
	
	@Override public int getPacketSize()
	{
		return 24;
	}
	
	public float getPitch()
	{
		return pitch / 63.0F;
	}
	
	public String getSoundName()
	{
		return soundName;
	}
	
	public float getVolume()
	{
		return volume;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleLevelSound(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		soundName = readString(p_73267_1_, 32);
		effectX = p_73267_1_.readInt();
		effectY = p_73267_1_.readInt();
		effectZ = p_73267_1_.readInt();
		volume = p_73267_1_.readFloat();
		pitch = p_73267_1_.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(soundName, p_73273_1_);
		p_73273_1_.writeInt(effectX);
		p_73273_1_.writeInt(effectY);
		p_73273_1_.writeInt(effectZ);
		p_73273_1_.writeFloat(volume);
		p_73273_1_.writeByte(pitch);
	}
}
