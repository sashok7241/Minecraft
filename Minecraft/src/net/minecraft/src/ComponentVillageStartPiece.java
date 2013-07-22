package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class ComponentVillageStartPiece extends ComponentVillageWell
{
	public final WorldChunkManager worldChunkMngr;
	public final boolean inDesert;
	public final int terrainType;
	public StructureVillagePieceWeight structVillagePieceWeight;
	public ArrayList structureVillageWeightedPieceList;
	public ArrayList field_74932_i = new ArrayList();
	public ArrayList field_74930_j = new ArrayList();
	
	public ComponentVillageStartPiece(WorldChunkManager par1WorldChunkManager, int par2, Random par3Random, int par4, int par5, ArrayList par6ArrayList, int par7)
	{
		super((ComponentVillageStartPiece) null, 0, par3Random, par4, par5);
		worldChunkMngr = par1WorldChunkManager;
		structureVillageWeightedPieceList = par6ArrayList;
		terrainType = par7;
		BiomeGenBase var8 = par1WorldChunkManager.getBiomeGenAt(par4, par5);
		inDesert = var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills;
		startPiece = this;
	}
	
	public WorldChunkManager getWorldChunkManager()
	{
		return worldChunkMngr;
	}
}
