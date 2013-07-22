package net.minecraft.src;

import java.util.concurrent.Callable;

final class CallableBlockLocation implements Callable
{
	final int blockXCoord;
	final int blockYCoord;
	final int blockZCoord;
	
	CallableBlockLocation(int p_i6807_1_, int p_i6807_2_, int p_i6807_3_)
	{
		blockXCoord = p_i6807_1_;
		blockYCoord = p_i6807_2_;
		blockZCoord = p_i6807_3_;
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
