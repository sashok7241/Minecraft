package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockTallGrass extends BlockFlower
{
	private static final String[] grassTypes = new String[] { "deadbush", "tallgrass", "fern" };
	private Icon[] iconArray;
	
	protected BlockTallGrass(int p_i9093_1_)
	{
		super(p_i9093_1_, Material.vine);
		float var2 = 0.4F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.8F, 0.5F + var2);
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		return var5 == 0 ? 16777215 : par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
	}
	
	@Override public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerGrass.getGrassColor(var1, var3);
	}
	
	@Override public int getDamageValue(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
	{
		return p_71873_1_.getBlockMetadata(p_71873_2_, p_71873_3_, p_71873_4_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 >= iconArray.length)
		{
			par2 = 0;
		}
		return iconArray[par2];
	}
	
	@Override public int getRenderColor(int par1)
	{
		return par1 == 0 ? 16777215 : ColorizerFoliage.getFoliageColorBasic();
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 1; var4 < 3; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public void harvestBlock(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
	{
		if(!p_71893_1_.isRemote && p_71893_2_.getCurrentEquippedItem() != null && p_71893_2_.getCurrentEquippedItem().itemID == Item.shears.itemID)
		{
			p_71893_2_.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(Block.tallGrass, 1, p_71893_6_));
		} else
		{
			super.harvestBlock(p_71893_1_, p_71893_2_, p_71893_3_, p_71893_4_, p_71893_5_, p_71893_6_);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return p_71885_2_.nextInt(8) == 0 ? Item.seeds.itemID : -1;
	}
	
	@Override public int quantityDroppedWithBonus(int p_71910_1_, Random p_71910_2_)
	{
		return 1 + p_71910_2_.nextInt(p_71910_1_ * 2 + 1);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[grassTypes.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(grassTypes[var2]);
		}
	}
}
