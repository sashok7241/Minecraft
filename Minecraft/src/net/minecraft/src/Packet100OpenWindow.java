package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet100OpenWindow extends Packet
{
	public int windowId;
	public int inventoryType;
	public String windowTitle;
	public int slotsCount;
	public boolean useProvidedWindowTitle;
	
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
	
	@Override public int getPacketSize()
	{
		return 4 + windowTitle.length();
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleOpenWindow(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		windowId = par1DataInputStream.readByte() & 255;
		inventoryType = par1DataInputStream.readByte() & 255;
		windowTitle = readString(par1DataInputStream, 32);
		slotsCount = par1DataInputStream.readByte() & 255;
		useProvidedWindowTitle = par1DataInputStream.readBoolean();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(windowId & 255);
		par1DataOutputStream.writeByte(inventoryType & 255);
		writeString(windowTitle, par1DataOutputStream);
		par1DataOutputStream.writeByte(slotsCount & 255);
		par1DataOutputStream.writeBoolean(useProvidedWindowTitle);
	}
}
