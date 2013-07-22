package net.minecraft.src;

import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase
{
	public BiomeGenTaiga(int par1)
	{
		super(par1);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
		theBiomeDecorator.treesPerChunk = 10;
		theBiomeDecorator.grassPerChunk = 1;
	}
	
	@Override public WorldGenerator getRandomWorldGenForTrees(Random par1Random)
	{
		return par1Random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2(false);
	}
}
