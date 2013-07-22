package net.minecraft.src;

import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase
{
	public BiomeGenTaiga(int p_i3765_1_)
	{
		super(p_i3765_1_);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
		theBiomeDecorator.treesPerChunk = 10;
		theBiomeDecorator.grassPerChunk = 1;
	}
	
	@Override public WorldGenerator getRandomWorldGenForTrees(Random p_76740_1_)
	{
		return p_76740_1_.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2(false);
	}
}
