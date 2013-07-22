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
	
	public Packet24MobSpawn(EntityLiving p_i3294_1_)
	{
		entityId = p_i3294_1_.entityId;
		type = (byte) EntityList.getEntityID(p_i3294_1_);
		xPosition = p_i3294_1_.myEntitySize.multiplyBy32AndRound(p_i3294_1_.posX);
		yPosition = MathHelper.floor_double(p_i3294_1_.posY * 32.0D);
		zPosition = p_i3294_1_.myEntitySize.multiplyBy32AndRound(p_i3294_1_.posZ);
		yaw = (byte) (int) (p_i3294_1_.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (p_i3294_1_.rotationPitch * 256.0F / 360.0F);
		headYaw = (byte) (int) (p_i3294_1_.rotationYawHead * 256.0F / 360.0F);
		double var2 = 3.9D;
		double var4 = p_i3294_1_.motionX;
		double var6 = p_i3294_1_.motionY;
		double var8 = p_i3294_1_.motionZ;
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
		metaData = p_i3294_1_.getDataWatcher();
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleMobSpawn(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		type = p_73267_1_.readByte() & 255;
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
		yaw = p_73267_1_.readByte();
		pitch = p_73267_1_.readByte();
		headYaw = p_73267_1_.readByte();
		velocityX = p_73267_1_.readShort();
		velocityY = p_73267_1_.readShort();
		velocityZ = p_73267_1_.readShort();
		metadata = DataWatcher.readWatchableObjects(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(type & 255);
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeByte(yaw);
		p_73273_1_.writeByte(pitch);
		p_73273_1_.writeByte(headYaw);
		p_73273_1_.writeShort(velocityX);
		p_73273_1_.writeShort(velocityY);
		p_73273_1_.writeShort(velocityZ);
		metaData.writeWatchableObjects(p_73273_1_);
	}
}
