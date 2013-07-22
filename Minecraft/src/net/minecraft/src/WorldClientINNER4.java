package net.minecraft.src;

import java.util.concurrent.Callable;

class WorldClientINNER4 implements Callable
{
	final WorldClient field_142029_a;
	
	WorldClientINNER4(WorldClient par1WorldClient)
	{
		field_142029_a = par1WorldClient;
	}
	
	@Override public Object call()
	{
		return func_142028_a();
	}
	
	public String func_142028_a()
	{
		return WorldClient.func_142030_c(field_142029_a).getIntegratedServer() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server";
	}
}
