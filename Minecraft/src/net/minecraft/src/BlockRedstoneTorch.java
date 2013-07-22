package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch
{
	private boolean torchActive = false;
	private static Map redstoneUpdateInfoCache = new HashMap();
	
	protected BlockRedstoneTorch(int p_i9074_1_, boolean p_i9074_2_)
	{
		super(p_i9074_1_);
		torchActive = p_i9074_2_;
		setTickRandomly(true);
		setCreativeTab((CreativeTabs) null);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		if(torchActive)
		{
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_ - 1, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_ + 1, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_ - 1, p_71852_3_, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_ + 1, p_71852_3_, p_71852_4_, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_, p_71852_4_ - 1, blockID);
			p_71852_1_.notifyBlocksOfNeighborChange(p_71852_2_, p_71852_3_, p_71852_4_ + 1, blockID);
		}
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	private boolean checkForBurnout(World p_72127_1_, int p_72127_2_, int p_72127_3_, int p_72127_4_, boolean p_72127_5_)
	{
		if(!redstoneUpdateInfoCache.containsKey(p_72127_1_))
		{
			redstoneUpdateInfoCache.put(p_72127_1_, new ArrayList());
		}
		List var6 = (List) redstoneUpdateInfoCache.get(p_72127_1_);
		if(p_72127_5_)
		{
			var6.add(new RedstoneUpdateInfo(p_72127_2_, p_72127_3_, p_72127_4_, p_72127_1_.getTotalWorldTime()));
		}
		int var7 = 0;
		for(int var8 = 0; var8 < var6.size(); ++var8)
		{
			RedstoneUpdateInfo var9 = (RedstoneUpdateInfo) var6.get(var8);
			if(var9.x == p_72127_2_ && var9.y == p_72127_3_ && var9.z == p_72127_4_)
			{
				++var7;
				if(var7 >= 8) return true;
			}
		}
		return false;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.torchRedstoneActive.blockID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.torchRedstoneActive.blockID;
	}
	
	@Override public boolean isAssociatedBlockID(int p_94334_1_)
	{
		return p_94334_1_ == Block.torchRedstoneIdle.blockID || p_94334_1_ == Block.torchRedstoneActive.blockID;
	}
	
	private boolean isIndirectlyPowered(World p_72128_1_, int p_72128_2_, int p_72128_3_, int p_72128_4_)
	{
		int var5 = p_72128_1_.getBlockMetadata(p_72128_2_, p_72128_3_, p_72128_4_);
		return var5 == 5 && p_72128_1_.getIndirectPowerOutput(p_72128_2_, p_72128_3_ - 1, p_72128_4_, 0) ? true : var5 == 3 && p_72128_1_.getIndirectPowerOutput(p_72128_2_, p_72128_3_, p_72128_4_ - 1, 2) ? true : var5 == 4 && p_72128_1_.getIndirectPowerOutput(p_72128_2_, p_72128_3_, p_72128_4_ + 1, 3) ? true : var5 == 1 && p_72128_1_.getIndirectPowerOutput(p_72128_2_ - 1, p_72128_3_, p_72128_4_, 4) ? true : var5 == 2 && p_72128_1_.getIndirectPowerOutput(p_72128_2_ + 1, p_72128_3_, p_72128_4_, 5);
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess p_71855_1_, int p_71855_2_, int p_71855_3_, int p_71855_4_, int p_71855_5_)
	{
		return p_71855_5_ == 0 ? isProvidingWeakPower(p_71855_1_, p_71855_2_, p_71855_3_, p_71855_4_, p_71855_5_) : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess p_71865_1_, int p_71865_2_, int p_71865_3_, int p_71865_4_, int p_71865_5_)
	{
		if(!torchActive) return 0;
		else
		{
			int var6 = p_71865_1_.getBlockMetadata(p_71865_2_, p_71865_3_, p_71865_4_);
			return var6 == 5 && p_71865_5_ == 1 ? 0 : var6 == 3 && p_71865_5_ == 3 ? 0 : var6 == 4 && p_71865_5_ == 2 ? 0 : var6 == 1 && p_71865_5_ == 5 ? 0 : var6 == 2 && p_71865_5_ == 4 ? 0 : 15;
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(p_71861_1_.getBlockMetadata(p_71861_2_, p_71861_3_, p_71861_4_) == 0)
		{
			super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		}
		if(torchActive)
		{
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_, p_71861_3_ - 1, p_71861_4_, blockID);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_, p_71861_3_ + 1, p_71861_4_, blockID);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_ - 1, p_71861_3_, p_71861_4_, blockID);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_ + 1, p_71861_3_, p_71861_4_, blockID);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_, p_71861_3_, p_71861_4_ - 1, blockID);
			p_71861_1_.notifyBlocksOfNeighborChange(p_71861_2_, p_71861_3_, p_71861_4_ + 1, blockID);
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!func_94397_d(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_5_))
		{
			boolean var6 = isIndirectlyPowered(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_);
			if(torchActive && var6 || !torchActive && !var6)
			{
				p_71863_1_.scheduleBlockUpdate(p_71863_2_, p_71863_3_, p_71863_4_, blockID, tickRate(p_71863_1_));
			}
		}
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(torchActive)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			double var7 = par2 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.2D;
			double var9 = par3 + 0.7F + (par5Random.nextFloat() - 0.5F) * 0.2D;
			double var11 = par4 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.2D;
			double var13 = 0.2199999988079071D;
			double var15 = 0.27000001072883606D;
			if(var6 == 1)
			{
				par1World.spawnParticle("reddust", var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
			} else if(var6 == 2)
			{
				par1World.spawnParticle("reddust", var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
			} else if(var6 == 3)
			{
				par1World.spawnParticle("reddust", var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
			} else if(var6 == 4)
			{
				par1World.spawnParticle("reddust", var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
			} else
			{
				par1World.spawnParticle("reddust", var7, var9, var11, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		if(torchActive)
		{
			blockIcon = par1IconRegister.registerIcon("redtorch_lit");
		} else
		{
			blockIcon = par1IconRegister.registerIcon("redtorch");
		}
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 2;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		boolean var6 = isIndirectlyPowered(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
		List var7 = (List) redstoneUpdateInfoCache.get(p_71847_1_);
		while(var7 != null && !var7.isEmpty() && p_71847_1_.getTotalWorldTime() - ((RedstoneUpdateInfo) var7.get(0)).updateTime > 60L)
		{
			var7.remove(0);
		}
		if(torchActive)
		{
			if(var6)
			{
				p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.torchRedstoneIdle.blockID, p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_), 3);
				if(checkForBurnout(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, true))
				{
					p_71847_1_.playSoundEffect(p_71847_2_ + 0.5F, p_71847_3_ + 0.5F, p_71847_4_ + 0.5F, "random.fizz", 0.5F, 2.6F + (p_71847_1_.rand.nextFloat() - p_71847_1_.rand.nextFloat()) * 0.8F);
					for(int var8 = 0; var8 < 5; ++var8)
					{
						double var9 = p_71847_2_ + p_71847_5_.nextDouble() * 0.6D + 0.2D;
						double var11 = p_71847_3_ + p_71847_5_.nextDouble() * 0.6D + 0.2D;
						double var13 = p_71847_4_ + p_71847_5_.nextDouble() * 0.6D + 0.2D;
						p_71847_1_.spawnParticle("smoke", var9, var11, var13, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		} else if(!var6 && !checkForBurnout(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, false))
		{
			p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.torchRedstoneActive.blockID, p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_), 3);
		}
	}
}
