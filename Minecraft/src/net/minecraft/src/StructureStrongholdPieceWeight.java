package net.minecraft.src;

class StructureStrongholdPieceWeight
{
	public Class pieceClass;
	public final int pieceWeight;
	public int instancesSpawned;
	public int instancesLimit;
	
	public StructureStrongholdPieceWeight(Class p_i3845_1_, int p_i3845_2_, int p_i3845_3_)
	{
		pieceClass = p_i3845_1_;
		pieceWeight = p_i3845_2_;
		instancesLimit = p_i3845_3_;
	}
	
	public boolean canSpawnMoreStructures()
	{
		return instancesLimit == 0 || instancesSpawned < instancesLimit;
	}
	
	public boolean canSpawnMoreStructuresOfType(int p_75189_1_)
	{
		return instancesLimit == 0 || instancesSpawned < instancesLimit;
	}
}
