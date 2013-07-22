package net.minecraft.src;

public class BiomeGenPlains extends BiomeGenBase
{
	protected BiomeGenPlains(int par1)
	{
		super(par1);
		theBiomeDecorator.treesPerChunk = -999;
		theBiomeDecorator.flowersPerChunk = 4;
		theBiomeDecorator.grassPerChunk = 10;
	}
}
