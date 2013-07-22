package net.minecraft.src;

import java.util.Random;

public class BlockGlowStone extends Block
{
	public BlockGlowStone(int p_i9070_1_, Material p_i9070_2_)
	{
		super(p_i9070_1_, p_i9070_2_);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.glowstone.itemID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 2 + p_71925_1_.nextInt(3);
	}
	
	@Override public int quantityDroppedWithBonus(int p_71910_1_, Random p_71910_2_)
	{
		return MathHelper.clamp_int(quantityDropped(p_71910_2_) + p_71910_2_.nextInt(p_71910_1_ + 1), 1, 4);
	}
}
