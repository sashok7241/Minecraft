package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet9Respawn extends Packet
{
	public int respawnDimension;
	public int difficulty;
	public int worldHeight;
	public EnumGameType gameType;
	public WorldType terrainType;
	
	public Packet9Respawn()
	{
	}
	
	public Packet9Respawn(int p_i3342_1_, byte p_i3342_2_, WorldType p_i3342_3_, int p_i3342_4_, EnumGameType p_i3342_5_)
	{
		respawnDimension = p_i3342_1_;
		difficulty = p_i3342_2_;
		worldHeight = p_i3342_4_;
		gameType = p_i3342_5_;
		terrainType = p_i3342_3_;
	}
	
	@Override public int getPacketSize()
	{
		return 8 + (terrainType == null ? 0 : terrainType.getWorldTypeName().length());
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleRespawn(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		respawnDimension = p_73267_1_.readInt();
		difficulty = p_73267_1_.readByte();
		gameType = EnumGameType.getByID(p_73267_1_.readByte());
		worldHeight = p_73267_1_.readShort();
		String var2 = readString(p_73267_1_, 16);
		terrainType = WorldType.parseWorldType(var2);
		if(terrainType == null)
		{
			terrainType = WorldType.DEFAULT;
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(respawnDimension);
		p_73273_1_.writeByte(difficulty);
		p_73273_1_.writeByte(gameType.getID());
		p_73273_1_.writeShort(worldHeight);
		writeString(terrainType.getWorldTypeName(), p_73273_1_);
	}
}
