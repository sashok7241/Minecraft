package net.minecraft.src;

import java.util.Random;

public class BlockWeb extends Block
{
	public BlockWeb(int par1)
	{
		super(par1, Material.web);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	
	@Override public int getRenderType()
	{
		return 1;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.silk.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		par5Entity.setInWeb();
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
