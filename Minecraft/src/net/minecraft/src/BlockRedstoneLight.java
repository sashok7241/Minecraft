package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneLight extends Block
{
	private final boolean powered;
	
	public BlockRedstoneLight(int p_i3988_1_, boolean p_i3988_2_)
	{
		super(p_i3988_1_, Material.redstoneLight);
		powered = p_i3988_2_;
		if(p_i3988_2_)
		{
			setLightValue(1.0F);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.redstoneLampIdle.blockID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.redstoneLampIdle.blockID;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(!p_71861_1_.isRemote)
		{
			if(powered && !p_71861_1_.isBlockIndirectlyGettingPowered(p_71861_2_, p_71861_3_, p_71861_4_))
			{
				p_71861_1_.scheduleBlockUpdate(p_71861_2_, p_71861_3_, p_71861_4_, blockID, 4);
			} else if(!powered && p_71861_1_.isBlockIndirectlyGettingPowered(p_71861_2_, p_71861_3_, p_71861_4_))
			{
				p_71861_1_.setBlock(p_71861_2_, p_71861_3_, p_71861_4_, Block.redstoneLampActive.blockID, 0, 2);
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote)
		{
			if(powered && !p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_))
			{
				p_71863_1_.scheduleBlockUpdate(p_71863_2_, p_71863_3_, p_71863_4_, blockID, 4);
			} else if(!powered && p_71863_1_.isBlockIndirectlyGettingPowered(p_71863_2_, p_71863_3_, p_71863_4_))
			{
				p_71863_1_.setBlock(p_71863_2_, p_71863_3_, p_71863_4_, Block.redstoneLampActive.blockID, 0, 2);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		if(powered)
		{
			blockIcon = par1IconRegister.registerIcon("redstoneLight_lit");
		} else
		{
			blockIcon = par1IconRegister.registerIcon("redstoneLight");
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote && powered && !p_71847_1_.isBlockIndirectlyGettingPowered(p_71847_2_, p_71847_3_, p_71847_4_))
		{
			p_71847_1_.setBlock(p_71847_2_, p_71847_3_, p_71847_4_, Block.redstoneLampIdle.blockID, 0, 2);
		}
	}
}
