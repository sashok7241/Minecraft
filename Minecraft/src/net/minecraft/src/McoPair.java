package net.minecraft.src;

public class McoPair
{
	private final Object field_98160_a;
	private final Object field_98159_b;
	
	protected McoPair(Object p_i11018_1_, Object p_i11018_2_)
	{
		field_98160_a = p_i11018_1_;
		field_98159_b = p_i11018_2_;
	}
	
	public Object func_100004_b()
	{
		return field_98159_b;
	}
	
	public Object func_100005_a()
	{
		return field_98160_a;
	}
	
	public static McoPair func_98157_a(Object p_98157_0_, Object p_98157_1_)
	{
		return new McoPair(p_98157_0_, p_98157_1_);
	}
}
