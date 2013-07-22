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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		sfxID = par1DataInputStream.readInt();
		posX = par1DataInputStream.readInt();
		posY = par1DataInputStream.readByte() & 255;
		posZ = par1DataInputStream.readInt();
		auxData = par1DataInputStream.readInt();
		disableRelativeVolume = par1DataInputStream.readBoolean();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(sfxID);
		par1DataOutputStream.writeInt(posX);
		par1DataOutputStream.writeByte(posY & 255);
		par1DataOutputStream.writeInt(posZ);
		par1DataOutputStream.writeInt(auxData);
		par1DataOutputStream.writeBoolean(disableRelativeVolume);
	}
}
