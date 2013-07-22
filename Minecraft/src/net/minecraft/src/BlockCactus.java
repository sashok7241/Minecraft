package net.minecraft.src;

import java.util.Random;

public class BlockCactus extends Block
{
	private Icon cactusTopIcon;
	private Icon cactusBottomIcon;
	
	protected BlockCactus(int par1)
	{
		super(par1, Material.cactus);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		if(par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid()) return false;
		else if(par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid()) return false;
		else if(par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid()) return false;
		else if(par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid()) return false;
		else
		{
			int var5 = par1World.getBlockId(par2, par3 - 1, par4);
			return var5 == Block.cactus.blockID || var5 == Block.sand.blockID;
		}
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return !super.canPlaceBlockAt(par1World, par2, par3, par4) ? false : canBlockStay(par1World, par2, par3, par4);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1 - var5, par4 + 1 - var5);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? cactusTopIcon : par1 == 0 ? cactusBottomIcon : blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 13;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float var5 = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + var5, par3, par4 + var5, par2 + 1 - var5, par3 + 1, par4 + 1 - var5);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		par5Entity.attackEntityFrom(DamageSource.cactus, 1.0F);
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!canBlockStay(par1World, par2, par3, par4))
		{
			par1World.destroyBlock(par2, par3, par4, true);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(func_111023_E() + "_side");
		cactusTopIcon = par1IconRegister.registerIcon(func_111023_E() + "_top");
		cactusBottomIcon = par1IconRegister.registerIcon(func_111023_E() + "_bottom");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par1World.isAirBlock(par2, par3 + 1, par4))
		{
			int var6;
			for(var6 = 1; par1World.getBlockId(par2, par3 - var6, par4) == blockID; ++var6)
			{
				;
			}
			if(var6 < 3)
			{
				int var7 = par1World.getBlockMetadata(par2, par3, par4);
				if(var7 == 15)
				{
					par1World.setBlock(par2, par3 + 1, par4, blockID);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 4);
					onNeighborBlockChange(par1World, par2, par3 + 1, par4, blockID);
				} else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1, 4);
				}
			}
		}
	}
}
