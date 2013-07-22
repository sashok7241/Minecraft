package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet100OpenWindow extends Packet
{
	public int windowId;
	public int inventoryType;
	public String windowTitle;
	public int slotsCount;
	public boolean useProvidedWindowTitle;
	public int field_111008_f;
	
	public Packet100OpenWindow()
	{
	}
	
	public Packet100OpenWindow(int par1, int par2, String par3Str, int par4, boolean par5)
	{
		windowId = par1;
		inventoryType = par2;
		windowTitle = par3Str;
		slotsCount = par4;
		useProvidedWindowTitle = par5;
	}
	
	public Packet100OpenWindow(int par1, int par2, String par3Str, int par4, boolean par5, int par6)
	{
		this(par1, par2, par3Str, par4, par5);
		field_111008_f = par6;
	}
	
	@Override public int getPacketSize()
	{
		return inventoryType == 11 ? 8 + windowTitle.length() : 4 + windowTitle.length();
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleOpenWindow(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		windowId = par1DataInput.readByte() & 255;
		inventoryType = par1DataInput.readByte() & 255;
		windowTitle = readString(par1DataInput, 32);
		slotsCount = par1DataInput.readByte() & 255;
		useProvidedWindowTitle = par1DataInput.readBoolean();
		if(inventoryType == 11)
		{
			field_111008_f = par1DataInput.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(windowId & 255);
		par1DataOutput.writeByte(inventoryType & 255);
		writeString(windowTitle, par1DataOutput);
		par1DataOutput.writeByte(slotsCount & 255);
		par1DataOutput.writeBoolean(useProvidedWindowTitle);
		if(inventoryType == 11)
		{
			par1DataOutput.writeInt(field_111008_f);
		}
	}
}
