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
	
	public Packet207SetScore(Score par1, int par2)
	{
		itemName = par1.getPlayerName();
		scoreName = par1.func_96645_d().getName();
		value = par1.getScorePoints();
		updateOrRemove = par2;
	}
	
	public Packet207SetScore(String par1)
	{
		itemName = par1;
		scoreName = "";
		value = 0;
		updateOrRemove = 1;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + itemName.length() + 2 + scoreName.length() + 4 + 1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSetScore(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		itemName = readString(par1DataInputStream, 16);
		updateOrRemove = par1DataInputStream.readByte();
		if(updateOrRemove != 1)
		{
			scoreName = readString(par1DataInputStream, 16);
			value = par1DataInputStream.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(itemName, par1DataOutputStream);
		par1DataOutputStream.writeByte(updateOrRemove);
		if(updateOrRemove != 1)
		{
			writeString(scoreName, par1DataOutputStream);
			par1DataOutputStream.writeInt(value);
		}
	}
}
