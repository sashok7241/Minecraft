package net.minecraft.src;

import java.util.Random;

public class BlockGrass extends Block
{
	private Icon iconGrassTop;
	private Icon iconSnowSide;
	private Icon iconGrassSideOverlay;
	
	protected BlockGrass(int p_i3952_1_)
	{
		super(p_i3952_1_, Material.grass);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = 0;
		int var6 = 0;
		int var7 = 0;
		for(int var8 = -1; var8 <= 1; ++var8)
		{
			for(int var9 = -1; var9 <= 1; ++var9)
			{
				int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeGrassColor();
				var5 += (var10 & 16711680) >> 16;
				var6 += (var10 & 65280) >> 8;
				var7 += var10 & 255;
			}
		}
		return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
	}
	
	@Override public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerGrass.getGrassColor(var1, var3);
	}
	
	@Override public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par5 == 1) return iconGrassTop;
		else if(par5 == 0) return Block.dirt.getBlockTextureFromSide(par5);
		else
		{
			Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
			return var6 != Material.snow && var6 != Material.craftedSnow ? blockIcon : iconSnowSide;
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? iconGrassTop : par1 == 0 ? Block.dirt.getBlockTextureFromSide(par1) : blockIcon;
	}
	
	@Override public int getRenderColor(int par1)
	{
		return getBlockColor();
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.dirt.idDropped(0, p_71885_2_, p_71885_3_);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("grass_side");
		iconGrassTop = par1IconRegister.registerIcon("grass_top");
		iconSnowSide = par1IconRegister.registerIcon("snow_side");
		iconGrassSideOverlay = par1IconRegister.registerIcon("grass_side_overlay");
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
						p_71847_1_.setBlock(var7, var8, var9, Block.grass.blockID);
					}
				}
			}
		}
	}
	
	public static Icon getIconSideOverlay()
	{
		return Block.grass.iconGrassSideOverlay;
	}
}
