package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableParticleScreenName implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableParticleScreenName(Minecraft par1Minecraft)
	{
		theMinecraft = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return callParticleScreenName();
	}
	
	public String callParticleScreenName()
	{
		return theMinecraft.currentScreen.getClass().getCanonicalName();
	}
}
