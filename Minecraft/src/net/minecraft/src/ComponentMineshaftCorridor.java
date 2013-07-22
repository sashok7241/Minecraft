package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentMineshaftCorridor extends StructureComponent
{
	private final boolean hasRails;
	private final boolean hasSpiders;
	private boolean spawnerPlaced;
	private int sectionCount;
	
	public ComponentMineshaftCorridor(int p_i3807_1_, Random p_i3807_2_, StructureBoundingBox p_i3807_3_, int p_i3807_4_)
	{
		super(p_i3807_1_);
		coordBaseMode = p_i3807_4_;
		boundingBox = p_i3807_3_;
		hasRails = p_i3807_2_.nextInt(3) == 0;
		hasSpiders = !hasRails && p_i3807_2_.nextInt(23) == 0;
		if(coordBaseMode != 2 && coordBaseMode != 0)
		{
			sectionCount = p_i3807_3_.getXSize() / 5;
		} else
		{
			sectionCount = p_i3807_3_.getZSize() / 5;
		}
	}
	
	@Override public boolean addComponentParts(World p_74875_1_, Random p_74875_2_, StructureBoundingBox p_74875_3_)
	{
		if(isLiquidInStructureBoundingBox(p_74875_1_, p_74875_3_)) return false;
		else
		{
			int var8 = sectionCount * 5 - 1;
			fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, 0, 2, 1, var8, 0, 0, false);
			randomlyFillWithBlocks(p_74875_1_, p_74875_3_, p_74875_2_, 0.8F, 0, 2, 0, 2, 2, var8, 0, 0, false);
			if(hasSpiders)
			{
				randomlyFillWithBlocks(p_74875_1_, p_74875_3_, p_74875_2_, 0.6F, 0, 0, 0, 2, 1, var8, Block.web.blockID, 0, false);
			}
			int var9;
			int var10;
			int var11;
			for(var9 = 0; var9 < sectionCount; ++var9)
			{
				var10 = 2 + var9 * 5;
				fillWithBlocks(p_74875_1_, p_74875_3_, 0, 0, var10, 0, 1, var10, Block.fence.blockID, 0, false);
				fillWithBlocks(p_74875_1_, p_74875_3_, 2, 0, var10, 2, 1, var10, Block.fence.blockID, 0, false);
				if(p_74875_2_.nextInt(4) == 0)
				{
					fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, var10, 0, 2, var10, Block.planks.blockID, 0, false);
					fillWithBlocks(p_74875_1_, p_74875_3_, 2, 2, var10, 2, 2, var10, Block.planks.blockID, 0, false);
				} else
				{
					fillWithBlocks(p_74875_1_, p_74875_3_, 0, 2, var10, 2, 2, var10, Block.planks.blockID, 0, false);
				}
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 0, 2, var10 - 1, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 2, 2, var10 - 1, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 0, 2, var10 + 1, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.1F, 2, 2, var10 + 1, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 0, 2, var10 - 2, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 2, 2, var10 - 2, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 0, 2, var10 + 2, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 2, 2, var10 + 2, Block.web.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 1, 2, var10 - 1, Block.torchWood.blockID, 0);
				randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.05F, 1, 2, var10 + 1, Block.torchWood.blockID, 0);
				if(p_74875_2_.nextInt(100) == 0)
				{
					generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 2, 0, var10 - 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.func_78816_a(), new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 3 + p_74875_2_.nextInt(4));
				}
				if(p_74875_2_.nextInt(100) == 0)
				{
					generateStructureChestContents(p_74875_1_, p_74875_3_, p_74875_2_, 0, 0, var10 + 1, WeightedRandomChestContent.func_92080_a(StructureMineshaftPieces.func_78816_a(), new WeightedRandomChestContent[] { Item.enchantedBook.func_92114_b(p_74875_2_) }), 3 + p_74875_2_.nextInt(4));
				}
				if(hasSpiders && !spawnerPlaced)
				{
					var11 = getYWithOffset(0);
					int var12 = var10 - 1 + p_74875_2_.nextInt(3);
					int var13 = getXWithOffset(1, var12);
					var12 = getZWithOffset(1, var12);
					if(p_74875_3_.isVecInside(var13, var11, var12))
					{
						spawnerPlaced = true;
						p_74875_1_.setBlock(var13, var11, var12, Block.mobSpawner.blockID, 0, 2);
						TileEntityMobSpawner var14 = (TileEntityMobSpawner) p_74875_1_.getBlockTileEntity(var13, var11, var12);
						if(var14 != null)
						{
							var14.getSpawnerLogic().setMobID("CaveSpider");
						}
					}
				}
			}
			for(var9 = 0; var9 <= 2; ++var9)
			{
				for(var10 = 0; var10 <= var8; ++var10)
				{
					var11 = getBlockIdAtCurrentPosition(p_74875_1_, var9, -1, var10, p_74875_3_);
					if(var11 == 0)
					{
						placeBlockAtCurrentPosition(p_74875_1_, Block.planks.blockID, 0, var9, -1, var10, p_74875_3_);
					}
				}
			}
			if(hasRails)
			{
				for(var9 = 0; var9 <= var8; ++var9)
				{
					var10 = getBlockIdAtCurrentPosition(p_74875_1_, 1, -1, var9, p_74875_3_);
					if(var10 > 0 && Block.opaqueCubeLookup[var10])
					{
						randomlyPlaceBlock(p_74875_1_, p_74875_3_, p_74875_2_, 0.7F, 1, 0, var9, Block.rail.blockID, getMetadataWithOffset(Block.rail.blockID, 0));
					}
				}
			}
			return true;
		}
	}
	
	@Override public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
		int var4 = getComponentType();
		int var5 = p_74861_3_.nextInt(4);
		switch(coordBaseMode)
		{
			case 0:
				if(var5 <= 1)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ + 1, coordBaseMode, var4);
				} else if(var5 == 2)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ - 3, 1, var4);
				} else
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ - 3, 3, var4);
				}
				break;
			case 1:
				if(var5 <= 1)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ, coordBaseMode, var4);
				} else if(var5 == 2)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ - 1, 2, var4);
				} else
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ + 1, 0, var4);
				}
				break;
			case 2:
				if(var5 <= 1)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ - 1, coordBaseMode, var4);
				} else if(var5 == 2)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ, 1, var4);
				} else
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ, 3, var4);
				}
				break;
			case 3:
				if(var5 <= 1)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ, coordBaseMode, var4);
				} else if(var5 == 2)
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX - 3, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.minZ - 1, 2, var4);
				} else
				{
					StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX - 3, boundingBox.minY - 1 + p_74861_3_.nextInt(3), boundingBox.maxZ + 1, 0, var4);
				}
		}
		if(var4 < 8)
		{
			int var6;
			int var7;
			if(coordBaseMode != 2 && coordBaseMode != 0)
			{
				for(var6 = boundingBox.minX + 3; var6 + 3 <= boundingBox.maxX; var6 += 5)
				{
					var7 = p_74861_3_.nextInt(5);
					if(var7 == 0)
					{
						StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, var6, boundingBox.minY, boundingBox.minZ - 1, 2, var4 + 1);
					} else if(var7 == 1)
					{
						StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, var6, boundingBox.minY, boundingBox.maxZ + 1, 0, var4 + 1);
					}
				}
			} else
			{
				for(var6 = boundingBox.minZ + 3; var6 + 3 <= boundingBox.maxZ; var6 += 5)
				{
					var7 = p_74861_3_.nextInt(5);
					if(var7 == 0)
					{
						StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.minX - 1, boundingBox.minY, var6, 1, var4 + 1);
					} else if(var7 == 1)
					{
						StructureMineshaftPieces.getNextComponent(p_74861_1_, p_74861_2_, p_74861_3_, boundingBox.maxX + 1, boundingBox.minY, var6, 3, var4 + 1);
					}
				}
			}
		}
	}
	
	@Override protected boolean generateStructureChestContents(World p_74879_1_, StructureBoundingBox p_74879_2_, Random p_74879_3_, int p_74879_4_, int p_74879_5_, int p_74879_6_, WeightedRandomChestContent[] p_74879_7_, int p_74879_8_)
	{
		int var9 = getXWithOffset(p_74879_4_, p_74879_6_);
		int var10 = getYWithOffset(p_74879_5_);
		int var11 = getZWithOffset(p_74879_4_, p_74879_6_);
		if(p_74879_2_.isVecInside(var9, var10, var11) && p_74879_1_.getBlockId(var9, var10, var11) == 0)
		{
			p_74879_1_.setBlock(var9, var10, var11, Block.rail.blockID, getMetadataWithOffset(Block.rail.blockID, p_74879_3_.nextBoolean() ? 1 : 0), 2);
			EntityMinecartChest var12 = new EntityMinecartChest(p_74879_1_, var9 + 0.5F, var10 + 0.5F, var11 + 0.5F);
			WeightedRandomChestContent.generateChestContents(p_74879_3_, p_74879_7_, var12, p_74879_8_);
			p_74879_1_.spawnEntityInWorld(var12);
			return true;
		} else return false;
	}
	
	public static StructureBoundingBox findValidPlacement(List p_74954_0_, Random p_74954_1_, int p_74954_2_, int p_74954_3_, int p_74954_4_, int p_74954_5_)
	{
		StructureBoundingBox var6 = new StructureBoundingBox(p_74954_2_, p_74954_3_, p_74954_4_, p_74954_2_, p_74954_3_ + 2, p_74954_4_);
		int var7;
		for(var7 = p_74954_1_.nextInt(3) + 2; var7 > 0; --var7)
		{
			int var8 = var7 * 5;
			switch(p_74954_5_)
			{
				case 0:
					var6.maxX = p_74954_2_ + 2;
					var6.maxZ = p_74954_4_ + var8 - 1;
					break;
				case 1:
					var6.minX = p_74954_2_ - (var8 - 1);
					var6.maxZ = p_74954_4_ + 2;
					break;
				case 2:
					var6.maxX = p_74954_2_ + 2;
					var6.minZ = p_74954_4_ - (var8 - 1);
					break;
				case 3:
					var6.maxX = p_74954_2_ + var8 - 1;
					var6.maxZ = p_74954_4_ + 2;
			}
			if(StructureComponent.findIntersecting(p_74954_0_, var6) == null)
			{
				break;
			}
		}
		return var7 > 0 ? var6 : null;
	}
}
