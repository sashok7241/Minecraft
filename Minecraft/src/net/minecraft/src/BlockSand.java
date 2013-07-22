package net.minecraft.src;

import java.util.Random;

public class BlockSand extends Block
{
	public static boolean fallInstantly = false;
	
	public BlockSand(int p_i9060_1_)
	{
		super(p_i9060_1_, Material.sand);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	public BlockSand(int p_i9061_1_, Material p_i9061_2_)
	{
		super(p_i9061_1_, p_i9061_2_);
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		p_71861_1_.scheduleBlockUpdate(p_71861_2_, p_71861_3_, p_71861_4_, blockID, tickRate(p_71861_1_));
	}
	
	public void onFinishFalling(World p_82519_1_, int p_82519_2_, int p_82519_3_, int p_82519_4_, int p_82519_5_)
	{
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		p_71863_1_.scheduleBlockUpdate(p_71863_2_, p_71863_3_, p_71863_4_, blockID, tickRate(p_71863_1_));
	}
	
	protected void onStartFalling(EntityFallingSand p_82520_1_)
	{
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 2;
	}
	
	private void tryToFall(World p_72190_1_, int p_72190_2_, int p_72190_3_, int p_72190_4_)
	{
		if(canFallBelow(p_72190_1_, p_72190_2_, p_72190_3_ - 1, p_72190_4_) && p_72190_3_ >= 0)
		{
			byte var8 = 32;
			if(!fallInstantly && p_72190_1_.checkChunksExist(p_72190_2_ - var8, p_72190_3_ - var8, p_72190_4_ - var8, p_72190_2_ + var8, p_72190_3_ + var8, p_72190_4_ + var8))
			{
				if(!p_72190_1_.isRemote)
				{
					EntityFallingSand var9 = new EntityFallingSand(p_72190_1_, p_72190_2_ + 0.5F, p_72190_3_ + 0.5F, p_72190_4_ + 0.5F, blockID, p_72190_1_.getBlockMetadata(p_72190_2_, p_72190_3_, p_72190_4_));
					onStartFalling(var9);
					p_72190_1_.spawnEntityInWorld(var9);
				}
			} else
			{
				p_72190_1_.setBlockToAir(p_72190_2_, p_72190_3_, p_72190_4_);
				while(canFallBelow(p_72190_1_, p_72190_2_, p_72190_3_ - 1, p_72190_4_) && p_72190_3_ > 0)
				{
					--p_72190_3_;
				}
				if(p_72190_3_ > 0)
				{
					p_72190_1_.setBlock(p_72190_2_, p_72190_3_, p_72190_4_, blockID);
				}
			}
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			tryToFall(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
		}
	}
	
	public static boolean canFallBelow(World p_72191_0_, int p_72191_1_, int p_72191_2_, int p_72191_3_)
	{
		int var4 = p_72191_0_.getBlockId(p_72191_1_, p_72191_2_, p_72191_3_);
		if(var4 == 0) return true;
		else if(var4 == Block.fire.blockID) return true;
		else
		{
			Material var5 = Block.blocksList[var4].blockMaterial;
			return var5 == Material.water ? true : var5 == Material.lava;
		}
	}
}
