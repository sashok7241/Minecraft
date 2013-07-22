package net.minecraft.src;

import java.util.List;

final class CreativeTabTools extends CreativeTabs
{
	CreativeTabTools(int p_i3640_1_, String p_i3640_2_)
	{
		super(p_i3640_1_, p_i3640_2_);
	}
	
	@Override public void displayAllReleventItems(List par1List)
	{
		super.displayAllReleventItems(par1List);
		addEnchantmentBooksToList(par1List, new EnumEnchantmentType[] { EnumEnchantmentType.digger });
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.axeIron.itemID;
	}
}
