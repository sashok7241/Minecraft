package net.minecraft.src;

import java.net.DatagramPacket;
import java.util.Date;
import java.util.Random;

class RConThreadQueryAuth
{
	private long timestamp;
	private int randomChallenge;
	private byte[] requestId;
	private byte[] challengeValue;
	private String requestIdAsString;
	final RConThreadQuery queryThread;
	
	public RConThreadQueryAuth(RConThreadQuery par1RConThreadQuery, DatagramPacket par2DatagramPacket)
	{
		queryThread = par1RConThreadQuery;
		timestamp = new Date().getTime();
		byte[] var3 = par2DatagramPacket.getData();
		requestId = new byte[4];
		requestId[0] = var3[3];
		requestId[1] = var3[4];
		requestId[2] = var3[5];
		requestId[3] = var3[6];
		requestIdAsString = new String(requestId);
		randomChallenge = new Random().nextInt(16777216);
		challengeValue = String.format("\t%s%d\u0000", new Object[] { requestIdAsString, Integer.valueOf(randomChallenge) }).getBytes();
	}
	
	public byte[] getChallengeValue()
	{
		return challengeValue;
	}
	
	public int getRandomChallenge()
	{
		return randomChallenge;
	}
	
	public byte[] getRequestId()
	{
		return requestId;
	}
	
	public Boolean hasExpired(long par1)
	{
		return Boolean.valueOf(timestamp < par1);
	}
}
