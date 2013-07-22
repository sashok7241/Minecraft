package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentMineshaftStairs extends StructureComponent
{
	public ComponentMineshaftStairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
	{
		super(par1);
		coordBaseMode = par4;
		boundingBox = par3StructureBoundingBox;
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		if(isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) return false;
		else
		{
			fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 2, 7, 1, 0, 0, false);
			fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 7, 2, 2, 8, 0, 0, false);
			for(int var4 = 0; var4 < 5; ++var4)
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, 0, 0, false);
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		int var4 = getComponentType();
		switch(coordBaseMode)
		{
			case 0:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				break;
			case 1:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, var4);
				break;
			case 2:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				break;
			case 3:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, var4);
		}
	}
	
	public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
	{
		StructureBoundingBox var6 = new StructureBoundingBox(par2, par3 - 5, par4, par2, par3 + 2, par4);
		switch(par5)
		{
			case 0:
				var6.maxX = par2 + 2;
				var6.maxZ = par4 + 8;
				break;
			case 1:
				var6.minX = par2 - 8;
				var6.maxZ = par4 + 2;
				break;
			case 2:
				var6.maxX = par2 + 2;
				var6.minZ = par4 - 8;
				break;
			case 3:
				var6.maxX = par2 + 8;
				var6.maxZ = par4 + 2;
		}
		return StructureComponent.findIntersecting(par0List, var6) != null ? null : var6;
	}
}
