package net.minecraft.src;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{
	protected BlockMobSpawner(int p_i9072_1_)
	{
		super(p_i9072_1_, Material.rock);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityMobSpawner();
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
		int var8 = 15 + p_71914_1_.rand.nextInt(15) + p_71914_1_.rand.nextInt(15);
		dropXpOnBlockBreak(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, var8);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return 0;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
}
