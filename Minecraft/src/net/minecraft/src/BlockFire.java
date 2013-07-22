package net.minecraft.src;

import java.util.Random;

public class BlockFire extends Block
{
	private int[] chanceToEncourageFire = new int[256];
	private int[] abilityToCatchFire = new int[256];
	private Icon[] iconArray;
	
	protected BlockFire(int p_i9056_1_)
	{
		super(p_i9056_1_, Material.fire);
		setTickRandomly(true);
	}
	
	public boolean canBlockCatchFire(IBlockAccess p_72256_1_, int p_72256_2_, int p_72256_3_, int p_72256_4_)
	{
		return chanceToEncourageFire[p_72256_1_.getBlockId(p_72256_2_, p_72256_3_, p_72256_4_)] > 0;
	}
	
	private boolean canNeighborBurn(World p_72251_1_, int p_72251_2_, int p_72251_3_, int p_72251_4_)
	{
		return canBlockCatchFire(p_72251_1_, p_72251_2_ + 1, p_72251_3_, p_72251_4_) ? true : canBlockCatchFire(p_72251_1_, p_72251_2_ - 1, p_72251_3_, p_72251_4_) ? true : canBlockCatchFire(p_72251_1_, p_72251_2_, p_72251_3_ - 1, p_72251_4_) ? true : canBlockCatchFire(p_72251_1_, p_72251_2_, p_72251_3_ + 1, p_72251_4_) ? true : canBlockCatchFire(p_72251_1_, p_72251_2_, p_72251_3_, p_72251_4_ - 1) ? true : canBlockCatchFire(p_72251_1_, p_72251_2_, p_72251_3_, p_72251_4_ + 1);
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return p_71930_1_.doesBlockHaveSolidTopSurface(p_71930_2_, p_71930_3_ - 1, p_71930_4_) || canNeighborBurn(p_71930_1_, p_71930_2_, p_71930_3_, p_71930_4_);
	}
	
	@Override public boolean func_82506_l()
	{
		return false;
	}
	
	public Icon func_94438_c(int par1)
	{
		return iconArray[par1];
	}
	
	private int getChanceOfNeighborsEncouragingFire(World p_72254_1_, int p_72254_2_, int p_72254_3_, int p_72254_4_)
	{
		byte var5 = 0;
		if(!p_72254_1_.isAirBlock(p_72254_2_, p_72254_3_, p_72254_4_)) return 0;
		else
		{
			int var6 = getChanceToEncourageFire(p_72254_1_, p_72254_2_ + 1, p_72254_3_, p_72254_4_, var5);
			var6 = getChanceToEncourageFire(p_72254_1_, p_72254_2_ - 1, p_72254_3_, p_72254_4_, var6);
			var6 = getChanceToEncourageFire(p_72254_1_, p_72254_2_, p_72254_3_ - 1, p_72254_4_, var6);
			var6 = getChanceToEncourageFire(p_72254_1_, p_72254_2_, p_72254_3_ + 1, p_72254_4_, var6);
			var6 = getChanceToEncourageFire(p_72254_1_, p_72254_2_, p_72254_3_, p_72254_4_ - 1, var6);
			var6 = getChanceToEncourageFire(p_72254_1_, p_72254_2_, p_72254_3_, p_72254_4_ + 1, var6);
			return var6;
		}
	}
	
	public int getChanceToEncourageFire(World p_72252_1_, int p_72252_2_, int p_72252_3_, int p_72252_4_, int p_72252_5_)
	{
		int var6 = chanceToEncourageFire[p_72252_1_.getBlockId(p_72252_2_, p_72252_3_, p_72252_4_)];
		return var6 > p_72252_5_ ? var6 : p_72252_5_;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		return null;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return iconArray[0];
	}
	
	@Override public int getRenderType()
	{
		return 3;
	}
	
	@Override public void initializeBlock()
	{
		setBurnRate(Block.planks.blockID, 5, 20);
		setBurnRate(Block.woodDoubleSlab.blockID, 5, 20);
		setBurnRate(Block.woodSingleSlab.blockID, 5, 20);
		setBurnRate(Block.fence.blockID, 5, 20);
		setBurnRate(Block.stairsWoodOak.blockID, 5, 20);
		setBurnRate(Block.stairsWoodBirch.blockID, 5, 20);
		setBurnRate(Block.stairsWoodSpruce.blockID, 5, 20);
		setBurnRate(Block.stairsWoodJungle.blockID, 5, 20);
		setBurnRate(Block.wood.blockID, 5, 5);
		setBurnRate(Block.leaves.blockID, 30, 60);
		setBurnRate(Block.bookShelf.blockID, 30, 20);
		setBurnRate(Block.tnt.blockID, 15, 100);
		setBurnRate(Block.tallGrass.blockID, 60, 100);
		setBurnRate(Block.cloth.blockID, 30, 60);
		setBurnRate(Block.vine.blockID, 15, 100);
	}
	
	@Override public boolean isCollidable()
	{
		return false;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		if(p_71861_1_.provider.dimensionId > 0 || p_71861_1_.getBlockId(p_71861_2_, p_71861_3_ - 1, p_71861_4_) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_))
		{
			if(!p_71861_1_.doesBlockHaveSolidTopSurface(p_71861_2_, p_71861_3_ - 1, p_71861_4_) && !canNeighborBurn(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_))
			{
				p_71861_1_.setBlockToAir(p_71861_2_, p_71861_3_, p_71861_4_);
			} else
			{
				p_71861_1_.scheduleBlockUpdate(p_71861_2_, p_71861_3_, p_71861_4_, blockID, tickRate(p_71861_1_) + p_71861_1_.rand.nextInt(10));
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		if(!p_71863_1_.doesBlockHaveSolidTopSurface(p_71863_2_, p_71863_3_ - 1, p_71863_4_) && !canNeighborBurn(p_71863_1_, p_71863_2_, p_71863_3_, p_71863_4_))
		{
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par5Random.nextInt(24) == 0)
		{
			par1World.playSound(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "fire.fire", 1.0F + par5Random.nextFloat(), par5Random.nextFloat() * 0.7F + 0.3F, false);
		}
		int var6;
		float var7;
		float var8;
		float var9;
		if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(par1World, par2, par3 - 1, par4))
		{
			if(Block.fire.canBlockCatchFire(par1World, par2 - 1, par3, par4))
			{
				for(var6 = 0; var6 < 2; ++var6)
				{
					var7 = par2 + par5Random.nextFloat() * 0.1F;
					var8 = par3 + par5Random.nextFloat();
					var9 = par4 + par5Random.nextFloat();
					par1World.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
				}
			}
			if(Block.fire.canBlockCatchFire(par1World, par2 + 1, par3, par4))
			{
				for(var6 = 0; var6 < 2; ++var6)
				{
					var7 = par2 + 1 - par5Random.nextFloat() * 0.1F;
					var8 = par3 + par5Random.nextFloat();
					var9 = par4 + par5Random.nextFloat();
					par1World.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
				}
			}
			if(Block.fire.canBlockCatchFire(par1World, par2, par3, par4 - 1))
			{
				for(var6 = 0; var6 < 2; ++var6)
				{
					var7 = par2 + par5Random.nextFloat();
					var8 = par3 + par5Random.nextFloat();
					var9 = par4 + par5Random.nextFloat() * 0.1F;
					par1World.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
				}
			}
			if(Block.fire.canBlockCatchFire(par1World, par2, par3, par4 + 1))
			{
				for(var6 = 0; var6 < 2; ++var6)
				{
					var7 = par2 + par5Random.nextFloat();
					var8 = par3 + par5Random.nextFloat();
					var9 = par4 + 1 - par5Random.nextFloat() * 0.1F;
					par1World.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
				}
			}
			if(Block.fire.canBlockCatchFire(par1World, par2, par3 + 1, par4))
			{
				for(var6 = 0; var6 < 2; ++var6)
				{
					var7 = par2 + par5Random.nextFloat();
					var8 = par3 + 1 - par5Random.nextFloat() * 0.1F;
					var9 = par4 + par5Random.nextFloat();
					par1World.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
				}
			}
		} else
		{
			for(var6 = 0; var6 < 3; ++var6)
			{
				var7 = par2 + par5Random.nextFloat();
				var8 = par3 + par5Random.nextFloat() * 0.5F + 0.5F;
				var9 = par4 + par5Random.nextFloat();
				par1World.spawnParticle("largesmoke", var7, var8, var9, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[] { par1IconRegister.registerIcon("fire_0"), par1IconRegister.registerIcon("fire_1") };
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	private void setBurnRate(int p_72253_1_, int p_72253_2_, int p_72253_3_)
	{
		chanceToEncourageFire[p_72253_1_] = p_72253_2_;
		abilityToCatchFire[p_72253_1_] = p_72253_3_;
	}
	
	@Override public int tickRate(World p_71859_1_)
	{
		return 30;
	}
	
	private void tryToCatchBlockOnFire(World p_72255_1_, int p_72255_2_, int p_72255_3_, int p_72255_4_, int p_72255_5_, Random p_72255_6_, int p_72255_7_)
	{
		int var8 = abilityToCatchFire[p_72255_1_.getBlockId(p_72255_2_, p_72255_3_, p_72255_4_)];
		if(p_72255_6_.nextInt(p_72255_5_) < var8)
		{
			boolean var9 = p_72255_1_.getBlockId(p_72255_2_, p_72255_3_, p_72255_4_) == Block.tnt.blockID;
			if(p_72255_6_.nextInt(p_72255_7_ + 10) < 5 && !p_72255_1_.canLightningStrikeAt(p_72255_2_, p_72255_3_, p_72255_4_))
			{
				int var10 = p_72255_7_ + p_72255_6_.nextInt(5) / 4;
				if(var10 > 15)
				{
					var10 = 15;
				}
				p_72255_1_.setBlock(p_72255_2_, p_72255_3_, p_72255_4_, blockID, var10, 3);
			} else
			{
				p_72255_1_.setBlockToAir(p_72255_2_, p_72255_3_, p_72255_4_);
			}
			if(var9)
			{
				Block.tnt.onBlockDestroyedByPlayer(p_72255_1_, p_72255_2_, p_72255_3_, p_72255_4_, 1);
			}
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(p_71847_1_.getGameRules().getGameRuleBooleanValue("doFireTick"))
		{
			boolean var6 = p_71847_1_.getBlockId(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == Block.netherrack.blockID;
			if(p_71847_1_.provider instanceof WorldProviderEnd && p_71847_1_.getBlockId(p_71847_2_, p_71847_3_ - 1, p_71847_4_) == Block.bedrock.blockID)
			{
				var6 = true;
			}
			if(!canPlaceBlockAt(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_))
			{
				p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
			}
			if(!var6 && p_71847_1_.isRaining() && (p_71847_1_.canLightningStrikeAt(p_71847_2_, p_71847_3_, p_71847_4_) || p_71847_1_.canLightningStrikeAt(p_71847_2_ - 1, p_71847_3_, p_71847_4_) || p_71847_1_.canLightningStrikeAt(p_71847_2_ + 1, p_71847_3_, p_71847_4_) || p_71847_1_.canLightningStrikeAt(p_71847_2_, p_71847_3_, p_71847_4_ - 1) || p_71847_1_.canLightningStrikeAt(p_71847_2_, p_71847_3_, p_71847_4_ + 1)))
			{
				p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
			} else
			{
				int var7 = p_71847_1_.getBlockMetadata(p_71847_2_, p_71847_3_, p_71847_4_);
				if(var7 < 15)
				{
					p_71847_1_.setBlockMetadataWithNotify(p_71847_2_, p_71847_3_, p_71847_4_, var7 + p_71847_5_.nextInt(3) / 2, 4);
				}
				p_71847_1_.scheduleBlockUpdate(p_71847_2_, p_71847_3_, p_71847_4_, blockID, tickRate(p_71847_1_) + p_71847_5_.nextInt(10));
				if(!var6 && !canNeighborBurn(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_))
				{
					if(!p_71847_1_.doesBlockHaveSolidTopSurface(p_71847_2_, p_71847_3_ - 1, p_71847_4_) || var7 > 3)
					{
						p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
					}
				} else if(!var6 && !canBlockCatchFire(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_) && var7 == 15 && p_71847_5_.nextInt(4) == 0)
				{
					p_71847_1_.setBlockToAir(p_71847_2_, p_71847_3_, p_71847_4_);
				} else
				{
					boolean var8 = p_71847_1_.isBlockHighHumidity(p_71847_2_, p_71847_3_, p_71847_4_);
					byte var9 = 0;
					if(var8)
					{
						var9 = -50;
					}
					tryToCatchBlockOnFire(p_71847_1_, p_71847_2_ + 1, p_71847_3_, p_71847_4_, 300 + var9, p_71847_5_, var7);
					tryToCatchBlockOnFire(p_71847_1_, p_71847_2_ - 1, p_71847_3_, p_71847_4_, 300 + var9, p_71847_5_, var7);
					tryToCatchBlockOnFire(p_71847_1_, p_71847_2_, p_71847_3_ - 1, p_71847_4_, 250 + var9, p_71847_5_, var7);
					tryToCatchBlockOnFire(p_71847_1_, p_71847_2_, p_71847_3_ + 1, p_71847_4_, 250 + var9, p_71847_5_, var7);
					tryToCatchBlockOnFire(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ - 1, 300 + var9, p_71847_5_, var7);
					tryToCatchBlockOnFire(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_ + 1, 300 + var9, p_71847_5_, var7);
					for(int var10 = p_71847_2_ - 1; var10 <= p_71847_2_ + 1; ++var10)
					{
						for(int var11 = p_71847_4_ - 1; var11 <= p_71847_4_ + 1; ++var11)
						{
							for(int var12 = p_71847_3_ - 1; var12 <= p_71847_3_ + 4; ++var12)
							{
								if(var10 != p_71847_2_ || var12 != p_71847_3_ || var11 != p_71847_4_)
								{
									int var13 = 100;
									if(var12 > p_71847_3_ + 1)
									{
										var13 += (var12 - (p_71847_3_ + 1)) * 100;
									}
									int var14 = getChanceOfNeighborsEncouragingFire(p_71847_1_, var10, var12, var11);
									if(var14 > 0)
									{
										int var15 = (var14 + 40 + p_71847_1_.difficultySetting * 7) / (var7 + 30);
										if(var8)
										{
											var15 /= 2;
										}
										if(var15 > 0 && p_71847_5_.nextInt(var13) <= var15 && (!p_71847_1_.isRaining() || !p_71847_1_.canLightningStrikeAt(var10, var12, var11)) && !p_71847_1_.canLightningStrikeAt(var10 - 1, var12, p_71847_4_) && !p_71847_1_.canLightningStrikeAt(var10 + 1, var12, var11) && !p_71847_1_.canLightningStrikeAt(var10, var12, var11 - 1) && !p_71847_1_.canLightningStrikeAt(var10, var12, var11 + 1))
										{
											int var16 = var7 + p_71847_5_.nextInt(5) / 4;
											if(var16 > 15)
											{
												var16 = 15;
											}
											p_71847_1_.setBlock(var10, var12, var11, blockID, var16, 3);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
