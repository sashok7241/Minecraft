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
	
	public Packet206SetObjective(ScoreObjective p_i10003_1_, int p_i10003_2_)
	{
		objectiveName = p_i10003_1_.getName();
		objectiveDisplayName = p_i10003_1_.getDisplayName();
		change = p_i10003_2_;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + objectiveName.length() + 2 + objectiveDisplayName.length() + 1;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSetObjective(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		objectiveName = readString(p_73267_1_, 16);
		objectiveDisplayName = readString(p_73267_1_, 32);
		change = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(objectiveName, p_73273_1_);
		writeString(objectiveDisplayName, p_73273_1_);
		p_73273_1_.writeByte(change);
	}
}
