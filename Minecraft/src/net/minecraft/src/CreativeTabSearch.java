package net.minecraft.src;

final class CreativeTabSearch extends CreativeTabs
{
	CreativeTabSearch(int p_i3638_1_, String p_i3638_2_)
	{
		super(p_i3638_1_, p_i3638_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.compass.itemID;
	}
}
