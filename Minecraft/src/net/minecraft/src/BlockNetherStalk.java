package net.minecraft.src;

import java.util.Random;

public class BlockNetherStalk extends BlockFlower
{
	private static final String[] field_94373_a = new String[] { "netherStalk_0", "netherStalk_1", "netherStalk_2" };
	private Icon[] iconArray;
	
	protected BlockNetherStalk(int p_i3974_1_)
	{
		super(p_i3974_1_);
		setTickRandomly(true);
		float var2 = 0.5F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
		setCreativeTab((CreativeTabs) null);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		return canThisPlantGrowOnThisBlockID(p_71854_1_.getBlockId(p_71854_2_, p_71854_3_ - 1, p_71854_4_));
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return p_72263_1_ == Block.slowSand.blockID;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		if(!p_71914_1_.isRemote)
		{
			int var8 = 1;
			if(p_71914_5_ >= 3)
			{
				var8 = 2 + p_71914_1_.rand.nextInt(3);
				if(p_71914_7_ > 0)
				{
					var8 += p_71914_1_.rand.nextInt(p_71914_7_ + 1);
				}
			}
			for(int var9 = 0; var9 < var8; ++var9)
			{
				dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(Item.netherStalkSeeds));
			}
		}
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par2 >= 3 ? iconArray[2] : par2 > 0 ? iconArray[1] : iconArray[0];
	}
	
	@Override public int getRenderType()
	{
		return 6;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return 0;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.netherStalkSeeds.itemID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[field_94373_a.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(field_94373_a[var2]);
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
		if(var6 < 3 && p_71847_5_.nextInt(10) == 0)
		{
			++var6;
			p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var6, 2);
		}
		super.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
	}
}
