package net.minecraft.src;

import java.util.Random;

public class BlockSnowBlock extends Block
{
	protected BlockSnowBlock(int p_i9090_1_)
	{
		super(p_i9090_1_, Material.craftedSnow);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.snowball.itemID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 4;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(p_71847_1_.getSavedLightValue(EnumSkyBlock.Block, p_71847_2_, p_71847_3_, p_71847_4_) > 11)
		{
			dropBlockAsItem(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_), 0);
			p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
		}
	}
}
