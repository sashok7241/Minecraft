package net.minecraft.src;

import java.util.Random;

public class BlockEnchantmentTable extends BlockContainer
{
	private Icon field_94461_a;
	private Icon field_94460_b;
	
	protected BlockEnchantmentTable(int p_i3941_1_)
	{
		super(p_i3941_1_, Material.rock);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setLightOpacity(0);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityEnchantmentTable();
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 0 ? field_94460_b : par1 == 1 ? field_94461_a : blockIcon;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			TileEntityEnchantmentTable var10 = (TileEntityEnchantmentTable) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
			p_71903_5_.displayGUIEnchantment(p_71903_2_, p_71903_3_, p_71903_4_, var10.func_94135_b() ? var10.func_94133_a() : null);
			return true;
		}
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		super.onBlockPlacedBy(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_, p_71860_6_);
		if(p_71860_6_.hasDisplayName())
		{
			((TileEntityEnchantmentTable) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_)).func_94134_a(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
		for(int var6 = par2 - 2; var6 <= par2 + 2; ++var6)
		{
			for(int var7 = par4 - 2; var7 <= par4 + 2; ++var7)
			{
				if(var6 > par2 - 2 && var6 < par2 + 2 && var7 == par4 - 1)
				{
					var7 = par4 + 2;
				}
				if(par5Random.nextInt(16) == 0)
				{
					for(int var8 = par3; var8 <= par3 + 1; ++var8)
					{
						if(par1World.getBlockId(var6, var8, var7) == Block.bookShelf.blockID)
						{
							if(!par1World.isAirBlock((var6 - par2) / 2 + par2, var8, (var7 - par4) / 2 + par4))
							{
								break;
							}
							par1World.spawnParticle("enchantmenttable", par2 + 0.5D, par3 + 2.0D, par4 + 0.5D, var6 - par2 + par5Random.nextFloat() - 0.5D, var8 - par3 - par5Random.nextFloat() - 1.0F, var7 - par4 + par5Random.nextFloat() - 0.5D);
						}
					}
				}
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("enchantment_side");
		field_94461_a = par1IconRegister.registerIcon("enchantment_top");
		field_94460_b = par1IconRegister.registerIcon("enchantment_bottom");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
