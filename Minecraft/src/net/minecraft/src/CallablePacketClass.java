package net.minecraft.src;

import java.util.concurrent.Callable;

class CallablePacketClass implements Callable
{
	final Packet thePacket;
	final NetServerHandler theNetServerHandler;
	
	CallablePacketClass(NetServerHandler par1NetServerHandler, Packet par2Packet)
	{
		theNetServerHandler = par1NetServerHandler;
		thePacket = par2Packet;
	}
	
	@Override public Object call()
	{
		return getPacketClass();
	}
	
	public String getPacketClass()
	{
		return thePacket.getClass().getCanonicalName();
	}
}
