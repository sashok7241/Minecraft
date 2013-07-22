package net.minecraft.src;

import java.util.Random;

public class BlockVine extends Block
{
	public BlockVine(int p_i4019_1_)
	{
		super(p_i4019_1_, Material.vine);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	private boolean canBePlacedOn(int p_72151_1_)
	{
		if(p_72151_1_ == 0) return false;
		else
		{
			Block var2 = Block.blocksList[p_72151_1_];
			return var2.renderAsNormalBlock() && var2.blockMaterial.blocksMovement();
		}
	}
	
	@Override public boolean canPlaceBlockOnSide(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
	{
		switch(p_71850_5_)
		{
			case 1:
				return canBePlacedOn(p_71850_1_.getBlockId(p_71850_2_, p_71850_3_ + 1, p_71850_4_));
			case 2:
				return canBePlacedOn(p_71850_1_.getBlockId(p_71850_2_, p_71850_3_, p_71850_4_ + 1));
			case 3:
				return canBePlacedOn(p_71850_1_.getBlockId(p_71850_2_, p_71850_3_, p_71850_4_ - 1));
			case 4:
				return canBePlacedOn(p_71850_1_.getBlockId(p_71850_2_ + 1, p_71850_3_, p_71850_4_));
			case 5:
				return canBePlacedOn(p_71850_1_.getBlockId(p_71850_2_ - 1, p_71850_3_, p_71850_4_));
			default:
				return false;
		}
	}
	
	private boolean canVineStay(World p_72150_1_, int p_72150_2_, int p_72150_3_, int p_72150_4_)
	{
		int var5 = p_72150_1_.getBlockMetadata(p_72150_2_, p_72150_3_, p_72150_4_);
		int var6 = var5;
		if(var5 > 0)
		{
			for(int var7 = 0; var7 <= 3; ++var7)
			{
				int var8 = 1 << var7;
				if((var5 & var8) != 0 && !canBePlacedOn(p_72150_1_.getBlockId(p_72150_2_ + Direction.offsetX[var7], p_72150_3_, p_72150_4_ + Direction.offsetZ[var7])) && (p_72150_1_.getBlockId(p_72150_2_, p_72150_3_ + 1, p_72150_4_) != blockID || (p_72150_1_.getBlockMetadata(p_72150_2_, p_72150_3_ + 1, p_72150_4_) & var8) == 0))
				{
					var6 &= ~var8;
				}
			}
		}
		if(var6 == 0 && !canBePlacedOn(p_72150_1_.getBlockId(p_72150_2_, p_72150_3_ + 1, p_72150_4_))) return false;
		else
		{
			if(var6 != var5)
			{
				p_72150_1_.setBlockMetadataWithNotify(p_72150_2_, p_72150_3_, p_72150_4_, var6, 2);
			}
			return true;
		}
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeFoliageColor();
	}
	
	@Override public int getBlockColor()
	{
		return ColorizerFoliage.getFoliageColorBasic();
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public int getRenderColor(int par1)
	{
		return ColorizerFoliage.getFoliageColorBasic();
	}
	
	@Override public int getRenderType()
	{
		return 20;
	}
	
	@Override public void harvestBlock(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
	{
		if(!p_71893_1_.isRemote && p_71893_2_.getCurrentEquippedItem() != null && p_71893_2_.getCurrentEquippedItem().itemID == Item.shears.itemID)
		{
			p_71893_2_.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(Block.vine, 1, 0));
		} else
		{
			super.harvestBlock(p_71893_1_, p_71893_2_, p_71893_3_, p_71893_4_, p_71893_5_, p_71893_6_);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		byte var10 = 0;
		switch(p_85104_5_)
		{
			case 2:
				var10 = 1;
				break;
			case 3:
				var10 = 4;
				break;
			case 4:
				var10 = 8;
				break;
			case 5:
				var10 = 2;
		}
		return var10 != 0 ? var10 : p_85104_9_;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.isRemote && !canVineStay(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			dropBlockAsItem(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_, p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_), 0);
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var6 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
		float var7 = 1.0F;
		float var8 = 1.0F;
		float var9 = 1.0F;
		float var10 = 0.0F;
		float var11 = 0.0F;
		float var12 = 0.0F;
		boolean var13 = var6 > 0;
		if((var6 & 2) != 0)
		{
			var10 = Math.max(var10, 0.0625F);
			var7 = 0.0F;
			var8 = 0.0F;
			var11 = 1.0F;
			var9 = 0.0F;
			var12 = 1.0F;
			var13 = true;
		}
		if((var6 & 8) != 0)
		{
			var7 = Math.min(var7, 0.9375F);
			var10 = 1.0F;
			var8 = 0.0F;
			var11 = 1.0F;
			var9 = 0.0F;
			var12 = 1.0F;
			var13 = true;
		}
		if((var6 & 4) != 0)
		{
			var12 = Math.max(var12, 0.0625F);
			var9 = 0.0F;
			var7 = 0.0F;
			var10 = 1.0F;
			var8 = 0.0F;
			var11 = 1.0F;
			var13 = true;
		}
		if((var6 & 1) != 0)
		{
			var9 = Math.min(var9, 0.9375F);
			var12 = 1.0F;
			var7 = 0.0F;
			var10 = 1.0F;
			var8 = 0.0F;
			var11 = 1.0F;
			var13 = true;
		}
		if(!var13 && canBePlacedOn(p_71902_1_.getBlockId(p_71902_2_, p_71902_3_ + 1, p_71902_4_)))
		{
			var8 = Math.min(var8, 0.9375F);
			var11 = 1.0F;
			var7 = 0.0F;
			var10 = 1.0F;
			var9 = 0.0F;
			var12 = 1.0F;
		}
		setBlockBounds(var7, var8, var9, var10, var11, var12);
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote && p_71847_1_.rand.nextInt(4) == 0)
		{
			byte var6 = 4;
			int var7 = 5;
			boolean var8 = false;
			int var9;
			int var10;
			int var11;
			label138: for(var9 = p_71847_2_ - var6; var9 <= p_71847_2_ + var6; ++var9)
			{
				for(var10 = p_71847_4_ - var6; var10 <= p_71847_4_ + var6; ++var10)
				{
					for(var11 = p_71847_3_ - 1; var11 <= p_71847_3_ + 1; ++var11)
					{
						if(p_71847_1_.getBlockId(var9, var11, var10) == blockID)
						{
							--var7;
							if(var7 <= 0)
							{
								var8 = true;
								break label138;
							}
						}
					}
				}
			}
			var9 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			var10 = p_71847_1_.rand.nextInt(6);
			var11 = Direction.facingToDirection[var10];
			int var12;
			int var13;
			if(var10 == 1 && p_71847_3_ < 255 && p_71847_1_.isAirBlock(p_71847_2_, p_71847_3_ + 1, p_71847_4_))
			{
				if(var8) return;
				var12 = p_71847_1_.rand.nextInt(16) & var9;
				if(var12 > 0)
				{
					for(var13 = 0; var13 <= 3; ++var13)
					{
						if(!canBePlacedOn(p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var13], p_71847_3_ + 1, p_71847_4_ + Direction.offsetZ[var13])))
						{
							var12 &= ~(1 << var13);
						}
					}
					if(var12 > 0)
					{
						p_71847_1_.setBlock(p_71847_2_, p_71847_3_ + 1, p_71847_4_, blockID, var12, 2);
					}
				}
			} else
			{
				int var14;
				if(var10 >= 2 && var10 <= 5 && (var9 & 1 << var11) == 0)
				{
					if(var8) return;
					var12 = p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var11], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11]);
					if(var12 != 0 && Block.blocksList[var12] != null)
					{
						if(Block.blocksList[var12].blockMaterial.isOpaque() && Block.blocksList[var12].renderAsNormalBlock())
						{
							p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var9 | 1 << var11, 2);
						}
					} else
					{
						var13 = var11 + 1 & 3;
						var14 = var11 + 3 & 3;
						if((var9 & 1 << var13) != 0 && canBePlacedOn(p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var11] + Direction.offsetX[var13], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var13])))
						{
							p_71847_1_.setBlock(p_71847_2_ + Direction.offsetX[var11], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11], blockID, 1 << var13, 2);
						} else if((var9 & 1 << var14) != 0 && canBePlacedOn(p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var11] + Direction.offsetX[var14], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var14])))
						{
							p_71847_1_.setBlock(p_71847_2_ + Direction.offsetX[var11], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11], blockID, 1 << var14, 2);
						} else if((var9 & 1 << var13) != 0 && p_71847_1_.isAirBlock(p_71847_2_ + Direction.offsetX[var11] + Direction.offsetX[var13], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var13]) && canBePlacedOn(p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var13], p_71847_3_, p_71847_4_ + Direction.offsetZ[var13])))
						{
							p_71847_1_.setBlock(p_71847_2_ + Direction.offsetX[var11] + Direction.offsetX[var13], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var13], blockID, 1 << (var11 + 2 & 3), 2);
						} else if((var9 & 1 << var14) != 0 && p_71847_1_.isAirBlock(p_71847_2_ + Direction.offsetX[var11] + Direction.offsetX[var14], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var14]) && canBePlacedOn(p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var14], p_71847_3_, p_71847_4_ + Direction.offsetZ[var14])))
						{
							p_71847_1_.setBlock(p_71847_2_ + Direction.offsetX[var11] + Direction.offsetX[var14], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11] + Direction.offsetZ[var14], blockID, 1 << (var11 + 2 & 3), 2);
						} else if(canBePlacedOn(p_71847_1_.getBlockId(p_71847_2_ + Direction.offsetX[var11], p_71847_3_ + 1, p_71847_4_ + Direction.offsetZ[var11])))
						{
							p_71847_1_.setBlock(p_71847_2_ + Direction.offsetX[var11], p_71847_3_, p_71847_4_ + Direction.offsetZ[var11], blockID, 0, 2);
						}
					}
				} else if(p_71847_3_ > 1)
				{
					var12 = p_71847_1_.getBlockId(p_71847_2_, p_71847_3_ - 1, p_71847_4_);
					if(var12 == 0)
					{
						var13 = p_71847_1_.rand.nextInt(16) & var9;
						if(var13 > 0)
						{
							p_71847_1_.setBlock(p_71847_2_, p_71847_3_ - 1, p_71847_4_, blockID, var13, 2);
						}
					} else if(var12 == blockID)
					{
						var13 = p_71847_1_.rand.nextInt(16) & var9;
						var14 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_ - 1, p_71847_4_);
						if(var14 != (var14 | var13))
						{
							p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_ - 1, p_71847_4_, var14 | var13, 2);
						}
					}
				}
			}
		}
	}
}
