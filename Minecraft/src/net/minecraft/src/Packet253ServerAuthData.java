package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
	
	public Packet253ServerAuthData(String p_i3344_1_, PublicKey p_i3344_2_, byte[] p_i3344_3_)
	{
		serverId = p_i3344_1_;
		publicKey = p_i3344_2_;
		verifyToken = p_i3344_3_;
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleServerAuthData(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		serverId = readString(p_73267_1_, 20);
		publicKey = CryptManager.decodePublicKey(readBytesFromStream(p_73267_1_));
		verifyToken = readBytesFromStream(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeString(serverId, p_73273_1_);
		writeByteArray(p_73273_1_, publicKey.getEncoded());
		writeByteArray(p_73273_1_, verifyToken);
	}
}
