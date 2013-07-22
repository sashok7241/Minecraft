package net.minecraft.src;

public enum EnumRarity
{
	common(15, "Common"), uncommon(14, "Uncommon"), rare(11, "Rare"), epic(13, "Epic");
	public final int rarityColor;
	public final String rarityName;
	
	private EnumRarity(int par3, String par4Str)
	{
		rarityColor = par3;
		rarityName = par4Str;
	}
}
