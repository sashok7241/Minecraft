package net.minecraft.src;

public class BlockFenceGate extends BlockDirectional
{
	public BlockFenceGate(int p_i9054_1_)
	{
		super(p_i9054_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return !p_71930_1_.getBlockMaterial(p_71930_2_, p_71930_3_ - 1, p_71930_4_).isSolid() ? false : super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return isFenceGateOpen(p_71918_1_.getBlockMetadata(p_71918_2_, p_71918_3_, p_71918_4_));
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		int var5 = p_71872_1_.getBlockMetadata(p_71872_2_, p_71872_3_, p_71872_4_);
		return isFenceGateOpen(var5) ? null : var5 != 2 && var5 != 0 ? AxisAlignedBB.getAABBPool().getAABB(p_71872_2_ + 0.375F, p_71872_3_, p_71872_4_, p_71872_2_ + 0.625F, p_71872_3_ + 1.5F, p_71872_4_ + 1) : AxisAlignedBB.getAABBPool().getAABB(p_71872_2_, p_71872_3_, p_71872_4_ + 0.375F, p_71872_2_ + 1, p_71872_3_ + 1.5F, p_71872_4_ + 0.625F);
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
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		int var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
		if(isFenceGateOpen(var10))
		{
			p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var10 & -5, 2);
		} else
		{
			int var11 = (MathHelper.floor_double(p_71903_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
			int var12 = getDirection(var10);
			if(var12 == (var11 + 2) % 4)
			{
				var10 = var11;
			}
			p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var10 | 4, 2);
		}
		p_71903_1_.playAuxSFXAtEntity(p_71903_5_, 1003, p_71903_2_, p_71903_3_, p_71903_4_, 0);
		return true;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = (MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) % 4;
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
			boolean var7 = p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_);
			if(var7 || p_71863_5_ > 0 && Block.blocksList[p_71863_5_].canProvidePower())
			{
				if(var7 && !isFenceGateOpen(var6))
				{
					p_71863_1_.setBlockMetadataWithNotify(p_71863_2_, p_71863_3_, p_71863_4_, var6 | 4, 2);
					p_71863_1_.playAuxSFXAtEntity((EntityPlayer) null, 1003, p_71863_2_, p_71863_3_, p_71863_4_, 0);
				} else if(!var7 && isFenceGateOpen(var6))
				{
					p_71863_1_.setBlockMetadataWithNotify(p_71863_2_, p_71863_3_, p_71863_4_, var6 & -5, 2);
					p_71863_1_.playAuxSFXAtEntity((EntityPlayer) null, 1003, p_71863_2_, p_71863_3_, p_71863_4_, 0);
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = getDirection(p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_));
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
	
	public static boolean isFenceGateOpen(int p_72224_0_)
	{
		return (p_72224_0_ & 4) != 0;
	}
}
