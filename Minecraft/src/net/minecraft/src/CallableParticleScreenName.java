package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableParticleScreenName implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableParticleScreenName(Minecraft p_i7001_1_)
	{
		theMinecraft = p_i7001_1_;
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
