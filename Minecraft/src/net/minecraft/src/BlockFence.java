package net.minecraft.src;

import java.util.List;

public class BlockFence extends Block
{
	private final String field_94464_a;
	
	public BlockFence(int par1, String par2Str, Material par3Material)
	{
		super(par1, par3Material);
		field_94464_a = par2Str;
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		boolean var8 = canConnectFenceTo(par1World, par2, par3, par4 - 1);
		boolean var9 = canConnectFenceTo(par1World, par2, par3, par4 + 1);
		boolean var10 = canConnectFenceTo(par1World, par2 - 1, par3, par4);
		boolean var11 = canConnectFenceTo(par1World, par2 + 1, par3, par4);
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
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
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
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
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
	
	public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockId(par2, par3, par4);
		if(var5 != blockID && var5 != Block.fenceGate.blockID)
		{
			Block var6 = Block.blocksList[var5];
			return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? var6.blockMaterial != Material.pumpkin : false;
		} else return true;
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		boolean var5 = canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
		boolean var6 = canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
		boolean var7 = canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
		boolean var8 = canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
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
	
	public static boolean isIdAFence(int par0)
	{
		return par0 == Block.fence.blockID || par0 == Block.netherFence.blockID;
	}
}
