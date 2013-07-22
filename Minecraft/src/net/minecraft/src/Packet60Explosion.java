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
	
	public Packet60Explosion(double par1, double par3, double par5, float par7, List par8List, Vec3 par9Vec3)
	{
		explosionX = par1;
		explosionY = par3;
		explosionZ = par5;
		explosionSize = par7;
		chunkPositionRecords = new ArrayList(par8List);
		if(par9Vec3 != null)
		{
			playerVelocityX = (float) par9Vec3.xCoord;
			playerVelocityY = (float) par9Vec3.yCoord;
			playerVelocityZ = (float) par9Vec3.zCoord;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleExplosion(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		explosionX = par1DataInputStream.readDouble();
		explosionY = par1DataInputStream.readDouble();
		explosionZ = par1DataInputStream.readDouble();
		explosionSize = par1DataInputStream.readFloat();
		int var2 = par1DataInputStream.readInt();
		chunkPositionRecords = new ArrayList(var2);
		int var3 = (int) explosionX;
		int var4 = (int) explosionY;
		int var5 = (int) explosionZ;
		for(int var6 = 0; var6 < var2; ++var6)
		{
			int var7 = par1DataInputStream.readByte() + var3;
			int var8 = par1DataInputStream.readByte() + var4;
			int var9 = par1DataInputStream.readByte() + var5;
			chunkPositionRecords.add(new ChunkPosition(var7, var8, var9));
		}
		playerVelocityX = par1DataInputStream.readFloat();
		playerVelocityY = par1DataInputStream.readFloat();
		playerVelocityZ = par1DataInputStream.readFloat();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeDouble(explosionX);
		par1DataOutputStream.writeDouble(explosionY);
		par1DataOutputStream.writeDouble(explosionZ);
		par1DataOutputStream.writeFloat(explosionSize);
		par1DataOutputStream.writeInt(chunkPositionRecords.size());
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
			par1DataOutputStream.writeByte(var7);
			par1DataOutputStream.writeByte(var8);
			par1DataOutputStream.writeByte(var9);
		}
		par1DataOutputStream.writeFloat(playerVelocityX);
		par1DataOutputStream.writeFloat(playerVelocityY);
		par1DataOutputStream.writeFloat(playerVelocityZ);
	}
}
