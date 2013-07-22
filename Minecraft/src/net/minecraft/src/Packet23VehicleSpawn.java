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
	
	public Packet23VehicleSpawn(Entity p_i3289_1_, int p_i3289_2_)
	{
		this(p_i3289_1_, p_i3289_2_, 0);
	}
	
	public Packet23VehicleSpawn(Entity p_i3290_1_, int p_i3290_2_, int p_i3290_3_)
	{
		entityId = p_i3290_1_.entityId;
		xPosition = MathHelper.floor_double(p_i3290_1_.posX * 32.0D);
		yPosition = MathHelper.floor_double(p_i3290_1_.posY * 32.0D);
		zPosition = MathHelper.floor_double(p_i3290_1_.posZ * 32.0D);
		pitch = MathHelper.floor_float(p_i3290_1_.rotationPitch * 256.0F / 360.0F);
		yaw = MathHelper.floor_float(p_i3290_1_.rotationYaw * 256.0F / 360.0F);
		type = p_i3290_2_;
		throwerEntityId = p_i3290_3_;
		if(p_i3290_3_ > 0)
		{
			double var4 = p_i3290_1_.motionX;
			double var6 = p_i3290_1_.motionY;
			double var8 = p_i3290_1_.motionZ;
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleVehicleSpawn(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		type = p_73267_1_.readByte();
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
		pitch = p_73267_1_.readByte();
		yaw = p_73267_1_.readByte();
		throwerEntityId = p_73267_1_.readInt();
		if(throwerEntityId > 0)
		{
			speedX = p_73267_1_.readShort();
			speedY = p_73267_1_.readShort();
			speedZ = p_73267_1_.readShort();
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeByte(type);
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeByte(pitch);
		p_73273_1_.writeByte(yaw);
		p_73273_1_.writeInt(throwerEntityId);
		if(throwerEntityId > 0)
		{
			p_73273_1_.writeShort(speedX);
			p_73273_1_.writeShort(speedY);
			p_73273_1_.writeShort(speedZ);
		}
	}
}
