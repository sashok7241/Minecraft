package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl2 implements Callable
{
	final World theWorld;
	
	CallableLvl2(World par1World)
	{
		theWorld = par1World;
	}
	
	@Override public Object call()
	{
		return getPlayerEntities();
	}
	
	public String getPlayerEntities()
	{
		return theWorld.playerEntities.size() + " total; " + theWorld.playerEntities.toString();
	}
}
