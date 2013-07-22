package net.minecraft.src;

public class BlockTrapDoor extends Block
{
	protected BlockTrapDoor(int p_i4015_1_, Material p_i4015_2_)
	{
		super(p_i4015_1_, p_i4015_2_);
		float var3 = 0.5F;
		float var4 = 1.0F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var4, 0.5F + var3);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public boolean canPlaceBlockOnSide(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
	{
		if(p_71850_5_ == 0) return false;
		else if(p_71850_5_ == 1) return false;
		else
		{
			if(p_71850_5_ == 2)
			{
				++p_71850_4_;
			}
			if(p_71850_5_ == 3)
			{
				--p_71850_4_;
			}
			if(p_71850_5_ == 4)
			{
				++p_71850_2_;
			}
			if(p_71850_5_ == 5)
			{
				--p_71850_2_;
			}
			return isValidSupportBlock(p_71850_1_.getBlockId(p_71850_2_, p_71850_3_, p_71850_4_));
		}
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World p_71878_1_, int p_71878_2_, int p_71878_3_, int p_71878_4_, Vec3 p_71878_5_, Vec3 p_71878_6_)
	{
		setBlockBoundsBasedOnState(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_);
		return super.collisionRayTrace(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_, p_71878_5_, p_71878_6_);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return !isTrapdoorOpen(p_71918_1_.getBlockMetadata(p_71918_2_, p_71918_3_, p_71918_4_));
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
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
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(blockMaterial == Material.iron) return true;
		else
		{
			int var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
			p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var10 ^ 4, 2);
			p_71903_1_.playAuxSFXAtEntity(p_71903_5_, 1003, p_71903_2_, p_71903_3_, p_71903_4_, 0);
			return true;
		}
	}
	
	@Override public void onBlockClicked(World p_71921_1_, int p_71921_2_, int p_71921_3_, int p_71921_4_, EntityPlayer p_71921_5_)
	{
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		int var10 = 0;
		if(p_85104_5_ == 2)
		{
			var10 = 0;
		}
		if(p_85104_5_ == 3)
		{
			var10 = 1;
		}
		if(p_85104_5_ == 4)
		{
			var10 = 2;
		}
		if(p_85104_5_ == 5)
		{
			var10 = 3;
		}
		if(p_85104_5_ != 1 && p_85104_5_ != 0 && p_85104_7_ > 0.5F)
		{
			var10 |= 8;
		}
		return var10;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
			int var7 = p_71863_2_;
			int var8 = p_71863_4_;
			if((var6 & 3) == 0)
			{
				var8 = p_71863_4_ + 1;
			}
			if((var6 & 3) == 1)
			{
				--var8;
			}
			if((var6 & 3) == 2)
			{
				var7 = p_71863_2_ + 1;
			}
			if((var6 & 3) == 3)
			{
				--var7;
			}
			if(!isValidSupportBlock(p_71863_1_.getBlockId(var7, p_71863_3_, var8)))
			{
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
				dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var6, 0);
			}
			boolean var9 = p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_);
			if(var9 || p_71863_5_ > 0 && Block.blocksList[p_71863_5_].canProvidePower())
			{
				onPoweredBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var9);
			}
		}
	}
	
	public void onPoweredBlockChange(World p_72138_1_, int p_72138_2_, int p_72138_3_, int p_72138_4_, boolean p_72138_5_)
	{
		int var6 = p_72138_1_.getBlockMetadata(p_72138_2_, p_72138_3_, p_72138_4_);
		boolean var7 = (var6 & 4) > 0;
		if(var7 != p_72138_5_)
		{
			p_72138_1_.setBlockMetadataWithNotify(p_72138_2_, p_72138_3_, p_72138_4_, var6 ^ 4, 2);
			p_72138_1_.playAuxSFXAtEntity((EntityPlayer) null, 1003, p_72138_2_, p_72138_3_, p_72138_4_, 0);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		setBlockBoundsForBlockRender(p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_));
	}
	
	public void setBlockBoundsForBlockRender(int p_72139_1_)
	{
		float var2 = 0.1875F;
		if((p_72139_1_ & 8) != 0)
		{
			setBlockBounds(0.0F, 1.0F - var2, 0.0F, 1.0F, 1.0F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, var2, 1.0F);
		}
		if(isTrapdoorOpen(p_72139_1_))
		{
			if((p_72139_1_ & 3) == 0)
			{
				setBlockBounds(0.0F, 0.0F, 1.0F - var2, 1.0F, 1.0F, 1.0F);
			}
			if((p_72139_1_ & 3) == 1)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var2);
			}
			if((p_72139_1_ & 3) == 2)
			{
				setBlockBounds(1.0F - var2, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
			if((p_72139_1_ & 3) == 3)
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
	
	public static boolean isTrapdoorOpen(int p_72137_0_)
	{
		return (p_72137_0_ & 4) != 0;
	}
	
	private static boolean isValidSupportBlock(int p_72140_0_)
	{
		if(p_72140_0_ <= 0) return false;
		else
		{
			Block var1 = Block.blocksList[p_72140_0_];
			return var1 != null && var1.blockMaterial.isOpaque() && var1.renderAsNormalBlock() || var1 == Block.glowStone || var1 instanceof BlockHalfSlab || var1 instanceof BlockStairs;
		}
	}
}
