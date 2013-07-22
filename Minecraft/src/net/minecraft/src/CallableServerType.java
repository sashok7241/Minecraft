package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableServerType implements Callable
{
	final DedicatedServer theDedicatedServer;
	
	CallableServerType(DedicatedServer p_i6810_1_)
	{
		theDedicatedServer = p_i6810_1_;
	}
	
	@Override public Object call()
	{
		return callServerType();
	}
	
	public String callServerType()
	{
		return "Dedicated Server (map_server.txt)";
	}
}
