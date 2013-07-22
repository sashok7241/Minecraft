package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet61DoorChange(int par1, int par2, int par3, int par4, int par5, boolean par6)
	{
		sfxID = par1;
		posX = par2;
		posY = par3;
		posZ = par4;
		auxData = par5;
		disableRelativeVolume = par6;
	}
	
	@Override public int getPacketSize()
	{
		return 21;
	}
	
	public boolean getRelativeVolumeDisabled()
	{
		return disableRelativeVolume;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleDoorChange(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		sfxID = par1DataInput.readInt();
		posX = par1DataInput.readInt();
		posY = par1DataInput.readByte() & 255;
		posZ = par1DataInput.readInt();
		auxData = par1DataInput.readInt();
		disableRelativeVolume = par1DataInput.readBoolean();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(sfxID);
		par1DataOutput.writeInt(posX);
		par1DataOutput.writeByte(posY & 255);
		par1DataOutput.writeInt(posZ);
		par1DataOutput.writeInt(auxData);
		par1DataOutput.writeBoolean(disableRelativeVolume);
	}
}
