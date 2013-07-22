package net.minecraft.src;

import java.util.List;

final class CreativeTabCombat extends CreativeTabs
{
	CreativeTabCombat(int par1, String par2Str)
	{
		super(par1, par2Str);
	}
	
	@Override public void displayAllReleventItems(List par1List)
	{
		super.displayAllReleventItems(par1List);
		addEnchantmentBooksToList(par1List, new EnumEnchantmentType[] { EnumEnchantmentType.armor, EnumEnchantmentType.armor_feet, EnumEnchantmentType.armor_head, EnumEnchantmentType.armor_legs, EnumEnchantmentType.armor_torso, EnumEnchantmentType.bow, EnumEnchantmentType.weapon });
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.swordGold.itemID;
	}
}