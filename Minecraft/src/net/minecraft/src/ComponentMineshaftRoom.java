package net.minecraft.src;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ComponentMineshaftRoom extends StructureComponent
{
	private List roomsLinkedToTheRoom = new LinkedList();
	
	public ComponentMineshaftRoom(int par1, Random par2Random, int par3, int par4)
	{
		super(par1);
		boundingBox = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		if(isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) return false;
		else
		{
			fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, Block.dirt.blockID, 0, true);
			fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ, boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ, 0, 0, false);
			Iterator var4 = roomsLinkedToTheRoom.iterator();
			while(var4.hasNext())
			{
				StructureBoundingBox var5 = (StructureBoundingBox) var4.next();
				fillWithBlocks(par1World, par3StructureBoundingBox, var5.minX, var5.maxY - 2, var5.minZ, var5.maxX, var5.maxY, var5.maxZ, 0, 0, false);
			}
			randomlyRareFillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, 0, false);
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		int var4 = getComponentType();
		int var6 = boundingBox.getYSize() - 3 - 1;
		if(var6 <= 0)
		{
			var6 = 1;
		}
		int var5;
		StructureComponent var7;
		StructureBoundingBox var8;
		for(var5 = 0; var5 < boundingBox.getXSize(); var5 += 4)
		{
			var5 += par3Random.nextInt(boundingBox.getXSize());
			if(var5 + 3 > boundingBox.getXSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + var5, boundingBox.minY + par3Random.nextInt(var6) + 1, boundingBox.minZ - 1, 2, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, boundingBox.minZ, var8.maxX, var8.maxY, boundingBox.minZ + 1));
			}
		}
		for(var5 = 0; var5 < boundingBox.getXSize(); var5 += 4)
		{
			var5 += par3Random.nextInt(boundingBox.getXSize());
			if(var5 + 3 > boundingBox.getXSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + var5, boundingBox.minY + par3Random.nextInt(var6) + 1, boundingBox.maxZ + 1, 0, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(var8.minX, var8.minY, boundingBox.maxZ - 1, var8.maxX, var8.maxY, boundingBox.maxZ));
			}
		}
		for(var5 = 0; var5 < boundingBox.getZSize(); var5 += 4)
		{
			var5 += par3Random.nextInt(boundingBox.getZSize());
			if(var5 + 3 > boundingBox.getZSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par3Random.nextInt(var6) + 1, boundingBox.minZ + var5, 1, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, var8.minY, var8.minZ, boundingBox.minX + 1, var8.maxY, var8.maxZ));
			}
		}
		for(var5 = 0; var5 < boundingBox.getZSize(); var5 += 4)
		{
			var5 += par3Random.nextInt(boundingBox.getZSize());
			if(var5 + 3 > boundingBox.getZSize())
			{
				break;
			}
			var7 = StructureMineshaftPieces.getNextComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par3Random.nextInt(var6) + 1, boundingBox.minZ + var5, 3, var4);
			if(var7 != null)
			{
				var8 = var7.getBoundingBox();
				roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.maxX - 1, var8.minY, var8.minZ, boundingBox.maxX, var8.maxY, var8.maxZ));
			}
		}
	}
}
