package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockRedstoneWire extends Block
{
	private boolean wiresProvidePower = true;
	private Set blocksNeedingUpdate = new HashSet();
	private Icon field_94413_c;
	private Icon field_94410_cO;
	private Icon field_94411_cP;
	private Icon field_94412_cQ;
	
	public BlockRedstoneWire(int p_i9085_1_)
	{
		super(p_i9085_1_, Material.circuits);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		if(!p_71852_1_.isRemote)
		{
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_ + 1, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_ - 1, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_ + 1, p_71852_3_, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_ - 1, p_71852_3_, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_, p_71852_4_ + 1, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_, p_71852_4_ - 1, blockID);
			updateAndPropagateCurrentStrength(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
			notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_ - 1, p_71852_3_, p_71852_4_);
			notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_ + 1, p_71852_3_, p_71852_4_);
			notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_ - 1);
			notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_ + 1);
			if(p_71852_1_.isBlockNormalCube(p_71852_2_ - 1, p_71852_3_, p_71852_4_))
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_ - 1, p_71852_3_ + 1, p_71852_4_);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_ - 1, p_71852_3_ - 1, p_71852_4_);
			}
			if(p_71852_1_.isBlockNormalCube(p_71852_2_ + 1, p_71852_3_, p_71852_4_))
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_ + 1, p_71852_3_ + 1, p_71852_4_);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_ + 1, p_71852_3_ - 1, p_71852_4_);
			}
			if(p_71852_1_.isBlockNormalCube(p_71852_2_, p_71852_3_, p_71852_4_ - 1))
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_, p_71852_3_ + 1, p_71852_4_ - 1);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_, p_71852_3_ - 1, p_71852_4_ - 1);
			}
			if(p_71852_1_.isBlockNormalCube(p_71852_2_, p_71852_3_, p_71852_4_ + 1))
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_, p_71852_3_ + 1, p_71852_4_ + 1);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71852_1_, p_71852_2_, p_71852_3_ - 1, p_71852_4_ + 1);
			}
		}
	}
	
	private void calculateCurrentChanges(World p_72171_1_, int p_72171_2_, int p_72171_3_, int p_72171_4_, int p_72171_5_, int p_72171_6_, int p_72171_7_)
	{
		int var8 = p_72171_1_.getBlockMetadata(p_72171_2_, p_72171_3_, p_72171_4_);
		byte var9 = 0;
		int var15 = getMaxCurrentStrength(p_72171_1_, p_72171_5_, p_72171_6_, p_72171_7_, var9);
		wiresProvidePower = false;
		int var10 = p_72171_1_.getStrongestIndirectPower(p_72171_2_, p_72171_3_, p_72171_4_);
		wiresProvidePower = true;
		if(var10 > 0 && var10 > var15 - 1)
		{
			var15 = var10;
		}
		int var11 = 0;
		for(int var12 = 0; var12 < 4; ++var12)
		{
			int var13 = p_72171_2_;
			int var14 = p_72171_4_;
			if(var12 == 0)
			{
				var13 = p_72171_2_ - 1;
			}
			if(var12 == 1)
			{
				++var13;
			}
			if(var12 == 2)
			{
				var14 = p_72171_4_ - 1;
			}
			if(var12 == 3)
			{
				++var14;
			}
			if(var13 != p_72171_5_ || var14 != p_72171_7_)
			{
				var11 = getMaxCurrentStrength(p_72171_1_, var13, p_72171_3_, var14, var11);
			}
			if(p_72171_1_.isBlockNormalCube(var13, p_72171_3_, var14) && !p_72171_1_.isBlockNormalCube(p_72171_2_, p_72171_3_ + 1, p_72171_4_))
			{
				if((var13 != p_72171_5_ || var14 != p_72171_7_) && p_72171_3_ >= p_72171_6_)
				{
					var11 = getMaxCurrentStrength(p_72171_1_, var13, p_72171_3_ + 1, var14, var11);
				}
			} else if(!p_72171_1_.isBlockNormalCube(var13, p_72171_3_, var14) && (var13 != p_72171_5_ || var14 != p_72171_7_) && p_72171_3_ <= p_72171_6_)
			{
				var11 = getMaxCurrentStrength(p_72171_1_, var13, p_72171_3_ - 1, var14, var11);
			}
		}
		if(var11 > var15)
		{
			var15 = var11 - 1;
		} else if(var15 > 0)
		{
			--var15;
		} else
		{
			var15 = 0;
		}
		if(var10 > var15 - 1)
		{
			var15 = var10;
		}
		if(var8 != var15)
		{
			p_72171_1_.setBlockMetadataWithNotify(p_72171_2_, p_72171_3_, p_72171_4_, var15, 2);
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_, p_72171_3_, p_72171_4_));
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_ - 1, p_72171_3_, p_72171_4_));
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_ + 1, p_72171_3_, p_72171_4_));
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_, p_72171_3_ - 1, p_72171_4_));
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_, p_72171_3_ + 1, p_72171_4_));
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_, p_72171_3_, p_72171_4_ - 1));
			blocksNeedingUpdate.add(new ChunkPosition(p_72171_2_, p_72171_3_, p_72171_4_ + 1));
		}
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_) || p_71930_1_.getBlockId(p_71930_2_, p_71930_3_ - 1, p_71930_4_) == Block.glowStone.blockID;
	}
	
	@Override public boolean canProvidePower()
	{
		return wiresProvidePower;
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 8388608;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	private int getMaxCurrentStrength(World p_72170_1_, int p_72170_2_, int p_72170_3_, int p_72170_4_, int p_72170_5_)
	{
		if(p_72170_1_.getBlockId(p_72170_2_, p_72170_3_, p_72170_4_) != blockID) return p_72170_5_;
		else
		{
			int var6 = p_72170_1_.getBlockMetadata(p_72170_2_, p_72170_3_, p_72170_4_);
			return var6 > p_72170_5_ ? var6 : p_72170_5_;
		}
	}
	
	@Override public int getRenderType()
	{
		return 5;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.redstone.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.redstone.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		return !wiresProvidePower ? 0 : isProvidingWeakPower(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_);
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		if(!wiresProvidePower) return 0;
		else
		{
			int var6 = p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_);
			if(var6 == 0) return 0;
			else if(p_71865_5_ == 1) return var6;
			else
			{
				boolean var7 = isPoweredOrRepeater(p_71865_1_, p_71865_2_ - 1, p_71865_3_, p_71865_4_, 1) || !p_71865_1_.isBlockNormalCube(p_71865_2_ - 1, p_71865_3_, p_71865_4_) && isPoweredOrRepeater(p_71865_1_, p_71865_2_ - 1, p_71865_3_ - 1, p_71865_4_, -1);
				boolean var8 = isPoweredOrRepeater(p_71865_1_, p_71865_2_ + 1, p_71865_3_, p_71865_4_, 3) || !p_71865_1_.isBlockNormalCube(p_71865_2_ + 1, p_71865_3_, p_71865_4_) && isPoweredOrRepeater(p_71865_1_, p_71865_2_ + 1, p_71865_3_ - 1, p_71865_4_, -1);
				boolean var9 = isPoweredOrRepeater(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_ - 1, 2) || !p_71865_1_.isBlockNormalCube(p_71865_2_, p_71865_3_, p_71865_4_ - 1) && isPoweredOrRepeater(p_71865_1_, p_71865_2_, p_71865_3_ - 1, p_71865_4_ - 1, -1);
				boolean var10 = isPoweredOrRepeater(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_ + 1, 0) || !p_71865_1_.isBlockNormalCube(p_71865_2_, p_71865_3_, p_71865_4_ + 1) && isPoweredOrRepeater(p_71865_1_, p_71865_2_, p_71865_3_ - 1, p_71865_4_ + 1, -1);
				if(!p_71865_1_.isBlockNormalCube(p_71865_2_, p_71865_3_ + 1, p_71865_4_))
				{
					if(p_71865_1_.isBlockNormalCube(p_71865_2_ - 1, p_71865_3_, p_71865_4_) && isPoweredOrRepeater(p_71865_1_, p_71865_2_ - 1, p_71865_3_ + 1, p_71865_4_, -1))
					{
						var7 = true;
					}
					if(p_71865_1_.isBlockNormalCube(p_71865_2_ + 1, p_71865_3_, p_71865_4_) && isPoweredOrRepeater(p_71865_1_, p_71865_2_ + 1, p_71865_3_ + 1, p_71865_4_, -1))
					{
						var8 = true;
					}
					if(p_71865_1_.isBlockNormalCube(p_71865_2_, p_71865_3_, p_71865_4_ - 1) && isPoweredOrRepeater(p_71865_1_, p_71865_2_, p_71865_3_ + 1, p_71865_4_ - 1, -1))
					{
						var9 = true;
					}
					if(p_71865_1_.isBlockNormalCube(p_71865_2_, p_71865_3_, p_71865_4_ + 1) && isPoweredOrRepeater(p_71865_1_, p_71865_2_, p_71865_3_ + 1, p_71865_4_ + 1, -1))
					{
						var10 = true;
					}
				}
				return !var9 && !var8 && !var7 && !var10 && p_71865_5_ >= 2 && p_71865_5_ <= 5 ? var6 : p_71865_5_ == 2 && var9 && !var7 && !var8 ? var6 : p_71865_5_ == 3 && var10 && !var7 && !var8 ? var6 : p_71865_5_ == 4 && var7 && !var9 && !var10 ? var6 : p_71865_5_ == 5 && var8 && !var9 && !var10 ? var6 : 0;
			}
		}
	}
	
	private void notifyWireNeighborsOfNeighborChange(World p_72172_1_, int p_72172_2_, int p_72172_3_, int p_72172_4_)
	{
		if(p_72172_1_.getBlockId(p_72172_2_, p_72172_3_, p_72172_4_) == blockID)
		{
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_, p_72172_3_, p_72172_4_, blockID);
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_ - 1, p_72172_3_, p_72172_4_, blockID);
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_ + 1, p_72172_3_, p_72172_4_, blockID);
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_, p_72172_3_, p_72172_4_ - 1, blockID);
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_, p_72172_3_, p_72172_4_ + 1, blockID);
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_, p_72172_3_ - 1, p_72172_4_, blockID);
			p_72172_1_.notifyBlocksOfNeighborChange(p_72172_2_, p_72172_3_ + 1, p_72172_4_, blockID);
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		if(!p_71861_1_.isRemote)
		{
			updateAndPropagateCurrentStrength(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_, p_71861_3_ + 1, p_71861_4_, blockID);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_, p_71861_3_ - 1, p_71861_4_, blockID);
			notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_ - 1, p_71861_3_, p_71861_4_);
			notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_ + 1, p_71861_3_, p_71861_4_);
			notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ - 1);
			notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_ + 1);
			if(p_71861_1_.isBlockNormalCube(p_71861_2_ - 1, p_71861_3_, p_71861_4_))
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_ - 1, p_71861_3_ + 1, p_71861_4_);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_ - 1, p_71861_3_ - 1, p_71861_4_);
			}
			if(p_71861_1_.isBlockNormalCube(p_71861_2_ + 1, p_71861_3_, p_71861_4_))
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_ + 1, p_71861_3_ + 1, p_71861_4_);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_ + 1, p_71861_3_ - 1, p_71861_4_);
			}
			if(p_71861_1_.isBlockNormalCube(p_71861_2_, p_71861_3_, p_71861_4_ - 1))
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_, p_71861_3_ + 1, p_71861_4_ - 1);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_, p_71861_3_ - 1, p_71861_4_ - 1);
			}
			if(p_71861_1_.isBlockNormalCube(p_71861_2_, p_71861_3_, p_71861_4_ + 1))
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_, p_71861_3_ + 1, p_71861_4_ + 1);
			} else
			{
				notifyWireNeighborsOfNeighborChange(p_71861_1_, p_71861_2_, p_71861_3_ - 1, p_71861_4_ + 1);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			boolean var6 = canPlaceBlockAt(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
			if(var6)
			{
				updateAndPropagateCurrentStrength(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
			} else
			{
				dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, 0, 0);
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			}
			super.onNeighborBlockChange(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
		}
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		if(var6 > 0)
		{
			double var7 = par2 + 0.5D + (par5Random.nextFloat() - 0.5D) * 0.2D;
			double var9 = par3 + 0.0625F;
			double var11 = par4 + 0.5D + (par5Random.nextFloat() - 0.5D) * 0.2D;
			float var13 = var6 / 15.0F;
			float var14 = var13 * 0.6F + 0.4F;
			if(var6 == 0)
			{
				var14 = 0.0F;
			}
			float var15 = var13 * var13 * 0.7F - 0.5F;
			float var16 = var13 * var13 * 0.6F - 0.7F;
			if(var15 < 0.0F)
			{
				var15 = 0.0F;
			}
			if(var16 < 0.0F)
			{
				var16 = 0.0F;
			}
			par1World.spawnParticle("reddust", var7, var9, var11, var14, var15, var16);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94413_c = par1IconRegister.registerIcon("redstoneDust_cross");
		field_94410_cO = par1IconRegister.registerIcon("redstoneDust_line");
		field_94411_cP = par1IconRegister.registerIcon("redstoneDust_cross_overlay");
		field_94412_cQ = par1IconRegister.registerIcon("redstoneDust_line_overlay");
		blockIcon = field_94413_c;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	private void updateAndPropagateCurrentStrength(World p_72168_1_, int p_72168_2_, int p_72168_3_, int p_72168_4_)
	{
		calculateCurrentChanges(p_72168_1_, p_72168_2_, p_72168_3_, p_72168_4_, p_72168_2_, p_72168_3_, p_72168_4_);
		ArrayList var5 = new ArrayList(blocksNeedingUpdate);
		blocksNeedingUpdate.clear();
		for(int var6 = 0; var6 < var5.size(); ++var6)
		{
			ChunkPosition var7 = (ChunkPosition) var5.get(var6);
			p_72168_1_.notifyBlocksOfNeighborChange(var7.x, var7.y, var7.z, blockID);
		}
	}
	
	public static Icon func_94409_b(String par0Str)
	{
		return par0Str == "redstoneDust_cross" ? Block.redstoneWire.field_94413_c : par0Str == "redstoneDust_line" ? Block.redstoneWire.field_94410_cO : par0Str == "redstoneDust_cross_overlay" ? Block.redstoneWire.field_94411_cP : par0Str == "redstoneDust_line_overlay" ? Block.redstoneWire.field_94412_cQ : null;
	}
	
	public static boolean isPoweredOrRepeater(IBlockAccess p_72169_0_, int p_72169_1_, int p_72169_2_, int p_72169_3_, int p_72169_4_)
	{
		if(isPowerProviderOrWire(p_72169_0_, p_72169_1_, p_72169_2_, p_72169_3_, p_72169_4_)) return true;
		else
		{
			int var5 = p_72169_0_.getBlockId(p_72169_1_, p_72169_2_, p_72169_3_);
			if(var5 == Block.redstoneRepeaterActive.blockID)
			{
				int var6 = p_72169_0_.getBlockMetadata(p_72169_1_, p_72169_2_, p_72169_3_);
				return p_72169_4_ == (var6 & 3);
			} else return false;
		}
	}
	
	public static boolean isPowerProviderOrWire(IBlockAccess p_72173_0_, int p_72173_1_, int p_72173_2_, int p_72173_3_, int p_72173_4_)
	{
		int var5 = p_72173_0_.getBlockId(p_72173_1_, p_72173_2_, p_72173_3_);
		if(var5 == Block.redstoneWire.blockID) return true;
		else if(var5 == 0) return false;
		else if(!Block.redstoneRepeaterIdle.func_94487_f(var5)) return Block.blocksList[var5].canProvidePower() && p_72173_4_ != -1;
		else
		{
			int var6 = p_72173_0_.getBlockMetadata(p_72173_1_, p_72173_2_, p_72173_3_);
			return p_72173_4_ == (var6 & 3) || p_72173_4_ == Direction.rotateOpposite[var6 & 3];
		}
	}
}
