package net.minecraft.src;

import java.util.Random;

public class BlockCake extends Block
{
	private Icon cakeTopIcon;
	private Icon cakeBottomIcon;
	private Icon field_94382_c;
	
	protected BlockCake(int p_i9044_1_)
	{
		super(p_i9044_1_, Material.cake);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		return p_71854_1_.getBlockMaterial(p_71854_2_, p_71854_3_ - 1, p_71854_4_).isSolid();
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return !super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) ? false : canBlockStay(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	private void eatCakeSlice(World p_72259_1_, int p_72259_2_, int p_72259_3_, int p_72259_4_, EntityPlayer p_72259_5_)
	{
		if(p_72259_5_.canEat(false))
		{
			p_72259_5_.getFoodStats().addStats(2, 0.1F);
			int var6 = p_72259_1_.getBlockMetadata(p_72259_2_, p_72259_3_, p_72259_4_) + 1;
			if(var6 >= 6)
			{
				p_72259_1_.setBlockToAir(p_72259_2_, p_72259_3_, p_72259_4_);
			} else
			{
				p_72259_1_.setBlockMetadataWithNotify(p_72259_2_, p_72259_3_, p_72259_4_, var6, 2);
			}
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		int var5 = p_71872_1_.getBlockMetadata(p_71872_2_, p_71872_3_, p_71872_4_);
		float var6 = 0.0625F;
		float var7 = (1 + var5 * 2) / 16.0F;
		float var8 = 0.5F;
		return AxisAlignedBB.getAABBPool().getAABB(p_71872_2_ + var7, p_71872_3_, p_71872_4_ + var6, p_71872_2_ + 1 - var6, p_71872_3_ + var8 - var6, p_71872_4_ + 1 - var6);
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
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
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
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		eatCakeSlice(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, p_71903_5_);
		return true;
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
		eatCakeSlice(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_, p_71921_5_);
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!canBlockStay(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
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
