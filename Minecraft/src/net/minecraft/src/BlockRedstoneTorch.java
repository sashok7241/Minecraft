package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BlockRedstoneTorch extends BlockTorch
{
	private boolean torchActive;
	private static Map redstoneUpdateInfoCache = new HashMap();
	
	protected BlockRedstoneTorch(int par1, boolean par2)
	{
		super(par1);
		torchActive = par2;
		setTickRandomly(true);
		setCreativeTab((CreativeTabs) null);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if(torchActive)
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		}
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	private boolean checkForBurnout(World par1World, int par2, int par3, int par4, boolean par5)
	{
		if(!redstoneUpdateInfoCache.containsKey(par1World))
		{
			redstoneUpdateInfoCache.put(par1World, new ArrayList());
		}
		List var6 = (List) redstoneUpdateInfoCache.get(par1World);
		if(par5)
		{
			var6.add(new RedstoneUpdateInfo(par2, par3, par4, par1World.getTotalWorldTime()));
		}
		int var7 = 0;
		for(int var8 = 0; var8 < var6.size(); ++var8)
		{
			RedstoneUpdateInfo var9 = (RedstoneUpdateInfo) var6.get(var8);
			if(var9.x == par2 && var9.y == par3 && var9.z == par4)
			{
				++var7;
				if(var7 >= 8) return true;
			}
		}
		return false;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.torchRedstoneActive.blockID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.torchRedstoneActive.blockID;
	}
	
	@Override public boolean isAssociatedBlockID(int par1)
	{
		return par1 == Block.torchRedstoneIdle.blockID || par1 == Block.torchRedstoneActive.blockID;
	}
	
	private boolean isIndirectlyPowered(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		return var5 == 5 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0) ? true : var5 == 3 && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2) ? true : var5 == 4 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3) ? true : var5 == 1 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4) ? true : var5 == 2 && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5);
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 0 ? isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5) : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(!torchActive) return 0;
		else
		{
			int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
			return var6 == 5 && par5 == 1 ? 0 : var6 == 3 && par5 == 3 ? 0 : var6 == 4 && par5 == 2 ? 0 : var6 == 1 && par5 == 5 ? 0 : var6 == 2 && par5 == 4 ? 0 : 15;
		}
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(par1World.getBlockMetadata(par2, par3, par4) == 0)
		{
			super.onBlockAdded(par1World, par2, par3, par4);
		}
		if(torchActive)
		{
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!func_94397_d(par1World, par2, par3, par4, par5))
		{
			boolean var6 = isIndirectlyPowered(par1World, par2, par3, par4);
			if(torchActive && var6 || !torchActive && !var6)
			{
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
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
	
	@Override public int tickRate(World par1World)
	{
		return 2;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		boolean var6 = isIndirectlyPowered(par1World, par2, par3, par4);
		List var7 = (List) redstoneUpdateInfoCache.get(par1World);
		while(var7 != null && !var7.isEmpty() && par1World.getTotalWorldTime() - ((RedstoneUpdateInfo) var7.get(0)).updateTime > 60L)
		{
			var7.remove(0);
		}
		if(torchActive)
		{
			if(var6)
			{
				par1World.setBlock(par2, par3, par4, Block.torchRedstoneIdle.blockID, par1World.getBlockMetadata(par2, par3, par4), 3);
				if(checkForBurnout(par1World, par2, par3, par4, true))
				{
					par1World.playSoundEffect(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);
					for(int var8 = 0; var8 < 5; ++var8)
					{
						double var9 = par2 + par5Random.nextDouble() * 0.6D + 0.2D;
						double var11 = par3 + par5Random.nextDouble() * 0.6D + 0.2D;
						double var13 = par4 + par5Random.nextDouble() * 0.6D + 0.2D;
						par1World.spawnParticle("smoke", var9, var11, var13, 0.0D, 0.0D, 0.0D);
					}
				}
			}
		} else if(!var6 && !checkForBurnout(par1World, par2, par3, par4, false))
		{
			par1World.setBlock(par2, par3, par4, Block.torchRedstoneActive.blockID, par1World.getBlockMetadata(par2, par3, par4), 3);
		}
	}
}
