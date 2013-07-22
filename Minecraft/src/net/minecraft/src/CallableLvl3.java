package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableLvl3 implements Callable
{
	final World theWorld;
	
	CallableLvl3(World p_i3730_1_)
	{
		theWorld = p_i3730_1_;
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
