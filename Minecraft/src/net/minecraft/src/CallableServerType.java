package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableServerType implements Callable
{
	final DedicatedServer theDedicatedServer;
	
	CallableServerType(DedicatedServer par1DedicatedServer)
	{
		theDedicatedServer = par1DedicatedServer;
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
