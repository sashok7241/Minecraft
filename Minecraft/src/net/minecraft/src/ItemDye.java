package net.minecraft.src;

import java.util.List;

public class ItemDye extends Item
{
	public static final String[] dyeColorNames = new String[] { "black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "lightBlue", "magenta", "orange", "white" };
	public static final String[] field_94595_b = new String[] { "dyePowder_black", "dyePowder_red", "dyePowder_green", "dyePowder_brown", "dyePowder_blue", "dyePowder_purple", "dyePowder_cyan", "dyePowder_silver", "dyePowder_gray", "dyePowder_pink", "dyePowder_lime", "dyePowder_yellow", "dyePowder_lightBlue", "dyePowder_magenta", "dyePowder_orange", "dyePowder_white" };
	public static final int[] dyeColors = new int[] { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
	private Icon[] field_94594_d;
	
	public ItemDye(int p_i3645_1_)
	{
		super(p_i3645_1_);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		int var2 = MathHelper.clamp_int(par1, 0, 15);
		return field_94594_d[var2];
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 0; var4 < 16; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		int var2 = MathHelper.clamp_int(p_77667_1_.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + dyeColorNames[var2];
	}
	
	@Override public boolean itemInteractionForEntity(ItemStack p_77646_1_, EntityLiving p_77646_2_)
	{
		if(p_77646_2_ instanceof EntitySheep)
		{
			EntitySheep var3 = (EntitySheep) p_77646_2_;
			int var4 = BlockCloth.getBlockFromDye(p_77646_1_.getItemDamage());
			if(!var3.getSheared() && var3.getFleeceColor() != var4)
			{
				var3.setFleeceColor(var4);
				--p_77646_1_.stackSize;
			}
			return true;
		} else return false;
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else
		{
			if(p_77648_1_.getItemDamage() == 15)
			{
				if(func_96604_a(p_77648_1_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_))
				{
					if(!p_77648_3_.isRemote)
					{
						p_77648_3_.playAuxSFX(2005, p_77648_4_, p_77648_5_, p_77648_6_, 0);
					}
					return true;
				}
			} else if(p_77648_1_.getItemDamage() == 3)
			{
				int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
				int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
				if(var11 == Block.wood.blockID && BlockLog.limitToValidMetadata(var12) == 3)
				{
					if(p_77648_7_ == 0) return false;
					if(p_77648_7_ == 1) return false;
					if(p_77648_7_ == 2)
					{
						--p_77648_6_;
					}
					if(p_77648_7_ == 3)
					{
						++p_77648_6_;
					}
					if(p_77648_7_ == 4)
					{
						--p_77648_4_;
					}
					if(p_77648_7_ == 5)
					{
						++p_77648_4_;
					}
					if(p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_, p_77648_6_))
					{
						int var13 = Block.blocksList[Block.cocoaPlant.blockID].onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, 0);
						p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, Block.cocoaPlant.blockID, var13, 2);
						if(!p_77648_2_.capabilities.isCreativeMode)
						{
							--p_77648_1_.stackSize;
						}
					}
					return true;
				}
			}
			return false;
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94594_d = new Icon[field_94595_b.length];
		for(int var2 = 0; var2 < field_94595_b.length; ++var2)
		{
			field_94594_d[var2] = par1IconRegister.registerIcon(field_94595_b[var2]);
		}
	}
	
	public static void func_96603_a(World par0World, int par1, int par2, int par3, int par4)
	{
		int var5 = par0World.getBlockId(par1, par2, par3);
		if(par4 == 0)
		{
			par4 = 15;
		}
		Block var6 = var5 > 0 && var5 < Block.blocksList.length ? Block.blocksList[var5] : null;
		if(var6 != null)
		{
			var6.setBlockBoundsBasedOnState(par0World, par1, par2, par3);
			for(int var7 = 0; var7 < par4; ++var7)
			{
				double var8 = itemRand.nextGaussian() * 0.02D;
				double var10 = itemRand.nextGaussian() * 0.02D;
				double var12 = itemRand.nextGaussian() * 0.02D;
				par0World.spawnParticle("happyVillager", par1 + itemRand.nextFloat(), par2 + itemRand.nextFloat() * var6.getBlockBoundsMaxY(), par3 + itemRand.nextFloat(), var8, var10, var12);
			}
		}
	}
	
	public static boolean func_96604_a(ItemStack p_96604_0_, World p_96604_1_, int p_96604_2_, int p_96604_3_, int p_96604_4_)
	{
		int var5 = p_96604_1_.getBlockId(p_96604_2_, p_96604_3_, p_96604_4_);
		if(var5 == Block.sapling.blockID)
		{
			if(!p_96604_1_.isRemote)
			{
				if(p_96604_1_.rand.nextFloat() < 0.45D)
				{
					((BlockSapling) Block.sapling).markOrGrowMarked(p_96604_1_, p_96604_2_, p_96604_3_, p_96604_4_, p_96604_1_.rand);
				}
				--p_96604_0_.stackSize;
			}
			return true;
		} else if(var5 != Block.mushroomBrown.blockID && var5 != Block.mushroomRed.blockID)
		{
			if(var5 != Block.melonStem.blockID && var5 != Block.pumpkinStem.blockID)
			{
				if(var5 > 0 && Block.blocksList[var5] instanceof BlockCrops)
				{
					if(p_96604_1_.getBlockMetadata(p_96604_2_, p_96604_3_, p_96604_4_) == 7) return false;
					else
					{
						if(!p_96604_1_.isRemote)
						{
							((BlockCrops) Block.blocksList[var5]).fertilize(p_96604_1_, p_96604_2_, p_96604_3_, p_96604_4_);
							--p_96604_0_.stackSize;
						}
						return true;
					}
				} else
				{
					int var6;
					int var7;
					int var8;
					if(var5 == Block.cocoaPlant.blockID)
					{
						var6 = p_96604_1_.getBlockMetadata(p_96604_2_, p_96604_3_, p_96604_4_);
						var7 = BlockDirectional.getDirection(var6);
						var8 = BlockCocoa.func_72219_c(var6);
						if(var8 >= 2) return false;
						else
						{
							if(!p_96604_1_.isRemote)
							{
								++var8;
								p_96604_1_.setBlockMetadataWithNotify(p_96604_2_, p_96604_3_, p_96604_4_, var8 << 2 | var7, 2);
								--p_96604_0_.stackSize;
							}
							return true;
						}
					} else if(var5 != Block.grass.blockID) return false;
					else
					{
						if(!p_96604_1_.isRemote)
						{
							--p_96604_0_.stackSize;
							label102: for(var6 = 0; var6 < 128; ++var6)
							{
								var7 = p_96604_2_;
								var8 = p_96604_3_ + 1;
								int var9 = p_96604_4_;
								for(int var10 = 0; var10 < var6 / 16; ++var10)
								{
									var7 += itemRand.nextInt(3) - 1;
									var8 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
									var9 += itemRand.nextInt(3) - 1;
									if(p_96604_1_.getBlockId(var7, var8 - 1, var9) != Block.grass.blockID || p_96604_1_.isBlockNormalCube(var7, var8, var9))
									{
										continue label102;
									}
								}
								if(p_96604_1_.getBlockId(var7, var8, var9) == 0)
								{
									if(itemRand.nextInt(10) != 0)
									{
										if(Block.tallGrass.canBlockStay(p_96604_1_, var7, var8, var9))
										{
											p_96604_1_.setBlock(var7, var8, var9, Block.tallGrass.blockID, 1, 3);
										}
									} else if(itemRand.nextInt(3) != 0)
									{
										if(Block.plantYellow.canBlockStay(p_96604_1_, var7, var8, var9))
										{
											p_96604_1_.setBlock(var7, var8, var9, Block.plantYellow.blockID);
										}
									} else if(Block.plantRed.canBlockStay(p_96604_1_, var7, var8, var9))
									{
										p_96604_1_.setBlock(var7, var8, var9, Block.plantRed.blockID);
									}
								}
							}
						}
						return true;
					}
				}
			} else if(p_96604_1_.getBlockMetadata(p_96604_2_, p_96604_3_, p_96604_4_) == 7) return false;
			else
			{
				if(!p_96604_1_.isRemote)
				{
					((BlockStem) Block.blocksList[var5]).fertilizeStem(p_96604_1_, p_96604_2_, p_96604_3_, p_96604_4_);
					--p_96604_0_.stackSize;
				}
				return true;
			}
		} else
		{
			if(!p_96604_1_.isRemote)
			{
				if(p_96604_1_.rand.nextFloat() < 0.4D)
				{
					((BlockMushroom) Block.blocksList[var5]).fertilizeMushroom(p_96604_1_, p_96604_2_, p_96604_3_, p_96604_4_, p_96604_1_.rand);
				}
				--p_96604_0_.stackSize;
			}
			return true;
		}
	}
}
