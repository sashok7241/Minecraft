package net.minecraft.src;

public class BiomeGenPlains extends BiomeGenBase
{
	protected BiomeGenPlains(int p_i3762_1_)
	{
		super(p_i3762_1_);
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.flowersPerChunk = 4;
		theBiomeDecorator.grassPerChunk = 10;
	}
}
