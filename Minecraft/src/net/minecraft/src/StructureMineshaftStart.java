package net.minecraft.src;

import java.util.Random;

public class StructureMineshaftStart extends StructureStart
{
	public StructureMineshaftStart(World p_i3811_1_, Random p_i3811_2_, int p_i3811_3_, int p_i3811_4_)
	{
		ComponentMineshaftRoom var5 = new ComponentMineshaftRoom(0, p_i3811_2_, (p_i3811_3_ << 4) + 2, (p_i3811_4_ << 4) + 2);
		components.add(var5);
		var5.buildComponent(var5, components, p_i3811_2_);
		updateBoundingBox();
		markAvailableHeight(p_i3811_1_, p_i3811_2_, 10);
	}
}
