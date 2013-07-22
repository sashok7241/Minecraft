package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet71Weather extends Packet
{
	public int entityID;
	public int posX;
	public int posY;
	public int posZ;
	public int isLightningBolt;
	
	public Packet71Weather()
	{
	}
	
	public Packet71Weather(Entity par1Entity)
	{
		entityID = par1Entity.entityId;
		posX = MathHelper.floor_double(par1Entity.posX * 32.0D);
		posY = MathHelper.floor_double(par1Entity.posY * 32.0D);
		posZ = MathHelper.floor_double(par1Entity.posZ * 32.0D);
		if(par1Entity instanceof EntityLightningBolt)
		{
			isLightningBolt = 1;
		}
	}
	
	@Override public int getPacketSize()
	{
		return 17;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleWeather(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityID = par1DataInput.readInt();
		isLightningBolt = par1DataInput.readByte();
		posX = par1DataInput.readInt();
		posY = par1DataInput.readInt();
		posZ = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityID);
		par1DataOutput.writeByte(isLightningBolt);
		par1DataOutput.writeInt(posX);
		par1DataOutput.writeInt(posY);
		par1DataOutput.writeInt(posZ);
	}
}
