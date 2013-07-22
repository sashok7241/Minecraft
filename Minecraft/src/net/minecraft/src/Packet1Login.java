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
	
	public Packet1Login(int par1, WorldType par2WorldType, EnumGameType par3EnumGameType, boolean par4, int par5, int par6, int par7, int par8)
	{
		clientEntityId = par1;
		terrainType = par2WorldType;
		dimension = par5;
		difficultySetting = (byte) par6;
		gameType = par3EnumGameType;
		worldHeight = (byte) par7;
		maxPlayers = (byte) par8;
		hardcoreMode = par4;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleLogin(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		clientEntityId = par1DataInputStream.readInt();
		String var2 = readString(par1DataInputStream, 16);
		terrainType = WorldType.parseWorldType(var2);
		if(terrainType == null)
		{
			terrainType = WorldType.DEFAULT;
		}
		byte var3 = par1DataInputStream.readByte();
		hardcoreMode = (var3 & 8) == 8;
		int var4 = var3 & -9;
		gameType = EnumGameType.getByID(var4);
		dimension = par1DataInputStream.readByte();
		difficultySetting = par1DataInputStream.readByte();
		worldHeight = par1DataInputStream.readByte();
		maxPlayers = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(clientEntityId);
		writeString(terrainType == null ? "" : terrainType.getWorldTypeName(), par1DataOutputStream);
		int var2 = gameType.getID();
		if(hardcoreMode)
		{
			var2 |= 8;
		}
		par1DataOutputStream.writeByte(var2);
		par1DataOutputStream.writeByte(dimension);
		par1DataOutputStream.writeByte(difficultySetting);
		par1DataOutputStream.writeByte(worldHeight);
		par1DataOutputStream.writeByte(maxPlayers);
	}
}
