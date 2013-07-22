package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableParticlePositionInfo implements Callable
{
	final double field_85101_a;
	final double field_85099_b;
	final double field_85100_c;
	final RenderGlobal globalRenderer;
	
	CallableParticlePositionInfo(RenderGlobal p_i6801_1_, double p_i6801_2_, double p_i6801_4_, double p_i6801_6_)
	{
		globalRenderer = p_i6801_1_;
		field_85101_a = p_i6801_2_;
		field_85099_b = p_i6801_4_;
		field_85100_c = p_i6801_6_;
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
