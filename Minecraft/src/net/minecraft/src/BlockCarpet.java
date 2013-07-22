package net.minecraft.src;

import java.util.List;

public class BlockCarpet extends Block
{
	protected BlockCarpet(int par1)
	{
		super(par1, Material.field_111018_r);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
		func_111047_d(0);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return !par1World.isAirBlock(par2, par3 - 1, par4);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1;
	}
	
	private boolean func_111046_k(World par1World, int par2, int par3, int par4)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
			return false;
		} else return true;
	}
	
	protected void func_111047_d(int par1)
	{
		byte var2 = 0;
		float var3 = 1 * (1 + var2) / 16.0F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		byte var5 = 0;
		float var6 = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + var5 * var6, par4 + maxZ);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.cloth.getIcon(par1, par2);
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 0; var4 < 16; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		func_111046_k(par1World, par2, par3, par4);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		func_111047_d(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		func_111047_d(0);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
