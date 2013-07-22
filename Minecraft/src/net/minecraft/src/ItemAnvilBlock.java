package net.minecraft.src;

public class ItemAnvilBlock extends ItemMultiTextureTile
{
	public ItemAnvilBlock(Block p_i5081_1_)
	{
		super(p_i5081_1_.blockID - 256, p_i5081_1_, BlockAnvil.statuses);
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_ << 2;
	}
}
