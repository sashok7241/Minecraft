package net.minecraft.src;

import java.util.Random;

public class BiomeGenHills extends BiomeGenBase
{
	private WorldGenerator theWorldGenerator;
	
	protected BiomeGenHills(int p_i3754_1_)
	{
		super(p_i3754_1_);
		theWorldGenerator = new WorldGenMinable(Block.silverfish.blockID, 8);
	}
	
	@Override public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_)
	{
		super.decorate(p_76728_1_, p_76728_2_, p_76728_3_, p_76728_4_);
		int var5 = 3 + p_76728_2_.nextInt(6);
		int var6;
		int var7;
		int var8;
		for(var6 = 0; var6 < var5; ++var6)
		{
			var7 = p_76728_3_ + p_76728_2_.nextInt(16);
			var8 = p_76728_2_.nextInt(28) + 4;
			int var9 = p_76728_4_ + p_76728_2_.nextInt(16);
			int var10 = p_76728_1_.getBlockId(var7, var8, var9);
			if(var10 == Block.stone.blockID)
			{
				p_76728_1_.setBlock(var7, var8, var9, Block.oreEmerald.blockID, 0, 2);
			}
		}
		for(var5 = 0; var5 < 7; ++var5)
		{
			var6 = p_76728_3_ + p_76728_2_.nextInt(16);
			var7 = p_76728_2_.nextInt(64);
			var8 = p_76728_4_ + p_76728_2_.nextInt(16);
			theWorldGenerator.generate(p_76728_1_, p_76728_2_, var6, var7, var8);
		}
	}
}
