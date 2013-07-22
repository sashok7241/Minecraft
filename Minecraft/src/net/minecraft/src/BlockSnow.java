package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block
{
	protected BlockSnow(int p_i9096_1_)
	{
		super(p_i9096_1_, Material.snow);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBoundsForSnowDepth(0);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		int var5 = p_71930_1_.getBlockId(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
		return var5 == 0 ? false : var5 == blockID && (p_71930_1_.getBlockMetadata(p_71930_2_, p_71930_3_ - 1, p_71930_4_) & 7) == 7 ? true : var5 != Block.leaves.blockID && !Block.blocksList[var5].isOpaqueCube() ? false : p_71930_1_.getBlockMaterial(p_71930_2_, p_71930_3_ - 1, p_71930_4_).blocksMovement();
	}
	
	private boolean canSnowStay(World p_72124_1_, int p_72124_2_, int p_72124_3_, int p_72124_4_)
	{
		if(!canPlaceBlockAt(p_72124_1_, p_72124_2_, p_72124_3_, p_72124_4_))
		{
			dropBlockAsItem(p_72124_1_, p_72124_2_, p_72124_3_, p_72124_4_, p_72124_1_.getBlockMetadata(p_72124_2_, p_72124_3_, p_72124_4_), 0);
			p_72124_1_.setBlockToAir(p_72124_2_, p_72124_3_, p_72124_4_);
			return false;
		} else return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		int var5 = p_71872_1_.getBlockMetadata(p_71872_2_, p_71872_3_, p_71872_4_) & 7;
		float var6 = 0.125F;
		return AxisAlignedBB.getAABBPool().getAABB(p_71872_2_ + minX, p_71872_3_ + minY, p_71872_4_ + minZ, p_71872_2_ + maxX, p_71872_3_ + var5 * var6, p_71872_4_ + maxZ);
	}
	
	@Override public void harvestBlock(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
	{
		int var7 = Item.snowball.itemID;
		int var8 = p_71893_6_ & 7;
		dropBlockAsItem_do(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(var7, var8 + 1, 0));
		p_71893_1_.setBlockToAir(p_71893_3_, p_71893_4_, p_71893_5_);
		p_71893_2_.addStat(StatList.mineBlockStatArray[blockID], 1);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.snowball.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		canSnowStay(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("snow");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		setBlockBoundsForSnowDepth(p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_));
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBoundsForSnowDepth(0);
	}
	
	protected void setBlockBoundsForSnowDepth(int p_96478_1_)
	{
		int var2 = p_96478_1_ & 7;
		float var3 = 2 * (1 + var2) / 16.0F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
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
