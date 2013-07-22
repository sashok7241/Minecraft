package net.minecraft.src;

import java.util.Random;

public class BiomeGenDesert extends BiomeGenBase
{
	public BiomeGenDesert(int p_i3753_1_)
	{
		super(p_i3753_1_);
		spawnableCreatureList.clear();
		topBlock = (byte) Block.sand.blockID;
		fillerBlock = (byte) Block.sand.blockID;
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 2;
		theBiomeDecorator.reedsPerChunk = 50;
		theBiomeDecorator.cactiPerChunk = 10;
	}
	
	@Override public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_)
	{
		super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
		if(p_76728_2_.nextInt(1000) == 0)
		{
			int var5 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
			int var6 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
			WorldGenDesertWells var7 = new WorldGenDesertWells();
			var7.generate(p_76728_1_, p_76728_2_, var5, p_76728_1_.getHeightValue(var5, var6) + 1, var6);
		}
	}
}
