package net.minecraft.src;

import java.util.Random;

public class BlockReed extends Block
{
	protected BlockReed(int par1)
	{
		super(par1, Material.plants);
		float var2 = 0.375F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 1.0F, 0.5F + var2);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return canPlaceBlockAt(par1World, par2, par3, par4);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3 - 1, par4);
		return var5 == blockID ? true : var5 != Block.grass.blockID && var5 != Block.dirt.blockID && var5 != Block.sand.blockID ? false : par1World.getBlockMaterial(par2 - 1, par3 - 1, par4) == Material.water ? true : par1World.getBlockMaterial(par2 + 1, par3 - 1, par4) == Material.water ? true : par1World.getBlockMaterial(par2, par3 - 1, par4 - 1) == Material.water ? true : par1World.getBlockMaterial(par2, par3 - 1, par4 + 1) == Material.water;
	}
	
	protected final void checkBlockCoordValid(World par1World, int par2, int par3, int par4)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
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
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.reed.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.reed.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		checkBlockCoordValid(par1World, par2, par3, par4);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par1World.isAirBlock(par2, par3 + 1, par4))
		{
			int var6;
			for(var6 = 1; par1World.getBlockId(par2, par3 - var6, par4) == blockID; ++var6)
			{
				;
			}
			if(var6 < 3)
			{
				int var7 = par1World.getBlockMetadata(par2, par3, par4);
				if(var7 == 15)
				{
					par1World.setBlock(par2, par3 + 1, par4, blockID);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 4);
				} else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1, 4);
				}
			}
		}
	}
}
