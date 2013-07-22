package net.minecraft.src;

import java.util.Random;

public class BlockCrops extends BlockFlower
{
	private Icon[] iconArray;
	
	protected BlockCrops(int p_i9048_1_)
	{
		super(p_i9048_1_);
		setTickRandomly(true);
		float var2 = 0.5F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
		setCreativeTab((CreativeTabs) null);
		setHardness(0.0F);
		setStepSound(soundGrassFootstep);
		disableStats();
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return p_72263_1_ == Block.tilledField.blockID;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, 0);
		if(!p_71914_1_.isRemote)
		{
			if(p_71914_5_ >= 7)
			{
				int var8 = 3 + p_71914_7_;
				for(int var9 = 0; var9 < var8; ++var9)
				{
					if(p_71914_1_.rand.nextInt(15) <= p_71914_5_)
					{
						dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(getSeedItem(), 1, 0));
					}
				}
			}
		}
	}
	
	public void fertilize(World p_72272_1_, int p_72272_2_, int p_72272_3_, int p_72272_4_)
	{
		int var5 = p_72272_1_.getBlockMetadata(p_72272_2_, p_72272_3_, p_72272_4_) + MathHelper.getRandomIntegerInRange(p_72272_1_.rand, 2, 5);
		if(var5 > 7)
		{
			var5 = 7;
		}
		p_72272_1_.setBlockMetadataWithNotify(p_72272_2_, p_72272_3_, p_72272_4_, var5, 2);
	}
	
	protected int getCropItem()
	{
		return Item.wheat.itemID;
	}
	
	private float getGrowthRate(World p_72273_1_, int p_72273_2_, int p_72273_3_, int p_72273_4_)
	{
		float var5 = 1.0F;
		int var6 = p_72273_1_.getBlockId(p_72273_2_, p_72273_3_, p_72273_4_ - 1);
		int var7 = p_72273_1_.getBlockId(p_72273_2_, p_72273_3_, p_72273_4_ + 1);
		int var8 = p_72273_1_.getBlockId(p_72273_2_ - 1, p_72273_3_, p_72273_4_);
		int var9 = p_72273_1_.getBlockId(p_72273_2_ + 1, p_72273_3_, p_72273_4_);
		int var10 = p_72273_1_.getBlockId(p_72273_2_ - 1, p_72273_3_, p_72273_4_ - 1);
		int var11 = p_72273_1_.getBlockId(p_72273_2_ + 1, p_72273_3_, p_72273_4_ - 1);
		int var12 = p_72273_1_.getBlockId(p_72273_2_ + 1, p_72273_3_, p_72273_4_ + 1);
		int var13 = p_72273_1_.getBlockId(p_72273_2_ - 1, p_72273_3_, p_72273_4_ + 1);
		boolean var14 = var8 == blockID || var9 == blockID;
		boolean var15 = var6 == blockID || var7 == blockID;
		boolean var16 = var10 == blockID || var11 == blockID || var12 == blockID || var13 == blockID;
		for(int var17 = p_72273_2_ - 1; var17 <= p_72273_2_ + 1; ++var17)
		{
			for(int var18 = p_72273_4_ - 1; var18 <= p_72273_4_ + 1; ++var18)
			{
				int var19 = p_72273_1_.getBlockId(var17, p_72273_3_ - 1, var18);
				float var20 = 0.0F;
				if(var19 == Block.tilledField.blockID)
				{
					var20 = 1.0F;
					if(p_72273_1_.getBlockMetadata(var17, p_72273_3_ - 1, var18) > 0)
					{
						var20 = 3.0F;
					}
				}
				if(var17 != p_72273_2_ || var18 != p_72273_4_)
				{
					var20 /= 4.0F;
				}
				var5 += var20;
			}
		}
		if(var16 || var14 && var15)
		{
			var5 /= 2.0F;
		}
		return var5;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 < 0 || par2 > 7)
		{
			par2 = 7;
		}
		return iconArray[par2];
	}
	
	@Override public int getRenderType()
	{
		return 6;
	}
	
	protected int getSeedItem()
	{
		return Item.seeds.itemID;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return p_71885_1_ == 7 ? getCropItem() : getSeedItem();
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return getSeedItem();
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[8];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon("crops_" + var2);
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		super.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
		if(p_71847_1_.getBlockLightValue(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9)
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			if(var6 < 7)
			{
				float var7 = getGrowthRate(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
				if(p_71847_5_.nextInt((int) (25.0F / var7) + 1) == 0)
				{
					++var6;
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var6, 2);
				}
			}
		}
	}
}
