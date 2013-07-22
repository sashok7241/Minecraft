package net.minecraft.src;

import java.util.Random;

public class BlockStationary extends BlockFluid
{
	protected BlockStationary(int par1, Material par2Material)
	{
		super(par1, par2Material);
		setTickRandomly(false);
		if(par2Material == Material.lava)
		{
			setTickRandomly(true);
		}
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return blockMaterial != Material.lava;
	}
	
	private boolean isFlammable(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockMaterial(par2, par3, par4).getCanBurn();
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		if(par1World.getBlockId(par2, par3, par4) == blockID)
		{
			setNotStationary(par1World, par2, par3, par4);
		}
	}
	
	private void setNotStationary(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlock(par2, par3, par4, blockID - 1, var5, 2);
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID - 1, tickRate(par1World));
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(blockMaterial == Material.lava)
		{
			int var6 = par5Random.nextInt(3);
			int var7;
			int var8;
			for(var7 = 0; var7 < var6; ++var7)
			{
				par2 += par5Random.nextInt(3) - 1;
				++par3;
				par4 += par5Random.nextInt(3) - 1;
				var8 = par1World.getBlockId(par2, par3, par4);
				if(var8 == 0)
				{
					if(isFlammable(par1World, par2 - 1, par3, par4) || isFlammable(par1World, par2 + 1, par3, par4) || isFlammable(par1World, par2, par3, par4 - 1) || isFlammable(par1World, par2, par3, par4 + 1) || isFlammable(par1World, par2, par3 - 1, par4) || isFlammable(par1World, par2, par3 + 1, par4))
					{
						par1World.setBlock(par2, par3, par4, Block.fire.blockID);
						return;
					}
				} else if(Block.blocksList[var8].blockMaterial.blocksMovement()) return;
			}
			if(var6 == 0)
			{
				var7 = par2;
				var8 = par4;
				for(int var9 = 0; var9 < 3; ++var9)
				{
					par2 = var7 + par5Random.nextInt(3) - 1;
					par4 = var8 + par5Random.nextInt(3) - 1;
					if(par1World.isAirBlock(par2, par3 + 1, par4) && isFlammable(par1World, par2, par3, par4))
					{
						par1World.setBlock(par2, par3 + 1, par4, Block.fire.blockID);
					}
				}
			}
		}
	}
}
