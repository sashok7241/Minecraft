package net.minecraft.src;

public class BlockFenceGate extends BlockDirectional
{
	public BlockFenceGate(int par1)
	{
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return !par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid() ? false : super.canPlaceBlockAt(par1World, par2, par3, par4);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return isFenceGateOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		return isFenceGateOpen(var5) ? null : var5 != 2 && var5 != 0 ? AxisAlignedBB.getAABBPool().getAABB(par2 + 0.375F, par3, par4, par2 + 0.625F, par3 + 1.5F, par4 + 1) : AxisAlignedBB.getAABBPool().getAABB(par2, par3, par4 + 0.375F, par2 + 1, par3 + 1.5F, par4 + 0.625F);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getBlockTextureFromSide(par1);
	}
	
	@Override public int getRenderType()
	{
		return 21;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		int var10 = par1World.getBlockMetadata(par2, par3, par4);
		if(isFenceGateOpen(var10))
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 & -5, 2);
		} else
		{
			int var11 = (MathHelper.floor_double(par5EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
			int var12 = getDirection(var10);
			if(var12 == (var11 + 2) % 4)
			{
				var10 = var11;
			}
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 | 4, 2);
		}
		par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
		return true;
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int var7 = (MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			boolean var7 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
			if(var7 || par5 > 0 && Block.blocksList[par5].canProvidePower())
			{
				if(var7 && !isFenceGateOpen(var6))
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 4, 2);
					par1World.playAuxSFXAtEntity((EntityPlayer) null, 1003, par2, par3, par4, 0);
				} else if(!var7 && isFenceGateOpen(var6))
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & -5, 2);
					par1World.playAuxSFXAtEntity((EntityPlayer) null, 1003, par2, par3, par4, 0);
				}
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = getDirection(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
		if(var5 != 2 && var5 != 0)
		{
			setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
		}
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}
	
	public static boolean isFenceGateOpen(int par0)
	{
		return (par0 & 4) != 0;
	}
}
