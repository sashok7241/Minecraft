package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet206SetObjective extends Packet
{
	public String objectiveName;
	public String objectiveDisplayName;
	public int change;
	
	public Packet206SetObjective()
	{
	}
	
	public Packet206SetObjective(ScoreObjective par1, int par2)
	{
		objectiveName = par1.getName();
		objectiveDisplayName = par1.getDisplayName();
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
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		objectiveName = readString(par1DataInputStream, 16);
		objectiveDisplayName = readString(par1DataInputStream, 32);
		change = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(objectiveName, par1DataOutputStream);
		writeString(objectiveDisplayName, par1DataOutputStream);
		par1DataOutputStream.writeByte(change);
	}
}
