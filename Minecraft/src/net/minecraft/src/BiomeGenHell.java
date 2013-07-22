package net.minecraft.src;

public class BiomeGenHell extends BiomeGenBase
{
	public BiomeGenHell(int p_i3757_1_)
	{
		super(p_i3757_1_);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityGhast.class, 50, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 100, 4, 4));
		spawnableMonsterList.add(new SpawnListEntry(EntityMagmaCube.class, 1, 4, 4));
	}
}
