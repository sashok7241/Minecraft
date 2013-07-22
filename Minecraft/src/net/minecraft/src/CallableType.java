package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType implements Callable
{
	final DedicatedServer theDecitatedServer;
	
	CallableType(DedicatedServer p_i3381_1_)
	{
		theDecitatedServer = p_i3381_1_;
	}
	
	@Override public Object call()
	{
		return getType();
	}
	
	public String getType()
	{
		String var1 = theDecitatedServer.getServerModName();
		return !var1.equals("vanilla") ? "Definitely; Server brand changed to \'" + var1 + "\'" : "Unknown (can\'t tell)";
	}
}
