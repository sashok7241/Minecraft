package net.minecraft.src;

public class TextureMetadataSection implements MetadataSection
{
	private final boolean field_110482_a;
	private final boolean field_110481_b;
	
	public TextureMetadataSection(boolean par1, boolean par2)
	{
		field_110482_a = par1;
		field_110481_b = par2;
	}
	
	public boolean func_110479_a()
	{
		return field_110482_a;
	}
	
	public boolean func_110480_b()
	{
		return field_110481_b;
	}
}
