package net.minecraft.src;

import java.util.List;

public class BlockLilyPad extends BlockFlower
{
	protected BlockLilyPad(int par1)
	{
		super(par1);
		float var2 = 0.5F;
		float var3 = 0.015625F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var3, 0.5F + var2);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		if(par7Entity == null || !(par7Entity instanceof EntityBoat))
		{
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return par3 >= 0 && par3 < 256 ? par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water && par1World.getBlockMetadata(par2, par3 - 1, par4) == 0 : false;
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == Block.waterStill.blockID;
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 2129968;
	}
	
	@Override public int getBlockColor()
	{
		return 2129968;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getAABBPool().getAABB(par2 + minX, par3 + minY, par4 + minZ, par2 + maxX, par3 + maxY, par4 + maxZ);
	}
	
	@Override public int getRenderColor(int par1)
	{
		return 2129968;
	}
	
	@Override public int getRenderType()
	{
		return 23;
	}
}
