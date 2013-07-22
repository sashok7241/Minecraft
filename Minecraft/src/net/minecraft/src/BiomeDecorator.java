package net.minecraft.src;

import java.util.Random;

public class BiomeDecorator
{
	protected World currentWorld;
	protected Random randomGenerator;
	protected int chunk_X;
	protected int chunk_Z;
	protected BiomeGenBase biome;
	protected WorldGenerator clayGen = new WorldGenClay(4);
	protected WorldGenerator sandGen;
	protected WorldGenerator gravelAsSandGen;
	protected WorldGenerator dirtGen;
	protected WorldGenerator gravelGen;
	protected WorldGenerator coalGen;
	protected WorldGenerator ironGen;
	protected WorldGenerator goldGen;
	protected WorldGenerator redstoneGen;
	protected WorldGenerator diamondGen;
	protected WorldGenerator lapisGen;
	protected WorldGenerator plantYellowGen;
	protected WorldGenerator plantRedGen;
	protected WorldGenerator mushroomBrownGen;
	protected WorldGenerator mushroomRedGen;
	protected WorldGenerator bigMushroomGen;
	protected WorldGenerator reedGen;
	protected WorldGenerator cactusGen;
	protected WorldGenerator waterlilyGen;
	protected int waterlilyPerChunk;
	protected int treesPerChunk;
	protected int flowersPerChunk;
	protected int grassPerChunk;
	protected int deadBushPerChunk;
	protected int mushroomsPerChunk;
	protected int reedsPerChunk;
	protected int cactiPerChunk;
	protected int sandPerChunk;
	protected int sandPerChunk2;
	protected int clayPerChunk;
	protected int bigMushroomsPerChunk;
	public boolean generateLakes;
	
	public BiomeDecorator(BiomeGenBase p_i3750_1_)
	{
		sandGen = new WorldGenSand(7, Block.sand.blockID);
		gravelAsSandGen = new WorldGenSand(6, Block.gravel.blockID);
		dirtGen = new WorldGenMinable(Block.dirt.blockID, 32);
		gravelGen = new WorldGenMinable(Block.gravel.blockID, 32);
		coalGen = new WorldGenMinable(Block.oreCoal.blockID, 16);
		ironGen = new WorldGenMinable(Block.oreIron.blockID, 8);
		goldGen = new WorldGenMinable(Block.oreGold.blockID, 8);
		redstoneGen = new WorldGenMinable(Block.oreRedstone.blockID, 7);
		diamondGen = new WorldGenMinable(Block.oreDiamond.blockID, 7);
		lapisGen = new WorldGenMinable(Block.oreLapis.blockID, 6);
		plantYellowGen = new WorldGenFlowers(Block.plantYellow.blockID);
		plantRedGen = new WorldGenFlowers(Block.plantRed.blockID);
		mushroomBrownGen = new WorldGenFlowers(Block.mushroomBrown.blockID);
		mushroomRedGen = new WorldGenFlowers(Block.mushroomRed.blockID);
		bigMushroomGen = new WorldGenBigMushroom();
		reedGen = new WorldGenReed();
		cactusGen = new WorldGenCactus();
		waterlilyGen = new WorldGenWaterlily();
		waterlilyPerChunk = 0;
		treesPerChunk = 0;
		flowersPerChunk = 2;
		grassPerChunk = 1;
		deadBushPerChunk = 0;
		mushroomsPerChunk = 0;
		reedsPerChunk = 0;
		cactiPerChunk = 0;
		sandPerChunk = 1;
		sandPerChunk2 = 3;
		clayPerChunk = 1;
		bigMushroomsPerChunk = 0;
		generateLakes = true;
		biome = p_i3750_1_;
	}
	
	protected void decorate()
	{
		generateOres();
		int var1;
		int var2;
		int var3;
		for(var1 = 0; var1 < sandPerChunk2; ++var1)
		{
			var2 = chunk_X + randomGenerator.nextInt(16) + 8;
			var3 = chunk_Z + randomGenerator.nextInt(16) + 8;
			sandGen.generate(currentWorld, randomGenerator, var2, currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		}
		for(var1 = 0; var1 < clayPerChunk; ++var1)
		{
			var2 = chunk_X + randomGenerator.nextInt(16) + 8;
			var3 = chunk_Z + randomGenerator.nextInt(16) + 8;
			clayGen.generate(currentWorld, randomGenerator, var2, currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		}
		for(var1 = 0; var1 < sandPerChunk; ++var1)
		{
			var2 = chunk_X + randomGenerator.nextInt(16) + 8;
			var3 = chunk_Z + randomGenerator.nextInt(16) + 8;
			sandGen.generate(currentWorld, randomGenerator, var2, currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
		}
		var1 = treesPerChunk;
		if(randomGenerator.nextInt(10) == 0)
		{
			++var1;
		}
		int var4;
		for(var2 = 0; var2 < var1; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			WorldGenerator var5 = biome.getRandomWorldGenForTrees(randomGenerator);
			var5.setScale(1.0D, 1.0D, 1.0D);
			var5.generate(currentWorld, randomGenerator, var3, currentWorld.getHeightValue(var3, var4), var4);
		}
		for(var2 = 0; var2 < bigMushroomsPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			bigMushroomGen.generate(currentWorld, randomGenerator, var3, currentWorld.getHeightValue(var3, var4), var4);
		}
		int var7;
		for(var2 = 0; var2 < flowersPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(128);
			var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
			plantYellowGen.generate(currentWorld, randomGenerator, var3, var4, var7);
			if(randomGenerator.nextInt(4) == 0)
			{
				var3 = chunk_X + randomGenerator.nextInt(16) + 8;
				var4 = randomGenerator.nextInt(128);
				var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
				plantRedGen.generate(currentWorld, randomGenerator, var3, var4, var7);
			}
		}
		for(var2 = 0; var2 < grassPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(128);
			var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
			WorldGenerator var6 = biome.getRandomWorldGenForGrass(randomGenerator);
			var6.generate(currentWorld, randomGenerator, var3, var4, var7);
		}
		for(var2 = 0; var2 < deadBushPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(128);
			var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
			new WorldGenDeadBush(Block.deadBush.blockID).generate(currentWorld, randomGenerator, var3, var4, var7);
		}
		for(var2 = 0; var2 < waterlilyPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			for(var7 = randomGenerator.nextInt(128); var7 > 0 && currentWorld.getBlockId(var3, var7 - 1, var4) == 0; --var7)
			{
				;
			}
			waterlilyGen.generate(currentWorld, randomGenerator, var3, var7, var4);
		}
		for(var2 = 0; var2 < mushroomsPerChunk; ++var2)
		{
			if(randomGenerator.nextInt(4) == 0)
			{
				var3 = chunk_X + randomGenerator.nextInt(16) + 8;
				var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
				var7 = currentWorld.getHeightValue(var3, var4);
				mushroomBrownGen.generate(currentWorld, randomGenerator, var3, var7, var4);
			}
			if(randomGenerator.nextInt(8) == 0)
			{
				var3 = chunk_X + randomGenerator.nextInt(16) + 8;
				var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
				var7 = randomGenerator.nextInt(128);
				mushroomRedGen.generate(currentWorld, randomGenerator, var3, var7, var4);
			}
		}
		if(randomGenerator.nextInt(4) == 0)
		{
			var2 = chunk_X + randomGenerator.nextInt(16) + 8;
			var3 = randomGenerator.nextInt(128);
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			mushroomBrownGen.generate(currentWorld, randomGenerator, var2, var3, var4);
		}
		if(randomGenerator.nextInt(8) == 0)
		{
			var2 = chunk_X + randomGenerator.nextInt(16) + 8;
			var3 = randomGenerator.nextInt(128);
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			mushroomRedGen.generate(currentWorld, randomGenerator, var2, var3, var4);
		}
		for(var2 = 0; var2 < reedsPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			var7 = randomGenerator.nextInt(128);
			reedGen.generate(currentWorld, randomGenerator, var3, var7, var4);
		}
		for(var2 = 0; var2 < 10; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(128);
			var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
			reedGen.generate(currentWorld, randomGenerator, var3, var4, var7);
		}
		if(randomGenerator.nextInt(32) == 0)
		{
			var2 = chunk_X + randomGenerator.nextInt(16) + 8;
			var3 = randomGenerator.nextInt(128);
			var4 = chunk_Z + randomGenerator.nextInt(16) + 8;
			new WorldGenPumpkin().generate(currentWorld, randomGenerator, var2, var3, var4);
		}
		for(var2 = 0; var2 < cactiPerChunk; ++var2)
		{
			var3 = chunk_X + randomGenerator.nextInt(16) + 8;
			var4 = randomGenerator.nextInt(128);
			var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
			cactusGen.generate(currentWorld, randomGenerator, var3, var4, var7);
		}
		if(generateLakes)
		{
			for(var2 = 0; var2 < 50; ++var2)
			{
				var3 = chunk_X + randomGenerator.nextInt(16) + 8;
				var4 = randomGenerator.nextInt(randomGenerator.nextInt(120) + 8);
				var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
				new WorldGenLiquids(Block.waterMoving.blockID).generate(currentWorld, randomGenerator, var3, var4, var7);
			}
			for(var2 = 0; var2 < 20; ++var2)
			{
				var3 = chunk_X + randomGenerator.nextInt(16) + 8;
				var4 = randomGenerator.nextInt(randomGenerator.nextInt(randomGenerator.nextInt(112) + 8) + 8);
				var7 = chunk_Z + randomGenerator.nextInt(16) + 8;
				new WorldGenLiquids(Block.lavaMoving.blockID).generate(currentWorld, randomGenerator, var3, var4, var7);
			}
		}
	}
	
	public void decorate(World p_76796_1_, Random p_76796_2_, int p_76796_3_, int p_76796_4_)
	{
		if(currentWorld != null) throw new RuntimeException("Already decorating!!");
		else
		{
			currentWorld = p_76796_1_;
			randomGenerator = p_76796_2_;
			chunk_X = p_76796_3_;
			chunk_Z = p_76796_4_;
			this.decorate();
			currentWorld = null;
			randomGenerator = null;
		}
	}
	
	protected void generateOres()
	{
		genStandardOre1(20, dirtGen, 0, 128);
		genStandardOre1(10, gravelGen, 0, 128);
		genStandardOre1(20, coalGen, 0, 128);
		genStandardOre1(20, ironGen, 0, 64);
		genStandardOre1(2, goldGen, 0, 32);
		genStandardOre1(8, redstoneGen, 0, 16);
		genStandardOre1(1, diamondGen, 0, 16);
		genStandardOre2(1, lapisGen, 16, 16);
	}
	
	protected void genStandardOre1(int p_76795_1_, WorldGenerator p_76795_2_, int p_76795_3_, int p_76795_4_)
	{
		for(int var5 = 0; var5 < p_76795_1_; ++var5)
		{
			int var6 = chunk_X + randomGenerator.nextInt(16);
			int var7 = randomGenerator.nextInt(p_76795_4_ - p_76795_3_) + p_76795_3_;
			int var8 = chunk_Z + randomGenerator.nextInt(16);
			p_76795_2_.generate(currentWorld, randomGenerator, var6, var7, var8);
		}
	}
	
	protected void genStandardOre2(int p_76793_1_, WorldGenerator p_76793_2_, int p_76793_3_, int p_76793_4_)
	{
		for(int var5 = 0; var5 < p_76793_1_; ++var5)
		{
			int var6 = chunk_X + randomGenerator.nextInt(16);
			int var7 = randomGenerator.nextInt(p_76793_4_) + randomGenerator.nextInt(p_76793_4_) + p_76793_3_ - p_76793_4_;
			int var8 = chunk_Z + randomGenerator.nextInt(16);
			p_76793_2_.generate(currentWorld, randomGenerator, var6, var7, var8);
		}
	}
}
