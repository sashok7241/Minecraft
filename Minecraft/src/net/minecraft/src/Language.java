package net.minecraft.src;

public class Language implements Comparable
{
	private final String field_135039_a;
	private final String field_135037_b;
	private final String field_135038_c;
	private final boolean field_135036_d;
	
	public Language(String par1Str, String par2Str, String par3Str, boolean par4)
	{
		field_135039_a = par1Str;
		field_135037_b = par2Str;
		field_135038_c = par3Str;
		field_135036_d = par4;
	}
	
	@Override public int compareTo(Object par1Obj)
	{
		return func_135033_a((Language) par1Obj);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		return this == par1Obj ? true : !(par1Obj instanceof Language) ? false : field_135039_a.equals(((Language) par1Obj).field_135039_a);
	}
	
	public int func_135033_a(Language par1Language)
	{
		return field_135039_a.compareTo(par1Language.field_135039_a);
	}
	
	public String func_135034_a()
	{
		return field_135039_a;
	}
	
	public boolean func_135035_b()
	{
		return field_135036_d;
	}
	
	@Override public int hashCode()
	{
		return field_135039_a.hashCode();
	}
	
	@Override public String toString()
	{
		return String.format("%s (%s)", new Object[] { field_135038_c, field_135037_b });
	}
}
