package net.minecraft.src;

import java.util.Random;

public class BiomeGenForest extends BiomeGenBase
{
	public BiomeGenForest(int p_i3756_1_)
	{
		super(p_i3756_1_);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
		theBiomeDecorator.treesPerChunk = 10;
		theBiomeDecorator.grassPerChunk = 2;
	}
	
	@Override public WorldGenerator getRandomWorldGenForTrees(Random p_76740_1_)
	{
		return p_76740_1_.nextInt(5) == 0 ? worldGeneratorForest : p_76740_1_.nextInt(10) == 0 ? worldGeneratorBigTree : worldGeneratorTrees;
	}
}
