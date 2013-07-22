package net.minecraft.src;

class StructureStrongholdPieceWeight
{
	public Class pieceClass;
	public final int pieceWeight;
	public int instancesSpawned;
	public int instancesLimit;
	
	public StructureStrongholdPieceWeight(Class par1Class, int par2, int par3)
	{
		pieceClass = par1Class;
		pieceWeight = par2;
		instancesLimit = par3;
	}
	
	public boolean canSpawnMoreStructures()
	{
		return instancesLimit == 0 || instancesSpawned < instancesLimit;
	}
	
	public boolean canSpawnMoreStructuresOfType(int par1)
	{
		return instancesLimit == 0 || instancesSpawned < instancesLimit;
	}
}
