package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet208SetDisplayObjective extends Packet
{
	public int scoreboardPosition;
	public String scoreName;
	
	public Packet208SetDisplayObjective()
	{
	}
	
	public Packet208SetDisplayObjective(int par1, ScoreObjective par2ScoreObjective)
	{
		scoreboardPosition = par1;
		if(par2ScoreObjective == null)
		{
			scoreName = "";
		} else
		{
			scoreName = par2ScoreObjective.getName();
		}
	}
	
	@Override public int getPacketSize()
	{
		return 3 + scoreName.length();
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSetDisplayObjective(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		scoreboardPosition = par1DataInput.readByte();
		scoreName = readString(par1DataInput, 16);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(scoreboardPosition);
		writeString(scoreName, par1DataOutput);
	}
}
