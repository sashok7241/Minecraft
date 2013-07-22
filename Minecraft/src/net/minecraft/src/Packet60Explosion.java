package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Packet60Explosion extends Packet
{
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public float explosionSize;
	public List chunkPositionRecords;
	private float playerVelocityX;
	private float playerVelocityY;
	private float playerVelocityZ;
	
	public Packet60Explosion()
	{
	}
	
	public Packet60Explosion(double p_i3319_1_, double p_i3319_3_, double p_i3319_5_, float p_i3319_7_, List p_i3319_8_, Vec3 p_i3319_9_)
	{
		explosionX = p_i3319_1_;
		explosionY = p_i3319_3_;
		explosionZ = p_i3319_5_;
		explosionSize = p_i3319_7_;
		chunkPositionRecords = new ArrayList(p_i3319_8_);
		if(p_i3319_9_ != null)
		{
			playerVelocityX = (float) p_i3319_9_.xCoord;
			playerVelocityY = (float) p_i3319_9_.yCoord;
			playerVelocityZ = (float) p_i3319_9_.zCoord;
		}
	}
	
	@Override public int getPacketSize()
	{
		return 32 + chunkPositionRecords.size() * 3 + 3;
	}
	
	public float getPlayerVelocityX()
	{
		return playerVelocityX;
	}
	
	public float getPlayerVelocityY()
	{
		return playerVelocityY;
	}
	
	public float getPlayerVelocityZ()
	{
		return playerVelocityZ;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleExplosion(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		explosionX = p_73267_1_.readDouble();
		explosionY = p_73267_1_.readDouble();
		explosionZ = p_73267_1_.readDouble();
		explosionSize = p_73267_1_.readFloat();
		int var2 = p_73267_1_.readInt();
		chunkPositionRecords = new ArrayList(var2);
		int var3 = (int) explosionX;
		int var4 = (int) explosionY;
		int var5 = (int) explosionZ;
		for(int var6 = 0; var6 < var2; ++var6)
		{
			int var7 = p_73267_1_.readByte() + var3;
			int var8 = p_73267_1_.readByte() + var4;
			int var9 = p_73267_1_.readByte() + var5;
			chunkPositionRecords.add(new ChunkPosition(var7, var8, var9));
		}
		playerVelocityX = p_73267_1_.readFloat();
		playerVelocityY = p_73267_1_.readFloat();
		playerVelocityZ = p_73267_1_.readFloat();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeDouble(explosionX);
		p_73273_1_.writeDouble(explosionY);
		p_73273_1_.writeDouble(explosionZ);
		p_73273_1_.writeFloat(explosionSize);
		p_73273_1_.writeInt(chunkPositionRecords.size());
		int var2 = (int) explosionX;
		int var3 = (int) explosionY;
		int var4 = (int) explosionZ;
		Iterator var5 = chunkPositionRecords.iterator();
		while(var5.hasNext())
		{
			ChunkPosition var6 = (ChunkPosition) var5.next();
			int var7 = var6.x - var2;
			int var8 = var6.y - var3;
			int var9 = var6.z - var4;
			p_73273_1_.writeByte(var7);
			p_73273_1_.writeByte(var8);
			p_73273_1_.writeByte(var9);
		}
		p_73273_1_.writeFloat(playerVelocityX);
		p_73273_1_.writeFloat(playerVelocityY);
		p_73273_1_.writeFloat(playerVelocityZ);
	}
}
