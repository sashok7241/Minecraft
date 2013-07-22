package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockSapling extends BlockFlower
{
	public static final String[] WOOD_TYPES = new String[] { "oak", "spruce", "birch", "jungle" };
	private static final String[] field_94370_b = new String[] { "sapling", "sapling_spruce", "sapling_birch", "sapling_jungle" };
	private Icon[] saplingIcon;
	
	protected BlockSapling(int par1)
	{
		super(par1);
		float var2 = 0.4F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var2 * 2.0F, 0.5F + var2);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1 & 3;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		par2 &= 3;
		return saplingIcon[par2];
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4) & 3;
		Object var7 = null;
		int var8 = 0;
		int var9 = 0;
		boolean var10 = false;
		if(var6 == 1)
		{
			var7 = new WorldGenTaiga2(true);
		} else if(var6 == 2)
		{
			var7 = new WorldGenForest(true);
		} else if(var6 == 3)
		{
			for(var8 = 0; var8 >= -1; --var8)
			{
				for(var9 = 0; var9 >= -1; --var9)
				{
					if(isSameSapling(par1World, par2 + var8, par3, par4 + var9, 3) && isSameSapling(par1World, par2 + var8 + 1, par3, par4 + var9, 3) && isSameSapling(par1World, par2 + var8, par3, par4 + var9 + 1, 3) && isSameSapling(par1World, par2 + var8 + 1, par3, par4 + var9 + 1, 3))
					{
						var7 = new WorldGenHugeTrees(true, 10 + par5Random.nextInt(20), 3, 3);
						var10 = true;
						break;
					}
				}
				if(var7 != null)
				{
					break;
				}
			}
			if(var7 == null)
			{
				var9 = 0;
				var8 = 0;
				var7 = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
			}
		} else
		{
			var7 = new WorldGenTrees(true);
			if(par5Random.nextInt(10) == 0)
			{
				var7 = new WorldGenBigTree(true);
			}
		}
		if(var10)
		{
			par1World.setBlock(par2 + var8, par3, par4 + var9, 0, 0, 4);
			par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, 0, 0, 4);
			par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, 0, 0, 4);
			par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, 0, 0, 4);
		} else
		{
			par1World.setBlock(par2, par3, par4, 0, 0, 4);
		}
		if(!((WorldGenerator) var7).generate(par1World, par5Random, par2 + var8, par3, par4 + var9))
		{
			if(var10)
			{
				par1World.setBlock(par2 + var8, par3, par4 + var9, blockID, var6, 4);
				par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, blockID, var6, 4);
				par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, blockID, var6, 4);
				par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, blockID, var6, 4);
			} else
			{
				par1World.setBlock(par2, par3, par4, blockID, var6, 4);
			}
		}
	}
	
	public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5)
	{
		return par1World.getBlockId(par2, par3, par4) == blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
	}
	
	public void markOrGrowMarked(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		if((var6 & 8) == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 8, 4);
		} else
		{
			growTree(par1World, par2, par3, par4, par5Random);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		saplingIcon = new Icon[field_94370_b.length];
		for(int var2 = 0; var2 < saplingIcon.length; ++var2)
		{
			saplingIcon[var2] = par1IconRegister.registerIcon(field_94370_b[var2]);
		}
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			super.updateTick(par1World, par2, par3, par4, par5Random);
			if(par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
			{
				markOrGrowMarked(par1World, par2, par3, par4, par5Random);
			}
		}
	}
}
