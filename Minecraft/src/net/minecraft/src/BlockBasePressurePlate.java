package net.minecraft.src;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block
{
	private String pressurePlateIconName;
	
	protected BlockBasePressurePlate(int p_i9036_1_, String p_i9036_2_, Material p_i9036_3_)
	{
		super(p_i9036_1_, p_i9036_3_);
		pressurePlateIconName = p_i9036_2_;
		setCreativeTab(CreativeTabs.tabRedstone);
		setTickRandomly(true);
		func_94353_c_(getMetaFromWeight(15));
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		if(getPowerSupply(p_71852_6_) > 0)
		{
			func_94354_b_(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_) || BlockFence.isIdAFence(p_71930_1_.getBlockId(p_71930_2_, p_71930_3_ - 1, p_71930_4_));
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	protected void func_94353_c_(int p_94353_1_)
	{
		boolean var2 = getPowerSupply(p_94353_1_) > 0;
		float var3 = 0.0625F;
		if(var2)
		{
			setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.03125F, 1.0F - var3);
		} else
		{
			setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.0625F, 1.0F - var3);
		}
	}
	
	protected void func_94354_b_(World p_94354_1_, int p_94354_2_, int p_94354_3_, int p_94354_4_)
	{
		p_94354_1_.notifyBlocksOfNeighborChange(p_94354_2_, p_94354_3_, p_94354_4_, blockID);
		p_94354_1_.notifyBlocksOfNeighborChange(p_94354_2_, p_94354_3_ - 1, p_94354_4_, blockID);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess p_71918_1_, int p_71918_2_, int p_71918_3_, int p_71918_4_)
	{
		return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	protected abstract int getMetaFromWeight(int var1);
	
	@Override public int getMobilityFlag()
	{
		return 1;
	}
	
	protected abstract int getPlateState(World var1, int var2, int var3, int var4);
	
	protected abstract int getPowerSupply(int var1);
	
	protected AxisAlignedBB getSensitiveAABB(int p_94352_1_, int p_94352_2_, int p_94352_3_)
	{
		float var4 = 0.125F;
		return AxisAlignedBB.getAABBPool().getAABB(p_94352_1_ + var4, p_94352_2_, p_94352_3_ + var4, p_94352_1_ + 1 - var4, p_94352_2_ + 0.25D, p_94352_3_ + 1 - var4);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		return p_71855_5_ == 1 ? getPowerSupply(p_71855_1_.getBlockMetadata(p_71855_2_, p_71855_3_, p_71855_4_)) : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		return getPowerSupply(p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_));
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(!p_71869_1_.isRemote)
		{
			int var6 = getPowerSupply(p_71869_1_.getBlockMetadata(p_71869_2_, p_71869_3_, p_71869_4_));
			if(var6 == 0)
			{
				setStateIfMobInteractsWithPlate(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_, var6);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		boolean var6 = false;
		if(!p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_ - 1, p_71863_4_) && !BlockFence.isIdAFence(p_71863_1_.getBlockId(p_71863_2_, p_71863_3_ - 1, p_71863_4_)))
		{
			var6 = true;
		}
		if(var6)
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(pressurePlateIconName);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		func_94353_c_(p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_));
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.5F;
		float var2 = 0.125F;
		float var3 = 0.5F;
		setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
	}
	
	protected void setStateIfMobInteractsWithPlate(World p_72193_1_, int p_72193_2_, int p_72193_3_, int p_72193_4_, int p_72193_5_)
	{
		int var6 = getPlateState(p_72193_1_, p_72193_2_, p_72193_3_, p_72193_4_);
		boolean var7 = p_72193_5_ > 0;
		boolean var8 = var6 > 0;
		if(p_72193_5_ != var6)
		{
			p_72193_1_.setBlockMetadataWithNotify(p_72193_2_, p_72193_3_, p_72193_4_, getMetaFromWeight(var6), 2);
			func_94354_b_(p_72193_1_, p_72193_2_, p_72193_3_, p_72193_4_);
			p_72193_1_.markBlockRangeForRenderUpdate(p_72193_2_, p_72193_3_, p_72193_4_, p_72193_2_, p_72193_3_, p_72193_4_);
		}
		if(!var8 && var7)
		{
			p_72193_1_.playSoundEffect(p_72193_2_ + 0.5D, p_72193_3_ + 0.1D, p_72193_4_ + 0.5D, "random.click", 0.3F, 0.5F);
		} else if(var8 && !var7)
		{
			p_72193_1_.playSoundEffect(p_72193_2_ + 0.5D, p_72193_3_ + 0.1D, p_72193_4_ + 0.5D, "random.click", 0.3F, 0.6F);
		}
		if(var8)
		{
			p_72193_1_.scheduleBlockUpdate(p_72193_2_, p_72193_3_, p_72193_4_, blockID, tickRate(p_72193_1_));
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 20;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			int var6 = getPowerSupply(p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_));
			if(var6 > 0)
			{
				setStateIfMobInteractsWithPlate(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, var6);
			}
		}
	}
}
