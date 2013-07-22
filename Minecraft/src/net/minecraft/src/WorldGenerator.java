package net.minecraft.src;

import java.util.Random;

public abstract class WorldGenerator
{
	private final boolean doBlockNotify;
	
	public WorldGenerator()
	{
		doBlockNotify = false;
	}
	
	public WorldGenerator(boolean p_i3789_1_)
	{
		doBlockNotify = p_i3789_1_;
	}
	
	public abstract boolean generate(World var1, Random var2, int var3, int var4, int var5);
	
	protected void setBlock(World p_76486_1_, int p_76486_2_, int p_76486_3_, int p_76486_4_, int p_76486_5_)
	{
		setBlockAndMetadata(p_76486_1_, p_76486_2_, p_76486_3_, p_76486_4_, p_76486_5_, 0);
	}
	
	protected void setBlockAndMetadata(World p_76485_1_, int p_76485_2_, int p_76485_3_, int p_76485_4_, int p_76485_5_, int p_76485_6_)
	{
		if(doBlockNotify)
		{
			p_76485_1_.setBlock(p_76485_2_, p_76485_3_, p_76485_4_, p_76485_5_, p_76485_6_, 3);
		} else
		{
			p_76485_1_.setBlock(p_76485_2_, p_76485_3_, p_76485_4_, p_76485_5_, p_76485_6_, 2);
		}
	}
	
	public void setScale(double p_76487_1_, double p_76487_3_, double p_76487_5_)
	{
	}
}
