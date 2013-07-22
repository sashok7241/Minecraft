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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleWorldParticles(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		particleName = readString(p_73267_1_, 64);
		posX = p_73267_1_.readFloat();
		posY = p_73267_1_.readFloat();
		posZ = p_73267_1_.readFloat();
		offsetX = p_73267_1_.readFloat();
		offsetY = p_73267_1_.readFloat();
		offsetZ = p_73267_1_.readFloat();
		speed = p_73267_1_.readFloat();
		quantity = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(particleName, p_73273_1_);
		p_73273_1_.writeFloat(posX);
		p_73273_1_.writeFloat(posY);
		p_73273_1_.writeFloat(posZ);
		p_73273_1_.writeFloat(offsetX);
		p_73273_1_.writeFloat(offsetY);
		p_73273_1_.writeFloat(offsetZ);
		p_73273_1_.writeFloat(speed);
		p_73273_1_.writeInt(quantity);
	}
}
