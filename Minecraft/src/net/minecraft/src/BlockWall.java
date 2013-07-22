package net.minecraft.src;

import java.util.List;

public class BlockWall extends Block
{
	public static final String[] types = new String[] { "normal", "mossy" };
	
	public BlockWall(int p_i5108_1_, Block p_i5108_2_)
	{
		super(p_i5108_1_, p_i5108_2_.blockMaterial);
		setHardness(p_i5108_2_.blockHardness);
		setResistance(p_i5108_2_.blockResistance / 3.0F);
		setStepSound(p_i5108_2_.stepSound);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public boolean canConnectWallTo(IBlockAccess p_82538_1_, int p_82538_2_, int p_82538_3_, int p_82538_4_)
	{
		int var5 = p_82538_1_.getBlockId(p_82538_2_, p_82538_3_, p_82538_4_);
		if(var5 != blockID && var5 != Block.fenceGate.blockID)
		{
			Block var6 = Block.blocksList[var5];
			return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? var6.blockMaterial != Material.pumpkin : false;
		} else return true;
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_;
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return false;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		maxY = 1.5D;
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par2 == 1 ? Block.cobblestoneMossy.getBlockTextureFromSide(par1) : Block.cobblestone.getBlockTextureFromSide(par1);
	}
	
	@Override public int getRenderType()
	{
		return 32;
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		boolean var5 = canConnectWallTo(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_ - 1);
		boolean var6 = canConnectWallTo(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_ + 1);
		boolean var7 = canConnectWallTo(p_71902_1_, p_71902_2_ - 1, p_71902_3_, p_71902_4_);
		boolean var8 = canConnectWallTo(p_71902_1_, p_71902_2_ + 1, p_71902_3_, p_71902_4_);
		float var9 = 0.25F;
		float var10 = 0.75F;
		float var11 = 0.25F;
		float var12 = 0.75F;
		float var13 = 1.0F;
		if(var5)
		{
			var11 = 0.0F;
		}
		if(var6)
		{
			var12 = 1.0F;
		}
		if(var7)
		{
			var9 = 0.0F;
		}
		if(var8)
		{
			var10 = 1.0F;
		}
		if(var5 && var6 && !var7 && !var8)
		{
			var13 = 0.8125F;
			var9 = 0.3125F;
			var10 = 0.6875F;
		} else if(!var5 && !var6 && var7 && var8)
		{
			var13 = 0.8125F;
			var11 = 0.3125F;
			var12 = 0.6875F;
		}
		setBlockBounds(var9, 0.0F, var11, var10, var13, var12);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 0 ? super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5) : true;
	}
}
