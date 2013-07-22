package net.minecraft.src;

public class McoPair
{
	private final Object field_98160_a;
	private final Object field_98159_b;
	
	protected McoPair(Object par1Obj, Object par2Obj)
	{
		field_98160_a = par1Obj;
		field_98159_b = par2Obj;
	}
	
	public Object func_100004_b()
	{
		return field_98159_b;
	}
	
	public Object func_100005_a()
	{
		return field_98160_a;
	}
	
	public static McoPair func_98157_a(Object par0Obj, Object par1Obj)
	{
		return new McoPair(par0Obj, par1Obj);
	}
}
