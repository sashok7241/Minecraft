package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		soundName = readString(par1DataInput, 256);
		effectX = par1DataInput.readInt();
		effectY = par1DataInput.readInt();
		effectZ = par1DataInput.readInt();
		volume = par1DataInput.readFloat();
		pitch = par1DataInput.readUnsignedByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(soundName, par1DataOutput);
		par1DataOutput.writeInt(effectX);
		par1DataOutput.writeInt(effectY);
		par1DataOutput.writeInt(effectZ);
		par1DataOutput.writeFloat(volume);
		par1DataOutput.writeByte(pitch);
	}
}
