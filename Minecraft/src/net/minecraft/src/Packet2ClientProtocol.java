package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet2ClientProtocol(int par1, String par2Str, String par3Str, int par4)
	{
		protocolVersion = par1;
		username = par2Str;
		serverHost = par3Str;
		serverPort = par4;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleClientProtocol(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		protocolVersion = par1DataInput.readByte();
		username = readString(par1DataInput, 16);
		serverHost = readString(par1DataInput, 255);
		serverPort = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(protocolVersion);
		writeString(username, par1DataOutput);
		writeString(serverHost, par1DataOutput);
		par1DataOutput.writeInt(serverPort);
	}
}
