package net.minecraft.src;

public class BiomeGenMushroomIsland extends BiomeGenBase
{
	public BiomeGenMushroomIsland(int par1)
	{
		super(par1);
		theBiomeDecorator.treesPerChunk = -100;
		theBiomeDecorator.flowersPerChunk = -100;
		theBiomeDecorator.grassPerChunk = -100;
		theBiomeDecorator.mushroomsPerChunk = 1;
		theBiomeDecorator.bigMushroomsPerChunk = 1;
		topBlock = (byte) Block.mycelium.blockID;
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
	}
}
