package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		explosionX = par1DataInput.readDouble();
		explosionY = par1DataInput.readDouble();
		explosionZ = par1DataInput.readDouble();
		explosionSize = par1DataInput.readFloat();
		int var2 = par1DataInput.readInt();
		chunkPositionRecords = new ArrayList(var2);
		int var3 = (int) explosionX;
		int var4 = (int) explosionY;
		int var5 = (int) explosionZ;
		for(int var6 = 0; var6 < var2; ++var6)
		{
			int var7 = par1DataInput.readByte() + var3;
			int var8 = par1DataInput.readByte() + var4;
			int var9 = par1DataInput.readByte() + var5;
			chunkPositionRecords.add(new ChunkPosition(var7, var8, var9));
		}
		playerVelocityX = par1DataInput.readFloat();
		playerVelocityY = par1DataInput.readFloat();
		playerVelocityZ = par1DataInput.readFloat();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeDouble(explosionX);
		par1DataOutput.writeDouble(explosionY);
		par1DataOutput.writeDouble(explosionZ);
		par1DataOutput.writeFloat(explosionSize);
		par1DataOutput.writeInt(chunkPositionRecords.size());
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
			par1DataOutput.writeByte(var7);
			par1DataOutput.writeByte(var8);
			par1DataOutput.writeByte(var9);
		}
		par1DataOutput.writeFloat(playerVelocityX);
		par1DataOutput.writeFloat(playerVelocityY);
		par1DataOutput.writeFloat(playerVelocityZ);
	}
}
