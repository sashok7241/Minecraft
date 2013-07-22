package net.minecraft.src;

import java.util.Random;

public class BiomeGenSwamp extends BiomeGenBase
{
	protected BiomeGenSwamp(int p_i3764_1_)
	{
		super(p_i3764_1_);
		theBiomeDecorator.treesPerChunk = 2;
		theBiomeDecorator.flowersPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 1;
		theBiomeDecorator.mushroomsPerChunk = 8;
		theBiomeDecorator.reedsPerChunk = 10;
		theBiomeDecorator.clayPerChunk = 1;
		theBiomeDecorator.waterlilyPerChunk = 4;
		waterColorMultiplier = 14745518;
		spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
	}
	
	@Override public int getBiomeFoliageColor()
	{
		double var1 = getFloatTemperature();
		double var3 = getFloatRainfall();
		return ((ColorizerFoliage.getFoliageColor(var1, var3) & 16711422) + 5115470) / 2;
	}
	
	@Override public int getBiomeGrassColor()
	{
		double var1 = getFloatTemperature();
		double var3 = getFloatRainfall();
		return ((ColorizerGrass.getGrassColor(var1, var3) & 16711422) + 5115470) / 2;
	}
	
	@Override public WorldGenerator getRandomWorldGenForTrees(Random p_76740_1_)
	{
		return worldGeneratorSwamp;
	}
}
