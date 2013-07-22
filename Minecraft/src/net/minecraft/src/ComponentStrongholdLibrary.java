package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentStrongholdLibrary extends ComponentStronghold
{
	private static final WeightedRandomChestContent[] strongholdLibraryChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.book.itemID, 0, 1, 3, 20), new WeightedRandomChestContent(Item.paper.itemID, 0, 2, 7, 20), new WeightedRandomChestContent(Item.emptyMap.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Item.compass.itemID, 0, 1, 1, 1) };
	protected final EnumDoor doorType;
	private final boolean isLargeRoom;
	
	public ComponentStrongholdLibrary(int p_i3844_1_, Random p_i3844_2_, StructureBoundingBox p_i3844_3_, int p_i3844_4_)
	{
		super(p_i3844_1_);
		coordBaseMode = p_i3844_4_;
		doorType = getRandomDoor(p_i3844_2_);
		boundingBox = p_i3844_3_;
		isLargeRoom = p_i3844_3_.getYSize() > 6;
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			byte var4 = 11;
			if(!isLargeRoom)
			{
				var4 = 6;
			}
			fillWithRandomizedBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 13, var4 - 1, 14, true, p_74875_2_, StructureStrongholdPieces.getStrongholdStones());
			placeDoor(p_74875_1_, p_74875_2_, p_74875_3_, doorType, 4, 1, 0);
			randomlyFillWithBlocks(p_74875_1_, p_74875_3_, p_74875_2_, 0.07F, 2, 1, 1, 11, 4, 13, Block.web.blockID, Block.web.blockID, false);
			int var7;
			for(var7 = 1; var7 <= 13; ++var7)
			{
				if((var7 - 1) % 4 == 0)
				{
					fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, var7, 1, 4, var7, Block.planks.blockID, Block.planks.blockID, false);
					fillWithBlocks(p_74875_1_, p_74875_3_, 12, 1, var7, 12, 4, var7, Block.planks.blockID, Block.planks.blockID, false);
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 2, 3, var7, p_74875_3_);
					placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, 11, 3, var7, p_74875_3_);
					if(isLargeRoom)
					{
						fillWithBlocks(p_74875_1_, p_74875_3_, 1, 6, var7, 1, 9, var7, Block.planks.blockID, Block.planks.blockID, false);
						fillWithBlocks(p_74875_1_, p_74875_3_, 12, 6, var7, 12, 9, var7, Block.planks.blockID, Block.planks.blockID, false);
					}
				} else
				{
					fillWithBlocks(p_74875_1_, p_74875_3_, 1, 1, var7, 1, 4, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
					fillWithBlocks(p_74875_1_, p_74875_3_, 12, 1, var7, 12, 4, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
					if(isLargeRoom)
					{
						fillWithBlocks(p_74875_1_, p_74875_3_, 1, 6, var7, 1, 9, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
						fillWithBlocks(p_74875_1_, p_74875_3_, 12, 6, var7, 12, 9, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
					}
				}
			}
			for(var7 = 3; var7 < 12; var7 += 2)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 3, 1, var7, 4, 3, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 6, 1, var7, 7, 3, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 9, 1, var7, 10, 3, var7, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
			}
			if(isLargeRoom)
			{
				fillWithBlocks(p_74875_1_, p_74875_3_, 1, 5, 1, 3, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 10, 5, 1, 12, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 5, 1, 9, 5, 2, Block.planks.blockID, Block.planks.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 5, 12, 9, 5, 13, Block.planks.blockID, Block.planks.blockID, false);
				placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 9, 5, 11, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 8, 5, 11, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, 9, 5, 10, p_74875_3_);
				fillWithBlocks(p_74875_1_, p_74875_3_, 3, 6, 2, 3, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 10, 6, 2, 10, 6, 10, Block.fence.blockID, Block.fence.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 6, 2, 9, 6, 2, Block.fence.blockID, Block.fence.blockID, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 4, 6, 12, 8, 6, 12, Block.fence.blockID, Block.fence.blockID, false);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 9, 6, 11, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 8, 6, 11, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, 9, 6, 10, p_74875_3_);
				var7 = getMetadataWithOffset(Block.ladder.blockID, 3);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 1, 13, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 2, 13, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 3, 13, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 4, 13, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 5, 13, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 6, 13, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.ladder.blockID, var7, 10, 7, 13, p_74875_3_);
				byte var8 = 7;
				byte var9 = 7;
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 - 1, 9, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8, 9, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 - 1, 8, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8, 8, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 - 1, 7, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8, 7, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 - 2, 7, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 + 1, 7, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 - 1, 7, var9 - 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8 - 1, 7, var9 + 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8, 7, var9 - 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.fence.blockID, 0, var8, 7, var9 + 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, var8 - 2, 8, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, var8 + 1, 8, var9, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, var8 - 1, 8, var9 - 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, var8 - 1, 8, var9 + 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, var8, 8, var9 - 1, p_74875_3_);
				placeBlockAtCurrentPosition(p_74875_1_, Block.torchWood.blockID, 0, var8, 8, var9 + 1, p_74875_3_);
			}
			generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 3, 3, 5, WeightedRandomChestContent.func_92080_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92112_a(p_74875_2_, 1, 5, 2) }), 1 + p_74875_2_.nextInt(4));
			if(isLargeRoom)
			{
				placeBlockAtCurrentPosition(p_74875_1_, 0, 0, 12, 9, 1, p_74875_3_);
				generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 12, 8, 1, WeightedRandomChestContent.func_92080_a(strongholdLibraryChestContents, new WeightedRandomChestContent[] { Item.enchantedBook.func_92112_a(p_74875_2_, 1, 5, 2) }), 1 + p_74875_2_.nextInt(4));
			}
			return true;
		}
	}
	
	public static ComponentStrongholdLibrary findValidPlacement(List p_75006_0_, Random p_75006_1_, int p_75006_2_, int p_75006_3_, int p_75006_4_, int p_75006_5_, int p_75006_6_)
	{
		StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75006_2_, p_75006_3_, p_75006_4_, -4, -1, 0, 14, 11, 15, p_75006_5_);
		if(!canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_75006_0_, var7) != null)
		{
			var7 = StructureBoundingBox.getComponentToAddBoundingBox(p_75006_2_, p_75006_3_, p_75006_4_, -4, -1, 0, 14, 6, 15, p_75006_5_);
			if(!canStrongholdGoDeeper(var7) || StructureComponent.findIntersecting(p_75006_0_, var7) != null) return null;
		}
		return new ComponentStrongholdLibrary(p_75006_6_, p_75006_1_, var7, p_75006_5_);
	}
}
