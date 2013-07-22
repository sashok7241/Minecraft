package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable
{
	public BlockIce(int par1)
	{
		super(par1, "ice", Material.ice, false);
		slipperiness = 0.98F;
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int getMobilityFlag()
	{
		return 0;
	}
	
	@Override public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
		par2EntityPlayer.addExhaustion(0.025F);
		if(canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer))
		{
			ItemStack var9 = createStackedBlock(par6);
			if(var9 != null)
			{
				dropBlockAsItem_do(par1World, par3, par4, par5, var9);
			}
		} else
		{
			if(par1World.provider.isHellWorld)
			{
				par1World.setBlockToAir(par3, par4, par5);
				return;
			}
			int var7 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
			dropBlockAsItem(par1World, par3, par4, par5, par6, var7);
			Material var8 = par1World.getBlockMaterial(par3, par4 - 1, par5);
			if(var8.blocksMovement() || var8.isLiquid())
			{
				par1World.setBlock(par3, par4, par5, Block.waterMoving.blockID);
			}
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[blockID])
		{
			if(par1World.provider.isHellWorld)
			{
				par1World.setBlockToAir(par2, par3, par4);
				return;
			}
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlock(par2, par3, par4, Block.waterStill.blockID);
		}
	}
}
