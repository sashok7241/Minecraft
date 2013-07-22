package net.minecraft.src;

import java.util.Random;

public class BlockGlowStone extends Block
{
	public BlockGlowStone(int par1, Material par2Material)
	{
		super(par1, par2Material);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.glowstone.itemID;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 2 + par1Random.nextInt(3);
	}
	
	@Override public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		return MathHelper.clamp_int(quantityDropped(par2Random) + par2Random.nextInt(par1 + 1), 1, 4);
	}
}
