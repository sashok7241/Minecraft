package net.minecraft.src;

final class CreativeTabTransport extends CreativeTabs
{
	CreativeTabTransport(int p_i3636_1_, String p_i3636_2_)
	{
		super(p_i3636_1_, p_i3636_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Block.railPowered.blockID;
	}
}
