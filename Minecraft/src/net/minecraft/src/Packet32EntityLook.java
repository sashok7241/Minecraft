package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet32EntityLook extends Packet30Entity
{
	public Packet32EntityLook()
	{
		rotating = true;
	}
	
	public Packet32EntityLook(int par1, byte par2, byte par3)
	{
		super(par1);
		yaw = par2;
		pitch = par3;
		rotating = true;
	}
	
	@Override public int getPacketSize()
	{
		return 6;
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		super.readPacketData(par1DataInputStream);
		yaw = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		super.writePacketData(par1DataOutputStream);
		par1DataOutputStream.writeByte(yaw);
		par1DataOutputStream.writeByte(pitch);
	}
}
