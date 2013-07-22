package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		yaw = (byte) par1DataInputStream.read();
		pitch = (byte) par1DataInputStream.read();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.write(yaw);
		par1DataOutputStream.write(pitch);
	}
}
