package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdLeftTurn extends ComponentStronghold
{
	protected final EnumDoor doorType;
	
	public ComponentStrongholdLeftTurn(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
	{
		super(par1);
		coordBaseMode = par4;
		doorType = getRandomDoor(par2Random);
		boundingBox = par3StructureBoundingBox;
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		if(isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) return false;
		else
		{
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 1, 1, 0);
			if(coordBaseMode != 2 && coordBaseMode != 3)
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, 0, 0, false);
			} else
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, 0, 0, false);
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		if(coordBaseMode != 2 && coordBaseMode != 3)
		{
			getNextComponentZ((ComponentStrongholdStairs2) par1StructureComponent, par2List, par3Random, 1, 1);
		} else
		{
			getNextComponentX((ComponentStrongholdStairs2) par1StructureComponent, par2List, par3Random, 1, 1);
		}
	}
	
	public static ComponentStrongholdLeftTurn findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 5, par5);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new ComponentStrongholdLeftTurn(par6, par1Random, var7, par5) : null;
	}
}
