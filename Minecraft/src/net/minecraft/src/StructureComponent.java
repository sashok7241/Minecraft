package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class StructureComponent
{
	protected StructureBoundingBox boundingBox;
	protected int coordBaseMode;
	protected int componentType;
	
	protected StructureComponent(int p_i3857_1_)
	{
		componentType = p_i3857_1_;
		coordBaseMode = -1;
	}
	
	public abstract boolean addComponentParts(World var1, Random var2, StructureBoundingBox var3);
	
	public void buildComponent(StructureComponent p_74861_1_, List p_74861_2_, Random p_74861_3_)
	{
	}
	
	protected void clearCurrentPositionBlocksUpwards(World p_74871_1_, int p_74871_2_, int p_74871_3_, int p_74871_4_, StructureBoundingBox p_74871_5_)
	{
		int var6 = getXWithOffset(p_74871_2_, p_74871_4_);
		int var7 = getYWithOffset(p_74871_3_);
		int var8 = getZWithOffset(p_74871_2_, p_74871_4_);
		if(p_74871_5_.isVecInside(var6, var7, var8))
		{
			while(!p_74871_1_.isAirBlock(var6, var7, var8) && var7 < 255)
			{
				p_74871_1_.setBlock(var6, var7, var8, 0, 0, 2);
				++var7;
			}
		}
	}
	
	protected void fillCurrentPositionBlocksDownwards(World p_74870_1_, int p_74870_2_, int p_74870_3_, int p_74870_4_, int p_74870_5_, int p_74870_6_, StructureBoundingBox p_74870_7_)
	{
		int var8 = getXWithOffset(p_74870_4_, p_74870_6_);
		int var9 = getYWithOffset(p_74870_5_);
		int var10 = getZWithOffset(p_74870_4_, p_74870_6_);
		if(p_74870_7_.isVecInside(var8, var9, var10))
		{
			while((p_74870_1_.isAirBlock(var8, var9, var10) || p_74870_1_.getBlockMaterial(var8, var9, var10).isLiquid()) && var9 > 1)
			{
				p_74870_1_.setBlock(var8, var9, var10, p_74870_2_, p_74870_3_, 2);
				--var9;
			}
		}
	}
	
	protected void fillWithAir(World p_74878_1_, StructureBoundingBox p_74878_2_, int p_74878_3_, int p_74878_4_, int p_74878_5_, int p_74878_6_, int p_74878_7_, int p_74878_8_)
	{
		for(int var9 = p_74878_4_; var9 <= p_74878_7_; ++var9)
		{
			for(int var10 = p_74878_3_; var10 <= p_74878_6_; ++var10)
			{
				for(int var11 = p_74878_5_; var11 <= p_74878_8_; ++var11)
				{
					placeBlockAtCurrentPosition(p_74878_1_, 0, 0, var10, var9, var11, p_74878_2_);
				}
			}
		}
	}
	
	protected void fillWithBlocks(World p_74884_1_, StructureBoundingBox p_74884_2_, int p_74884_3_, int p_74884_4_, int p_74884_5_, int p_74884_6_, int p_74884_7_, int p_74884_8_, int p_74884_9_, int p_74884_10_, boolean p_74884_11_)
	{
		for(int var12 = p_74884_4_; var12 <= p_74884_7_; ++var12)
		{
			for(int var13 = p_74884_3_; var13 <= p_74884_6_; ++var13)
			{
				for(int var14 = p_74884_5_; var14 <= p_74884_8_; ++var14)
				{
					if(!p_74884_11_ || getBlockIdAtCurrentPosition(p_74884_1_, var13, var12, var14, p_74884_2_) != 0)
					{
						if(var12 != p_74884_4_ && var12 != p_74884_7_ && var13 != p_74884_3_ && var13 != p_74884_6_ && var14 != p_74884_5_ && var14 != p_74884_8_)
						{
							placeBlockAtCurrentPosition(p_74884_1_, p_74884_10_, 0, var13, var12, var14, p_74884_2_);
						} else
						{
							placeBlockAtCurrentPosition(p_74884_1_, p_74884_9_, 0, var13, var12, var14, p_74884_2_);
						}
					}
				}
			}
		}
	}
	
	protected void fillWithMetadataBlocks(World p_74872_1_, StructureBoundingBox p_74872_2_, int p_74872_3_, int p_74872_4_, int p_74872_5_, int p_74872_6_, int p_74872_7_, int p_74872_8_, int p_74872_9_, int p_74872_10_, int p_74872_11_, int p_74872_12_, boolean p_74872_13_)
	{
		for(int var14 = p_74872_4_; var14 <= p_74872_7_; ++var14)
		{
			for(int var15 = p_74872_3_; var15 <= p_74872_6_; ++var15)
			{
				for(int var16 = p_74872_5_; var16 <= p_74872_8_; ++var16)
				{
					if(!p_74872_13_ || getBlockIdAtCurrentPosition(p_74872_1_, var15, var14, var16, p_74872_2_) != 0)
					{
						if(var14 != p_74872_4_ && var14 != p_74872_7_ && var15 != p_74872_3_ && var15 != p_74872_6_ && var16 != p_74872_5_ && var16 != p_74872_8_)
						{
							placeBlockAtCurrentPosition(p_74872_1_, p_74872_11_, p_74872_12_, var15, var14, var16, p_74872_2_);
						} else
						{
							placeBlockAtCurrentPosition(p_74872_1_, p_74872_9_, p_74872_10_, var15, var14, var16, p_74872_2_);
						}
					}
				}
			}
		}
	}
	
	protected void fillWithRandomizedBlocks(World p_74882_1_, StructureBoundingBox p_74882_2_, int p_74882_3_, int p_74882_4_, int p_74882_5_, int p_74882_6_, int p_74882_7_, int p_74882_8_, boolean p_74882_9_, Random p_74882_10_, StructurePieceBlockSelector p_74882_11_)
	{
		for(int var12 = p_74882_4_; var12 <= p_74882_7_; ++var12)
		{
			for(int var13 = p_74882_3_; var13 <= p_74882_6_; ++var13)
			{
				for(int var14 = p_74882_5_; var14 <= p_74882_8_; ++var14)
				{
					if(!p_74882_9_ || getBlockIdAtCurrentPosition(p_74882_1_, var13, var12, var14, p_74882_2_) != 0)
					{
						p_74882_11_.selectBlocks(p_74882_10_, var13, var12, var14, var12 == p_74882_4_ || var12 == p_74882_7_ || var13 == p_74882_3_ || var13 == p_74882_6_ || var14 == p_74882_5_ || var14 == p_74882_8_);
						placeBlockAtCurrentPosition(p_74882_1_, p_74882_11_.getSelectedBlockId(), p_74882_11_.getSelectedBlockMetaData(), var13, var12, var14, p_74882_2_);
					}
				}
			}
		}
	}
	
	protected boolean generateStructureChestContents(World p_74879_1_, StructureBoundingBox p_74879_2_, Random p_74879_3_, int p_74879_4_, int p_74879_5_, int p_74879_6_, WeightedRandomChestContent[] p_74879_7_, int p_74879_8_)
	{
		int var9 = getXWithOffset(p_74879_4_, p_74879_6_);
		int var10 = getYWithOffset(p_74879_5_);
		int var11 = getZWithOffset(p_74879_4_, p_74879_6_);
		if(p_74879_2_.isVecInside(var9, var10, var11) && p_74879_1_.getBlockId(var9, var10, var11) != Block.chest.blockID)
		{
			p_74879_1_.setBlock(var9, var10, var11, Block.chest.blockID, 0, 2);
			TileEntityChest var12 = (TileEntityChest) p_74879_1_.getBlockTileEntity(var9, var10, var11);
			if(var12 != null)
			{
				WeightedRandomChestContent.generateChestContents(p_74879_3_, p_74879_7_, var12, p_74879_8_);
			}
			return true;
		} else return false;
	}
	
	protected boolean generateStructureDispenserContents(World p_74869_1_, StructureBoundingBox p_74869_2_, Random p_74869_3_, int p_74869_4_, int p_74869_5_, int p_74869_6_, int p_74869_7_, WeightedRandomChestContent[] p_74869_8_, int p_74869_9_)
	{
		int var10 = getXWithOffset(p_74869_4_, p_74869_6_);
		int var11 = getYWithOffset(p_74869_5_);
		int var12 = getZWithOffset(p_74869_4_, p_74869_6_);
		if(p_74869_2_.isVecInside(var10, var11, var12) && p_74869_1_.getBlockId(var10, var11, var12) != Block.dispenser.blockID)
		{
			p_74869_1_.setBlock(var10, var11, var12, Block.dispenser.blockID, getMetadataWithOffset(Block.dispenser.blockID, p_74869_7_), 2);
			TileEntityDispenser var13 = (TileEntityDispenser) p_74869_1_.getBlockTileEntity(var10, var11, var12);
			if(var13 != null)
			{
				WeightedRandomChestContent.generateDispenserContents(p_74869_3_, p_74869_8_, var13, p_74869_9_);
			}
			return true;
		} else return false;
	}
	
	protected int getBlockIdAtCurrentPosition(World p_74866_1_, int p_74866_2_, int p_74866_3_, int p_74866_4_, StructureBoundingBox p_74866_5_)
	{
		int var6 = getXWithOffset(p_74866_2_, p_74866_4_);
		int var7 = getYWithOffset(p_74866_3_);
		int var8 = getZWithOffset(p_74866_2_, p_74866_4_);
		return !p_74866_5_.isVecInside(var6, var7, var8) ? 0 : p_74866_1_.getBlockId(var6, var7, var8);
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
	
	protected int getMetadataWithOffset(int p_74863_1_, int p_74863_2_)
	{
		if(p_74863_1_ == Block.rail.blockID)
		{
			if(coordBaseMode == 1 || coordBaseMode == 3)
			{
				if(p_74863_2_ == 1) return 0;
				return 1;
			}
		} else if(p_74863_1_ != Block.doorWood.blockID && p_74863_1_ != Block.doorIron.blockID)
		{
			if(p_74863_1_ != Block.stairsCobblestone.blockID && p_74863_1_ != Block.stairsWoodOak.blockID && p_74863_1_ != Block.stairsNetherBrick.blockID && p_74863_1_ != Block.stairsStoneBrick.blockID && p_74863_1_ != Block.stairsSandStone.blockID)
			{
				if(p_74863_1_ == Block.ladder.blockID)
				{
					if(coordBaseMode == 0)
					{
						if(p_74863_2_ == 2) return 3;
						if(p_74863_2_ == 3) return 2;
					} else if(coordBaseMode == 1)
					{
						if(p_74863_2_ == 2) return 4;
						if(p_74863_2_ == 3) return 5;
						if(p_74863_2_ == 4) return 2;
						if(p_74863_2_ == 5) return 3;
					} else if(coordBaseMode == 3)
					{
						if(p_74863_2_ == 2) return 5;
						if(p_74863_2_ == 3) return 4;
						if(p_74863_2_ == 4) return 2;
						if(p_74863_2_ == 5) return 3;
					}
				} else if(p_74863_1_ == Block.stoneButton.blockID)
				{
					if(coordBaseMode == 0)
					{
						if(p_74863_2_ == 3) return 4;
						if(p_74863_2_ == 4) return 3;
					} else if(coordBaseMode == 1)
					{
						if(p_74863_2_ == 3) return 1;
						if(p_74863_2_ == 4) return 2;
						if(p_74863_2_ == 2) return 3;
						if(p_74863_2_ == 1) return 4;
					} else if(coordBaseMode == 3)
					{
						if(p_74863_2_ == 3) return 2;
						if(p_74863_2_ == 4) return 1;
						if(p_74863_2_ == 2) return 3;
						if(p_74863_2_ == 1) return 4;
					}
				} else if(p_74863_1_ != Block.tripWireSource.blockID && (Block.blocksList[p_74863_1_] == null || !(Block.blocksList[p_74863_1_] instanceof BlockDirectional)))
				{
					if(p_74863_1_ == Block.pistonBase.blockID || p_74863_1_ == Block.pistonStickyBase.blockID || p_74863_1_ == Block.lever.blockID || p_74863_1_ == Block.dispenser.blockID)
					{
						if(coordBaseMode == 0)
						{
							if(p_74863_2_ == 2 || p_74863_2_ == 3) return Facing.oppositeSide[p_74863_2_];
						} else if(coordBaseMode == 1)
						{
							if(p_74863_2_ == 2) return 4;
							if(p_74863_2_ == 3) return 5;
							if(p_74863_2_ == 4) return 2;
							if(p_74863_2_ == 5) return 3;
						} else if(coordBaseMode == 3)
						{
							if(p_74863_2_ == 2) return 5;
							if(p_74863_2_ == 3) return 4;
							if(p_74863_2_ == 4) return 2;
							if(p_74863_2_ == 5) return 3;
						}
					}
				} else if(coordBaseMode == 0)
				{
					if(p_74863_2_ == 0 || p_74863_2_ == 2) return Direction.rotateOpposite[p_74863_2_];
				} else if(coordBaseMode == 1)
				{
					if(p_74863_2_ == 2) return 1;
					if(p_74863_2_ == 0) return 3;
					if(p_74863_2_ == 1) return 2;
					if(p_74863_2_ == 3) return 0;
				} else if(coordBaseMode == 3)
				{
					if(p_74863_2_ == 2) return 3;
					if(p_74863_2_ == 0) return 1;
					if(p_74863_2_ == 1) return 2;
					if(p_74863_2_ == 3) return 0;
				}
			} else if(coordBaseMode == 0)
			{
				if(p_74863_2_ == 2) return 3;
				if(p_74863_2_ == 3) return 2;
			} else if(coordBaseMode == 1)
			{
				if(p_74863_2_ == 0) return 2;
				if(p_74863_2_ == 1) return 3;
				if(p_74863_2_ == 2) return 0;
				if(p_74863_2_ == 3) return 1;
			} else if(coordBaseMode == 3)
			{
				if(p_74863_2_ == 0) return 2;
				if(p_74863_2_ == 1) return 3;
				if(p_74863_2_ == 2) return 1;
				if(p_74863_2_ == 3) return 0;
			}
		} else if(coordBaseMode == 0)
		{
			if(p_74863_2_ == 0) return 2;
			if(p_74863_2_ == 2) return 0;
		} else
		{
			if(coordBaseMode == 1) return p_74863_2_ + 1 & 3;
			if(coordBaseMode == 3) return p_74863_2_ + 3 & 3;
		}
		return p_74863_2_;
	}
	
	protected int getXWithOffset(int p_74865_1_, int p_74865_2_)
	{
		switch(coordBaseMode)
		{
			case 0:
			case 2:
				return boundingBox.minX + p_74865_1_;
			case 1:
				return boundingBox.maxX - p_74865_2_;
			case 3:
				return boundingBox.minX + p_74865_2_;
			default:
				return p_74865_1_;
		}
	}
	
	protected int getYWithOffset(int p_74862_1_)
	{
		return coordBaseMode == -1 ? p_74862_1_ : p_74862_1_ + boundingBox.minY;
	}
	
	protected int getZWithOffset(int p_74873_1_, int p_74873_2_)
	{
		switch(coordBaseMode)
		{
			case 0:
				return boundingBox.minZ + p_74873_2_;
			case 1:
			case 3:
				return boundingBox.minZ + p_74873_1_;
			case 2:
				return boundingBox.maxZ - p_74873_2_;
			default:
				return p_74873_2_;
		}
	}
	
	protected boolean isLiquidInStructureBoundingBox(World p_74860_1_, StructureBoundingBox p_74860_2_)
	{
		int var3 = Math.max(boundingBox.minX - 1, p_74860_2_.minX);
		int var4 = Math.max(boundingBox.minY - 1, p_74860_2_.minY);
		int var5 = Math.max(boundingBox.minZ - 1, p_74860_2_.minZ);
		int var6 = Math.min(boundingBox.maxX + 1, p_74860_2_.maxX);
		int var7 = Math.min(boundingBox.maxY + 1, p_74860_2_.maxY);
		int var8 = Math.min(boundingBox.maxZ + 1, p_74860_2_.maxZ);
		int var9;
		int var10;
		int var11;
		for(var9 = var3; var9 <= var6; ++var9)
		{
			for(var10 = var5; var10 <= var8; ++var10)
			{
				var11 = p_74860_1_.getBlockId(var9, var4, var10);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
				var11 = p_74860_1_.getBlockId(var9, var7, var10);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
			}
		}
		for(var9 = var3; var9 <= var6; ++var9)
		{
			for(var10 = var4; var10 <= var7; ++var10)
			{
				var11 = p_74860_1_.getBlockId(var9, var10, var5);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
				var11 = p_74860_1_.getBlockId(var9, var10, var8);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
			}
		}
		for(var9 = var5; var9 <= var8; ++var9)
		{
			for(var10 = var4; var10 <= var7; ++var10)
			{
				var11 = p_74860_1_.getBlockId(var3, var10, var9);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
				var11 = p_74860_1_.getBlockId(var6, var10, var9);
				if(var11 > 0 && Block.blocksList[var11].blockMaterial.isLiquid()) return true;
			}
		}
		return false;
	}
	
	protected void placeBlockAtCurrentPosition(World p_74864_1_, int p_74864_2_, int p_74864_3_, int p_74864_4_, int p_74864_5_, int p_74864_6_, StructureBoundingBox p_74864_7_)
	{
		int var8 = getXWithOffset(p_74864_4_, p_74864_6_);
		int var9 = getYWithOffset(p_74864_5_);
		int var10 = getZWithOffset(p_74864_4_, p_74864_6_);
		if(p_74864_7_.isVecInside(var8, var9, var10))
		{
			p_74864_1_.setBlock(var8, var9, var10, p_74864_2_, p_74864_3_, 2);
		}
	}
	
	protected void placeDoorAtCurrentPosition(World p_74881_1_, StructureBoundingBox p_74881_2_, Random p_74881_3_, int p_74881_4_, int p_74881_5_, int p_74881_6_, int p_74881_7_)
	{
		int var8 = getXWithOffset(p_74881_4_, p_74881_6_);
		int var9 = getYWithOffset(p_74881_5_);
		int var10 = getZWithOffset(p_74881_4_, p_74881_6_);
		if(p_74881_2_.isVecInside(var8, var9, var10))
		{
			ItemDoor.placeDoorBlock(p_74881_1_, var8, var9, var10, p_74881_7_, Block.doorWood);
		}
	}
	
	protected void randomlyFillWithBlocks(World p_74880_1_, StructureBoundingBox p_74880_2_, Random p_74880_3_, float p_74880_4_, int p_74880_5_, int p_74880_6_, int p_74880_7_, int p_74880_8_, int p_74880_9_, int p_74880_10_, int p_74880_11_, int p_74880_12_, boolean p_74880_13_)
	{
		for(int var14 = p_74880_6_; var14 <= p_74880_9_; ++var14)
		{
			for(int var15 = p_74880_5_; var15 <= p_74880_8_; ++var15)
			{
				for(int var16 = p_74880_7_; var16 <= p_74880_10_; ++var16)
				{
					if(p_74880_3_.nextFloat() <= p_74880_4_ && (!p_74880_13_ || getBlockIdAtCurrentPosition(p_74880_1_, var15, var14, var16, p_74880_2_) != 0))
					{
						if(var14 != p_74880_6_ && var14 != p_74880_9_ && var15 != p_74880_5_ && var15 != p_74880_8_ && var16 != p_74880_7_ && var16 != p_74880_10_)
						{
							placeBlockAtCurrentPosition(p_74880_1_, p_74880_12_, 0, var15, var14, var16, p_74880_2_);
						} else
						{
							placeBlockAtCurrentPosition(p_74880_1_, p_74880_11_, 0, var15, var14, var16, p_74880_2_);
						}
					}
				}
			}
		}
	}
	
	protected void randomlyPlaceBlock(World p_74876_1_, StructureBoundingBox p_74876_2_, Random p_74876_3_, float p_74876_4_, int p_74876_5_, int p_74876_6_, int p_74876_7_, int p_74876_8_, int p_74876_9_)
	{
		if(p_74876_3_.nextFloat() < p_74876_4_)
		{
			placeBlockAtCurrentPosition(p_74876_1_, p_74876_8_, p_74876_9_, p_74876_5_, p_74876_6_, p_74876_7_, p_74876_2_);
		}
	}
	
	protected void randomlyRareFillWithBlocks(World p_74867_1_, StructureBoundingBox p_74867_2_, int p_74867_3_, int p_74867_4_, int p_74867_5_, int p_74867_6_, int p_74867_7_, int p_74867_8_, int p_74867_9_, boolean p_74867_10_)
	{
		float var11 = p_74867_6_ - p_74867_3_ + 1;
		float var12 = p_74867_7_ - p_74867_4_ + 1;
		float var13 = p_74867_8_ - p_74867_5_ + 1;
		float var14 = p_74867_3_ + var11 / 2.0F;
		float var15 = p_74867_5_ + var13 / 2.0F;
		for(int var16 = p_74867_4_; var16 <= p_74867_7_; ++var16)
		{
			float var17 = (var16 - p_74867_4_) / var12;
			for(int var18 = p_74867_3_; var18 <= p_74867_6_; ++var18)
			{
				float var19 = (var18 - var14) / (var11 * 0.5F);
				for(int var20 = p_74867_5_; var20 <= p_74867_8_; ++var20)
				{
					float var21 = (var20 - var15) / (var13 * 0.5F);
					if(!p_74867_10_ || getBlockIdAtCurrentPosition(p_74867_1_, var18, var16, var20, p_74867_2_) != 0)
					{
						float var22 = var19 * var19 + var17 * var17 + var21 * var21;
						if(var22 <= 1.05F)
						{
							placeBlockAtCurrentPosition(p_74867_1_, p_74867_9_, 0, var18, var16, var20, p_74867_2_);
						}
					}
				}
			}
		}
	}
	
	public static StructureComponent findIntersecting(List p_74883_0_, StructureBoundingBox p_74883_1_)
	{
		Iterator var2 = p_74883_0_.iterator();
		StructureComponent var3;
		do
		{
			if(!var2.hasNext()) return null;
			var3 = (StructureComponent) var2.next();
		} while(var3.getBoundingBox() == null || !var3.getBoundingBox().intersectsWith(p_74883_1_));
		return var3;
	}
}
