package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet23VehicleSpawn extends Packet
{
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int speedX;
	public int speedY;
	public int speedZ;
	public int pitch;
	public int yaw;
	public int type;
	public int throwerEntityId;
	
	public Packet23VehicleSpawn()
	{
	}
	
	public Packet23VehicleSpawn(Entity par1Entity, int par2)
	{
		this(par1Entity, par2, 0);
	}
	
	public Packet23VehicleSpawn(Entity par1Entity, int par2, int par3)
	{
		entityId = par1Entity.entityId;
		xPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
		yPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
		zPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
		pitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0F / 360.0F);
		yaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0F / 360.0F);
		type = par2;
		throwerEntityId = par3;
		if(par3 > 0)
		{
			double var4 = par1Entity.motionX;
			double var6 = par1Entity.motionY;
			double var8 = par1Entity.motionZ;
			double var10 = 3.9D;
			if(var4 < -var10)
			{
				var4 = -var10;
			}
			if(var6 < -var10)
			{
				var6 = -var10;
			}
			if(var8 < -var10)
			{
				var8 = -var10;
			}
			if(var4 > var10)
			{
				var4 = var10;
			}
			if(var6 > var10)
			{
				var6 = var10;
			}
			if(var8 > var10)
			{
				var8 = var10;
			}
			speedX = (int) (var4 * 8000.0D);
			speedY = (int) (var6 * 8000.0D);
			speedZ = (int) (var8 * 8000.0D);
		}
	}
	
	@Override public int getPacketSize()
	{
		return 21 + throwerEntityId > 0 ? 6 : 0;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleVehicleSpawn(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		type = par1DataInputStream.readByte();
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		pitch = par1DataInputStream.readByte();
		yaw = par1DataInputStream.readByte();
		throwerEntityId = par1DataInputStream.readInt();
		if(throwerEntityId > 0)
		{
			speedX = par1DataInputStream.readShort();
			speedY = par1DataInputStream.readShort();
			speedZ = par1DataInputStream.readShort();
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeByte(type);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte(pitch);
		par1DataOutputStream.writeByte(yaw);
		par1DataOutputStream.writeInt(throwerEntityId);
		if(throwerEntityId > 0)
		{
			par1DataOutputStream.writeShort(speedX);
			par1DataOutputStream.writeShort(speedY);
			par1DataOutputStream.writeShort(speedZ);
		}
	}
}
