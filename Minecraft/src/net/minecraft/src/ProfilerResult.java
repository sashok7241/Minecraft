package net.minecraft.src;

public final class ProfilerResult implements Comparable
{
	public double field_76332_a;
	public double field_76330_b;
	public String field_76331_c;
	
	public ProfilerResult(String p_i3421_1_, double p_i3421_2_, double p_i3421_4_)
	{
		field_76331_c = p_i3421_1_;
		field_76332_a = p_i3421_2_;
		field_76330_b = p_i3421_4_;
	}
	
	@Override public int compareTo(Object p_compareTo_1_)
	{
		return func_76328_a((ProfilerResult) p_compareTo_1_);
	}
	
	public int func_76328_a(ProfilerResult p_76328_1_)
	{
		return p_76328_1_.field_76332_a < field_76332_a ? -1 : p_76328_1_.field_76332_a > field_76332_a ? 1 : p_76328_1_.field_76331_c.compareTo(field_76331_c);
	}
	
	public int func_76329_a()
	{
		return (field_76331_c.hashCode() & 11184810) + 4473924;
	}
}
