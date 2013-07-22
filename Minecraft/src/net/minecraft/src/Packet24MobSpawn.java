package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet24MobSpawn(EntityLivingBase par1EntityLivingBase)
	{
		entityId = par1EntityLivingBase.entityId;
		type = (byte) EntityList.getEntityID(par1EntityLivingBase);
		xPosition = par1EntityLivingBase.myEntitySize.multiplyBy32AndRound(par1EntityLivingBase.posX);
		yPosition = MathHelper.floor_double(par1EntityLivingBase.posY * 32.0D);
		zPosition = par1EntityLivingBase.myEntitySize.multiplyBy32AndRound(par1EntityLivingBase.posZ);
		yaw = (byte) (int) (par1EntityLivingBase.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (par1EntityLivingBase.rotationPitch * 256.0F / 360.0F);
		headYaw = (byte) (int) (par1EntityLivingBase.rotationYawHead * 256.0F / 360.0F);
		double var2 = 3.9D;
		double var4 = par1EntityLivingBase.motionX;
		double var6 = par1EntityLivingBase.motionY;
		double var8 = par1EntityLivingBase.motionZ;
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
		metaData = par1EntityLivingBase.getDataWatcher();
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		type = par1DataInput.readByte() & 255;
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
		yaw = par1DataInput.readByte();
		pitch = par1DataInput.readByte();
		headYaw = par1DataInput.readByte();
		velocityX = par1DataInput.readShort();
		velocityY = par1DataInput.readShort();
		velocityZ = par1DataInput.readShort();
		metadata = DataWatcher.readWatchableObjects(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(type & 255);
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeByte(yaw);
		par1DataOutput.writeByte(pitch);
		par1DataOutput.writeByte(headYaw);
		par1DataOutput.writeShort(velocityX);
		par1DataOutput.writeShort(velocityY);
		par1DataOutput.writeShort(velocityZ);
		metaData.writeWatchableObjects(par1DataOutput);
	}
}
