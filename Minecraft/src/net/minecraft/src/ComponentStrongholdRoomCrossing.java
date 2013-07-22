package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdRoomCrossing extends ComponentStronghold
{
	private static final WeightedRandomChestContent[] strongholdRoomCrossingChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5), new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5), new WeightedRandomChestContent(Item.coal.itemID, 0, 3, 8, 10), new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 1) };
	protected final EnumDoor doorType;
	protected final int roomType;
	
	public ComponentStrongholdRoomCrossing(int p_i3848_1_, Random p_i3848_2_, StructureBoundingBox p_i3848_3_, int p_i3848_4_)
	{
		super(p_i3848_1_);
		coordBaseMode = p_i3848_4_;
		doorType = getRandomDoor(p_i3848_2_);
		boundingBox = p_i3848_3_;
		roomType = p_i3848_2_.nextInt(5);
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 10, 6, 10, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 4, 1, 0);
			fillWithBlocks(p_74875_1_, p_74875_3_, 4, 1, 10, 6, 3, 10, 0, 0, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 1, 4, 0, 3, 6, 0, 0, false);
			fillWithBlocks(p_74875_1_, p_74875_3_, 10, 1, 4, 10, 3, 6, 0, 0, false);
			int var4;
			switch(roomType)
			{
				case 0:
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 5, 1, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 5, 2, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 5, 3, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 4, 3, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 6, 3, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 5, 3, 4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 5, 3, 6, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 4, 1, 4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 4, 1, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 4, 1, 6, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 6, 1, 4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 6, 1, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 6, 1, 6, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 5, 1, 4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneSingleSlab.blockID, 0, 5, 1, 6, p_74875_3_);
					break;
				case 1:
					for(var4 = 0; var4 < 5; ++var4)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3, 1, 3 + var4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 7, 1, 3 + var4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3 + var4, 1, 3, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 3 + var4, 1, 7, p_74875_3_);
					}
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 5, 1, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 5, 2, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.stoneBrick.blockID, 0, 5, 3, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.waterMoving.blockID, 0, 5, 4, 5, p_74875_3_);
					break;
				case 2:
					for(var4 = 1; var4 <= 9; ++var4)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 1, 3, var4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 9, 3, var4, p_74875_3_);
					}
					for(var4 = 1; var4 <= 9; ++var4)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, var4, 3, 1, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, var4, 3, 9, p_74875_3_);
					}
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 5, 1, 4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 5, 1, 6, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 5, 3, 4, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 5, 3, 6, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 1, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 6, 1, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, 3, 5, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 6, 3, 5, p_74875_3_);
					for(var4 = 1; var4 <= 3; ++var4)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, var4, 4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 6, var4, 4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 4, var4, 6, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.cobblestone.blockID, 0, 6, var4, 6, p_74875_3_);
					}
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 5, 3, 5, p_74875_3_);
					for(var4 = 2; var4 <= 8; ++var4)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 2, 3, var4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 3, 3, var4, p_74875_3_);
						if(var4 <= 3 || var4 >= 7)
						{
							placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 4, 3, var4, p_74875_3_);
							placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 5, 3, var4, p_74875_3_);
							placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 6, 3, var4, p_74875_3_);
						}
						placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 7, 3, var4, p_74875_3_);
						placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 3, var4, p_74875_3_);
					}
					placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, getMetadataWithOffset(Block.ladder.blockID, 4), 9, 1, 3, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, getMetadataWithOffset(Block.ladder.blockID, 4), 9, 2, 3, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, getMetadataWithOffset(Block.ladder.blockID, 4), 9, 3, 3, p_74875_3_);
					generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, 4, 8, WeightedRandomChestContent.func_92080_a(strongholdRoomCrossingChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 1 + p_74875_2_.nextInt(4));
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		getNextComponentNormal((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
		getNextComponentX((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
		getNextComponentZ((ComponentStrongholdStairs2) p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
	}
	
	public static ComponentStrongholdRoomCrossing findValidPlacement(List p_75012_0_, Random p_75012_1_, int p_75012_2_, int p_75012_3_, int p_75012_4_, int p_75012_5_, int p_75012_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75012_2_, p_75012_3_, p_75012_4_, -4, -1, 0, 11, 7, 11, p_75012_5_);
		return canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(p_75012_0_, var7) == null ? new ComponentStrongholdRoomCrossing(p_75012_6_, p_75012_1_, var7, p_75012_5_) : null;
	}
}
