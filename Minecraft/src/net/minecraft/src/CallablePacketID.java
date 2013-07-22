package net.minecraft.src;

import java.util.concurrent.Callable;

class CallablePacketID implements Callable
{
	final Packet thePacket;
	final NetServerHandler theNetServerHandler;
	
	CallablePacketID(NetServerHandler par1NetServerHandler, Packet par2Packet)
	{
		theNetServerHandler = par1NetServerHandler;
		thePacket = par2Packet;
	}
	
	@Override public Object call()
	{
		return callPacketID();
	}
	
	public String callPacketID()
	{
		return String.valueOf(thePacket.getPacketId());
	}
}
