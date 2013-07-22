package net.minecraft.src;

public class SpawnListEntry extends WeightedRandomItem
{
	public Class entityClass;
	public int minGroupCount;
	public int maxGroupCount;
	
	public SpawnListEntry(Class par1Class, int par2, int par3, int par4)
	{
		super(par2);
		entityClass = par1Class;
		minGroupCount = par3;
		maxGroupCount = par4;
	}
}
