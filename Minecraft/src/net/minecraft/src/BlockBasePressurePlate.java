package net.minecraft.src;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block
{
	private String pressurePlateIconName;
	
	protected BlockBasePressurePlate(int par1, String par2Str, Material par3Material)
	{
		super(par1, par3Material);
		pressurePlateIconName = par2Str;
		setCreativeTab(CreativeTabs.tabRedstone);
		setTickRandomly(true);
		func_94353_c_(getMetaFromWeight(15));
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if(getPowerSupply(par6) > 0)
		{
			func_94354_b_(par1World, par2, par3, par4);
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || BlockFence.isIdAFence(par1World.getBlockId(par2, par3 - 1, par4));
	}
	
	@Override public boolean canProvidePower()
	{
		return true;
	}
	
	protected void func_94353_c_(int par1)
	{
		boolean var2 = getPowerSupply(par1) > 0;
		float var3 = 0.0625F;
		if(var2)
		{
			setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.03125F, 1.0F - var3);
		} else
		{
			setBlockBounds(var3, 0.0F, var3, 1.0F - var3, 0.0625F, 1.0F - var3);
		}
	}
	
	protected void func_94354_b_(World par1World, int par2, int par3, int par4)
	{
		par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
		par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
	}
	
	@Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return true;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
	
	protected AxisAlignedBB getSensitiveAABB(int par1, int par2, int par3)
	{
		float var4 = 0.125F;
		return AxisAlignedBB.getAABBPool().getAABB(par1 + var4, par2, par3 + var4, par1 + 1 - var4, par2 + 0.25D, par3 + 1 - var4);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return par5 == 1 ? getPowerSupply(par1IBlockAccess.getBlockMetadata(par2, par3, par4)) : 0;
	}
	
	@Override public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return getPowerSupply(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		if(!par1World.isRemote)
		{
			int var6 = getPowerSupply(par1World.getBlockMetadata(par2, par3, par4));
			if(var6 == 0)
			{
				setStateIfMobInteractsWithPlate(par1World, par2, par3, par4, var6);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		boolean var6 = false;
		if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !BlockFence.isIdAFence(par1World.getBlockId(par2, par3 - 1, par4)))
		{
			var6 = true;
		}
		if(var6)
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		func_94353_c_(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.5F;
		float var2 = 0.125F;
		float var3 = 0.5F;
		setBlockBounds(0.5F - var1, 0.5F - var2, 0.5F - var3, 0.5F + var1, 0.5F + var2, 0.5F + var3);
	}
	
	protected void setStateIfMobInteractsWithPlate(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = getPlateState(par1World, par2, par3, par4);
		boolean var7 = par5 > 0;
		boolean var8 = var6 > 0;
		if(par5 != var6)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, getMetaFromWeight(var6), 2);
			func_94354_b_(par1World, par2, par3, par4);
			par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
		}
		if(!var8 && var7)
		{
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.1D, par4 + 0.5D, "random.click", 0.3F, 0.5F);
		} else if(var8 && !var7)
		{
			par1World.playSoundEffect(par2 + 0.5D, par3 + 0.1D, par4 + 0.5D, "random.click", 0.3F, 0.6F);
		}
		if(var8)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
		}
	}
	
	@Override public int tickRate(World par1World)
	{
		return 20;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote)
		{
			int var6 = getPowerSupply(par1World.getBlockMetadata(par2, par3, par4));
			if(var6 > 0)
			{
				setStateIfMobInteractsWithPlate(par1World, par2, par3, par4, var6);
			}
		}
	}
}
