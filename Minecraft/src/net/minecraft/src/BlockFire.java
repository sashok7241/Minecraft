package net.minecraft.src;

import java.util.Random;

public class BlockFire extends Block
{
	private int[] chanceToEncourageFire = new int[256];
	private int[] abilityToCatchFire = new int[256];
	private Icon[] iconArray;
	
	protected BlockFire(int par1)
	{
		super(par1, Material.fire);
		setTickRandomly(true);
	}
	
	public boolean canBlockCatchFire(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return chanceToEncourageFire[par1IBlockAccess.getBlockId(par2, par3, par4)] > 0;
	}
	
	private boolean canNeighborBurn(World par1World, int par2, int par3, int par4)
	{
		return canBlockCatchFire(par1World, par2 + 1, par3, par4) ? true : canBlockCatchFire(par1World, par2 - 1, par3, par4) ? true : canBlockCatchFire(par1World, par2, par3 - 1, par4) ? true : canBlockCatchFire(par1World, par2, par3 + 1, par4) ? true : canBlockCatchFire(par1World, par2, par3, par4 - 1) ? true : canBlockCatchFire(par1World, par2, par3, par4 + 1);
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || canNeighborBurn(par1World, par2, par3, par4);
	}
	
	@Override public boolean func_82506_l()
	{
		return false;
	}
	
	public Icon func_94438_c(int par1)
	{
		return iconArray[par1];
	}
	
	private int getChanceOfNeighborsEncouragingFire(World par1World, int par2, int par3, int par4)
	{
		byte var5 = 0;
		if(!par1World.isAirBlock(par2, par3, par4)) return 0;
		else
		{
			int var6 = getChanceToEncourageFire(par1World, par2 + 1, par3, par4, var5);
			var6 = getChanceToEncourageFire(par1World, par2 - 1, par3, par4, var6);
			var6 = getChanceToEncourageFire(par1World, par2, par3 - 1, par4, var6);
			var6 = getChanceToEncourageFire(par1World, par2, par3 + 1, par4, var6);
			var6 = getChanceToEncourageFire(par1World, par2, par3, par4 - 1, var6);
			var6 = getChanceToEncourageFire(par1World, par2, par3, par4 + 1, var6);
			return var6;
		}
	}
	
	public int getChanceToEncourageFire(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = chanceToEncourageFire[par1World.getBlockId(par2, par3, par4)];
		return var6 > par5 ? var6 : par5;
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
	
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(par1World.provider.dimensionId > 0 || par1World.getBlockId(par2, par3 - 1, par4) != Block.obsidian.blockID || !Block.portal.tryToCreatePortal(par1World, par2, par3, par4))
		{
			if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !canNeighborBurn(par1World, par2, par3, par4))
			{
				par1World.setBlockToAir(par2, par3, par4);
			} else
			{
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World) + par1World.rand.nextInt(10));
			}
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !canNeighborBurn(par1World, par2, par3, par4))
		{
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
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
	
	private void setBurnRate(int par1, int par2, int par3)
	{
		chanceToEncourageFire[par1] = par2;
		abilityToCatchFire[par1] = par3;
	}
	
	@Override public int tickRate(World par1World)
	{
		return 30;
	}
	
	private void tryToCatchBlockOnFire(World par1World, int par2, int par3, int par4, int par5, Random par6Random, int par7)
	{
		int var8 = abilityToCatchFire[par1World.getBlockId(par2, par3, par4)];
		if(par6Random.nextInt(par5) < var8)
		{
			boolean var9 = par1World.getBlockId(par2, par3, par4) == Block.tnt.blockID;
			if(par6Random.nextInt(par7 + 10) < 5 && !par1World.canLightningStrikeAt(par2, par3, par4))
			{
				int var10 = par7 + par6Random.nextInt(5) / 4;
				if(var10 > 15)
				{
					var10 = 15;
				}
				par1World.setBlock(par2, par3, par4, blockID, var10, 3);
			} else
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
			if(var9)
			{
				Block.tnt.onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
			}
		}
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par1World.getGameRules().getGameRuleBooleanValue("doFireTick"))
		{
			boolean var6 = par1World.getBlockId(par2, par3 - 1, par4) == Block.netherrack.blockID;
			if(par1World.provider instanceof WorldProviderEnd && par1World.getBlockId(par2, par3 - 1, par4) == Block.bedrock.blockID)
			{
				var6 = true;
			}
			if(!canPlaceBlockAt(par1World, par2, par3, par4))
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
			if(!var6 && par1World.isRaining() && (par1World.canLightningStrikeAt(par2, par3, par4) || par1World.canLightningStrikeAt(par2 - 1, par3, par4) || par1World.canLightningStrikeAt(par2 + 1, par3, par4) || par1World.canLightningStrikeAt(par2, par3, par4 - 1) || par1World.canLightningStrikeAt(par2, par3, par4 + 1)))
			{
				par1World.setBlockToAir(par2, par3, par4);
			} else
			{
				int var7 = par1World.getBlockMetadata(par2, par3, par4);
				if(var7 < 15)
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + par5Random.nextInt(3) / 2, 4);
				}
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World) + par5Random.nextInt(10));
				if(!var6 && !canNeighborBurn(par1World, par2, par3, par4))
				{
					if(!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) || var7 > 3)
					{
						par1World.setBlockToAir(par2, par3, par4);
					}
				} else if(!var6 && !canBlockCatchFire(par1World, par2, par3 - 1, par4) && var7 == 15 && par5Random.nextInt(4) == 0)
				{
					par1World.setBlockToAir(par2, par3, par4);
				} else
				{
					boolean var8 = par1World.isBlockHighHumidity(par2, par3, par4);
					byte var9 = 0;
					if(var8)
					{
						var9 = -50;
					}
					tryToCatchBlockOnFire(par1World, par2 + 1, par3, par4, 300 + var9, par5Random, var7);
					tryToCatchBlockOnFire(par1World, par2 - 1, par3, par4, 300 + var9, par5Random, var7);
					tryToCatchBlockOnFire(par1World, par2, par3 - 1, par4, 250 + var9, par5Random, var7);
					tryToCatchBlockOnFire(par1World, par2, par3 + 1, par4, 250 + var9, par5Random, var7);
					tryToCatchBlockOnFire(par1World, par2, par3, par4 - 1, 300 + var9, par5Random, var7);
					tryToCatchBlockOnFire(par1World, par2, par3, par4 + 1, 300 + var9, par5Random, var7);
					for(int var10 = par2 - 1; var10 <= par2 + 1; ++var10)
					{
						for(int var11 = par4 - 1; var11 <= par4 + 1; ++var11)
						{
							for(int var12 = par3 - 1; var12 <= par3 + 4; ++var12)
							{
								if(var10 != par2 || var12 != par3 || var11 != par4)
								{
									int var13 = 100;
									if(var12 > par3 + 1)
									{
										var13 += (var12 - (par3 + 1)) * 100;
									}
									int var14 = getChanceOfNeighborsEncouragingFire(par1World, var10, var12, var11);
									if(var14 > 0)
									{
										int var15 = (var14 + 40 + par1World.difficultySetting * 7) / (var7 + 30);
										if(var8)
										{
											var15 /= 2;
										}
										if(var15 > 0 && par5Random.nextInt(var13) <= var15 && (!par1World.isRaining() || !par1World.canLightningStrikeAt(var10, var12, var11)) && !par1World.canLightningStrikeAt(var10 - 1, var12, par4) && !par1World.canLightningStrikeAt(var10 + 1, var12, var11) && !par1World.canLightningStrikeAt(var10, var12, var11 - 1) && !par1World.canLightningStrikeAt(var10, var12, var11 + 1))
										{
											int var16 = var7 + par5Random.nextInt(5) / 4;
											if(var16 > 15)
											{
												var16 = 15;
											}
											par1World.setBlock(var10, var12, var11, blockID, var16, 3);
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
