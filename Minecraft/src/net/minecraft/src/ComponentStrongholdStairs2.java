package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class ComponentStrongholdStairs2 extends ComponentStrongholdStairs
{
	public StructureStrongholdPieceWeight strongholdPieceWeight;
	public ComponentStrongholdPortalRoom strongholdPortalRoom;
	public ArrayList field_75026_c = new ArrayList();
	
	public ComponentStrongholdStairs2(int p_i3852_1_, Random p_i3852_2_, int p_i3852_3_, int p_i3852_4_)
	{
		super(0, p_i3852_2_, p_i3852_3_, p_i3852_4_);
	}
	
	@Override public ChunkPosition getCenter()
	{
		return strongholdPortalRoom != null ? strongholdPortalRoom.getCenter() : super.getCenter();
	}
}
