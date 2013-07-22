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
	
	public Packet204ClientInfo(String p_i5031_1_, int p_i5031_2_, int p_i5031_3_, boolean p_i5031_4_, int p_i5031_5_, boolean p_i5031_6_)
	{
		language = p_i5031_1_;
		renderDistance = p_i5031_2_;
		chatVisisble = p_i5031_3_;
		chatColours = p_i5031_4_;
		gameDifficulty = p_i5031_5_;
		showCape = p_i5031_6_;
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleClientInfo(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		language = readString(p_73267_1_, 7);
		renderDistance = p_73267_1_.readByte();
		byte var2 = p_73267_1_.readByte();
		chatVisisble = var2 & 7;
		chatColours = (var2 & 8) == 8;
		gameDifficulty = p_73267_1_.readByte();
		showCape = p_73267_1_.readBoolean();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(language, p_73273_1_);
		p_73273_1_.writeByte(renderDistance);
		p_73273_1_.writeByte(chatVisisble | (chatColours ? 1 : 0) << 3);
		p_73273_1_.writeByte(gameDifficulty);
		p_73273_1_.writeBoolean(showCape);
	}
}
