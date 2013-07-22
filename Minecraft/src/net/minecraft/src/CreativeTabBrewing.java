package net.minecraft.src;

final class CreativeTabBrewing extends CreativeTabs
{
	CreativeTabBrewing(int par1, String par2Str)
	{
		super(par1, par2Str);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.potion.itemID;
	}
}
