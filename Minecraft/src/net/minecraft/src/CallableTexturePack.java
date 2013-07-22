package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableTexturePack implements Callable
{
	final Minecraft theMinecraft;
	
	public CallableTexturePack(Minecraft p_i3012_1_)
	{
		theMinecraft = p_i3012_1_;
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
