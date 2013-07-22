package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableTexturePack implements Callable
{
	final Minecraft theMinecraft;
	
	CallableTexturePack(Minecraft par1Minecraft)
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
