package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdChestCorridor extends ComponentStronghold
{
	private static final WeightedRandomChestContent[] strongholdChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.enderPearl.itemID, 0, 1, 1, 10), new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3), new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5), new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5), new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.swordIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.plateIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.helmetIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.legsIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.bootsIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.appleGold.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Item.saddle.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Item.field_111215_ce.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Item.field_111216_cf.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Item.field_111213_cg.itemID, 0, 1, 1, 1) };
	private final EnumDoor doorType;
	private boolean hasMadeChest;
	
	public ComponentStrongholdChestCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
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
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(par1World, par2Random, par3StructureBoundingBox, doorType, 1, 1, 0);
			placeDoor(par1World, par2Random, par3StructureBoundingBox, EnumDoor.OPENING, 1, 1, 6);
			fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 1, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
			placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 1, 1, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 1, 5, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 2, 2, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 3, 2, 4, par3StructureBoundingBox);
			int var4;
			for(var4 = 2; var4 <= 4; ++var4)
			{
				placeBlockAtCurrentPosition(par1World, Block.stoneSingleSlab.blockID, 5, 2, 1, var4, par3StructureBoundingBox);
			}
			if(!hasMadeChest)
			{
				var4 = getYWithOffset(2);
				int var5 = getXWithOffset(3, 3);
				int var6 = getZWithOffset(3, 3);
				if(par3StructureBoundingBox.isVecInside(var5, var4, var6))
				{
					hasMadeChest = true;
					generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, WeightedRandomChestContent.func_92080_a(strongholdChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(par2Random) }), 2 + par2Random.nextInt(2));
				}
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
	{
		getNextComponentNormal((ComponentStrongholdStairs2) par1StructureComponent, par2List, par3Random, 1, 1);
	}
	
	public static ComponentStrongholdChestCorridor findValidPlacement(List par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null ? new ComponentStrongholdChestCorridor(par6, par1Random, var7, par5) : null;
	}
}
