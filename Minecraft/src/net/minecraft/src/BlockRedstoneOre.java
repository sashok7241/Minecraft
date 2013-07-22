package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneOre extends Block
{
	private boolean glowing;
	
	public BlockRedstoneOre(int p_i9086_1_, boolean p_i9086_2_)
	{
		super(p_i9086_1_, Material.rock);
		if(p_i9086_2_)
		{
			setTickRandomly(true);
		}
		glowing = p_i9086_2_;
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return new ItemStack(Block.oreRedstone);
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
		if(idDropped(p_71914_5_, p_71914_1_.rand, p_71914_7_) != blockID)
		{
			int var8 = 1 + p_71914_1_.rand.nextInt(5);
			dropXpOnBlockBreak(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, var8);
		}
	}
	
	private void glow(World p_72176_1_, int p_72176_2_, int p_72176_3_, int p_72176_4_)
	{
		sparkle(p_72176_1_, p_72176_2_, p_72176_3_, p_72176_4_);
		if(blockID == Block.oreRedstone.blockID)
		{
			p_72176_1_.setBlock(p_72176_2_, p_72176_3_, p_72176_4_, Block.oreRedstoneGlowing.blockID);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.redstone.itemID;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		glow(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_);
		return super.onBlockActivated(p_71903_1_, p_71903_2_, p_71903_3_, p_71903_4_, p_71903_5_, p_71903_6_, p_71903_7_, p_71903_8_, p_71903_9_);
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
		glow(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_);
		super.onBlockClicked(p_71921_1_, p_71921_2_, p_71921_3_, p_71921_4_, p_71921_5_);
	}
	
	@Override public void onEntityWalking(World p_71891_1_, int p_71891_2_, int p_71891_3_, int p_71891_4_, Entity p_71891_5_)
	{
		glow(p_71891_1_, p_71891_2_, p_71891_3_, p_71891_4_);
		super.onEntityWalking(p_71891_1_, p_71891_2_, p_71891_3_, p_71891_4_, p_71891_5_);
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 4 + p_71925_1_.nextInt(2);
	}
	
	@Override public int quantityDroppedWithBonus(int p_71910_1_, Random p_71910_2_)
	{
		return quantityDropped(p_71910_2_) + p_71910_2_.nextInt(p_71910_1_ + 1);
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(glowing)
		{
			sparkle(par1World, par2, par3, par4);
		}
	}
	
	private void sparkle(World p_72177_1_, int p_72177_2_, int p_72177_3_, int p_72177_4_)
	{
		Random var5 = p_72177_1_.rand;
		double var6 = 0.0625D;
		for(int var8 = 0; var8 < 6; ++var8)
		{
			double var9 = p_72177_2_ + var5.nextFloat();
			double var11 = p_72177_3_ + var5.nextFloat();
			double var13 = p_72177_4_ + var5.nextFloat();
			if(var8 == 0 && !p_72177_1_.isBlockOpaqueCube(p_72177_2_, p_72177_3_ + 1, p_72177_4_))
			{
				var11 = p_72177_3_ + 1 + var6;
			}
			if(var8 == 1 && !p_72177_1_.isBlockOpaqueCube(p_72177_2_, p_72177_3_ - 1, p_72177_4_))
			{
				var11 = p_72177_3_ + 0 - var6;
			}
			if(var8 == 2 && !p_72177_1_.isBlockOpaqueCube(p_72177_2_, p_72177_3_, p_72177_4_ + 1))
			{
				var13 = p_72177_4_ + 1 + var6;
			}
			if(var8 == 3 && !p_72177_1_.isBlockOpaqueCube(p_72177_2_, p_72177_3_, p_72177_4_ - 1))
			{
				var13 = p_72177_4_ + 0 - var6;
			}
			if(var8 == 4 && !p_72177_1_.isBlockOpaqueCube(p_72177_2_ + 1, p_72177_3_, p_72177_4_))
			{
				var9 = p_72177_2_ + 1 + var6;
			}
			if(var8 == 5 && !p_72177_1_.isBlockOpaqueCube(p_72177_2_ - 1, p_72177_3_, p_72177_4_))
			{
				var9 = p_72177_2_ + 0 - var6;
			}
			if(var9 < p_72177_2_ || var9 > p_72177_2_ + 1 || var11 < 0.0D || var11 > p_72177_3_ + 1 || var13 < p_72177_4_ || var13 > p_72177_4_ + 1)
			{
				p_72177_1_.spawnParticle("reddust", var9, var11, var13, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 30;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(blockID == Block.oreRedstoneGlowing.blockID)
		{
			p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.oreRedstone.blockID);
		}
	}
}
