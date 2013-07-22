package net.minecraft.src;

final class CreativeTabFood extends CreativeTabs
{
	CreativeTabFood(int p_i3639_1_, String p_i3639_2_)
	{
		super(p_i3639_1_, p_i3639_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.appleRed.itemID;
	}
}
