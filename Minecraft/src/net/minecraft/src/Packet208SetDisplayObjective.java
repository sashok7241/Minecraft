package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet208SetDisplayObjective extends Packet
{
	public int scoreboardPosition;
	public String scoreName;
	
	public Packet208SetDisplayObjective()
	{
	}
	
	public Packet208SetDisplayObjective(int p_i10002_1_, ScoreObjective p_i10002_2_)
	{
		scoreboardPosition = p_i10002_1_;
		if(p_i10002_2_ == null)
		{
			scoreName = "";
		} else
		{
			scoreName = p_i10002_2_.getName();
		}
	}
	
	@Override public int getPacketSize()
	{
		return 3 + scoreName.length();
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSetDisplayObjective(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		scoreboardPosition = p_73267_1_.readByte();
		scoreName = readString(p_73267_1_, 16);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(scoreboardPosition);
		writeString(scoreName, p_73273_1_);
	}
}
