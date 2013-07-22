package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.security.PublicKey;

public class Packet253ServerAuthData extends Packet
{
	private String serverId;
	private PublicKey publicKey;
	private byte[] verifyToken = new byte[0];
	
	public Packet253ServerAuthData()
	{
	}
	
	public Packet253ServerAuthData(String par1Str, PublicKey par2PublicKey, byte[] par3ArrayOfByte)
	{
		serverId = par1Str;
		publicKey = par2PublicKey;
		verifyToken = par3ArrayOfByte;
	}
	
	@Override public int getPacketSize()
	{
		return 2 + serverId.length() * 2 + 2 + publicKey.getEncoded().length + 2 + verifyToken.length;
	}
	
	public PublicKey getPublicKey()
	{
		return publicKey;
	}
	
	public String getServerId()
	{
		return serverId;
	}
	
	public byte[] getVerifyToken()
	{
		return verifyToken;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleServerAuthData(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		serverId = readString(par1DataInput, 20);
		publicKey = CryptManager.decodePublicKey(readBytesFromStream(par1DataInput));
		verifyToken = readBytesFromStream(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		writeString(serverId, par1DataOutput);
		writeByteArray(par1DataOutput, publicKey.getEncoded());
		writeByteArray(par1DataOutput, verifyToken);
	}
}
