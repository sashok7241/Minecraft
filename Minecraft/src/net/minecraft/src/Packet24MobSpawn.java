package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet24MobSpawn extends Packet
{
	public int entityId;
	public int type;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int velocityX;
	public int velocityY;
	public int velocityZ;
	public byte yaw;
	public byte pitch;
	public byte headYaw;
	private DataWatcher metaData;
	private List metadata;
	
	public Packet24MobSpawn()
	{
	}
	
	public Packet24MobSpawn(EntityLiving par1EntityLiving)
	{
		entityId = par1EntityLiving.entityId;
		type = (byte) EntityList.getEntityID(par1EntityLiving);
		xPosition = par1EntityLiving.myEntitySize.multiplyBy32AndRound(par1EntityLiving.posX);
		yPosition = MathHelper.floor_double(par1EntityLiving.posY * 32.0D);
		zPosition = par1EntityLiving.myEntitySize.multiplyBy32AndRound(par1EntityLiving.posZ);
		yaw = (byte) (int) (par1EntityLiving.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (par1EntityLiving.rotationPitch * 256.0F / 360.0F);
		headYaw = (byte) (int) (par1EntityLiving.rotationYawHead * 256.0F / 360.0F);
		double var2 = 3.9D;
		double var4 = par1EntityLiving.motionX;
		double var6 = par1EntityLiving.motionY;
		double var8 = par1EntityLiving.motionZ;
		if(var4 < -var2)
		{
			var4 = -var2;
		}
		if(var6 < -var2)
		{
			var6 = -var2;
		}
		if(var8 < -var2)
		{
			var8 = -var2;
		}
		if(var4 > var2)
		{
			var4 = var2;
		}
		if(var6 > var2)
		{
			var6 = var2;
		}
		if(var8 > var2)
		{
			var8 = var2;
		}
		velocityX = (int) (var4 * 8000.0D);
		velocityY = (int) (var6 * 8000.0D);
		velocityZ = (int) (var8 * 8000.0D);
		metaData = par1EntityLiving.getDataWatcher();
	}
	
	public List getMetadata()
	{
		if(metadata == null)
		{
			metadata = metaData.getAllWatched();
		}
		return metadata;
	}
	
	@Override public int getPacketSize()
	{
		return 26;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleMobSpawn(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		type = par1DataInputStream.readByte() & 255;
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		yaw = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
		headYaw = par1DataInputStream.readByte();
		velocityX = par1DataInputStream.readShort();
		velocityY = par1DataInputStream.readShort();
		velocityZ = par1DataInputStream.readShort();
		metadata = DataWatcher.readWatchableObjects(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(type & 255);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte(yaw);
		par1DataOutputStream.writeByte(pitch);
		par1DataOutputStream.writeByte(headYaw);
		par1DataOutputStream.writeShort(velocityX);
		par1DataOutputStream.writeShort(velocityY);
		par1DataOutputStream.writeShort(velocityZ);
		metaData.writeWatchableObjects(par1DataOutputStream);
	}
}
