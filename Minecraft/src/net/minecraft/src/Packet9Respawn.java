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
	
	public Packet9Respawn(int par1, byte par2, WorldType par3WorldType, int par4, EnumGameType par5EnumGameType)
	{
		respawnDimension = par1;
		difficulty = par2;
		worldHeight = par4;
		gameType = par5EnumGameType;
		terrainType = par3WorldType;
	}
	
	@Override public int getPacketSize()
	{
		return 8 + (terrainType == null ? 0 : terrainType.getWorldTypeName().length());
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleRespawn(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		respawnDimension = par1DataInputStream.readInt();
		difficulty = par1DataInputStream.readByte();
		gameType = EnumGameType.getByID(par1DataInputStream.readByte());
		worldHeight = par1DataInputStream.readShort();
		String var2 = readString(par1DataInputStream, 16);
		terrainType = WorldType.parseWorldType(var2);
		if(terrainType == null)
		{
			terrainType = WorldType.DEFAULT;
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(respawnDimension);
		par1DataOutputStream.writeByte(difficulty);
		par1DataOutputStream.writeByte(gameType.getID());
		par1DataOutputStream.writeShort(worldHeight);
		writeString(terrainType.getWorldTypeName(), par1DataOutputStream);
	}
}
