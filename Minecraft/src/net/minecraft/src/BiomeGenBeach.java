package net.minecraft.src;

public class BiomeGenBeach extends BiomeGenBase
{
	public BiomeGenBeach(int p_i3745_1_)
	{
		super(p_i3745_1_);
		spawnableCreatureList.clear();
		topBlock = (byte) Block.sand.blockID;
		fillerBlock = (byte) Block.sand.blockID;
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.deadBushPerChunk = 0;
		theBiomeDecorator.reedsPerChunk = 0;
		theBiomeDecorator.cactiPerChunk = 0;
	}
}
