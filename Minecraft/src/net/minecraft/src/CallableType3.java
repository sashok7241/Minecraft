package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType3 implements Callable
{
	final IntegratedServer theIntegratedServer;
	
	CallableType3(IntegratedServer p_i3120_1_)
	{
		theIntegratedServer = p_i3120_1_;
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
