package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	public Packet71Weather(Entity p_i3292_1_)
	{
		entityID = p_i3292_1_.entityId;
		posX = MathHelper.floor_double(p_i3292_1_.posX * 32.0D);
		posY = MathHelper.floor_double(p_i3292_1_.posY * 32.0D);
		posZ = MathHelper.floor_double(p_i3292_1_.posZ * 32.0D);
		if(p_i3292_1_ instanceof EntityLightningBolt)
		{
			isLightningBolt = 1;
		}
	}
	
	@Override public int getPacketSize()
	{
		return 17;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleWeather(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityID = p_73267_1_.readInt();
		isLightningBolt = p_73267_1_.readByte();
		posX = p_73267_1_.readInt();
		posY = p_73267_1_.readInt();
		posZ = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityID);
		p_73273_1_.writeByte(isLightningBolt);
		p_73273_1_.writeInt(posX);
		p_73273_1_.writeInt(posY);
		p_73273_1_.writeInt(posZ);
	}
}
