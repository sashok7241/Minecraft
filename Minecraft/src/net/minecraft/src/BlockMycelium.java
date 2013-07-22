package net.minecraft.src;

import java.util.Random;

public class BlockMycelium extends Block
{
	private Icon field_94422_a;
	private Icon field_94421_b;
	
	protected BlockMycelium(int p_i3973_1_)
	{
		super(p_i3973_1_, Material.grass);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par5 == 1) return field_94422_a;
		else if(par5 == 0) return Block.dirt.getBlockTextureFromSide(par5);
		else
		{
			Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
			return var6 != Material.snow && var6 != Material.craftedSnow ? blockIcon : field_94421_b;
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? field_94422_a : par1 == 0 ? Block.dirt.getBlockTextureFromSide(par1) : blockIcon;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.dirt.idDropped(0, p_71885_2_, p_71885_3_);
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
		if(par5Random.nextInt(10) == 0)
		{
			par1World.spawnParticle("townaura", par2 + par5Random.nextFloat(), par3 + 1.1F, par4 + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("mycel_side");
		field_94422_a = par1IconRegister.registerIcon("mycel_top");
		field_94421_b = par1IconRegister.registerIcon("snow_side");
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			if(p_71847_1_.getBlockLightValue(p_71847_2_, p_71847_3_ + 1, p_71847_4_) < 4 && Block.lightOpacity[p_71847_1_.getBlockId(p_71847_2_, p_71847_3_ + 1, p_71847_4_)] > 2)
			{
				p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.dirt.blockID);
			} else if(p_71847_1_.getBlockLightValue(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9)
			{
				for(int var6 = 0; var6 < 4; ++var6)
				{
					int var7 = p_71847_2_ + p_71847_5_.nextInt(3) - 1;
					int var8 = p_71847_3_ + p_71847_5_.nextInt(5) - 3;
					int var9 = p_71847_4_ + p_71847_5_.nextInt(3) - 1;
					int var10 = p_71847_1_.getBlockId(var7, var8 + 1, var9);
					if(p_71847_1_.getBlockId(var7, var8, var9) == Block.dirt.blockID && p_71847_1_.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
					{
						p_71847_1_.setBlock(var7, var8, var9, blockID);
					}
				}
			}
		}
	}
}
