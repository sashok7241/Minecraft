package net.minecraft.src;

import java.util.concurrent.Callable;

final class CallableBlockType implements Callable
{
	final int blockID;
	
	CallableBlockType(int par1)
	{
		blockID = par1;
	}
	
	@Override public Object call()
	{
		return callBlockType();
	}
	
	public String callBlockType()
	{
		try
		{
			return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(blockID), Block.blocksList[blockID].getUnlocalizedName(), Block.blocksList[blockID].getClass().getCanonicalName() });
		} catch(Throwable var2)
		{
			return "ID #" + blockID;
		}
	}
}
