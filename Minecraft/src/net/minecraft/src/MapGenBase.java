package net.minecraft.src;

import java.util.Random;

public class MapGenBase
{
	protected int range = 8;
	protected Random rand = new Random();
	protected World worldObj;
	
	public void generate(IChunkProvider p_75036_1_, World p_75036_2_, int p_75036_3_, int p_75036_4_, byte[] p_75036_5_)
	{
		int var6 = range;
		worldObj = p_75036_2_;
		rand.setSeed(p_75036_2_.getSeed());
		long var7 = rand.nextLong();
		long var9 = rand.nextLong();
		for(int var11 = p_75036_3_ - var6; var11 <= p_75036_3_ + var6; ++var11)
		{
			for(int var12 = p_75036_4_ - var6; var12 <= p_75036_4_ + var6; ++var12)
			{
				long var13 = var11 * var7;
				long var15 = var12 * var9;
				rand.setSeed(var13 ^ var15 ^ p_75036_2_.getSeed());
				recursiveGenerate(p_75036_2_, var11, var12, p_75036_3_, p_75036_4_, p_75036_5_);
			}
		}
	}
	
	protected void recursiveGenerate(World p_75037_1_, int p_75037_2_, int p_75037_3_, int p_75037_4_, int p_75037_5_, byte[] p_75037_6_)
	{
	}
}
