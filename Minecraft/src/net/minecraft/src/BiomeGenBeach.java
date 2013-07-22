package net.minecraft.src;

public class BiomeGenBeach extends BiomeGenBase
{
	public BiomeGenBeach(int par1)
	{
		super(par1);
		spawnableCreatureList.clear();
		topBlock = (byte) Block.sand.blockID;
		fillerBlock = (byte) Block.sand.blockID;
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 0;
		theBiomeDecorator.reedsPerChunk = 0;
		theBiomeDecorator.cactiPerChunk = 0;
	}
}
