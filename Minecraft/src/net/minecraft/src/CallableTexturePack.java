package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableTexturePack implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableTexturePack(Minecraft par1Minecraft)
	{
		theMinecraft = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return callTexturePack();
	}
	
	public String callTexturePack()
	{
		return theMinecraft.gameSettings.skin;
	}
}
