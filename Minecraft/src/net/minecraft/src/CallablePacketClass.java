package net.minecraft.src;

import java.util.concurrent.Callable;

class CallablePacketClass implements Callable
{
	final Packet thePacket;
	final NetServerHandler theNetServerHandler;
	
	CallablePacketClass(NetServerHandler p_i11033_1_, Packet p_i11033_2_)
	{
		theNetServerHandler = p_i11033_1_;
		thePacket = p_i11033_2_;
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
