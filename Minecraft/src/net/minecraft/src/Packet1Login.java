package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet1Login extends Packet
{
	public int clientEntityId = 0;
	public WorldType terrainType;
	public boolean hardcoreMode;
	public EnumGameType gameType;
	public int dimension;
	public byte difficultySetting;
	public byte worldHeight;
	public byte maxPlayers;
	
	public Packet1Login()
	{
	}
	
	public Packet1Login(int p_i3327_1_, WorldType p_i3327_2_, EnumGameType p_i3327_3_, boolean p_i3327_4_, int p_i3327_5_, int p_i3327_6_, int p_i3327_7_, int p_i3327_8_)
	{
		clientEntityId = p_i3327_1_;
		terrainType = p_i3327_2_;
		dimension = p_i3327_5_;
		difficultySetting = (byte) p_i3327_6_;
		gameType = p_i3327_3_;
		worldHeight = (byte) p_i3327_7_;
		maxPlayers = (byte) p_i3327_8_;
		hardcoreMode = p_i3327_4_;
	}
	
	@Override public int getPacketSize()
	{
		int var1 = 0;
		if(terrainType != null)
		{
			var1 = terrainType.getWorldTypeName().length();
		}
		return 6 + 2 * var1 + 4 + 4 + 1 + 1 + 1;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleLogin(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		clientEntityId = p_73267_1_.readInt();
		String var2 = readString(p_73267_1_, 16);
		terrainType = WorldType.parseWorldType(var2);
		if(terrainType == null)
		{
			terrainType = WorldType.DEFAULT;
		}
		byte var3 = p_73267_1_.readByte();
		hardcoreMode = (var3 & 8) == 8;
		int var4 = var3 & -9;
		gameType = EnumGameType.getByID(var4);
		dimension = p_73267_1_.readByte();
		difficultySetting = p_73267_1_.readByte();
		worldHeight = p_73267_1_.readByte();
		maxPlayers = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(clientEntityId);
		writeString(terrainType == null ? "" : terrainType.getWorldTypeName(), p_73273_1_);
		int var2 = gameType.getID();
		if(hardcoreMode)
		{
			var2 |= 8;
		}
		p_73273_1_.writeByte(var2);
		p_73273_1_.writeByte(dimension);
		p_73273_1_.writeByte(difficultySetting);
		p_73273_1_.writeByte(worldHeight);
		p_73273_1_.writeByte(maxPlayers);
	}
}
