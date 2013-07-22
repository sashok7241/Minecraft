package net.minecraft.src;

public class BiomeGenEnd extends BiomeGenBase
{
	public BiomeGenEnd(int p_i3766_1_)
	{
		super(p_i3766_1_);
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 10, 4, 4));
		topBlock = (byte) Block.dirt.blockID;
		fillerBlock = (byte) Block.dirt.blockID;
		theBiomeDecorator = new BiomeEndDecorator(this);
	}
	
	@Override public int getSkyColorByTemp(float par1)
	{
		return 0;
	}
}
