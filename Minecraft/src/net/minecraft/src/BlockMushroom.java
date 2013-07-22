package net.minecraft.src;

import java.util.Random;

public class BlockMushroom extends BlockFlower
{
	protected BlockMushroom(int par1)
	{
		super(par1);
		float var2 = 0.2F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var2 * 2.0F, 0.5F + var2);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		if(par3 >= 0 && par3 < 256)
		{
			int var5 = par1World.getBlockId(par2, par3 - 1, par4);
			return var5 == Block.mycelium.blockID || par1World.getFullBlockLightValue(par2, par3, par4) < 13 && canThisPlantGrowOnThisBlockID(var5);
		} else return false;
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return Block.opaqueCubeLookup[par1];
	}
	
	public boolean fertilizeMushroom(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		par1World.setBlockToAir(par2, par3, par4);
		WorldGenBigMushroom var7 = null;
		if(blockID == Block.mushroomBrown.blockID)
		{
			var7 = new WorldGenBigMushroom(0);
		} else if(blockID == Block.mushroomRed.blockID)
		{
			var7 = new WorldGenBigMushroom(1);
		}
		if(var7 != null && var7.generate(par1World, par5Random, par2, par3, par4)) return true;
		else
		{
			par1World.setBlock(par2, par3, par4, blockID, var6, 3);
			return false;
		}
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par5Random.nextInt(25) == 0)
		{
			byte var6 = 4;
			int var7 = 5;
			int var8;
			int var9;
			int var10;
			for(var8 = par2 - var6; var8 <= par2 + var6; ++var8)
			{
				for(var9 = par4 - var6; var9 <= par4 + var6; ++var9)
				{
					for(var10 = par3 - 1; var10 <= par3 + 1; ++var10)
					{
						if(par1World.getBlockId(var8, var10, var9) == blockID)
						{
							--var7;
							if(var7 <= 0) return;
						}
					}
				}
			}
			var8 = par2 + par5Random.nextInt(3) - 1;
			var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
			var10 = par4 + par5Random.nextInt(3) - 1;
			for(int var11 = 0; var11 < 4; ++var11)
			{
				if(par1World.isAirBlock(var8, var9, var10) && canBlockStay(par1World, var8, var9, var10))
				{
					par2 = var8;
					par3 = var9;
					par4 = var10;
				}
				var8 = par2 + par5Random.nextInt(3) - 1;
				var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
				var10 = par4 + par5Random.nextInt(3) - 1;
			}
			if(par1World.isAirBlock(var8, var9, var10) && canBlockStay(par1World, var8, var9, var10))
			{
				par1World.setBlock(var8, var9, var10, blockID, 0, 2);
			}
		}
	}
}
