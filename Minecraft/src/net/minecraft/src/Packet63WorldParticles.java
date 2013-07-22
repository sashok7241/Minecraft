package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		particleName = readString(par1DataInput, 64);
		posX = par1DataInput.readFloat();
		posY = par1DataInput.readFloat();
		posZ = par1DataInput.readFloat();
		offsetX = par1DataInput.readFloat();
		offsetY = par1DataInput.readFloat();
		offsetZ = par1DataInput.readFloat();
		speed = par1DataInput.readFloat();
		quantity = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(particleName, par1DataOutput);
		par1DataOutput.writeFloat(posX);
		par1DataOutput.writeFloat(posY);
		par1DataOutput.writeFloat(posZ);
		par1DataOutput.writeFloat(offsetX);
		par1DataOutput.writeFloat(offsetY);
		par1DataOutput.writeFloat(offsetZ);
		par1DataOutput.writeFloat(speed);
		par1DataOutput.writeInt(quantity);
	}
}
