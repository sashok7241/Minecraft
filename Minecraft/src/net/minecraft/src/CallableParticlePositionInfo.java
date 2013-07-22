package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableParticlePositionInfo implements Callable
{
	final double field_85101_a;
	final double field_85099_b;
	final double field_85100_c;
	final RenderGlobal globalRenderer;
	
	CallableParticlePositionInfo(RenderGlobal par1RenderGlobal, double par2, double par4, double par6)
	{
		globalRenderer = par1RenderGlobal;
		field_85101_a = par2;
		field_85099_b = par4;
		field_85100_c = par6;
	}
	
	@Override public Object call()
	{
		return callParticlePositionInfo();
	}
	
	public String callParticlePositionInfo()
	{
		return CrashReportCategory.func_85074_a(field_85101_a, field_85099_b, field_85100_c);
	}
}
