package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentMineshaftCross extends StructureComponent
{
	private final int corridorDirection;
	private final boolean isMultipleFloors;
	
	public ComponentMineshaftCross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
	{
		super(par1);
		corridorDirection = par4;
		boundingBox = par3StructureBoundingBox;
		isMultipleFloors = par3StructureBoundingBox.getYSize() > 3;
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		if(isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) return false;
		else
		{
			if(isMultipleFloors)
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.minY + 3 - 1, boundingBox.maxZ, 0, 0, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.minY + 3 - 1, boundingBox.maxZ - 1, 0, 0, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, 0, 0, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, 0, 0, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1, 0, 0, false);
			} else
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, 0, 0, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, 0, 0, false);
			}
			fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, Block.planks.blockID, 0, false);
			fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, Block.planks.blockID, 0, false);
			fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, Block.planks.blockID, 0, false);
			fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, Block.planks.blockID, 0, false);
			for(int var4 = boundingBox.minX; var4 <= boundingBox.maxX; ++var4)
			{
				for(int var5 = boundingBox.minZ; var5 <= boundingBox.maxZ; ++var5)
				{
					int var6 = getBlockIdAtCurrentPosition(par1World, var4, boundingBox.minY - 1, var5, par3StructureBoundingBox);
					if(var6 == 0)
					{
						placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, var4, boundingBox.minY - 1, var5, par3StructureBoundingBox);
					}
				}
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		int var4 = getComponentType();
		switch(corridorDirection)
		{
			case 0:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, var4);
				break;
			case 1:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, var4);
				break;
			case 2:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, var4);
				break;
			case 3:
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, var4);
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, var4);
		}
		if(isMultipleFloors)
		{
			if(par3Random.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ - 1, 2, var4);
			}
			if(par3Random.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 1, var4);
			}
			if(par3Random.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 3, var4);
			}
			if(par3Random.nextBoolean())
			{
				StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, 0, var4);
			}
		}
	}
	
	public static StructureBoundingBox findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
	{
		StructureBoundingBox var6 = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
		if(par1Random.nextInt(4) == 0)
		{
			var6.maxY += 4;
		}
		switch(par5)
		{
			case 0:
				var6.minX = par2 - 1;
				var6.maxX = par2 + 3;
				var6.maxZ = par4 + 4;
				break;
			case 1:
				var6.minX = par2 - 4;
				var6.minZ = par4 - 1;
				var6.maxZ = par4 + 3;
				break;
			case 2:
				var6.minX = par2 - 1;
				var6.maxX = par2 + 3;
				var6.minZ = par4 - 4;
				break;
			case 3:
				var6.maxX = par2 + 4;
				var6.minZ = par4 - 1;
				var6.maxZ = par4 + 3;
		}
		return StructureComponent.findIntersecting(par0List, var6) != null ? null : var6;
	}
}
