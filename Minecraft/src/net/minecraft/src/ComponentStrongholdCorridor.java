package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdCorridor extends ComponentStronghold
{
	private final int field_74993_a;
	
	public ComponentStrongholdCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
	{
		super(par1);
		coordBaseMode = par4;
		boundingBox = par3StructureBoundingBox;
		field_74993_a = par4 != 2 && par4 != 0 ? par3StructureBoundingBox.getXSize() : par3StructureBoundingBox.getZSize();
	}
	
	@Override public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
	{
		if(isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) return false;
		else
		{
			for(int var4 = 0; var4 < field_74993_a; ++var4)
			{
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, 0, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 0, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 0, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 0, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, 0, var4, par3StructureBoundingBox);
				for(int var5 = 1; var5 <= 3; ++var5)
				{
					placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, var5, var4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, 0, 0, 1, var5, var4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, 0, 0, 2, var5, var4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, 0, 0, 3, var5, var4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, var5, var4, par3StructureBoundingBox);
				}
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 0, 4, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 4, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 4, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 4, var4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 4, 4, var4, par3StructureBoundingBox);
			}
			return true;
		}
	}
	
	public static StructureBoundingBox func_74992_a(List par0List, Random par1Random, int par2, int par3, int par4, int par5)
	{
		boolean var6 = true;
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
		StructureComponent var8 = StructureComponent.findIntersecting(par0List, var7);
		if(var8 == null) return null;
		else
		{
			if(var8.getBoundingBox().minY == var7.minY)
			{
				for(int var9 = 3; var9 >= 1; --var9)
				{
					var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9 - 1, par5);
					if(!var8.getBoundingBox().intersectsWith(var7)) return StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, var9, par5);
				}
			}
			return null;
		}
	}
}
