package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

class StructureNetherBridgeStart extends StructureStart
{
	public StructureNetherBridgeStart(World p_i3812_1_, Random p_i3812_2_, int p_i3812_3_, int p_i3812_4_)
	{
		ComponentNetherBridgeStartPiece var5 = new ComponentNetherBridgeStartPiece(p_i3812_2_, (p_i3812_3_ << 4) + 2, (p_i3812_4_ << 4) + 2);
		components.add(var5);
		var5.buildComponent(var5, components, p_i3812_2_);
		ArrayList var6 = var5.field_74967_d;
		while(!var6.isEmpty())
		{
			int var7 = p_i3812_2_.nextInt(var6.size());
			StructureComponent var8 = (StructureComponent) var6.remove(var7);
			var8.buildComponent(var5, components, p_i3812_2_);
		}
		updateBoundingBox();
		setRandomHeight(p_i3812_1_, p_i3812_2_, 48, 70);
	}
}
