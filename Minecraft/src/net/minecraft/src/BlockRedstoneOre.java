package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneOre extends Block
{
	private boolean glowing;
	
	public BlockRedstoneOre(int par1, boolean par2)
	{
		super(par1, Material.rock);
		if(par2)
		{
			setTickRandomly(true);
		}
		glowing = par2;
	}
	
	@Override protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(Block.oreRedstone);
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		if(idDropped(par5, par1World.rand, par7) != blockID)
		{
			int var8 = 1 + par1World.rand.nextInt(5);
			dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
		}
	}
	
	private void glow(World par1World, int par2, int par3, int par4)
	{
		sparkle(par1World, par2, par3, par4);
		if(blockID == Block.oreRedstone.blockID)
		{
			par1World.setBlock(par2, par3, par4, Block.oreRedstoneGlowing.blockID);
		}
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.redstone.itemID;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		glow(par1World, par2, par3, par4);
		return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}
	
	@Override public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		glow(par1World, par2, par3, par4);
		super.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}
	
	@Override public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		glow(par1World, par2, par3, par4);
		super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 4 + par1Random.nextInt(2);
	}
	
	@Override public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		return quantityDropped(par2Random) + par2Random.nextInt(par1 + 1);
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(glowing)
		{
			sparkle(par1World, par2, par3, par4);
		}
	}
	
	private void sparkle(World par1World, int par2, int par3, int par4)
	{
		Random var5 = par1World.rand;
		double var6 = 0.0625D;
		for(int var8 = 0; var8 < 6; ++var8)
		{
			double var9 = par2 + var5.nextFloat();
			double var11 = par3 + var5.nextFloat();
			double var13 = par4 + var5.nextFloat();
			if(var8 == 0 && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var11 = par3 + 1 + var6;
			}
			if(var8 == 1 && !par1World.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var11 = par3 + 0 - var6;
			}
			if(var8 == 2 && !par1World.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var13 = par4 + 1 + var6;
			}
			if(var8 == 3 && !par1World.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var13 = par4 + 0 - var6;
			}
			if(var8 == 4 && !par1World.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var9 = par2 + 1 + var6;
			}
			if(var8 == 5 && !par1World.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var9 = par2 + 0 - var6;
			}
			if(var9 < par2 || var9 > par2 + 1 || var11 < 0.0D || var11 > par3 + 1 || var13 < par4 || var13 > par4 + 1)
			{
				par1World.spawnParticle("reddust", var9, var11, var13, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override public int tickRate(World par1World)
	{
		return 30;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(blockID == Block.oreRedstoneGlowing.blockID)
		{
			par1World.setBlock(par2, par3, par4, Block.oreRedstone.blockID);
		}
	}
}
