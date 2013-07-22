package net.minecraft.src;

public class BlockTrapDoor extends Block
{
	protected BlockTrapDoor(int par1, Material par2Material)
	{
		super(par1, par2Material);
		float var3 = 0.5F;
		float var4 = 1.0F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		if(par5 == 0) return false;
		else if(par5 == 1) return false;
		else
		{
			if(par5 == 2)
			{
				++par4;
			}
			if(par5 == 3)
			{
				--par4;
			}
			if(par5 == 4)
			{
				++par2;
			}
			if(par5 == 5)
			{
				--par2;
			}
			return isValidSupportBlock(par1World.getBlockId(par2, par3, par4));
		}
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return !isTrapdoorOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public int getRenderType()
	{
		return 0;
	}
	
	@Override public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		setBlockBoundsBasedOnState(par1World, par2, par3, par4);
		return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(blockMaterial == Material.iron) return true;
		else
		{
			int var10 = par1World.getBlockMetadata(par2, par3, par4);
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var10 ^ 4, 2);
			par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
			return true;
		}
	}
	
	@Override public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		int var10 = 0;
		if(par5 == 2)
		{
			var10 = 0;
		}
		if(par5 == 3)
		{
			var10 = 1;
		}
		if(par5 == 4)
		{
			var10 = 2;
		}
		if(par5 == 5)
		{
			var10 = 3;
		}
		if(par5 != 1 && par5 != 0 && par7 > 0.5F)
		{
			var10 |= 8;
		}
		return var10;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			int var7 = par2;
			int var8 = par4;
			if((var6 & 3) == 0)
			{
				var8 = par4 + 1;
			}
			if((var6 & 3) == 1)
			{
				--var8;
			}
			if((var6 & 3) == 2)
			{
				var7 = par2 + 1;
			}
			if((var6 & 3) == 3)
			{
				--var7;
			}
			if(!isValidSupportBlock(par1World.getBlockId(var7, par3, var8)))
			{
				par1World.setBlockToAir(par2, par3, par4);
				dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
			}
			boolean var9 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
			if(var9 || par5 > 0 && Block.blocksList[par5].canProvidePower())
			{
				onPoweredBlockChange(par1World, par2, par3, par4, var9);
			}
		}
	}
	
	public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		boolean var7 = (var6 & 4) > 0;
		if(var7 != par5)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 ^ 4, 2);
			par1World.playAuxSFXAtEntity((EntityPlayer) null, 1003, par2, par3, par4, 0);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		setBlockBoundsForBlockRender(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	public void setBlockBoundsForBlockRender(int par1)
	{
		float var2 = 0.1875F;
		if((par1 & 8) != 0)
		{
			setBlockBounds(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
		}
		if(isTrapdoorOpen(par1))
		{
			if((par1 & 3) == 0)
			{
				setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
			}
			if((par1 & 3) == 1)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
			}
			if((par1 & 3) == 2)
			{
				setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			if((par1 & 3) == 3)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, var2, 1.0F, 1.0F);
			}
		}
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.1875F;
		setBlockBounds(0.0F, 0.5F - var1 / 2.0F, 0.0F, 1.0F, 0.5F + var1 / 2.0F, 1.0F);
	}
	
	public static boolean isTrapdoorOpen(int par0)
	{
		return (par0 & 4) != 0;
	}
	
	private static boolean isValidSupportBlock(int par0)
	{
		if(par0 <= 0) return false;
		else
		{
			Block var1 = Block.blocksList[par0];
			return var1 != null && var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock() || var1 == Block.glowStone || var1 instanceof BlockHalfSlab || var1 instanceof BlockStairs;
		}
	}
}
