package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl3 implements Callable
{
	final World theWorld;
	
	CallableLvl3(World par1World)
	{
		theWorld = par1World;
	}
	
	@Override public Object call()
	{
		return getChunkProvider();
	}
	
	public String getChunkProvider()
	{
		return theWorld.chunkProvider.makeString();
	}
}
