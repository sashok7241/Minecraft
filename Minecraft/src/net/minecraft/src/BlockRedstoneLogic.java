package net.minecraft.src;

import java.util.Random;

public abstract class BlockRedstoneLogic extends BlockDirectional
{
	protected final boolean isRepeaterPowered;
	
	protected BlockRedstoneLogic(int p_i9012_1_, boolean p_i9012_2_)
	{
		super(p_i9012_1_, Material.circuits);
		isRepeaterPowered = p_i9012_2_;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		return !p_71854_1_.doesBlockHaveSolidTopSurface(p_71854_2_, p_71854_3_ - 1, p_71854_4_) ? false : super.canBlockStay(p_71854_1_, p_71854_2_, p_71854_3_, p_71854_4_);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return !p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_) ? false : super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	public boolean func_83011_d(World p_83011_1_, int p_83011_2_, int p_83011_3_, int p_83011_4_, int p_83011_5_)
	{
		int var6 = getDirection(p_83011_5_);
		if(isRedstoneRepeaterBlockID(p_83011_1_.getBlockId(p_83011_2_ - Direction.offsetX[var6], p_83011_3_, p_83011_4_ - Direction.offsetZ[var6])))
		{
			int var7 = p_83011_1_.getBlockMetadata(p_83011_2_ - Direction.offsetX[var6], p_83011_3_, p_83011_4_ - Direction.offsetZ[var6]);
			int var8 = getDirection(var7);
			return var8 != var6;
		} else return false;
	}
	
	public boolean func_94476_e(IBlockAccess p_94476_1_, int p_94476_2_, int p_94476_3_, int p_94476_4_, int p_94476_5_)
	{
		return false;
	}
	
	protected boolean func_94477_d(int p_94477_1_)
	{
		Block var2 = Block.blocksList[p_94477_1_];
		return var2 != null && var2.canProvidePower();
	}
	
	protected void func_94479_f(World p_94479_1_, int p_94479_2_, int p_94479_3_, int p_94479_4_, int p_94479_5_)
	{
		int var6 = p_94479_1_.getBlockMetadata(p_94479_2_, p_94479_3_, p_94479_4_);
		if(!func_94476_e(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, var6))
		{
			boolean var7 = isGettingInput(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, var6);
			if((isRepeaterPowered && !var7 || !isRepeaterPowered && var7) && !p_94479_1_.isBlockTickScheduledThisTick(p_94479_2_, p_94479_3_, p_94479_4_, blockID))
			{
				byte var8 = -1;
				if(func_83011_d(p_94479_1_, p_94479_2_, p_94479_3_, p_94479_4_, var6))
				{
					var8 = -3;
				} else if(isRepeaterPowered)
				{
					var8 = -2;
				}
				p_94479_1_.scheduleBlockUpdateWithPriority(p_94479_2_, p_94479_3_, p_94479_4_, blockID, func_94481_j_(var6), var8);
			}
		}
	}
	
	protected int func_94480_d(IBlockAccess p_94480_1_, int p_94480_2_, int p_94480_3_, int p_94480_4_, int p_94480_5_)
	{
		return 15;
	}
	
	protected abstract int func_94481_j_(int var1);
	
	protected int func_94482_f(IBlockAccess p_94482_1_, int p_94482_2_, int p_94482_3_, int p_94482_4_, int p_94482_5_)
	{
		int var6 = getDirection(p_94482_5_);
		switch(var6)
		{
			case 0:
			case 2:
				return Math.max(func_94488_g(p_94482_1_, p_94482_2_ - 1, p_94482_3_, p_94482_4_, 4), func_94488_g(p_94482_1_, p_94482_2_ + 1, p_94482_3_, p_94482_4_, 5));
			case 1:
			case 3:
				return Math.max(func_94488_g(p_94482_1_, p_94482_2_, p_94482_3_, p_94482_4_ + 1, 3), func_94488_g(p_94482_1_, p_94482_2_, p_94482_3_, p_94482_4_ - 1, 2));
			default:
				return 0;
		}
	}
	
	protected void func_94483_i_(World p_94483_1_, int p_94483_2_, int p_94483_3_, int p_94483_4_)
	{
		int var5 = getDirection(p_94483_1_.getBlockMetadata(p_94483_2_, p_94483_3_, p_94483_4_));
		if(var5 == 1)
		{
			p_94483_1_.notifyBlockOfNeighborChange(p_94483_2_ + 1, p_94483_3_, p_94483_4_, blockID);
			p_94483_1_.notifyBlocksOfNeighborChange(p_94483_2_ + 1, p_94483_3_, p_94483_4_, blockID, 4);
		}
		if(var5 == 3)
		{
			p_94483_1_.notifyBlockOfNeighborChange(p_94483_2_ - 1, p_94483_3_, p_94483_4_, blockID);
			p_94483_1_.notifyBlocksOfNeighborChange(p_94483_2_ - 1, p_94483_3_, p_94483_4_, blockID, 5);
		}
		if(var5 == 2)
		{
			p_94483_1_.notifyBlockOfNeighborChange(p_94483_2_, p_94483_3_, p_94483_4_ + 1, blockID);
			p_94483_1_.notifyBlocksOfNeighborChange(p_94483_2_, p_94483_3_, p_94483_4_ + 1, blockID, 2);
		}
		if(var5 == 0)
		{
			p_94483_1_.notifyBlockOfNeighborChange(p_94483_2_, p_94483_3_, p_94483_4_ - 1, blockID);
			p_94483_1_.notifyBlocksOfNeighborChange(p_94483_2_, p_94483_3_, p_94483_4_ - 1, blockID, 3);
		}
	}
	
	protected abstract BlockRedstoneLogic func_94484_i();
	
	protected abstract BlockRedstoneLogic func_94485_e();
	
	protected int func_94486_g(int p_94486_1_)
	{
		return func_94481_j_(p_94486_1_);
	}
	
	public boolean func_94487_f(int p_94487_1_)
	{
		return p_94487_1_ == func_94485_e().blockID || p_94487_1_ == func_94484_i().blockID;
	}
	
	protected int func_94488_g(IBlockAccess p_94488_1_, int p_94488_2_, int p_94488_3_, int p_94488_4_, int p_94488_5_)
	{
		int var6 = p_94488_1_.getBlockId(p_94488_2_, p_94488_3_, p_94488_4_);
		return func_94477_d(var6) ? var6 == Block.redstoneWire.blockID ? p_94488_1_.getBlockMetadata(p_94488_2_, p_94488_3_, p_94488_4_) : p_94488_1_.isBlockProvidingPowerTo(p_94488_2_, p_94488_3_, p_94488_4_, p_94488_5_) : 0;
	}
	
	protected boolean func_96470_c(int p_96470_1_)
	{
		return isRepeaterPowered;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 0 ? isRepeaterPowered ? Block.torchRedstoneActive.getBlockTextureFromSide(par1) : Block.torchRedstoneIdle.getBlockTextureFromSide(par1) : par1 == 1 ? blockIcon : Block.stoneDoubleSlab.getBlockTextureFromSide(1);
	}
	
	protected int getInputStrength(World p_72220_1_, int p_72220_2_, int p_72220_3_, int p_72220_4_, int p_72220_5_)
	{
		int var6 = getDirection(p_72220_5_);
		int var7 = p_72220_2_ + Direction.offsetX[var6];
		int var8 = p_72220_4_ + Direction.offsetZ[var6];
		int var9 = p_72220_1_.getIndirectPowerLevelTo(var7, p_72220_3_, var8, Direction.directionToFacing[var6]);
		return var9 >= 15 ? var9 : Math.max(var9, p_72220_1_.getBlockId(var7, p_72220_3_, var8) == Block.redstoneWire.blockID ? p_72220_1_.getBlockMetadata(var7, p_72220_3_, var8) : 0);
	}
	
	@Override public int getRenderType()
	{
		return 36;
	}
	
	@Override public boolean isAssociatedBlockID(int p_94334_1_)
	{
		return func_94487_f(p_94334_1_);
	}
	
	protected boolean isGettingInput(World p_94478_1_, int p_94478_2_, int p_94478_3_, int p_94478_4_, int p_94478_5_)
	{
		return getInputStrength(p_94478_1_, p_94478_2_, p_94478_3_, p_94478_4_, p_94478_5_) > 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		return isProvidingWeakPower(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_);
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		int var6 = p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_);
		if(!func_96470_c(var6)) return 0;
		else
		{
			int var7 = getDirection(var6);
			return var7 == 0 && p_71865_5_ == 3 ? func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, var6) : var7 == 1 && p_71865_5_ == 4 ? func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, var6) : var7 == 2 && p_71865_5_ == 2 ? func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, var6) : var7 == 3 && p_71865_5_ == 5 ? func_94480_d(p_71865_1_, p_71865_2_, p_71865_3_, p_71865_4_, var6) : 0;
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		func_94483_i_(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public void onBlockDestroyedByPlayer(World p_71898_1_, int p_71898_2_, int p_71898_3_, int p_71898_4_, int p_71898_5_)
	{
		if(isRepeaterPowered)
		{
			p_71898_1_.notifyBlocksOfNeighborChange(p_71898_2_ + 1, p_71898_3_, p_71898_4_, blockID);
			p_71898_1_.notifyBlocksOfNeighborChange(p_71898_2_ - 1, p_71898_3_, p_71898_4_, blockID);
			p_71898_1_.notifyBlocksOfNeighborChange(p_71898_2_, p_71898_3_, p_71898_4_ + 1, blockID);
			p_71898_1_.notifyBlocksOfNeighborChange(p_71898_2_, p_71898_3_, p_71898_4_ - 1, blockID);
			p_71898_1_.notifyBlocksOfNeighborChange(p_71898_2_, p_71898_3_ - 1, p_71898_4_, blockID);
			p_71898_1_.notifyBlocksOfNeighborChange(p_71898_2_, p_71898_3_ + 1, p_71898_4_, blockID);
		}
		super.onBlockDestroyedByPlayer(p_71898_1_, p_71898_2_, p_71898_3_, p_71898_4_, p_71898_5_);
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = ((MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) + 2) % 4;
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 3);
		boolean var8 = isGettingInput(p_71860_1_, p_71860_2_, p_71860_3_, p_71860_4_, var7);
		if(var8)
		{
			p_71860_1_.scheduleBlockUpdate(p_71860_2_, p_71860_3_, p_71860_4_, blockID, 1);
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!canBlockStay(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
			p_71863_1_.notifyBlocksOfNeighborChange(p_71863_2_ + 1, p_71863_3_, p_71863_4_, blockID);
			p_71863_1_.notifyBlocksOfNeighborChange(p_71863_2_ - 1, p_71863_3_, p_71863_4_, blockID);
			p_71863_1_.notifyBlocksOfNeighborChange(p_71863_2_, p_71863_3_, p_71863_4_ + 1, blockID);
			p_71863_1_.notifyBlocksOfNeighborChange(p_71863_2_, p_71863_3_, p_71863_4_ - 1, blockID);
			p_71863_1_.notifyBlocksOfNeighborChange(p_71863_2_, p_71863_3_ - 1, p_71863_4_, blockID);
			p_71863_1_.notifyBlocksOfNeighborChange(p_71863_2_, p_71863_3_ + 1, p_71863_4_, blockID);
		} else
		{
			func_94479_f(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(isRepeaterPowered ? "repeater_lit" : "repeater");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 != 0 && par5 != 1;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
		if(!func_94476_e(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, var6))
		{
			boolean var7 = isGettingInput(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, var6);
			if(isRepeaterPowered && !var7)
			{
				p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, func_94484_i().blockID, var6, 2);
			} else if(!isRepeaterPowered)
			{
				p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, func_94485_e().blockID, var6, 2);
				if(!var7)
				{
					p_71847_1_.scheduleBlockUpdateWithPriority(p_71847_2_, p_71847_3_, p_71847_4_, func_94485_e().blockID, func_94486_g(var6), -1);
				}
			}
		}
	}
	
	public static boolean isRedstoneRepeaterBlockID(int p_82524_0_)
	{
		return Block.redstoneRepeaterIdle.func_94487_f(p_82524_0_) || Block.redstoneComparatorIdle.func_94487_f(p_82524_0_);
	}
}
