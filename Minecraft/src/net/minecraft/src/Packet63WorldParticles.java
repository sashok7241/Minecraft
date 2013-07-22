package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet63WorldParticles extends Packet
{
	private String particleName;
	private float posX;
	private float posY;
	private float posZ;
	private float offsetX;
	private float offsetY;
	private float offsetZ;
	private float speed;
	private int quantity;
	
	public float getOffsetX()
	{
		return offsetX;
	}
	
	public float getOffsetY()
	{
		return offsetY;
	}
	
	public float getOffsetZ()
	{
		return offsetZ;
	}
	
	@Override public int getPacketSize()
	{
		return 64;
	}
	
	public String getParticleName()
	{
		return particleName;
	}
	
	public double getPositionX()
	{
		return posX;
	}
	
	public double getPositionY()
	{
		return posY;
	}
	
	public double getPositionZ()
	{
		return posZ;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public float getSpeed()
	{
		return speed;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleWorldParticles(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		particleName = readString(par1DataInputStream, 64);
		posX = par1DataInputStream.readFloat();
		posY = par1DataInputStream.readFloat();
		posZ = par1DataInputStream.readFloat();
		offsetX = par1DataInputStream.readFloat();
		offsetY = par1DataInputStream.readFloat();
		offsetZ = par1DataInputStream.readFloat();
		speed = par1DataInputStream.readFloat();
		quantity = par1DataInputStream.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(particleName, par1DataOutputStream);
		par1DataOutputStream.writeFloat(posX);
		par1DataOutputStream.writeFloat(posY);
		par1DataOutputStream.writeFloat(posZ);
		par1DataOutputStream.writeFloat(offsetX);
		par1DataOutputStream.writeFloat(offsetY);
		par1DataOutputStream.writeFloat(offsetZ);
		par1DataOutputStream.writeFloat(speed);
		par1DataOutputStream.writeInt(quantity);
	}
}
