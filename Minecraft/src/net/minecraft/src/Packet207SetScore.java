package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet207SetScore extends Packet
{
	public String itemName = "";
	public String scoreName = "";
	public int value = 0;
	public int updateOrRemove = 0;
	
	public Packet207SetScore()
	{
	}
	
	public Packet207SetScore(Score p_i10006_1_, int p_i10006_2_)
	{
		itemName = p_i10006_1_.getPlayerName();
		scoreName = p_i10006_1_.func_96645_d().getName();
		value = p_i10006_1_.getScorePoints();
		updateOrRemove = p_i10006_2_;
	}
	
	public Packet207SetScore(String p_i10007_1_)
	{
		itemName = p_i10007_1_;
		scoreName = "";
		value = 0;
		updateOrRemove = 1;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + itemName.length() + 2 + scoreName.length() + 4 + 1;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSetScore(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		itemName = readString(p_73267_1_, 16);
		updateOrRemove = p_73267_1_.readByte();
		if(updateOrRemove != 1)
		{
			scoreName = readString(p_73267_1_, 16);
			value = p_73267_1_.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(itemName, p_73273_1_);
		p_73273_1_.writeByte(updateOrRemove);
		if(updateOrRemove != 1)
		{
			writeString(scoreName, p_73273_1_);
			p_73273_1_.writeInt(value);
		}
	}
}
