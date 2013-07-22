package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockLeaves extends BlockLeavesBase
{
	public static final String[] LEAF_TYPES = new String[] { "oak", "spruce", "birch", "jungle" };
	public static final String[][] field_94396_b = new String[][] { { "leaves", "leaves_spruce", "leaves", "leaves_jungle" }, { "leaves_opaque", "leaves_spruce_opaque", "leaves_opaque", "leaves_jungle_opaque" } };
	private int field_94394_cP;
	private Icon[][] iconArray = new Icon[2][];
	int[] adjacentTreeBlocks;
	
	protected BlockLeaves(int p_i9068_1_)
	{
		super(p_i9068_1_, Material.leaves, false);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		byte var7 = 1;
		int var8 = var7 + 1;
		if(p_71852_1_.checkChunksExist(p_71852_2_ - var8, p_71852_3_ - var8, p_71852_4_ - var8, p_71852_2_ + var8, p_71852_3_ + var8, p_71852_4_ + var8))
		{
			for(int var9 = -var7; var9 <= var7; ++var9)
			{
				for(int var10 = -var7; var10 <= var7; ++var10)
				{
					for(int var11 = -var7; var11 <= var7; ++var11)
					{
						int var12 = p_71852_1_.getBlockId(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11);
						if(var12 == Block.leaves.blockID)
						{
							int var13 = p_71852_1_.getBlockMetadata(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11);
							p_71852_1_.setBlockMetadataWithNotify(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11, var13 | 8, 4);
						}
					}
				}
			}
		}
	}
	
	@Override public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		if((var5 & 3) == 1) return ColorizerFoliage.getFoliageColorPine();
		else if((var5 & 3) == 2) return ColorizerFoliage.getFoliageColorBirch();
		else
		{
			int var6 = 0;
			int var7 = 0;
			int var8 = 0;
			for(int var9 = -1; var9 <= 1; ++var9)
			{
				for(int var10 = -1; var10 <= 1; ++var10)
				{
					int var11 = par1IBlockAccess.getBiomeGenForCoords(par2 + var10, par4 + var9).getBiomeFoliageColor();
					var6 += (var11 & 16711680) >> 16;
					var7 += (var11 & 65280) >> 8;
					var8 += var11 & 255;
				}
			}
			return (var6 / 9 & 255) << 16 | (var7 / 9 & 255) << 8 | var8 / 9 & 255;
		}
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return new ItemStack(blockID, 1, p_71880_1_ & 3);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_ & 3;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		if(!p_71914_1_.isRemote)
		{
			int var8 = 20;
			if((p_71914_5_ & 3) == 3)
			{
				var8 = 40;
			}
			if(p_71914_7_ > 0)
			{
				var8 -= 2 << p_71914_7_;
				if(var8 < 10)
				{
					var8 = 10;
				}
			}
			if(p_71914_1_.rand.nextInt(var8) == 0)
			{
				int var9 = idDropped(p_71914_5_, p_71914_1_.rand, p_71914_7_);
				dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(var9, 1, damageDropped(p_71914_5_)));
			}
			var8 = 200;
			if(p_71914_7_ > 0)
			{
				var8 -= 10 << p_71914_7_;
				if(var8 < 40)
				{
					var8 = 40;
				}
			}
			if((p_71914_5_ & 3) == 0 && p_71914_1_.rand.nextInt(var8) == 0)
			{
				dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(Item.appleRed, 1, 0));
			}
		}
	}
	
	@Override public int getBlockColor()
	{
		double var1 = 0.5D;
		double var3 = 1.0D;
		return ColorizerFoliage.getFoliageColor(var1, var3);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return (par2 & 3) == 1 ? iconArray[field_94394_cP][1] : (par2 & 3) == 3 ? iconArray[field_94394_cP][3] : iconArray[field_94394_cP][0];
	}
	
	@Override public int getRenderColor(int par1)
	{
		return (par1 & 3) == 1 ? ColorizerFoliage.getFoliageColorPine() : (par1 & 3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic();
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	@Override public void harvestBlock(World p_71893_1_, EntityPlayer p_71893_2_, int p_71893_3_, int p_71893_4_, int p_71893_5_, int p_71893_6_)
	{
		if(!p_71893_1_.isRemote && p_71893_2_.getCurrentEquippedItem() != null && p_71893_2_.getCurrentEquippedItem().itemID == Item.shears.itemID)
		{
			p_71893_2_.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(p_71893_1_, p_71893_3_, p_71893_4_, p_71893_5_, new ItemStack(Block.leaves.blockID, 1, p_71893_6_ & 3));
		} else
		{
			super.harvestBlock(p_71893_1_, p_71893_2_, p_71893_3_, p_71893_4_, p_71893_5_, p_71893_6_);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.sapling.blockID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return !graphicsLevel;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return p_71925_1_.nextInt(20) == 0 ? 1 : 0;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1)
		{
			double var6 = par2 + par5Random.nextFloat();
			double var8 = par3 - 0.05D;
			double var10 = par4 + par5Random.nextFloat();
			par1World.spawnParticle("dripWater", var6, var8, var10, 0.0D, 0.0D, 0.0D);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		for(int var2 = 0; var2 < field_94396_b.length; ++var2)
		{
			iconArray[var2] = new Icon[field_94396_b[var2].length];
			for(int var3 = 0; var3 < field_94396_b[var2].length; ++var3)
			{
				iconArray[var2][var3] = par1IconRegister.registerIcon(field_94396_b[var2][var3]);
			}
		}
	}
	
	private void removeLeaves(World p_72132_1_, int p_72132_2_, int p_72132_3_, int p_72132_4_)
	{
		dropBlockAsItem(p_72132_1_, p_72132_2_, p_72132_3_, p_72132_4_, p_72132_1_.getBlockMetadata(p_72132_2_, p_72132_3_, p_72132_4_), 0);
		p_72132_1_.setBlockToAir(p_72132_2_, p_72132_3_, p_72132_4_);
	}
	
	public void setGraphicsLevel(boolean par1)
	{
		graphicsLevel = par1;
		field_94394_cP = par1 ? 0 : 1;
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			int var6 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
			if((var6 & 8) != 0 && (var6 & 4) == 0)
			{
				byte var7 = 4;
				int var8 = var7 + 1;
				byte var9 = 32;
				int var10 = var9 * var9;
				int var11 = var9 / 2;
				if(adjacentTreeBlocks == null)
				{
					adjacentTreeBlocks = new int[var9 * var9 * var9];
				}
				int var12;
				if(p_71847_1_.checkChunksExist(p_71847_2_ - var8, p_71847_3_ - var8, p_71847_4_ - var8, p_71847_2_ + var8, p_71847_3_ + var8, p_71847_4_ + var8))
				{
					int var13;
					int var14;
					int var15;
					for(var12 = -var7; var12 <= var7; ++var12)
					{
						for(var13 = -var7; var13 <= var7; ++var13)
						{
							for(var14 = -var7; var14 <= var7; ++var14)
							{
								var15 = p_71847_1_.getBlockId(p_71847_2_ + var12, p_71847_3_ + var13, p_71847_4_ + var14);
								if(var15 == Block.wood.blockID)
								{
									adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
								} else if(var15 == Block.leaves.blockID)
								{
									adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
								} else
								{
									adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
								}
							}
						}
					}
					for(var12 = 1; var12 <= 4; ++var12)
					{
						for(var13 = -var7; var13 <= var7; ++var13)
						{
							for(var14 = -var7; var14 <= var7; ++var14)
							{
								for(var15 = -var7; var15 <= var7; ++var15)
								{
									if(adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
									{
										if(adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
										{
											adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
										}
										if(adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
										{
											adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
										}
										if(adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
										{
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
										}
										if(adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
										{
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
										}
										if(adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 - 1] == -2)
										{
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 - 1] = var12;
										}
										if(adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
										{
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
										}
									}
								}
							}
						}
					}
				}
				var12 = adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];
				if(var12 >= 0)
				{
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var6 & -9, 4);
				} else
				{
					removeLeaves(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_);
				}
			}
		}
	}
}
