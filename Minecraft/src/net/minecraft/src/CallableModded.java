package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

public class CallableModded implements Callable
{
	final Minecraft mc;
	
	public CallableModded(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return getClientProfilerEnabled();
	}
	
	public String getClientProfilerEnabled()
	{
		String var1 = ClientBrandRetriever.getClientModName();
		return !var1.equals("vanilla") ? "Definitely; Client brand changed to \'" + var1 + "\'" : Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
	}
}
