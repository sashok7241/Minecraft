package net.minecraft.src;

public class StructureVillagePieceWeight
{
	public Class villagePieceClass;
	public final int villagePieceWeight;
	public int villagePiecesSpawned;
	public int villagePiecesLimit;
	
	public StructureVillagePieceWeight(Class p_i3864_1_, int p_i3864_2_, int p_i3864_3_)
	{
		villagePieceClass = p_i3864_1_;
		villagePieceWeight = p_i3864_2_;
		villagePiecesLimit = p_i3864_3_;
	}
	
	public boolean canSpawnMoreVillagePieces()
	{
		return villagePiecesLimit == 0 || villagePiecesSpawned < villagePiecesLimit;
	}
	
	public boolean canSpawnMoreVillagePiecesOfType(int p_75085_1_)
	{
		return villagePiecesLimit == 0 || villagePiecesSpawned < villagePiecesLimit;
	}
}
