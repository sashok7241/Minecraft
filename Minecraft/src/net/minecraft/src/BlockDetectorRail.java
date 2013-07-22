package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockDetectorRail extends BlockRailBase
{
	private Icon[] iconArray;
	
	public BlockDetectorRail(int par1)
	{
		super(par1, true);
		setTickRandomly(true);
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	@Override public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		if((par1World.getBlockMetadata(par2, par3, par4) & 8) > 0)
		{
			float var6 = 0.125F;
			List var7 = par1World.selectEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(par2 + var6, par3, par4 + var6, par2 + 1 - var6, par3 + 1 - var6, par4 + 1 - var6), IEntitySelector.selectInventories);
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
	
	@Override public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) == 0 ? 0 : par5 == 1 ? 15 : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return (par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 8) != 0 ? 15 : 0;
	}
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		if(!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			if((var6 & 8) == 0)
			{
				setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, var6);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[2];
		iconArray[0] = par1IconRegister.registerIcon("detectorRail");
		iconArray[1] = par1IconRegister.registerIcon("detectorRail_on");
	}
	
	private void setStateIfMinecartInteractsWithRail(World par1World, int par2, int par3, int par4, int par5)
	{
		boolean var6 = (par5 & 8) != 0;
		boolean var7 = false;
		float var8 = 0.125F;
		List var9 = par1World.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getAABBPool().getAABB(par2 + var8, par3, par4 + var8, par2 + 1 - var8, par3 + 1 - var8, par4 + 1 - var8));
		if(!var9.isEmpty())
		{
			var7 = true;
		}
		if(var7 && !var6)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 8, 3);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
		}
		if(!var7 && var6)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 & 7, 3);
			par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
			par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
			par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
		}
		if(var7)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
		}
		par1World.func_96440_m(par2, par3, par4, blockID);
	}
	
	@Override public int tickRate(World par1World)
	{
		return 20;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			if((var6 & 8) != 0)
			{
				setStateIfMinecartInteractsWithRail(par1World, par2, par3, par4, var6);
			}
		}
	}
}
