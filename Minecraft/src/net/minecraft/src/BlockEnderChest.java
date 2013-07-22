package net.minecraft.src;

import java.util.Random;

public class BlockEnderChest extends BlockContainer
{
	protected BlockEnderChest(int p_i3942_1_)
	{
		super(p_i3942_1_, Material.rock);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}
	
	@Override protected boolean canSilkHarvest()
	{
		return true;
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityEnderChest();
	}
	
	@Override public int getRenderType()
	{
		return 22;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.obsidian.blockID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		InventoryEnderChest var10 = p_71903_5_.getInventoryEnderChest();
		TileEntityEnderChest var11 = (TileEntityEnderChest) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
		if(var10 != null && var11 != null)
		{
			if(p_71903_1_.isBlockNormalCube(p_71903_2_, p_71903_3_ + 1, p_71903_4_)) return true;
			else if(p_71903_1_.isRemote) return true;
			else
			{
				var10.setAssociatedChest(var11);
				p_71903_5_.displayGUIChest(var10);
				return true;
			}
		} else return true;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		byte var7 = 0;
		int var8 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(var8 == 0)
		{
			var7 = 2;
		}
		if(var8 == 1)
		{
			var7 = 5;
		}
		if(var8 == 2)
		{
			var7 = 3;
		}
		if(var8 == 3)
		{
			var7 = 4;
		}
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 8;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		for(int var6 = 0; var6 < 3; ++var6)
		{
			double var10000 = par2 + par5Random.nextFloat();
			double var9 = par3 + par5Random.nextFloat();
			var10000 = par4 + par5Random.nextFloat();
			double var13 = 0.0D;
			double var15 = 0.0D;
			double var17 = 0.0D;
			int var19 = par5Random.nextInt(2) * 2 - 1;
			int var20 = par5Random.nextInt(2) * 2 - 1;
			var13 = (par5Random.nextFloat() - 0.5D) * 0.125D;
			var15 = (par5Random.nextFloat() - 0.5D) * 0.125D;
			var17 = (par5Random.nextFloat() - 0.5D) * 0.125D;
			double var11 = par4 + 0.5D + 0.25D * var20;
			var17 = par5Random.nextFloat() * 1.0F * var20;
			double var7 = par2 + 0.5D + 0.25D * var19;
			var13 = par5Random.nextFloat() * 1.0F * var19;
			par1World.spawnParticle("portal", var7, var9, var11, var13, var15, var17);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("obsidian");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
}
