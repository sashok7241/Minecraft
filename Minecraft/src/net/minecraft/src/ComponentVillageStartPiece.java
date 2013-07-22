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
	
	public ComponentVillageStartPiece(WorldChunkManager p_i3870_1_, int p_i3870_2_, Random p_i3870_3_, int p_i3870_4_, int p_i3870_5_, ArrayList p_i3870_6_, int p_i3870_7_)
	{
		super((ComponentVillageStartPiece) null, 0, p_i3870_3_, p_i3870_4_, p_i3870_5_);
		worldChunkMngr = p_i3870_1_;
		structureVillageWeightedPieceList = p_i3870_6_;
		terrainType = p_i3870_7_;
		BiomeGenBase var8 = p_i3870_1_.getBiomeGenAt(p_i3870_4_, p_i3870_5_);
		inDesert = var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills;
		startPiece = this;
	}
	
	public WorldChunkManager getWorldChunkManager()
	{
		return worldChunkMngr;
	}
}
