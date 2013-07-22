package net.minecraft.src;

import java.util.concurrent.Callable;

import net.minecraft.client.Minecraft;

public class CallableType2 implements Callable
{
	final Minecraft mc;
	
	public CallableType2(Minecraft p_i3007_1_)
	{
		mc = p_i3007_1_;
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
