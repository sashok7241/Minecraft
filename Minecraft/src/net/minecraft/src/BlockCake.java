package net.minecraft.src;

import java.util.Random;

public class BlockCake extends Block
{
	private Icon cakeTopIcon;
	private Icon cakeBottomIcon;
	private Icon field_94382_c;
	
	protected BlockCake(int par1)
	{
		super(par1, Material.cake);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid();
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return !super.canPlaceBlockAt(par1World, par2, par3, par4) ? false : canBlockStay(par1World, par2, par3, par4);
	}
	
	private void eatCakeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		if(par5EntityPlayer.canEat(false))
		{
			par5EntityPlayer.getFoodStats().addStats(2, 0.1F);
			int var6 = par1World.getBlockMetadata(par2, par3, par4) + 1;
			if(var6 >= 6)
			{
				par1World.setBlockToAir(par2, par3, par4);
			} else
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
			}
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		float var6 = 0.0625F;
		float var7 = (1 + var5 * 2) / 16.0F;
		float var8 = 0.5F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + var7, par3, par4 + var6, par2 + 1 - var6, par3 + var8 - var6, par4 + 1 - var6);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? cakeTopIcon : par1 == 0 ? cakeBottomIcon : par2 > 0 && par1 == 4 ? field_94382_c : blockIcon;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		float var6 = 0.0625F;
		float var7 = (1 + var5 * 2) / 16.0F;
		float var8 = 0.5F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + var7, par3, par4 + var6, par2 + 1 - var6, par3 + var8, par4 + 1 - var6);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.cake.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
		return true;
	}
	
	@Override public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("cake_side");
		field_94382_c = par1IconRegister.registerIcon("cake_inner");
		cakeTopIcon = par1IconRegister.registerIcon("cake_top");
		cakeBottomIcon = par1IconRegister.registerIcon("cake_bottom");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float var6 = 0.0625F;
		float var7 = (1 + var5 * 2) / 16.0F;
		float var8 = 0.5F;
		setBlockBounds(var7, 0.0F, var6, 1.0F - var6, var8, 1.0F - var6);
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.0625F;
		float var2 = 0.5F;
		setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
	}
}
