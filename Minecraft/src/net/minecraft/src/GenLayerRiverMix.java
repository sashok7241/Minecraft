package net.minecraft.src;

public class GenLayerRiverMix extends GenLayer
{
	private GenLayer biomePatternGeneratorChain;
	private GenLayer riverPatternGeneratorChain;
	
	public GenLayerRiverMix(long par1, GenLayer par3GenLayer, GenLayer par4GenLayer)
	{
		super(par1);
		biomePatternGeneratorChain = par3GenLayer;
		riverPatternGeneratorChain = par4GenLayer;
	}
	
	@Override public int[] getInts(int par1, int par2, int par3, int par4)
	{
		int[] var5 = biomePatternGeneratorChain.getInts(par1, par2, par3, par4);
		int[] var6 = riverPatternGeneratorChain.getInts(par1, par2, par3, par4);
		int[] var7 = IntCache.getIntCache(par3 * par4);
		for(int var8 = 0; var8 < par3 * par4; ++var8)
		{
			if(var5[var8] == BiomeGenBase.ocean.biomeID)
			{
				var7[var8] = var5[var8];
			} else if(var6[var8] >= 0)
			{
				if(var5[var8] == BiomeGenBase.icePlains.biomeID)
				{
					var7[var8] = BiomeGenBase.frozenRiver.biomeID;
				} else if(var5[var8] != BiomeGenBase.mushroomIsland.biomeID && var5[var8] != BiomeGenBase.mushroomIslandShore.biomeID)
				{
					var7[var8] = var6[var8];
				} else
				{
					var7[var8] = BiomeGenBase.mushroomIslandShore.biomeID;
				}
			} else
			{
				var7[var8] = var5[var8];
			}
		}
		return var7;
	}
	
	@Override public void initWorldGenSeed(long par1)
	{
		biomePatternGeneratorChain.initWorldGenSeed(par1);
		riverPatternGeneratorChain.initWorldGenSeed(par1);
		super.initWorldGenSeed(par1);
	}
}
