package net.minecraft.src;

import java.util.List;

public class BlockPistonBase extends Block
{
	private final boolean isSticky;
	private Icon innerTopIcon;
	private Icon bottomIcon;
	private Icon topIcon;
	
	public BlockPistonBase(int p_i9105_1_, boolean p_i9105_2_)
	{
		super(p_i9105_1_, Material.piston);
		isSticky = p_i9105_2_;
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
	}
	
	public void func_96479_b(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		setBlockBounds(par1, par2, par3, par4, par5, par6);
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = getOrientation(par2);
		return var3 > 5 ? topIcon : par1 == var3 ? !isExtended(par2) && minX <= 0.0D && minY <= 0.0D && minZ <= 0.0D && maxX >= 1.0D && maxY >= 1.0D && maxZ >= 1.0D ? topIcon : innerTopIcon : par1 == Facing.oppositeSide[var3] ? bottomIcon : blockIcon;
	}
	
	public Icon getPistonExtensionTexture()
	{
		return topIcon;
	}
	
	@Override public int getRenderType()
	{
		return 16;
	}
	
	private boolean isIndirectlyPowered(World p_72113_1_, int p_72113_2_, int p_72113_3_, int p_72113_4_, int p_72113_5_)
	{
		return p_72113_5_ != 0 && p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_ - 1, p_72113_4_, 0) ? true : p_72113_5_ != 1 && p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_ + 1, p_72113_4_, 1) ? true : p_72113_5_ != 2 && p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_, p_72113_4_ - 1, 2) ? true : p_72113_5_ != 3 && p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_, p_72113_4_ + 1, 3) ? true : p_72113_5_ != 5 && p_72113_1_.getIndirectPowerOutput(p_72113_2_ + 1, p_72113_3_, p_72113_4_, 5) ? true : p_72113_5_ != 4 && p_72113_1_.getIndirectPowerOutput(p_72113_2_ - 1, p_72113_3_, p_72113_4_, 4) ? true : p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_, p_72113_4_, 0) ? true : p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_ + 2, p_72113_4_, 1) ? true : p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_ + 1, p_72113_4_ - 1, 2) ? true : p_72113_1_.getIndirectPowerOutput(p_72113_2_, p_72113_3_ + 1, p_72113_4_ + 1, 3) ? true : p_72113_1_.getIndirectPowerOutput(p_72113_2_ - 1, p_72113_3_ + 1, p_72113_4_, 4) ? true : p_72113_1_.getIndirectPowerOutput(p_72113_2_ + 1, p_72113_3_ + 1, p_72113_4_, 5);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		return false;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(!p_71861_1_.isRemote && p_71861_1_.getBlockTileEntity(p_71861_2_, p_71861_3_, p_71861_4_) == null)
		{
			updatePistonState(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		}
	}
	
	@Override public boolean onBlockEventReceived(World p_71883_1_, int p_71883_2_, int p_71883_3_, int p_71883_4_, int p_71883_5_, int p_71883_6_)
	{
		if(!p_71883_1_.isRemote)
		{
			boolean var7 = isIndirectlyPowered(p_71883_1_, p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_);
			if(var7 && p_71883_5_ == 1)
			{
				p_71883_1_.setBlockMetadataWithNotify(p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_ | 8, 2);
				return false;
			}
			if(!var7 && p_71883_5_ == 0) return false;
		}
		if(p_71883_5_ == 0)
		{
			if(!tryExtend(p_71883_1_, p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_)) return false;
			p_71883_1_.setBlockMetadataWithNotify(p_71883_2_, p_71883_3_, p_71883_4_, p_71883_6_ | 8, 2);
			p_71883_1_.playSoundEffect(p_71883_2_ + 0.5D, p_71883_3_ + 0.5D, p_71883_4_ + 0.5D, "tile.piston.out", 0.5F, p_71883_1_.rand.nextFloat() * 0.25F + 0.6F);
		} else if(p_71883_5_ == 1)
		{
			TileEntity var16 = p_71883_1_.getBlockTileEntity(p_71883_2_ + Facing.offsetsXForSide[p_71883_6_], p_71883_3_ + Facing.offsetsYForSide[p_71883_6_], p_71883_4_ + Facing.offsetsZForSide[p_71883_6_]);
			if(var16 instanceof TileEntityPiston)
			{
				((TileEntityPiston) var16).clearPistonTileEntity();
			}
			p_71883_1_.setBlock(p_71883_2_, p_71883_3_, p_71883_4_, Block.pistonMoving.blockID, p_71883_6_, 3);
			p_71883_1_.setBlockTileEntity(p_71883_2_, p_71883_3_, p_71883_4_, BlockPistonMoving.getTileEntity(blockID, p_71883_6_, p_71883_6_, false, true));
			if(isSticky)
			{
				int var8 = p_71883_2_ + Facing.offsetsXForSide[p_71883_6_] * 2;
				int var9 = p_71883_3_ + Facing.offsetsYForSide[p_71883_6_] * 2;
				int var10 = p_71883_4_ + Facing.offsetsZForSide[p_71883_6_] * 2;
				int var11 = p_71883_1_.getBlockId(var8, var9, var10);
				int var12 = p_71883_1_.getBlockMetadata(var8, var9, var10);
				boolean var13 = false;
				if(var11 == Block.pistonMoving.blockID)
				{
					TileEntity var14 = p_71883_1_.getBlockTileEntity(var8, var9, var10);
					if(var14 instanceof TileEntityPiston)
					{
						TileEntityPiston var15 = (TileEntityPiston) var14;
						if(var15.getPistonOrientation() == p_71883_6_ && var15.isExtending())
						{
							var15.clearPistonTileEntity();
							var11 = var15.getStoredBlockID();
							var12 = var15.getBlockMetadata();
							var13 = true;
						}
					}
				}
				if(!var13 && var11 > 0 && canPushBlock(var11, p_71883_1_, var8, var9, var10, false) && (Block.blocksList[var11].getMobilityFlag() == 0 || var11 == Block.pistonBase.blockID || var11 == Block.pistonStickyBase.blockID))
				{
					p_71883_2_ += Facing.offsetsXForSide[p_71883_6_];
					p_71883_3_ += Facing.offsetsYForSide[p_71883_6_];
					p_71883_4_ += Facing.offsetsZForSide[p_71883_6_];
					p_71883_1_.setBlock(p_71883_2_, p_71883_3_, p_71883_4_, Block.pistonMoving.blockID, var12, 3);
					p_71883_1_.setBlockTileEntity(p_71883_2_, p_71883_3_, p_71883_4_, BlockPistonMoving.getTileEntity(var11, var12, p_71883_6_, false, false));
					p_71883_1_.setBlockToAir(var8, var9, var10);
				} else if(!var13)
				{
					p_71883_1_.setBlockToAir(p_71883_2_ + Facing.offsetsXForSide[p_71883_6_], p_71883_3_ + Facing.offsetsYForSide[p_71883_6_], p_71883_4_ + Facing.offsetsZForSide[p_71883_6_]);
				}
			} else
			{
				p_71883_1_.setBlockToAir(p_71883_2_ + Facing.offsetsXForSide[p_71883_6_], p_71883_3_ + Facing.offsetsYForSide[p_71883_6_], p_71883_4_ + Facing.offsetsZForSide[p_71883_6_]);
			}
			p_71883_1_.playSoundEffect(p_71883_2_ + 0.5D, p_71883_3_ + 0.5D, p_71883_4_ + 0.5D, "tile.piston.in", 0.5F, p_71883_1_.rand.nextFloat() * 0.15F + 0.6F);
		}
		return true;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = determineOrientation(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, p_71860_5_);
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
		if(!p_71860_1_.isRemote)
		{
			updatePistonState(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_);
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			updatePistonState(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("piston_side");
		topIcon = par1IconRegister.registerIcon(isSticky ? "piston_top_sticky" : "piston_top");
		innerTopIcon = par1IconRegister.registerIcon("piston_inner_top");
		bottomIcon = par1IconRegister.registerIcon("piston_bottom");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
		if(isExtended(var5))
		{
			switch(getOrientation(var5))
			{
				case 0:
					setBlockBounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 1:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
					break;
				case 2:
					setBlockBounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
					break;
				case 3:
					setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
					break;
				case 4:
					setBlockBounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					break;
				case 5:
					setBlockBounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
			}
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private boolean tryExtend(World p_72115_1_, int p_72115_2_, int p_72115_3_, int p_72115_4_, int p_72115_5_)
	{
		int var6 = p_72115_2_ + Facing.offsetsXForSide[p_72115_5_];
		int var7 = p_72115_3_ + Facing.offsetsYForSide[p_72115_5_];
		int var8 = p_72115_4_ + Facing.offsetsZForSide[p_72115_5_];
		int var9 = 0;
		while(true)
		{
			int var10;
			if(var9 < 13)
			{
				if(var7 <= 0 || var7 >= 255) return false;
				var10 = p_72115_1_.getBlockId(var6, var7, var8);
				if(var10 != 0)
				{
					if(!canPushBlock(var10, p_72115_1_, var6, var7, var8, true)) return false;
					if(Block.blocksList[var10].getMobilityFlag() != 1)
					{
						if(var9 == 12) return false;
						var6 += Facing.offsetsXForSide[p_72115_5_];
						var7 += Facing.offsetsYForSide[p_72115_5_];
						var8 += Facing.offsetsZForSide[p_72115_5_];
						++var9;
						continue;
					}
					Block.blocksList[var10].dropBlockAsItem(p_72115_1_, var6, var7, var8, p_72115_1_.getBlockMetadata(var6, var7, var8), 0);
					p_72115_1_.setBlockToAir(var6, var7, var8);
				}
			}
			var9 = var6;
			var10 = var7;
			int var11 = var8;
			int var12 = 0;
			int[] var13;
			int var14;
			int var15;
			int var16;
			for(var13 = new int[13]; var6 != p_72115_2_ || var7 != p_72115_3_ || var8 != p_72115_4_; var8 = var16)
			{
				var14 = var6 - Facing.offsetsXForSide[p_72115_5_];
				var15 = var7 - Facing.offsetsYForSide[p_72115_5_];
				var16 = var8 - Facing.offsetsZForSide[p_72115_5_];
				int var17 = p_72115_1_.getBlockId(var14, var15, var16);
				int var18 = p_72115_1_.getBlockMetadata(var14, var15, var16);
				if(var17 == blockID && var14 == p_72115_2_ && var15 == p_72115_3_ && var16 == p_72115_4_)
				{
					p_72115_1_.setBlock(var6, var7, var8, Block.pistonMoving.blockID, p_72115_5_ | (isSticky ? 8 : 0), 4);
					p_72115_1_.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(Block.pistonExtension.blockID, p_72115_5_ | (isSticky ? 8 : 0), p_72115_5_, true, false));
				} else
				{
					p_72115_1_.setBlock(var6, var7, var8, Block.pistonMoving.blockID, var18, 4);
					p_72115_1_.setBlockTileEntity(var6, var7, var8, BlockPistonMoving.getTileEntity(var17, var18, p_72115_5_, true, false));
				}
				var13[var12++] = var17;
				var6 = var14;
				var7 = var15;
			}
			var6 = var9;
			var7 = var10;
			var8 = var11;
			for(var12 = 0; var6 != p_72115_2_ || var7 != p_72115_3_ || var8 != p_72115_4_; var8 = var16)
			{
				var14 = var6 - Facing.offsetsXForSide[p_72115_5_];
				var15 = var7 - Facing.offsetsYForSide[p_72115_5_];
				var16 = var8 - Facing.offsetsZForSide[p_72115_5_];
				p_72115_1_.notifyBlocksOfNeighborChange(var14, var15, var16, var13[var12++]);
				var6 = var14;
				var7 = var15;
			}
			return true;
		}
	}
	
	private void updatePistonState(World p_72110_1_, int p_72110_2_, int p_72110_3_, int p_72110_4_)
	{
		int var5 = p_72110_1_.getBlockMetadata(p_72110_2_, p_72110_3_, p_72110_4_);
		int var6 = getOrientation(var5);
		if(var6 != 7)
		{
			boolean var7 = isIndirectlyPowered(p_72110_1_, p_72110_2_, p_72110_3_, p_72110_4_, var6);
			if(var7 && !isExtended(var5))
			{
				if(canExtend(p_72110_1_, p_72110_2_, p_72110_3_, p_72110_4_, var6))
				{
					p_72110_1_.addBlockEvent(p_72110_2_, p_72110_3_, p_72110_4_, blockID, 0, var6);
				}
			} else if(!var7 && isExtended(var5))
			{
				p_72110_1_.setBlockMetadataWithNotify(p_72110_2_, p_72110_3_, p_72110_4_, var6, 2);
				p_72110_1_.addBlockEvent(p_72110_2_, p_72110_3_, p_72110_4_, blockID, 1, var6);
			}
		}
	}
	
	private static boolean canExtend(World p_72112_0_, int p_72112_1_, int p_72112_2_, int p_72112_3_, int p_72112_4_)
	{
		int var5 = p_72112_1_ + Facing.offsetsXForSide[p_72112_4_];
		int var6 = p_72112_2_ + Facing.offsetsYForSide[p_72112_4_];
		int var7 = p_72112_3_ + Facing.offsetsZForSide[p_72112_4_];
		int var8 = 0;
		while(true)
		{
			if(var8 < 13)
			{
				if(var6 <= 0 || var6 >= 255) return false;
				int var9 = p_72112_0_.getBlockId(var5, var6, var7);
				if(var9 != 0)
				{
					if(!canPushBlock(var9, p_72112_0_, var5, var6, var7, true)) return false;
					if(Block.blocksList[var9].getMobilityFlag() != 1)
					{
						if(var8 == 12) return false;
						var5 += Facing.offsetsXForSide[p_72112_4_];
						var6 += Facing.offsetsYForSide[p_72112_4_];
						var7 += Facing.offsetsZForSide[p_72112_4_];
						++var8;
						continue;
					}
				}
			}
			return true;
		}
	}
	
	private static boolean canPushBlock(int p_72111_0_, World p_72111_1_, int p_72111_2_, int p_72111_3_, int p_72111_4_, boolean p_72111_5_)
	{
		if(p_72111_0_ == Block.obsidian.blockID) return false;
		else
		{
			if(p_72111_0_ != Block.pistonBase.blockID && p_72111_0_ != Block.pistonStickyBase.blockID)
			{
				if(Block.blocksList[p_72111_0_].getBlockHardness(p_72111_1_, p_72111_2_, p_72111_3_, p_72111_4_) == -1.0F) return false;
				if(Block.blocksList[p_72111_0_].getMobilityFlag() == 2) return false;
				if(Block.blocksList[p_72111_0_].getMobilityFlag() == 1)
				{
					if(!p_72111_5_) return false;
					return true;
				}
			} else if(isExtended(p_72111_1_.getBlockMetadata(p_72111_2_, p_72111_3_, p_72111_4_))) return false;
			return !(Block.blocksList[p_72111_0_] instanceof ITileEntityProvider);
		}
	}
	
	public static int determineOrientation(World p_72116_0_, int p_72116_1_, int p_72116_2_, int p_72116_3_, EntityLiving p_72116_4_)
	{
		if(MathHelper.abs((float) p_72116_4_.posX - p_72116_1_) < 2.0F && MathHelper.abs((float) p_72116_4_.posZ - p_72116_3_) < 2.0F)
		{
			double var5 = p_72116_4_.posY + 1.82D - p_72116_4_.yOffset;
			if(var5 - p_72116_2_ > 2.0D) return 1;
			if(p_72116_2_ - var5 > 0.0D) return 0;
		}
		int var7 = MathHelper.floor_double(p_72116_4_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		return var7 == 0 ? 2 : var7 == 1 ? 5 : var7 == 2 ? 3 : var7 == 3 ? 4 : 0;
	}
	
	public static Icon func_94496_b(String par0Str)
	{
		return par0Str == "piston_side" ? Block.pistonBase.blockIcon : par0Str == "piston_top" ? Block.pistonBase.topIcon : par0Str == "piston_top_sticky" ? Block.pistonStickyBase.topIcon : par0Str == "piston_inner_top" ? Block.pistonBase.innerTopIcon : null;
	}
	
	public static int getOrientation(int p_72117_0_)
	{
		return p_72117_0_ & 7;
	}
	
	public static boolean isExtended(int p_72114_0_)
	{
		return (p_72114_0_ & 8) != 0;
	}
}
