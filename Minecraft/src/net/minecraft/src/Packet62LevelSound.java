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
	
	public Packet62LevelSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
	{
		soundName = par1Str;
		effectX = (int) (par2 * 8.0D);
		effectY = (int) (par4 * 8.0D);
		effectZ = (int) (par6 * 8.0D);
		volume = par8;
		pitch = (int) (par9 * 63.0F);
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleLevelSound(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		soundName = readString(par1DataInputStream, 32);
		effectX = par1DataInputStream.readInt();
		effectY = par1DataInputStream.readInt();
		effectZ = par1DataInputStream.readInt();
		volume = par1DataInputStream.readFloat();
		pitch = par1DataInputStream.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(soundName, par1DataOutputStream);
		par1DataOutputStream.writeInt(effectX);
		par1DataOutputStream.writeInt(effectY);
		par1DataOutputStream.writeInt(effectZ);
		par1DataOutputStream.writeFloat(volume);
		par1DataOutputStream.writeByte(pitch);
	}
}
