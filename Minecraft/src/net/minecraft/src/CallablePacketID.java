package net.minecraft.src;

import java.util.concurrent.Callable;

class CallablePacketID implements Callable
{
	final Packet thePacket;
	final NetServerHandler theNetServerHandler;
	
	CallablePacketID(NetServerHandler p_i11032_1_, Packet p_i11032_2_)
	{
		theNetServerHandler = p_i11032_1_;
		thePacket = p_i11032_2_;
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
