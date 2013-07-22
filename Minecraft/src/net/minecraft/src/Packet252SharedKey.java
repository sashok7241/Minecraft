package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

public class Packet252SharedKey extends Packet
{
	private byte[] sharedSecret = new byte[0];
	private byte[] verifyToken = new byte[0];
	private SecretKey sharedKey;
	
	public Packet252SharedKey()
	{
	}
	
	public Packet252SharedKey(SecretKey p_i3356_1_, PublicKey p_i3356_2_, byte[] p_i3356_3_)
	{
		sharedKey = p_i3356_1_;
		sharedSecret = CryptManager.encryptData(p_i3356_2_, p_i3356_1_.getEncoded());
		verifyToken = CryptManager.encryptData(p_i3356_2_, p_i3356_3_);
	}
	
	@Override public int getPacketSize()
	{
		return 2 + sharedSecret.length + 2 + verifyToken.length;
	}
	
	public SecretKey getSharedKey()
	{
		return this.getSharedKey((PrivateKey) null);
	}
	
	public SecretKey getSharedKey(PrivateKey p_73303_1_)
	{
		return p_73303_1_ == null ? sharedKey : (sharedKey = CryptManager.decryptSharedKey(p_73303_1_, sharedSecret));
	}
	
	public byte[] getVerifyToken(PrivateKey p_73302_1_)
	{
		return p_73302_1_ == null ? verifyToken : CryptManager.decryptData(p_73302_1_, verifyToken);
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSharedKey(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		sharedSecret = readBytesFromStream(p_73267_1_);
		verifyToken = readBytesFromStream(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		writeByteArray(p_73273_1_, sharedSecret);
		writeByteArray(p_73273_1_, verifyToken);
	}
}
