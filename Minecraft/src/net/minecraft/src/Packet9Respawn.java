package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		respawnDimension = par1DataInput.readInt();
		difficulty = par1DataInput.readByte();
		gameType = EnumGameType.getByID(par1DataInput.readByte());
		worldHeight = par1DataInput.readShort();
		String var2 = readString(par1DataInput, 16);
		terrainType = WorldType.parseWorldType(var2);
		if(terrainType == null)
		{
			terrainType = WorldType.DEFAULT;
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(respawnDimension);
		par1DataOutput.writeByte(difficulty);
		par1DataOutput.writeByte(gameType.getID());
		par1DataOutput.writeShort(worldHeight);
		writeString(terrainType.getWorldTypeName(), par1DataOutput);
	}
}
