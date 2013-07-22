package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableConnectionName implements Callable
{
	final NetServerHandler field_111201_a;
	final NetworkListenThread field_111200_b;
	
	CallableConnectionName(NetworkListenThread par1NetworkListenThread, NetServerHandler par2NetServerHandler)
	{
		field_111200_b = par1NetworkListenThread;
		field_111201_a = par2NetServerHandler;
	}
	
	@Override public Object call()
	{
		return func_111199_a();
	}
	
	public String func_111199_a()
	{
		return field_111201_a.toString();
	}
}
