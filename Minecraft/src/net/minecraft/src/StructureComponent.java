package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class StructureComponent
{
	protected StructureBoundingBox boundingBox;
	protected int coordBaseMode;
	protected int componentType;
	
	protected StructureComponent(int par1)
	{
		componentType = par1;
		coordBaseMode = -1;
	}
	
	public abstract boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3);
	
	public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
	}
	
	protected void clearCurrentPositionBlocksUpwards(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox)
	{
		int var6 = getXWithOffset(par2, par4);
		int var7 = getYWithOffset(par3);
		int var8 = getZWithOffset(par2, par4);
		if(par5StructureBoundingBox.isVecInside(var6, var7, var8))
		{
			while(!par1World.isAirBlock(var6, var7, var8) && var7 < 255)
			{
				par1World.setBlock(var6, var7, var8, 0, 0, 2);
				++var7;
			}
		}
	}
	
	protected void fillCurrentPositionBlocksDownwards(World par1World, int par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox)
	{
		int var8 = getXWithOffset(par4, par6);
		int var9 = getYWithOffset(par5);
		int var10 = getZWithOffset(par4, par6);
		if(par7StructureBoundingBox.isVecInside(var8, var9, var10))
		{
			while((par1World.isAirBlock(var8, var9, var10) || par1World.getBlockMaterial(var8, var9, var10).isLiquid()) && var9 > 1)
			{
				par1World.setBlock(var8, var9, var10, par2, par3, 2);
				--var9;
			}
		}
	}
	
	protected void fillWithAir(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8)
	{
		for(int var9 = par4; var9 <= par7; ++var9)
		{
			for(int var10 = par3; var10 <= par6; ++var10)
			{
				for(int var11 = par5; var11 <= par8; ++var11)
				{
					placeBlockAtCurrentPosition(par1World, 0, 0, var10, var9, var11, par2StructureBoundingBox);
				}
			}
		}
	}
	
	protected void fillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, int par10, boolean par11)
	{
		for(int var12 = par4; var12 <= par7; ++var12)
		{
			for(int var13 = par3; var13 <= par6; ++var13)
			{
				for(int var14 = par5; var14 <= par8; ++var14)
				{
					if(!par11 || getBlockIdAtCurrentPosition(par1World, var13, var12, var14, par2StructureBoundingBox) != 0)
					{
						if(var12 != par4 && var12 != par7 && var13 != par3 && var13 != par6 && var14 != par5 && var14 != par8)
						{
							placeBlockAtCurrentPosition(par1World, par10, 0, var13, var12, var14, par2StructureBoundingBox);
						} else
						{
							placeBlockAtCurrentPosition(par1World, par9, 0, var13, var12, var14, par2StructureBoundingBox);
						}
					}
				}
			}
		}
	}
	
	protected void fillWithMetadataBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, int par10, int par11, int par12, boolean par13)
	{
		for(int var14 = par4; var14 <= par7; ++var14)
		{
			for(int var15 = par3; var15 <= par6; ++var15)
			{
				for(int var16 = par5; var16 <= par8; ++var16)
				{
					if(!par13 || getBlockIdAtCurrentPosition(par1World, var15, var14, var16, par2StructureBoundingBox) != 0)
					{
						if(var14 != par4 && var14 != par7 && var15 != par3 && var15 != par6 && var16 != par5 && var16 != par8)
						{
							placeBlockAtCurrentPosition(par1World, par11, par12, var15, var14, var16, par2StructureBoundingBox);
						} else
						{
							placeBlockAtCurrentPosition(par1World, par9, par10, var15, var14, var16, par2StructureBoundingBox);
						}
					}
				}
			}
		}
	}
	
	protected void fillWithRandomizedBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, boolean par9, Random par10Random, StructurePieceBlockSelector par11StructurePieceBlockSelector)
	{
		for(int var12 = par4; var12 <= par7; ++var12)
		{
			for(int var13 = par3; var13 <= par6; ++var13)
			{
				for(int var14 = par5; var14 <= par8; ++var14)
				{
					if(!par9 || getBlockIdAtCurrentPosition(par1World, var13, var12, var14, par2StructureBoundingBox) != 0)
					{
						par11StructurePieceBlockSelector.selectBlocks(par10Random, var13, var12, var14, var12 == par4 || var12 == par7 || var13 == par3 || var13 == par6 || var14 == par5 || var14 == par8);
						placeBlockAtCurrentPosition(par1World, par11StructurePieceBlockSelector.getSelectedBlockId(), par11StructurePieceBlockSelector.getSelectedBlockMetaData(), var13, var12, var14, par2StructureBoundingBox);
					}
				}
			}
		}
	}
	
	protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8)
	{
		int var9 = getXWithOffset(par4, par6);
		int var10 = getYWithOffset(par5);
		int var11 = getZWithOffset(par4, par6);
		if(par2StructureBoundingBox.isVecInside(var9, var10, var11) && par1World.getBlockId(var9, var10, var11) != Block.chest.blockID)
		{
			par1World.setBlock(var9, var10, var11, Block.chest.blockID, 0, 2);
			TileEntityChest var12 = (TileEntityChest) par1World.getBlockTileEntity(var9, var10, var11);
			if(var12 != null)
			{
				WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, var12, par8);
			}
			return true;
		} else return false;
	}
	
	protected boolean generateStructureDispenserContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7, WeightedRandomChestContent[] par8ArrayOfWeightedRandomChestContent, int par9)
	{
		int var10 = getXWithOffset(par4, par6);
		int var11 = getYWithOffset(par5);
		int var12 = getZWithOffset(par4, par6);
		if(par2StructureBoundingBox.isVecInside(var10, var11, var12) && par1World.getBlockId(var10, var11, var12) != Block.dispenser.blockID)
		{
			par1World.setBlock(var10, var11, var12, Block.dispenser.blockID, getMetadataWithOffset(Block.dispenser.blockID, par7), 2);
			TileEntityDispenser var13 = (TileEntityDispenser) par1World.getBlockTileEntity(var10, var11, var12);
			if(var13 != null)
			{
				WeightedRandomChestContent.generateDispenserContents(par3Random, par8ArrayOfWeightedRandomChestContent, var13, par9);
			}
			return true;
		} else return false;
	}
	
	protected int getBlockIdAtCurrentPosition(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox)
	{
		int var6 = getXWithOffset(par2, par4);
		int var7 = getYWithOffset(par3);
		int var8 = getZWithOffset(par2, par4);
		return !par5StructureBoundingBox.isVecInside(var6, var7, var8) ? 0 : par1World.getBlockId(var6, var7, var8);
	}
	
	public StructureBoundingBox getBoundingBox()
	{
		return boundingBox;
	}
	
	public ChunkPosition getCenter()
	{
		return new ChunkPosition(boundingBox.getCenterX(), boundingBox.getCenterY(), boundingBox.getCenterZ());
	}
	
	public int getComponentType()
	{
		return componentType;
	}
	
	protected int getMetadataWithOffset(int par1, int par2)
	{
		if(par1 == Block.rail.blockID)
		{
			if(coordBaseMode == 1 || coordBaseMode == 3)
			{
				if(par2 == 1) return 0;
				return 1;
			}
		} else if(par1 != Block.doorWood.blockID && par1 != Block.doorIron.blockID)
		{
			if(par1 != Block.stairsCobblestone.blockID && par1 != Block.stairsWoodOak.blockID && par1 != Block.stairsNetherBrick.blockID && par1 != Block.stairsStoneBrick.blockID && par1 != Block.stairsSandStone.blockID)
			{
				if(par1 == Block.ladder.blockID)
				{
					if(coordBaseMode == 0)
					{
						if(par2 == 2) return 3;
						if(par2 == 3) return 2;
					} else if(coordBaseMode == 1)
					{
						if(par2 == 2) return 4;
						if(par2 == 3) return 5;
						if(par2 == 4) return 2;
						if(par2 == 5) return 3;
					} else if(coordBaseMode == 3)
					{
						if(par2 == 2) return 5;
						if(par2 == 3) return 4;
						if(par2 == 4) return 2;
						if(par2 == 5) return 3;
					}
				} else if(par1 == Block.stoneButton.blockID)
				{
					if(coordBaseMode == 0)
					{
						if(par2 == 3) return 4;
						if(par2 == 4) return 3;
					} else if(coordBaseMode == 1)
					{
						if(par2 == 3) return 1;
						if(par2 == 4) return 2;
						if(par2 == 2) return 3;
						if(par2 == 1) return 4;
					} else if(coordBaseMode == 3)
					{
						if(par2 == 3) return 2;
						if(par2 == 4) return 1;
						if(par2 == 2) return 3;
						if(par2 == 1) return 4;
					}
				} else if(par1 != Block.tripWireSource.blockID && (Block.blocksList[par1] == null || !(Block.blocksList[par1] instanceof BlockDirectional)))
				{
					if(par1 == Block.pistonBase.blockID || par1 == Block.pistonStickyBase.blockID || par1 == Block.lever.blockID || par1 == Block.dispenser.blockID)
					{
						if(coordBaseMode == 0)
						{
							if(par2 == 2 || par2 == 3) return Facing.oppositeSide[par2];
						} else if(coordBaseMode == 1)
						{
							if(par2 == 2) return 4;
							if(par2 == 3) return 5;
							if(par2 == 4) return 2;
							if(par2 == 5) return 3;
						} else if(coordBaseMode == 3)
						{
							if(par2 == 2) return 5;
							if(par2 == 3) return 4;
							if(par2 == 4) return 2;
							if(par2 == 5) return 3;
						}
					}
				} else if(coordBaseMode == 0)
				{
					if(par2 == 0 || par2 == 2) return Direction.rotateOpposite[par2];
				} else if(coordBaseMode == 1)
				{
					if(par2 == 2) return 1;
					if(par2 == 0) return 3;
					if(par2 == 1) return 2;
					if(par2 == 3) return 0;
				} else if(coordBaseMode == 3)
				{
					if(par2 == 2) return 3;
					if(par2 == 0) return 1;
					if(par2 == 1) return 2;
					if(par2 == 3) return 0;
				}
			} else if(coordBaseMode == 0)
			{
				if(par2 == 2) return 3;
				if(par2 == 3) return 2;
			} else if(coordBaseMode == 1)
			{
				if(par2 == 0) return 2;
				if(par2 == 1) return 3;
				if(par2 == 2) return 0;
				if(par2 == 3) return 1;
			} else if(coordBaseMode == 3)
			{
				if(par2 == 0) return 2;
				if(par2 == 1) return 3;
				if(par2 == 2) return 1;
				if(par2 == 3) return 0;
			}
		} else if(coordBaseMode == 0)
		{
			if(par2 == 0) return 2;
			if(par2 == 2) return 0;
		} else
		{
			if(coordBaseMode == 1) return par2 + 1 & 3;
			if(coordBaseMode == 3) return par2 + 3 & 3;
		}
		return par2;
	}
	
	protected int getXWithOffset(int par1, int par2)
	{
		switch(coordBaseMode)
		{
			case 0:
			case 2:
				return boundingBox.minX + par1;
			case 1:
				return boundingBox.maxX - par2;
			case 3:
				return boundingBox.minX + par2;
			default:
				return par1;
		}
	}
	
	protected int getYWithOffset(int par1)
	{
		return coordBaseMode == -1 ? par1 : par1 + boundingBox.minY;
	}
	
	protected int getZWithOffset(int par1, int par2)
	{
		switch(coordBaseMode)
		{
			case 0:
				return boundingBox.minZ + par2;
			case 1:
			case 3:
				return boundingBox.minZ + par1;
			case 2:
				return boundingBox.maxZ - par2;
			default:
				return par2;
		}
	}
	
	protected boolean isLiquidInStructureBoundingBox(World par1World, StructureBoundingBox par2StructureBoundingBox)
	{
		int var3 = Math.max(boundingBox.minX - 1, par2StructureBoundingBox.minX);
		int var4 = Math.max(boundingBox.minY - 1, par2StructureBoundingBox.minY);
		int var5 = Math.max(boundingBox.minZ - 1, par2StructureBoundingBox.minZ);
		int var6 = Math.min(boundingBox.maxX + 1, par2StructureBoundingBox.maxX);
		int var7 = Math.min(boundingBox.maxY + 1, par2StructureBoundingBox.maxY);
		int var8 = Math.min(boundingBox.maxZ + 1, par2StructureBoundingBox.maxZ);
		int var9;
		int var10;
		int var11;
		for(var9 = var3; var9 <= var6; ++var9)
		{
			for(var10 = var5; var10 <= var8; ++var10)
			{
				var11 = par1World.getBlockId(var9, var4, var10);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
				var11 = par1World.getBlockId(var9, var7, var10);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
			}
		}
		for(var9 = var3; var9 <= var6; ++var9)
		{
			for(var10 = var4; var10 <= var7; ++var10)
			{
				var11 = par1World.getBlockId(var9, var10, var5);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
				var11 = par1World.getBlockId(var9, var10, var8);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
			}
		}
		for(var9 = var5; var9 <= var8; ++var9)
		{
			for(var10 = var4; var10 <= var7; ++var10)
			{
				var11 = par1World.getBlockId(var3, var10, var9);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
				var11 = par1World.getBlockId(var6, var10, var9);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
			}
		}
		return false;
	}
	
	protected void placeBlockAtCurrentPosition(World par1World, int par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox)
	{
		int var8 = getXWithOffset(par4, par6);
		int var9 = getYWithOffset(par5);
		int var10 = getZWithOffset(par4, par6);
		if(par7StructureBoundingBox.isVecInside(var8, var9, var10))
		{
			par1World.setBlock(var8, var9, var10, par2, par3, 2);
		}
	}
	
	protected void placeDoorAtCurrentPosition(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7)
	{
		int var8 = getXWithOffset(par4, par6);
		int var9 = getYWithOffset(par5);
		int var10 = getZWithOffset(par4, par6);
		if(par2StructureBoundingBox.isVecInside(var8, var9, var10))
		{
			ItemDoor.placeDoorBlock(par1World, var8, var9, var10, par7, Block.doorWood);
		}
	}
	
	protected void randomlyFillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, float par4, int par5, int par6, int par7, int par8, int par9, int par10, int par11, int par12, boolean par13)
	{
		for(int var14 = par6; var14 <= par9; ++var14)
		{
			for(int var15 = par5; var15 <= par8; ++var15)
			{
				for(int var16 = par7; var16 <= par10; ++var16)
				{
					if(par3Random.nextFloat() <= par4 && (!par13 || getBlockIdAtCurrentPosition(par1World, var15, var14, var16, par2StructureBoundingBox) != 0))
					{
						if(var14 != par6 && var14 != par9 && var15 != par5 && var15 != par8 && var16 != par7 && var16 != par10)
						{
							placeBlockAtCurrentPosition(par1World, par12, 0, var15, var14, var16, par2StructureBoundingBox);
						} else
						{
							placeBlockAtCurrentPosition(par1World, par11, 0, var15, var14, var16, par2StructureBoundingBox);
						}
					}
				}
			}
		}
	}
	
	protected void randomlyPlaceBlock(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, float par4, int par5, int par6, int par7, int par8, int par9)
	{
		if(par3Random.nextFloat() < par4)
		{
			placeBlockAtCurrentPosition(par1World, par8, par9, par5, par6, par7, par2StructureBoundingBox);
		}
	}
	
	protected void randomlyRareFillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, boolean par10)
	{
		float var11 = par6 - par3 + 1;
		float var12 = par7 - par4 + 1;
		float var13 = par8 - par5 + 1;
		float var14 = par3 + var11 / 2.0F;
		float var15 = par5 + var13 / 2.0F;
		for(int var16 = par4; var16 <= par7; ++var16)
		{
			float var17 = (var16 - par4) / var12;
			for(int var18 = par3; var18 <= par6; ++var18)
			{
				float var19 = (var18 - var14) / (var11 * 0.5F);
				for(int var20 = par5; var20 <= par8; ++var20)
				{
					float var21 = (var20 - var15) / (var13 * 0.5F);
					if(!par10 || getBlockIdAtCurrentPosition(par1World, var18, var16, var20, par2StructureBoundingBox) != 0)
					{
						float var22 = var19 * var19 + var17 * var17 + var21 * var21;
						if(var22 <= 1.05F)
						{
							placeBlockAtCurrentPosition(par1World, par9, 0, var18, var16, var20, par2StructureBoundingBox);
						}
					}
				}
			}
		}
	}
	
	public static StructureComponent findIntersecting(List par0List, StructureBoundingBox par1StructureBoundingBox)
	{
		Iterator var2 = par0List.iterator();
		StructureComponent var3;
		do
		{
			if(!var2.hasNext()) return null;
			var3 = (StructureComponent) var2.next();
		} while(var3.getBoundingBox() == null || !var3.getBoundingBox().intersectsWith(par1StructureBoundingBox));
		return var3;
	}
}
