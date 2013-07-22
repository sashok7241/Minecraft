package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet2ClientProtocol extends Packet
{
	private int protocolVersion;
	private String username;
	private String serverHost;
	private int serverPort;
	
	public Packet2ClientProtocol()
	{
	}
	
	public Packet2ClientProtocol(int p_i3305_1_, String p_i3305_2_, String p_i3305_3_, int p_i3305_4_)
	{
		protocolVersion = p_i3305_1_;
		username = p_i3305_2_;
		serverHost = p_i3305_3_;
		serverPort = p_i3305_4_;
	}
	
	@Override public int getPacketSize()
	{
		return 3 + 2 * username.length();
	}
	
	public int getProtocolVersion()
	{
		return protocolVersion;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleClientProtocol(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		protocolVersion = p_73267_1_.readByte();
		username = readString(p_73267_1_, 16);
		serverHost = readString(p_73267_1_, 255);
		serverPort = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(protocolVersion);
		writeString(username, p_73273_1_);
		writeString(serverHost, p_73273_1_);
		p_73273_1_.writeInt(serverPort);
	}
}
