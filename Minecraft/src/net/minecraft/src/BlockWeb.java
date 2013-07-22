package net.minecraft.src;

import java.util.Random;

public class BlockWeb extends Block
{
	public BlockWeb(int p_i9100_1_)
	{
		super(p_i9100_1_, Material.web);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getRenderType()
	{
		return 1;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.silk.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		p_71869_5_.setInWeb();
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
