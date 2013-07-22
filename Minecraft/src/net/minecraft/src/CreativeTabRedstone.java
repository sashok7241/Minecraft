package net.minecraft.src;

final class CreativeTabRedstone extends CreativeTabs
{
	CreativeTabRedstone(int p_i3635_1_, String p_i3635_2_)
	{
		super(p_i3635_1_, p_i3635_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Item.redstone.itemID;
	}
}
