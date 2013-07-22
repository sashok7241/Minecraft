package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet34EntityTeleport extends Packet
{
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte yaw;
	public byte pitch;
	
	public Packet34EntityTeleport()
	{
	}
	
	public Packet34EntityTeleport(Entity par1Entity)
	{
		entityId = par1Entity.entityId;
		xPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
		yPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
		zPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
		yaw = (byte) (int) (par1Entity.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (par1Entity.rotationPitch * 256.0F / 360.0F);
	}
	
	public Packet34EntityTeleport(int par1, int par2, int par3, int par4, byte par5, byte par6)
	{
		entityId = par1;
		xPosition = par2;
		yPosition = par3;
		zPosition = par4;
		yaw = par5;
		pitch = par6;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet34EntityTeleport var2 = (Packet34EntityTeleport) par1Packet;
		return var2.entityId == entityId;
	}
	
	@Override public int getPacketSize()
	{
		return 34;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityTeleport(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
		yaw = par1DataInput.readByte();
		pitch = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.write(yaw);
		par1DataOutput.write(pitch);
	}
}
