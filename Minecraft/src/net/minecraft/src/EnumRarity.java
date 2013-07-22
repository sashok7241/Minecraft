package net.minecraft.src;

public enum EnumRarity
{
	common(15, "Common"), uncommon(14, "Uncommon"), rare(11, "Rare"), epic(13, "Epic");
	public final int rarityColor;
	public final String rarityName;
	
	private EnumRarity(int p_i3676_3_, String p_i3676_4_)
	{
		rarityColor = p_i3676_3_;
		rarityName = p_i3676_4_;
	}
}
