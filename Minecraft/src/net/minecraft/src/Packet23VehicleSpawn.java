package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		type = par1DataInput.readByte();
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
		pitch = par1DataInput.readByte();
		yaw = par1DataInput.readByte();
		throwerEntityId = par1DataInput.readInt();
		if(throwerEntityId > 0)
		{
			speedX = par1DataInput.readShort();
			speedY = par1DataInput.readShort();
			speedZ = par1DataInput.readShort();
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeByte(type);
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeByte(pitch);
		par1DataOutput.writeByte(yaw);
		par1DataOutput.writeInt(throwerEntityId);
		if(throwerEntityId > 0)
		{
			par1DataOutput.writeShort(speedX);
			par1DataOutput.writeShort(speedY);
			par1DataOutput.writeShort(speedZ);
		}
	}
}
