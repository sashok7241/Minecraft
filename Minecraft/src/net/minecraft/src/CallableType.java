package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableType implements Callable
{
	final DedicatedServer theDecitatedServer;
	
	CallableType(DedicatedServer par1DedicatedServer)
	{
		theDecitatedServer = par1DedicatedServer;
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
