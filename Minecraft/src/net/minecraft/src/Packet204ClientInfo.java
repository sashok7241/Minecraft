package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet204ClientInfo extends Packet
{
	private String language;
	private int renderDistance;
	private int chatVisisble;
	private boolean chatColours;
	private int gameDifficulty;
	private boolean showCape;
	
	public Packet204ClientInfo()
	{
	}
	
	public Packet204ClientInfo(String par1Str, int par2, int par3, boolean par4, int par5, boolean par6)
	{
		language = par1Str;
		renderDistance = par2;
		chatVisisble = par3;
		chatColours = par4;
		gameDifficulty = par5;
		showCape = par6;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		return true;
	}
	
	public boolean getChatColours()
	{
		return chatColours;
	}
	
	public int getChatVisibility()
	{
		return chatVisisble;
	}
	
	public int getDifficulty()
	{
		return gameDifficulty;
	}
	
	public String getLanguage()
	{
		return language;
	}
	
	@Override public int getPacketSize()
	{
		return 7;
	}
	
	public int getRenderDistance()
	{
		return renderDistance;
	}
	
	public boolean getShowCape()
	{
		return showCape;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleClientInfo(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		language = readString(par1DataInputStream, 7);
		renderDistance = par1DataInputStream.readByte();
		byte var2 = par1DataInputStream.readByte();
		chatVisisble = var2 & 7;
		chatColours = (var2 & 8) == 8;
		gameDifficulty = par1DataInputStream.readByte();
		showCape = par1DataInputStream.readBoolean();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeString(language, par1DataOutputStream);
		par1DataOutputStream.writeByte(renderDistance);
		par1DataOutputStream.writeByte(chatVisisble | (chatColours ? 1 : 0) << 3);
		par1DataOutputStream.writeByte(gameDifficulty);
		par1DataOutputStream.writeBoolean(showCape);
	}
}
