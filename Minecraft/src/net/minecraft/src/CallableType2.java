package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

class CallableType2 implements Callable
{
	final Minecraft mc;
	
	CallableType2(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
	}
	
	@Override public Object call()
	{
		return func_82886_a();
	}
	
	public String func_82886_a()
	{
		return "Client (map_client.txt)";
	}
}
