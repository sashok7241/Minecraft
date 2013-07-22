package net.minecraft.src;

public class SpawnListEntry extends WeightedRandomItem
{
	public Class entityClass;
	public int minGroupCount;
	public int maxGroupCount;
	
	public SpawnListEntry(Class p_i3746_1_, int p_i3746_2_, int p_i3746_3_, int p_i3746_4_)
	{
		super(p_i3746_2_);
		entityClass = p_i3746_1_;
		minGroupCount = p_i3746_3_;
		maxGroupCount = p_i3746_4_;
	}
}
