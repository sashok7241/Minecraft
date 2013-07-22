package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockDetectorRail extends BlockRailBase
{
	private Icon[] iconArray;
	
	public BlockDetectorRail(int p_i9051_1_)
	{
		super(p_i9051_1_, true);
		setTickRandomly(true);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		if((p_94328_1_.getBlockMetadata(p_94328_2_, p_94328_3_, p_94328_4_) & 8) > 0)
		{
			float var6 = 0.125F;
			List var7 = p_94328_1_.selectEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(p_94328_2_ + var6, p_94328_3_, p_94328_4_ + var6, p_94328_2_ + 1 - var6, p_94328_3_ + 1 - var6, p_94328_4_ + 1 - var6), IEntitySelector.selectInventories);
			if(var7.size() > 0) return Container.calcRedstoneFromInventory((IInventory) var7.get(0));
		}
		return 0;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return (par2 & 8) != 0 ? iconArray[1] : iconArray[0];
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		return (p_71855_1_.getBlockMetadata(p_71855_2_, p_71855_3_, p_71855_4_) & 8) == 0 ? 0 : p_71855_5_ == 1 ? 15 : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		return (p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_) & 8) != 0 ? 15 : 0;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		setStateIfMinecartInteractsWithRail(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_, p_71861_1_.getBlockMetadata(p_71861_2_, p_71861_3_, p_71861_4_));
	}
	
	@Override public void onEntityCollidedWithBlock(World p_71869_1_, int p_71869_2_, int p_71869_3_, int p_71869_4_, Entity p_71869_5_)
	{
		if(!p_71869_1_.isRemote)
		{
			int var6 = p_71869_1_.getBlockMetadata(p_71869_2_, p_71869_3_, p_71869_4_);
			if((var6 & 8) == 0)
			{
				setStateIfMinecartInteractsWithRail(p_71869_1_, p_71869_2_, p_71869_3_, p_71869_4_, var6);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[2];
		iconArray[0] = par1IconRegister.registerIcon("detectorRail");
		iconArray[1] = par1IconRegister.registerIcon("detectorRail_on");
	}
	
	private void setStateIfMinecartInteractsWithRail(World p_72187_1_, int p_72187_2_, int p_72187_3_, int p_72187_4_, int p_72187_5_)
	{
		boolean var6 = (p_72187_5_ & 8) != 0;
		boolean var7 = false;
		float var8 = 0.125F;
		List var9 = p_72187_1_.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(p_72187_2_ + var8, p_72187_3_, p_72187_4_ + var8, p_72187_2_ + 1 - var8, p_72187_3_ + 1 - var8, p_72187_4_ + 1 - var8));
		if(!var9.isEmpty())
		{
			var7 = true;
		}
		if(var7 && !var6)
		{
			p_72187_1_.setBlockMetadataWithNotify(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_5_ | 8, 3);
			p_72187_1_.notifyBlocksOfNeighborChange(p_72187_2_, p_72187_3_, p_72187_4_, blockID);
			p_72187_1_.notifyBlocksOfNeighborChange(p_72187_2_, p_72187_3_ - 1, p_72187_4_, blockID);
			p_72187_1_.markBlockRangeForRenderUpdate(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_2_, p_72187_3_, p_72187_4_);
		}
		if(!var7 && var6)
		{
			p_72187_1_.setBlockMetadataWithNotify(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_5_ & 7, 3);
			p_72187_1_.notifyBlocksOfNeighborChange(p_72187_2_, p_72187_3_, p_72187_4_, blockID);
			p_72187_1_.notifyBlocksOfNeighborChange(p_72187_2_, p_72187_3_ - 1, p_72187_4_, blockID);
			p_72187_1_.markBlockRangeForRenderUpdate(p_72187_2_, p_72187_3_, p_72187_4_, p_72187_2_, p_72187_3_, p_72187_4_);
		}
		if(var7)
		{
			p_72187_1_.scheduleBlockUpdate(p_72187_2_, p_72187_3_, p_72187_4_, blockID, tickRate(p_72187_1_));
		}
		p_72187_1_.func_96440_m(p_72187_2_, p_72187_3_, p_72187_4_, blockID);
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 20;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			if((var6 & 8) != 0)
			{
				setStateIfMinecartInteractsWithRail(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, var6);
			}
		}
	}
}
