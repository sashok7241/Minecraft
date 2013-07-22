package net.minecraft.src;

public final class ProfilerResult implements Comparable
{
	public double field_76332_a;
	public double field_76330_b;
	public String field_76331_c;
	
	public ProfilerResult(String par1Str, double par2, double par4)
	{
		field_76331_c = par1Str;
		field_76332_a = par2;
		field_76330_b = par4;
	}
	
	@Override public int compareTo(Object par1Obj)
	{
		return func_76328_a((ProfilerResult) par1Obj);
	}
	
	public int func_76328_a(ProfilerResult par1ProfilerResult)
	{
		return par1ProfilerResult.field_76332_a < field_76332_a ? -1 : par1ProfilerResult.field_76332_a > field_76332_a ? 1 : par1ProfilerResult.field_76331_c.compareTo(field_76331_c);
	}
	
	public int func_76329_a()
	{
		return (field_76331_c.hashCode() & 11184810) + 4473924;
	}
}
