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
	
	public Packet209SetPlayerTeam(ScorePlayerTeam par1ScorePlayerTeam, Collection par2Collection, int par3)
	{
		if(par3 != 3 && par3 != 4) throw new IllegalArgumentException("Method must be join or leave for player constructor");
		else if(par2Collection != null && !par2Collection.isEmpty())
		{
			mode = par3;
			teamName = par1ScorePlayerTeam.func_96661_b();
			playerNames.addAll(par2Collection);
		} else throw new IllegalArgumentException("Players cannot be null/empty");
	}
	
	public Packet209SetPlayerTeam(ScorePlayerTeam par1, int par2)
	{
		teamName = par1.func_96661_b();
		mode = par2;
		if(par2 == 0 || par2 == 2)
		{
			teamDisplayName = par1.func_96669_c();
			teamPrefix = par1.getColorPrefix();
			teamSuffix = par1.getColorSuffix();
			friendlyFire = par1.func_98299_i();
		}
		if(par2 == 0)
		{
			playerNames.addAll(par1.getMembershipCollection());
		}
	}
	
	@Override public int getPacketSize()
	{
		return 3 + teamName.length();
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSetPlayerTeam(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		teamName = readString(par1DataInputStream, 16);
		mode = par1DataInputStream.readByte();
		if(mode == 0 || mode == 2)
		{
			teamDisplayName = readString(par1DataInputStream, 32);
			teamPrefix = readString(par1DataInputStream, 16);
			teamSuffix = readString(par1DataInputStream, 16);
			friendlyFire = par1DataInputStream.readByte();
		}
		if(mode == 0 || mode == 3 || mode == 4)
		{
			short var2 = par1DataInputStream.readShort();
			for(int var3 = 0; var3 < var2; ++var3)
			{
				playerNames.add(readString(par1DataInputStream, 16));
			}
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(teamName, par1DataOutputStream);
		par1DataOutputStream.writeByte(mode);
		if(mode == 0 || mode == 2)
		{
			writeString(teamDisplayName, par1DataOutputStream);
			writeString(teamPrefix, par1DataOutputStream);
			writeString(teamSuffix, par1DataOutputStream);
			par1DataOutputStream.writeByte(friendlyFire);
		}
		if(mode == 0 || mode == 3 || mode == 4)
		{
			par1DataOutputStream.writeShort(playerNames.size());
			Iterator var2 = playerNames.iterator();
			while(var2.hasNext())
			{
				String var3 = (String) var2.next();
				writeString(var3, par1DataOutputStream);
			}
		}
	}
}
