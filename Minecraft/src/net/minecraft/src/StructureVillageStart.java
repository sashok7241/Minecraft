package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class StructureVillageStart extends StructureStart
{
	private boolean hasMoreThanTwoComponents = false;
	
	public StructureVillageStart(World p_i3858_1_, Random p_i3858_2_, int p_i3858_3_, int p_i3858_4_, int p_i3858_5_)
	{
		ArrayList var6 = StructureVillagePieces.getStructureVillageWeightedPieceList(p_i3858_2_, p_i3858_5_);
		ComponentVillageStartPiece var7 = new ComponentVillageStartPiece(p_i3858_1_.getWorldChunkManager(), 0, p_i3858_2_, (p_i3858_3_ << 4) + 2, (p_i3858_4_ << 4) + 2, var6, p_i3858_5_);
		components.add(var7);
		var7.buildComponent(var7, components, p_i3858_2_);
		ArrayList var8 = var7.field_74930_j;
		ArrayList var9 = var7.field_74932_i;
		int var10;
		while(!var8.isEmpty() || !var9.isEmpty())
		{
			StructureComponent var11;
			if(var8.isEmpty())
			{
				var10 = p_i3858_2_.nextInt(var9.size());
				var11 = (StructureComponent) var9.remove(var10);
				var11.buildComponent(var7, components, p_i3858_2_);
			} else
			{
				var10 = p_i3858_2_.nextInt(var8.size());
				var11 = (StructureComponent) var8.remove(var10);
				var11.buildComponent(var7, components, p_i3858_2_);
			}
		}
		updateBoundingBox();
		var10 = 0;
		Iterator var13 = components.iterator();
		while(var13.hasNext())
		{
			StructureComponent var12 = (StructureComponent) var13.next();
			if(!(var12 instanceof ComponentVillageRoadPiece))
			{
				++var10;
			}
		}
		hasMoreThanTwoComponents = var10 > 2;
	}
	
	@Override public boolean isSizeableStructure()
	{
		return hasMoreThanTwoComponents;
	}
}
