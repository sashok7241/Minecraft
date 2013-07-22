package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet203AutoComplete extends Packet
{
	private String text;
	
	public Packet203AutoComplete()
	{
	}
	
	public Packet203AutoComplete(String p_i3299_1_)
	{
		text = p_i3299_1_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		return true;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + text.length() * 2;
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleAutoComplete(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		text = readString(p_73267_1_, Packet3Chat.maxChatLength);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(text, p_73273_1_);
	}
}
