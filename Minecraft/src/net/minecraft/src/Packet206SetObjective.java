package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet206SetObjective extends Packet
{
	public String objectiveName;
	public String objectiveDisplayName;
	public int change;
	
	public Packet206SetObjective()
	{
	}
	
	public Packet206SetObjective(ScoreObjective par1ScoreObjective, int par2)
	{
		objectiveName = par1ScoreObjective.getName();
		objectiveDisplayName = par1ScoreObjective.getDisplayName();
		change = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + objectiveName.length() + 2 + objectiveDisplayName.length() + 1;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSetObjective(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		objectiveName = readString(par1DataInput, 16);
		objectiveDisplayName = readString(par1DataInput, 32);
		change = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(objectiveName, par1DataOutput);
		writeString(objectiveDisplayName, par1DataOutput);
		par1DataOutput.writeByte(change);
	}
}
