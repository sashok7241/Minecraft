package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

class StructureStrongholdStart extends StructureStart
{
	public StructureStrongholdStart(World p_i3837_1_, Random p_i3837_2_, int p_i3837_3_, int p_i3837_4_)
	{
		StructureStrongholdPieces.prepareStructurePieces();
		ComponentStrongholdStairs2 var5 = new ComponentStrongholdStairs2(0, p_i3837_2_, (p_i3837_3_ << 4) + 2, (p_i3837_4_ << 4) + 2);
		components.add(var5);
		var5.buildComponent(var5, components, p_i3837_2_);
		ArrayList var6 = var5.field_75026_c;
		while(!var6.isEmpty())
		{
			int var7 = p_i3837_2_.nextInt(var6.size());
			StructureComponent var8 = (StructureComponent) var6.remove(var7);
			var8.buildComponent(var5, components, p_i3837_2_);
		}
		updateBoundingBox();
		markAvailableHeight(p_i3837_1_, p_i3837_2_, 10);
	}
}
