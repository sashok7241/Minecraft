package net.minecraft.src;

import java.util.concurrent.Callable;

class WorldClientINNER3 implements Callable
{
	final WorldClient field_142027_a;
	
	WorldClientINNER3(WorldClient par1WorldClient)
	{
		field_142027_a = par1WorldClient;
	}
	
	@Override public Object call()
	{
		return func_142026_a();
	}
	
	public String func_142026_a()
	{
		return WorldClient.func_142030_c(field_142027_a).thePlayer.func_142021_k();
	}
}
