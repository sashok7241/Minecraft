package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Packet209SetPlayerTeam extends Packet
{
	public String teamName = "";
	public String teamDisplayName = "";
	public String teamPrefix = "";
	public String teamSuffix = "";
	public Collection playerNames = new ArrayList();
	public int mode = 0;
	public int friendlyFire;
	
	public Packet209SetPlayerTeam()
	{
	}
	
	public Packet209SetPlayerTeam(ScorePlayerTeam p_i10005_1_, Collection p_i10005_2_, int p_i10005_3_)
	{
		if(p_i10005_3_ != 3 && p_i10005_3_ != 4) throw new IllegalArgumentException("Method must be join or leave for player constructor");
		else if(p_i10005_2_ != null && !p_i10005_2_.isEmpty())
		{
			mode = p_i10005_3_;
			teamName = p_i10005_1_.func_96661_b();
			playerNames.addAll(p_i10005_2_);
		} else throw new IllegalArgumentException("Players cannot be null/empty");
	}
	
	public Packet209SetPlayerTeam(ScorePlayerTeam p_i10004_1_, int p_i10004_2_)
	{
		teamName = p_i10004_1_.func_96661_b();
		mode = p_i10004_2_;
		if(p_i10004_2_ == 0 || p_i10004_2_ == 2)
		{
			teamDisplayName = p_i10004_1_.func_96669_c();
			teamPrefix = p_i10004_1_.getColorPrefix();
			teamSuffix = p_i10004_1_.getColorSuffix();
			friendlyFire = p_i10004_1_.func_98299_i();
		}
		if(p_i10004_2_ == 0)
		{
			playerNames.addAll(p_i10004_1_.getMembershipCollection());
		}
	}
	
	@Override public int getPacketSize()
	{
		return 3 + teamName.length();
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSetPlayerTeam(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		teamName = readString(p_73267_1_, 16);
		mode = p_73267_1_.readByte();
		if(mode == 0 || mode == 2)
		{
			teamDisplayName = readString(p_73267_1_, 32);
			teamPrefix = readString(p_73267_1_, 16);
			teamSuffix = readString(p_73267_1_, 16);
			friendlyFire = p_73267_1_.readByte();
		}
		if(mode == 0 || mode == 3 || mode == 4)
		{
			short var2 = p_73267_1_.readShort();
			for(int var3 = 0; var3 < var2; ++var3)
			{
				playerNames.add(readString(p_73267_1_, 16));
			}
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(teamName, p_73273_1_);
		p_73273_1_.writeByte(mode);
		if(mode == 0 || mode == 2)
		{
			writeString(teamDisplayName, p_73273_1_);
			writeString(teamPrefix, p_73273_1_);
			writeString(teamSuffix, p_73273_1_);
			p_73273_1_.writeByte(friendlyFire);
		}
		if(mode == 0 || mode == 3 || mode == 4)
		{
			p_73273_1_.writeShort(playerNames.size());
			Iterator var2 = playerNames.iterator();
			while(var2.hasNext())
			{
				String var3 = (String) var2.next();
				writeString(var3, p_73273_1_);
			}
		}
	}
}
