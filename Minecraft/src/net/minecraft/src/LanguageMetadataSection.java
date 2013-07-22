package net.minecraft.src;

import java.util.Collection;

public class LanguageMetadataSection implements MetadataSection
{
	private final Collection field_135019_a;
	
	public LanguageMetadataSection(Collection par1Collection)
	{
		field_135019_a = par1Collection;
	}
	
	public Collection func_135018_a()
	{
		return field_135019_a;
	}
}
