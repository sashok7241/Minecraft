package net.minecraft.src;

import java.util.List;

public class BlockFence extends Block
{
	private final String field_94464_a;
	
	public BlockFence(int p_i9055_1_, String p_i9055_2_, Material p_i9055_3_)
	{
		super(p_i9055_1_, p_i9055_3_);
		field_94464_a = p_i9055_2_;
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		boolean var8 = canConnectFenceTo(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_ - 1);
		boolean var9 = canConnectFenceTo(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_ + 1);
		boolean var10 = canConnectFenceTo(p_71871_1_, p_71871_2_ - 1, p_71871_3_, p_71871_4_);
		boolean var11 = canConnectFenceTo(p_71871_1_, p_71871_2_ + 1, p_71871_3_, p_71871_4_);
		float var12 = 0.375F;
		float var13 = 0.625F;
		float var14 = 0.375F;
		float var15 = 0.625F;
		if(var8)
		{
			var14 = 0.0F;
		}
		if(var9)
		{
			var15 = 1.0F;
		}
		if(var8 || var9)
		{
			setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
		var14 = 0.375F;
		var15 = 0.625F;
		if(var10)
		{
			var12 = 0.0F;
		}
		if(var11)
		{
			var13 = 1.0F;
		}
		if(var10 || var11 || !var8 && !var9)
		{
			setBlockBounds(var12, 0.0F, var14, var13, 1.5F, var15);
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
		if(var8)
		{
			var14 = 0.0F;
		}
		if(var9)
		{
			var15 = 1.0F;
		}
		setBlockBounds(var12, 0.0F, var14, var13, 1.0F, var15);
	}
	
	public boolean canConnectFenceTo(IBlockAccess p_72250_1_, int p_72250_2_, int p_72250_3_, int p_72250_4_)
	{
		int var5 = p_72250_1_.getBlockId(p_72250_2_, p_72250_3_, p_72250_4_);
		if(var5 != blockID && var5 != Block.fenceGate.blockID)
		{
			Block var6 = Block.blocksList[var5];
			return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? var6.blockMaterial != Material.pumpkin : false;
		} else return true;
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return false;
	}
	
	@Override public int getRenderType()
	{
		return 11;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(field_94464_a);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		boolean var5 = canConnectFenceTo(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_ - 1);
		boolean var6 = canConnectFenceTo(p_71902_1_, p_71902_2_, p_71902_3_, p_71902_4_ + 1);
		boolean var7 = canConnectFenceTo(p_71902_1_, p_71902_2_ - 1, p_71902_3_, p_71902_4_);
		boolean var8 = canConnectFenceTo(p_71902_1_, p_71902_2_ + 1, p_71902_3_, p_71902_4_);
		float var9 = 0.375F;
		float var10 = 0.625F;
		float var11 = 0.375F;
		float var12 = 0.625F;
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
		setBlockBounds(var9, 0.0F, var11, var10, 1.0F, var12);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}
	
	public static boolean isIdAFence(int p_72249_0_)
	{
		return p_72249_0_ == Block.fence.blockID || p_72249_0_ == Block.netherFence.blockID;
	}
}
