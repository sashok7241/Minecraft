package net.minecraft.src;

final class CreativeTabDeco extends CreativeTabs
{
	CreativeTabDeco(int p_i3634_1_, String p_i3634_2_)
	{
		super(p_i3634_1_, p_i3634_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Block.plantRed.blockID;
	}
}
