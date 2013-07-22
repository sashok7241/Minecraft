package net.minecraft.src;

public class BiomeGenMushroomIsland extends BiomeGenBase
{
	public BiomeGenMushroomIsland(int p_i3760_1_)
	{
		super(p_i3760_1_);
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
