package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet1Login extends Packet
{
	public int clientEntityId;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		clientEntityId = par1DataInput.readInt();
		String var2 = readString(par1DataInput, 16);
		terrainType = WorldType.parseWorldType(var2);
		if(terrainType == null)
		{
			terrainType = WorldType.DEFAULT;
		}
		byte var3 = par1DataInput.readByte();
		hardcoreMode = (var3 & 8) == 8;
		int var4 = var3 & -9;
		gameType = EnumGameType.getByID(var4);
		dimension = par1DataInput.readByte();
		difficultySetting = par1DataInput.readByte();
		worldHeight = par1DataInput.readByte();
		maxPlayers = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(clientEntityId);
		writeString(terrainType == null ? "" : terrainType.getWorldTypeName(), par1DataOutput);
		int var2 = gameType.getID();
		if(hardcoreMode)
		{
			var2 |= 8;
		}
		par1DataOutput.writeByte(var2);
		par1DataOutput.writeByte(dimension);
		par1DataOutput.writeByte(difficultySetting);
		par1DataOutput.writeByte(worldHeight);
		par1DataOutput.writeByte(maxPlayers);
	}
}
