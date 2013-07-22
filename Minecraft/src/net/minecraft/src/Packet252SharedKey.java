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
	
	public Packet252SharedKey(SecretKey par1SecretKey, PublicKey par2PublicKey, byte[] par3ArrayOfByte)
	{
		sharedKey = par1SecretKey;
		sharedSecret = CryptManager.encryptData(par2PublicKey, par1SecretKey.getEncoded());
		verifyToken = CryptManager.encryptData(par2PublicKey, par3ArrayOfByte);
	}
	
	@Override public int getPacketSize()
	{
		return 2 + sharedSecret.length + 2 + verifyToken.length;
	}
	
	public SecretKey getSharedKey()
	{
		return this.getSharedKey((PrivateKey) null);
	}
	
	public SecretKey getSharedKey(PrivateKey par1PrivateKey)
	{
		return par1PrivateKey == null ? sharedKey : (sharedKey = CryptManager.decryptSharedKey(par1PrivateKey, sharedSecret));
	}
	
	public byte[] getVerifyToken(PrivateKey par1PrivateKey)
	{
		return par1PrivateKey == null ? verifyToken : CryptManager.decryptData(par1PrivateKey, verifyToken);
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSharedKey(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		sharedSecret = readBytesFromStream(par1DataInputStream);
		verifyToken = readBytesFromStream(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		writeByteArray(par1DataOutputStream, sharedSecret);
		writeByteArray(par1DataOutputStream, verifyToken);
	}
}
