package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType3 implements Callable
{
	final IntegratedServer theIntegratedServer;
	
	CallableType3(IntegratedServer par1IntegratedServer)
	{
		theIntegratedServer = par1IntegratedServer;
	}
	
	@Override public Object call()
	{
		return getType();
	}
	
	public String getType()
	{
		return "Integrated Server (map_client.txt)";
	}
}
