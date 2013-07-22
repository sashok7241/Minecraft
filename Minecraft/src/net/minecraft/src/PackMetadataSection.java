package net.minecraft.src;

public class PackMetadataSection implements MetadataSection
{
	private final String field_110464_a;
	private final int field_110463_b;
	
	public PackMetadataSection(String par1Str, int par2)
	{
		field_110464_a = par1Str;
		field_110463_b = par2;
	}
	
	public String func_110461_a()
	{
		return field_110464_a;
	}
	
	public int func_110462_b()
	{
		return field_110463_b;
	}
}
