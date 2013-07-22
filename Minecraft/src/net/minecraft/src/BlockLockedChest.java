package net.minecraft.src;

import java.util.Random;

public class BlockLockedChest extends Block
{
	protected BlockLockedChest(int p_i3967_1_)
	{
		super(p_i3967_1_, Material.wood);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return true;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
	}
}
