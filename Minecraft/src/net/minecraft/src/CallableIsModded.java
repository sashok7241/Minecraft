package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

class CallableIsModded implements Callable
{
	final IntegratedServer theIntegratedServer;
	
	CallableIsModded(IntegratedServer par1IntegratedServer)
	{
		theIntegratedServer = par1IntegratedServer;
	}
	
	@Override public Object call()
	{
		return getMinecraftIsModded();
	}
	
	public String getMinecraftIsModded()
	{
		String var1 = ClientBrandRetriever.getClientModName();
		if(!var1.equals("vanilla")) return "Definitely; Client brand changed to \'" + var1 + "\'";
		else
		{
			var1 = theIntegratedServer.getServerModName();
			return !var1.equals("vanilla") ? "Definitely; Server brand changed to \'" + var1 + "\'" : Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.";
		}
	}
}
