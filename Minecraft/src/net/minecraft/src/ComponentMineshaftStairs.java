package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentMineshaftStairs extends StructureComponent
{
	public ComponentMineshaftStairs(int p_i3810_1_, Random p_i3810_2_, StructureBoundingBox p_i3810_3_, int p_i3810_4_)
	{
		super(p_i3810_1_);
		coordBaseMode = p_i3810_4_;
		boundingBox = p_i3810_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5, 0, 2, 7, 1, 0, 0, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 7, 2, 2, 8, 0, 0, false);
			for(int var4 = 0; var4 < 5; ++var4)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, 0, 0, false);
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		int var4 = getComponentType();
		switch(coordBaseMode)
		{
			case 0:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				break;
			case 1:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, var4);
				break;
			case 2:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				break;
			case 3:
				StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, var4);
		}
	}
	
	public static StructureBoundingBox findValidPlacement(List p_74950_0_, Random p_74950_1_, int p_74950_2_, int p_74950_3_, int p_74950_4_, int p_74950_5_)
	{
		StructureBoundingBox var6 = new StructureBoundingBox(p_74950_2_, p_74950_3_ - 5, p_74950_4_, p_74950_2_, p_74950_3_ + 2, p_74950_4_);
		switch(p_74950_5_)
		{
			case 0:
				var6.maxX = p_74950_2_ + 2;
				var6.maxZ = p_74950_4_ + 8;
				break;
			case 1:
				var6.minX = p_74950_2_ - 8;
				var6.maxZ = p_74950_4_ + 2;
				break;
			case 2:
				var6.maxX = p_74950_2_ + 2;
				var6.minZ = p_74950_4_ - 8;
				break;
			case 3:
				var6.maxX = p_74950_2_ + 8;
				var6.maxZ = p_74950_4_ + 2;
		}
		return StructureComponent.findIntersecting(p_74950_0_, var6) != null ? null : var6;
	}
}
