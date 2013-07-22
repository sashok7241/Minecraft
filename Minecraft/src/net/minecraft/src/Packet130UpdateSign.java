package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet130UpdateSign extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public String[] signLines;
	
	public Packet130UpdateSign()
	{
		isChunkDataPacket = true;
	}
	
	public Packet130UpdateSign(int par1, int par2, int par3, String[] par4ArrayOfStr)
	{
		isChunkDataPacket = true;
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
		signLines = new String[] { par4ArrayOfStr[0], par4ArrayOfStr[1], par4ArrayOfStr[2], par4ArrayOfStr[3] };
	}
	
	@Override public int getPacketSize()
	{
		int var1 = 0;
		for(int var2 = 0; var2 < 4; ++var2)
		{
			var1 += signLines[var2].length();
		}
		return var1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleUpdateSign(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readShort();
		zPosition = par1DataInput.readInt();
		signLines = new String[4];
		for(int var2 = 0; var2 < 4; ++var2)
		{
			signLines[var2] = readString(par1DataInput, 15);
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeShort(yPosition);
		par1DataOutput.writeInt(zPosition);
		for(int var2 = 0; var2 < 4; ++var2)
		{
			writeString(signLines[var2], par1DataOutput);
		}
	}
}
