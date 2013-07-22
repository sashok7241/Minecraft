package net.minecraft.src;

import java.util.Random;

public class WorldGenDungeons extends WorldGenerator
{
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		byte var6 = 3;
		int var7 = p_76484_2_.nextInt(2) + 2;
		int var8 = p_76484_2_.nextInt(2) + 2;
		int var9 = 0;
		int var10;
		int var11;
		int var12;
		for(var10 = p_76484_3_ - var7 - 1; var10 <= p_76484_3_ + var7 + 1; ++var10)
		{
			for(var11 = p_76484_4_ - 1; var11 <= p_76484_4_ + var6 + 1; ++var11)
			{
				for(var12 = p_76484_5_ - var8 - 1; var12 <= p_76484_5_ + var8 + 1; ++var12)
				{
					Material var13 = p_76484_1_.getBlockMaterial(var10, var11, var12);
					if(var11 == p_76484_4_ - 1 && !var13.isSolid()) return false;
					if(var11 == p_76484_4_ + var6 + 1 && !var13.isSolid()) return false;
					if((var10 == p_76484_3_ - var7 - 1 || var10 == p_76484_3_ + var7 + 1 || var12 == p_76484_5_ - var8 - 1 || var12 == p_76484_5_ + var8 + 1) && var11 == p_76484_4_ && p_76484_1_.isAirBlock(var10, var11, var12) && p_76484_1_.isAirBlock(var10, var11 + 1, var12))
					{
						++var9;
					}
				}
			}
		}
		if(var9 >= 1 && var9 <= 5)
		{
			for(var10 = p_76484_3_ - var7 - 1; var10 <= p_76484_3_ + var7 + 1; ++var10)
			{
				for(var11 = p_76484_4_ + var6; var11 >= p_76484_4_ - 1; --var11)
				{
					for(var12 = p_76484_5_ - var8 - 1; var12 <= p_76484_5_ + var8 + 1; ++var12)
					{
						if(var10 != p_76484_3_ - var7 - 1 && var11 != p_76484_4_ - 1 && var12 != p_76484_5_ - var8 - 1 && var10 != p_76484_3_ + var7 + 1 && var11 != p_76484_4_ + var6 + 1 && var12 != p_76484_5_ + var8 + 1)
						{
							p_76484_1_.setBlockToAir(var10, var11, var12);
						} else if(var11 >= 0 && !p_76484_1_.getBlockMaterial(var10, var11 - 1, var12).isSolid())
						{
							p_76484_1_.setBlockToAir(var10, var11, var12);
						} else if(p_76484_1_.getBlockMaterial(var10, var11, var12).isSolid())
						{
							if(var11 == p_76484_4_ - 1 && p_76484_2_.nextInt(4) != 0)
							{
								p_76484_1_.setBlock(var10, var11, var12, Block.cobblestoneMossy.blockID, 0, 2);
							} else
							{
								p_76484_1_.setBlock(var10, var11, var12, Block.cobblestone.blockID, 0, 2);
							}
						}
					}
				}
			}
			var10 = 0;
			while(var10 < 2)
			{
				var11 = 0;
				while(true)
				{
					if(var11 < 3)
					{
						label210:
						{
							var12 = p_76484_3_ + p_76484_2_.nextInt(var7 * 2 + 1) - var7;
							int var14 = p_76484_5_ + p_76484_2_.nextInt(var8 * 2 + 1) - var8;
							if(p_76484_1_.isAirBlock(var12, p_76484_4_, var14))
							{
								int var15 = 0;
								if(p_76484_1_.getBlockMaterial(var12 - 1, p_76484_4_, var14).isSolid())
								{
									++var15;
								}
								if(p_76484_1_.getBlockMaterial(var12 + 1, p_76484_4_, var14).isSolid())
								{
									++var15;
								}
								if(p_76484_1_.getBlockMaterial(var12, p_76484_4_, var14 - 1).isSolid())
								{
									++var15;
								}
								if(p_76484_1_.getBlockMaterial(var12, p_76484_4_, var14 + 1).isSolid())
								{
									++var15;
								}
								if(var15 == 1)
								{
									p_76484_1_.setBlock(var12, p_76484_4_, var14, Block.chest.blockID, 0, 2);
									TileEntityChest var16 = (TileEntityChest) p_76484_1_.getBlockTileEntity(var12, p_76484_4_, var14);
									if(var16 != null)
									{
										for(int var17 = 0; var17 < 8; ++var17)
										{
											ItemStack var18 = pickCheckLootItem(p_76484_2_);
											if(var18 != null)
											{
												var16.setInventorySlotContents(p_76484_2_.nextInt(var16.getSizeInventory()), var18);
											}
										}
									}
									break label210;
								}
							}
							++var11;
							continue;
						}
					}
					++var10;
					break;
				}
			}
			p_76484_1_.setBlock(p_76484_3_, p_76484_4_, p_76484_5_, Block.mobSpawner.blockID, 0, 2);
			TileEntityMobSpawner var19 = (TileEntityMobSpawner) p_76484_1_.getBlockTileEntity(p_76484_3_, p_76484_4_, p_76484_5_);
			if(var19 != null)
			{
				var19.getSpawnerLogic().setMobID(pickMobSpawner(p_76484_2_));
			} else
			{
				System.err.println("Failed to fetch mob spawner entity at (" + p_76484_3_ + ", " + p_76484_4_ + ", " + p_76484_5_ + ")");
			}
			return true;
		} else return false;
	}
	
	private ItemStack pickCheckLootItem(Random p_76544_1_)
	{
		int var2 = p_76544_1_.nextInt(12);
		return var2 == 0 ? new ItemStack(Item.saddle) : var2 == 1 ? new ItemStack(Item.ingotIron, p_76544_1_.nextInt(4) + 1) : var2 == 2 ? new ItemStack(Item.bread) : var2 == 3 ? new ItemStack(Item.wheat, p_76544_1_.nextInt(4) + 1) : var2 == 4 ? new ItemStack(Item.gunpowder, p_76544_1_.nextInt(4) + 1) : var2 == 5 ? new ItemStack(Item.silk, p_76544_1_.nextInt(4) + 1) : var2 == 6 ? new ItemStack(Item.bucketEmpty) : var2 == 7 && p_76544_1_.nextInt(100) == 0 ? new ItemStack(Item.appleGold) : var2 == 8 && p_76544_1_.nextInt(2) == 0 ? new ItemStack(Item.redstone, p_76544_1_.nextInt(4) + 1) : var2 == 9 && p_76544_1_.nextInt(10) == 0 ? new ItemStack(Item.itemsList[Item.record13.itemID + p_76544_1_.nextInt(2)]) : var2 == 10 ? new ItemStack(Item.dyePowder, 1, 3) : var2 == 11 ? Item.enchantedBook.func_92109_a(p_76544_1_) : null;
	}
	
	private String pickMobSpawner(Random p_76543_1_)
	{
		int var2 = p_76543_1_.nextInt(4);
		return var2 == 0 ? "Skeleton" : var2 == 1 ? "Zombie" : var2 == 2 ? "Zombie" : var2 == 3 ? "Spider" : "";
	}
}
