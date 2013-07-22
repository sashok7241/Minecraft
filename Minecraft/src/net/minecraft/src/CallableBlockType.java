package net.minecraft.src;

import java.util.concurrent.Callable;

final class CallableBlockType implements Callable
{
	final int blockID;
	
	CallableBlockType(int p_i6805_1_)
	{
		blockID = p_i6805_1_;
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
