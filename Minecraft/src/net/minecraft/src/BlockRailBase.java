package net.minecraft.src;

import java.util.Random;

public abstract class BlockRailBase extends Block
{
	protected final boolean isPowered;
	
	protected BlockRailBase(int p_i9011_1_, boolean p_i9011_2_)
	{
		super(p_i9011_1_, Material.circuits);
		isPowered = p_i9011_2_;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setCreativeTab(CreativeTabs.tabTransport);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		int var7 = p_71852_6_;
		if(isPowered)
		{
			var7 = p_71852_6_ & 7;
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		if(var7 == 2 || var7 == 3 || var7 == 4 || var7 == 5)
		{
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_ + 1, p_71852_4_, p_71852_5_);
		}
		if(isPowered)
		{
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_ - 1, p_71852_4_, p_71852_5_);
		}
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_);
	}
	
	@Override public MovingObjectPosition collisionRayTrace(World p_71878_1_, int p_71878_2_, int p_71878_3_, int p_71878_4_, Vec3 p_71878_5_, Vec3 p_71878_6_)
	{
		setBlockBoundsBasedOnState(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_);
		return super.collisionRayTrace(p_71878_1_, p_71878_2_, p_71878_3_, p_71878_4_, p_71878_5_, p_71878_6_);
	}
	
	protected void func_94358_a(World p_94358_1_, int p_94358_2_, int p_94358_3_, int p_94358_4_, int p_94358_5_, int p_94358_6_, int p_94358_7_)
	{
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getMobilityFlag()
	{
		return 0;
	}
	
	@Override public int getRenderType()
	{
		return 9;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	public boolean isPowered()
	{
		return isPowered;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(!p_71861_1_.isRemote)
		{
			refreshTrackShape(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, true);
			if(isPowered)
			{
				onNeighborBlockChange(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, blockID);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			int var6 = p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_);
			int var7 = var6;
			if(isPowered)
			{
				var7 = var6 & 7;
			}
			boolean var8 = false;
			if(!p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_ - 1, p_71863_4_))
			{
				var8 = true;
			}
			if(var7 == 2 && !p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_ + 1, p_71863_3_, p_71863_4_))
			{
				var8 = true;
			}
			if(var7 == 3 && !p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_ - 1, p_71863_3_, p_71863_4_))
			{
				var8 = true;
			}
			if(var7 == 4 && !p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_, p_71863_4_ - 1))
			{
				var8 = true;
			}
			if(var7 == 5 && !p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_, p_71863_4_ + 1))
			{
				var8 = true;
			}
			if(var8)
			{
				dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
				p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			} else
			{
				func_94358_a(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, var6, var7, p_71863_5_);
			}
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
	
	protected void refreshTrackShape(World p_72181_1_, int p_72181_2_, int p_72181_3_, int p_72181_4_, boolean p_72181_5_)
	{
		if(!p_72181_1_.isRemote)
		{
			new BlockBaseRailLogic(this, p_72181_1_, p_72181_2_, p_72181_3_, p_72181_4_).func_94511_a(p_72181_1_.isBlockIndirectlyGettingPowered(p_72181_2_, p_72181_3_, p_72181_4_), p_72181_5_);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
		if(var5 >= 2 && var5 <= 5)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		}
	}
	
	public static final boolean isRailBlock(int p_72184_0_)
	{
		return p_72184_0_ == Block.rail.blockID || p_72184_0_ == Block.railPowered.blockID || p_72184_0_ == Block.railDetector.blockID || p_72184_0_ == Block.railActivator.blockID;
	}
	
	public static final boolean isRailBlockAt(World p_72180_0_, int p_72180_1_, int p_72180_2_, int p_72180_3_)
	{
		return isRailBlock(p_72180_0_.getBlockId(p_72180_1_, p_72180_2_, p_72180_3_));
	}
}
