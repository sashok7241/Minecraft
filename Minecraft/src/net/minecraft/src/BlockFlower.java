package net.minecraft.src;

import java.util.Random;

public class BlockFlower extends Block
{
	protected BlockFlower(int par1)
	{
		this(par1, Material.plants);
	}
	
	protected BlockFlower(int par1, Material par2Material)
	{
		super(par1, par2Material);
		setTickRandomly(true);
		float var3 = 0.2F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}
	
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID;
	}
	
	protected final void checkFlowerChange(World par1World, int par2, int par3, int par4)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlock(par2, par3, par4, 0, 0, 2);
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	
	@Override public int getRenderType()
	{
		return 1;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		checkFlowerChange(par1World, par2, par3, par4);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		checkFlowerChange(par1World, par2, par3, par4);
	}
}
