package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdChestCorridor extends ComponentStronghold
{
	private static final WeightedRandomChestContent[] strongholdChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.enderPearl.itemID, 0, 1, 1, 10), new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 3, 3), new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5), new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5), new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.swordIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.plateIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.helmetIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.legsIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.bootsIron.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.appleGold.itemID, 0, 1, 1, 1) };
	private final EnumDoor doorType;
	private boolean hasMadeChest;
	
	public ComponentStrongholdChestCorridor(int p_i3840_1_, Random p_i3840_2_, StructureBoundingBox p_i3840_3_, int p_i3840_4_)
	{
		super(p_i3840_1_);
		coordBaseMode = p_i3840_4_;
		doorType = getRandomDoor(p_i3840_2_);
		boundingBox = p_i3840_3_;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 1, 1, 0);
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, EnumDoor.OPENING, 1, 1, 6);
			fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, 2, 3, 1, 4, Block.stoneBrick.blockID, Block.stoneBrick.blockID, false);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 5, 3, 1, 1, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 5, 3, 1, 5, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 5, 3, 2, 2, p_74875_3_);
			placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 5, 3, 2, 4, p_74875_3_);
			int var4;
			for(var4 = 2; var4 <= 4; ++var4)
			{
				placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 5, 2, 1, var4, p_74875_3_);
			}
			if(!hasMadeChest)
			{
				var4 = getYWithOffset(2);
				int var5 = getXWithOffset(3, 3);
				int var6 = getZWithOffset(3, 3);
				if(p_74875_3_.isVecInside(var5, var4, var6))
				{
					hasMadeChest = true;
					generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, 2, 3, WeightedRandomChestContent.func_92080_a(strongholdChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 2 + p_74875_2_.nextInt(2));
				}
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
	}
	
	public static ComponentStrongholdChestCorridor findValidPlacement(List p_75000_0_, Random p_75000_1_, int p_75000_2_, int p_75000_3_, int p_75000_4_, int p_75000_5_, int p_75000_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75000_2_, p_75000_3_, p_75000_4_, -1, -1, 0, 5, 5, 7, p_75000_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75000_0_, var7) == null ? new ComponentStrongholdChestCorridor(p_75000_6_, p_75000_1_, var7, p_75000_5_) : null;
	}
}
