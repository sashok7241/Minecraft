package net.minecraft.src;

public class GenLayerRiverMix extends GenLayer
{
	private GenLayer biomePatternGeneratorChain;
	private GenLayer riverPatternGeneratorChain;
	
	public GenLayerRiverMix(long p_i3895_1_, GenLayer p_i3895_3_, GenLayer p_i3895_4_)
	{
		super(p_i3895_1_);
		biomePatternGeneratorChain = p_i3895_3_;
		riverPatternGeneratorChain = p_i3895_4_;
	}
	
	@Override public int[] getInts(int p_75904_1_, int p_75904_2_, int p_75904_3_, int p_75904_4_)
	{
		int[] var5 = biomePatternGeneratorChain.getInts(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
		int[] var6 = riverPatternGeneratorChain.getInts(p_75904_1_, p_75904_2_, p_75904_3_, p_75904_4_);
		int[] var7 = IntCache.getIntCache(p_75904_3_ * p_75904_4_);
		for(int var8 = 0; var8 < p_75904_3_ * p_75904_4_; ++var8)
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
	
	@Override public void initWorldGenSeed(long p_75905_1_)
	{
		biomePatternGeneratorChain.initWorldGenSeed(p_75905_1_);
		riverPatternGeneratorChain.initWorldGenSeed(p_75905_1_);
		super.initWorldGenSeed(p_75905_1_);
	}
}
