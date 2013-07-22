package net.minecraft.src;

import java.util.Random;

public class BlockMycelium extends Block
{
	private Icon field_94422_a;
	private Icon field_94421_b;
	
	protected BlockMycelium(int par1)
	{
		super(par1, Material.grass);
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
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.dirt.idDropped(0, par2Random, par3);
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
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			if(par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[par1World.getBlockId(par2, par3 + 1, par4)] > 2)
			{
				par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
			} else if(par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
			{
				for(int var6 = 0; var6 < 4; ++var6)
				{
					int var7 = par2 + par5Random.nextInt(3) - 1;
					int var8 = par3 + par5Random.nextInt(5) - 3;
					int var9 = par4 + par5Random.nextInt(3) - 1;
					int var10 = par1World.getBlockId(var7, var8 + 1, var9);
					if(par1World.getBlockId(var7, var8, var9) == Block.dirt.blockID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2)
					{
						par1World.setBlock(var7, var8, var9, blockID);
					}
				}
			}
		}
	}
}
