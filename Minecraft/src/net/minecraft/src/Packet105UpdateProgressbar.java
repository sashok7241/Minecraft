package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet105UpdateProgressbar extends Packet
{
	public int windowId;
	public int progressBar;
	public int progressBarValue;
	
	public Packet105UpdateProgressbar()
	{
	}
	
	public Packet105UpdateProgressbar(int p_i3313_1_, int p_i3313_2_, int p_i3313_3_)
	{
		windowId = p_i3313_1_;
		progressBar = p_i3313_2_;
		progressBarValue = p_i3313_3_;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleUpdateProgressbar(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		windowId = p_73267_1_.readByte();
		progressBar = p_73267_1_.readShort();
		progressBarValue = p_73267_1_.readShort();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(windowId);
		p_73273_1_.writeShort(progressBar);
		p_73273_1_.writeShort(progressBarValue);
	}
}
