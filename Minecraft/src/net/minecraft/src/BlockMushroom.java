package net.minecraft.src;

import java.util.Random;

public class BlockMushroom extends BlockFlower
{
	private final String field_94374_a;
	
	protected BlockMushroom(int p_i9073_1_, String p_i9073_2_)
	{
		super(p_i9073_1_);
		field_94374_a = p_i9073_2_;
		float var3 = 0.2F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
		setTickRandomly(true);
	}
	
	@Override public boolean canBlockStay(World p_71854_1_, int p_71854_2_, int p_71854_3_, int p_71854_4_)
	{
		if(p_71854_3_ >= 0 && p_71854_3_ < 256)
		{
			int var5 = p_71854_1_.getBlockId(p_71854_2_, p_71854_3_ - 1, p_71854_4_);
			return var5 == Block.mycelium.blockID || p_71854_1_.getFullBlockLightValue(p_71854_2_, p_71854_3_, p_71854_4_) < 13 && canThisPlantGrowOnThisBlockID(var5);
		} else return false;
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return super.canPlaceBlockAt(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_) && canBlockStay(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int p_72263_1_)
	{
		return Block.opaqueCubeLookup[p_72263_1_];
	}
	
	public boolean fertilizeMushroom(World p_72271_1_, int p_72271_2_, int p_72271_3_, int p_72271_4_, Random p_72271_5_)
	{
		int var6 = p_72271_1_.getBlockMetadata(p_72271_2_, p_72271_3_, p_72271_4_);
		p_72271_1_.setBlockToAir(p_72271_2_, p_72271_3_, p_72271_4_);
		WorldGenBigMushroom var7 = null;
		if(blockID == Block.mushroomBrown.blockID)
		{
			var7 = new WorldGenBigMushroom(0);
		} else if(blockID == Block.mushroomRed.blockID)
		{
			var7 = new WorldGenBigMushroom(1);
		}
		if(var7 != null && var7.generate(p_72271_1_, p_72271_5_, p_72271_2_, p_72271_3_, p_72271_4_)) return true;
		else
		{
			p_72271_1_.setBlock(p_72271_2_, p_72271_3_, p_72271_4_, blockID, var6, 3);
			return false;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(field_94374_a);
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(p_71847_5_.nextInt(25) == 0)
		{
			byte var6 = 4;
			int var7 = 5;
			int var8;
			int var9;
			int var10;
			for(var8 = p_71847_2_ - var6; var8 <= p_71847_2_ + var6; ++var8)
			{
				for(var9 = p_71847_4_ - var6; var9 <= p_71847_4_ + var6; ++var9)
				{
					for(var10 = p_71847_3_ - 1; var10 <= p_71847_3_ + 1; ++var10)
					{
						if(p_71847_1_.getBlockId(var8, var10, var9) == blockID)
						{
							--var7;
							if(var7 <= 0) return;
						}
					}
				}
			}
			var8 = p_71847_2_ + p_71847_5_.nextInt(3) - 1;
			var9 = p_71847_3_ + p_71847_5_.nextInt(2) - p_71847_5_.nextInt(2);
			var10 = p_71847_4_ + p_71847_5_.nextInt(3) - 1;
			for(int var11 = 0; var11 < 4; ++var11)
			{
				if(p_71847_1_.isAirBlock(var8, var9, var10) && canBlockStay(p_71847_1_, var8, var9, var10))
				{
					p_71847_2_ = var8;
					p_71847_3_ = var9;
					p_71847_4_ = var10;
				}
				var8 = p_71847_2_ + p_71847_5_.nextInt(3) - 1;
				var9 = p_71847_3_ + p_71847_5_.nextInt(2) - p_71847_5_.nextInt(2);
				var10 = p_71847_4_ + p_71847_5_.nextInt(3) - 1;
			}
			if(p_71847_1_.isAirBlock(var8, var9, var10) && canBlockStay(p_71847_1_, var8, var9, var10))
			{
				p_71847_1_.setBlock(var8, var9, var10, blockID);
			}
		}
	}
}
