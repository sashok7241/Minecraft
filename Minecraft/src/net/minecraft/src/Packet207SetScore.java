package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet207SetScore extends Packet
{
	public String itemName = "";
	public String scoreName = "";
	public int value;
	public int updateOrRemove;
	
	public Packet207SetScore()
	{
	}
	
	public Packet207SetScore(Score par1Score, int par2)
	{
		itemName = par1Score.getPlayerName();
		scoreName = par1Score.func_96645_d().getName();
		value = par1Score.getScorePoints();
		updateOrRemove = par2;
	}
	
	public Packet207SetScore(String par1Str)
	{
		itemName = par1Str;
		scoreName = "";
		value = 0;
		updateOrRemove = 1;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + (itemName == null ? 0 : itemName.length()) + 2 + (scoreName == null ? 0 : scoreName.length()) + 4 + 1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSetScore(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		itemName = readString(par1DataInput, 16);
		updateOrRemove = par1DataInput.readByte();
		if(updateOrRemove != 1)
		{
			scoreName = readString(par1DataInput, 16);
			value = par1DataInput.readInt();
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(itemName, par1DataOutput);
		par1DataOutput.writeByte(updateOrRemove);
		if(updateOrRemove != 1)
		{
			writeString(scoreName, par1DataOutput);
			par1DataOutput.writeInt(value);
		}
	}
}
