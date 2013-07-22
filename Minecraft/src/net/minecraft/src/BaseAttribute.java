package net.minecraft.src;

public abstract class BaseAttribute implements Attribute
{
	private final String field_111115_a;
	private final double field_111113_b;
	private boolean field_111114_c;
	
	protected BaseAttribute(String par1Str, double par2)
	{
		field_111115_a = par1Str;
		field_111113_b = par2;
		if(par1Str == null) throw new IllegalArgumentException("Name cannot be null!");
	}
	
	@Override public String func_111108_a()
	{
		return field_111115_a;
	}
	
	@Override public double func_111110_b()
	{
		return field_111113_b;
	}
	
	@Override public boolean func_111111_c()
	{
		return field_111114_c;
	}
	
	public BaseAttribute func_111112_a(boolean par1)
	{
		field_111114_c = par1;
		return this;
	}
	
	@Override public int hashCode()
	{
		return field_111115_a.hashCode();
	}
}
