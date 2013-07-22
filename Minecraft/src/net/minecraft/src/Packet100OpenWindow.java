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
	
	public Packet100OpenWindow(int p_i9017_1_, int p_i9017_2_, String p_i9017_3_, int p_i9017_4_, boolean p_i9017_5_)
	{
		windowId = p_i9017_1_;
		inventoryType = p_i9017_2_;
		windowTitle = p_i9017_3_;
		slotsCount = p_i9017_4_;
		useProvidedWindowTitle = p_i9017_5_;
	}
	
	@Override public int getPacketSize()
	{
		return 4 + windowTitle.length();
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleOpenWindow(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		windowId = p_73267_1_.readByte() & 255;
		inventoryType = p_73267_1_.readByte() & 255;
		windowTitle = readString(p_73267_1_, 32);
		slotsCount = p_73267_1_.readByte() & 255;
		useProvidedWindowTitle = p_73267_1_.readBoolean();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(windowId & 255);
		p_73273_1_.writeByte(inventoryType & 255);
		writeString(windowTitle, p_73273_1_);
		p_73273_1_.writeByte(slotsCount & 255);
		p_73273_1_.writeBoolean(useProvidedWindowTitle);
	}
}
