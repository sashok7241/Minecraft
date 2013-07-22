package net.minecraft.src;

import java.util.Random;

public class BlockIce extends BlockBreakable
{
	public BlockIce(int p_i9066_1_)
	{
		super(p_i9066_1_, "ice", Material.ice, false);
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
	
	@Override public void harvestBlock(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
	{
		p_71893_2_.addStat(StatList.mineBlockStatArray[blockID], 1);
		p_71893_2_.addExhaustion(0.025F);
		if(canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(p_71893_2_))
		{
			ItemStack var9 = createStackedBlock(p_71893_6_);
			if(var9 != null)
			{
				dropBlockAsItem_do(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, var9);
			}
		} else
		{
			if(p_71893_1_.provider.isHellWorld)
			{
				p_71893_1_.setBlockToAir(p_71893_3_, p_71893_4_, p_71893_5_);
				return;
			}
			int var7 = EnchantmentHelper.getFortuneModifier(p_71893_2_);
			dropBlockAsItem(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, p_71893_6_, var7);
			Material var8 = p_71893_1_.getBlockMaterial(p_71893_3_, p_71893_4_ - 1, p_71893_5_);
			if(var8.blocksMovement() || var8.isLiquid())
			{
				p_71893_1_.setBlock(p_71893_3_, p_71893_4_, p_71893_5_, Block.waterMoving.blockID);
			}
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(p_71847_1_.getSavedLightValue(EnumSkyBlock.Block, p_71847_2_, p_71847_3_, p_71847_4_) > 11 - Block.lightOpacity[blockID])
		{
			if(p_71847_1_.provider.isHellWorld)
			{
				p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
				return;
			}
			dropBlockAsItem(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_), 0);
			p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.waterStill.blockID);
		}
	}
}
