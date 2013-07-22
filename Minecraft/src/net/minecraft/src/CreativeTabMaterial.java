package net.minecraft.src;

final class CreativeTabMaterial extends CreativeTabs
{
	CreativeTabMaterial(int p_i3632_1_, String p_i3632_2_)
	{
		super(p_i3632_1_, p_i3632_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.stick.itemID;
	}
}
