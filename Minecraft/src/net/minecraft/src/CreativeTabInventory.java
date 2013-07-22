package net.minecraft.src;

final class CreativeTabInventory extends CreativeTabs
{
	CreativeTabInventory(int p_i3633_1_, String p_i3633_2_)
	{
		super(p_i3633_1_, p_i3633_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Block.chest.blockID;
	}
}
