package net.minecraft.src;

import java.util.List;

final class CreativeTabMisc extends CreativeTabs
{
	CreativeTabMisc(int p_i3637_1_, String p_i3637_2_)
	{
		super(p_i3637_1_, p_i3637_2_);
	}
	
	@Override public void displayAllReleventItems(List par1List)
	{
		super.displayAllReleventItems(par1List);
		addEnchantmentBooksToList(par1List, new EnumEnchantmentType[] { EnumEnchantmentType.all });
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.bucketLava.itemID;
	}
}
