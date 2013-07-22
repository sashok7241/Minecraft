package net.minecraft.src;

final class CreativeTabBlock extends CreativeTabs
{
	CreativeTabBlock(int p_i3630_1_, String p_i3630_2_)
	{
		super(p_i3630_1_, p_i3630_2_);
	}
	
	@Override public int getTabIconItemIndex()
	{
		return Block.brick.blockID;
	}
}
