package net.minecraft.src;

import java.util.Random;

public class BlockStem extends BlockFlower
{
	private final Block fruitType;
	private Icon theIcon;
	
	protected BlockStem(int p_i3998_1_, Block p_i3998_2_)
	{
		super(p_i3998_1_);
		fruitType = p_i3998_2_;
		setTickRandomly(true);
		float var3 = 0.125F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
		setCreativeTab((CreativeTabs) null);
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return p_72263_1_ == Block.tilledField.blockID;
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return getRenderColor(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
		if(!p_71914_1_.isRemote)
		{
			Item var8 = null;
			if(fruitType == Block.pumpkin)
			{
				var8 = Item.pumpkinSeeds;
			}
			if(fruitType == Block.melon)
			{
				var8 = Item.melonSeeds;
			}
			for(int var9 = 0; var9 < 3; ++var9)
			{
				if(p_71914_1_.rand.nextInt(15) <= p_71914_5_)
				{
					dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(var8));
				}
			}
		}
	}
	
	public void fertilizeStem(World p_72264_1_, int p_72264_2_, int p_72264_3_, int p_72264_4_)
	{
		int var5 = p_72264_1_.getBlockMetadata(p_72264_2_, p_72264_3_, p_72264_4_) + MathHelper.getRandomIntegerInRange(p_72264_1_.rand, 2, 5);
		if(var5 > 7)
		{
			var5 = 7;
		}
		p_72264_1_.setBlockMetadataWithNotify(p_72264_2_, p_72264_3_, p_72264_4_, var5, 2);
	}
	
	public Icon func_94368_p()
	{
		return theIcon;
	}
	
	private float getGrowthModifier(World p_72266_1_, int p_72266_2_, int p_72266_3_, int p_72266_4_)
	{
		float var5 = 1.0F;
		int var6 = p_72266_1_.getBlockId(p_72266_2_, p_72266_3_, p_72266_4_ - 1);
		int var7 = p_72266_1_.getBlockId(p_72266_2_, p_72266_3_, p_72266_4_ + 1);
		int var8 = p_72266_1_.getBlockId(p_72266_2_ - 1, p_72266_3_, p_72266_4_);
		int var9 = p_72266_1_.getBlockId(p_72266_2_ + 1, p_72266_3_, p_72266_4_);
		int var10 = p_72266_1_.getBlockId(p_72266_2_ - 1, p_72266_3_, p_72266_4_ - 1);
		int var11 = p_72266_1_.getBlockId(p_72266_2_ + 1, p_72266_3_, p_72266_4_ - 1);
		int var12 = p_72266_1_.getBlockId(p_72266_2_ + 1, p_72266_3_, p_72266_4_ + 1);
		int var13 = p_72266_1_.getBlockId(p_72266_2_ - 1, p_72266_3_, p_72266_4_ + 1);
		boolean var14 = var8 == blockID || var9 == blockID;
		boolean var15 = var6 == blockID || var7 == blockID;
		boolean var16 = var10 == blockID || var11 == blockID || var12 == blockID || var13 == blockID;
		for(int var17 = p_72266_2_ - 1; var17 <= p_72266_2_ + 1; ++var17)
		{
			for(int var18 = p_72266_4_ - 1; var18 <= p_72266_4_ + 1; ++var18)
			{
				int var19 = p_72266_1_.getBlockId(var17, p_72266_3_ - 1, var18);
				float var20 = 0.0F;
				if(var19 == Block.tilledField.blockID)
				{
					var20 = 1.0F;
					if(p_72266_1_.getBlockMetadata(var17, p_72266_3_ - 1, var18) > 0)
					{
						var20 = 3.0F;
					}
				}
				if(var17 != p_72266_2_ || var18 != p_72266_4_)
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
	
	@Override public int getRenderColor(int par1)
	{
		int var2 = par1 * 32;
		int var3 = 255 - par1 * 8;
		int var4 = par1 * 4;
		return var2 << 16 | var3 << 8 | var4;
	}
	
	@Override public int getRenderType()
	{
		return 19;
	}
	
	public int getState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		return var5 < 7 ? -1 : par1IBlockAccess.getBlockId(par2 - 1, par3, par4) == fruitType.blockID ? 0 : par1IBlockAccess.getBlockId(par2 + 1, par3, par4) == fruitType.blockID ? 1 : par1IBlockAccess.getBlockId(par2, par3, par4 - 1) == fruitType.blockID ? 2 : par1IBlockAccess.getBlockId(par2, par3, par4 + 1) == fruitType.blockID ? 3 : -1;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return -1;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return fruitType == Block.pumpkin ? Item.pumpkinSeeds.itemID : fruitType == Block.melon ? Item.melonSeeds.itemID : 0;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("stem_straight");
		theIcon = par1IconRegister.registerIcon("stem_bent");
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		maxY = (p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_) * 2 + 2) / 16.0F;
		float var5 = 0.125F;
		setBlockBounds(0.5F - var5, 0.0F, 0.5F - var5, 0.5F + var5, (float) maxY, 0.5F + var5);
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		float var1 = 0.125F;
		setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		super.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
		if(p_71847_1_.getBlockLightValue(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9)
		{
			float var6 = getGrowthModifier(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
			if(p_71847_5_.nextInt((int) (25.0F / var6) + 1) == 0)
			{
				int var7 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
				if(var7 < 7)
				{
					++var7;
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var7, 2);
				} else
				{
					if(p_71847_1_.getBlockId(p_71847_2_ - 1, p_71847_3_, p_71847_4_) == fruitType.blockID) return;
					if(p_71847_1_.getBlockId(p_71847_2_ + 1, p_71847_3_, p_71847_4_) == fruitType.blockID) return;
					if(p_71847_1_.getBlockId(p_71847_2_, p_71847_3_, p_71847_4_ - 1) == fruitType.blockID) return;
					if(p_71847_1_.getBlockId(p_71847_2_, p_71847_3_, p_71847_4_ + 1) == fruitType.blockID) return;
					int var8 = p_71847_5_.nextInt(4);
					int var9 = p_71847_2_;
					int var10 = p_71847_4_;
					if(var8 == 0)
					{
						var9 = p_71847_2_ - 1;
					}
					if(var8 == 1)
					{
						++var9;
					}
					if(var8 == 2)
					{
						var10 = p_71847_4_ - 1;
					}
					if(var8 == 3)
					{
						++var10;
					}
					int var11 = p_71847_1_.getBlockId(var9, p_71847_3_ - 1, var10);
					if(p_71847_1_.getBlockId(var9, p_71847_3_, var10) == 0 && (var11 == Block.tilledField.blockID || var11 == Block.dirt.blockID || var11 == Block.grass.blockID))
					{
						p_71847_1_.setBlock(var9, p_71847_3_, var10, fruitType.blockID);
					}
				}
			}
		}
	}
}
