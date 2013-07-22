package net.minecraft.src;

final class CreativeTabSearch extends CreativeTabs
{
	CreativeTabSearch(int par1, String par2Str)
	{
		super(par1, par2Str);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.compass.itemID;
	}
}
