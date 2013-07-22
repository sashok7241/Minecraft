package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet53BlockChange extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int type;
	public int metadata;
	
	public Packet53BlockChange()
	{
		isChunkDataPacket = true;
	}
	
	public Packet53BlockChange(int par1, int par2, int par3, World par4World)
	{
		isChunkDataPacket = true;
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
		type = par4World.getBlockId(par1, par2, par3);
		metadata = par4World.getBlockMetadata(par1, par2, par3);
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleBlockChange(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.read();
		zPosition = par1DataInputStream.readInt();
		type = par1DataInputStream.readShort();
		metadata = par1DataInputStream.read();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.write(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeShort(type);
		par1DataOutputStream.write(metadata);
	}
}
