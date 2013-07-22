package net.minecraft.src;

import java.util.Random;

public class BiomeGenForest extends BiomeGenBase
{
	public BiomeGenForest(int par1)
	{
		super(par1);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
		theBiomeDecorator.treesPerChunk = 10;
		theBiomeDecorator.grassPerChunk = 2;
	}
	
	@Override public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(5) == 0 ? worldGeneratorForest : par1Random.nextInt(10) == 0 ? worldGeneratorBigTree : worldGeneratorTrees;
	}
}
