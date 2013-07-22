package net.minecraft.src;

import java.util.Random;

public class BlockSnow extends Block
{
	protected BlockSnow(int par1)
	{
		super(par1, Material.snow);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBoundsForSnowDepth(0);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		return var5 == 0 ? false : var5 == blockID && (par1World.getBlockMetadata(par2, par3 - 1, par4) & 7) == 7 ? true : var5 != Block.leaves.blockID && !Block.blocksList[var5].isOpaqueCube() ? false : par1World.getBlockMaterial(par2, par3 - 1, par4).blocksMovement();
	}
	
	private boolean canSnowStay(World par1World, int par2, int par3, int par4)
	{
		if(!canPlaceBlockAt(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
			return false;
		} else return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4) & 7;
		float var6 = 0.125F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + var5 * var6, par4 + maxZ);
	}
	
	@Override public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		int var7 = Item.snowball.itemID;
		int var8 = par6 & 7;
		dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(var7, var8 + 1, 0));
		par1World.setBlockToAir(par3, par4, par5);
		par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.snowball.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		canSnowStay(par1World, par2, par3, par4);
	}
	
	@Override public int quantityDropped(Random par1Random)
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		setBlockBoundsForSnowDepth(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBoundsForSnowDepth(0);
	}
	
	protected void setBlockBoundsForSnowDepth(int par1)
	{
		int var2 = par1 & 7;
		float var3 = 2 * (1 + var2) / 16.0F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11)
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
}
