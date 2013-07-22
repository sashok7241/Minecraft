package net.minecraft.src;

import java.util.Random;

public class BlockVine extends Block
{
	public BlockVine(int par1)
	{
		super(par1, Material.vine);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	private boolean canBePlacedOn(int par1)
	{
		if(par1 == 0) return false;
		else
		{
			Block var2 = Block.blocksList[par1];
			return var2.renderAsNormalBlock() && var2.blockMaterial.blocksMovement();
		}
	}
	
	@Override public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		switch(par5)
		{
			case 1:
				return canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4));
			case 2:
				return canBePlacedOn(par1World.getBlockId(par2, par3, par4 + 1));
			case 3:
				return canBePlacedOn(par1World.getBlockId(par2, par3, par4 - 1));
			case 4:
				return canBePlacedOn(par1World.getBlockId(par2 + 1, par3, par4));
			case 5:
				return canBePlacedOn(par1World.getBlockId(par2 - 1, par3, par4));
			default:
				return false;
		}
	}
	
	private boolean canVineStay(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		int var6 = var5;
		if(var5 > 0)
		{
			for(int var7 = 0; var7 <= 3; ++var7)
			{
				int var8 = 1 << var7;
				if((var5 & var8) != 0 && !canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var7], par3, par4 + Direction.offsetZ[var7])) && (par1World.getBlockId(par2, par3 + 1, par4) != blockID || (par1World.getBlockMetadata(par2, par3 + 1, par4) & var8) == 0))
				{
					var6 &= ~var8;
				}
			}
		}
		if(var6 == 0 && !canBePlacedOn(par1World.getBlockId(par2, par3 + 1, par4))) return false;
		else
		{
			if(var6 != var5)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
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
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
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
	
	@Override public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		if(!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID)
		{
			par2EntityPlayer.addStat(StatList.mineBlockStatArray[blockID], 1);
			dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.vine, 1, 0));
		} else
		{
			super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
		}
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		byte var10 = 0;
		switch(par5)
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
		return var10 != 0 ? var10 : par9;
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote && !canVineStay(par1World, par2, par3, par4))
		{
			dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
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
		if(!var13 && canBePlacedOn(par1IBlockAccess.getBlockId(par2, par3 + 1, par4)))
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
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote && par1World.rand.nextInt(4) == 0)
		{
			byte var6 = 4;
			int var7 = 5;
			boolean var8 = false;
			int var9;
			int var10;
			int var11;
			label138: for(var9 = par2 - var6; var9 <= par2 + var6; ++var9)
			{
				for(var10 = par4 - var6; var10 <= par4 + var6; ++var10)
				{
					for(var11 = par3 - 1; var11 <= par3 + 1; ++var11)
					{
						if(par1World.getBlockId(var9, var11, var10) == blockID)
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
			var9 = par1World.getBlockMetadata(par2, par3, par4);
			var10 = par1World.rand.nextInt(6);
			var11 = Direction.facingToDirection[var10];
			int var12;
			int var13;
			if(var10 == 1 && par3 < 255 && par1World.isAirBlock(par2, par3 + 1, par4))
			{
				if(var8) return;
				var12 = par1World.rand.nextInt(16) & var9;
				if(var12 > 0)
				{
					for(var13 = 0; var13 <= 3; ++var13)
					{
						if(!canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var13], par3 + 1, par4 + Direction.offsetZ[var13])))
						{
							var12 &= ~(1 << var13);
						}
					}
					if(var12 > 0)
					{
						par1World.setBlock(par2, par3 + 1, par4, blockID, var12, 2);
					}
				}
			} else
			{
				int var14;
				if(var10 >= 2 && var10 <= 5 && (var9 & 1 << var11) == 0)
				{
					if(var8) return;
					var12 = par1World.getBlockId(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11]);
					if(var12 != 0 && Block.blocksList[var12] != null)
					{
						if(Block.blocksList[var12].blockMaterial.isOpaque() && Block.blocksList[var12].renderAsNormalBlock())
						{
							par1World.setBlockMetadataWithNotify(par2, par3, par4, var9 | 1 << var11, 2);
						}
					} else
					{
						var13 = var11 + 1 & 3;
						var14 = var11 + 3 & 3;
						if((var9 & 1 << var13) != 0 && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var11] + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var13])))
						{
							par1World.setBlock(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11], blockID, 1 << var13, 2);
						} else if((var9 & 1 << var14) != 0 && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var11] + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var14])))
						{
							par1World.setBlock(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11], blockID, 1 << var14, 2);
						} else if((var9 & 1 << var13) != 0 && par1World.isAirBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var13]) && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var13])))
						{
							par1World.setBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var13], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var13], blockID, 1 << (var11 + 2 & 3), 2);
						} else if((var9 & 1 << var14) != 0 && par1World.isAirBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var14]) && canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var14])))
						{
							par1World.setBlock(par2 + Direction.offsetX[var11] + Direction.offsetX[var14], par3, par4 + Direction.offsetZ[var11] + Direction.offsetZ[var14], blockID, 1 << (var11 + 2 & 3), 2);
						} else if(canBePlacedOn(par1World.getBlockId(par2 + Direction.offsetX[var11], par3 + 1, par4 + Direction.offsetZ[var11])))
						{
							par1World.setBlock(par2 + Direction.offsetX[var11], par3, par4 + Direction.offsetZ[var11], blockID, 0, 2);
						}
					}
				} else if(par3 > 1)
				{
					var12 = par1World.getBlockId(par2, par3 - 1, par4);
					if(var12 == 0)
					{
						var13 = par1World.rand.nextInt(16) & var9;
						if(var13 > 0)
						{
							par1World.setBlock(par2, par3 - 1, par4, blockID, var13, 2);
						}
					} else if(var12 == blockID)
					{
						var13 = par1World.rand.nextInt(16) & var9;
						var14 = par1World.getBlockMetadata(par2, par3 - 1, par4);
						if(var14 != (var14 | var13))
						{
							par1World.setBlockMetadataWithNotify(par2, par3 - 1, par4, var14 | var13, 2);
						}
					}
				}
			}
		}
	}
}
