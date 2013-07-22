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
	
	public Packet34EntityTeleport(Entity p_i3359_1_)
	{
		entityId = p_i3359_1_.entityId;
		xPosition = MathHelper.floor_double(p_i3359_1_.posX * 32.0D);
		yPosition = MathHelper.floor_double(p_i3359_1_.posY * 32.0D);
		zPosition = MathHelper.floor_double(p_i3359_1_.posZ * 32.0D);
		yaw = (byte) (int) (p_i3359_1_.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (p_i3359_1_.rotationPitch * 256.0F / 360.0F);
	}
	
	public Packet34EntityTeleport(int p_i3360_1_, int p_i3360_2_, int p_i3360_3_, int p_i3360_4_, byte p_i3360_5_, byte p_i3360_6_)
	{
		entityId = p_i3360_1_;
		xPosition = p_i3360_2_;
		yPosition = p_i3360_3_;
		zPosition = p_i3360_4_;
		yaw = p_i3360_5_;
		pitch = p_i3360_6_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet34EntityTeleport var2 = (Packet34EntityTeleport) p_73268_1_;
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityTeleport(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
		yaw = (byte) p_73267_1_.read();
		pitch = (byte) p_73267_1_.read();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.write(yaw);
		p_73273_1_.write(pitch);
	}
}
