package net.minecraft.src;

import java.util.concurrent.Callable;

final class CallableBlockLocation implements Callable
{
	final int blockXCoord;
	final int blockYCoord;
	final int blockZCoord;
	
	CallableBlockLocation(int par1, int par2, int par3)
	{
		blockXCoord = par1;
		blockYCoord = par2;
		blockZCoord = par3;
	}
	
	@Override public Object call()
	{
		return callBlockLocationInfo();
	}
	
	public String callBlockLocationInfo()
	{
		return CrashReportCategory.getLocationInfo(blockXCoord, blockYCoord, blockZCoord);
	}
}
