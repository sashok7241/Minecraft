package net.minecraft.src;

final class CreativeTabMisc extends CreativeTabs
{
	CreativeTabMisc(int par1, String par2Str)
	{
		super(par1, par2Str);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.compass.itemID;
	}
}
