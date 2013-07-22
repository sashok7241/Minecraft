package net.minecraft.src;

import java.util.Random;

public class BiomeGenJungle extends BiomeGenBase
{
	public BiomeGenJungle(int p_i3759_1_)
	{
		super(p_i3759_1_);
		theBiomeDecorator.treesPerChunk = 50;
		theBiomeDecorator.grassPerChunk = 25;
		theBiomeDecorator.flowersPerChunk = 4;
		spawnableMonsterList.add(new SpawnListEntry(EntityOcelot.class, 2, 1, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntityChicken.class, 10, 4, 4));
	}
	
	@Override public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_)
	{
		super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
		WorldGenVines var5 = new WorldGenVines();
		for(int var6 = 0; var6 < 50; ++var6)
		{
			int var7 = p_76728_3_ + p_76728_2_.nextInt(16) + 8;
			byte var8 = 64;
			int var9 = p_76728_4_ + p_76728_2_.nextInt(16) + 8;
			var5.generate(p_76728_1_, p_76728_2_, var7, var8, var9);
		}
	}
	
	@Override public WorldGenerator getRandomWorldGenForGrass(Random p_76730_1_)
	{
		return p_76730_1_.nextInt(4) == 0 ? new WorldGenTallGrass(Block.tallGrass.blockID, 2) : new WorldGenTallGrass(Block.tallGrass.blockID, 1);
	}
	
	@Override public WorldGenerator getRandomWorldGenForTrees(Random p_76740_1_)
	{
		return p_76740_1_.nextInt(10) == 0 ? worldGeneratorBigTree : p_76740_1_.nextInt(2) == 0 ? new WorldGenShrub(3, 0) : p_76740_1_.nextInt(3) == 0 ? new WorldGenHugeTrees(false, 10 + p_76740_1_.nextInt(20), 3, 3) : new WorldGenTrees(false, 4 + p_76740_1_.nextInt(7), 3, 3, true);
	}
}
